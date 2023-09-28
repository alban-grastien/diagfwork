package lang;

public class YAMLDAssignment 
{
	final YAMLDGenericVar _var;
	final YAMLDExpr _expr;

	public YAMLDAssignment(YAMLDGenericVar v, YAMLDExpr e)
	{
		_var = v; 
        _expr = e;
	}

	// TODO: Should return YAMLDVar?
	public YAMLDGenericVar variable() {
		return _var;
	}
	
	public YAMLDExpr expression() {
		return _expr;
	}
	
	public String toFormattedString() 
	{
		return _var.name() + " := " + _expr.toFormattedString();
	}
    
    @Override
    public String toString() {
        return _var + " := " + _expr;
    }
}
