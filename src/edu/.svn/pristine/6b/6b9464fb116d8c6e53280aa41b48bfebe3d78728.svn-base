/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;

/**
 * An automaton refiner is an object that is able to perform a refinement, i.e.
 * that is able to remove the states that cannot be reached from an initial
 * state or from which no final state can be reached.  
 *
 * @author Alban Grastien
 */
public interface AutomatonRefiner {

    /**
     * Removes the states of the specified automaton that cannot be reached from
     * an initial state.
     *
     * @param auto the automaton on which the refinement must be performed.
     */
    public void irefine(Automaton<?,?> auto);

    /**
     * Removes the states of the specified automaton from which no final state
     * can be reached.
     *
     * @param auto the automaton on which the refinement must be performed.  
     */
    public void frefine(Automaton<?,?> auto);

    /**
     * Removes the states that cannot be reached from an initial state or from
     * which no final state can be reached.
     *
     * @param auto the automaton on which the refinement must be performed.
     */
    public void trim(Automaton<?,?> auto);
}
