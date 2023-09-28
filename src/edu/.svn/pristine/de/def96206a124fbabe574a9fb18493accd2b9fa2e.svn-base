/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * An unmodifiable map constructor is an object that is able to build an
 * unmodifiable map.  The construction is done as follows: first the user calls
 * {@link #Constructor()} (or a different constructor with, e.g., an already
 * defined map of elements); second, the user can put and remove elements, and 
 * even access the collection that is not unmodifiable yet; last, the user
 * freezes the map (method {@link #getMap()}) which automatically becomes 
 * unmodifiable (the constructor is then useless).<p />
 * The main advantage of this class is that it is not necessary to store the 
 * elements in a map and later copy them: they can be stored in an unmodifiable
 * map from the beginning.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <K> the class of keys in the map constructed.
 * @param <V> the class of values in the map constructed.
 * @todo an addMapping and a removeMapping methods that do not test whether the
 * element was in effect added or removed.
 */
public class UnmodifiableMapConstructor<K,V> implements Map<K,V> {

    /**
     * The unmodifiable map that is being constructed.  
     */
    private final UnmodifiableMap<K,V> _result;

    /**
     * A flag that indicates whether the unmodifiable map has been accessed.
     * If it has, then the map can no longer be modified.
     */
    private boolean _flag = false;

    /**
     * The map that is contained in _result.  This map (and therefore result)
     * can be modified as long as _flag is false.  
     */
    private final Map<K,V> _map;

    /**
     * Builds a constructor for an empty map.  
     */
    @SuppressWarnings("unchecked")
    public UnmodifiableMapConstructor() {
        this((Map<K,V>)Collections.emptyMap());
    }

    /**
     * Builds a constructor for an already specified map.
     *
     * @param map the original map.  
     */
    public UnmodifiableMapConstructor(Map<? extends K,? extends V> map) {
        _map = new HashMap<K, V>(map);
        _result = new UMCM<K, V>(_map);
    }

    /**
     * Returns the unmodifiable map that is being constructed.  After this
     * method is being called, the other methods of this constructor cannot be
     * applied.
     *
     * @return the unmodifiable map.
     */
    public UnmodifiableMap<K,V> getMap() {
        _flag = true;
        return _result;
    }

    /**
     * Adds the specified mapping in the map being constructed.  If the map has
     * already been accessed, the method throws an exception.
     *
     * @param k the key of the mapping.
     * @param v the value associated with the mapping.
     * @return true if the object was succesfully added.
     */
    public boolean addMapping(K k, V v) {
        if (_flag) {
            throw new IllegalStateException("Cannot add a mapping: unmodifiable map already accessed,");
        }
        final V old = _map.put(k, v);
        if (old == null) {
            return v != null;
        }
        return !old.equals(v); // If they are equal, then nothing was changed.
    }

    /**
     * Removes the specified mapping in the map being constructed.  If the map
     * has already been accessed, the method throws an exception.
     *
     * @param k the key of the mapping.
     * @return true if the object was succesfully removed.
     */
    public boolean removeMapping(K k) {
        if (_flag) {
            throw new IllegalStateException("Cannot add a mapping: unmodifiable map already accessed,");
        }
        if (!_map.containsKey(k)) {
            return false;
        }
        _map.remove(k);
        return true;
    }

    @Override
    public int size() {
        return _map.size();
    }

    @Override
    public boolean isEmpty() {
        return size() != 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return _map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return _map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return _map.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (_flag) {
            throw new IllegalStateException("Cannot add a mapping: unmodifiable map already accessed,");
        }
        return _map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if (_flag) {
            throw new IllegalStateException("Cannot remove a mapping: unmodifiable map already accessed,");
        }
        return _map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (_flag) {
            throw new IllegalStateException("Cannot add a mapping: unmodifiable map already accessed,");
        }
        _map.putAll(m);
    }

    @Override
    public void clear() {
        if (_flag) {
            throw new IllegalStateException("Cannot clear the map: unmodifiable map already accessed,");
        }
        _map.clear();
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(_map.keySet());
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(_map.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(_map.entrySet());
    }

}
class UMCM<K,V> extends UnmodifiableMap<K,V> {

    private final Map<K,V> _map;

    UMCM(Map<? extends K, ? extends V> map) {
        _map = Collections.unmodifiableMap(map);
    }

    @Override
    public UnmodifiableSet<Entry<K, V>> entrySet() {
        return new UnmodifiableSet<Entry<K, V>>() {

            @Override
            public UnmodifiableSet<Entry<K, V>> addElement(Entry<K, V> el) {
                final UnmodifiableSetConstructor<Entry<K, V>> con = new UnmodifiableSetConstructor<Entry<K, V>>(this);
                if (con.add(el)) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public UnmodifiableSet<Entry<K, V>> addElements(Collection<? extends Entry<K, V>> coll) {
                final UnmodifiableSetConstructor<Entry<K, V>> con = new UnmodifiableSetConstructor<Entry<K, V>>(this);
                boolean mod = false;
                for (final Entry<K,V> el: coll) {
                    mod = con.add(el) || mod;
                }
                if (mod) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public UnmodifiableSet<Entry<K, V>> removeElement(Entry<K, V> el) {
                final UnmodifiableSetConstructor<Entry<K, V>> con = new UnmodifiableSetConstructor<Entry<K, V>>(this);
                if (con.remove(el)) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public UnmodifiableSet<Entry<K, V>> removeElements(Collection<? extends Entry<K, V>> coll) {
                final UnmodifiableSetConstructor<Entry<K, V>> con = new UnmodifiableSetConstructor<Entry<K, V>>(this);
                boolean mod = false;
                for (final Entry<K,V> el: coll) {
                    mod = con.remove(el) || mod;
                }
                if (mod) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return _map.entrySet().iterator();
            }

            @Override
            public int size() {
                return _map.size();
            }
        };
    }

    @Override
    public UnmodifiableSet<K> keySet() {
        return new UnmodifiableSet<K>() {

            @Override
            public UnmodifiableSet<K> addElement(K el) {
                final UnmodifiableSetConstructor<K> con = new UnmodifiableSetConstructor<K>(this);
                if (con.add(el)) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public UnmodifiableSet<K> addElements(Collection<? extends K> coll) {
                final UnmodifiableSetConstructor<K> con = new UnmodifiableSetConstructor<K>(this);
                boolean mod = false;
                for (final K el: coll) {
                    mod = con.add(el) || mod;
                }
                if (mod) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public UnmodifiableSet<K> removeElement(K el) {
                final UnmodifiableSetConstructor<K> con = new UnmodifiableSetConstructor<K>(this);
                if (con.remove(el)) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public UnmodifiableSet<K> removeElements(Collection<? extends K> coll) {
                final UnmodifiableSetConstructor<K> con = new UnmodifiableSetConstructor<K>(this);
                boolean mod = false;
                for (final K el: coll) {
                    mod = con.remove(el) || mod;
                }
                if (mod) {
                    return con.getSet();
                }
                return this;
            }

            @Override
            public Iterator<K> iterator() {
                return _map.keySet().iterator();
            }

            @Override
            public int size() {
                return _map.size();
            }
        };
    }

    @Override
    public V get(Object key) {
        return _map.get(key);
    }

    @Override
    public UnmodifiableCollection<V> values() {
        return new UnmodifiableCollection<V>() {

            @Override
            public UnmodifiableCollection<V> addElement(V el) {
                final UnmodifiableListConstructor<V> con = new UnmodifiableListConstructor<V>(this);
                if (con.addElement(el)) {
                    return con.getList();
                }
                return this;
            }

            @Override
            public UnmodifiableCollection<V> addElements(Collection<? extends V> coll) {
                final UnmodifiableListConstructor<V> con = new UnmodifiableListConstructor<V>(this);
                boolean mod = false;
                for (final V el: coll) {
                    mod = con.addElement(el) || mod;
                }
                if (mod) {
                    return con.getList();
                }
                return this;
            }

            @Override
            public UnmodifiableCollection<V> removeElement(V el) {
                final UnmodifiableListConstructor<V> con = new UnmodifiableListConstructor<V>(this);
                if (con.removeElement(el)) {
                    return con.getList();
                }
                return this;
            }

            @Override
            public UnmodifiableCollection<V> removeElements(Collection<? extends V> coll) {
                final UnmodifiableListConstructor<V> con = new UnmodifiableListConstructor<V>(this);
                boolean mod = false;
                for (final V el: coll) {
                    mod = con.removeElement(el) || mod;
                }
                if (mod) {
                    return con.getList();
                }
                return this;
            }

            @Override
            public Iterator<V> iterator() {
                return _map.values().iterator();
            }

            @Override
            public int size() {
                return _map.size();
            }
        };
    }

    @Override
    public UMCM<K, V> putElement(K k, V v) {
        final V oldValue = get(k);
        if (oldValue != null && v.equals(oldValue)) {
            return this;
        }
        final Map<K,V> resultMap = new HashMap<K, V>(_map);
        resultMap.put(k, v);
        final UMCM<K,V> result = new UMCM<K,V>(resultMap);
        return result;
    }
    
}