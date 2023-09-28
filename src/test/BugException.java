package test;

/**
 * A <code>BugException</code>, i.e., a bug exception, 
 * is an exception related to a bug.  
 * It should be generated only as a consequence of testing an implementation.  
 */
public class BugException extends Exception {
    
    /**
     * Creates a bug exception with the specified message.  
     * 
     * @param s the message.  
     */
    public BugException(String s) {
        super(s);
    }
    
    /**
     * Creates a bug exception as a consequence of a mismatch.  
     * 
     * @param s the message (for instance, which method is tested).  
     * @param o1 the expected value.  
     * @param o2 the returned value.  
     */
    public BugException(String s, Object o1, Object o2) {
        super(s + " (expected " + o1 + "; obtained " + o2 + ")");
    }
    
    /**
     * Tests whether the two specified values match 
     * and throws an exception if they do not.  
     * 
     * @param s the message generated if the values do not match.  
     * @param o1 the first value.  
     * @param o2 the second value.  
     */
    public static void test(String s, Object o1, Object o2) throws BugException {
        if (!o1.equals(o2)) {
            throw new BugException(s, o1, o2);
        }
    }
    
}