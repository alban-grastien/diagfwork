package diag.reiter2.log;

import diag.reiter2.Conflict;

/**
 * A <code>ConflictFinding</code>, i.e., a conflict finding, 
 * is a log that indicates that the specified conflict was found.  
 */
public class ConflictFinding implements Log {
    /**
     * The conflict found.  
     */
    private final Conflict _conflict;
    
    /**
     * Creates a log that indicates that the specified conflict 
     * has been found.  
     * 
     * @param c the conflict that has been found.  
     */
    public ConflictFinding(Conflict c) {
        _conflict = c;
    }

    @Override
    public String stringLog() {
        return "  conflict found " + _conflict;
    }
}
