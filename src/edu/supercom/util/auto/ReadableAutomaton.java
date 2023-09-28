/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto;

import java.util.Collection;
import java.util.Iterator;

/**
 * A simple version of an automaton that allows only to read the automaton and
 * does not allow to modify it.  This will be replaced by an unmodifiable
 * automaton some day...
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 2.0
 */
public interface ReadableAutomaton<SL,TL>
extends AutomatonFactory {

    // State related

    /**
     * Returns the set of states in this automaton.
     *
     * @return the unmodifiable set of states in this automaton.
     */
    public Collection<? extends State> states();

    /**
     * Returns the label of the specified state.
     *
     * @param state the state whose label is required.
     * @return the label of <code>state</code>, <code>null</code> if the state
     * is not in the automaton.
     */
    public SL stateLabel(State state);

    /**
     * Returns the state with the specified label.
     *
     * @param label the label of the state.
     * @return the state labeled by <code>label</code>, <code>null</code> if
     * no such state exists.
     */
    public State getState(SL label);

    /**
     * Returns the collection of state labels.  Note that this collection is not
     * a set, and that it may contain the same element more than once.
     *
     * @return the collection of labels of the states in this automaton.
     */
    public Collection<? extends SL> stateLabels();

    /** Indicates whether this state is an initial state.
     *
     * @param state the state we check.
     * @return <code>true</code> if the state is initial.
     */
    public boolean isInitial(State state);

    /**
     * Returns the set of initial states.
     *
     * @return the unmodifiable set of initial states of this automaton.
     */
    public Collection<? extends State> initialStates();


    /** Indicates whether this state is a final state.
     *
     * @param state the state we check.
     * @return <code>true</code> if the state is final.
     */
    public boolean isFinal(State state);

    /**
     * Returns the set of final states.
     *
     * @return the unmodifiable set of final states of this automaton.
     */
    public Collection<? extends State> finalStates();

    // Transition related

    /**
     * Returns the empty transition label.  This is the label that labels a loop
     * on each state.
     *
     * @return the empty transition label.
     */
    public TL getEmptyTransitionLabel();

    /**
     * Returns the set of transitions in this automaton.
     *
     * @return the unmodifiable set of transitions in this automaton.
     */
    public Collection<? extends Transition> transitions();

    /**
     * Returns the label of the specified transition.
     *
     * @param trans the transition whose label is required.
     * @return the label of <code>trans</code>, <code>null</code> if the
     * transition is not in the automaton.
     */
    public TL transLabel(Transition trans);

    // State -> transitions

    /**
     * Returns an unmodifiable collection of outgoing transitions of the
     * specified state.
     *
     * @param s the state whose outgoing transitions are required.
     * @return the list of transitions outgoing from <code>s</code>.
     */
    public Collection<? extends Transition> outgoingTrans(State s);

    /**
     * Returns an unmodifiable collection of incoming transitions of the
     * specified state.
     *
     * @param s the state whose incoming transitions are required.
     * @return the list of transitions incoming in <code>s</code>.
     */
    public Collection<? extends Transition> incomingTrans(State s);

    /**
     * Indicates whether some transition comes from or leads to the specified
     * state.
     *
     * @param s the state whose transitions are checked.  
     * @return <code>true</code> if this state has transitions.
     */
    public boolean hasTransitions(State s);

    /**
     * Indicates whether the specified state has outgoing transitions.
     *
     * @param s the state whose outgoing transitions are checked.  
     * @return <code>true</code> if <code>s</code> has outgoing transitions.
     */
    public boolean hasOutgoingTransitions(State s);

    /**
     * Indicates whether the specified state has incoming transitions.
     *
     * @param s the state whose incoming transitions are checked.  
     * @return <code>true</code> if <code>s</code> has incoming transitions.
     */
    public boolean hasIncomingTransitions(State s);

    // Operations

    /**
     * Returns the string representation of this automaton in a dot format.
     *
     * @return the dot representation of this automaton.  
     */
    public String toDot(String name);

    /**
     * Creates a copy of the automaton.
     * @return an automaton which is a copy of the automaton.
     */
    public Automaton<SL,TL> copy();


}
