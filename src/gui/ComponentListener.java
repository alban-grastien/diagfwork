package gui;

import lang.YAMLDComponent;

/**
 * A component listener is an object that is supposed to do something 
 * when the selected {@link YAMLDComponent} of the network is modified.  
 * Typical example is a graphical component showing the current state 
 * of the component currently selected.
 * 
 * @author Alban Grastien.
 * */
public interface ComponentListener {

	/**
	 * Notifies this component listener that the selected component has changed.  
	 * 
	 * @param c the component that is now selected.  
	 * */
	public void newComponent(YAMLDComponent c);
	
}
