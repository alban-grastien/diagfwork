package lang;

/**
 * A YAMLDExpression is an object that represents a value.  
 * An expression is usually defined for a component (the context).  
 * For instance, if the expression is "the variable of name n" and the context is "c", 
 * then the value of the expression is the value of the variable  in component c 
 * that has name c.  
 * */
public interface YAMLDExpr // extends YAMLDFormula 
{
	public String toFormattedString();
	
	/**
	 * Evaluates this expression in the specified state for the specified context.  
	 * 
	 * @param state the state of the network.  
	 * @param con the context on which this expression is defined.  
	 * */
    public YAMLDValue value(State state, YAMLDComponent con);
	
	/**
	 * Evaluates, if possible, this expression in the specified partial state 
	 * for the specified context.  
	 * 
	 * @param state the partial state of the network.  
	 * @param con the context on which this expression is defined.
	 * @return the value of this expression 
	 * if it can be computed in the specified partial state, 
	 * <code>null</code> otherwise.  
	 * */
    public YAMLDValue partialValue(PartialState state, YAMLDComponent con);
	
	/**
	 * Applies some operations to simplify this expression.  
	 * A typical operation is to link a reference to a {@link YAMLDGenericVar} 
	 * (for instance "my_comp.my_var") statically with the actual variable.  
	 * 
	 * @param net the network.  
	 * @return a simplified version of this expression.  In case the expression 
	 * is the same, returns <code>this</code>.  
	 * */
	public YAMLDExpr simplify(Network net);
	
	/**
	 * Returns the trivial value of this expression in the specified context, 
	 * if any.  The trivial value is defined as the value that expression always 
	 * evaluates to regardless of the state of the system.  In many cases, 
	 * the value of the expression differs depending on the system state 
	 * and this method will therefore return <code>null</code>.  
	 * 
	 * @param net the network on which this expression is evaluated.  
	 * @param con the context on which this expression is defined.  
	 * @return the value that {@link #value(State, YAMLDComponent)} will always return 
	 * regardless of the specified state if existing, <code>null</code> otherwise.  
	 * */
	public YAMLDValue getTrivialValue(Network net, YAMLDComponent con);
    
    /**
     * Tests whether the expression is sensible for the specified component.  
     * A reason why this could not be the case 
     * is if the expression refers to a variable 
     * that is not defined for the specified component.  
     * 
     * @param c the component for which this expression is checked.  
     * @param net the network on which this expression is checked.  
     * @throws IllegalArgumentException if this expression 
     * is not sensible for component <code>c</code>.  
     */
    public void test(YAMLDComponent con, Network net);
}
