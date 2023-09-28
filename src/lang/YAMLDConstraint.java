package lang;

/**
 * A constraint is a rule that defines the assignment of a dependent variable.  
 * It is defined as a tuple: precondition, variable, value.  In case the precondition 
 * is satisfied, then the variable is assigned to the value.  <p />
 * In a deterministic context, for a given state and a given variable, 
 * exactly one constraint has a satisfied precondition 
 * (or, alternatively, only the first one would be applied).  Furthermore, 
 * the precondition should be defined in a stratified manner to avoid cyclic dependencies.  
 * */
public class YAMLDConstraint {
	
	/**
	 * The precondition of this constraint.  
	 * */
	private final YAMLDFormula _prec;
	
	/**
	 * The dependent variable affected by this constraint.  
	 * */
	private final YAMLDDVar _dvar;
	
	/**
	 * The expression that defines how the dependent variable should be affected.  
	 * */
	private final YAMLDExpr _expr;
	
	/**
	 * The component for which this constraint is defined.  
	 * */
	private final YAMLDComponent _comp;
	
	/**
	 * Constructor.  Right now, it is assumed the formula has form <code>var = val</code> 
	 * or <code>NOT g OR (var = val)</code> which corresponds to a conditional 
	 * (possibly trivial) assignment of the dependent variable <code>var</code>.  
	 * 
	 * @param f the formula that defines the constraint.
	 * @param comp the component for which this constraint is defined.  
	 * */
	public YAMLDConstraint(YAMLDFormula f, YAMLDComponent comp) {
		_comp = comp;
		
		// Testing case 
		if (f instanceof YAMLDEqFormula) {
			final YAMLDEqFormula eq = (YAMLDEqFormula)f;
			final MyAss ass = extractVar(eq,comp);
			
			_prec = YAMLDTrue.TRUE;
			_dvar = ass._var;
			_expr = ass._expr;
			
			return;
		}
		
		if (f instanceof YAMLDOrFormula) {
			final YAMLDOrFormula fOR = (YAMLDOrFormula)f;
			final YAMLDFormula f1 = fOR.getOp1();
			if (f1 instanceof YAMLDNotFormula) {
				final YAMLDNotFormula notF1 = (YAMLDNotFormula)f1;
				final YAMLDFormula g = notF1.getOp();
				final YAMLDFormula f2 = fOR.getOp2();
				if (f2 instanceof YAMLDEqFormula) {
					final YAMLDEqFormula eq = (YAMLDEqFormula)f2;
					final MyAss ass = extractVar(eq,comp);
					
					_prec = g;
					_dvar = ass._var;
					_expr = ass._expr;
					
					return;
				}				
			}
		}
		
		throw new IllegalArgumentException("Impossible to build a YAMLDConstraint: "
				+ " should have form (var = val) or (NOT g OR (var = val)). Is: " 
				+ f.toFormattedString());
	}

	static class MyAss {
		public final YAMLDDVar _var;
		public final YAMLDExpr _expr;
		public MyAss(YAMLDDVar var, YAMLDExpr expr) {
			_var = var;
			_expr = expr;
		}
	}
	
	private static MyAss extractVar(YAMLDEqFormula eq, YAMLDComponent comp) {
		final YAMLDExpr eq1 = eq._lhs;
		if (!(eq1 instanceof VariableValue)) {
			System.out.println("Is not a VariableValue: " + eq1);
			return null;
		}
		final VariableValue vv = (VariableValue)eq1;
		final YAMLDGenericVar gvar = vv.variable();
		//final YAMLDGenericVar gvar = vv.variable();
		//if (gvar == null) {
		//	throw new IllegalArgumentException("Unknown variable '" + vv.get_id() 
		//			+ "' of component '" + comp.name() + "'");
		//}
		if (!(gvar instanceof YAMLDDVar)) {
			throw new IllegalArgumentException("Is not a dependent variable '" 
					+ gvar.name() + 
					"' of component '" + comp.name() + "'");
		}
		final YAMLDDVar var = (YAMLDDVar)gvar;
		final YAMLDExpr expr = eq._rhs;
		return new MyAss(var, expr);
	}
	
	/**
	 * Returns the precondition that needs to be satisfied for this constraint to apply.  
	 * 
	 * @return the precondition of this constraint.  
	 * */
	public YAMLDFormula getPrecondition() {
		return _prec;
	}
	
	/**
	 * Returns the {@link YAMLDDVar} that is assigned if this constraint is satisfied.
	 * 
	 * @return the variable affected by this constraint.  
	 * */
	public YAMLDDVar getVariable() {
		return _dvar;
	}
	
	/**
	 * Returns the value {@link #getVariable()} is assigned to 
	 * if this constraint is satisfied.  
	 * 
	 * @return the assignment value of this constraint.    
	 * */
	public YAMLDExpr getAssignment() {
		return _expr;
	}
	
	/**
	 * Returns a formatted version of this constraint.  
	 * 
	 * @return a formatted version of this constraint.  
	 * */
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		// TODO: the current implementation is temporary.
		result.append("constraint ");
		result.append("(");
		//result.append("NOT (");
		result.append(_prec.toFormattedString());
		//result.append(")");
		//result.append(" OR ");
		result.append(" => ");
		result.append(_dvar.toFormattedString());
		result.append("=");
		result.append(_expr.toFormattedString());
		result.append(")");
		result.append(";");
		
		return result.toString();
	}
	
	/**
	 * Returns the component for which this constraint is defined.  
	 * 
	 * @return the component that this constraint applies to.  
	 * */
	public YAMLDComponent getComponent() {
		return _comp;
	}
}
