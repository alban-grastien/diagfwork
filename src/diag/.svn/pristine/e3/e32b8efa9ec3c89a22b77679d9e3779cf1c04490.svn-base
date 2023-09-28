package diag.reiter2.log;

import diag.reiter2.Hypothesis;

/**
 * A <code>CandidateFinding</code>, i.e., a candidate finding, 
 * is a log that indicates that the specified candidate has been found.  
 */
public class CandidateFinding implements Log {
    
    /**
     * The candidate that has been found.  
     */
    private final Hypothesis _candidate;
    
    /**
     * Creates a log that indicates that the specified candidate 
     * has been found.  
     * 
     * @param c the candidate that has been found.  
     */
    public CandidateFinding(Hypothesis c) {
        _candidate = c;
    }

    @Override
    public String stringLog() {
        return "  Candidate found: " + _candidate;
    }
    
}
