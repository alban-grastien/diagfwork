package lang;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import util.GlobalTransition;
import util.MMLDGlobalTransition;

/**
 * Defines a state of the whole system.  An implementation of this interface 
 * is {@link ExplicitState}.    
 * 
 * @author Alban Grastien
 * @version 1.0
 * TODO: {@link #hashCode()} and {@link #equals(Object)}.  
 * */
public interface State {

	/**
	 * Returns the assignment of the specified variable.  Note that a {@link YAMLDGenericVar}
	 * is associated to exactly one {@link YAMLDComponent}.   
	 * 
	 * @param var the variable for which the assignment is requested.  
	 * @return the assignment of variable <code>var</code>.    
	 * */
	public YAMLDValue getValue(YAMLDGenericVar var);

	/**
	 * Returns the network on which this state is defined.  
	 * This method should probably be inherited by any object 
	 * that is defined globally on the network.  
	 * 
	 * @return the network.  
	 * */
	public Network getNetwork();
	
	/**
	 * Returns a formatted view of the state.  This string contains only the 
	 * state variables.  The format is: <code>comp.var := val</code>, 
	 * one line for each variable, plus a plurality (possibly empty) of 
	 * commented lines starting with <code>//</code>.
	 * 
	 * @return the value of each state variable in string format.  
	 * */
	public String toFormattedString();
	
	/**
	 * Returns a formatted view of the state including the dependent variables.  
	 * The format is the one specified by {@link #toFormattedString()}.  
	 * 
	 * @return the value of each state variable in string format.  
	 * */
	public String completeFormattedString();
	
	/**
	 * Returns the result of applying the specified state modification.  
	 * 
	 * @param stateMod the state modification that is applied on this state.  
	 * @return the new state after application of stateMod.  
	 * */
	public State apply(StateModification stateMod);
	
	/**
	 * Applies in order all transitions starting from the current state.
	 * It is assumed that the first transition can be applied in the initial state
	 * and that the i-th transition can be applied in the state that
	 * results after applying the (i-1)-th transition. 
	 * @param transes
	 * @return the final state
	 */
	public State applyMMLDGlobalTransitions(List<MMLDGlobalTransition> transes);
	
	/**
	 * Checks whether a transition of the type {@link MMLDGlobalTransition}
	 * is applicable in this state.
	 * @param trans
	 * @return true or false
	 */
	public boolean isApplicable(MMLDGlobalTransition trans);
	
	/**
	 * Returns the result of applying the specified assignments to this state.  
	 * This state is unmodified.  
	 * 
	 * @param m a map of assignments for some state variables, the other variables 
	 * remain the same.  The assignments of variables that are not state variables 
	 * are ignored.  
	 * @return a state corresponding to the application of <code>m</code> on this state.
	 * @deprecated Use {@link #apply(StateModification)} 
	 * for instance with {@link MapStateModification}.  
	 * */
	@Deprecated
	public State apply(Map<? extends YAMLDVar,YAMLDValue> m);
	
	/**
	 * Returns the result of applying the specified set of transition to this state.  
	 * It is assumed that all the transitions are applicable 
	 * (in other words, this condition is not checked) 
	 * and that they do not interfere; 
	 * if these conditions fail to be satisfied, the behaviour of this method 
	 * is not specified (but it might lead to an exception being thrown).  
	 * 
	 * @param transes the collection of transitions applied on this state.  
	 * @return the new state after the transitions of <code>transes</code> 
	 * have been applied.  
	 * @deprecated Should disappear anyway.  
	 * */
	@Deprecated
	public State apply(Collection<YAMLDTrans> transes);
	
	/**
	 * Converts the collection of {@link GlobalTransition} objects into 
	 * a collection c of {@link YAMLDTrans} objects.
	 * Then, calls apply(<code>c</code>) and returns the result.
	 * @param transitions
	 * @return the new state after the transitions have been applied
	 * @deprecated
	 */
	@Deprecated
	public State applyGlobalTransitions(Collection<GlobalTransition> transitions);
	
	/**
	 * Indicates whether the precondition 
	 * of a forced transition is enabled in this state.  
	 * 
	 * @return <code>true</code> if a forced transition is enabled.  
	 * @deprecated this method is related to {@link YAMLDTrans}.  
	 * */
	@Deprecated
	public boolean hasEnabledForcedTransition();

}
