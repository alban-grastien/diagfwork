package diag.reiter.hypos;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import diag.reiter.HypothesisSpace;
import diag.reiter.HypothesisSubspace.HypothesisSubspaceType;
import diag.reiter.HypothesisSubspace.SearchType;

import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;

import lang.YAMLDEvent;

import util.Scenario;

/**
 * A sequential hypothesis space is a hypothesis space 
 * where a hypothesis is defined as a sequence of faults from a specified set.
 * */
public final class SequentialHypothesisSpace 
implements HypothesisSpace<SequentialHypothesis, SequentialConflict> {
	
	/**
	 * The set of faults that defines this hypothesis space.  
	 * */
	private final Set<YAMLDEvent> _faults;
	
	/**
	 * The (unique) set of minimal hypotheses.  
	 * */
	public final Set<SequentialHypothesis> _min;
	
	/**
	 * The number of time steps.
	 * */
	public final int _nbTrans;
	
	/**
	 * Builds a sequential hypothesis space defined by the specified set of faults.
	 *   
	 * @param f the set of faults for this sequential hypothesis space.  
	 * @param nbTrans the number of time steps.  
	 * */
	public SequentialHypothesisSpace(Set<YAMLDEvent> f, int nbTrans) {
		_nbTrans = nbTrans;
		{
			final Set<YAMLDEvent> faults = new HashSet<YAMLDEvent>(f);
			_faults = Collections.unmodifiableSet(faults);
		}
		{
			final Set<SequentialHypothesis> min = new HashSet<SequentialHypothesis>();
			min.add(new SequentialHypothesis(_faults, nbTrans));
			_min = Collections.unmodifiableSet(min);
		}
	}

	@Override
	public SequentialHypothesis getHypothesis(Scenario sce) {
		return new SequentialHypothesis(sce, _faults, _nbTrans);
	}

	@Override
	public Set<SequentialHypothesis> minimalHypotheses() {
		return _min;
	}

	@Override
	public List<? extends SequentialHypothesis> createSubHypotheses(
			SequentialHypothesis h, SequentialConflict c) {
		return h.createSubHypotheses(c);
	}

	@Override
	public boolean isSubHypothesis(SequentialHypothesis h1,
			SequentialHypothesis h2) {
		return SequentialHypothesis.isSubHypothesis(h1, h2);
	}

	@Override
	public boolean hits(SequentialHypothesis h, SequentialConflict c) {
		return h.hits(c);
	}

	@Override
	public SequentialConflict buildConflict(SequentialHypothesis h, List<Integer> con,
			int n, VariableAssigner varass) {
		return h.buildConflict(con, n, varass);
	}
	public VariableAssigner createVariables(SequentialHypothesis h, 
			VariableLoader loader, 
			VariableAssigner varass, 
			HypothesisSubspaceType hss, 
			SearchType st) {
		return h.createVariables(loader, varass, hss, st);
	}
	
	/**
	 * @see #createVariables(VariableLoader, int, VariableAssigner, HypothesisSubSpace, SearchType)
	 * @return the list of variable semantics that are used for conflict generation 
	 * (possibly null if st is different from CONFLICT).  
	 * */
	public List<Integer> generateSAT(SequentialHypothesis h,
			VariableAssigner eventAss, 
			VariableAssigner hypoAss, 
			ClauseStream out,  
			HypothesisSubspaceType hss, 
			SearchType st) {
		return h.generateSAT(eventAss, hypoAss, out, hss, st);
	}


}
