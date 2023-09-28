package gui;

/**
 * A <code>StateChanger</code>, i.e., a state changer, 
 * is an object that is responsible in keeping a list of state listeners 
 * and giving them updates when the state is modified.  
 */
public interface StateChanger {
    /**
	 * Adds the specified state listener to the list of listeners 
	 * monitoring the current state of the changer.  
	 * 
	 * @param sl the state listener that wants to know 
	 * when the state is modified.  
	 */
	public void addStateListener(StateListener sl);
    
	/**
	 * Removes the state listener from the list of listeners
	 * monitoring the current state of the changer.  
	 * 
	 * @param sl the state listener that no longer wants to know 
	 * when the state is modified.  
	 */
	public void removeStateListener(StateListener sl);

    
}
