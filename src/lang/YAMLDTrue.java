package lang;

public class YAMLDTrue implements YAMLDFormula 
{
	public static final YAMLDTrue TRUE = new YAMLDTrue();
	
	private YAMLDTrue()
	{
	}
	
	@Override
	public String toFormattedString() 
	{
		return "TRUE";
	}

	@Override
	public boolean satisfied(State state, YAMLDComponent con) {
		return true;
	}

	@Override
	public YAMLDFormula simplify(Network net) {
		return this;
	}

	@Override
	public boolean isTriviallyFalse(Network net, YAMLDComponent c) {
		return false;
	}

	@Override
	public boolean isTriviallyTrue(Network net, YAMLDComponent c) {
		return true;
	}
	
    @Override
	public String toString() {
		return toFormattedString();
	}

	@Override
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state,
			YAMLDComponent comp) {
		return THREE_VALUED_BOOL.TRUE;
	}
	
	@Override
	public int hashCode() {
		return 5; 
	}

    @Override
    public void test(YAMLDComponent c, Network net) {
    }
}
