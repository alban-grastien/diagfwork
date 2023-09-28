package lang;

/**
 * This class is deprecated.  {@link YAMLDValue} should be used instead.  
 * */
@Deprecated
public class YAMLDNum implements YAMLDExpr 
{
	private YAMLDValue _num;
	
	public YAMLDNum(Integer i)
	{
		set_num(i);
	}

	public void set_num(int _num) 
	{
		this._num = YAMLDValue.getValue(_num);
	}

	public String toFormattedString() 
	{
		return String.valueOf(_num);
	}

	@Override
	public YAMLDValue value(State state, YAMLDComponent con) {
		return _num;
	}

	@Override
	public YAMLDExpr simplify(Network net) {
		return this;
	}

	@Override
	public YAMLDValue getTrivialValue(Network net, YAMLDComponent con) {
		return _num;
	}

	@Override
	public YAMLDValue partialValue(PartialState state, YAMLDComponent con) {
		return _num;
	}

    @Override
    public void test(YAMLDComponent con, Network net) {
    }
}
