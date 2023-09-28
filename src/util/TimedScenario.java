package util;

import java.util.Collection;

import lang.State;
import lang.TimedState;
import lang.YAMLDEvent;

/**
 * A timed scenario is a scenario that is attached timed information.  
 * */
public interface TimedScenario {
	
	/**
	 * Indicates the number of transitions in this scenario.  By extension, 
	 * the number of states is <code>nbTrans() + 1</code>.  
	 * This method should return the same value 
	 * as <code>getScenario().nbTrans()</code>.  
	 * 
	 * @return the number of transitions in this scenario.  
	 * */
	public int nbTrans();
	
	/**
	 * Returns the timed state in this scenario 
	 * right before the <i>i</i>th transition 
	 * (starting from <i>0</i>).  
	 * If 
	 * <ul>
	 * <li><code>i < 0</code>, throws an exception; </li>
	 * <li><code>i == 0</code>, returns the initial state 
	 * before the <i>0</i>th transition; </li>
	 * <li><code>i = nbTrans()</code>, returns the final state
	 * (possibly a long time after the last transition); </li>
	 * <li><code>i > nbTrans()</code>, throws an exception.</li>
	 * </ul>
	 * 
	 * @param i the number of the state.  
	 * @return the state of the system after <i>i+1</i> transitions took place.  
	 * */
	public TimedState getStateBeforeTransition(int i);
	
	/**
	 * Returns the timed state in this scenario 
	 * right after the <i>i</i>th transition 
	 * (starting from <i>0</i>).  
	 * If 
	 * <ul>
	 * <li><code>i < -1</code>, throws an exception; </li>
	 * <li><code>i == -1</code>, returns the initial state 
	 * (possibly a long time before the initial transition); </li>
	 * <li><code>i = nbTrans() -1</code>, returns the final state
	 * after the last transition; </li>
	 * <li><code>i >= nbTrans()</code>, throws an exception.</li>
	 * </ul>
	 * 
	 * @param i the number of the state.  
	 * @return the state of the system after <i>i+1</i> transitions took place.  
	 * */
	public TimedState getStateAfterTransition(int i);
	
	/**
	 * Returns the <i>i</i>th state of this scenario.  
	 * This is equivalent to <code>getStateAfterTransition(i-1).getState()</code>.  
	 * 
	 * @param i the number of the state (between <code>0</code> and <code>nbTrans()</code> included).  
	 * @return the state of this scenario after <code>i</code> transitions.  
	 * @see TimedScenario#nbTrans()
	 * */
	public State getState(int i);
	
	/**
	 * Returns the state of the network at the end of this scenario.  
	 * 
	 * @return the final state of the network.  
	 * */
	public TimedState getLastState();
	
	/**
	 * Returns the state of the network at the beginning of this scenario.  
	 * 
	 * @return the initial state of the network.  
	 * */
	public TimedState getFirstState();
	
	/**
	 * Returns the <i>i</i>th transition of this scenario with the first transition 
	 * being <code>0</code>.  
	 * 
	 * @param i the number of the transition (between <code>0</code> and <code>nbTrans()-1</code>).  
	 * @return the <i>i</i>th transition of the system.  
	 * @see TimedScenario#nbTrans()
	 * */
	public MMLDGlobalTransition getTrans(int i);
	
	/**
	 * Returns the time at the specified position in the scenario.  
	 * <ul>
	 * <li><code>i == -1</code>: the time of the initial state in the scenario.  
	 * </li>
	 * <li><code>i</code> in <code>[0,nbTrans()-1]</code>: the time when <code>getTrans(i)</code> takes place.  
	 * </li>
	 * <li><code>i == nbTrans()</code>: the time at the end of the scenario.  
	 * </li>
	 * </ul>
	 * Notice also that the network is in state <code>getState(i)</code> 
	 * during <code>[getTime(i-1),getTime(i)]</code>.  
	 * 
	 * @param i the number of the transition (between <code>0</code> and <code>nbTrans()</code>).  
	 * @return the <i>i</i>th transition of the system.  
	 * @see TimedScenario#nbTrans()
	 * */
	public Time getTime(int i);
	
	/**
	 * Returns a string representation of this scenario.  
	 * 
	 * @return a string representation of this scenario.  
	 * */
	public String toFormattedString();
	
	/**
	 * Returns the alarm log associated with this scenario 
	 * provided the specified set of observable events.  
	 * 
	 * @param obs the set of events that should be recorded in the alarm log.  
	 * @return the alarm log of this scenario.  
	 * */
	public AlarmLog alarmLog(Collection<YAMLDEvent> obs);
	
	/**
	 * Returns the scenario associated with this timed scenario.  
	 * 
	 * @return a version of this scenario without time.  
	 * */
	public Scenario getScenario();
	
	/**
	 * Returns the time at the end of this scenario.  
	 * 
	 * @return the time when this scenario ends.  
	 * */
	public Time getFinalTime();
	
	/**
	 * Returns the time at the beginning of this scenario.  
	 * 
	 * @return the time when this scenario starts.  
	 * */
	public Time getInitialTime();
}
