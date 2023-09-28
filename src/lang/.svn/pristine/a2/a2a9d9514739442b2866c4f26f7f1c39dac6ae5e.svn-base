package lang;

public class YAMLDFalse implements YAMLDFormula
{
	public static final YAMLDFalse FALSE = new YAMLDFalse();
	
	private YAMLDFalse()
	{
	}
	
	@Override
	public String toFormattedString() 
	{
		return "FALSE";
	}

	@Override
	public boolean satisfied(State state, YAMLDComponent con) 
	{
		return false;
	}

	@Override
	public YAMLDFormula simplify(Network net) {
		return this;
	}

	@Override
	public boolean isTriviallyFalse(Network net, YAMLDComponent c) {
		return true;
	}

	@Override
	public boolean isTriviallyTrue(Network net, YAMLDComponent c) {
		return false;
	}
	
    @Override
	public String toString() {
		return toFormattedString();
	}

	@Override
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state,
			YAMLDComponent comp) {
		return THREE_VALUED_BOOL.FALSE;
	}
	
	@Override
	public int hashCode() {
		return 11;
	}

    @Override
    public void test(YAMLDComponent c, Network net) {
    }
}
