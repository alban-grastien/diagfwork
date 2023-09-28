/*
 * MapVariableAssigner.java
 *
 * Created on 23 April 2007, 12:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.sat;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of a variable assigner as a map.  This implementation can be 
 * very bad if it contains a lot of elements.
 *
 * @author Alban Grastien
 * @version 1.0.2
 * @since 1.0.2
 */
public class MapVariableAssigner implements VariableAssigner {

    /**
     * A map <code>VariableSemantics -> Integer</code> that gives the 
     * propositional variable associated with the variable semantics assigned by 
     * this <code>VariableAssigner</code>.
     */
    private final Map<VariableSemantics, Integer> _map;

    /** 
     * Creates an empty instance of MapVariableAssigner.
     */
    public MapVariableAssigner() {
        _map = new HashMap<VariableSemantics, Integer>();
    }

    /**
     * Gives the propositional variable associated with the specified
     * variable semantics if existing.  The method {@link
     * #surelyGetVariable(VariableSemantics)} should be used in case the assigner
     * is not allowed not to find the propositional variable.
     *
     * @param sem the semantics of the propositional variable that is
     * required.
     * @return the propositional variable (i.e an integer) associated
     * with <code>sem</code>, 0 if the variable assigner does not
     * associate any element to <code>sem</code>.
     */
    @Override
    public int getVariable(VariableSemantics sem) {
        Integer integer = _map.get(sem);
        if (integer == null) {
            return 0;
        }
        return integer.intValue();
    }

    /**
     * Gives the propositional variable associated with the specified
     * variable semantics and throws an exception if the variable is
     * not found.  This method should be preferred to {@link
     * #getVariable(VariableSemantics)} if a propositional variable
     * should be found in all cases.
     *
     * @param sem the semantics of the propositional variable that is
     * required.
     * @return the propositional variable (i.e an integer) associated
     * with <code>sem</code>.
     * @throws NoSuchElementException if no proposition variable is
     * associated with <code>sem</code> in this assigner.
     */
    @Override
    public int surelyGetVariable(VariableSemantics sem) {
        final int result = getVariable(sem);
        if (result == 0) {
            throw new java.util.NoSuchElementException();
        }

        return result;
    }

    /**
     * Prints a description of all the propositional variables that
     * can be assigned by this assigner in the specified print stream.
     * The description contains one variable per line, and starts with
     * the propositional variable.  For instance:<p />
     *
     * <pre> 1 [Semantics 1]
     * 2 [Semantics 2]
     * 3 [1 OR 2]</pre><p />
     *
     * The implementation of this method should describe the variable
     * in incremental order.
     *
     * @param out the output stream where the description is to be
     * printed.  If this output stream is <code>null</code>, nothing
     * is printed.
     */
    @Override
    public void print(java.io.PrintStream out) {
        for (final Map.Entry<VariableSemantics, Integer> entry: _map.entrySet()) {
            out.println(entry.getValue() + "\t" + entry.getKey());
        }
    }

    /**
     * Adds a mapping <code>semantics variable -> integer</code>.
     *
     * @param sem the variable semantics.
     * @param var the literal associated with <code>sem</code>.
     * @return the literal already associated with <code>sem</code> if existing, 
     * <code>0</code> otherwise.
     */
    public int add(VariableSemantics sem, int var) {
        final Integer integer = _map.put(sem, new Integer(var));
        if (integer == null) {
            return 0;
        }

        return integer.intValue();
    }

	@Override
	public void copy(MapVariableAssigner map) {
		for (final Entry<VariableSemantics, Integer> ent: _map.entrySet()) {
			map.add(ent.getKey(), ent.getValue());
		}
	}
}
