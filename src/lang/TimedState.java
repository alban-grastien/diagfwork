package lang;

import java.util.Map;

/**
 * A <b>timed state</b> is an extension of a {@link State} 
 * that keeps track of how long precondition have been satisfied for.  
 * Notice that the interface {@link TimedState} 
 * does not extend the interface {@link State} 
 * as their {{@link #equals(Object)} and {{@link #hashCode()} methods differ.  
 * 
 * @author Alban Grastien
 * @see Network#getForcingPreconditionsWithComponent()()
 * */
public interface TimedState {

	/**
	 * Returns how long the specified forcing condition 
	 * has been satisfied for on the specified component.  
	 * The formula and the component should be part 
	 * of {@link Network#getForcingPreconditionsWithComponent()}; 
	 * otherwise, the behaviour of this method is unspecified 
	 * (but should not lead to throwing an exception).  
	 * 
	 * @param c the component for which the formula should be satisfied.  
	 * @param f the forcing formula.  
	 * @return the period for which the formula has been satisfied in this state
	 * if satisfied, this should return a <code>null</code> value.  
	 */
	public Period satisfiedFor(YAMLDComponent c, YAMLDFormula f);

	/**
	 * Returns the state associated with this timed state.  
	 * 
	 * @return the state of this timed state.  
	 * */
	public State getState();

	/**
	 * Returns the result of applying the specified assignments to this state.  
	 * 
	 * @param m a map of assignments for some state variables, the other variables 
	 * remain the same.  The assignments of variables that are not state variables 
	 * are ignored.  
	 * @return a state corresponding to the application of <code>m</code> on this state.
	 * @deprecated Use {@link #apply(StateModification)}.  
	 * */
	public TimedState apply(Map<? extends YAMLDVar,YAMLDValue> m);

	/**
	 * Returns the result of applying the specified modification to this state.    
	 * 
	 * @param modif a modification applied on the state.  
	 * @return a state corresponding to the application of <code>modif</code> to this state.
	 * */
	public TimedState apply(StateModification modif);
	

	/**
	 * Elapses the specified period in this timed state.  
	 * No transition is triggered.  
	 * 
	 * @param p the period that must elapse.  
	 * @return the new timed state.  
	 * */
	public TimedState elapse(Period p);
	
	/**
	 * Returns a string representation of this timed state in the following format.  
	 * The string is a collection of atomic declarations 
	 * where a declaration is: 
	 * <ul>
	 * <li>
	 *   either an assignment written by 
	 *   <code>compName <b>.</b> varName <b>:=</b> valName</code> 
	 *   (where varName is a state variable)
	 * </li> 
	 * <li> 
	 *   or the declaration of the value of a clock 
	 *   indicating how long a specific formula 
	 *   (which is a precondition of some transition of some component) 
	 *   has been satisfied in the component, written by 
	 *   <code><b>&lt;</b> compName <b>,</b> formula <b>&gt; :=</b> time</code>.  
	 * </li> 
	 * </ul>
	 * All state variables assignments should be returned.  
	 * The satisfied preconditions for which the clock value is not returned 
	 * implicitly have a <code>0</code> value.  
	 * This method is implemented in 
	 * {@link AbstractTimedState#toFormattedString(TimedState)}.  
	 * 
	 * @return a formatted string for this timed state.  
	 * */
	public String toFormattedString();
}
