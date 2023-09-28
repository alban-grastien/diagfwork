package gui;

import sim.Simulator;

/**
 * A <code>ScenarioSelecterListener</code>, i.e., a scenario selecter listener, 
 * is an object that can be notified 
 * when the simulator currently selected is modified.  
 */
public interface ScenarioSelecterListener {
    /**
     * Notifies that the scenario in the specified simulator 
     * with the specified key is now selected.  
     * 
     * @param selectedKey the key of the scenario that is selected.  
     * @param selectedSim the simulator of the scenario that is selected.  
     */
    public void simulatorSelected(String selectedKey, Simulator selectedSim);
    
    /**
     * Indicates that the specified simulator (with the specified key) 
     * was added to the list of scenarios of the scenario selecter.  
     * 
     * @param addedKey the key of the scenario that is added.  
     * @param addedSim the simulator of the scenario that is added.  
     */
    public void simulatorAdded(String addedKey, Simulator addedSim);
    
    /**
     * Indicates that the specified simulator (with the specified key) 
     * was removed to the list of scenarios of the scenario selecter.  
     * 
     * @param removedKey the key of the scenario that is removed.  
     * @param removedSim the simulator of the scenario that is removed.  
     */
    public void simulatorRemoved(String removedKey, Simulator removedSim);
    
    /**
     * Indicates that the name of the specified simulator has been changed.  
     * 
     * 
     * @param oldKey the old key of the scenario that is changed.  
     * @param newKey the new key of the scenario that is changed.  
     * @param sim the simulator whose name is changed.  
     */
    public void simulatorKeyChanged(String oldKey, String newKey, Simulator sim);
    
}