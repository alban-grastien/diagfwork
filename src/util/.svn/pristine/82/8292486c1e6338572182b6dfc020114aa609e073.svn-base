package util;

import java.util.Collection;

import lang.MMLDTransition;
import lang.YAMLDComponent;
import lang.YAMLDTrans;

/**
 * A global transition is a transition defined on the whole system.  
 * It is defined as a bunch of simultaneous (synchronised) component transitions.
 * 
 * @author Alban Grastien 
 * @version 1.0 
 * @deprecated Use {@link MMLDTransition} instead 
 * */
@Deprecated
public interface GlobalTransition {

	/**
	 * Returns the transition that takes place in the specified component.  
	 * 
	 * @param c the component we are interested in.  
	 * @return the transition that is taken by component <code>c</code> if any, 
	 * <code>null</code> otherwise.  Note that the dependent state of <code>c</code> 
	 * can be modified even if <code>getTransition(c) == null</code>.  
	 * */
	public YAMLDTrans getTransition(YAMLDComponent c);
	
	/**
	 * Returns the list of components on which a transition takes place.  
	 * This method should return exactly the list of 
	 * {@link YAMLDComponent} <code>c</code> 
	 * such <code>getTransition(c) != null</code>.
	 * 
	 * @return the components affected by this global transition.  
	 * */
	public Collection<YAMLDComponent> affectedComponents();
	
	public String toFormattedString();
}
