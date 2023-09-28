package diag.reiter;

import java.util.List;
import java.util.Set;

import diag.reiter.HypothesisSubspace.HypothesisSubspaceType;
import diag.reiter.HypothesisSubspace.SearchType;

import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;

import util.Scenario;

/**
 * A hypothesis space is an object that defines a set of hypotheses.  
 * This set is complete in the sense that any scenario 
 * on the model is associated with exactly one hypothesis.  
 * 
 * @param <H> The type of hypothesis of this space.  
 * @param <C> The type of conflicts generated on this space.  
 * */
public interface HypothesisSpace<H extends Hypothesis, C extends Conflict> {

	/**
	 * Returns the list of minimal hypotheses for this space.  
	 * 
	 * @return all the minimal hypotheses of this space.  
	 * */
	public Set<? extends H> minimalHypotheses();
	
	/**
	 * Returns the hypothesis associated with the specified scenario.  
	 * 
	 * @param sce the scenario.  
	 * @return the hypothesis <code>sce</code> belongs to.  
	 * */
	public H getHypothesis(Scenario sce);

	/**
	 * Returns the list of sub hypotheses of the specified hypothesis 
	 * that are immediate successors of the specified hypothesis 
	 * through the specified conflict.  
	 * In other words, it returns those sub hypothesis of <code>h</code> 
	 * that are not rejected by <code>c</code> and such that any hypothesis <code>h'</code> 
	 * strictly better than <code>h'</code> and strictly worst than <code>h</code> 
	 * are rejected by <code>c</code>.  
	 * 
	 * @param h the hypothesis whose successors and built.  
	 * @param c the conflict.  
	 * @return the list of direct successors of <code>h</code> through <code>c</code>.  
	 * */
	public List<? extends H> createSubHypotheses(H hypothesis, C c);
	
	/**
	 * Indicates whether the first specified hypothesis 
	 * is a sub hypothesis of the second specified hypothesis.  
	 * 
	 * @param h1 the first hypothesis.  
	 * @param h2 the second hypothesis.  
	 * @return <code>true</code> if <code>h1</code> is preferable to <code>h2</code>.  
	 * */
	public boolean isSubHypothesis(H h1, H h2);
	
	/**
	 * Indicates whether the specified hypothesis <i>hits</i> the specified conflict.  
	 * A hypothesis hits a conflict if and only if 
	 * it is not dismissed by the conflict.  
	 * The method may be imprecise, i.e., return that the hypothesis 
	 * does hit the conflict although it does not.  
	 * Being able to notify that a hypothesis does not hit a conflict 
	 * is preferable though as it allows to easily dismiss a hypothesis.  
	 * 
	 * @param h the hypothesis on which the conflict is tested.  
	 * @param c the conflict that is tested.  
	 * @return <code>true</code> if the hypothesis hits the conflict, 
	 * unspecified (i.e, either <code>true</code> or <code>false</code>) 
	 * otherwise.  
	 * */
	public boolean hits(H h, C c);
	
	/**
	 * Builds a conflict from the specified SAT conflict.  
	 * 
	 * @param h the hypothesis from which this conflict is built.  
	 * @param con the SAT conflict, i.e., the set of variable 
	 * that cannot be assigned the value this hypothesis suggests.  
	 * @param n the number of time steps.  
	 * @param varass the variable assigner that explain what each SAT variable is about.  
	 * @return the conflict built from the SAT conflict.  
	 * */
	public C buildConflict(H h, List<Integer> con,  
			int n, VariableAssigner varass);
	
	/**
	 * Allocates SAT variables from the specified loader to allow 
	 * to encode the specified constraint on the specified hypothesis.  
	 * For instance, the procedure for testing a hypothesis 
	 * would be <code>createVariables(loader, n, varass, HYPO, CONFLICT);</code>.  
	 * The method may throw {@link UnsupportedOperationException} for certain 
	 * combinations of arguments.   
	 * 
	 * @param varass a variable assigner (possibly null) with a bunch of variables 
	 * already defined for other problems.  In case a variable semantics is already defined 
	 * in <code>varass</code>, this method may reuse them.  
	 * @param hss the HypothesisSubSpace that indicates what subspace is considered.  
	 * @param st indicates whether the search is inclusive, exclusive, 
	 * or whether it is inclusive and should generate a conflict if it fails.  
	 * @return a variable assigner representing all SAT variables used 
	 * for the search (even if those variables were already in <code>varass</code>).  
	 * */
	public VariableAssigner createVariables(H h, 
			VariableLoader loader, 
			VariableAssigner varass, 
			HypothesisSubspaceType hss, 
			SearchType st);
	
	/**
	 * @see #createVariables(VariableLoader, int, VariableAssigner, HypothesisSubSpace, SearchType)
	 * @return the list of variable semantics that are used for conflict generation 
	 * (possibly null if st is different from CONFLICT).  
	 * */
	public List<Integer> generateSAT(H h,
			VariableAssigner eventAss, 
			VariableAssigner hypoAss, 
			ClauseStream out,  
			HypothesisSubspaceType hss, 
			SearchType st);
}
