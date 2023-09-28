package util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lang.YAMLDComponent;
import lang.YAMLDTrans;

/**
 * Implementation of a global transition as a map.  
 * 
 * @author Alban Grastien 
 * @version 1.0
 * */
@Deprecated
public class MapGlobalTransition implements GlobalTransition {
	
	/**
	 * A map that indicates what transition takes place on which component.  
	 * */
	private final Map<YAMLDComponent,YAMLDTrans> _map;
	
	/**
	 * Builds a global transition that corresponds to the application of the transitions 
	 * defined in the specified map.  
	 * 
	 * @param m a map such that the transition <code>t</code> takes place 
	 * on component <code>c</code> iff <code>m.get(c) == t</code>.  
	 * */
	public MapGlobalTransition(Map<YAMLDComponent,YAMLDTrans> m) {
		for (final Map.Entry<YAMLDComponent, YAMLDTrans> e: m.entrySet()) {
			final YAMLDComponent c = e.getKey();
			final YAMLDTrans t = e.getValue();
			if (t.getComponent() != c) {
				throw new IllegalArgumentException("Cannot apply transition " 
						+ t + " to component " + c.name());
			}
		}
		
		_map = new HashMap<YAMLDComponent, YAMLDTrans>(m);
	}

	@Override
	public YAMLDTrans getTransition(YAMLDComponent c) {
		return _map.get(c);
	}

	@Override
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		for (final Map.Entry<YAMLDComponent,YAMLDTrans> entry: _map.entrySet()) {
			result.append(entry.getKey().name())
			      .append(" -> ")
			      .append(entry.getValue().eventTrigger().name())
			      .append(";\n");
		}
		return result.toString();
	}

	@Override
	public Collection<YAMLDComponent> affectedComponents() {
		return Collections.unmodifiableCollection(_map.keySet());
	}

}
