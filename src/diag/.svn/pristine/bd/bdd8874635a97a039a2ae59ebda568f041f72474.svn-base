package diag.reiter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
import edu.supercom.util.sat.BufferedPrintClauseStream;
import edu.supercom.util.sat.CNF;
import edu.supercom.util.sat.ClausePruner;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.MapVariableAssigner;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;

import util.AbstractScenario;
import util.AlarmLog;
import util.Scenario;

import lang.Network;
import lang.State;
import lang.YAMLDEvent;
import util.Time;

/**
 * The class in charge of doing the diagnosis.
 * */
public class Diag<H extends Hypothesis, C extends Conflict> {
	
	public static int NB_SAT_CALL = 0;
	public static long SAT_RUNTIME = 0;
	public static int HYPOTHESES_GENERATED = 0;
	
	/**
	 * The model of the network.  
	 * */
	private final Network _net;
	/**
	 * The initial state. 
	 * */
	private final State _st;
	/**
	 * The search space.  
	 * */
	 private final HypothesisSpace<H,C> _space;
	/**
	 * The list of faults.  
	 * */
	private final Set<YAMLDEvent> _faults;
	/**
	 * The observations.  
	 * */
	private final AlarmLog _obs;
	/**
	 * The list of diagnosis candidates.
	 * */
	private final List<Candidate<H>> _candidates;
	/**
	 * A list of temporary candidates (at this stage, it is unsure they are minimal)  
	 * */
	private final Deque<Candidate<H>> _tmpCandidates;
	/**
	 * The literal assigner for the model.  
	 * */
	private LiteralAssigner _litAss; 
	/**
	 * The CNF file encoding the model and the observations.  
	 * */
	private File _modAndObs;
	
	private final MapVariableAssigner _allVariables;
	
	public Diag(Network net, State st, HypothesisSpace<H,C> space, 
			Set<YAMLDEvent> faults, AlarmLog log) {
		_net = net;
		_faults = faults;
		_space = space;
		_obs = log;
		_st = st;
		_candidates = new ArrayList<Candidate<H>>();
		_tmpCandidates = new ArrayDeque<Candidate<H>>();
		_allVariables = new MapVariableAssigner();
	}

	/**
	 * Returns the list of (minimal) candidates found so far.  
	 * 
	 * @return the list of minimal diagnosis candidates stored in this instance.  
	 * */
	public List<Candidate<H>> diagnosisCandidates() {
		return Collections.unmodifiableList(_candidates);
	}
	
	/**
	 * Generates the SAT problem that encodes the search of a scenario on the model 
	 * and consistent with the observations.  
	 * 
	 * @param loader the variable loader used to allocate SAT variables.
	 * @param opt the list of options.  
	 * */
	private void generateModAndObsFile(VariableLoader loader, Options opt) {
		// Allocating the variables
		final int stepsPerObs;
		{
			final String nb = opt.getOption("stepsPerObs");
			if (nb == null) { // Default value
				stepsPerObs = 6;
			} else {
				stepsPerObs = Integer.parseInt(nb);
			}
		}
		final int nbTrans = _obs.nbEntries() * stepsPerObs;
		
		_litAss = new LiteralAssigner(loader, _net, nbTrans);

		if ("true".equals(opt.getOption("showLiterals"))) {
			System.out.println("== literal mapping ==");
			_litAss.print(System.out);
			System.out.println("== end mapping ==");
		}

		_modAndObs = Util.openTemporaryFile("mod", "obs",null);
		final String modAndObsCNFFile = _modAndObs.getPath();
		// Building the cnf file that encodes the model and the observations.
		try {
			final BufferedPrintClauseStream out1 = new BufferedPrintClauseStream(
					new PrintStream(new File(modAndObsCNFFile)));
			out1.setBufferSize(10000);
			final ClauseStream out = new ClausePruner(out1);
			final MMLDTranslator tl = new MMLDTranslator(_litAss._ios);
			tl.translateNetwork(out, _litAss, _net, nbTrans);
			tl.noSimultaneousOccurrence(_litAss, out, _faults, nbTrans);
			if ("true".equals(opt.getOption("encodeTime"))) {
				Time[] times = new Time[nbTrans];
				for (int i=0 ; i<nbTrans ; i++) {
					times[i] = null;
				}
				for (int i=0 ; i<_obs.nbEntries() ; i++) {
					times[((i+1)*stepsPerObs)-1] = _obs.get(i)._time;
				}
				tl.timedForcedTransitions(_litAss, out, _net, times, nbTrans);
			}
			tl.state(_litAss, out, _st);
			// final SpyClauseStream spyout = new SpyClauseStream(out,
			// System.out);
			if ("true".equals(opt.getOption("maxTimeLag"))) {
				diag.Diag.maxTimeLag(_net, _obs, stepsPerObs, out, _litAss, tl);
			}
			for (int i = 0; i < _obs.nbEntries(); i++) {
				tl.observationsInInterval(_litAss, out, _net.observableEvents(),
						_obs.get(i)._events, stepsPerObs * i, stepsPerObs);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//System.out.println("___ END GENERATION MOD AND OBS SAT");
	}
	
	/**
	 * Tests if the specified hypothesis is a diagnosis candidate for the diagnosis problem.  
	 * 
	 * @param loader the loader used to allocate variables if necessary.  
	 * @param h the hypothesis that is tested.  
	 * @param opt the list of options.  
	 * @return an extended diagnosis report, i.e., 
	 * either a scenario associated with <code>h</code> and consistent with the observations, 
	 * or a conflict for <code>h</code>.  
	 * */
	public ExtendedDiagnosisReport<C> testHypothesis(VariableLoader loader, H h, Options opt) {
		if (opt == null) {
			opt = new Options();
		}
		
		
		if (_litAss == null) {
			generateModAndObsFile(loader, opt);
		}
		final List<String> cnfFiles = new ArrayList<String>();
		cnfFiles.add(_modAndObs.getPath());
		
		final VariableAssigner hypoVarass = _space.createVariables(h, loader, _allVariables, 
				HypothesisSubspaceType.HYPO, SearchType.CONFLICT);

		// Generating the additional clauses for the hypothesis
		List<Integer> ass = null;
		final File hypoFile = Util.openTemporaryFile("hypo","hypo", null);
		try {
			cnfFiles.add(hypoFile.getPath());
			
			final ClauseStream out = new BufferedPrintClauseStream(
					new PrintStream(new File(hypoFile.getPath())));
			//final ClauseStream other = new SpyClauseStream(out);
			ass = _space.generateSAT(h, _litAss, hypoVarass, out,  
					HypothesisSubspaceType.HYPO, SearchType.CONFLICT);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Generating the assumptions file
		final File assFile = Util.openTemporaryFile("hypo","assum", null);
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(assFile.getPath()));
			for (final int lit: ass) {
				writer.write(Integer.toString(lit));
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Solving the SAT problem.
		ExtendedDiagnosisReport<C> result = null;
		File cnfFile = null;
		try { 
			if ("true".equals(opt.getOption("keepCNFFiles"))) {
				cnfFile = new File("DIAG_HYPO");
			} else {
				cnfFile = Util.openTemporaryFile("cnf", "cnf", null);
			}
			final String[] fileArray = new String[cnfFiles.size()];
			cnfFiles.toArray(fileArray);
			CNF.simplerFinalizeCNF("Satisfiable if the obs match the model",
					fileArray, cnfFile.getPath());
			
			
			final List<Boolean> sol = new ArrayList<Boolean>();
			final long bef = System.currentTimeMillis();
			final List<Integer> con = CNF.solveWithAssumption("./lib/solvers/mynisat", 
					cnfFile.getPath(), assFile.getPath(), sol);
			NB_SAT_CALL++;
			SAT_RUNTIME += System.currentTimeMillis() - bef;

			// Allocating the variables
			final int stepsPerObs;
			{
				final String nb = opt.getOption("stepsPerObs");
				if (nb == null) { // Default value
					stepsPerObs = 6;
				} else {
					stepsPerObs = Integer.parseInt(nb);
				}
			}
			final int nbTrans = _obs.nbEntries() * stepsPerObs;
			if (con == null) {
				final DiagnosisReport rep = new DiagnosisReport(_net, nbTrans, _litAss, sol);
				result = new ExtendedDiagnosisReport<C>(rep);
			} else {
//				final Conflict c = buildConflict(con, _faults, h, nbTrans, hypoVarass);
				final C c = _space.buildConflict(h, con, nbTrans, hypoVarass);
				result = new ExtendedDiagnosisReport<C>(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Removing the temp files
		assFile.delete();
		hypoFile.delete();
		cnfFile.delete();
		
		return result;
	}
	
	/**
	 * Indicates whether the specified hypothesis 
	 * should not be put in the stack of hypotheses to test.  
	 * 
	 * @param h the hypothesis.  
	 * @return <code>true</code> if some other hypothesis <code>h'</code> exists 
	 * s.t. <code>h</code> is a subhypothesis of <code>h'</code> 
	 * and <code>h'</code> was proved a candidate.  
	 * */
	private boolean isSubsumed(H h) {
		for (final Candidate<H> other: _candidates) {
			if (_space.isSubHypothesis(h,other.getHypothesis())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Indicates whether there exist hypotheses compatible with the observations 
	 * that are not subsumed by the current set of candidates.  
	 * 
	 * @param loader the variable loader.  
	 * @param hypoAssigners the variable assigner associated with each candidate 
	 * (generated by {@link 
	 * Hypothesis#generatePrecSAT(VariableAssigner, VariableAssigner, ClauseStream, int)}).
	 * @param opt the options.    
	 * @return <code>false</code> if there are still solutions to find.  
	 * */
	public Candidate<H> newExistMoreSolutions(VariableLoader loader, 
			Map<HypothesisSubspace<H>, VariableAssigner> hypoAssigners, Options opt) {
		final List<HypothesisSubspace<H>> subspaces = 
			new ArrayList<HypothesisSubspace<H>>();
		for (final Candidate<H> cand: _candidates) {
			subspaces.add(new HypothesisSubspace<H>(
					cand.getHypothesis(),HypothesisSubspaceType.SUB,SearchType.EXCLUDE));
		}
		for (final Candidate<H> cand: _tmpCandidates) {
			subspaces.add(new HypothesisSubspace<H>(
					cand.getHypothesis(),HypothesisSubspaceType.SUB,SearchType.EXCLUDE));
		}
		
		return newSearchHypothesis(loader, hypoAssigners, opt, subspaces);
	}
	
	/**
	 * Searches for a hypothesis compatible with the observations, 
	 * not subsumed by the current set of candidates, 
	 * but subsumed by the specified hypothesis.  
	 * 
	 * @param loader the variable loader.  
	 * @param hypoAssigners the variable assigner associated with each candidate 
	 * (generated by {@link 
	 * Hypothesis#createVariables(VariableLoader, int, VariableAssigner, HypothesisSubspaceType, SearchType)}. 
	 * @param opt the options.
	 * @param currentHypo the hypothesis that is tested.      
	 * @return a hypothesis that is subsumed by <code>currentHypo</code> 
	 * and by none of the hypotheses found so far.  
	 * */
	public Candidate<H> newSearchSolutionsFromHypo(VariableLoader loader, 
			Map<HypothesisSubspace<H>, VariableAssigner> hypoAssigners, Options opt, 
			H currentHypo) {
		final List<HypothesisSubspace<H>> subspaces = 
			new ArrayList<HypothesisSubspace<H>>();
		if (currentHypo != null) {
			subspaces.add(new HypothesisSubspace<H>(
					currentHypo, HypothesisSubspaceType.SUB, SearchType.INCLUDE));
		}
		for (final Candidate<H> cand: _candidates) {
			subspaces.add(new HypothesisSubspace<H>(
					cand.getHypothesis(),HypothesisSubspaceType.SUB,SearchType.EXCLUDE));
		}
		for (final Candidate<H> cand: _tmpCandidates) {
			subspaces.add(new HypothesisSubspace<H>(
					cand.getHypothesis(),HypothesisSubspaceType.SUB,SearchType.EXCLUDE));
		}
		return newSearchHypothesis(loader, hypoAssigners, opt, subspaces);
	}
	
	/**
	 * Inserts in the specified clause stream the constraints 
	 * that force the scenario to be within the specified hypothesis subspace.  
	 * 
	 * @param loader the variable loader if SAT variables need to be defined.  
	 * @param out the output stream where the constraints are to be stored.  
	 * @param hypoAssigners a map that contains the list of variables 
	 * assigned for each hypothesis subspace.  
	 * @param hss the hypothesis subspace that is being encoded.  
	 * @param nbTrans the number of transitions.  
	 * */
	private void addHypothesisSubspace(VariableLoader loader, 
			ClauseStream out, 
			Map<HypothesisSubspace<H>, VariableAssigner> hypoAssigners, 
			HypothesisSubspace<H> hss, int nbTrans) {
		final VariableAssigner hypoAss;
		final SearchType st = hss.getSearchType();
		final HypothesisSubspaceType type = hss.getType();
		final H h = hss.getHypothesis();
		{
			VariableAssigner tmp = hypoAssigners.get(hss);
			if (tmp == null) {
				tmp = _space.createVariables(h, loader, _allVariables, type, st);
				tmp.copy(_allVariables);
				hypoAssigners.put(hss, tmp);
			}
			hypoAss = tmp;
		}
		_space.generateSAT(h, _litAss, hypoAss, out, type, st);
	}
	
	public Candidate<H> newSearchHypothesis(VariableLoader loader,
			Map<HypothesisSubspace<H>, VariableAssigner> hypoAssigners, Options opt, 
			Collection<HypothesisSubspace<H>> subspaces) {
		if (opt == null) {
			opt = new Options();
		}
		
		// Allocating the variables
		final int stepsPerObs;
		{
			final String nb = opt.getOption("stepsPerObs");
			if (nb == null) { // Default value
				stepsPerObs = 6;
			} else {
				stepsPerObs = Integer.parseInt(nb);
			}
		}
		final int nbTrans = _obs.nbEntries() * stepsPerObs;
		
		if (_litAss == null) { 
			generateModAndObsFile(loader, opt);
		}
		final List<String> cnfFiles = new ArrayList<String>();
		cnfFiles.add(_modAndObs.getPath());

		final File hypoFile = Util.openTemporaryFile("hypo","hypo", null);
		cnfFiles.add(hypoFile.getPath());
		try {
			final ClauseStream out = new BufferedPrintClauseStream(
					new PrintStream(hypoFile));
			for (final HypothesisSubspace<H> hss: subspaces) {
				addHypothesisSubspace(loader, out, hypoAssigners, hss, nbTrans);
			}
			

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		final File cnfFile = Util.openTemporaryFile("cnf", "cnf", null);
		//final File cnfFile = new File("/tmp/cnffile");
		// Solving the SAT problem.
		Candidate<H> result = null;
		try { 
			final String[] fileArray = new String[cnfFiles.size()];
			cnfFiles.toArray(fileArray);
			CNF.simplerFinalizeCNF("Satisfiable if the obs match the model",
					fileArray, cnfFile.getPath());
			
			
			//final List<Boolean> sol = CNF.solve("./lib/solvers/picosat", cnfFile.getPath());
			final long bef = System.currentTimeMillis();
			final List<Boolean> sol = CNF.solve("./minisat_core", cnfFile.getPath());
			NB_SAT_CALL++;
			SAT_RUNTIME += System.currentTimeMillis() - bef;
			
			if (sol != null) {
				final DiagnosisReport rep = new DiagnosisReport(_net, nbTrans, _litAss, sol);
				Scenario sce = rep.getScenario();
				if (!"true".equals(opt.getOption("encodeTime"))) {
					sce = AbstractScenario.simplify(sce);
				} else {
					// Nothing, unfortunately
					// TODO: Is it possible to improve that?
				}
				result = new Candidate<H>(_space.getHypothesis(sce),sce);
				//Util.printSolution(rep, _net);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Removing the temp files
		hypoFile.delete();
		cnfFile.delete();
		
		return result;
	}
	
	
	
	/**
	 * Applies the specified conflict to the specified hypothesis 
	 * and store them in the specified list of hypotheses.
	 * 
	 * @param h the hypothesis on which the conflict is applied.  
	 * @param con the conflict that is applied.  
	 * @param hypos the list of hypotheses where the new hypotheses will be stored.  
	 * @param generated the list of hypotheses already generated.   
	 * */
	public void applyConflict(H h, C con, 
			Collection<H> hypos, Collection<H> generated) {
		final List<H> newHypos = new ArrayList<H>();
		{
			final List<? extends H> tmpHypos = //h.createSubHypotheses(con);
				_space.createSubHypotheses(h, con);
			for (final H newHypo: tmpHypos) {
				if (isSubsumed(newHypo)) {
					continue;
				}
				if (generated.add(newHypo)) {
					newHypos.add(newHypo);
				}
			}
		}
		hypos.addAll(newHypos);
		HYPOTHESES_GENERATED += newHypos.size();
		System.out.println("Adding: ");
		for (final H nh: newHypos) {
			System.out.println("\t" + nh);
		}
		System.out.println("Current stack (" + hypos.size() + " hypotheses): ");
		for (final H nh: hypos) {
			System.out.println("\t" + nh);
		}
		System.out.println("Generated so far: " + HYPOTHESES_GENERATED);
		System.out.println("Number of SAT problems: " + NB_SAT_CALL);
	}
	
	public void solve(Options opt) {
		final VariableLoader loader = new VariableLoader();
		final List<H> hypos = new LinkedList<H>();
		final Collection<H> generated = new HashSet<H>();
		final Map<HypothesisSubspace<H>, VariableAssigner> newHypoAssigners = 
			new HashMap<HypothesisSubspace<H>, VariableAssigner>();
		final List<C> cons = new ArrayList<C>();
		_candidates.clear();
		{
			final Set<? extends H> initialHypos = _space.minimalHypotheses();
			hypos.addAll(initialHypos);
			generated.addAll(initialHypos);
			HYPOTHESES_GENERATED++;
		}
		
		while (!hypos.isEmpty()) {
			final H h = hypos.remove(0);
			Candidate<H> cand = null;
			boolean isMinimalSolution = false;
			System.out.println();
			System.out.println("Trying to solve " + h);

			{ // Testing whether h is a sub hypothesis of a solution.
				boolean isSub = false;
				for (final Candidate<H> other: _candidates) {
					if (_space.isSubHypothesis(h,other.getHypothesis())) {
						System.out.println("000 is sub of " + other);
						isSub = true;
						break;
					}
				}
				if (!isSub) {
					for (final Candidate<H> other: _tmpCandidates) {
						if (_space.isSubHypothesis(h,other.getHypothesis())) {
							System.out.println("000 is sub of " + other);
							isSub = true;
							break;
						}
					}
				}
				if (isSub) {
					System.out.println("Hypothesis droped.");
					continue;
				}
			}
			
			{ // Searching if it is worth digging from h.  
				final Candidate<H> subCand = newSearchSolutionsFromHypo(loader, newHypoAssigners, opt, h);
				if (subCand == null) {
					System.out.println("No more solutions from " + h);
					continue;
				} else {
					System.out.println("There is more: " + subCand.getHypothesis());
					if (subCand.getHypothesis().equals(h)) {
						cand = subCand;
						isMinimalSolution = true;
					} else {
						addTemporaryCandidate(subCand);
					}
				}
			}
			
			if (!isMinimalSolution) { 
				// Looking if there exists a conflict that does not hit this hypothesis.
				C con = null;
				for (final C c: cons) {
					if (_space.hits(h,c)) {
						continue;
					}
					con = c;
					break;
				}
				
				if (con != null) {
					System.out.println("Does not hit: " + con);
					applyConflict(h, con, hypos, generated);
					continue;
				}
			}
			
			if (!isMinimalSolution) {
				final ExtendedDiagnosisReport<C> xrep = testHypothesis(loader, h, opt);
				final DiagnosisReport rep = xrep.getReport();
				if (rep == null) {
					final C con = xrep.getConflict();
					cons.add(con);
					System.out.println("Not a candidate: " + con);
					applyConflict(h, con, hypos, generated);
				} else {
					cand = new Candidate<H>(h, rep.getScenario());
					isMinimalSolution = true;
				}
			}
			
			if (isMinimalSolution) {
				addMinimalCandidate(cand);
				{
					final Candidate<H> more = newExistMoreSolutions(loader, newHypoAssigners, opt);
					if (more == null) {
						break;
					}
					System.out.println("There is more out there; e.g. " + more.getHypothesis());
				}
			}
		} // while
		
		_modAndObs.delete();
		_modAndObs = null;
		_litAss = null;
		
		// Deals with the tmpCandidates
		_candidates.addAll(_tmpCandidates);
	}
	
	public void showCandidates() {
		System.out.println("Current list of candidates: ");
		for (final Candidate<H> cand: _candidates) {
			System.out.println("CANDIDATE >> " + cand.getHypothesis());
		}
		for (final Candidate<H> cand: _tmpCandidates) {
			System.out.println("TMPCANDIDATE >> " + cand.getHypothesis());
		}
	}
	
	public void bottomUp(Options opt) {
		final VariableLoader loader = new VariableLoader();
		final Map<HypothesisSubspace<H>, VariableAssigner> hypoAssigners = 
			new HashMap<HypothesisSubspace<H>, VariableAssigner>();
		_candidates.clear();
		
		for(;;) {
			System.out.println();
			System.out.println("Searching for new hypotheses.");
			final Candidate<H> more = newExistMoreSolutions(loader, hypoAssigners, opt);
			if (more == null) {
				break;
			}
			
			final Iterator<Candidate<H>> candIt = _candidates.iterator(); 
			while (candIt.hasNext()) {
				final Candidate<H> old = candIt.next();
				if (_space.isSubHypothesis(old.getHypothesis(),more.getHypothesis())) {
					candIt.remove();
				}
			}
			System.out.println("Generated hypotheses: " + more);
			_candidates.add(more);
			showCandidates();
		}
	}
	
	/**
	 * Adds the specified hypothesis to the list of candidates of this diagnosis problem.  
	 * 
	 * @param c the candidate that is being added.  
	 * */
	public void addMinimalCandidate(Candidate<H> h) {
		System.out.println("That's a candidate!");
		_candidates.add(h);
		final Iterator<Candidate<H>> candIt = _tmpCandidates.iterator(); 
		while (candIt.hasNext()) {
			final Candidate<H> other = candIt.next();
			if (_space.isSubHypothesis(other.getHypothesis(),h.getHypothesis())) {
				candIt.remove();
				System.out.println("Removing candidate " + other.getHypothesis());
			}
		}
		showCandidates();
	}
	
	/**
	 * Adds a temporary candidate.  A temporary candidate is a candidate 
	 * that has not been proved to be minimal yet.  
	 * 
	 * @param c the candidate that is to be added.  
	 * */
	public void addTemporaryCandidate(Candidate<H> c) {
		final Iterator<Candidate<H>> candIt = _tmpCandidates.iterator(); 
		while (candIt.hasNext()) {
			final Candidate<H> other = candIt.next();
			if (_space.isSubHypothesis(other.getHypothesis(),c.getHypothesis())) {
				candIt.remove();
				System.out.println("Removing candidate " + other);
			} else if (_space.isSubHypothesis(c.getHypothesis(),other.getHypothesis())) {
				return;
			}
		}
		_tmpCandidates.add(c);
		showCandidates();
	}
}
