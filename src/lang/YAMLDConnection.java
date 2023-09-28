package lang;

/**
 * A connection is defined by its name, its target and its type.  
 * The source of the connection is not explicitly mentionned.  
 * */
public class YAMLDConnection 
{
	private final String _name;
	private final YAMLDConnType _type;
	private final YAMLDComponent _targetComp;
	
	public YAMLDConnection(String name, YAMLDConnType type, YAMLDComponent target)
	{
		_name = name; _type = type; _targetComp = target;
		type.addConnection(this);
	}
	
	public String name() 
	{
		return _name;
	}
	
	public YAMLDComponent target() 
	{
		return _targetComp;
	}
	
	public YAMLDConnType type() 
	{
		return _type;
	}
	
	public String toFormattedString()
	{
		String retStmt = "connection ";
		
		if (_name != null)
			retStmt += _name;

		if (_type != null)
			retStmt += " : " + _type;
		
		if (_targetComp != null)
			retStmt += " = " + _targetComp.name();
		
		return retStmt;
	}
}
