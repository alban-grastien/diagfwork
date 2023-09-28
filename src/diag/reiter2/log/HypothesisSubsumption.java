package diag.reiter2.log;

import diag.reiter2.Hypothesis;

/**
 * A <code>HypothesisSubsumption</code>, i.e., a hypothesis subsumption, 
 * is a log that indicates that a specified hypothesis is subsumed 
 * by a candidate.  
 */
public class HypothesisSubsumption implements Log {
    
    /**
     * The hypothesis that is subsumed.  
     */
    private final Hypothesis _subsumed; 
    /**
     * The candidate that subsumes the hypothesis
     */
    private final Hypothesis _candidate; 
    
    /**
     * Creates a log that indicates that the specified hypothesis 
     * is subsumed by the specified candidate.  
     * 
     * @param h the hypothesis that is subsumed.  
     * @param c the candidate.  
     */
    public HypothesisSubsumption(Hypothesis h, Hypothesis c) {
        _subsumed = h;
        _candidate = c;
    }

    @Override
    public String stringLog() {
        return "  " + _subsumed + " is subsumed by " + _candidate;
    }
    
}
