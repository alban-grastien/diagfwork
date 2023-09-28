package gui;

import lang.MMLDTransition;
import lang.Period;
import lang.YAMLDComponent;
import lang.YAMLDFormula;
import util.Time;

/**
 * A <code>InteractiveInterface</code>, i.e., an interactive interface, 
 * is an interface that can be used to act on a simulation.  
 */
public interface InteractiveInterface extends AccessInterface {
    
    /**
     * Indicates whether the specified transition 
     * can be triggered by the simulation.  
     * The reason why this method is not defined in {@link AccessInterface} 
     * is that there is no need to know what can be triggered 
     * if you are not allowed to (arguable).  
     * 
     * @param tr the transition that is triggered.  
     * @return <code>true</code> if <code>tr</code> can be triggered.  
     */
    public boolean canBeTriggered(MMLDTransition tr);
    
    /**
     * Triggers the specified transition in the simulation.  
     * A non empty amount of time might elapse before the trigger.  
     * 
     * @param tr the transition that is triggered.  
     * @throws IllegalArgumentException if <code>tr</code> cannot be triggered.  
     */
    public void trigger(MMLDTransition tr);
    
    /**
     * Elapses exactly the specified amount of time.  
     * This may result in one or several forced transitions 
     * to trigger.  
     * 
     * @param t the time that must elapse.  
     */
    public void elapseTime(Period t);
    
    /**
     * Advances to the next state in the scenario of the simulator, 
     * and failing to do so (if the scenario is already at the last state), 
     * triggers the next forced event.  
     */
    public void nextEvent();
    
    /**
     * Elapse the specified amount of time 
     * without triggering a spontaneous transition if possible, 
     * triggers the next forced transition otherwise.  
     * 
     * @param t the time that should elapse.  
	 * @return <code>true</code> if the time elapsed, <code>false</code> if a
	 *         forced transition was taken.
     */
    public boolean elapseOrOneForcedTransition(Period t);
    
    /**
     * Ensures that the specified forced transition 
     * is set to be the next one to trigger.  
     * 
     * @param tr the transition to trigger first.  
     * @param f the formula that will trigger the transition.  
     * @return <code>true</code> if the operation was successful.  
     */
    public boolean setFirst(MMLDTransition tr, YAMLDFormula f);
    
    /**
	 * Sets the schedule time of the specified precondition 
	 * for the specified transition to the specified value.  
	 * */
	public void setSchedule(MMLDTransition trans, YAMLDFormula f, Time d);

    // VISUALISATION
    
    /**
     * Indicates that the interface should not focus on the specified component.  
     * 
     * @param c the component the interface should focus on.  
     */
    public void chooseComponent(YAMLDComponent c);
    
    // TODO: chooseNetwork(Network n)
}
