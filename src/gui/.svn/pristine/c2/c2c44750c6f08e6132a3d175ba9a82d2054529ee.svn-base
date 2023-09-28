package gui;

import lang.MMLDTransition;
import lang.Network;
import lang.TimedState;
import lang.YAMLDFormula;
import util.Time;

/**
 * A <code>AccessInterface</code>, i.e., an access interface, 
 * is an interface that can be used by any element to determine 
 * what is currently going on in the simulation.  
 */
public interface AccessInterface extends StateChanger, NetworkChanger {
    
    /**
     * Returns the network currently used in the simulation.  
     * 
     * @return the network used in the simulation, 
     * <code>null</code> if no network was loaded yet.  
     */
    public Network getNetwork();

    /**
     * Indicates whether the current state of the simulation is the final state.  
     * 
     * @return <code>true</code> if the current state is the final state.  
     */
    public boolean isInFinalState();
    
    /**
     * Returns the final state.  
     * 
     * @return the final state.  
     */
    public TimedState getFinalState();
    
    /**
     * Returns the current time.  
     * 
     * @return the current time.  
     */
    public Time getCurrentTime();
    
    /**
	 * Indicates when the specified precondition will trigger 
	 * the specified transition (provided the precondition is not falsified before).  
	 * If the specified formula is not currently satisfied, 
	 * returns a negative value.  
	 * The result is also unspecified if the specified formula 
	 * is not a precondition of the specified transition 
	 * (and it may throw an exception).  
	 * 
	 * @param pr the precondition.  
	 * @param tr the transition.  
	 * @return the time <code>f</code> will be satisfied for long enough 
	 * so that it will trigger the transition <code>t</code>.  
	 * */
	public Time willTrigger(YAMLDFormula pr, MMLDTransition tr);
    
    /**
     * Returns the number of transitions in the scenario.  
     * 
     * @return the number of transitions from the first state to the final state.  
     */
    public int nbTransitions();
    
    /**
     * Returns the current position (in terms of transitions) in the scenario.  
     * 
     * @return the number of transitions from the first state 
     * to the current state.  
     */
    public int getCurrentTransitionPosition();
}
