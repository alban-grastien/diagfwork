package diag.reiter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import util.AlarmLog;
import util.ImmutableAlarmLog;

import diag.Util;
import diag.reiter.hypos.SequentialHypothesis;
import diag.reiter.hypos.SequentialHypothesisSpace;
import diag.reiter.solvers.TopDown;

import lang.MMLDlightLexer;
import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDEvent;
import edu.supercom.util.Options;

public class Test {

	public static Network readNetwork(Options opt) {
		try {
			final String filename = opt.getOption(true, false, "network",
					"model", "mod", "net");
			InputStream inputStream = new FileInputStream(filename);
			Reader input = new InputStreamReader(inputStream);
			MMLDlightLexer lexer = new MMLDlightLexer(new ANTLRReaderStream(
					input));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MMLDlightParser parser = new MMLDlightParser(tokens);
			parser.net();

			return MMLDlightParser.net;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}

	public static Set<YAMLDEvent> readFaults(Network net, Options opt) {
		final String faultfilename = opt.getOption(true, true, "faults");
		return new HashSet<YAMLDEvent>(Util.parseEvents(net, faultfilename));
	}

	/**
	 * Reads a hypothesis in the following form: 
	 * each fault in the hypothesis is put 
	 * in the correct order of tabular option <code>name</code>.  
	 * For instance, if <code>name</code> equals <b>h</b> 
	 * and the hypothesis is <code>[f1,f2,f1,f3]</code>, 
	 * then the options should contain <code>@h=f1 @h=f2 @h=f1 @h=f3</code>.  
	 * 
	 * @param net the network for which this hypothesis is defined.  
	 * @param name the name of the option.  
	 * @param options the list of options.  
	 * @param nbTrans the number of time steps.  
	 * */
	public static SequentialHypothesis readHypothesis(Network net, String name,
			Options opt, Set<YAMLDEvent> faultEvents, int nbTrans) {
		final List<YAMLDEvent> list = new ArrayList<YAMLDEvent>();
		for (final String str : opt.getOptions(name)) {
			final YAMLDEvent evt = net.getEvent(str, System.err);
			list.add(evt);
		}
		return new SequentialHypothesis(list, faultEvents, nbTrans);
	}

	public static AlarmLog readAlarms(Network net, Options opt) {
		final String alarmfilename = opt.getOption(true, true, "alarm", "obs");
		return ImmutableAlarmLog.readLogFile(net, alarmfilename);
	}
	
	/**
	 * Tries to open the print stream at the address given in the specified options 
	 * with the specified option name.  The method takes a default print stream 
	 * as argument in case the option is not defined or fails.  
	 * If the default print stream is null, then the option must be defined.  
	 * 
	 * @param opt the options.  
	 * @param optionName the name of the option where the file name can be found.  
	 * @param def the default print stream.  
	 * @return the print stream at address defined by option <code>optionName</code> 
	 * in <code>opt</code> if defined, <code>def</code> otherwise.  
	 * @throws IllegalArgumentException if <code>def</code> is <code>null</code> 
	 * and no correct option was defined.  
	 * */
	public static PrintStream openPrintStream(Options opt, String optionName, PrintStream def) {
		{
			final String outOption = opt.getOption("out");
			if (outOption != null) {
				try {
					return new PrintStream(new File(outOption));
				} catch (IOException e) {
				}
			}
		}
		
		if (def != null) {
			return def;
		}
		
		throw new IllegalArgumentException("Error with option " + opt);
	}
	
	public static int getNbTrans(Options opt, AlarmLog log) {
		final int stepsPerObs;
		{
			final String nb = opt.getOption("stepsPerObs");
			if (nb == null) { // Default value
				stepsPerObs = 6;
			} else {
				stepsPerObs = Integer.parseInt(nb);
			}
		}
		return log.nbEntries() * stepsPerObs;
	}

	/**
	 * Returns the hypothesis space associated 
	 * with the specified set of faults and number of time steps.  
	 * The class of the space that should be used is 
	 * the one with the name in option "space"; 
	 * the default space is "diag.reiter.hypos.SequentialHypothesis".
	 * */
	@SuppressWarnings("unchecked")
	public static <H extends Hypothesis, C extends Conflict> HypothesisSpace<H,C> getSpace(
			Options opt, Set<YAMLDEvent> faults, int nb) {
		final String className = opt.getOption("space");
		
		if (className != null) {
			try {
				final Class<?> cl = Class.forName(className);
				final Constructor<?> con = 
					cl.getDeclaredConstructor(Set.class,int.class);
				final Object result = con.newInstance(faults, nb);
				if (result instanceof HypothesisSpace<?,?>) {
					return (HypothesisSpace<H,C>)result;
				}
				System.err.println("Class " + className + " does not implement HypothesisSpace.");
			} catch (ClassNotFoundException  e) {
				System.err.println("Unknown class " + className + ".  Using default.");
			} catch (NoSuchMethodException e) {
				System.err.println("No constructor with signature (Set,int) " + 
						"for this type of space: " + className);
			} catch (IllegalArgumentException e) {
				System.err.println("No constructor with signature (Set,int) " + 
						"for this type of space: " + className);
			} catch (InstantiationException e) {
				System.err.println("Cannot instantiate constructor of: " + className);
			} catch (IllegalAccessException e) {
				System.err.println("Illegal access to constructor of: " + className);
			} catch (InvocationTargetException e) {
				System.err.println("Invocation Target Exception for " + className);
			}
		}
		
		return (HypothesisSpace<H,C>)new SequentialHypothesisSpace(faults, nb);
	}

	/**
	 * Returns a solver.  
	 * The class of the solver that should be used is 
	 * the one with the name in option "solver"; 
	 * the default solver is "diag.reiter.solver.TopDown".
	 * */	
	@SuppressWarnings("unchecked")
	public static <H extends Hypothesis, C extends Conflict> Solver<H,C> getSolver(
			Options opt) {
		final String className = opt.getOption("solver");
		
		if (className != null) {
			try {
				final Class<?> cl = Class.forName(className);
				final Constructor<?> con = 
					cl.getDeclaredConstructor();
				final Object result = con.newInstance();
				if (result instanceof Solver<?,?>) {
					return (Solver<H,C>)result;
				}
				System.err.println("Class " + className + " does not implement Solver.");
			} catch (ClassNotFoundException  e) {
				System.err.println("Unknown class " + className + ".  Using default.");
			} catch (NoSuchMethodException e) {
				System.err.println("No constructor with signature () " + 
						"for this type of solver: " + className);
			} catch (IllegalArgumentException e) {
				System.err.println("No constructor with signature () " + 
						"for this type of solver: " + className);
			} catch (InstantiationException e) {
				System.err.println("Cannot instantiate constructor of: " + className);
			} catch (IllegalAccessException e) {
				System.err.println("Illegal access to constructor of: " + className);
			} catch (InvocationTargetException e) {
				System.err.println("Invocation Target Exception for " + className);
			}
		}
		
		return new TopDown<H, C>();
	}
	
	/**
	 * Option:
	 * <ul> 
	 * <li><b>out</b>: the output file where the results are stored (default is System.out).
	 * <li><b>space</b>: the type of hypothesis space.  
	 *   This should be specified by the class of the hypothesis space, 
	 *   e.g., "diag.reiter.hypos.SetHypothesis".  
	 *   Default is "diag.reiter.hypos.SequentialHypothesisSpace".  
	 *   Obviously, the class referred to should implement the {@link HypothesisSpace} interface; 
	 *   furthermore, it should have a constructor with the following signature: 
	 *   <code>Set<YAMLDEvent>,int</code> where the set is the set of faults 
	 *   and the integer is the number of timesteps.  
	 * </li>
	 * </ul>
	 * */
	public static <H extends Hypothesis, C extends Conflict> void main(String[] args) {
		final long startingTime = System.currentTimeMillis();
		
		final Options opt = new Options(args);

		final Network net = readNetwork(opt);
		final Set<YAMLDEvent> faults = readFaults(net, opt);
		final AlarmLog log = readAlarms(net, opt);

		final HypothesisSpace<H,C> space = 
			getSpace(opt, faults, getNbTrans(opt, log));

		//final Diag<?,?> diag = new Diag(
		//			net, MMLDlightParser.st, space, faults, log);
		//diag.solve(opt);
		final Solver<H,C> solver = getSolver(opt);
		final Diagnosis<H> diag = solver.solve(opt, net, MMLDlightParser.st, log, space, faults);

		final PrintStream ps = openPrintStream(opt, "out", System.out);
		
		ps.println("FINISHED");
		for (final Candidate<H> h: diag) {
			ps.println("CANDIDATE >> " + h.getHypothesis());
		}

		ps.println("Nb SAT calls: " + DiagnosisProblem.NB_SAT_CALL);
		ps.println("Nb candidates: " + diag.candidates().size());
		ps.println("SAT Runtime (ms): " + DiagnosisProblem.SAT_RUNTIME);
		//ps.println("Hypotheses generated: " + solver.HYPOTHESES_GENERATED);
		ps.println("Total time (ms): " + (System.currentTimeMillis()-startingTime));
		
//		final ExtendedDiagnosisReport xrep = diag(net, log, faults, hypo, MMLDlightParser.st, opt);
//		final DiagnosisReport rep = xrep.getReport();
//		if (rep == null) {
//			System.err.println(xrep.getConflict());
//		} else {
//			Util.printSolution(rep, net);
//		}
		
		//final Scenario sce = rep.getScenario();
		//System.out.println(sce.toFormattedString());
	}

}
