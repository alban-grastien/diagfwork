/*
 * Graph.java
 *
 * Created on 14 February 2007, 10:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.auto;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


/**
 * This class implements a graph.  A graph is basically a collection of states
 * and transitions between pairs of states.  The behaviour of an object of this
 * class in case bad parameters are given (for instance if the method of a graph
 * is called with a state that does not belong to the graph) is unspecified;
 * it may not fail.
 *
 * @author Alban Grastien
 * @version 2.0
 * @see State
 * @see Transition
 * @since 0.5
 */
public interface DirectedGraph {

    /**
     * Creates a new state in the graph.
     *
     * @return the new state created.
     */
    public State newState();

    /**
     * Creates a transition between the two specified states.   The states can
     * be identical (in this case, this is a loop).
     *
     * @param state1 the origin of the transition.
     * @param state2 the target of the transition.
     * @return a transition between <code>state1</code> and <code>state2</code>.
     * @throws NullPointerException if <code>state1 == null</code> or
     * <code>state2 == null</code>.
     */
    public Transition newTransition(State state1, State state2);

    /**
     * Removes the specified state from this graph.  The state must have no
     * outgoing or incoming transition.
     *
     * @param state the state to remove.
     * @return <code>true</code> if this state has been deleted in this graph.
     * @throws IllegalArgumentException if <code>state</code> has transitions.
     */
    public boolean remove(State state);

    /**
     * Removes the transition of the graph.
     *
     * @param trans the transition to remove.
     * @return <code>true</code> if the transition was removed.
     * @throws NullPointerException if <code>trans == null</code>.
     */
    public boolean remove(Transition trans);

    /**
     * Removes the transitions of the specified state.  This implementation is
     * much more efficient than using {@link #remove(Transition)} on every
     * transition of the state.
     *
     * @param state the state whose transitions are removed.
     */
    public void removeTransitions(State state);

    /**
     * Removes the specified state and all the transitions on this state.
     *
     * @param state the state to remove.
     * @return <code>true</code> if the state was removed.
     * @throws IllegalArgumentException if <code>state == null</code>.
     */
    public boolean removeAll(State state);

    /**
     * Returns an iterator on the states of the graph.  The <code>remove()</code>
     * method cannot be applied on this iterator.
     *
     * @return an iterator on the states of the graph.
     */
    public Iterator<? extends State> stateIterator();

    /**
     * Returns an iterator on the transitions of this graph.
     *
     * @return an iterator on the transitions of this graph.
     */
    public Iterator<? extends Transition> transitionIterator();

    /**
     * Returns the set of states in this graph.
     *
     * @return the unmodifiable set of states in this graph.
     */
    public Set<? extends State> states();

    /**
     * Returns the set of transitions in this graph.
     *
     * @return the unmodifiable set of states in this automaton.
     */
    public Set<? extends Transition> transitions();

    /**
     * Returns a copy of the set of states.
     *
     * @deprecated Use <code>new HashSet<State>(states())</code> instead.
     * @return a copy of the set of states.
     */
    public Set<? extends State> copyOfTheStates();


    // State -> transitions

    /**
     * Returns an iterator on the outgoing transitions of the specified state.  An
     * outgoing transition of a state is a transition such that the origin is
     * the state.
     *
     * @param s the state whose outgoing transitions are required.
     * @return an iterator on the outgoing transitions of <code>s</code>.
     */
    public Iterator<? extends Transition> outgoingTransIterator(State s);

    /**
     * Returns an iterator on the incoming transitions of the specified state.
     * An incoming transition of a state is a transition such that the target is
     * the state.
     *
     * @param s the state whose incoming transitions are required.
     * @return an iterator on the incoming transitions in <code>s</code>.
     */
    public Iterator<? extends Transition> incomingTransIterator(State s);

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
}
