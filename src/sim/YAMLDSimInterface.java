package sim;

import lang.YAMLDEvent;

/**
 *  A simulator is a component that is in charge of generating a scenario on the system.  
 *  Different simulators may have different behaviours regarding forced events: 
 *  they may authoritatively decide when the forced event will take place, 
 *  they may also wait until the last moment, thus giving the opportunity 
 *  for the user to trigger them.  
 *  
 *  @author Jussi Rintanen
 *  @author Alban Grastien Adding new functionalities
 */
public interface YAMLDSimInterface {
	/**
	 * Makes the specified time elapse in the system.  If a forced transition 
	 * is supposed to take place (right) after the specified amount of time passes, 
	 * then the transition takes place.  If a forced transition is supposed to 
	 * take place before the specified time elapses, the method fails.  
	 * 
	 * @param time the time that takes place.  
	 * @see #queryMaxProgress()
	 * */
	public void progressTime(double time); 

	/**
	 * Progresses until the next forced event.  
	 * TODO: change the signature and give an upper bound on how much time can take place.  
	 * */
	public void doMaxProgress();
	
	/**
	 * Triggers the specified event.  
	 * */
	public void triggerEvent(YAMLDEvent eventname); 
	
	/**
	 * Indicates the maximum time before the next forced event takes place.  
	 * 
	 * @return the maximum time that can elapse before the next transition.  
	 * */
	double queryMaxProgress();
	
	/**
	 * Returns the current time.  
	 * 
	 * @return the current time.  
	 * */
	double queryAbsoluteTime();
}
