package diag.reiter.solvers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import diag.reiter.Candidate;
import diag.reiter.Conflict;
import diag.reiter.Diagnosis;
import diag.reiter.DiagnosisProblem;
import diag.reiter.Hypothesis;
import diag.reiter.HypothesisSpace;
import diag.reiter.Solver;

import lang.Network;
import lang.State;
import lang.YAMLDEvent;
import util.AlarmLog;
import edu.supercom.util.Options;

/**
 * An implementation of a bottom up solver.  
 * */
public class BlindBottomUp<H extends Hypothesis,C extends Conflict> 
implements Solver<H,C>  {

	
	@Override
	public Diagnosis<H> solve(Options opt, Network net, State st, AlarmLog log,
			HypothesisSpace<H, C> space, Set<YAMLDEvent> faults) {
		DiagnosisProblem<H,C> prob = new DiagnosisProblem<H, C>(opt, net, st, log, space, faults);
		
		final List<Candidate<H>> candidates = new ArrayList<Candidate<H>>();
		final List<H> hypos = new ArrayList<H>();
		
		for (;;) {
			final Candidate<H> current = BottomUp.findDifferentSolution(prob, space, hypos);
			if (current == null) {
				break;
			}
			final H currentHypo = current.getHypothesis();
			System.out.println("I found " + currentHypo);
			
			final Iterator<Candidate<H>> cIt = candidates.iterator();
			final Iterator<H> hIt = hypos.iterator();
			while (hIt.hasNext()) {
				cIt.next();
				final H old = hIt.next();
				if (space.isSubHypothesis(old, currentHypo)) {
					System.out.println("Removing " + old);
					cIt.remove();
					hIt.remove();
				}
			}
			
			candidates.add(current);
			hypos.add(currentHypo);
			
			System.out.println("Current hypotheses: ");
			for (final H h: hypos) {
				System.out.println("TMPCANDIDATE >> " + h);
			}
			System.out.println();
		}
		
		final Diagnosis<H> result = new Diagnosis<H>(candidates);
		
		prob.clear();
		prob = null;
		
		return result;
	}

}
