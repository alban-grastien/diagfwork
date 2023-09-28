package edu.supercom.util.auto;

import edu.supercom.util.auto.changer.LabelChanger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * A <code>WrappedAutomaton</code>, i.e., a wrapped automaton, 
 * is a modifiable automaton that is ''wrapped'' around an existing automaton.  
 * By default, the wrapped automaton calls 
 * the methods of the automaton it is wrapped around; 
 * however, extensions of wrapped automaton may have their own implementations 
 * of some methods.  
 * 
 * @param <SL> the type of state labels.  
 * @param <TL> the type of transition labels.  
 */
public abstract class WrappedAutomaton<SL,TL> implements Automaton<SL,TL> {
    
    /**
     * The automaton this automaton is wrapped around.  
     */
    protected Automaton<SL,TL> _core;
    
    /**
     * Builds a wrapped automaton around the specified automaton.  
     * 
     * @param a the automaton around which a wrapped automaton is built.  
     */
    protected WrappedAutomaton(Automaton<SL,TL> a) {
        _core = a;
    }

    @Override
    public State newState(SL label) {
        return _core.newState(label);
    }

    @Override
    public boolean remove(State state) {
        return _core.remove(state);
    }

    @Override
    public boolean setInitial(State state) {
        return _core.setInitial(state);
    }

    @Override
    public boolean unsetInitial(State state) {
        return _core.unsetInitial(state);
    }

    @Override
    public boolean setFinal(State state) {
        return _core.setFinal(state);
    }

    @Override
    public boolean unsetFinal(State state) {
        return _core.unsetFinal(state);
    }

    @Override
    public Transition newTransition(State state1, State state2, TL label) {
        return _core.newTransition(state1, state2, label);
    }

    @Override
    public boolean remove(Transition trans) {
        return _core.remove(trans);
    }

    @Override
    public void removeAll(State state) {
        _core.removeAll(state);
    }

    @Override
    public SL changeStateLabel(State s, SL l) {
        return _core.changeStateLabel(s, l);
    }

    @Override
    public TL changeTransLabel(Transition t, TL l) {
        return _core.changeTransLabel(t, l);
    }

    @Override
    public Collection<? extends State> states() {
        return _core.states();
    }

    @Override
    public SL stateLabel(State state) {
        return _core.stateLabel(state);
    }

    @Override
    public State getState(SL label) {
        return _core.getState(label);
    }

    @Override
    public Collection<? extends SL> stateLabels() {
        return _core.stateLabels();
    }

    @Override
    public Collection<? extends State> initialStates() {
        return _core.initialStates();
    }

    @Override
    public boolean isInitial(State s) {
        return _core.isInitial(s);
    }

    @Override
    public Collection<? extends State> finalStates() {
        return _core.finalStates();
    }

    @Override
    public boolean isFinal(State s) {
        return _core.isFinal(s);
    }

    @Override
    public TL getEmptyTransitionLabel() {
        return _core.getEmptyTransitionLabel();
    }

    @Override
    public Collection<? extends Transition> transitions() {
        return _core.transitions();
    }

    @Override
    public TL transLabel(Transition trans) {
        return _core.transLabel(trans);
    }

    @Override
    public Collection<? extends Transition> outgoingTrans(State s) {
        return _core.outgoingTrans(s);
    }

    @Override
    public Collection<? extends Transition> incomingTrans(State s) {
        return _core.incomingTrans(s);
    }

    @Override
    public void irefine() {
        Automata.irefine(this);
    }

    @Override
    public void frefine() {
        Automata.frefine(this);
    }

    @Override
    public void trim() {
        irefine();
        frefine();
    }

    @Override
    public void changeTransLabels(LabelChanger<? super TL, ? extends TL> cl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void changeStateLabels(LabelChanger<? super SL, ? extends SL> cl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void changeLabels(LabelChanger<? super SL, ? extends SL> sm, LabelChanger<? super TL, ? extends TL> tm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void doMinimize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Automaton<Set<SL>, TL> minimize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TL setEmptyTransitionLabel(TL empty) {
        return _core.setEmptyTransitionLabel(empty);
    }

    @Override
    public abstract <SL, TL> Automaton<SL, TL> buildAutomaton(TL tl);

    @Override
    public boolean hasTransitions(State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasOutgoingTransitions(State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasIncomingTransitions(State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toDot(String name) {
        return _core.toDot(name);
    }

    @Override
    public Automaton<SL, TL> copy() {
        final Automaton<SL,TL> result = this.buildAutomaton(_core.getEmptyTransitionLabel());
        
        final Map<State,State> oldToNew = new HashMap<State, State>();
        for (final State oldState: states()) {
            final SL label = stateLabel(oldState);
            final State newState = result.newState(label);
            oldToNew.put(oldState, newState);
            if (isInitial(oldState)) {
                result.setInitial(newState);
            }
            if (isFinal(oldState)) {
                result.setFinal(newState);
            }
        } 
        
        for (final Transition transition: transitions()) {
            final State oldO = transition.getOrigin();
            final State newO = oldToNew.get(oldO);
            final State oldT = transition.getTarget();
            final State newT = oldToNew.get(oldT);
            final TL label = transLabel(transition);
            result.newTransition(newO, newT, label);
        }
        
        return result;
    }
}
