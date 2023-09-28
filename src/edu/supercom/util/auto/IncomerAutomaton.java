package edu.supercom.util.auto;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A <code>IncomerAutomaton</code>, i.e., an incomer automaton, 
 * is a wrapped automaton that registers the transitions coming in any state.  
 * 
 * @param <SL> the type of state labels.  
 * @param <TL> the type of transition labels.  
 * @deprecated
 */
@Deprecated
public class IncomerAutomaton<SL,TL> extends WrappedAutomaton<SL,TL> {
    
    /**
     * The multi map of outgoing transitions associated with each state.  
     */
    private Map<State,Set<Transition>> _in;
    
    /**
     * Builds an outgoer automaton on top of the specified automaton.  
     * 
     * @param a the automaton that is wrapped by this outgoer automaton.  
     */
    public IncomerAutomaton(Automaton<SL,TL> a) {
        super(a);
        _in = new HashMap<State, Set<Transition>>();
        for (final Transition t: a.transitions()) {
            final State target = t.getTarget();
            final Set<Transition> incomingTransitions = 
                    getOrCreateIncomingTransitions(target);
            incomingTransitions.add(t);
        }
    }
    
    @Override 
    public Transition newTransition(State state1, State state2, TL label) {
        final Transition result = super.newTransition(state1, state2, label);
        
        final Set<Transition> incomingTransitionsOfState2 = 
                getOrCreateIncomingTransitions(state2);
        
        incomingTransitionsOfState2.add(result);
        
        return result;
    }
    
    private Set<Transition> getOrCreateIncomingTransitions(State s) {
        {
            final Set<Transition> result = _in.get(s);
            if (result != null) {
                return result;
            }
        }
        
        final Set<Transition> result = new HashSet<Transition>();
        _in.put(s, result);
        return result;
    }
    
    @Override
    public boolean remove(Transition trans) {
        final boolean result = super.remove(trans);
        if (result) {
            final State target = trans.getTarget();
            final Set<Transition> incomingTransitions = _in.get(target);
            incomingTransitions.remove(trans);
            if (incomingTransitions.isEmpty()) {
                _in.remove(target);
            }
        }
        return result;
    }

    @Override
    public Set<Transition> incomingTrans(State s) {
        final Set<Transition> result = _in.get(s);
        if (result == null) {
            return new HashSet<Transition>();
        }
        
        return Collections.unmodifiableSet(result);
    }

    @Override
    public <SL, TL> Automaton<SL, TL> buildAutomaton(TL tl) {
        final Automaton<SL,TL> core = _core.buildAutomaton(tl);
        return new IncomerAutomaton<SL, TL>(core);
    }
}
