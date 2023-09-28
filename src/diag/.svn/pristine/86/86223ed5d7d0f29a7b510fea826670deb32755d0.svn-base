package diag.reiter2;

import java.util.Collection;

/**
 * A <code>TightPropertyComputer</code>, i.e., a tight-properties computer, 
 * is an object that is able to compute the set of tight-properties 
 * associated with a hypothesis.  
 * 
 * @param <H> the type of hypotheses the object is able to deal with.  
 */
public interface TightPropertyComputer<H extends Hypothesis> {
    
    /**
     * Computes the set of tight properties of the specified hypothesis.  
     * 
     * @param space the hypothesis space.  
     * @param h the hypothesis of interest.  
     * @return the tight properties of <code>h</code>.  
     * @param <HH> the type of hypotheses.  
     */
    public <HH extends H> Collection<Property<HH>> 
            tightProperties(HypothesisSpace<HH> space, HH h);
    
}
