package lang;

import java.util.Set;

/**
 * A path is a sequence of components.  An implicit property on a path is that 
 * the same component never appears twice.    A path is immutable, meaning that 
 * it cannot be modified once it has been created.  
 * 
 * @author Alban Grastien
 * @version 1.0
 * @see ArrayPath
 * @see AbstractPath
 */
public interface Path extends Iterable<YAMLDComponent> {

	/**
	 * Returns the size of this path, i.e. the number of components in the path.  
	 * 
	 * @return the size of this path.  
	 * */
	public int size();
	
	/**
	 * Returns the first component of this path.  This is equivalent 
	 * (but more efficient) to <code>iterator().next()</code>.  
	 * 
	 * @return the first component of this path.  
	 * */
	public YAMLDComponent first();
	
	/**
	 * Returns the last component of this path.  
	 * 
	 * @return the last component of this path.  
	 * */
	public YAMLDComponent last();
	
	/**
	 * Indicates whether the specified precondition is satisfied in this path 
	 * for the specified state, i.e., on every component of the path 
	 * except the first and last ones.  <p />
	 * There is no context because, well, the context is defined by the components 
	 * of the path.  <p />
	 * Although he proposed the implementation, Alban believes 
	 * {@link #satisfied(State, YAMLDFormula)} should not be a method of {@link Path}.  
	 * 
	 * @param s the state of the network.  
	 * @param f the formula that must be satisfied by the components on the path.  
	 * @return <code>null</code> if <code>f</code> is satisfied by all components 
	 * of the path in <code>s</code>, a component that does not satisfy <code>f</code> 
	 * otherwise.  
	 * */
	public YAMLDComponent satisfied(State s, YAMLDFormula f);
	
	/**
	 * Indicates whether this path contains the specified component.  
	 * 
	 * @param c the component that is tested.  
	 * @return <code>true</code> is <code>c</code> is part of this path.  
	 * */
	public boolean contains(YAMLDComponent c);
	
	/**
	 * Returns the component at the specified position.  
	 * 
	 * @param i the position.  
	 * @return the component of the path at position <code>i</code>.  
	 * */
	public YAMLDComponent get(int i);
	
	/**
	 * Computes the set of components in this path for which the specified 
	 * formula is not trivially true.  
	 * 
	 * @param net the network on which the simplification is done.  
	 * @param f the formula that is tested for all components.  
	 * @return <code>null</code> if the formula <i>exists a path</i> <code>f</code> 
	 * is trivially false for this path, the set of components 
	 * that need to satisfy <code>f</code> 
	 * for the formula to hold on each component of this path.  
	 * */
	public Set<YAMLDComponent> simplify(Network net, YAMLDFormula f);
}
