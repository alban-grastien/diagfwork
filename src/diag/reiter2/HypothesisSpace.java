package diag.reiter2;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * A <code>HypothesisSpace</code>, i.e., 
 */
public interface HypothesisSpace<H extends Hypothesis> {

    /**
     * Returns the minimal hypothesis of this space.  
     * 
     * @return the hypothesis all hypotheses are a descendant of.  
     */
    public H getMinimalHypothesis();
    
    /**
     * Returns the set of children of the specified hypothesis.  
     * 
     * @param h the hypothesis whose children are computed.  
     * @return the set of hypotheses 
     * that are directly descendant of <code>h</code>.  
     */
    public Set<H> getChildren(H h);
    
    /**
     * Returns the set of parents of the specified hypothesis.  
     * 
     * @param h the hypothesis whose parents are computed.  
     * @return the set of hypotheses 
     * that are directly ancestor of <code>h</code>.  
     */
    public Set<H> getParents(H h);
    
    /**
     * Returns whether the first specified hypothesis 
     * is a descendant of the second specified hypothesis.  
     * 
     * @param suspectedDescendant the suspected descendant.  
     * @param suspectedAncestor the suspected ancestor.  
     * @return <code>true</code> if <code>suspectedDescendant</code> 
     * is a descendant of <code>suspectedAncestor</code>.
     */
    public boolean isDescendant(H suspectedDescendant, H suspectedAncestor);
    
    /**
     * Returns the minimal elements 
     * that are descendant of the two specified hypotheses.  
     * 
     * @param h1 the first hypothesis.  
     * @param h2 the second hypothesis.  
     * @return the minimal hypotheses that are descendant 
     * to both <code>h1</code> and <code>h2</code>.  
     */
    public Set<H> minimalCommonDescendants(H h1, H h2);
    
    /**
     * Parses the hypothesis in the specified reader.  
     * 
     * @param reader the reader that must be parsed to extract the hypothesis.  
     * @return the hypothesis of this space that was written in the reader.  
     */
    public H parse(Reader reader) throws IOException;
    
}
