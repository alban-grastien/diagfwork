/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import java.util.Set;

/**
 * An <code>automaton minimizer</code> is able to minimize an automaton.  
 * Minimizing an automaton consists in merging states that have equivalent
 * outgoing transitions.  The states of the generated automaton are labeled with
 * {@link Set} objects.
 *
 * @author Alban Grastien
 */
public interface AutomatonMinimizer {

    /**
     * Creates a new automaton corresponding to the minimization of the
     * specified one.
     *
     * @param auto the automaton to minimize.
     * @return a minimized version of the specified automaton where the states
     * are labeled by the set of labels in the original automaton.
     */
    public <SL,TL> Automaton<Set<SL>,TL>
            minimize(ReadableAutomaton<SL,TL> auto);

    /**
     * Minimizes of the specified automaton.  The states removed are chosen
     * randomly.
     *
     * @param auto the automaton to minimize.
     */
    public <SL,TL> void
            doMinimize(Automaton<SL,TL> auto);

}
