package lang;

/**
 * The expression representing an expression defined as one or two IDs.  
 * In case the YAMLDID is represented by two IDs, then one is the 
 * component name, and the other one is the variable name.  
 * In case the YAMLDID is represented by one ID, then the ID represents 
 * a variable name and the component name is defined by the context.  
 * */
public class YAMLDID implements YAMLDExpr 
{
	private String _varName;
	
	// If owner == null, then variable should
	// be considered local.
	private String _owner = null;
	
	public YAMLDID(String i)
	{
		_varName = i;
	}

	public void setOwner(String owner)
	{
		_owner = owner;
	}
	
	public String owner()
	{
		return _owner;
	}
	
	public void set_id(String _id) 
	{
		this._varName = _id;
	}

	public String get_id() 
	{
		return _varName;
	}

	@Override
	public String toFormattedString() 
	{
		if (_owner == null)
			return _varName;
		
		return _owner + "." + _varName;
	}

	/**
	 * Returns the variable corresponding to this {@link YAMLDID} 
	 * provided the specified context.  
	 * */
// 	public YAMLDGenericVar variable(YAMLDComponent con) {
// 		final YAMLDComponent comp;
// 		if (_owner == null) {
// 			comp = con;
// 		} else {
// 			comp = con.getConnectedComponent(_owner);
// 		}
// 		if (comp == null) {
// 			throw new Error("ERROR: " + this.toFormattedString());
// 		}
// 		final YAMLDGenericVar var = comp.getVariable(_varName);
// 		return var;
// 	}

        public YAMLDGenericVar variable(YAMLDComponent con, Network net) {
		YAMLDComponent comp = null;
		if (_owner == null) {
			comp = con;
		} else {
		  // first guess that _owner is an explicit component name
		  comp = net.getComponent(_owner);
		  // if not, then it should be a connection
		  if (comp == null) 
		    comp = con.getConnectedComponent(_owner);
		}
		// if it's neither, we have an error
		if (comp == null) {
			throw new Error("ERROR: " + this.toFormattedString() + " for component " + con);
		}
		final YAMLDGenericVar var = comp.getVariable(_varName);
		return var;
	}
	
	@Override
	public YAMLDValue value(State state, YAMLDComponent con) {
	  return state.getValue(variable(con, state.getNetwork()));
	}

	@Override
	public YAMLDExpr simplify(Network net) {
		if (_owner != null) {
			final YAMLDComponent comp = net.getComponent(_owner);
			final YAMLDGenericVar var = comp.getVariable(_varName);
			return new VariableValue(var);
		}
		
		return this;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_owner == null) ? 0 : _owner.hashCode());
		result = prime * result
				+ ((_varName == null) ? 0 : _varName.hashCode());
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
		if (!(obj instanceof YAMLDID)) {
			return false;
		}
		YAMLDID other = (YAMLDID) obj;
		if (_owner == null) {
			if (other._owner != null) {
				return false;
			}
		} else if (!_owner.equals(other._owner)) {
			return false;
		}
		if (_varName == null) {
			if (other._varName != null) {
				return false;
			}
		} else if (!_varName.equals(other._varName)) {
			return false;
		}
		return true;
	}

	@Override
	public YAMLDValue getTrivialValue(Network net, YAMLDComponent con) {
	        final YAMLDGenericVar var = variable(con, net);
		return VariableValue.getTrivialValue(var, net, con);
	}

	@Override
	public YAMLDValue partialValue(PartialState state, YAMLDComponent con) {
		return state.getPartialValue(variable(con, state.getNetwork()));
	}

    @Override 
    public void test(YAMLDComponent con, Network net) {
        variable(con, net);
    }

}
