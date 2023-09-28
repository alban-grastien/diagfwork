package diag.reiter2.set;

import diag.reiter2.Hypothesis;
import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import java.util.Collection;

/**
 * A <code>SetHypothesis</code>, i.e., a set hypothesis, 
 * is a hypothesis that states that the precise specified subset of elements 
 * from a specified superset occurred.  
 * Practically, the superset is stored in the hypothesis space.  
 * 
 * @author Alban Grastien
 * @param X the type of objects in the sequence
 * @see SetHypothesisSpace
 */
public class SetHypothesis<X> implements Hypothesis {
    
    /**
     * The set of elements that occurred on the system.  
     */
    private final UnmodifiableSet<X> _occurred;
    
    /**
     * Builds a set hypothesis that states 
     * that none of the elements in the SHS occurred.  
     */
    public SetHypothesis() {
        final UnmodifiableSetConstructor<X> con = new UnmodifiableSetConstructor<X>();
        _occurred = con.getSet();
    }
    
    /**
     * Builds a set hypothesis that states 
     * that the specified subset of elements occurred.  
     * 
     * @param es the collection of elements that occurred.  
     */
    public SetHypothesis(Collection<X> es) {
        final UnmodifiableSetConstructor<X> con = new UnmodifiableSetConstructor<X>(es);
        _occurred = con.getSet();
    }
    
    /**
     * Builds a set hypothesis that states 
     * that the specified unmodifiable subset of elements occurred.  
     * 
     * @param es the unmodifiable set of elements that occurred.  
     */
    public SetHypothesis(UnmodifiableSet<X> es) {
        _occurred = es;
    }
    
    /**
     * Builds the set hypothesis defines as the occurrence 
     * of the elements of this set hypothesis and the specified element.  
     * 
     * @param e the element added to the specified hypothesis.  
     * @return a new hypothesis corresponding to the set of elements 
     * in <tt>this</tt> plus <tt>e</tt> if <tt>e</tt> is not already in, 
     * <tt>this</tt> otherwise.  
     */
    public SetHypothesis<X> add(X e) {
        if (_occurred.contains(e)) {
            return this;
        }
        final SetHypothesis<X> result;
        {
            final UnmodifiableSetConstructor<X> con = 
                    new UnmodifiableSetConstructor<X>(_occurred);
            con.add(e);
            final UnmodifiableSet<X> elements = con.getSet();
            result = new SetHypothesis<X>(elements);
        }
        
        
        return result;
    }
    
    /**
     * Builds the set hypothesis defines as the occurrence 
     * of the elements of this set hypothesis minus the specified element.  
     * 
     * @param e the element removed from the specified hypothesis.  
     * @return a new hypothesis corresponding to the set of elements 
     * in <tt>this</tt> minus <tt>e</tt> if <tt>e</tt> is already in, 
     * <tt>this</tt> otherwise.  
     */
    public SetHypothesis<X> remove(X e) {
        if (!_occurred.contains(e)) {
            return this;
        }
        final SetHypothesis<X> result;
        {
            final UnmodifiableSetConstructor<X> con = 
                    new UnmodifiableSetConstructor<X>(_occurred);
            con.remove(e);
            final UnmodifiableSet<X> elements = con.getSet();
            result = new SetHypothesis<X>(elements);
        }
        
        return result;
    }
    
    /**
     * Indicates whether this set hypothesis 
     * is a descendant of the specified hypothesis.  
     * 
     * @param h the set hypothesis compared to <tt>this</tt>.  
     * @return <tt>true</tt> if the elements in <tt>h</tt> 
     * are all elements of <tt>this</tt>, <tt>false</tt> otherwise.  
     */
    public boolean isDescendantOf(SetHypothesis<X> h) {
        for (final X e: h.occurred()) {
            if (!this._occurred.contains(e)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Returns the (single) common descendant 
     * of the two specified set hypotheses.  
     * 
     * @param h1 the first set hypothesis.  
     * @param h2 the second set hypothesis.  
     * @return the set hypothesis defined as the union of the set of elements 
     * of <tt>h1</tt> and <tt>h2</tt>.  
     */
    @SuppressWarnings("unchecked")
    public static <X> SetHypothesis<X> minimalCommunDescendant(
            SetHypothesis<X> h1, SetHypothesis<X> h2) {
        final UnmodifiableSetConstructor<X> con = 
                new UnmodifiableSetConstructor<X>();
        
        {
            final SetHypothesis[] hs = {h1, h2};
            for (final SetHypothesis<X> h : hs) {
                for (final X e : h.occurred()) {
                    con.add(e);
                }
            }
        }
        
        final UnmodifiableSet<X> elements = con.getSet();
        return new SetHypothesis<X>(elements);
    }
    
    /**
     * Returns the set of elements that occurred in this set hypothesis.  
     * 
     * @return all the elements that occurred according to this hypothesis.  
     */
    public UnmodifiableSet<X> occurred() {
        return _occurred;
    }
    
//    @Override
//    public String toString() {
//        return _occurred.toString();
//    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        
        result.append("{");
        
        boolean first = true;
        for (final X x: _occurred) {
            if (!first) {
                result.append(", ");
            }
            result.append("'");
            String string = x.toString();
            string = string.replaceAll("\\\\", "\\\\\\\\");
            string = string.replaceAll("\'", "\\\\\'");
            result.append(string);
            result.append("'");
            first = false;
        }
        
        result.append("}");
        
        return result.toString();
    }
    
    @Override
    public int hashCode() {
        return _occurred.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (! (o instanceof SetHypothesis)) {
            return false;
        }
        
        final SetHypothesis h = (SetHypothesis)o;
        return _occurred.equals(h._occurred);
    }
}
