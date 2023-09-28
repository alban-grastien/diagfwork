package edu.supercom.util.sat;

import java.util.Collection;

/**
 * An object that accepts a flow of clauses.  The resulting CNF may then be
 * solved or stored in an external file.  
 *
 * @author Alban Grastien
 * @version 1.0
 * @since 1.0
 * @todo Check that all subclasses of clause stream implement all methods
 * optimally.
 */
public interface ClauseStream {

    /**
     * Stores the specified clause in this stream.
     *
     * @param clause the clause.
     * 
     */
    public void put(Clause clause);
    
    /**
     * Stores a unit clause in this clause stream.
     *
     * @param lit the literal that is to be set.
     */
    public void put(int lit);
    
    /**
     * Stores a binary clause in this clause stream.
     *
     * @param lit1 the first literal in the binary clause.
     * @param lit2 the secondliteral in the binary clause.
     */
    public void put(int lit1, int lit2);
    
    /**
     * Stores a clause of size three in this clause stream.
     *
     * @param lit1 the first literal in the clause.
     * @param lit2 the second literal in the clause.
     * @param lit3 the third literal in the clause.
     */
    public void put(int lit1, int lit2, int lit3);

    /**
     * Stores a clause represented by an array of literals in this clause
     * stream.
     *
     * @param tab the array of literals in the clause that is to be set.
     */
    public void put(int[] tab);

    /**
     * Stores a clause represented by a collection of literals in this clause
     * stream.
     *
     * @param tab the collection of literals in the clause that is to be set.
     */
    public void put(Collection<Integer> tab);

    /**
     * Stores the specified list of clauses in this clause stream.
     *
     * @param clauses an array of clauses to put in this stream.
     */
    public void put(Clause[] clauses);

    /**
     * Stores the specified list of clauses defined as an array of arrays of
     * literals in this clauses stream.
     *
     * @param clauses an array of clauses, each clause is represented by an
     * array of literals.
     */
    public void put(int[][] clauses);
    
    /**
     * Closes this stream.  This method should be invoked when no more clause 
     * will be added to the stream.
     */
    public void close();
    
    /**
     * Solves the problem in this stream.  This method may be not implemented.
     *
     * @return <code>true</code> if the problem is solvable.
     * @throws UnsupportedOperationException if the method is not implemented.
     */
    public boolean solve();
    
    /**
     * Solves the problem in this stream.  This method may be not implemented.
     *
     * @param ass a list of assumptions (unit clauses) for this resolution only.
     * @return <code>true</code> if the problem is solvable.
     * @throws UnsupportedOperationException if the method is not implemented.
     */
    public boolean solve(int[] ass);
    
    /**
     * Returns the valuation given to the specified variable last time the 
     * solver was called.  This method may be not implemented.
     *
     * @return <code>true</code> if the variable is assigned a positiv value, 
     * false otherwise.
     * @throws Error if the solver was not called.
     * @throws Error if the problem was not solvable.
     * @throws UnsupportedOperationException if the method is not implemented.
     */
    public boolean model(int var);
    
    /**
     * Returns the valuation given to the specified variables last time the 
     * solver was called.
     *
     * @param vars the list of propositional variables whose Boolean assignment 
     * are required.
     * @return an array of Boolean that represents the list of assignments of 
     * <code>var</code>.
     * @throws Error if the solver was not called.
     * @throws Error if the problem was not solvable.
     * @throws UnsupportedOperationException as the method is not implemented.
     */
    public boolean[] model(int[] vars);

    /**
     * Solves the problem with a list of assumptions.
     *
     * @param ass a list of assumptions (unit clauses) for this resolution only.
     * @return nothing as an exception is generated.
     * @throws UnsupportedOperationException as the method is not implemented.
     */
    public boolean solve(Collection<Integer> ass);

} // clause buffer