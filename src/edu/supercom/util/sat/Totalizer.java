package edu.supercom.util.sat;

/**
 *
 * @author Alban Grastien
 */
public interface Totalizer {
    
    /**
     * Returns the maximum value of this node.
     *
     * @return the maximum value of this node.
     */
    public int max();
    
    /**
     * Adds a possible value to the encoding of this node.  Note that this
     * method may recursively upgrade its children.
     */
    public void upgrade(VariableLoader loader, ClauseStream out);
    
    /**
     * Puts a set of clauses in the specified clause stream that indicates that
     * the value of this node is greater or equal than the specified value.  If
     * the specified value is greater than {@link #max()}, this cannot be
     * implemented and the method should throw an exception.
     *
     * @param val the minimum value.
     * @param out the output clause stream where the clause are put.
     * @throws IllegalArgumentException if <code>val > max()</code>.
     */
    public void ge(int val, ClauseStream out);
    
    /**
     * Puts a set of clauses in the specified clause stream that indicates that
     * the value of this node is strictly less than the specified value.  If the
     * specified value is greater than {@link #max()}, this cannot be
     * implemented and the method should throw an exception.
     *
     * @param val the maximum value.
     * @throws IllegalArgumentException if <code>val > max()</code>.
     */
    public void less(int val, ClauseStream out);
    
}
