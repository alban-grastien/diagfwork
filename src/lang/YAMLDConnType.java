package lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YAMLDConnType {

	/**
	 * The name of the type.  It can also be accessed by {@link #getName()}.    
	 * */
	public final String _name;
	
	/**
	 * Contains the list of connections of this type.  
	 * */
	private final Collection<YAMLDConnection> _conn;
	
	/**
	 * The static list of types.  
	 * */
	private static final Map<String,YAMLDConnType> _types = new HashMap<String, YAMLDConnType>();
	
	/**
	 * Creates a type with specified name.  This constructor should not be 
	 * called directly but only by {@link #getType(String)} or 
	 * {@link #createType(String)}.      
	 * 
	 * @param name the name of the type.  
	 * */
	private YAMLDConnType(String name) {
		_name = name;
		_conn = new ArrayList<YAMLDConnection>();
		
		final YAMLDConnType old = _types.put(name, this);
		if (old != null ) {
			_types.put(name, old);
			throw new IllegalArgumentException("Cannot create connection type '" 
					+ name + "'.  Already exists.");
		}
	}
	
	/**
	 * Creates the {@link YAMLDConnType} of specified name.  If the type already exists, 
	 * an {@link IllegalArgumentException} is thrown.  
	 * 
	 * @param name the name of the type.  
	 * @return the type of specified name.  
	 * */
	public static YAMLDConnType createType(String name) {
		return new YAMLDConnType(name);
	}
	
	/**
	 * Indicates whether the {@link YAMLDConnType} of specified name exists by returning it.    
	 * 
	 * @param name the name of the component type.  
	 * @return the type of specified name if existing, <code>null</code> otherwise.  
	 * */
	public static YAMLDConnType existsType(String name) {
		return _types.get(name);
	}
	
	/**
	 * Returns the {@link YAMLDConnType} of specified name if existing.  
	 * In case this type does not exist, an {@link IllegalArgumentException} 
	 * is thrown; see {@link #getType(String)} for a method that handle this
	 * case more gently.  
	 * 
	 *  @param name the name of the type.  
	 *  @return the type of specified name.  
	 * */
	public static YAMLDConnType surelyGetType(String name) {
		final YAMLDConnType result = _types.get(name);
		if (result != null) {
			return result;
		}
		throw new IllegalArgumentException("Unknown component type " + name);
	}
	
	/**
	 * Returns the {@link YAMLDConnType} of specified name.  In case this type 
	 * does not exist, it is created.  See {@link #surelyGetType(String)} 
	 * for an implementation where the type is not created.  
	 * 
	 * @param name the name of the type.  
	 * @return type the type of name <code>name</code>.  
	 * */
	public static YAMLDConnType getType(String name) {
		{
			final YAMLDConnType result = _types.get(name);
			if (result != null) {
				return result;
			}
		}
		
		final YAMLDConnType result = new YAMLDConnType(name);
		return result;
	}
	
	/**
	 * Indicates to this type that the specified connection is of the same type.  
	 * 
	 * @param con the connection that is of this type.  
	 * */
	public void addConnection(YAMLDConnection con) {
		assert this == con.type();
		
		_conn.add(con);
	}
	
	/**
	 * Returns the list of connections of this type.  
	 * 
	 * @return the list of connections of this type.  
	 * */
	public Collection<YAMLDConnection> getConnections() {
		return Collections.unmodifiableCollection(_conn);
	}
	
	/**
	 * Returns the name of the component type.  
	 * 
	 * @return the name of the component type.  
	 * */
	public String getName() {
		return _name;
	}
	
	@Override 
	public String toString () {
		return _name;
	}

}
