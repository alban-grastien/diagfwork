/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple implementation of a directed graph.  
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 2.0
 */
public class SimpleDirectedGraph implements DirectedGraph {


    /**
     * The set of states in the graph.
     */
    private final Set<SimpleState> _states;
    /**
     * The set of transitions in the graph.
     */
    private final Set<SimpleTransition> _trans;

    /** Creates a new instance of Graph */
    public SimpleDirectedGraph() {
        _states = new HashSet<SimpleState>();
        _trans  = new HashSet<SimpleTransition>();
    }

    @Override
    public State newState() {
        final SimpleState result = new SimpleState();
        _states.add(result);
        return result;
    }

    @Override
    public Transition newTransition(State state1, State state2) {
        if (state1 == null || state2 == null) {
            throw new NullPointerException();
        }

        final SimpleState ss1 = (SimpleState)state1;
        final SimpleState ss2 = (SimpleState)state2;

        final SimpleTransition result = new SimpleTransition(ss1, ss2);

        ss1.addOutgoingTransition(result);
        ss2.addIncomingTransition(result);

        _trans.add(result);
        return result;
    }

    @Override
    public boolean remove(State state) {
        if (hasTransitions(state)) {
            throw new IllegalArgumentException();
        }
        return _states.remove(state);
    }

    @Override
    public boolean remove(Transition trans) {
        final SimpleTransition strans = (SimpleTransition)trans;
        final SimpleState state1 = strans.getOrigin();
        final SimpleState state2 = strans.getTarget();
        final boolean result =
                   state1.removeOutgoingTransition(strans)
                && state2.removeIncomingTransition(strans)
                && _trans.remove(trans);
        return result;
    }

    @Override
    public void removeTransitions(State state) {
        final SimpleState ss = (SimpleState)state;

        for (final Iterator<? extends Transition> transIt = incomingTransIterator(state); transIt.hasNext();) {
            final SimpleTransition trans = (SimpleTransition)transIt.next();
            //trans.erase();
            trans.getOrigin().removeOutgoingTransition(trans);
            this._trans.remove(trans);
        }

        for (final Iterator<? extends Transition> transIt = outgoingTransIterator(state); transIt.hasNext();) {
            final SimpleTransition trans = (SimpleTransition)transIt.next();
            //trans.erase();
            trans.getTarget().removeIncomingTransition(trans);
            this._trans.remove(trans);
        }

        ss.erase();
    }

    @Override
    public boolean removeAll(State state) {
        removeTransitions(state);
        return remove(state);
    }

    @Override
    public Iterator<? extends State> stateIterator() {
        return Collections.unmodifiableCollection(_states).iterator();
    }

    @Override
    public Iterator<? extends Transition> transitionIterator() {
        return Collections.unmodifiableCollection(this._trans).iterator();
    }

    @Override
    public Set<? extends State> states() {
        return Collections.unmodifiableSet(_states);
    }

    @Override
    public Set<? extends Transition> transitions() {
        return Collections.unmodifiableSet(_trans);
    }

    @Override
    @Deprecated
    public Set<? extends State> copyOfTheStates() {
        return new HashSet<SimpleState>(_states);
    }


    // State -> transitions

    @Override
    public Iterator<? extends Transition> outgoingTransIterator(State s) {
        return ((SimpleState)s).outgoingTransIterator();
    }

    @Override
    public Iterator<? extends Transition> incomingTransIterator(State s) {
        return ((SimpleState)s).incomingTransIterator();
    }

    @Override
    public Collection<? extends Transition> outgoingTrans(State s) {
        return ((SimpleState)s).outgoingTrans();
    }

    @Override
    public Collection<? extends Transition> incomingTrans(State s) {
        return ((SimpleState)s).incomingTrans();
    }

    @Override
    public boolean hasTransitions(State s) {
        return ((SimpleState)s).hasTransitions();
    }

    @Override
    public boolean hasOutgoingTransitions(State s) {
        return ((SimpleState)s).hasOutgoingTransitions();
    }

    @Override
    public boolean hasIncomingTransitions(State s) {
        return ((SimpleState)s).hasIncomingTransitions();
    }
}

class SimpleState implements State {
    /**
     * The set of outgoing transitions of this state.
     */
    private final Set<SimpleTransition> _outgoingTransitions;
    /**
     * The set of incoming transitions of this state.
     */
    private final Set<SimpleTransition> _incomingTransitions;

    SimpleState() {
        _outgoingTransitions = new HashSet<SimpleTransition>();
        _incomingTransitions = new HashSet<SimpleTransition>();
    }

    boolean addOutgoingTransition(SimpleTransition trans) {
        return _outgoingTransitions.add(trans);
    }

    boolean removeOutgoingTransition(SimpleTransition trans) {
        return _outgoingTransitions.remove(trans);
    }

    boolean addIncomingTransition(SimpleTransition trans) {
        return _incomingTransitions.add(trans);
    }

    boolean removeIncomingTransition(SimpleTransition trans) {
        return _incomingTransitions.remove(trans);
    }

    void erase() {
        _incomingTransitions.clear();
        _outgoingTransitions.clear();
    }

    public Iterator<SimpleTransition> outgoingTransIterator() {
        return Collections.unmodifiableCollection(_outgoingTransitions).iterator();
    }

    public Iterator<SimpleTransition> incomingTransIterator() {
        return Collections.unmodifiableCollection(_incomingTransitions).iterator();
    }

    public Collection<SimpleTransition> outgoingTrans() {
        return Collections.unmodifiableCollection(_outgoingTransitions);
    }

    public Collection<SimpleTransition> incomingTrans() {
        return Collections.unmodifiableCollection(_incomingTransitions);
    }

    @Override
    public int compareTo(State s) {
        return hashCode() - s.hashCode();
    }

    public boolean hasTransitions() {
        if (this._incomingTransitions.isEmpty() &&
                this._outgoingTransitions.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasOutgoingTransitions() {
        return !(this._outgoingTransitions.isEmpty());
    }

    public boolean hasIncomingTransitions() {
        return !(_incomingTransitions.isEmpty());
    }
}

class SimpleTransition implements Transition {
    /**
     * The origin state.
     */
    private final SimpleState _origin;
    /**
     * The target state.
     */
    private final SimpleState _target;

    /**
     * Creates a new transition.  This contructor is only to be used by the
     * Graph class.
     *
     * @param origin the origin of the transition.
     * @param target the target of the transition.
     */
    public SimpleTransition(SimpleState origin, SimpleState target) {
        _origin = origin;
        _target = target;
    }

    @Override
    public SimpleState getOrigin() {
        return _origin;
    }

    @Override
    public SimpleState getTarget() {
        return _target;
    }

    /**
     * Erases all the references in this transition.  This method is only to be
     * used by the Graph class.
     */
    void erase() {
//        _origin = null;
//        _target = null;
    }

    @Override
    public int compareTo(Transition t) {
        return hashCode() - t.hashCode();
    }

}
