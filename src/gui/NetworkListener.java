package gui;

import lang.Network;

/**
 * A <code>NetworkListener</code>, i.e., a network listener, 
 * is an object that should be notified when a new network 
 * is loaded in the GUI.  
 */
public interface NetworkListener {
    /**
     * Notifies this object that the specified network 
     * has been loaded by the GUI.  
     * 
     * @param n the network that was loaded.  
     */
    public void newNetwork(Network n);
}
