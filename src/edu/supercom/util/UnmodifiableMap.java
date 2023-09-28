/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util;

import java.util.AbstractMap;
import java.util.Map;

/**
 * An unmodifiable map is a map of elements that is guaranteed it cannot be
 * modified.  This property cannot be enforced and is merely a contract that
 * any extension of this abstract class should satisfy.  If the extension of
 * this class fails to satisfy the property, the behaviour of the software is
 * unspecified.  The purpose of this class is to avoid copies of maps when they 
 * are guaranteed they cannot be modified.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <K> The type of map keys.
 * @param <V> The type of map values.
 */
public abstract class UnmodifiableMap<K,V>
        extends AbstractMap<K,V>
        implements Map<K,V> {

    @Override
    public final V put(K k, V v) {
        throw new UnsupportedOperationException("Unmodifiable map.");
    }

    @Override
    public final V remove(Object key) {
        throw new UnsupportedOperationException("Unmodifiable map.");
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Unmodifiable map.");
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException("Unmodifiable map.");
    }

    /**
     * Returns an unmodifiable map that corresponds to this map plus the mapping
     * of the specified key to the specified value.  If the key was already
     * associated with some value, then the old mapping is masked in the 
     * resulting map.  This map is obviously unchanged.
     *
     * @param k the key of the new mapping.
     * @param v the value of the new mapping.
     * @return a new unmodifiable map identical of this map except that
     * <code>k</code> is now associated with <code>v</code>.
     */
    public abstract UnmodifiableMap<K,V> putElement(K k, V v);
    
    @Override
    public abstract UnmodifiableSet<Entry<K, V>> entrySet();

    @Override
    public abstract UnmodifiableSet<K> keySet();

    @Override
    public abstract V get(Object key);

    @Override
    public abstract UnmodifiableCollection<V> values();

}
