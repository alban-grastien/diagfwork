package edu.supercom.util.sat;

/**
 * A <code>TimedSemanticFactory</code>, i.e., a timed semantic factory, 
 * is an object that is able to build variable semantics 
 * that are defined as the combination of an object with a time stamp.  
 * 
 * @param <X> the type of object the time stamp is combined with.  
 */
public interface TimedSemanticFactory<X> {
    
    /**
     * Creates a variable semantics that is defined as the combination 
     * of the specified object with the specified time stamp.  
     * 
     * @param x the object.  
     * @param t the time stamp.  
     * @return the variable semantics which associated <code>x</code> 
     * with timestamp <code>t</code>.  
     */
    public VariableSemantics buildSemantics(X x, int t);
    
}
