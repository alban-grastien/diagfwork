package diag.reiter.solvers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import diag.reiter.Candidate;
import diag.reiter.Conflict;
import diag.reiter.Diagnosis;
import diag.reiter.DiagnosisProblem;
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

public class BottomUp<H extends Hypothesis,C extends Conflict> 
implements Solver<H,C> {

	private DiagnosisProblem<H,C> _prob;
	
	@Override
	public Diagnosis<H> solve(Options opt, Network net, State st, AlarmLog log,
			HypothesisSpace<H, C> space, Set<YAMLDEvent> faults) {
		_prob = new DiagnosisProblem<H, C>(opt, net, st, log, space, faults);
		
		final List<Candidate<H>> candidates = new ArrayList<Candidate<H>>();
		final List<H> hypos = new ArrayList<H>();
		
		for (;;) {
			Candidate<H> current = findDifferentSolution(space, hypos);
			if (current == null) {
				break;
			}
			System.out.println("I found " + current.getHypothesis());
			System.out.println("Trying to improve...");
			
			for (;;) {
				Candidate<H> better = betterSolution(space, current.getHypothesis());
				if (better == null) {
					break;
				}
				current = better;
				System.out.println("Better: " + current.getHypothesis());
			}
			System.out.println("Best!");
			System.out.println();
			
			candidates.add(current);
			hypos.add(current.getHypothesis());
		}
		
		final Diagnosis<H> result = new Diagnosis<H>(candidates);
		
		_prob.clear();
		_prob = null;
		
		return result;
	}
	
	private Candidate<H> findDifferentSolution(
			HypothesisSpace<H,?> space, Collection<H> candidates) {
		return findDifferentSolution(_prob, space, candidates);
	}
	
	/**
	 * Searches a candidate to the specified diagnosis problem in the specified hypothesis space 
	 * that is not covered by any of the candidates in the specified list.  
	 * 
	 * @param prob the diagnosis problem that is being solved.  
	 * @param space the hypothesis space on which the diagnosis is performed.  
	 * @param candidates the collections of hypotheses none of which should 
	 * cover the solution.  
	 * @return a diagnosis candidate covered by none of the specified hypotheses 
	 * if any, <code>null</code> otherwise.  
	 * */
	public static <H extends Hypothesis> Candidate<H> findDifferentSolution(
			DiagnosisProblem<H,?> prob, HypothesisSpace<H,?> space, Collection<H> candidates) {
		final List<HypothesisSubspace<H>> subspaces = 
			new ArrayList<HypothesisSubspace<H>>();

		for (final H cand: candidates) {
			final HypothesisSubspace<H> sub = new HypothesisSubspace<H>(
					cand, HypothesisSubspaceType.SUB, SearchType.EXCLUDE);
			subspaces.add(sub);
		}
		
		return prob.searchHypothesis(subspaces);
	}
	
	private Candidate<H> betterSolution(HypothesisSpace<H,?> space, H h) {
		return betterSolution(_prob, space, h);
	}
	
	/**
	 * Searches for a candidate to the specified diagnosis problem in the specified hypothesis space
	 * which is strictly better than the specified hypothesis.  
	 * 
	 * @param prob the diagnosis problem that is being solved.  
	 * @param space the hypothesis space on which the diagnosis is performed.  
	 * @param h the hypothesis which should be covered by the returned solution.  
	 * @return a candidate which is strictly better than <code>h</code> if any, 
	 * <code>null</code> otherwise.  
	 * */
	private static <H extends Hypothesis> Candidate<H> betterSolution(
			DiagnosisProblem<H,?> prob, HypothesisSpace<H,?> space, H h) {
		final List<HypothesisSubspace<H>> subspaces = 
			new ArrayList<HypothesisSubspace<H>>();

		subspaces.add(new HypothesisSubspace<H>(
				h, HypothesisSubspaceType.SUPER, SearchType.INCLUDE));
		subspaces.add(new HypothesisSubspace<H>(
				h, HypothesisSubspaceType.HYPO, SearchType.EXCLUDE));
		
		return prob.searchHypothesis(subspaces);
	}

}
