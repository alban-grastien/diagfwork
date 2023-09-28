package edu.supercom.util.auto;

import edu.supercom.util.Pair;
import edu.supercom.util.auto.changer.LabelChanger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A <code>BasicAutomaton</code>, i.e., a basic automaton,
 * is a modifiable automaton implemented with very basic methods.
 * It is a map based implementation,
 * but the following methods are supported in a very naive (inefficient) way:
 * <ul>
 * <li>{@link #getStates(java.lang.Object) }
 * and {@link #getState(java.lang.Object) },
 * </li>
 * <li>{@link #stateLabels() },
 * </li>
 * <li>{@link #removeAll(ban.auto.State) },
 * </li>
 * <li>{@link #incomingTrans(ban.auto.State) }
 * and {@link #outgoingTrans(ban.auto.Transition)},
 * </li>
 * <li>{@link #initialStates()}
 * and {@link #finalStates() }. 
 * </li>
 * </ul>
 *
 * @param <SL> the type of state labels.
 * @param <TL> the type of transition labels.
 */
public class ElaborateAutomaton<SL,TL> implements Automaton<SL,TL> {

    /**
     * The collection of states.  
     */
    private final Set<State> _states;
    /**
     * The number of states created so far.
     */
    private int _nbStatesCreated = 0;
    /**
     * The set of initial states.  
     */
    private final Set<State> _initial;
    /**
     * The set of final states.  
     */
    private final Set<State> _final;
    /**
     * The epsilon transition label.
     */
    private TL _epsilon;
    /**
     * The collection of transitions.
     */
    private final Set<Transition> _transes;
    /**
     * The number of transitions created so far.
     */
    private int _nbTransitionCreated = 0;
    /**
     * Outgoing transitions of each state.  
     */
    private final Map<State,Set<Transition>> _out;
    /**
     * Incoming transitions of each state.  
     */
    private final Map<State,Set<Transition>> _in;
    

    /**
     * Builds an empty basic automaton.
     *
     * @param e the epsilon transition label.
     */
    public ElaborateAutomaton(TL e) {
        _states = new HashSet<State>();
        _initial = new HashSet<State>();
        _final = new HashSet<State>();
        _transes = new HashSet<Transition>();
        _epsilon = e;
        _out = new HashMap<State, Set<Transition>>();
        _in = new HashMap<State, Set<Transition>>();
    }
    
    /**
     * Static constructor for a basic automaton.  
     * 
     * @param e the epsilon transition label.  
     */
    public static <SL,TL> ElaborateAutomaton<SL,TL> newAutomaton(TL e) {
        return new ElaborateAutomaton<SL,TL>(e);
    }

    @Override
    public State newState(SL label) {
        final EState<SL> result = new EState<SL>(_nbStatesCreated, label);
        _nbStatesCreated++;
        _states.add(result);
        return result;
    }

    @Override
    public boolean remove(State state) {
        // Assuming there is no transition involving the state.
        _initial.remove(state);
        _final.remove(state);
        return _states.remove(state);
    }

    @Override
    public boolean setInitial(State state) {
        assert (_states.contains(state));
        
        if (!_states.contains(state)) {
            throw new IllegalArgumentException();
        }
        
        return _initial.add(state);
    }

    @Override
    public boolean unsetInitial(State state) {
        return _initial.remove(state);
    }

    @Override
    public boolean setFinal(State state) {
        assert (_states.contains(state));
        
        if (!_states.contains(state)) {
            throw new IllegalArgumentException();
        }
        
        return _final.add(state);
    }

    @Override
    public boolean unsetFinal(State state) {
        return _final.remove(state);
    }

    @Override
    public Transition newTransition(State state1, State state2, TL label) {
        // Check if such transition already exists.  
        {
            final Collection<? extends Transition> transes;
            {
                final Collection<? extends Transition> t1 = outgoingTrans(state1);
                final Collection<? extends Transition> t2 = incomingTrans(state2);
                if (t1.size() > t2.size()) {
                    transes = t2;
                } else {
                    transes = t1;
                }
            }
            
            for (final Transition t: transes) {
                if ((t.getOrigin() == state1)
                        && (t.getTarget() == state2) 
                        && transLabel(t).equals(label)) {
                    // A similar transition already exists.  
                    return null;
                }
            }
        }
        
        final ETransition result = new ETransition<TL>(_nbTransitionCreated, state1, state2, label);
        _nbTransitionCreated++;
        _transes.add(result);
        getOrCreateSetFromMap(state1, _out).add(result);
        getOrCreateSetFromMap(state2, _in).add(result);
        return result;
    }

    @Override
    public boolean remove(Transition trans) {
        {
            final State origin = trans.getOrigin();
            final Set<Transition> outs = getOrCreateSetFromMap(origin,_out);
            outs.remove(trans);
            if (outs.isEmpty()) {
                _out.remove(origin);
            }
        }
        {
            final State target = trans.getTarget();
            final Set<Transition> ins = getOrCreateSetFromMap(target,_in);
            ins.remove(trans);
            if (ins.isEmpty()) {
                _in.remove(target);
            }
        }
        return _transes.remove(trans);
    }

    @Override
    public void removeAll(State state) {
        final List<Transition> toRemove = new ArrayList <Transition>();
        toRemove.addAll(outgoingTrans(state));
        toRemove.addAll(incomingTrans(state));

    	for (final Transition tr: toRemove) {
                remove(tr);
    	}
        remove(state);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SL changeStateLabel(State s, SL l) {
        final EState<SL> is = (EState<SL>)s;
        final SL result = is._label;
        is._label = l;
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TL changeTransLabel(Transition t, TL l) {
        final ETransition<TL> it = (ETransition<TL>)t;
        final TL result = it._label;
        it._label = l;
        return result;
    }

    @Override
    public Collection<? extends State> states() {
        return Collections.unmodifiableSet(_states);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SL stateLabel(State state) {
        final EState<SL> is = (EState<SL>)state;
        final SL result = is._label;
        return result;
    }

    /**
     * Returns the collection of states with the specified label.  
     * This implementation is very inefficient 
     * and anybody using {@link #getState(java.lang.Object) } method 
     * with this implementation of an automaton should wrap it 
     * with an {@link StateLabelerAutomaton}.
     *
     * @param label the label of the state.
     * @return all the states that are labelled by <code>label</code>.
     */
    @Override
    public State getState(SL label) {
        for (final State s: _states) {
            if (stateLabel(s).equals(label)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Returns the collection of states with the specified label.  
     * This implementation is very inefficient 
     * and anybody using {@link #getStates(java.lang.Object) } method 
     * with this implementation of an automaton should wrap it 
     * with an {@link StateLabelerAutomaton}.
     *
     * @param label the label of the state.
     * @return all the states that are labelled by <code>label</code>.
     */
    public Collection<? extends State> getStates(final SL label) {
        final Set<State> result = new HashSet<State>();
        for (final State s: _states) {
            if (stateLabel(s).equals(label)) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Returns the collection of state labels.  
     * In this implementation, if several states share the same label, 
     * the label will be duplicated in the collection.  
     *
     * @return the collection of labels of the states in this automaton.
     */
    @Override
    public Collection<? extends SL> stateLabels() {
        final Set<SL> result = new HashSet<SL>();
        for (final State s: _states) {
            result.add(stateLabel(s));
        }
        return result;
    }

    @Override
    public Set<? extends State> initialStates() {
        return Collections.unmodifiableSet(_initial);
    }

    @Override
    public boolean isInitial(State s) {
        return _initial.contains(s);
    }

    @Override
    public Set<? extends State> finalStates() {
        return Collections.unmodifiableSet(_final);
    }

    @Override
    public boolean isFinal(State s) {
        return _final.contains(s);
    }

    @Override
    public TL getEmptyTransitionLabel() {
        return _epsilon;
    }

    @Override
    public Collection<? extends Transition> transitions() {
        return Collections.unmodifiableSet(_transes);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TL transLabel(Transition trans) {
        final ETransition<TL> it = (ETransition<TL>)trans;
        return it._label;
    }

    /**
     * Returns an unmodifiable collection of outgoing transitions of the
     * specified state.  This implementation is very inefficient 
     * and anybody using {@link #outgoingTrans(ban.auto.State) } method 
     * with this implementation of an automaton should wrap it 
     * with an {@link OutgoerAutomaton}.
     *
     * @param s the state whose outgoing transitions are required.
     * @return the list of transitions outgoing from <code>s</code>.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<? extends Transition> outgoingTrans(final State s) {
        final Collection<Transition> result = _out.get(s);
        if (result == null) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableCollection(result);
    }

    /**
     * Returns an unmodifiable collection of incoming transitions to the
     * specified state.  This implementation is very inefficient 
     * and anybody using {@link #incomingTrans(ban.auto.State) } method 
     * with this implementation of an automaton should wrap it 
     * with an {@link IncomerAutomaton}.
     *
     * @param s the state whose incoming transitions are required.
     * @return the list of transitions incoming to <code>s</code>.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<? extends Transition> incomingTrans(final State s) {
        final Collection<Transition> result = _in.get(s);
        if (result == null) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableCollection(result);
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
        final TL result = _epsilon;
        _epsilon = empty;
        return result;
    }

    @Override
    public <SL, TL> Automaton<SL, TL> buildAutomaton(TL tl) {
        return new ElaborateAutomaton<SL, TL>(tl);
    }

    @Override
    public boolean hasTransitions(State s) {
        if (hasOutgoingTransitions(s)) {
            return true;
        }
        if (hasIncomingTransitions(s)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasOutgoingTransitions(State s) {
        return !outgoingTrans(s).isEmpty();
    }

    @Override
    public boolean hasIncomingTransitions(State s) {
        return !incomingTrans(s).isEmpty();
    }

    @Override
    public String toDot(String name) {
        return Automata.toDot(this, name);
    }

    @Override
    public Automaton<SL, TL> copy() {
        return Automata.copy(this);
    }
    
    private static Set<Transition> getOrCreateSetFromMap(State state, Map<State,Set<Transition>> map) {
        {
            final Set<Transition> result = map.get(state);
            if (result != null) {
                return result;
            }
        }
        final Set<Transition> result = new HashSet<Transition>();
        map.put(state, result);
        return result;
    }

}
class EState<SL> implements State {
	private final int _h;
    public SL _label;

	EState(int h, SL label) {
		_h = h;
                _label = label;
	}

	@Override
	public int hashCode() {
		return Pair.hashCode(_h, _h);
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	@Override
	public String toString() {
		return Integer.toString(_h);
	}

    @Override
    public int compareTo(State s) {
        return _h - ((EState)s)._h;
    }
}

class ETransition<TL> implements Transition {
	private int _h;
        public TL _label;
	private final State _s1;
	private final State _s2;

	ETransition(int h, State s1, State s2, TL label) {
		_h = h;
		_s1 = s1;
		_s2 = s2;
                _label = label;
	}

	@Override
	public State getOrigin() {
		return _s1;
	}

	@Override
	public State getTarget() {
		return _s2;
	}

	@Override
	public int hashCode() {
		return Pair.hashCode(_h, _h);
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

    @Override
    public int compareTo(Transition t) {
        return _h - ((ETransition)t)._h;
    }

}
