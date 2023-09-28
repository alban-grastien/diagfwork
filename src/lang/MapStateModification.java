package lang;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.supercom.util.Pair;

/**
 * A state modification specified by a map.  
 * 
 * @author Alban Grastien
 * */
public class MapStateModification implements StateModification {

	/**
	 * The map that indicates the assignments.  
	 * */
	private final Map<YAMLDVar, YAMLDValue> _map;
	
	/**
	 * Creates a state modification defined as the specified map of assignments.  
	 * The map cannot be modified by this state modification 
	 * (or any method using it) but any modification to the specified map 
	 * will affect this state modification.  
	 * 
	 * @param m the map that indicates which variable is assigned which value.  
	 * */
	public MapStateModification(Map<? extends YAMLDVar, ? extends YAMLDValue>  m) {
		_map = Collections.unmodifiableMap(m);
	}
	
	/**
	 * Creates a state modification by copying the specified map of assignments.  
	 * 
	 * @param m the map that indicates which variable is assigned which value.  
	 * @param b a flag that is used to distinguish this constructor 
	 * from {@link #MapStateModification(Map)}; using <code>true</code> 
	 * will make the copy use a {@link HashMap} (<code>false</code> 
	 * is unspecified at the moment).  
	 * */
	public MapStateModification(Map<? extends YAMLDVar, ? extends YAMLDValue>  m, boolean b) {
		_map = Collections.unmodifiableMap(new HashMap<YAMLDVar, YAMLDValue>(m));
	}
	
	@Override
	public YAMLDValue getModifiedValue(YAMLDVar var) {
		return _map.get(var);
	}

	@Override
	public Set<YAMLDVar> modifiedVariables() {
		return _map.keySet();
	}

	@Override
	public Iterator<Pair<YAMLDVar, YAMLDValue>> iterator() {
		return new Iterator<Pair<YAMLDVar,YAMLDValue>>() {

			final Iterator<Map.Entry<YAMLDVar, YAMLDValue>> _it = 
				_map.entrySet().iterator();
			
			@Override
			public boolean hasNext() {
				return _it.hasNext();
			}

			@Override
			public Pair<YAMLDVar, YAMLDValue> next() {
				final Map.Entry<YAMLDVar, YAMLDValue> ent = _it.next();
				return new Pair<YAMLDVar, YAMLDValue>(ent.getKey(), ent.getValue());
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Impossible to remove elements.");
			}
		};
	}

}
