/*
 * ClausePruner.java
 *
 * Created on 8 March 2007, 10:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.sat;

import java.util.Collection;

/**
 * This class implements the ClauseStream interface.  A clause pruner acts as a 
 * filter on the clauses for a clause stream: it prunes the clauses that are 
 * trivially <i>true</i>.
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 1.0
 */
public class ClausePruner
        extends AbstractClauseStream
        implements ClauseStream {

    /**
     * The stream to which the filtered clauses are sent.
     */
    private final ClauseStream _out;

    /** 
     * Creates a new instance of ClausePruner that prunes the clauses for the specified 
     * stream.
     *
     * @param out the stream to which the filtered clauses are sent.
     * @throws NullPointerException if <code>out == null</code>.
     */
    public ClausePruner(ClauseStream out) {
        if (out == null) {
            throw new NullPointerException();
        }

        _out = out;
    }

    /**
     * Gets a clause.  This implementation puts the clause to the clause 
     * stream used in the constructor iff the clause is not trivially true.
     *
     * @param clause the clause.
     */
    @Override
    public void put(Clause clause) {
        if (!clause.isTrue()) {
            this._out.put(clause);
        }
    }

    /**
     * Closes the stream.  This method should be invoked when no more clause 
     * will be added to the stream.
     */
    @Override
    public void close() {
        _out.close();
    }

    @Override
    public void put(Clause[] clauses) {
    	// TODO: Store the results in a boolean[]
        int nbTrue = 0;
        for (int i = 0; i < clauses.length; i++) {
            if (clauses[i].isTrue()) {
                nbTrue++;
            }
        }
        if (nbTrue == 0) {
            _out.put(clauses);
            return;
        }

        final Clause[] newClauses = new Clause[clauses.length - nbTrue];
        int i = 0;
        int j = 0;
        while (i < clauses.length) {
            if (clauses[i].isTrue()) {
            } else {
                newClauses[j] = clauses[i];
                j++;
            }
            i++;
        }
        _out.put(newClauses);
    }

	@Override
	public boolean model(int var) {
		return _out.model(var);
	}

	@Override
	public void put(int lit) {
		_out.put(lit);
	}

	@Override
	public void put(int lit1, int lit2) {
		if (lit1 == -lit2) {
			return;
		}
		_out.put(lit1, lit2);
	}

	@Override
	public void put(int lit1, int lit2, int lit3) {
		if (lit1 == -lit2) {
			return;
		}
		if (lit1 == -lit3) {
			return;
		}
		if (lit2 == -lit3) {
			return;
		}
		_out.put(lit1, lit2, lit3);
	}

	@Override
	public void put(int[] tab) {
		if (Clause.isTrue(tab)) {
			return;
		}
		_out.put(tab);
	}

	@Override
	public void put(Collection<Integer> tab) {
		if (Clause.isTrue(tab)) {
			return;
		}
		_out.put(tab);
	}

	@Override
	public void put(int[][] clauses) {
    	// TODO: Store the results in a boolean[]
        int nbTrue = 0;
        for (int i = 0; i < clauses.length; i++) {
            if (Clause.isTrue(clauses[i])) {
                nbTrue++;
            }
        }
        if (nbTrue == 0) {
            _out.put(clauses);
            return;
        }

        final int[][] newClauses = new int[clauses.length - nbTrue][];
        int i = 0;
        int j = 0;
        while (i < clauses.length) {
            if (Clause.isTrue(clauses[i])) {
            } else {
                newClauses[j] = clauses[i];
                j++;
            }
            i++;
        }
        _out.put(newClauses);
	}

	@Override
	public boolean solve() {
		return _out.solve();
	}

	@Override
	public boolean solve(int[] ass) {
		return _out.solve(ass);
	}

	@Override
	public boolean solve(Collection<Integer> ass) {
		return _out.solve(ass);
	}
}
