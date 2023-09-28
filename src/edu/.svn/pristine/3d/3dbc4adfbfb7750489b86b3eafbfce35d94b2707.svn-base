/*
 * UnionVariableAssigner.java
 *
 * Created on 2 March 2007, 15:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.sat;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of variable assigner as the union of variable assigner.
 *
 * @author Alban Grastien
 * @version 2.1
 * @since 1.0
 */
public class UnionVariableAssigner implements VariableAssigner {

    /** 
     * The list of the variable assigners.
     */
    final Collection<VariableAssigner> _varass;

    /**
     * Creates a variable assigner as the union as the specified variable assigners.
     *
     * @param coll a collection of <code>VariableAssigner</code> objects.
     * @throws ClassCastException if an element of <code>coll</code> is not a <code>VariableAssigner</code>.
     */
    public UnionVariableAssigner(Collection<VariableAssigner> coll) {
        _varass = new ArrayList<VariableAssigner>(coll);
        //for (final VariableAssigner varAss : coll) {
        //    _varass.add(varAss);
        //}
    }

    /**
     * Creates an empty variable assigner.
     */
    public UnionVariableAssigner() {
        _varass = new ArrayList<VariableAssigner>();
    }

    /**
     * Adds a variable assigner to the union of variable assigner.
     *
     * @param varAss the variable assigner to add.
     */
    public void addVariableAssigner(VariableAssigner varAss) {
        if (varAss != null) {
            _varass.add(varAss);
        }
    }

    @Override
    public int getVariable(VariableSemantics sem) {
        for (final VariableAssigner varass: _varass) {
            int result = varass.getVariable(sem);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    @Override
    public int surelyGetVariable(VariableSemantics sem) {
        int result = getVariable(sem);
        if (result != 0) {
            return result;
        }

        throw new java.util.NoSuchElementException();
    }

    @Override
    public void print(java.io.PrintStream out) {
        for (final VariableAssigner varass: _varass) {
            varass.print(out);
        }
    }

	@Override
	public void copy(MapVariableAssigner map) {
		for (final VariableAssigner varass: _varass) {
			varass.copy(map);
		}
	}
}
