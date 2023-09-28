/*
 * ImplyStream.java
 *
 * Created on 8 March 2007, 18:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.sat;

/**
 * This implementation of clause stream transforms all the clauses that it 
 * receives by stating that this clause is required if the specified literal is 
 * <code>true</code>.
 *
 * @author Alban Grastien
 * @version 1.0.1
 * @since 1.0.1
 */
public class ImplyStream
        extends AbstractClauseStream
        implements ClauseStream {

    /**
     * The literal such that the clauses put must be <i>true</i> if 
     * this literal is <i>true</i>
     */
    private final int _literal;
    /**
     * The clause stream where to put the clauses.
     */
    private final ClauseStream _out;

    /** 
     * Creates a new instance of ImplyStream that puts in the specified clause 
     * stream the clause it gets but stating that these clauses must be 
     * <i>true</i> if the specified literals is <i>true</i>.
     *
     * @param out the clause stream where to put the clauses.
     * @param lit the literal such that the clauses put must be <i>true</i> if 
     * this literal is <i>true</i>
     */
    public ImplyStream(ClauseStream out, int lit) {
        _literal = lit;
        _out = out;
    }

    /**
     * Puts the clause in this stream.  This stream first adds <i>not lit</i> 
     * to the clause and then sends the clause to the stream.
     *
     * @param cl the clause.
     */
    @Override
    public void put(Clause cl) {
        final Clause newCl = new Clause(cl, -this._literal);
        _out.put(newCl);
    }

    /**
     * Stores a unit clause in this clause stream.
     *
     * @param lit the literal that is to be set.
     */
    @Override
    public void put(int lit) {
        _out.put(lit, -_literal);
    }

    /**
     * Stores a binary clause in this clause stream.
     *
     * @param lit1 the first literal in the binary clause.
     * @param lit2 the secondliteral in the binary clause.
     */
    @Override
    public void put(int lit1, int lit2) {
        _out.put(lit1, lit2, -_literal);
    }

    /**
     * Closes the stream.  This method should be invoked when no more clause 
     * will be added to the stream.
     */
    @Override
    public void close() {
        _out.close();
    }
}
