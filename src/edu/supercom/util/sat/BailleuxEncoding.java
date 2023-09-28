/*
 * BailleuxEncoding.java
 *
 * Created on 16 May 2008, 17:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.sat;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the totalizer encoding proposed by Bailleux and Boufkhad.
 *
 * @author Alban Grastien
 * @version 2.0
 */
public class BailleuxEncoding implements TotalizerEncoding {

    @Override
    public Totalizer encode(int n) {
        return new BailleuxTotalizer(n);
    }

    @Override
    public Totalizer encode(Totalizer n1, Totalizer n2) {
        return new BailleuxTotalizer((BailleuxTotalizer) n1, (BailleuxTotalizer) n2);
    }
}

class BailleuxTotalizer implements Totalizer {

    /**
     * The set of variables of the node.
     */
    private final List<Integer> _vars;
    /**
     * The number of leaves in the subtree of this node.
     */
    private final int _max;
    /**
     * First child (if any)
     */
    private final BailleuxTotalizer _n1;
    /**
     * Second child (if any)
     */
    private final BailleuxTotalizer _n2;

    /**
     * Creates a node on a single Variable.
     *
     * @param var the variable.
     */
    public BailleuxTotalizer(int var) {
        _vars = new ArrayList(1);
        _vars.add(new Integer(var));
        _max = 1;
        _n1 = null;
        _n2 = null;
    }

    /**
     * Creates a node that has the two specified children.
     *
     * @param n1 the first node.
     * @param n2 the second node.
     */
    public BailleuxTotalizer(BailleuxTotalizer n1, BailleuxTotalizer n2) {
        _n1 = n1;
        _n2 = n2;
        _max = n1._max + n2._max;
        _vars = new ArrayList();
    }

    @Override
    public int max() {
        return _max;
    }

    @Override
    public void ge(int val, ClauseStream out) {
        if (val <= 0) {
            return;
        }

        if (val > max()) {
            throw new IllegalArgumentException();
        }

        out.put(_vars.get(val - 1).intValue());
    }

    @Override
    public void less(int val, ClauseStream out) {
        if (val > max()) {
            throw new IllegalArgumentException();
        }

        out.put(-(_vars.get(val - 1)).intValue());
    }

    @Override
    public void upgrade(VariableLoader loader, ClauseStream out) {
        if (_vars.size() >= _max) {
            return;
        }

        if (_n1 != null) { // look for children
            if (_n1._vars.size() == _vars.size()) {
                _n1.upgrade(loader, out);
            }
            if (_n2._vars.size() == _vars.size()) {
                _n2.upgrade(loader, out);
            }
        }

        final int newvar = loader.allocate(1);
        _vars.add(new Integer(newvar));

        final int size = _vars.size();

        // n1 >= i /\ n2 >= j => n1 + n2 >= i + j
        for (int i = 0; i <= size; i++) {
            final int j = size - i;
            if (i > _n1._max) {
                continue;
            }
            if (j > _n2._max) {
                continue;
            }

            if (i == 0) {
                int var = _n2.var(j);
                out.put(-var, newvar);
            } else if (j == 0) {
                int var = _n1.var(i);
                out.put(-var, newvar);
            } else {
                int var1 = _n1.var(i);
                int var2 = _n2.var(j);
                out.put(-var1, -var2, newvar);
            }
        }

        // n1 + n2 >= i + j -1 => n1 >= i \/ n2 >= j
        for (int i = 1; i <= size; i++) {
            final int j = size + 1 - i;
            if (i == _n1._max + 1) {
                final int var = _n2.var(j);
                out.put(var, -newvar);
            } else if (i > _n1._max) {
                continue;
            } else if (j == _n2._max + 1) {
                final int var = _n1.var(i);
                out.put(var, -newvar);
            } else if (j > _n2._max) {
                continue;
            } else {
                final int var1 = _n1.var(i);
                final int var2 = _n2.var(j);
                out.put(var1, var2, -newvar);
            }
        }
    }

    /**
     * Returns the variable associated with specified number.
     *
     * @param n the number of the variable.
     * @return the variable associated with n.
     */
    int var(int n) {
        return _vars.get(n - 1).intValue();
    }
}
