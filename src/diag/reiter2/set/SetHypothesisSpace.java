package diag.reiter2.set;

import diag.reiter2.HypothesisSpace;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A <code>SetHypothesisSpace</code>, i.e., a set hypothesis space or SHS, 
 * is a hypothesis space whose hypotheses 
 * are {@linkplain SetHypothesis set hypotheses}, 
 * i.e., hypotheses that states that a specified subset of elements 
 * from a specified superset (defined statically for the space) 
 * occurred on the system.  
 * 
 * @author Alban Grastien
 * @param X the type of elements.  
 */
public class SetHypothesisSpace<X> implements HypothesisSpace<SetHypothesis<X>> {

    /**
     * The list of elements of interest in the hypothesis space.  
     */
    private final UnmodifiableList<X> _elements;
    
    /**
     * Builds a set hypothesis space 
     * defined for the specified collection of elements.  
     * 
     * @param es the collection of elements that are monitored by the SHS.  
     */
    public SetHypothesisSpace(Set<X> es) {
        final UnmodifiableListConstructor<X> con = new UnmodifiableListConstructor<X>();
        for (final X e: es) {
            con.addElement(e);
        }
        _elements = con.getList();
    }
    
    /**
     * Builds a set hypothesis space 
     * defined for the specified unmodifiable list of elements.  
     * 
     * @param es the unmodifiable list of elements that are monitored by the SHS.  
     */
    public SetHypothesisSpace(UnmodifiableList<X> es) {
        _elements = es;
    }
    
    /**
     * Returns the list of elements monitored by this SHS.  
     * 
     * @return the list of elements for which a hypothesis tells us 
     * whether it occurred or not.  
     */
    public UnmodifiableList<X> getElements() {
        return _elements;
    }
    
    @Override
    public SetHypothesis<X> getMinimalHypothesis() {
        return new SetHypothesis<X>();
    }
    
    @Override
    public Set<SetHypothesis<X>> getChildren(SetHypothesis<X> h) {
        final Set<SetHypothesis<X>> result = new HashSet<SetHypothesis<X>>();
        
        for (final X e: _elements) {
            final SetHypothesis<X> newH = h.add(e);
            if (newH == h) {
                continue;
            }
            result.add(newH);
        }
        
        return result;
    }

    @Override
    public Set<SetHypothesis<X>> getParents(SetHypothesis<X> h) {
        final Set<SetHypothesis<X>> result = new HashSet<SetHypothesis<X>>();
        
        for (final X e: _elements) {
            final SetHypothesis<X> newH = h.remove(e);
            if (newH == h) {
                continue;
            }
            result.add(newH);
        }
        
        return result;
    }

    @Override
    public boolean isDescendant(SetHypothesis<X> suspectedDescendant, SetHypothesis<X> suspectedAncestor) {
        return suspectedDescendant.isDescendantOf(suspectedAncestor);
    }

    //// TO DO ABOVE

    @Override
    public Set<SetHypothesis<X>> minimalCommonDescendants(SetHypothesis<X> h1, SetHypothesis<X> h2) {
        final Set<SetHypothesis<X>> result = new HashSet<SetHypothesis<X>>();
        result.add(SetHypothesis.minimalCommunDescendant(h1, h2));
        return result;
    }

    @Override
    public SetHypothesis<X> parse(Reader reader) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
