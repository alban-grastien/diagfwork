package diag.reiter.solvers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import diag.DiagnosisReport;
import diag.reiter.Candidate;
import diag.reiter.Conflict;
import diag.reiter.Diagnosis;
import diag.reiter.DiagnosisProblem;
import diag.reiter.ExtendedDiagnosisReport;
import diag.reiter.Hypothesis;
import diag.reiter.HypothesisSpace;
import diag.reiter.HypothesisSubspace;
import diag.reiter.Solver;
import diag.reiter.HypothesisSubspace.HypothesisSubspaceType;
import diag.reiter.HypothesisSubspace.SearchType;

import lang.Network;
import lang.State;
import lang.YAMLDEvent;
import util.AlarmLog;
import edu.supercom.util.Options;
import java.io.IOException;

public class TopDown<H extends Hypothesis,C extends Conflict> 
implements Solver<H,C> {

	public int HYPOTHESES_GENERATED = 0;
	
	private final Set<Candidate<H>> _candidates = new HashSet<Candidate<H>>();
	
	private final Set<Candidate<H>> _tmpCandidates = new HashSet<Candidate<H>>();

	private HypothesisSpace<H,C> _space;
	
	private DiagnosisProblem<H,C> _prob;
	
	private final List<H> _open = new LinkedList<H>();
	
	private final Set<H> _generated = new HashSet<H>();
	
	public synchronized Diagnosis<H> solve(Options opt,
			Network net, 
			State st,  
			AlarmLog log, 
			HypothesisSpace<H,C> space,
			Set<YAMLDEvent> faults) {
		_space = space;
		_prob = new DiagnosisProblem<H, C>(opt, net, st, log, space, faults);
		
		_candidates.clear();
		_tmpCandidates.clear();
		_open.clear();
		_generated.clear();
		
		final List<C> cons = new ArrayList<C>();
		
		{
			final Set<? extends H> initialHypos = space.minimalHypotheses();
			_open.addAll(initialHypos);
			_generated.addAll(initialHypos);
			HYPOTHESES_GENERATED += _open.size();
		}
		
		while (!_open.isEmpty()) {
			final H h = _open.remove(0);
			Candidate<H> cand = null;
			boolean isMinimalSolution = false;
			System.out.println();
			System.out.println("Trying to solve " + h);

			{ // Testing whether h is a sub hypothesis of a solution.
				boolean isSub = false;
				for (final Candidate<H> other: _candidates) {
					if (space.isSubHypothesis(h,other.getHypothesis())) {
						System.out.println("000 is sub of " + other);
						isSub = true;
						break;
					}
				}
				if (!isSub) {
					for (final Candidate<H> other: _tmpCandidates) {
						if (space.isSubHypothesis(h,other.getHypothesis())) {
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
				final Candidate<H> subCand = digFrom(h);
					//newSearchSolutionsFromHypo(loader, newHypoAssigners, opt, h);
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
					if (space.hits(h,c)) {
						continue;
					}
					con = c;
					break;
				}
				
				if (con != null) {
					System.out.println("Does not hit: " + con);
					applyConflict(h, con);
					continue;
				}
			}
			
			if (!isMinimalSolution) {
                try {
                    final ExtendedDiagnosisReport<C> xrep = _prob.testHypothesis(h);
                    final DiagnosisReport rep = xrep.getReport();
                    if (rep == null) {
                        final C con = xrep.getConflict();
                        cons.add(con);
                        System.out.println("Not a candidate: " + con);
                        applyConflict(h, con);
                    } else {
                        cand = new Candidate<H>(h, rep.getScenario());
                        isMinimalSolution = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } 
			}
			
			if (isMinimalSolution) {
				addMinimalCandidate(cand);
				{
					final Candidate<H> more = newExistMoreSolutions();
					if (more == null) {
						break;
					}
					System.out.println("There is more out there; e.g. " + more.getHypothesis());
				}
			}
		} // while
		
		// Deals with the tmpCandidates
		_candidates.addAll(_tmpCandidates);

		final Diagnosis<H> result = new Diagnosis<H>(_candidates);

		_candidates.clear();
		_tmpCandidates.clear();
		_open.clear();
		_generated.clear();
		
		_prob.clear();
		
		_prob = null;
		_space = null;
		
		return result;
	}
	
	private Candidate<H> digFrom(H currentHypo) {
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
		return _prob.searchHypothesis(subspaces);
	}
	
	/**
	 * Adds a temporary candidate.  A temporary candidate is a candidate 
	 * that has not been proved to be minimal yet.  
	 * 
	 * @param c the candidate that is to be added. 
	 * */
	private void addTemporaryCandidate(Candidate<H> c) {
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
	
	/**
	 * Adds the specified hypothesis to the list of candidates of this diagnosis problem.  
	 * 
	 * @param c the candidate that is being added.  
	 * */
	private void addMinimalCandidate(Candidate<H> h) {
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
	
	private void showCandidates() {
		System.out.println("Current list of candidates: ");
		for (final Candidate<H> cand: _candidates) {
			System.out.println("CANDIDATE >> " + cand.getHypothesis());
		}
		for (final Candidate<H> cand: _tmpCandidates) {
			System.out.println("TMPCANDIDATE >> " + cand.getHypothesis());
		}
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
	private void applyConflict(H h, C con) {
		final List<H> newHypos = new ArrayList<H>();
		{
			final List<? extends H> tmpHypos = 
				_space.createSubHypotheses(h, con);
			for (final H newHypo: tmpHypos) {
				if (isSubsumed(newHypo)) {
					continue;
				}
				if (_generated.add(newHypo)) {
					newHypos.add(newHypo);
				}
			}
		}
		_open.addAll(newHypos);
		HYPOTHESES_GENERATED += newHypos.size();
		System.out.println("Adding: ");
		for (final H nh: newHypos) {
			System.out.println("\t" + nh);
		}
		System.out.println("Current stack (" + _open.size() + " hypotheses): ");
		for (final H nh: _open) {
			System.out.println("\t" + nh);
		}
		System.out.println("Generated so far: " + HYPOTHESES_GENERATED);
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
	
	private Candidate<H> newExistMoreSolutions() {
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
		
		return _prob.searchHypothesis(subspaces);
	}

	public int nbHypothesis() {
		return HYPOTHESES_GENERATED;
	}
}
