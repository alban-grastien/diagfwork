/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;

/**
 * An <b>epsilon reductor</b> is an object that is able to draw transitions in
 * an automaton between states by short-cutting epsilon transitions.  Any
 * sequence of transitions between two states <code>s1</code> and
 * <code>s2</code> that is exactly a number of epsilon transitions plus a non
 * epsilon transition (the last transition of the sequence) will lead to an
 * transition between these two states labeled by the label of the non epsilon
 * transition.  The states that can be reached from an initial state by a
 * sequence of epsilon transitions will be labeled as initial, and the states
 * from which a final state can be reached by a sequence of epsilon transitions
 * will be labeled final as well.  States that were originally reachable only
 * from epsilon transitions may then be removed (except initial and final
 * states).
 *
 * @author Alban Grastien
 */
public interface EpsilonReductor {

    /**
     * Runs the epsilon reduction on the specified automaton.  The epsilon
     * transitions are represented by transitions labeled by a label that is
     * equal to <code>auto.getEmptyTransitionLabel()</code>.
     *
     * @param auto the automaton on which the reduction is performed.
     */
    public <SL, TL> void runReduction(Automaton<SL,TL> auto);

}
