package gui;

/**
 * A <code>NetworkChanger</code>, i.e., a network changer, 
 * is an object that is responsible in keeping a list of network listeners 
 * and giving them updates when the network is modified.  
 */
public interface NetworkChanger {
    /**
	 * Adds the specified network listener to the list of listeners 
	 * monitoring the current network of the changer.  
	 * 
	 * @param sl the network listener that wants to know 
	 * when the network is modified.  
	 */
	public void addNetworkListener(NetworkListener sl);
    
	/**
	 * Removes the network listener from the list of listeners
	 * monitoring the current network of the changer.  
	 * 
	 * @param sl the network listener that no longer wants to know 
	 * when the network is modified.  
	 */
	public void removeNetworkListener(NetworkListener sl);

    
}
