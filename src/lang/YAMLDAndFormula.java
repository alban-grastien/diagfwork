package lang;

public class YAMLDAndFormula implements YAMLDFormula
{

	private YAMLDFormula op1, op2;

	public YAMLDAndFormula(YAMLDFormula op1, YAMLDFormula op2)
	{
		this.op1 = op1; this.op2 = op2;
	}
	
	public void setOp2(YAMLDFormula op2) 
	{
		this.op2 = op2;
	}

	public YAMLDFormula getOp2() 
	{
		return op2;
	}

	public void setOp1(YAMLDFormula op1) 
	{
		this.op1 = op1;
	}

	public YAMLDFormula getOp1() 
	{
		return op1;
	}

	@Override
	public String toFormattedString() 
	{
		return op1.toFormattedString() + " AND " + op2.toFormattedString();
	}

	@Override
	public boolean satisfied(State state, YAMLDComponent con) {
		final boolean result = op1.satisfied(state,con) && op2.satisfied(state,con);
		return result;
	}

	@Override
	public YAMLDFormula simplify(Network net) {
		final YAMLDFormula f1 = op1.simplify(net);
		final YAMLDFormula f2 = op2.simplify(net);
		if (f1 == op1 && f2 == op2) {
			return this;
		}
		return new YAMLDAndFormula(f1, f2);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((op1 == null) ? 0 : op1.hashCode());
		result = prime * result + ((op2 == null) ? 0 : op2.hashCode());
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
		if (!(obj instanceof YAMLDAndFormula)) {
			return false;
		}
		YAMLDAndFormula other = (YAMLDAndFormula) obj;
		if (op1 == null) {
			if (other.op1 != null) {
				return false;
			}
		} else if (!op1.equals(other.op1)) {
			return false;
		}
		if (op2 == null) {
			if (other.op2 != null) {
				return false;
			}
		} else if (!op2.equals(other.op2)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isTriviallyFalse(Network net, YAMLDComponent c) {
		return op1.isTriviallyFalse(net, c) || op2.isTriviallyFalse(net, c);
	}

	@Override
	public boolean isTriviallyTrue(Network net, YAMLDComponent c) {
		return op1.isTriviallyTrue(net, c) && op2.isTriviallyTrue(net, c);
	}
	
	public String toString() {
		return toFormattedString();
	}

	@Override
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state,
			YAMLDComponent comp) {
		final THREE_VALUED_BOOL val1 = op1.partiallySatisfied(state, comp);
		
		if (val1 == THREE_VALUED_BOOL.FALSE) {
			return THREE_VALUED_BOOL.FALSE;
		}
		
		final THREE_VALUED_BOOL val2 = op2.partiallySatisfied(state, comp);
		
		if (val2 == THREE_VALUED_BOOL.FALSE) {
			return THREE_VALUED_BOOL.FALSE;
		}
		
		if (val1 == THREE_VALUED_BOOL.UNDEFINED || val2 == THREE_VALUED_BOOL.UNDEFINED) {
			return THREE_VALUED_BOOL.UNDEFINED;
		}
		
		return THREE_VALUED_BOOL.TRUE;
	}

    @Override
    public void test(YAMLDComponent c, Network net) {
        op1.test(c,net);
        op2.test(c,net);
    }
	
}
