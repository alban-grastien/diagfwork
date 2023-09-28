package diag.reiter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import diag.DiagnosisReport;
import diag.LiteralAssigner;
import diag.MMLDTranslator;
import diag.Util;
import diag.reiter.HypothesisSubspace.HypothesisSubspaceType;
import diag.reiter.HypothesisSubspace.SearchType;

import edu.supercom.util.Options;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.BufferedPrintClauseStream;
import edu.supercom.util.sat.CNF;
import edu.supercom.util.sat.ClausePruner;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.MapVariableAssigner;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;

import lang.Network;
import lang.State;
import lang.YAMLDEvent;
import util.AbstractScenario;
import util.AlarmLog;
import util.Scenario;
import util.Time;

/**
 * A diagnosis problem is a framework where the diagnosis can be performed.  
 * Tests can be performed on a diagnosis problem.  
 * An example of such test is <i>Does there exist a trajectory on the system 
 * consistent with the observation and that corresponds to a specific hypothesis</i>.  
 * The diagnosis problem is defined for a system, an observation, and a hypothesis space.  
 * At the moment, it is a class implemented in SAT, 
 * but it could ultimately be defined as an interface.  
 * 
 * @author Alban Grastien
 * */
public class DiagnosisProblem<H extends Hypothesis,C extends Conflict> {

	private final Options _opt;

	private final File _modAndObs;
	
	private final VariableLoader _loader;
	
	private final LiteralAssigner _litAss;
	
	private final Map<HypothesisSubspace<H>, VariableAssigner> _hypoAssigners;
	
	private final MapVariableAssigner _allVariables;

	// TODO: Find a way to get rid of the following variables
	private final Network _net;
	private final int _nbTrans;
	// TODO: the space should not be a parameter of the problem?
	private final HypothesisSpace<H,C> _space;
	
	public static int NB_SAT_CALL = 0;
	public static double SAT_RUNTIME= 0;
	
	public DiagnosisProblem(
			Options opt,
			Network net, 
			State st,  
			AlarmLog log, 
			HypothesisSpace<H,C> space,
			Set<YAMLDEvent> faults) {
		_opt = (opt != null) ? opt : new Options();
		_loader = new VariableLoader();
		_net = net;
		_space = space;
		
		{
			final int stepsPerObs = nbStepsPerObs(opt);
			_nbTrans = log.nbEntries() * stepsPerObs;
		}
		
		{
			Pair<File,LiteralAssigner> pair = 
				generateModAndObs(_opt, net, st, _nbTrans, log, faults);
			_modAndObs  = pair.first();
			_litAss = pair.second();
		}
		_hypoAssigners = new HashMap<HypothesisSubspace<H>, VariableAssigner>();
		_allVariables = new MapVariableAssigner();
	}
	
	private int nbStepsPerObs(Options opt) {
		final int stepsPerObs;
		{
			final String nb = opt.getOption("stepsPerObs");
			if (nb == null) { // Default value
				stepsPerObs = 6;
			} else {
				stepsPerObs = Integer.parseInt(nb);
			}
		}
		return stepsPerObs;
	}
	
	private Pair<File,LiteralAssigner> generateModAndObs(
			Options opt,
			Network net, 
			State st, 
			int nbTrans,
			AlarmLog log,
			Set<YAMLDEvent> faults
			) {
		// Allocating the variables
		final int stepsPerObs = nbStepsPerObs(opt);
		final LiteralAssigner litAss = new LiteralAssigner(_loader, net, _nbTrans);

		if ("true".equals(opt.getOption("showLiterals"))) {
			System.out.println("== literal mapping ==");
			litAss.print(System.out);
			System.out.println("== end mapping ==");
		}

		final File modAndObs = Util.openTemporaryFile("mod", "obs",null);
		final String modAndObsCNFFile = modAndObs.getPath();
		// Building the cnf file that encodes the model and the observations.
		try {
			final BufferedPrintClauseStream out1 = new BufferedPrintClauseStream(
					new PrintStream(new File(modAndObsCNFFile)));
			out1.setBufferSize(10000);
			final ClauseStream out = new ClausePruner(out1);
			final MMLDTranslator tl = new MMLDTranslator(litAss._ios);
			tl.translateNetwork(out, litAss, net, nbTrans);
			tl.noSimultaneousOccurrence(litAss, out, faults, nbTrans);
			if ("true".equals(opt.getOption("encodeTime"))) {
				Time[] times = new Time[nbTrans];
				for (int i=0 ; i<nbTrans ; i++) {
					times[i] = null;
				}
				for (int i=0 ; i<log.nbEntries() ; i++) {
					times[((i+1)*stepsPerObs)-1] = log.get(i)._time;
				}
				tl.timedForcedTransitions(litAss, out, net, times, nbTrans);
			}
			tl.state(litAss, out, st);
			// final SpyClauseStream spyout = new SpyClauseStream(out,
			// System.out);
			if ("true".equals(opt.getOption("maxTimeLag"))) {
				diag.Diag.maxTimeLag(net, log, stepsPerObs, out, litAss, tl);
			}
			for (int i = 0; i < log.nbEntries(); i++) {
				tl.observationsInInterval(litAss, out, net.observableEvents(),
						log.get(i)._events, stepsPerObs * i, stepsPerObs);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return new Pair<File, LiteralAssigner>(modAndObs, litAss);
	}
	
	// TODO: factorise testHypothesis and searchHypothesis
	public ExtendedDiagnosisReport<C> testHypothesis(H h) 
            throws IOException, InterruptedException
    {
		final List<String> cnfFiles = new ArrayList<String>();
		cnfFiles.add(_modAndObs.getPath());
		
		final VariableAssigner hypoVarass = _space.createVariables(h, _loader, _allVariables, 
				HypothesisSubspaceType.HYPO, SearchType.CONFLICT);

		// Generating the additional clauses for the hypothesis
		List<Integer> ass = null;
		final File hypoFile = Util.openTemporaryFile("hypo","hypo", null);
//		try {
			cnfFiles.add(hypoFile.getPath());
			
			final ClauseStream out = new BufferedPrintClauseStream(
					new PrintStream(new File(hypoFile.getPath())));
			//final ClauseStream other = new SpyClauseStream(out);
			ass = _space.generateSAT(h, _litAss, hypoVarass, out,  
					HypothesisSubspaceType.HYPO, SearchType.CONFLICT);
			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// Generating the assumptions file
		final File assFile = Util.openTemporaryFile("hypo","assum", null);
//		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(assFile.getPath()));
			for (final int lit: ass) {
				writer.write(Integer.toString(lit));
				writer.write("\n");
			}
			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		// Solving the SAT problem.
		ExtendedDiagnosisReport<C> result = null;
//		File cnfFile = null;
//    	try 
        { 
//			if ("true".equals(_opt.getOption("keepCNFFiles"))) {
//				cnfFile = new File("DIAG_HYPO");
//			} else {
//				cnfFile = Util.openTemporaryFile("cnf", "cnf", null);
//			}
			final String[] fileArray = new String[cnfFiles.size()];
			cnfFiles.toArray(fileArray);
//			CNF.simplerFinalizeCNF("Satisfiable if the obs match the model",
//					fileArray, cnfFile.getPath());
			
			
			final long bef = System.currentTimeMillis();
            final String solver;
            {
                final String tmp = _opt.getOption(true,false,"satcsolver","satsolver");
                solver = (tmp == null) ? "./lib/solvers/mynisat" : tmp;
            }
			final Pair<List<Integer>,List<Boolean>> satResult = CNF.solveWithAssumption2(solver, 
					assFile.getPath(), fileArray);
			NB_SAT_CALL++;
			SAT_RUNTIME += System.currentTimeMillis() - bef;

            final List<Integer> con = satResult.first();
            
			if (con == null) {
                final List<Boolean> sol = satResult.second();
				final DiagnosisReport rep = new DiagnosisReport(_net, _nbTrans, _litAss, sol);
				result = new ExtendedDiagnosisReport<C>(rep);
			} else {
//				final Conflict c = buildConflict(con, _faults, h, nbTrans, hypoVarass);
				final C c = _space.buildConflict(h, con, _nbTrans, hypoVarass);
				result = new ExtendedDiagnosisReport<C>(c);
			}
//		} catch (IOException e) {
//			e.printStackTrace();
		}
		
		// Removing the temp files
		assFile.delete();
		hypoFile.delete();
		//cnfFile.delete();
		
		return result;
	}
	
	public Candidate<H> searchHypothesis(Collection<HypothesisSubspace<H>> subspaces) {
		final List<String> cnfFiles = new ArrayList<String>();
		cnfFiles.add(_modAndObs.getPath());

		final File hypoFile = Util.openTemporaryFile("hypo","hypo", null);
		cnfFiles.add(hypoFile.getPath());
		try {
			final ClauseStream out = new BufferedPrintClauseStream(
					new PrintStream(hypoFile));
			for (final HypothesisSubspace<H> hss: subspaces) {
				addHypothesisSubspace(_loader, out, hss);
			}
			

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		final File cnfFile = Util.openTemporaryFile("cnf", "cnf", null);
		Candidate<H> result = null;
//		try { 
			final String[] fileArray = new String[cnfFiles.size()];
			cnfFiles.toArray(fileArray);
//			CNF.simplerFinalizeCNF("Satisfiable if the obs match the model",
//					fileArray, cnfFile.getPath());
			
			
			//final List<Boolean> sol = CNF.solve("./lib/solvers/picosat", cnfFile.getPath());
			final long bef = System.currentTimeMillis();
//			final List<Boolean> sol = CNF.solve("./minisat_core", cnfFile.getPath());
            final String solver;
            if (_opt.getOption("satsolver") != null) {
                solver = _opt.getOption("satsolver");
            } else {
    		  solver = "./minisat_core";
    		}
			final List<Boolean> sol = CNF.solve2(solver, fileArray);
			NB_SAT_CALL++;
			SAT_RUNTIME += System.currentTimeMillis() - bef;
			
			if (sol != null) {
				final DiagnosisReport rep = new DiagnosisReport(_net, _nbTrans, _litAss, sol);
				Scenario sce = rep.getScenario();
				if (!"true".equals(_opt.getOption("encodeTime"))) {
					sce = AbstractScenario.simplify(sce);
				} else {
					// Nothing, unfortunately
				}
				result = new Candidate<H>(_space.getHypothesis(sce),sce);
				//Util.printSolution(rep, _net);
			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// Removing the temp files
		hypoFile.delete();
//		cnfFile.delete();
		
		return result;
	}
	
	/**
	 * Inserts in the specified clause stream the constraints 
	 * that force the scenario to be within the specified hypothesis subspace.  
	 * 
	 * @param loader the variable loader if SAT variables need to be defined.  
	 * @param out the output stream where the constraints are to be stored.
	 * @param hss the hypothesis subspace that is being encoded.  
	 * */
	private void addHypothesisSubspace(VariableLoader loader, 
			ClauseStream out, 
			HypothesisSubspace<H> hss) {
		final VariableAssigner hypoAss;
		final SearchType st = hss.getSearchType();
		final HypothesisSubspaceType type = hss.getType();
		final H h = hss.getHypothesis();
		{
			VariableAssigner tmp = _hypoAssigners.get(hss);
			if (tmp == null) {
				tmp = _space.createVariables(h, loader, _allVariables, type, st);
				tmp.copy(_allVariables);
				_hypoAssigners.put(hss, tmp);
			}
			hypoAss = tmp;
		}
		_space.generateSAT(h, _litAss, hypoAss, out, type, st);
	}
	
	@Override 
	public void finalize() {
		clear();
	}
	
	public void clear() {
		_modAndObs.delete();
	}
}
