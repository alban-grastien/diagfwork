package diag.reiter2.multiset;

import diag.reiter2.Hypothesis;
import edu.supercom.util.UnmodifiableMap;
import edu.supercom.util.UnmodifiableMapConstructor;
import java.util.Map;

/**
 * A <code>MultiSetHypothesis</code>, i.e., a multi-set hypothesis, 
 * is a hypothesis that associates each fault with the number of occurrences 
 * of this fault.  
 */
public class MultiSetHypothesis<X> implements Hypothesis {
    
    /**
     * The map that indicates how many occurrences 
     * are associated with each fault.  
     * (If none, then the element is skipped.)
     */
    private final UnmodifiableMap<X,Integer> _occurrences;
    
    /**
     * The empty multi set hypothesis.  
     */
    public MultiSetHypothesis() {
        _occurrences = new UnmodifiableMapConstructor<X,Integer>().getMap();
    }
    
    /**
     * Creates a multi set hypothesis 
     * that associates each fault with the number in the specified map.  
     * 
     * @param occurrencesPerFault  the map that associates each fault 
     * with the number of occurrence of this fault (it is assumed 
     * zero occurrences if a fault does not appear in the map).  
     */
    public MultiSetHypothesis(Map<X,Integer> occurrencesPerFault) {
        final UnmodifiableMapConstructor<X,Integer> con = 
                new UnmodifiableMapConstructor<X, Integer>();
        for (final Map.Entry<X,Integer> ent: occurrencesPerFault.entrySet()) {
            final X x = ent.getKey();
            final Integer i = ent.getValue();
            if (i == null) {
                throw new NullPointerException();
            }
            if (i < 0) {
                throw new IllegalArgumentException("Cannot have negative number of occurrences.");
            }
            if (i == 0) {
                continue;
            }
            con.addMapping(x, i);
        }
        _occurrences = con.getMap();
    }
    
    /**
     * Defines a multi set hypothesis as the addition/subtraction 
     * of a fault occurrence in the specified hypothesis.  
     * 
     * @param h the hypothesis the multi set hypothesis 
     * is defined with respect to.  
     * @param added <code>true</code> if the occurrence is added to <code>h</code>.  
     * @param x the object whose occurrence is increased.  
     */
    public MultiSetHypothesis(MultiSetHypothesis<X> h, boolean added, X x) {
        final UnmodifiableMapConstructor<X,Integer> con = 
                new UnmodifiableMapConstructor<X, Integer>();
        boolean foundX = false;
        for (final Map.Entry<X,Integer> ent: h._occurrences.entrySet()) {
            final X key = ent.getKey();
            Integer i = ent.getValue();
            if (key.equals(x)) {
                foundX = true;
                if (added) {
                    i++;
                } else {
                    i--;
                }
                if (i == 0) {
                    continue;
                }
            }
            con.addMapping(key, i);
        }
        
        if (!foundX) {
            if (added) {
                con.addMapping(x, 1);
            } else {
                throw new IllegalArgumentException(
                        "Cannot reduce occurrence of object: is null (" 
                        + x + ")");
            }
        }
        
        _occurrences = con.getMap();
    }
    
    @Override
    public int hashCode() {
        return _occurrences.hashCode();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof MultiSetHypothesis<?>)) {
            return false;
        }
        
        final MultiSetHypothesis<X> other = (MultiSetHypothesis<X>)o;
        
        if (!_occurrences.equals(other._occurrences)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return _occurrences.toString();
    }
    
    public int getOccurrences(X x) {
        final Integer result = _occurrences.get(x);
        if (result == null) {
            return 0;
        }
        return result;
    }
}
