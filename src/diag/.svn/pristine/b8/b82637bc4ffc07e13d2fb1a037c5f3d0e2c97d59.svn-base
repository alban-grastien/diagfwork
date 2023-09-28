package diag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.supercom.util.Options;
import edu.supercom.util.sat.BailleuxEncoding;
import edu.supercom.util.sat.BufferedPrintClauseStream;
import edu.supercom.util.sat.CNF;
import edu.supercom.util.sat.ClausePruner;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.Totalizer;
import edu.supercom.util.sat.TotalizerEncoding;
import edu.supercom.util.sat.TotalizerFactory;
import edu.supercom.util.sat.VariableLoader;

import util.AlarmLog;
import lang.Network;
import lang.State;
import lang.YAMLDEvent;

import lang.YAMLDComponent;
import lang.MMLDTransition;
import lang.YAMLDFormula;
import lang.YAMLDTrue;
import lang.YAMLDFalse;
import lang.YAMLDEqFormula;
import lang.YAMLDExpr;
import lang.YAMLDValue;
import lang.YAMLDGenericVar;
import lang.YAMLDID;
import lang.VariableValue;
import lang.YAMLDNotFormula;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableSemantics;
import lang.Period;

/**
 * A diagnoser.
 * */
public class Diag {

	// total time running sat solver, in nanosecs
	public static long time_in_sat_calls = 0;

	/**
	 * Add maximum time lag constraints for "forced" transitions to the
	 * encoding. (This is implemented here, rather than in the MMLDTranslator,
	 * because it depends on how observations are mapped.) The following model
	 * is assumed:
	 * 
	 * Let N be the length of the alarm log, i.e., the number of time points
	 * that are mentioned in it. The sat encoding consists of N consecutive
	 * "blocks", each of stepsPerObs time steps. The time associated with a
	 * block is assumed to hold at the first step in the corresponding block.
	 * So, for example, if we have a log like
	 * 
	 * time = 0.0 time = 2.0 time = 7.0
	 * 
	 * and stepsPerObs = 3, the sat encoding steps will be
	 * 
	 * s0: time(s0) = 0.0 s1 & s2: 0.0 <= time(s1) <= time(s2) <= 2.0 s3:
	 * time(s3) = 2.0 s3 & s4: 2.0 <= time(s3) <= time(s4) <= 7.0 s6: time(s6) =
	 * 7.0 s7 & s8: 7.0 <= time(s7) <= time(s8) <= +inf
	 * 
	 * Let the alarm log time points be t0, ..., t[N-1], and let step(ti) be the
	 * first sat enoding-step that belongs to the i:th block. So, in the example
	 * above, step(t0) = s0, step(t1) = s3, etc. In general, step(ti) =
	 * i*stepsPerObs (assuming zero-indexing of time points and steps).
	 * 
	 * Suppose we have a time triggered transition, of the form
	 * 
	 * transition T ...; triggeredby [0,X] phi;
	 * 
	 * We now want to enforce the max time constraint, i.e., that phi cannot
	 * remain true for X time units or more without an occurrence of T.
	 * 
	 * Let ti and tj be two time points in the alarm log, where tj comes after
	 * ti, such that (time(tj) - time(ti) > X, and for no tk that lies between
	 * ti and tj do we have (time(tk) - time(ti) > X. If phi holds at step(ti),
	 * and phi holds at every step up to and including step(tj), and there is no
	 * occurrence of T at any of the steps in that interval preceeding step(tj),
	 * then we have a violation of the above condition. Therefore, we must have
	 * the constraint
	 * 
	 * phi@step(ti) -> (-phi@step(ti)+1 \/ .. \/ -phi@step(tj) \/ T@step(ti)+1
	 * \/ .. \/ T@step(tj)-1
	 * 
	 * i.e., that at some step in the interval, either phi is not true or T
	 * takes place. Enforcing this condition, for all pairs ti,tj as specified
	 * above, is necessary to avoid violation of max time constraints. Is it
	 * also sufficient? Yes, as long as the min time of every triggering
	 * conditions interval is zero, because if that is the case, there is
	 * nothing that constrains the assignment of actual times to the
	 * intermediate steps other than the observation times themselves: hence, as
	 * long as T takes place at some step before step(tj), we can schedule those
	 * steps as early as required to satisfy the max time constraint.
	 */
	public static void maxTimeLag(Network net, AlarmLog log, int stepsPerObs,
			ClauseStream out, LiteralAssigner varass, MMLDTranslator tl) {
		// int N = log.nbEntries();
		for (final YAMLDComponent comp : net.getComponents()) {
			// System.out.println(comp.toFormattedString());
			for (final MMLDTransition trans : comp.transitions()) {
				for (final YAMLDFormula prec : trans.getPreconditions()) {
					// System.out.println(prec.toFormattedString());
					Period ub = trans.getConditionTime(prec).getEnd();
					maxTimeLag(comp, trans, prec, ub, net, log, stepsPerObs,
							out, varass, tl);
				}
			}
		}
	}

	public static void maxTimeLag(YAMLDComponent comp, MMLDTransition trans,
			YAMLDFormula prec, Period ub, Network net, AlarmLog log,
			int stepsPerObs, ClauseStream out, LiteralAssigner varass,
			MMLDTranslator tl) {
		for (int ti = 0; ti + 1 < log.nbEntries(); ti++) {
			int tj = ti + 1;
            // TODO: Make the following condition clearer
			while ((tj < log.nbEntries())
					&& !new Period(log.get(ti)._time, log.get(tj)._time).isLonger(ub)) {
//					&& ((log.get(tj)._time - log.get(ti)._time) <= ub))
				tj += 1;
            }
			if (tj < log.nbEntries()) {
				final List<Integer> clause = new ArrayList<Integer>();
				// add -prec@s for s = step(ti) .. step(tj)
				for (int s = (ti * stepsPerObs); s <= (tj * stepsPerObs); s++) {
					// final VariableSemantics sem = new FormulaHolds(prec,
					// comp, s);
					// final int fLit = varass.surelyGetVariable(sem);
					final int fLit = formulaHoldsAt(prec, comp, s, net, varass,
							out);
					clause.add(-fLit);
				}
				// add T@s for s = step(ti) .. step(tj)-1
				for (int s = (ti * stepsPerObs); s < (tj * stepsPerObs); s++) {
					final VariableSemantics sem = new MMLDTransitionTrigger(
							trans, s);
					final int transLit = varass.surelyGetVariable(sem);
					clause.add(transLit);
					// System.out.println(clause.toString());
				}
				out.put(clause);
			}
		}
	}

	/**
	 * Get a proposition that represents formula f in component c being true at
	 * the given time. At the moment, this is a very simplified, incomplete
	 * implementation. It also doesn't have a buffer, but that doesn't matter
	 * because it never introduces new variables.
	 */
	private static int formulaHoldsAt(YAMLDFormula f, YAMLDComponent c,
			int time, Network net, VariableAssigner varass, ClauseStream out) {
		if (f instanceof YAMLDTrue) {
			final FormulaHolds fh = new FormulaHolds(f, null, 0);
			final int result = varass.surelyGetVariable(fh);
			out.put(result);
			return result;
		}

		if (f instanceof YAMLDFalse) {
			final FormulaHolds fh = new FormulaHolds(f, null, 0);
			final int result = varass.surelyGetVariable(fh);
			out.put(-result);
			return result;
		}

		// Assuming the equality is defined like this: var = val
		if (f instanceof YAMLDEqFormula) {
			final YAMLDEqFormula eq = (YAMLDEqFormula) f;
			final YAMLDExpr expr1 = eq.expr1();
			final YAMLDExpr expr2 = eq.expr2();
			final YAMLDValue val = (YAMLDValue) expr2;

			if (expr1 instanceof VariableValue) {
				final YAMLDGenericVar var = ((VariableValue) expr1).variable();
				return varass.surelyGetVariable(new Assignment(var, val, time));
			}

			if (expr1 instanceof YAMLDID) {
				final YAMLDGenericVar var = ((YAMLDID) expr1).variable(c, net);
				return varass.surelyGetVariable(new Assignment(var, val, time));
			}

			throw new IllegalArgumentException("Cannot deal with expression "
					+ expr1.toFormattedString());
		}

		if (f instanceof YAMLDNotFormula) {
			final YAMLDFormula neg = ((YAMLDNotFormula) f).getOp();
			return -formulaHoldsAt(neg, c, time, net, varass, out);
		}

		throw new UnsupportedOperationException(
				"YAMLDFormula type not supported: " + f.getClass().getName());
	}

	/**
	 * Runs a diagnosis on the specified network for the specified set of
	 * alarms, from the specified state, trying to minimise the number of
	 * occurrences of events of the specified set. The list of options used in
	 * this method are:
	 * <ul>
	 * <li><b>stepsPerObs</b>: should be an integer; the default value is
	 * <code>6</code>;</li>
	 * <li><b>showLiterals</b>: if equals to <code>"true"</code>, prints out the
	 * list of literals generated for the encoding of the model;</li>
	 * <li><b>maxTimeLog</b>: if equals to <code>"true"</code>, uses P@trik's
	 * method for enforcing the maximum time a precondition may be satisfied.</li>
	 * <li><b>keepCNFFiles</b>: if equals to <code>"true"</code>, indicates that
	 * the CNF files evaluated by the SAT solver should be kept. The names of
	 * the files are "MOD" and "DIAGx" where "x" is the number of faults.</li>
	 * <li><b>solver</b>: specifies the SAT solver that should be used.  
	 * Default is <code>./minisat_core</code>.</li>  
	 * </ul>
	 * 
	 * @param net
	 *            the network.
	 * @param log
	 *            the logs that need to be explained.
	 * @param faults
	 *            the list of events that we want to minimise in the
	 *            explanation.
	 * @param state
	 *            the initial state of the network. // TODO: should be replaced
	 *            by a partial state
	 * @param opt
	 *            the list of options (possibly <code>null</code>).
	 * */
	public static DiagnosisReport diag(Network net, AlarmLog log,
			Collection<YAMLDEvent> faults, State state, Options opt) {
		if (opt == null) {
			opt = new Options();
		}
		final String solver;
        {
            final String tmp = opt.getOption(false, false, "solver","satsolver");
            if (tmp == null) {
                solver = "./lib/run-minisat.sh";
            } else {
                solver = tmp;
            }
        }
		
		// Allocating the variables
		final VariableLoader loader = new VariableLoader();
		final int stepsPerObs;
		{
			final String nb = opt.getOption("stepsPerObs");
			if (nb == null) { // Default value
				stepsPerObs = 6;
			} else {
				stepsPerObs = Integer.parseInt(nb);
			}
		}
		final int nbTrans = log.nbEntries() * stepsPerObs;
		LiteralAssigner litAss = new LiteralAssigner(loader, net, nbTrans);

		if ("true".equals(opt.getOption("showLiterals"))) {
			System.out.println("== literal mapping ==");
			litAss.print(System.out);
			System.out.println("== end mapping ==");
		}

		final String modAndObsCNFFile;
		if ("true".equals(opt.getOption("keepCNFFiles"))) {
		  modAndObsCNFFile = "MOD";
		} else {
		  modAndObsCNFFile = Util.openTemporaryFile("mod","obs", null).getPath();
		}

		// final String modAndObsCNFFile = Util.openTemporaryFile("mod", "obs", null).getPath();
		// Building the cnf file that encodes the model and the observations.
		try {
			final BufferedPrintClauseStream out1 = new BufferedPrintClauseStream(
					new PrintStream(new File(modAndObsCNFFile)));
			out1.setBufferSize(10000);
			final ClauseStream out = new ClausePruner(out1);
			final MMLDTranslator tl = new MMLDTranslator(litAss._ios);
			tl.translateNetwork(out, litAss, net, nbTrans);
			tl.state(litAss, out, state);
			// final SpyClauseStream spyout = new SpyClauseStream(out,
			// System.out);
			if ("true".equals(opt.getOption("maxTimeLag"))) {
				maxTimeLag(net, log, stepsPerObs, out, litAss, tl);
			}
			for (int i = 0; i < log.nbEntries(); i++) {
				tl.observationsInInterval(litAss, out, net.observableEvents(),
						log.get(i)._events, stepsPerObs * i, stepsPerObs);
				// final Collection<YAMLDEvent> localObs = new
				// ArrayList<YAMLDEvent>();
				// for (int k = 0 ; k<nbUnobs ; k++) {
				// tl.observationAtTime(litAss, out, net.observableEvents(),
				// localObs, ((nbUnobs+1) * i)+k);
				// }
				// localObs.addAll(log.get(i)._events);
				// tl.observationAtTime(litAss, out, net.observableEvents(),
				// localObs, ((nbUnobs+1) * i) + nbUnobs);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Trying to find a solution.");
		// Trying to determine if there is a solution
		{
			// final String formattedCNFFile;
			// if ("true".equals(opt.getOption("keepCNFFiles"))) {
			// 	formattedCNFFile = "MOD_CNF";
			// } else {
			// 	formattedCNFFile = Util.openTemporaryFile("mod","cnf", null).getPath();
			// }
			final String[] fileArray = new String[1];
			fileArray[0] = modAndObsCNFFile;
			// try {
			// 	CNF.simplerFinalizeCNF(
			// 		"Satisfiable if the obs match the model", fileArray,
			// 		formattedCNFFile);
			// } catch (IOException e) {
			// 	e.printStackTrace();
			// }
			long s = System.nanoTime();
			//List<Boolean> sol = CNF.solve(solver, formattedCNFFile);
			List<Boolean> sol = CNF.solve2(solver, fileArray);
			long f = System.nanoTime();
			time_in_sat_calls += (f - s);
			if (sol == null) {
				System.out.println("No solution was found.");
				return null;
			} else {
			  // Util.printSolution(sol, net, nbTrans, litAss);
			}
		}

		System.out.println("A solution was found.");
		// Creates the object that encodes the cardinality constraints
		Totalizer tot;
		{
			final TotalizerEncoding encoding = new BailleuxEncoding();
			final List<Totalizer> totalizers = new ArrayList<Totalizer>();
			for (final YAMLDEvent event : faults) {
				final List<EventOccurrence> sems = new ArrayList<EventOccurrence>();
				for (int i = 0; i < nbTrans; i++) {
					final EventOccurrence sem = new EventOccurrence(event, i);
					sems.add(sem);
				}
				final Totalizer localTot = TotalizerFactory
						.createBalancedTotalizer(sems, litAss, encoding);
				totalizers.add(localTot);
			}
			tot = TotalizerFactory.createBalancedTotalizer(totalizers, litAss,
					encoding);
		}

		int nbFaults = -1;
		final List<String> cnfFileList = new ArrayList<String>();

		cnfFileList.add(modAndObsCNFFile);
		// Searching a scenario with the min number of faults.
		List<Boolean> sol = null;
		while (sol == null) {
			nbFaults++;
			System.out.println("Searching with " + nbFaults + " faults.");
			try {
			        final String fileName;
				if ("true".equals(opt.getOption("keepCNFFiles"))) {
					fileName = "TOT" + nbFaults;
				} else {
				        fileName = Util.openTemporaryFile("tot",
						"" + nbFaults, null).getPath();
				}
				cnfFileList.add(fileName);
				final ClauseStream out = new BufferedPrintClauseStream(
						new PrintStream(new File(fileName)));
				tot.upgrade(loader, out);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			final List<String> cnfFileListForNbFaults = new ArrayList<String>(
					cnfFileList);
			try {
			        final String fileName;
				if ("true".equals(opt.getOption("keepCNFFiles"))) {
					fileName = "LT" + nbFaults;
				} else {
				        fileName = Util.openTemporaryFile("tot",
						"less-than-" + nbFaults, null).getPath();
				}
				cnfFileListForNbFaults.add(fileName);
				final ClauseStream out = new BufferedPrintClauseStream(
						new PrintStream(new File(fileName)));
				tot.less(nbFaults + 1, out);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			//try { // Trying to find ONE solution
				// final String formattedCNFFile;
				// if ("true".equals(opt.getOption("keepCNFFiles"))) {
				// 	formattedCNFFile = "DIAG" + nbFaults;
				// } else {
				// 	formattedCNFFile = Util.openTemporaryFile("cnf","cnf", null).getPath();
				// }
				final String[] fileArray = new String[cnfFileListForNbFaults
						.size()];
				cnfFileListForNbFaults.toArray(fileArray);
				// CNF.simplerFinalizeCNF(
				// 		"Satisfiable if the obs match the model", fileArray,
				// 		formattedCNFFile);
				long s = System.nanoTime();
				sol = CNF.solve2(solver, fileArray);
				// sol = CNF.solve("./minisat_core", formattedCNFFile);
				long f = System.nanoTime();
				time_in_sat_calls += (f - s);
				if (CNF.solverErrorFlag) {
				  System.out.println("Error running solver.");
				  return null;
				}
			// } catch (IOException e) {
			// 	e.printStackTrace();
			// }
		}

		return new DiagnosisReport(net, nbTrans, litAss, sol);
	}

	public static Collection<YAMLDEvent> diagnose(Network net, AlarmLog log,
			Collection<YAMLDEvent> faults, State state, Options opt) {
		final DiagnosisReport sol = diag(net, log, faults, state, opt);
		// Util.printSolution(sol, net);
		return Util.extractEvents(sol, net, faults);
	}
}
