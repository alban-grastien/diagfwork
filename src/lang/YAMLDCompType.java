package lang;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * A type of component.  At this level, the type does not care about hierarchy: 
 * that a type <code>t1</code> specialises a type <code>t2</code> is not modelled here.  
 * A type knows all the component of this type.   
 * 
 * @author Alban Grastien
 * @version 1.0
 * @deprecated Not necessary after the frontend.  I leave the code here because we may change our minds. 
 * */
public class YAMLDCompType {

	/**
	 * The name of the type.  It can also be accessed by {@link #getName()}.    
	 * */
	public final String _name;
	
	/**
	 * The list of component of this type.  
	 * */
	private final Collection<YAMLDComponent> _comps;
	
	/**
	 * The static list of types.  
	 * */
	private static final Map<String,YAMLDCompType> _types = new HashMap<String, YAMLDCompType>();
	
	/**
	 * Creates a type with specified name.  This constructor should not be 
	 * called directly but only by {@link #getType(String)} or 
	 * {@link #createType(String)}.      
	 * 
	 * @param name the name of the type.  
	 * */
	private YAMLDCompType(String name) {
		_name = name;  
		_comps = new HashSet<YAMLDComponent>();
		
		final YAMLDCompType old = _types.put(name, this);
		if (old != null ) {
			_types.put(name, old);
			throw new IllegalArgumentException("Cannot create component type '" 
					+ name + "'.  Already exists.");
		}
	}
	
	/**
	 * Creates the {@link YAMLDCompType} of specified name.  If the type already exists, 
	 * an {@link IllegalArgumentException} is thrown.  
	 * 
	 * @param name the name of the type.  
	 * @return the type of specified name.  
	 * */
	public static YAMLDCompType createType(String name) {
		return new YAMLDCompType(name);
	}
	
	/**
	 * Indicates whether the {@link YAMLDCompType} of specified name exists by returning it.    
	 * 
	 * @param name the name of the component type.  
	 * @return the type of specified name if existing, <code>null</code> otherwise.  
	 * */
	public static YAMLDCompType existsType(String name) {
		return _types.get(name);
	}
	
	/**
	 * Returns the {@link YAMLDCompType} of specified name if existing.  
	 * In case this type does not exist, an {@link IllegalArgumentException} 
	 * is thrown; see {@link #getType(String)} for a method that handle this
	 * case more gently.  
	 * 
	 *  @param name the name of the type.  
	 *  @return the type of specified name.  
	 * */
	public static YAMLDCompType surelyGetType(String name) {
		final YAMLDCompType result = _types.get(name);
		if (result != null) {
			return result;
		}
		throw new IllegalArgumentException("Unknown component type " + name);
	}
	
	/**
	 * Returns the {@link YAMLDCompType} of specified name.  In case this type 
	 * does not exist, it is created.  See {@link #surelyGetType(String)} 
	 * for an implementation where the type is not created.  
	 * 
	 * @param name the name of the type.  
	 * @return type the type of name <code>name</code>.  
	 * */
	public static YAMLDCompType getType(String name) {
		{
			final YAMLDCompType result = _types.get(name);
			if (result != null) {
				return result;
			}
		}
		
		final YAMLDCompType result = new YAMLDCompType(name);
		return result;
	}
	
	/**
	 * Returns the name of the component type.  
	 * 
	 * @return the name of the component type.  
	 * */
	public String getName() {
		return _name;
	}
	
	/**
	 * Adds a component to the list of components of this type.  
	 * 
	 * @param comp the component that has this type.  
	 * @return <code>true</code> if the component was successfully added 
	 * (usually not the case if the component has already been specified 
	 * as from this type).  
	 * */
	public boolean add(YAMLDComponent comp) {
		return _comps.add(comp);
	}
	
	/**
	 * Returns the collection of components of this type.  The returned 
	 * collection cannot be modified externally, however note that 
	 * adding a component to this type through {@link #add(YAMLDComponent)} 
	 * may modify the collection (which can lead to exceptions when iterating 
	 * on the collection).  
	 * 
	 * @return the list of components of this type.  
	 * */
	public Collection<YAMLDComponent> getComponents() {
		return Collections.unmodifiableCollection(_comps);
	}
	
	@Override 
	public String toString () {
		return _name;
	}
	
}
