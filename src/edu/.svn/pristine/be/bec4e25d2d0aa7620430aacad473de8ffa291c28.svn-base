/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto;

import edu.supercom.util.auto.changer.Identity;
import edu.supercom.util.auto.op.SimpleRefiner;
import edu.supercom.util.auto.label.DatedLabel;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.Map;
import edu.supercom.util.auto.changer.LabelChanger;
import edu.supercom.util.auto.io.DotWriter;
import edu.supercom.util.auto.op.SimpleCopier;
import edu.supercom.util.auto.op.SimpleLabelChanger;
import edu.supercom.util.auto.op.SimpleMinimizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * A simple implementation of automaton based on a graph.  In this
 * implementation, no two states can have the same label.  
 *
 * @author Alban Grastien
 * @since 2.0
 * @version 2.0
 */
public class SimpleAutomaton<SL,TL>
        extends AbstractAutomaton<SL,TL>
        implements Automaton<SL,TL> {
    /**
     * The graph of the automaton.
     */
    private final DirectedGraph _g;

    /**
     * The set of initial states.
     */
    private final Set<State> _initial;

    /**
     * The set of final states.
     */
    private final Set<State> _final;

    /**
     * A mapping <code>State -> StateLabel</code> that associates each state of
     * the automaton to its label.
     */
    private final Map<State,SL> _stateToLabel;

    /**
     * A mapping <code>Trans -> TransLabel</code> that associates each transition
     * of the automaton to its label.
     */
    private final Map<Transition,TL> _transToLabel;

    /**
     * A label associated with a loop on each state.
     */
    private TL _emptyTransLabel;

    /**
     * A mapping that associates any state label to its corresponding state.
     */
    private final Map<SL,State> _labelToState;


    /**
     * Creates a new automaton.  The transition label specified indicates what
     * transition labels loops on the state.  The state can obviously have other
     * looping transitions.
     *
     * @param label the label of the looping transitions on each state.
     */
    public SimpleAutomaton(TL label) {
        this._emptyTransLabel = label;
        this._g = new SimpleDirectedGraph();
        this._initial = new HashSet<State>();
        this._final = new HashSet<State>();
        this._stateToLabel = new HashMap<State, SL>();
        this._transToLabel = new HashMap<Transition, TL>();
        this._labelToState = new HashMap<SL, State>();
    }

    @Override
    public TL getEmptyTransitionLabel() {
        return this._emptyTransLabel;
    }

    @Override
    public State newState(SL label) {
        if (_labelToState.get(label) == null) {
            final State result = _g.newState();
            _labelToState.put(label, result);
            _stateToLabel.put(result, label);
            return result;
        } else {
            return null;
        }
    }

    @Override
    public boolean remove(State state) {
        if (state == null) {
            throw new NullPointerException();
        }

        if (!this._g.remove(state)) {
            return false;
        }

        final SL sl = _stateToLabel.remove(state);
        _labelToState.remove(sl);

        _initial.remove(state);
        _final.remove(state);

        return true;
    }

    @Override
    public SL stateLabel(State state) {
        return _stateToLabel.get(state);
    }

    @Override
    public State getState(SL label) {
        return _labelToState.get(label);
    }

    @Override
    public boolean setInitial(State state) {
        if (state == null) {
            throw new NullPointerException();
        }
        return _initial.add(state);
    }

    @Override
    public boolean unsetInitial(State state) {
//        if (state == null) {
//            throw new NullPointerException();
//        }
        return _initial.remove(state);
    }

    @Override
    public boolean isInitial(State state) {
        return _initial.contains(state);
    }

    @Override
    public boolean setFinal(State state) {
        if (state == null) {
            throw new NullPointerException();
        }
        return _final.add(state);
    }

    @Override
    public boolean unsetFinal(State state) {
//        if (state == null) {
//            throw new NullPointerException();
//        }
        return _final.remove(state);
    }

    @Override
    public boolean isFinal(State state) {
        return _final.contains(state);
    }

    @Override
    public Transition newTransition(State state1, State state2, TL label) {
        final Transition result = _g.newTransition(state1,state2);
        _transToLabel.put(result,label);
        return result;
    }

    @Override
    public boolean remove(Transition trans) {
        final boolean result = this._g.remove(trans);
        if (result) {
            this._transToLabel.remove(trans);
        }
        return result;
    }

    @Override
    public TL transLabel(Transition trans) {
        return this._transToLabel.get(trans);
    }

    @Override
    public void removeAll(State state) {
        // Outgoing transitions
        for (final Transition trans: new ArrayList<Transition>(outgoingTrans(state))) {
            remove(trans);
        }
        // Incoming transitions
        for (final Transition trans: new ArrayList<Transition>(incomingTrans(state))) {
            remove(trans);
        }
        remove(state);
    }

    @Override
    public Set<? extends State> states() {
        return _g.states();
    }

    @Override
    public Set<? extends Transition> transitions() {
        return _g.transitions();
    }

    @Override
    public Set<? extends State> finalStates() {
        return Collections.unmodifiableSet(_final);
    }

    @Override
    public Set<? extends State> initialStates() {
        return Collections.unmodifiableSet(_initial);
    }

    @Override
    public String toDot(String name) {
        return new DotWriter().toString(this);
    }

    @Override
    public SL changeStateLabel(State s, SL l) {
        if (!states().contains(s)) {
            throw new NoSuchElementException();
        }
        final State other = _labelToState.get(l);
        if (other != null && !other.equals(s)) {
            throw new IllegalArgumentException("State does not exist.");
        }
        final SL old = _stateToLabel.put(s, l);
        _labelToState.remove(old);
        _labelToState.put(l, s);
        return old;
    }

    @Override
    public TL changeTransLabel(Transition t, TL l) {
        if (!transitions().contains(t)) {
            throw new NoSuchElementException();
        }
        return _transToLabel.put(t, l);
    }

    @Override
    public TL setEmptyTransitionLabel(TL empty) {
        final TL result = _emptyTransLabel;
        _emptyTransLabel = empty;
        return result;
    }

    //////////////// OPERATIONS

    @Override
    public void irefine() {
        new SimpleRefiner().irefine(this);
    }

    @Override
    public void frefine() {
        new SimpleRefiner().frefine(this);
    }

    @Override
    public void trim() {
        irefine();
        frefine();
    }

    @Override
    public Automaton<SL,TL> copy() {
        return new SimpleCopier<SL,TL,SL,TL>(new Identity<SL>(), new Identity<TL>()).copy(this);
    }

    @Override
    public void changeTransLabels(LabelChanger<? super TL, ? extends TL> cl){
        new SimpleLabelChanger().changeTransLabels(this, cl);
    }

    @Override
    public void changeStateLabels(LabelChanger<? super SL,? extends SL> cl){
        new SimpleLabelChanger().changeStateLabels(this, cl);
    }

    @Override
    public void changeLabels(LabelChanger<? super SL,? extends SL> sm, LabelChanger<? super TL,? extends TL> tm) {
        new SimpleLabelChanger().changeLabels(this, sm,tm);
    }

    @Override
    public void doMinimize() {
        new SimpleMinimizer().doMinimize(this);
    }

    @Override
    public Automaton<Set<SL>,TL> minimize() {
        return new SimpleMinimizer().minimize(this);
    }

    public Automaton[] slicing(Set<State>[] setStates){
        final Automaton[] res = new Automaton[setStates.length+1];
        Set<State> finalStates = new HashSet<State>();

        for (int i=0;i<setStates.length;i++){
            final Set<State> states = setStates[i];
            final Automaton<SL,TL> a = this.copy();

            if(i!=0){
                for(final State state: this.initialStates()){
                    a.unsetInitial(a.getState(this.stateLabel(state)));
                }
                for(final State state: this.finalStates()){
                    a.setInitial(a.getState(this.stateLabel(state)));
                }
            }
            for(final State state: this.finalStates()){
                a.unsetFinal(a.getState(this.stateLabel(state)));
            }
            for(final State origState: states){
                final State copyState = a.getState(this.stateLabel(origState));
                a.setFinal(copyState);
            }
            finalStates = states;

            a.trim();
            res[i]=a;
        }

        // last automaton
        final Automaton a = this.copy();
        for(final State state: this.initialStates()){
            a.unsetInitial(a.getState(this.stateLabel(state)));
        }
        for(final State origState: finalStates){
            a.setInitial(a.getState(this.stateLabel(origState)));
        }
        a.trim();
        res[setStates.length]=a;

        return res;
    }

    public Automaton[] slicing(int[] time){
        final Set<State>[] setStates = new Set[time.length];
        for(int i=0;i<time.length;i++){
            if(i!=0){
                if(time[i-1] > time[i]){
                    throw new IllegalArgumentException();
                }
            }
            final Set<State> states = new HashSet<State>();
            int val = time[i];
            for(final State state: this.states()){
                final DatedLabel dl = (DatedLabel)this.stateLabel(state);
                if(dl.getMin()<=val && val<=dl.getMax()){
                    states.add(state);
                }
            }
            setStates[i]=states;
        }

        return slicing(setStates);
    }

    @Override
    public <SL,TL> Automaton<SL,TL> buildAutomaton(TL tl) {
        return new SimpleAutomaton<SL, TL>(tl);
    }

    @Override
    public Collection<? extends Transition> outgoingTrans(State s) {
        return _g.outgoingTrans(s);
    }

    @Override
    public Collection<? extends Transition> incomingTrans(State s) {
        return _g.incomingTrans(s);
    }

    @Override
    public boolean hasTransitions(State s) {
        return _g.hasTransitions(s);
    }

    @Override
    public boolean hasOutgoingTransitions(State s) {
        return _g.hasOutgoingTransitions(s);
    }

    @Override
    public boolean hasIncomingTransitions(State s) {
        return _g.hasIncomingTransitions(s);
    }

    @Override
    public Collection<SL> stateLabels() {
        return Collections.unmodifiableCollection(_stateToLabel.values());
    }

}
