package lang;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * An explicit path is an implementation of a path that represents the path 
 * as an array of components.
 * 
 * @author Alban Grastien
 * @version 1.0
 * */
public class ArrayPath implements Path {

	/**
	 * The array of components.  
	 * */
	private final YAMLDComponent[] _comps;
	/**
	 * A set representation of the components (useful to test inclusion).  
	 * */
	private final Set<YAMLDComponent> _set;
	
	/**
	 * Builds an array path from the specified collection of components.  
	 * It is assumed that the collection indeed implements a path (i.e., 
	 * the components form a path and each component appears at most once).  
	 * 
	 * @param comps the collection of components that form the path.   
	 * */
	public ArrayPath(Collection<YAMLDComponent> comps) {
		_comps = new YAMLDComponent[comps.size()];
		int i=0;
		for (final YAMLDComponent comp: comps) {
			_comps[i] = comp;
			i++;
		}
		_set = new HashSet<YAMLDComponent>(comps);
	}
	
	/**
	 * Builds an array path from the specified array of components.  
	 * It is assumed that the array indeed implements a path (i.e., 
	 * the components form a path and each component appears at most once).  
	 * The array is copied by the constructor.    
	 * 
	 * @param comps the array of components that form the path.   
	 * */
	public ArrayPath(YAMLDComponent[] comps) {
		_comps = new YAMLDComponent[comps.length];
		_set = new HashSet<YAMLDComponent>();
		int i=0;
		for (final YAMLDComponent comp: comps) {
			_comps[i] = comp;
			i++;
			_set.add(comp);
		}
	}
	
	
	@Override
	public Iterator<YAMLDComponent> iterator() {
		return new Iterator<YAMLDComponent>() {
			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < _comps.length;
			}

			@Override
			public YAMLDComponent next() {
				final YAMLDComponent result = _comps[i];
				i++;
				return result;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Cannot remove a component from a path.");
			}
		};
	}
	
	@Override 
	public int size() {
		return _comps.length;
	}

	@Override
	public YAMLDComponent satisfied(State s, YAMLDFormula f) {
		return satisfied(this, s, f);
	}

	@Override
	public YAMLDComponent first() {
		return _comps[0];
	}

	@Override
	public YAMLDComponent last() {
		return _comps[_comps.length-1];
	}
	
	public static YAMLDComponent satisfied(Path p, State s, YAMLDFormula f) {
		final Iterator<YAMLDComponent> it = p.iterator();
		it.next(); // Gets rid of the first element.  
		int nb = p.size()-2; // Not the first, not the last.
		while (nb > 0) {
			final YAMLDComponent comp = it.next();
			if (!f.satisfied(s, comp)) {
				return comp;
			}
			nb--;
		}
		return null;
	}

	@Override
	public boolean contains(YAMLDComponent c) {
		return _set.contains(c);
	}
	
	@Override 
	public String toString() {
		final StringBuilder buf = new StringBuilder();
		
		{
			final int last = _comps.length-1;
			for (int i=0 ; i<= last ; i++) {
				buf.append(_comps[i].name());
				if (i != last) {
					buf.append(" -> ");
				}
			}
		}
		
		return buf.toString();
	}
	
	@Override 
	public int hashCode() {
		int result = 0;
		
		for (final YAMLDComponent comp: _comps) {
			result = (result * 31) + comp.hashCode();
		}
		
		return result;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof Path)) {
			return false;
		}
		
		final Path p = (Path)obj;
		if (size() != p.size()) {
			return false;
		}
		
		for (int i=0 ; i<size() ; i++) {
			if (!_comps[i].equals(p.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public YAMLDComponent get(int i) {
		return _comps[i];
	}

	@Override
	public Set<YAMLDComponent> simplify(Network net, YAMLDFormula f) {
		// The list of components to remove
		final Set<YAMLDComponent> comps = new HashSet<YAMLDComponent>();
		comps.add(first());
		comps.add(last());
		
		for (final YAMLDComponent comp: this) {
			if (comps.contains(comp)) {
				continue;
			}
			if (f.isTriviallyTrue(net, comp)) {
				comps.add(comp);
			} else if (f.isTriviallyFalse(net, comp)) {
				return null; // Return no path since the formula is false anyway.  
			}
		}
		//if (comps.isEmpty()) {
		//	return this;
		//}
		
		final Set<YAMLDComponent> result = new HashSet<YAMLDComponent>(_set);
		result.removeAll(comps);
		return result;
	}
}
