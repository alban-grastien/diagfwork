/*
 * BufferedClauseStream.java
 *
 * Created on 18 April 2008, 11:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

/**
 * Implementation of a clause stream that buffers the clauses.  The clauses are
 * stored in a buffered of a specified size, and emptied when it is full.
 * Note that this implementation is probably less efficient than
 * {@link BufferedPrintClauseStream} which should be used rather than 
 * <code>BufferedClauseStream</code> + {@link PrintClauseStream}.
 * Note that unless you use the method {@link #close()}, the only way to be sure 
 * that all the clauses were transmitted to the clause stream is to use the 
 * method {@link #empty()}.
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 */
public class BufferedClauseStream
        extends AbstractClauseStream
        implements ClauseStream {
    
    /**
     * The clause stream where the clauses will be stored.
     */
    private ClauseStream _out;
    
    /**
     * The buffer of clauses.
     */
    private Clause[] _clauses;
    
    /**
     * The number of clauses in the stream.
     */
    private int _nb;
    
    /**
     * Constructor.
     *
     * @param out the clause stream where the clauses are stored.
     * @param size the size of the stream.
     * @throws IllegalArgumentException if <code>size <= 0</code>.
     * @throws NullPointerException if <code>out == null</code>.
     */
    public BufferedClauseStream(ClauseStream out, int size) {
        if (out == null) {
            throw new NullPointerException("null clause stream.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size of the buffer (" + size + ")");
        }
        _out = out;
        _clauses = new Clause[size];
    }
    
    /**
     * Empties the buffer.
     */
    public void empty() {
        if (_nb == _clauses.length) {
            _out.put(_clauses);
        } else {
            if (_nb == 0) {
                return;
            }
            Clause[] clauses = new Clause[_nb];
            for (int i=0 ; i<_nb ; i++) {
                clauses[i] = _clauses[i];
            }
            _out.put(clauses);
        }
        for (int i=0 ; i<_nb ; i++) {
            _clauses[i] = null;
        }
        _nb = 0;
    }
    
    /**
     * Stores the specified clause in this stream.
     *
     * @param clause the clause.
     *
     */
    public void put(Clause clause) {
        _clauses[_nb] = clause;
        _nb++;
        if (_nb == _clauses.length) {
            empty();
        }
    }
    
    public boolean solve() {
        empty();
        return _out.solve();
    }
    
    public boolean solve(int[] nb) {
        empty();
        return _out.solve(nb);
    }
    
    /**
     * Closes this stream.  This closes the stream that is used by this stream.
     */
    public void close() {
        empty();
        _out.close();
    }

    public boolean model(int var) {
        return _out.model(var);
    }
}
