/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple implementation of AutomatonRefiner.  This implementation first
 * enumerates all the states in the automaton and removes them one after the
 * one.
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 2.0
 */
public class SimpleRefiner implements AutomatonRefiner {

    // TODO: improve
    // * create internal methods to hide auto and toRemove
    // * reverse toRemove ? (start with empty)

    @Override
    public void irefine(Automaton<?,?> auto) {
        Set<State> toRemove = new HashSet<State>(auto.states());
        for (final State state: auto.initialStates()) {
            removeForward(auto,state,toRemove);
        }
        for (Iterator<State> stateIt = toRemove.iterator() ; stateIt.hasNext() ; ) {
            State state = stateIt.next();
            auto.removeAll(state);
        }
    }

    @Override
    public void frefine(Automaton<?,?> auto) {
        Set<State> toRemove = new HashSet<State>(auto.states());
        for (final State state: auto.finalStates()) {
            removeBackward(auto,state,toRemove);
        }
        for (Iterator<State> stateIt = toRemove.iterator() ; stateIt.hasNext() ; ) {
            State state = stateIt.next();
            auto.removeAll(state);
        }
    }

    /**
     * Recursivly removes from the specified set of states the states that
     * can be reached from the specified state.
     *
     * @param auto the automaton on which the removal is performed.  
     * @param state the current state.
     * @param set the set of states.
     */
    private static void removeForward(Automaton auto, State state, Collection<State> set) {
        if (set.remove(state)) {
            final Collection<? extends Transition> coll = auto.outgoingTrans(state);
            for (final Transition trans: coll) {
                State newState = trans.getTarget();
                removeForward(auto,newState,set);
            }
        }
    }

    /**
     * Recursivly removes from the specified set of states the states that
     * can reach the specified state.
     *
     * @param auto the automaton on which the removal is performed.  
     * @param state the current state.
     * @param set the set of states.
     */
    private static void removeBackward(Automaton auto, State state, Collection<State> set) {
        if (set.remove(state)) {
            final Collection<? extends Transition> coll = auto.incomingTrans(state);
            for (final Transition trans: coll) {
                State newState = trans.getOrigin();
                removeBackward(auto,newState,set);
            }
        }
    }

    @Override
    public void trim(Automaton auto) {
        irefine(auto);
        frefine(auto);
    }
}
