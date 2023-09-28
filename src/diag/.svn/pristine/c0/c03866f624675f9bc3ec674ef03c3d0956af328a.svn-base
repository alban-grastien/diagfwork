package diag.reiter2.log;

import diag.reiter2.Hypothesis;

/**
 * A <code>HypothesisConsideration</code>, i.e., a hypothesis consideration, 
 * is a log that states that the specified hypothesis is being considered.  
 */
public class HypothesisConsideration implements Log {

    /**
     * The hypothesis that is being tested.  
     */
    private final Hypothesis _h;
    
    /**
     * Builds the log that says that the specified hypothesis is being tested.  
     * 
     * @param h the hypothesis that is being tested.  
     */
    public HypothesisConsideration(Hypothesis h) {
        _h = h;
    }
    
    @Override
    public String stringLog() {
        return "Considering " + _h;
    }
    
}
