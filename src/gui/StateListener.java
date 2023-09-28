package gui;

import lang.State;
import lang.TimedState;

/**
 * A state listener is an object that is supposed to do something 
 * when the state of the network is modified.  
 * Typical example is a graphical component showing the current state 
 * of the network/a component.  <p />
 * <b>Implementation information</b>: When the state is modified, 
 * the {@link #newStateHandler(State)} method is broadcast 
 * to all {@link StateListener} objects in the current thread.  
 * Therefore, if the implementation of {@link #newStateHandler(State)} 
 * involves heavy computations, then it should be done in a different thread
 * to release the current one.  
 * 
 * @author Alban Grastien.
 * */
public interface StateListener {

	/**
	 * Indicates this component that the state of the network is modified.  
	 * 
	 * @param s the new state of the network.  
	 * */
	public void newStateHandler(State s);

	/**
	 * Indicates this component that the timed state of the network is modified.  
	 * 
	 * @param s the new state of the network.  
	 * */
	public void newStateHandler(TimedState s);
	
}
