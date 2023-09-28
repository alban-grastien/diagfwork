package edu.supercom.util.auto;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A <code>OutgoerAutomaton</code>, i.e., an outgoer automaton, 
 * is a wrapped automaton that registers the transitions outgoing 
 * from any state.  
 * 
 * @param <SL> the type of state labels.  
 * @param <TL> the type of transition labels.  
 * @deprecated
 */
@Deprecated
public class OutgoerAutomaton<SL,TL> extends WrappedAutomaton<SL,TL> {
    
    /**
     * The multi map of outgoing transitions associated with each state.  
     */
    private Map<State,Set<Transition>> _out;
    
    /**
     * Builds an outgoer automaton on top of the specified automaton.  
     * 
     * @param a the automaton that is wrapped by this outgoer automaton.  
     */
    public OutgoerAutomaton(Automaton<SL,TL> a) {
        super(a);
        _out = new HashMap<State, Set<Transition>>();
        for (final Transition t: a.transitions()) {
            final State origin = t.getOrigin();
            final Set<Transition> outgoingTransitions = 
                    getOrCreateOutgoingTransitions(origin);
            outgoingTransitions.add(t);
        }
    }
    
    @Override 
    public Transition newTransition(State state1, State state2, TL label) {
        final Transition result = super.newTransition(state1, state2, label);
        
        final Set<Transition> outgoingTransitionsOfState1 = 
                getOrCreateOutgoingTransitions(state1);
        
        outgoingTransitionsOfState1.add(result);
        
        return result;
    }
    
    private Set<Transition> getOrCreateOutgoingTransitions(State s) {
        {
            final Set<Transition> result = _out.get(s);
            if (result != null) {
                return result;
            }
        }
        
        final Set<Transition> result = new HashSet<Transition>();
        _out.put(s, result);
        return result;
    }
    
    @Override
    public boolean remove(Transition trans) {
        final boolean result = super.remove(trans);
        if (result) {
            final State origin = trans.getOrigin();
            final Set<Transition> outgoingTransitions = _out.get(origin);
            outgoingTransitions.remove(trans);
            if (outgoingTransitions.isEmpty()) {
                _out.remove(origin);
            }
        }
        return result;
    }

    @Override
    public Set<Transition> outgoingTrans(State s) {
        final Set<Transition> result = _out.get(s);
        if (result == null) {
            return new HashSet<Transition>();
        }
        
        return Collections.unmodifiableSet(result);
    }

    @Override
    public <SL, TL> Automaton<SL, TL> buildAutomaton(TL tl) {
        final Automaton<SL,TL> core = _core.buildAutomaton(tl);
        return new OutgoerAutomaton<SL, TL>(core);
    }
}
