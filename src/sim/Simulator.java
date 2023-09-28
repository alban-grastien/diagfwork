package sim;

import edu.supercom.util.PseudoRandom;
import lang.MMLDTransition;
import lang.Network;
import lang.Period;
import lang.YAMLDFormula;
import util.MMLDGlobalTransition;
import util.Time;
import util.TimedScenario;

/**
 * A <code>Simulator</code>, i.e., a simulator, 
 * is an object that can be used to simulate a network.  
 * The user can trigger transitions on the simulator, 
 * but the simulator is also able to decide 
 * when forced transition will take place 
 * (although the user may override such decisions).  
 * 
 * TODO: Static simulator (i.e., something that does not allow to modify the scenario)
 */
public interface Simulator {
    
    /**
     * Returns the random generator for this simulator.  
     * 
     * @return the random generator used by this simulator.  
     */
    public PseudoRandom getRandom();

    // ACCESS

    /**
     * Returns the network this simulator is defined on.  
     * 
     * @return the network of this simulator.  
     */
    public Network getNetwork();
    
	/**
	 * Returns the current time in this simulator.
	 * 
	 * @return the time of the final state in this simulator.
	 * */
	public Time getCurrentTime();

	/**
	 * Returns the scenario generated by this simulator.
	 * 
	 * @return the current scenario in this simulator.
	 * */
	public TimedScenario getScenario();
	
    /**
     * Indicates when the specified formula 
     * is scheduled to trigger the specified transition.  
     * 
     * @param f the formula that triggers the transition.  
     * @param t the transition that is triggered. 
     * @return the time when <code>t</code> will be triggered by <code>f</code>
     * (assuming nothing else happens meanwhile), 
     * or a negative value if <code>f</code> 
     * is not scheduled to trigger <code>t</code>.  
     */
	public Time willTrigger(YAMLDFormula f, MMLDTransition t);
    
    /**
     * Indicates the delay before the next forced transition triggers.  
     * 
     * @return the time when the next forced transition 
     * is scheduled to trigger if any, a negative value otherwise.  
     */
    public Period nextForcedTransitionDelay();
	
	/**
	 * Returns the scenario from the original state to the specified position.  
	 * 
	 * @param pos the position at the end of the scenario.  
	 * @return the scenario until state <code>pos</code>.  
	 * */
	public TimedScenario get(int pos);
    
    /**
     * Returns the next forced transition 
     * that will be triggered according to this simulator.  
     * 
     * @return the next forced transition to trigger.  
     */
    public MMLDTransition nextForcedTransition();
    
    // CHANGES
    
    /**
     * Triggers the specified global transition 
     * in the current state of the simulator (at the current time).  
     * 
     * @param tr the transition that should be triggered.  
     * @throws Exception if the transition cannot be triggered.  
     * @todo be more specific about what kind of transitions is allowed 
     * (forced transitions?) and what kind of exception is generated.  
     */
    public void trigger(MMLDGlobalTransition tr);

	/**
	 * Elapses the specified amount of time 
     * unless a forced transition is triggered; 
     * in this case, the forced transition takes place 
     * and no time elapses after this transition.
	 * 
	 * @return the time that actually elapsed.
	 * */
	public Period elapseTime(Period time);

	/**
	 * Returns to the specified step of the simulator. After this method is
	 * called, all transitions after the current state are lost.
	 * 
	 * @param i
	 *            the number of the transition where this simulator jumps back
	 *            to.
	 * */
	public void cut(int i);
    
    /**
     * Triggers the next scheduled transition if any.  
     * If necessary, the time before the transition 
     * triggers elapses.  
     */
    public void triggerNext();
    
    /**
     * Sets the triggering time when the specified formula 
     * will trigger the specified transition.  
     * 
     * @param tr the transition that will be triggered.  
     * @param f the formula that will trigger the transition.  
     * @param t the time the formula is set to.  
     * @throws IllegalArgumentException if the trigger time of the formula 
     * can be set to the specified time.  
     */
    public void setTriggeringTime(MMLDTransition tr, YAMLDFormula f, Time t);
    
    /**
     * Creates a copy of this simulator.  The copy is independent, 
     * meaning that no further change to this simulator will affect the copy, 
     * and vice versa.  
     * 
     * @return an independent copy of this simulator.  
     */
    public Simulator copy();
}