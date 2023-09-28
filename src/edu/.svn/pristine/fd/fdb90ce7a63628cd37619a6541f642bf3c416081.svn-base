/*
 * MapLabelSynchroniser.java
 *
 * Created on 16 February 2007, 18:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.auto.label;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A label synchroniser for map labels.  The labels synchronise if they have the 
 * same value for the keys they share.  The synchronisation is then the union.  
 * In case the set (or a superset) of shared keys is known, it may be provided 
 * at construction time to improve the projection.  
 *
 * @author Alban Grastien
 * @author Carole Aujames
 * @version 2.0
 * @since 1.0
 * @param <K> the type of the keys in the map.
 * @param <V> the type of the values in the map.  
 */
public class MapLabelSynchroniser<K,V>
        extends AbstractSynchroniser <
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>
            >
        implements Synchroniser <
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>,
            Map<? extends K,? extends V>
            > {

    /**
     * The set of shared keys.
     */
    final Set<? extends K> _synchr;

    /** 
     * Creates a MapLabelSynchroniser.
     */
    public MapLabelSynchroniser() {
        _synchr = null;
    }

    public MapLabelSynchroniser(Set<? extends K> synchr) {
        _synchr = synchr;
    }

    @Override
    public Map<? extends K,? extends V> synchroniseLabel(
            Map<? extends K,? extends V> lb1,
            Map<? extends K,? extends V> lb2) {
        return doSynchronise(lb1, lb2);
    }

    /**
     * Builds a map label as the union of the association in the two specified
     * map labels.  If a given key is associated two different values in the
     * maps, the method returns <code>null</code>.
     *
     * @param l1 the first map label.
     * @param l2 the second map label.
     * @return a map label that corresponds to the union of <code>l1</code> and
     * <code>l2</code> if such an union exists, <code>null</code> otherwise.
     */
    public static <K,V> Map<? extends K,? extends V> doSynchronise(Map<? extends K,? extends V> l1, Map<? extends K,? extends V> l2) {
        final Map<K,V>  result = new HashMap<K,V>(l1);
        for (final Map.Entry<? extends K,? extends V> entry: l2.entrySet()) {
            final V previous = result.put(entry.getKey(), entry.getValue());
            if (previous != null) {
                if (!previous.equals(entry.getValue())) {
                    return null;
                }
            }
        }

        return result;
    }

    public <KK extends K,VV extends V> Map<KK,VV> projection(Map<KK,VV> lb) {
        if (_synchr == null) {
            return lb;
        }

        final Map<KK,VV> result = new HashMap<KK,VV>();
        for (Map.Entry<KK,VV> entry: lb.entrySet()) {
            if (_synchr.contains(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    @Override
    public Map<? extends K,? extends V> projection1(Map<? extends K,? extends V> lb) {
        return projection(lb);
    }

    @Override
    public Map<? extends K,? extends V> projection2(Map<? extends K,? extends V> lb) {
        return projection(lb);
    }

    @Override
    public boolean synchronise(
            Map<? extends K,? extends V> m1,
            Map<? extends K,? extends V> m2) {
        for (Map.Entry<? extends K,? extends V> entry: m1.entrySet()) {
            final K key = entry.getKey();
            final V val = entry.getValue();
            if (m2.containsKey(key)) {
                Object val2 = m2.get(key);
                if (val.equals(val2)) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean synchroniseProjectedLabel1(
            Map<? extends K,? extends V> proj,
            Map<? extends K,? extends V> lb2) {
        return synchronise(proj, lb2);
    }

    @Override
    public boolean synchroniseProjectedLabel2(
            Map<? extends K,? extends V> lb1,
            Map<? extends K,? extends V> proj) {
        return synchronise(proj, lb1);
    }

    @Override
    public boolean synchroniseProjectedLabels(
            Map<? extends K,? extends V> proj1,
            Map<? extends K,? extends V> proj2) {
        return synchronise(proj1, proj2);
    }
}





