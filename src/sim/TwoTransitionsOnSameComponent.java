package sim;

import lang.YAMLDComponent;

/**
 * <b>TwoTransitionsOnSameComponent</b> is an exception that is generated 
 * where unfolding the effects of a transition triggering leads to two transitions 
 * being triggered on the same component.  This is not a mistake 
 * from how the consequences of the transition triggering have been decided; 
 * the error lies in the model.  
 * */
public class TwoTransitionsOnSameComponent extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The Partial MMLD Global Transition that serves as an explanation.  
	 * */
	public final PartialMMLDGlobalTransition _part;
	/**
	 * The component on which the transition triggers twice.  
	 * */
	public final YAMLDComponent _comp;
	
	public TwoTransitionsOnSameComponent(PartialMMLDGlobalTransition part, YAMLDComponent comp) {
		super("Two events on component " + comp.name());
		_part = part;
		_comp = comp;
	}
}
