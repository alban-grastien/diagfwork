package lang;

public class YAMLDNotFormula implements YAMLDFormula
{
	private YAMLDFormula op;

	public YAMLDNotFormula(YAMLDFormula yamldFormula) {
		op = yamldFormula;
	}

	public void setOp(YAMLDFormula op) 
	{
		this.op = op;
	}

	public YAMLDFormula getOp() 
	{
		return op;
	}
	
	@Override
	public String toFormattedString() 
	{
		return "not " + op.toFormattedString();
	}
	
	public void setLHS(YAMLDFormula f) {
	}

	@Override
	public boolean satisfied(State state, YAMLDComponent con) {
		return !op.satisfied(state,con);
	}

	@Override
	public YAMLDFormula simplify(Network net) {
		final YAMLDFormula f = op.simplify(net);
		if (f == op) {
			return this;
		}
		
		return new YAMLDNotFormula(f);
	}

	@Override
	public boolean isTriviallyFalse(Network net, YAMLDComponent c) {
		return op.isTriviallyTrue(net, c);
	}

	@Override
	public boolean isTriviallyTrue(Network net, YAMLDComponent c) {
		return op.isTriviallyFalse(net, c);
	}

	@Override
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state,
			YAMLDComponent comp) {
		final THREE_VALUED_BOOL neg = op.partiallySatisfied(state, comp);
		if (neg == THREE_VALUED_BOOL.TRUE) {
			return THREE_VALUED_BOOL.FALSE;
		}
		if (neg == THREE_VALUED_BOOL.FALSE) {
			return THREE_VALUED_BOOL.TRUE;
		}
		return THREE_VALUED_BOOL.UNDEFINED;
	}
	
	@Override
	public int hashCode() {
		return 31*op.hashCode();
	}

    @Override
    public void test(YAMLDComponent c, Network net) {
        op.test(c, net);
    }
}
