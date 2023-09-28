package lang;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A dependent variable.  
 * */
public class YAMLDDVar extends YAMLDGenericVar
{
	/**
	 * The set of constraints that helps define this variable.  
	 * */
	private final Collection<YAMLDConstraint> _cons;

	public YAMLDDVar(String newName, YAMLDComponent comp) {
		super(newName, comp);
		
		_cons = new HashSet<YAMLDConstraint>();
	}
	
	/**
	 * Indicates this variable that the specified constraint relates to this variable.  
	 * At this stage, it is assumed a constraint is defined as an assignment 
	 * of this variable if a precondition is satisfied.  
	 * 
	 * @param c the constraint to t
	 * */
	public void addConstraint(YAMLDConstraint c) {
		assert (c.getVariable() == this);
		_cons.add(c);
	}
	
	/**
	 * Returns the collection of constraints that express how this dependent 
	 * variable is assigned.  
	 * 
	 * @return the collection of constraints on this dependent variable.  
	 * */
	public Collection<YAMLDConstraint> getConstraints() {
		return Collections.unmodifiableCollection(_cons);
	}
}