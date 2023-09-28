package diag.reiter2.seq;

import diag.reiter2.Hypothesis;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A <code>SequentialHypothesis</code>, i.e., a sequence hypothesis, 
 * is a hypothesis defined as a sequence of object.  
 * 
 * @param <X> the type of object in the sequence.  
 */
public class SequentialHypothesis<X> implements Hypothesis {
    
    /**
     * The sequence of this hypothesis.  
     */
    private final UnmodifiableList<X> _seq;
    
    /**
     * Creates an empty sequential hypothesis.  
     */
    public SequentialHypothesis() {
        this(new UnmodifiableListConstructor<X>().getList());
    }
    
    /**
     * Creates a sequential hypothesis 
     * defined as the specified sequence of objects.  
     * 
     * @param l the sequence of objects.  
     */
    public SequentialHypothesis(List<? extends X> l) {
        final UnmodifiableListConstructor<X> con = 
                new UnmodifiableListConstructor<X>();
        for (final X x: l) {
            con.addElement(x);
        }
        _seq = con.getList();
    }
    
    /**
     * Creates a sequential hypothesis 
     * defined as the specified sequence of objects.  
     * 
     * @param l the sequence of objects.  
     */
    public SequentialHypothesis(UnmodifiableList<X> l) {
        _seq = l;
    }

    /**
     * Builds a sequential hypothesis with the specified sequence of elements.  
     * 
     * @param xs the list of elements.  
     */
    public SequentialHypothesis(X... xs) {
        final UnmodifiableListConstructor<X> con = 
                new UnmodifiableListConstructor<X>();
        for (final X x: xs) {
            con.addElement(x);
        }
        _seq = con.getList();
    }
    
    /**
     * Returns whether the first specified hypothesis 
     * is a descendant of the second specified hypothesis.  
     * This is the case is the sequence associated with the first hypothesis 
     * is a super sequence of the sequence 
     * associated with the second hypothesis.  
     * 
     * @param <X> the type of elements in the sequences.  
     * @param suspectedDescendant the sequence we suspect to be the descendant.  
     * @param suspectedAncestor the sequence we suspect to be the ancestor.  
     * @return <code>true</code> if <code>suspectedDescendant</code> 
     * is a descendant of <code>suspectedAncestor</code>, 
     * <code>false</code> otherwise.  
     */
    public static <X> boolean isDescendant(
            SequentialHypothesis<X> suspectedDescendant, 
            SequentialHypothesis<X> suspectedAncestor) {
        final UnmodifiableList<X> s1 = suspectedDescendant._seq;
        final UnmodifiableList<X> s2 = suspectedAncestor._seq;
        
        final int size1 = s1.size();
        final int size2 = s2.size();
        
        // Try to match each element of s2 with an element of s1
        // If element i1 is matched with i2, j1 with j2, and i1 < j1, then i2 < j2
        // We inductively search for the smallest i1 associated with i2, starting with i2 = 0
        int i1 = 0;
        int i2 = 0;
        
        
        for (;;) {
            if (i2 == size2) { // We matched all elements of suspectedAncestor
                return true; 
            }
            
            //if (i1 == size1) {
            //    return false;
            //}
            if (size1 - i1 < size2 - i2) { // Can no longer find enough elements
                return false;
            }
            
            final X x1 = s1.get(i1);
            final X x2 = s2.get(i2);
            
            if (x1.equals(x2)) { // Found an element for i2, now looking for the next one
                i2++;
            }
            
            i1++;
        }
    }
    
    /**
     * Returns the list of children of this sequential hypothesis 
     * given the specific domain of value the elements in the sequence can take.  
     * A child of a sequential hypothesis is another sequential hypothesis 
     * whose sequence contains one more element (from the domain) 
     * that the sequence of this hypothesis.  
     * 
     * @param domain the list of values that can be inserted in the sequence 
     * of the hypothesis.  
     * @return the set of children of this hypothesis.  
     */
    public Set<SequentialHypothesis<X>> children(Set<X> domain) {
        final Set<SequentialHypothesis<X>> result = new HashSet<SequentialHypothesis<X>>();
        
        // i indicates whether the additional element should be inserted
        for (int i=0 ; i<=_seq.size() ; i++) {
            // x is the element to insert
            for (final X x: domain) {
                final List<X> list = new ArrayList<X>(_seq);
                list.add(i, x);
                final SequentialHypothesis<X> child = new SequentialHypothesis<X>(list);
                result.add(child);
            }
        }
        
        return result;
    }

    /**
     * Returns the list of parents of this sequential hypothesis.  
     * 
     * @return the list of parents of this sequential hypothesis.  
     */
    public Set<SequentialHypothesis<X>> parents() {
        final Set<SequentialHypothesis<X>> result = new HashSet<SequentialHypothesis<X>>();
        for (int i=0 ; i<_seq.size() ; i++) {
            final List<X> list = new ArrayList<X>(_seq);
            list.remove(i);
            final SequentialHypothesis<X> parent = new SequentialHypothesis<X>(list);
            result.add(parent);
        }
        
        return result;
    }
    
    /**
     * Computes the set of minimal common descendants 
     * of the two specified hypotheses.  
     * 
     * @param h1 the first hypothesis.  
     * @param h2 the second hypothesis.  
     * @return the set of descendants of <code>h1</code> and <code>h2</code> 
     * that have no strict ancestors 
     * which are descendants of <code>h1</code> and <code>h2</code>.  
     */
    public static <X> Set<SequentialHypothesis<X>> minimalCommonDescendants(
            SequentialHypothesis<X> h1, SequentialHypothesis<X> h2) {
        final Set<SequentialHypothesis<X>> result = new HashSet<SequentialHypothesis<X>>();
        final List<X> currentSequence = new ArrayList<X>();
        add(h1, h2, result, currentSequence, 0, 0, null, false, null, false);
        return result;
    }
    
    // The minimal common descendants are computed recursively 
    // by adding an element from the first or the second sequence 
    // (or both if the element matches) 
    // until all elements have been added.  
    // Notice that if an element x coming only from h1 is added, 
    // then x coming only from h2 cannot be later added 
    // (unless something was chosen later).  
    // @param set: where the resulting hypotheses are stored.  
    // @param currentSequence: the sequence built so far
    // @param i1: the current position in h1
    // @param i2: the current position in h2
    // @param chosen1: the last element chosen on h1
    // @param wasChosen1: whether an element was chosen on h1
    // @param chosen2: the last element chosen on h2
    // @param wasChosen2: whether an element was chosen on h2
    private static <X> void add(
            SequentialHypothesis<X> h1, SequentialHypothesis<X> h2, 
            Set<SequentialHypothesis<X>> set, 
            List<X> currentSequence, 
            int i1, int i2, 
            X chosen1, boolean wasChosen1, X chosen2, boolean wasChosen2) {
        if (i1 == h1._seq.size() && i2 == h2._seq.size()) {
            set.add(new SequentialHypothesis<X>(currentSequence));
            return;
        }
        
        final boolean canAdd1;
        if (i1 == h1._seq.size()) {
            canAdd1 = false;
        } else {
            final X x1 = h1._seq.get(i1);
            if (wasChosen2 && chosen2.equals(x1)) { // chosen2 should have been chosen with x1
                canAdd1 = false;
            } else { // should x1 be chosen with x2?
                if (i2 == h2._seq.size()) {
                    canAdd1 = true;
                } else {
                    final X x2 = h2._seq.get(i2);
                    if (x1.equals(x2)) {
                        canAdd1 = false;
                    } else {
                        canAdd1 = true;
                    }
                }
            }
        }
        
        final boolean canAdd2;
        if (i2 == h2._seq.size()) {
            canAdd2 = false;
        } else {
            final X x2 = h2._seq.get(i2);
            if (wasChosen1 && chosen1.equals(x2)) {
                canAdd2 = false;
            } else { 
                if (i1 == h1._seq.size()) {
                    canAdd2 = true;
                } else {
                    final X x1 = h1._seq.get(i1);
                    if (x2.equals(x1)) {
                        canAdd2 = false;
                    } else {
                        canAdd2 = true;
                    }
                }
            }
        }
        
        final boolean canAddBoth;
        if (i1 == h1._seq.size() || i2 == h2._seq.size()) {
            canAddBoth = false;
        } else {
            final X x1 = h1._seq.get(i1);
            final X x2 = h2._seq.get(i2);
            if (x1.equals(x2)) {
                if (wasChosen1 && x2.equals(chosen1)) { // chosen1 should have been chosen together with x2
                    canAddBoth = false;
                } else if (wasChosen2 && x1.equals(chosen2)) {
                    canAddBoth = false;
                } else {
                    canAddBoth = true;
                }
            } else {
                canAddBoth = false;
            }
        }
        
//        final boolean add1, add2, add12; // Whether i1 or i2 or both together may be incremented
//        
//        if (i1 == h1._seq.size()) {
//            
//            add1 = false;
//            add2 = true;
//            add12 = false;
//        } else if (i2 == h2._seq.size()) {
//            add1 = true;
//            add2 = false;
//            add12 = false;
//        } else {
//            final X n1 = h1._seq.get(i1);
//            final X n2 = h2._seq.get(i2);
//            
//            if (wasChosen1 && n2.equals(chosen1)) {
//                // Rather than choosing chosen1 earlier, it should have been chosen together with n2
//                if (n1.equals(n2)) { // Cannot choose n1 alone
//                    add1 = false;
//                    add2 = false;
//                    add12 = false;
//                } else { // ok, can choose n1 alone
//                    add1 = true;
//                    add2 = false;
//                    add12 = false;
//                }
//            } else if (wasChosen2 && n1.equals(chosen2)) {
//                if (n2.equals(n1)) {
//                    add1 = false;
//                    add2 = false;
//                    add12 = false;
//                } else {
//                    add1 = false;
//                    add2 = true;
//                    add12 = false;
//                }
//            } else {
//                if (h1._seq.get(i1).equals(h2._seq.get(i2))) {
//                    add1 = false;
//                    add2 = false;
//                    add12 = true;
//                } else { // Each can be triggered separately
//                    add1 = true;
//                    add2 = true;
//                    add12 = false;
//                }
//            }
//        }
        
        if (canAdd1) {
            final List<X> newList = new ArrayList<X>(currentSequence);
            final X chosen = h1._seq.get(i1);
            newList.add(chosen);
            add(h1, h2, set, newList, i1+1, i2, chosen, true, chosen2, wasChosen2);
        }
        if (canAdd2) {
            final List<X> newList = new ArrayList<X>(currentSequence);
            final X chosen = h2._seq.get(i2);
            newList.add(chosen);
            add(h1, h2, set, newList, i1, i2+1, chosen1, wasChosen1, chosen, true);
        }
        if (canAddBoth) {
            final List<X> newList = new ArrayList<X>(currentSequence);
            newList.add(h1._seq.get(i1));
            add(h1, h2, set, newList, i1+1, i2+1, null, false, null, false);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SequentialHypothesis)) {
            return false;
        }
        
        final SequentialHypothesis other = (SequentialHypothesis)o;
        if (_seq.size() != other._seq.size()) {
            return false;
        }
        
        final int size = _seq.size();
        for (int i=0 ; i<size ; i++) {
            final X x1 = _seq.get(i);
            final Object x2 = other._seq.get(i);
            if (!x1.equals(x2)) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        return _seq.hashCode();
    }
    
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        
        result.append("[");
        
        boolean first = true;
        for (final X x: _seq) {
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
        
        result.append("]");
        
        return result.toString();
    }
    
    /**
     * Returns the list of elements in this sequential hypothesis.  
     * 
     * @return the list of elements that composed this sequential hypothesis.  
     */
    public UnmodifiableList<X> elements() {
        return _seq;
    }
}
