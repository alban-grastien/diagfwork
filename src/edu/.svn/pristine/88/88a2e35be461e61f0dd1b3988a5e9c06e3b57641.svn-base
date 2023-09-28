/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.io.PrintStream;
import java.util.Collection;

/**
 * Prints the clause that go through a specified stream.
 *
 * @author Alban Grastien
 * @since 1.0.10
 * @version 2.0
 * @todo remove methods already in AbstractClauseStream.
 * @todo make it a subclass of StreamSplitter?  
 */
public class SpyClauseStream extends AbstractClauseStream implements ClauseStream {

    private final ClauseStream _cl;

    private final PrintStream _out;

    /**
     * Spy clause stream constructor where the print stream is System.out.
     *
     * @param cl the clause stream that is spied upon.  
     */
    public SpyClauseStream(ClauseStream cl) {
        this(cl,System.out);
    }

    /**
     * Builds a clause stream that prints every clause in the output on top of
     * puting them in the specified stream.
     *
     * @param cl the stream that is being spied upon.
     * @param out the print stream where the clauses should be printed.  
     */
    public SpyClauseStream(ClauseStream cl, PrintStream out) {
        _cl = cl;
        _out = out;
    }

    @Override
    public void put(Clause clause) {
        _out.println(clause);
        _cl.put(clause);
    }

    @Override
    public void put(int lit) {
        _out.println(lit + "\t0");
        _cl.put(lit);
    }

    @Override
    public void put(int lit1, int lit2) {
        _out.println(lit1 + "\t" + lit2 + "\t0");
        _cl.put(lit1,lit2);
    }

    @Override
    public void put(int lit1, int lit2, int lit3) {
        _out.println(lit1 + "\t" + lit2 + "\t" + lit3 + "\t0");
        _cl.put(lit1,lit2,lit3);
    }

    @Override
    public void put(int[] tab) {
        _out.println(new Clause(tab));
        _cl.put(tab);
    }

    @Override
    public void put(Clause[] clauses) {
        for (final Clause clause: clauses) {
            _out.println(clause);
        }
        _cl.put(clauses);
    }

    @Override
    public void put(int[][] clauses) {
        for (final int[] clause: clauses) {
            _out.println(new Clause(clause));
        }
        _cl.put(clauses);
    }

    @Override
    public void close() {
        _cl.close();
    }

    @Override
    public boolean solve() {
        _out.println("SOLVE");
        return _cl.solve();
    }

    @Override
    public boolean solve(int[] ass) {
        final StringBuilder buf = new StringBuilder();
        for (final int lit: ass) {
            buf.append("\t" + lit);
        }
        _out.println("SOLVE" + buf);
        return _cl.solve(ass);
    }

    @Override
    public boolean model(int var) {
        return _cl.model(var);
    }

    @Override
    public boolean[] model(int[] vars) {
        return _cl.model(vars);
    }

    @Override
    public boolean solve(Collection<Integer> ass) {
        final StringBuilder buf = new StringBuilder();
        for (final int lit: ass) {
            buf.append("\t" + lit);
        }
        _out.println("SOLVE" + buf);
        return _cl.solve(ass);
    }

}
