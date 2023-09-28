package lang;

import java.util.ArrayList;
import java.util.List;

/**
 * The YAMLDTempConnections is a structure that keeps track of the connections 
 * during the parsing.  The real {@link YAMLDConnection}s are constructed only 
 * after the parsing is done, so that all components are defined.  
 * 
 * @author Alban Grastien
 * @version 1.0
 * */
public class YAMLDTempConnections {

	// The following lists represent the connections.  
	// The <i>i</i>th connection is represented by taking the <i>i</i>th value 
	// of each list.  
	
	/**
	 * The source component (the one that is connected).  
	 * */
	private final List<YAMLDComponent> _sources;
	
	/**
	 * The target component (the one the source is connected to).  
	 * */
	private final List<String> _targets;
	
	/**
	 * The name of the connection.  
	 * */
	private final List<String> _names;
	
	/**
	 * The type of connection.  
	 * */
	private final List<YAMLDConnType> _types;
	
	/**
	 * Creates a object that stores all the connections.
	 * */
	public YAMLDTempConnections() {
		_sources = new ArrayList<YAMLDComponent>();
		_targets = new ArrayList<String>();
		_names = new ArrayList<String>();
		_types = new ArrayList<YAMLDConnType>();
	}
	
	/**
	 * Stores the specified connection.  
	 * 
	 * @param s the source of the connection.  
	 * @param t the target of the connection.  
	 * @param n the name of the connection.  
	 * @param ty the type of connection.  
	 * */
	public void add(YAMLDComponent s, String t, String n, String ty) {
		_sources.add(s);
		_targets.add(t);
		_names.add(n);
		final YAMLDConnType type = YAMLDConnType.getType(ty);
		_types.add(type);
	}
	
	/**
	 * Saves the connections in the components.  
	 * 
	 * @param net the network of components.  
	 * */
	public void copyConnections(Network net) {
		for (int i=0 ; i<_sources.size() ; i++) {
			final YAMLDComponent s = _sources.get(i);
			final YAMLDComponent t = net.getComponent(_targets.get(i));
			final String n = _names.get(i);
			final YAMLDConnType ty = _types.get(i);
			s.addConn(new YAMLDConnection(n, ty, t));
		}

		_sources.clear();
		_targets.clear();
		_names.clear();
		_types.clear();
	}
	
}
