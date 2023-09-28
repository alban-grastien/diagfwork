package lang;

public class YAMLDAddExpr implements YAMLDExpr
{
	YAMLDExpr op1, op2;
	
	public YAMLDAddExpr(YAMLDExpr a, YAMLDExpr b)
	{
		op1 = a; 
		op2 = b;
	}

    @Override
	public String toFormattedString() 
	{
		return op1.toFormattedString() + " + " + op2.toFormattedString();
	}

	@Override
	public YAMLDValue value(State state, YAMLDComponent con) 
	{
	  return YAMLDValue.add(op1.value(state,con), op2.value(state,con));
	}

	@Override
	public YAMLDExpr simplify(Network net) {
		return this;
	}

	@Override
	public YAMLDValue getTrivialValue(Network net, YAMLDComponent con) {
		final YAMLDValue val1 = op1.getTrivialValue(net, con);
		if (val1 == null) {
			return null;
		}
		final YAMLDValue val2 = op2.getTrivialValue(net, con);
		if (val2 == null) {
			return null;
		}
		return YAMLDValue.add(val1, val2);
	}

	@Override
	public YAMLDValue partialValue(PartialState state, YAMLDComponent con) {
		final YAMLDValue val1 = op1.partialValue(state, con);
		if (val1 == null) {
			return null;
		}

		final YAMLDValue val2 = op2.partialValue(state, con);
		if (val2 == null) {
			return null;
		}
		
		return YAMLDValue.add(val1, val2);
	}

    @Override
    public void test(YAMLDComponent con, Network net) {
        op1.test(con,net);
        op2.test(con,net);
    }
    
    public YAMLDExpr getOp1() {
        return op1;
    }
    
    public YAMLDExpr getOp2() {
        return op2;
    }
}
