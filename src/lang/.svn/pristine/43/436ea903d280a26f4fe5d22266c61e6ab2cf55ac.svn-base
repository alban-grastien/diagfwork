package lang;

/**
 * A {@link YAMLDExpr} that corresponds to a specific variable.  
 * 
 * @author Alban Grastien
 * @version 1.0
 * */
public class VariableValue implements YAMLDExpr {
	
	/**
	 * The variable.  
	 * */
	private final YAMLDGenericVar _var;
	
	/**
	 * Builds an expression that corresponds to the value 
	 * of the specified variable.
	 * 
	 * @param var the variable this expression corresponds to.  
	 * */
	public VariableValue(YAMLDGenericVar var) {
        if (var == null) {
            throw new NullPointerException();
        }
		_var = var;
	}

	@Override
	public YAMLDExpr simplify(Network net) {
		return this;
	}

	@Override
	public String toFormattedString() {
		return _var.toFormattedString();
	}

	@Override
	public YAMLDValue value(State state, YAMLDComponent con) {
	        return state.getValue(_var);
	}
	
	/**
	 * Returns the variable this expression corresponds to.  
	 * 
	 * @return the variable of this expression.  
	 * */
	public YAMLDGenericVar variable() {
		return _var;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_var == null) ? 0 : _var.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VariableValue)) {
			return false;
		}
		VariableValue other = (VariableValue) obj;
		if (_var == null) {
			if (other._var != null) {
				return false;
			}
		} else if (!_var.equals(other._var)) {
			return false;
		}
		return true;
	}

	@Override
	public YAMLDValue getTrivialValue(Network net, YAMLDComponent con) {
		return getTrivialValue(_var, net, con);
	}

	public static YAMLDValue getTrivialValue(YAMLDGenericVar var, Network net, YAMLDComponent con) {
		if (var instanceof YAMLDDVar) {
			final YAMLDDVar dvar = (YAMLDDVar)var;
			// Determine if there is trivially true precondition for the first constraint on dvar.  
			final YAMLDConstraint cons = dvar.getConstraints().iterator().next();
			if (cons.getPrecondition().isTriviallyTrue(net, con)) {
				return cons.getAssignment().getTrivialValue(net, con);
			}
			
			return null;
		}
		
		return null;
	}

	@Override
	public YAMLDValue partialValue(PartialState state, YAMLDComponent con) {
		return state.getPartialValue(_var);
	}

    @Override
    public void test(YAMLDComponent con, Network net) {
        // ok
    }
}
