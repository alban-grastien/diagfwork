package lang;

/**
 * A YAMLDFormula is a condition on the state of the system.  A YAMLDFormula 
 * is usually defined with respect to a <i>context</i>, i.e. the component 
 * for which the formula is defined.  
 * 
 * @author Jussi Rintanen
 * @author Andreas Bauer
 * @author Alban Grastien
 * */
public interface YAMLDFormula 
{
	/**
	 * Indicates whether this formula is satisfied in the specified state 
	 * for the specified context.  The context indicates where the formula is 
	 * defined; this is useful in the case the context is defined dynamically, 
	 * for instance when this formula is a subformula of a path quantification.  
	 * The context is not intended to represent the current component, for instance 
	 * if the formula is a precondition of a particular component: in this case, 
	 * the component is known statically and the resolution of which component 
	 * is being referred to should be done off-line.  
	 * 
	 * @param state the state on which this formula is tested.  
	 * @param con the context on which this formula is tested.  <code>null</code> 
	 * if there is no context.  
	 * @return <code>true</code> if the state satisfies this formula, 
	 * <code>false</code> otherwise.  
	 * */
        public boolean satisfied(State state, YAMLDComponent con);
	
	String toFormattedString();
	
	/**
	 * Applies some operations to simplify this formula.  
	 * A typical operation is to link a reference to a {@link YAMLDGenericVar} 
	 * (for instance "my_comp.my_var") statically with the actual variable.  
	 * 
	 * @param net the network.  
	 * @return a simplified version of this formula.  In case the formula is the same, 
	 * returns <code>this</code>.  
	 * */
	public YAMLDFormula simplify(Network net);
	
	/**
	 * Indicates whether this formula is trivially true in the system 
	 * whatever the state of the system is.  This method is only used to prune 
	 * some of the search space.  It is therefore ok to return <code>false</code> 
	 * while the correct answer should be <code>true</code>.  
	 * 
	 * @param net the network on which the formula is tested.  
	 * @param c the context of this formula.  
	 * @return <code>true</code> if {@link #satisfied(State, YAMLDComponent)} 
	 * will allows evaluate to <code>true</code> on the specified context.  
	 * */
	public boolean isTriviallyTrue(Network net, YAMLDComponent c);
	
	/**
	 * Indicates whether this formula is trivially false in the system 
	 * whatever the state of the system is.  This method is only used to prune 
	 * some of the search space.  It is therefore ok to return <code>false</code> 
	 * while the correct answer should be <code>true</code>.  
	 * 
	 * @param net the network on which the formula is tested.  
	 * @param c the context of this formula.  
	 * @return <code>true</code> if {@link #satisfied(State, YAMLDComponent)} 
	 * will allows evaluate to <code>false</code> on the specified context.  
	 * */
	public boolean isTriviallyFalse(Network net, YAMLDComponent c);
	
	enum THREE_VALUED_BOOL {TRUE,FALSE,UNDEFINED};
	
	/**
	 * Estimates the value of this formula applied to the specified component 
	 * in the specified partial state.  
	 * Because the state is only partially defined, 
	 * the result can be <code>true</code>, <code>false</code> 
	 * or <code>undefined</code>.  
	 * The implementation may be defensive, i.e., return <code>undefined</code> 
	 * although the result is trivial; 
	 * for instance, the formula <code>(v == val1) AND (v == val2)</code>, 
	 * which is trivially <i>false</i> as <code>v</code> cannot be assigned 
	 * to two different values, 
	 * will probably be evaluated to <code>undefined</code>.  
	 * 
	 * @param state the partial state on which this formula is evaluated.  
	 * @param comp the component for which this formula is defined.  
	 * @return <code>THREE_VALUED_BOOL.TRUE</code>, 
	 * <code>THREE_VALUED_BOOL.FALSE</code> 
	 * or <code>THREE_VALUED_BOOL.UNDETERMINED</code>, 
	 * depending on whether the formula was proved true, false 
	 * or neither in any state that extends <code>state</code>.  
	 * */
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state, YAMLDComponent comp);
    
    /**
     * Verifies that this formula is sensible for the specified component.  
     * A reason why it would not be the case 
     * is if the formula refers to a variable 
     * that is not defined for the specified component.  
     * 
     * @param c the component for which this formula is verified. 
     * @param net the network on which this formula is verified.  
     * @throws IllegalArgumentException if this formula 
     * is not sensible for component <code>c</code>.  
     */
    public void test(YAMLDComponent c, Network net);
}
