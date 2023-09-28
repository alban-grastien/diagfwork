/*
 * AbstractClauseStream.java
 *
 * Created on 18 April 2008, 10:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.util.Collection;
import java.util.Iterator;

/**
 * This abstract implementation takes care of all the methods but 
 * {@link #put(Clause)} and {@link #close()}.  Any implementation of 
 * <code>ClauseStream</code> should extend this class to ensure that it 
 * implements methods that will be added to the interface.  However, noet that 
 * default implementation of the methods in this class may be far from optimal.
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 * @todo remove the javadoc that is the same as ClauseStream.  
 */
public abstract class AbstractClauseStream implements ClauseStream {
    
    /**
     * A tabular that is used to store binary clauses.
     */
    protected final int [] _tab2 = new int[2];
    
    /**
     * A tabular that is used to store clauses of size three.
     */
    protected final int [] _tab3 = new int[3];
    
    /**
     * Stores a unit clause in this clause stream.  The default implementation
     * simply generate a <code>Clause</code> object and calls the
     * {@link #put(Clause)} method.
     *
     * @param lit the literal that is to be set.
     */
    @Override
    public void put(int lit) {
        put(new Clause(lit));
    }
    
    /**
     * Stores a clause represented by an array of literals in this clause 
     * stream.  The default implementation simply generate a <code>Clause</code> 
     * object and calls the {@link #put(Clause)} method.
     *
     * @param tab the array of literals in the clause that is to be set.
     */
    @Override
    public void put(int[] tab) {
        put(new Clause(tab));
    }
    
    /**
     * Stores a binary clause in this clause stream.  The default implementation 
     * simply generate a <code>Clause</code> object and calls the 
     * {@link #put(Clause)} method.
     *
     * @param lit1 the first literal in the binary clause.
     * @param lit2 the secondliteral in the binary clause.
     */
    @Override
    public void put(int lit1, int lit2) {
        _tab2[0] = lit1;
        _tab2[1] = lit2;
        put(new Clause(_tab2));
    }
    
    /**
     * Stores a clause of size three in this clause stream.  The default 
     * implementation simply generate a <code>Clause</code> object and calls the 
     * {@link #put(Clause)} method.
     *
     * @param lit1 the first literal in the clause.
     * @param lit2 the second literal in the clause.
     * @param lit3 the third literal in the clause.
     */
    @Override
    public void put(int lit1, int lit2, int lit3) {
        _tab3[0] = lit1;
        _tab3[1] = lit2;
        _tab3[2] = lit3;
        put(new Clause(_tab3));
    }
    
    /**
     * Stores the specified list of clauses in this clause stream.  The default 
     * implementation simply calls the method {@link #put(Clause)} for each 
     * clause.
     *
     * @param clauses an array of clauses to put in this stream.
     */
    @Override
    public void put(Clause[] clauses) {
        for (int i=0 ; i<clauses.length ; i++) {
            put(clauses[i]);
        }
    }
    
    /**
     * Stores the specified list of clauses defined as an array of arrays of 
     * literals in this clauses stream.  The default implementation simply calls 
     * the method {@link #put(int[])} for each clause.
     *
     * @param clauses an array of clauses, each clause is represented by an 
     * array of literals.
     */
    @Override
    public void put(int[][] clauses) {
        for (int i=0 ; i<clauses.length ; i++) {
            put(clauses[i]);
        }
    }
    
    /**
     * Solves the problem.  By default, this method calls {@link #solve(int[])} 
     * with an empty array.
     *
     * @return <code>true</code> if the problem is solvable.
     * @throws UnsupportedOperationException if the implementation of 
     * <code>solve(int[])</code> generates this exception.
     */
    @Override
    public boolean solve() {
        return solve(new int[0]);
    }

    /**
     * Solves the problem with an array of assumptions.  By default, the method
     * generates a <code>UnsupportedOperationException</code>.
     *
     * @param ass a list of assumptions (unit clauses) for this resolution only.
     * @return nothing as an exception is generated.
     * @throws UnsupportedOperationException as the method is not implemented.
     */
    @Override
    public boolean solve(int[] ass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean solve(Collection<Integer> ass) {
        final int[] array = new int[ass.size()];
        int i=0;
        Iterator<Integer> it = ass.iterator();
        while (it.hasNext()) {
            array[i] = it.next();
        }
        return solve(array);
    }
    
    /**
     * Returns the valuation given to the specified variable last time the 
     * solver was called.
     *
     * @param var the propositional variable whose Boolean assignement is 
     * required.
     * @return <code>true</code> if the variable is assigned a positiv value, 
     * false otherwise.
     * @throws Error if the solver was not called.
     * @throws Error if the problem was not solvable.
     * @throws UnsupportedOperationException as the method is not implemented.
     */
    @Override
    public boolean model(int var) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns the valuation given to the specified variables last time the 
     * solver was called.  The default implementation calls {@link #model(int)} 
     * which may be far from optimal.
     *
     * @param vars the list of propositional variables whose Boolean assignment 
     * are required.
     * @return an array of Boolean that represents the list of assignments of 
     * <code>var</code>.
     * @throws Error if the solver was not called.
     * @throws Error if the problem was not solvable.
     * @throws UnsupportedOperationException as the method is not implemented.
     */
    @Override
    public boolean[] model(int[] vars) {
       final boolean[] result =  new boolean[vars.length];
       
       for (int i=0 ; i<vars.length ; i++) {
           result[i] = model(vars[i]);
       }
       
       return result;
    }

    @Override
    public void put(Collection<Integer> coll) {
        put(new Clause(coll));
    }
}
