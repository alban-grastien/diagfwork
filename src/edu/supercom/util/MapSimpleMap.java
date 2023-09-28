package edu.supercom.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class encapsulates a {@link Map} to a {@link SimpleMap}.  
 * This class also contains general methods for the {@link SimpleMap} interface.  
 * */
public final class MapSimpleMap<K,V> implements SimpleMap<K,V> {

	/**
	 * The map that is encapsulates.  
	 * */
	private final Map<Object,? extends V> _map;
	
	/**
	 * Encapsulates the specified map in a simple map.  
	 * Basically, any call to the {@link SimpleMap#get(Object)} 
	 * will be forwarded to {@link Map#get(Object)}.  
	 * 
	 * @param map the map that is encapsulated.  
	 * */
	public MapSimpleMap(Map<Object, ? extends V> map) {
		_map = map;
	}
	
	@Override
	public V get(K k) {
		return _map.get(k);
	}

	/**
	 * Returns an unmodifiable copy of the specified list where the elements 
	 * are replaced by the application of the {@link #get(Object)} method.  
	 * Notice that the returned list is not explicitly computed; 
	 * in particular, were the returned list to be heavily used 
	 * and were the {@link #get(Object)} operation expensive for this map, 
	 * then the returned list should explicitly copied (for instance, 
	 * in a {@link ArrayList}) so the number of {@link #get(Object)} operations 
	 * is limited.  
	 * Furthermore, the returned list keeps a reference to the original list: 
	 * any change on the original list will affect the returned list, 
	 * and the memory used by the original list will not be released 
	 * as long as the returned list is kept.  
	 * 
	 * @param l a list of <code>K</code> objects replaced by <code>V</code> objects.  
	 * @param sm the simple map that is used to generate the new objects.  
	 * @param <K> the types of the keys.  
	 * @param <V> the types of the values.  
	 * @return the list <code>l</code> where each element <code>k</code> 
	 * is replaced by <code>sm.get(l)</code>.  
	 * */ 
	public static <K,V> List<V> mapList(final List<K> l, 
			final SimpleMap<? super K, ? extends V> sm) {
		return new AbstractList<V>() {

			@Override
			public V get(int index) {
				return sm.get(l.get(index));
			}

			@Override
			public int size() {
				return l.size();
			}
		};
	}

	/**
	 * Returns a copy of the specified unmodifiable list where the elements 
	 * are replaced by the application of the {@link #get(Object)} method.  
	 * Notice that the returned list is not explicitly computed; 
	 * in particular, were the returned list to be heavily used 
	 * and were the {@link #get(Object)} operation expensive for this map, 
	 * then the returned list should explicitly copied (for instance, 
	 * in an {@link UnmodifiableListConstructor}) 
	 * so the number of {@link #get(Object)} operations is limited.  
	 * Furthermore, the returned list keeps a reference to the original list: 
	 * the memory used by the original list will not be released 
	 * as long as the returned list is kept.  
	 * 
	 * @param l a list of <code>K</code> objects replaced by <code>V</code> objects.  
	 * @param sm the simple map that is used to generate the new objects.  
	 * @param <K> the types of the keys.  
	 * @param <V> the types of the values.  
	 * @return the list <code>l</code> where each element <code>k</code> 
	 * is replaced by <code>sm.get(l)</code>.  
	 * */ 
	public static <K,V> UnmodifiableList<V> mapList(final UnmodifiableList<K> l, 
			final SimpleMap<? super K, ? extends V> sm) {
		return new ImplicitUnmodifiableList<V>() {

			@Override
			public int size() {
				return l.size();
			}

			@Override
			public V get(int index) {
				return sm.get(l.get(index));
			}

			@Override
			public UnmodifiableListConstructor<V> makeExplicit() {
				return new UnmodifiableListConstructor<V>(this);
			}
		};
	}
	
}
