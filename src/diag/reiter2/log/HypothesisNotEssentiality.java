package diag.reiter2.log;

import diag.reiter2.Conflict;
import diag.reiter2.Hypothesis;

/**
 * A <code>HypothesisNotEssentiality</code>, i.e., a hypothesis non essentiality, 
 * is a log that indicates that a specified hypothesis is not essential.  
 */
public class HypothesisNotEssentiality implements Log {
    
    /**
     * The hypothesis that is not essential.  
     */
    private final Hypothesis _h;
    
    /**
     * The conflict that proves that the hypothesis is not essential.  
     */
    private final Conflict _c;

    /**
     * Creates a log that indicates that the specified hypothesis 
     * is not essential, as proved by the specified conflict.  
     * 
     * @param h the hypothesis.  
     * @param c the conflict.  
     */
    public HypothesisNotEssentiality(Hypothesis h, Conflict c) {
        _h = h;
        _c = c;
    }

    @Override
    public String stringLog() {
        return "  " + _h + " is not essential (conflict: " + _c + ")";
    }
    
    
}
