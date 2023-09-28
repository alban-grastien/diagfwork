package edu.supercom.util.auto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Methods for automata.  
 */
public class Automata {
    
    @SuppressWarnings("unchecked")
    public static void irefine(Automaton auto) {
        final Set<State> closed = new HashSet<State>(auto.initialStates());
        final Stack<State> open = new Stack<State>();
        open.addAll(closed);
        
        while (!open.isEmpty()) {
            final State origin = open.pop();
            
            final Collection<? extends Transition> transes = auto.outgoingTrans(origin);
            for (final Transition trans: transes) {
                final State target = trans.getTarget();
                if (closed.contains(target)) {
                    continue;
                }
                closed.add(target);
                open.add(target);
            }
        }
        
        final Collection<? extends State> states = new ArrayList<State>(auto.states());
        for (final State state: states) {
            if (closed.contains(state)) {
                continue;
            }
            auto.removeAll(state);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void frefine(Automaton auto) {
        final Set<State> closed = new HashSet<State>(auto.finalStates());
        final Stack<State> open = new Stack<State>();
        open.addAll(closed);
        
        while (!open.isEmpty()) {
            final State target = open.pop();
            
            final Collection<? extends Transition> transes = auto.incomingTrans(target);
            for (final Transition trans: transes) {
                final State origin = trans.getOrigin();
                if (closed.contains(origin)) {
                    continue;
                }
                closed.add(origin);
                open.add(origin);
            }
        }
        
        final Collection<? extends State> states = new ArrayList<State>(auto.states());
        for (final State state: states) {
            if (closed.contains(state)) {
                continue;
            }
            auto.removeAll(state);
        }
    }
    
    public static <SL,TL> String toDot(Automaton<SL,TL> auto, String name) {
        final Map<State,Integer> stateToInt = new HashMap<State, Integer>();
        {
            int i=0;
            for (final State state: auto.states()) {
                stateToInt.put(state, i);
                i++;
            }
        }
        
        final StringBuilder result = new StringBuilder();
        result.append("digraph G {\n");
        result.append("  label=\"").append(name).append("\";\n");
        
        // States
        for (final State state: auto.states()) {
            final int stateIndex = stateToInt.get(state);
            final SL label = auto.stateLabel(state);
            result.append("  ")
                    .append(stateIndex)
                    .append(" [label=\"")
                    .append(label)
                    .append("\"");
            if (auto.isFinal(state)) {
                result.append(", fillcolor=lightgrey, shape=doubleoctagon");
            }
            result.append("];\n");
            
            if (auto.isInitial(state)) {
                result.append("  coste")
                    .append(stateIndex)
                    .append(" [label=\"\", color=\"White\"];\n");
                result.append("  coste")
                    .append(stateIndex)
                    .append(" -> ")
                    .append(stateIndex)
                    .append(";\n")
                    ;
            }
        }
        
        // Transitions
        for (final Transition tr: auto.transitions()) {
            final State s1 = tr.getOrigin();
            final int s1Index = stateToInt.get(s1);
            final State s2 = tr.getTarget();
            final int s2Index = stateToInt.get(s2);
            final TL label = auto.transLabel(tr);
            result.append("  ")
                    .append(s1Index)
                    .append(" -> ")
                    .append(s2Index)
                    .append(" [label=\"")
                    .append(label)
                    .append("\"];\n")
                    ;
        }
        
        result.append("}");
        
        return result.toString();
    }
    
    public static <SL,TL> Automaton<SL,TL> copy(ReadableAutomaton<SL,TL> auto) {
        final Automaton<SL,TL> result = auto.buildAutomaton(auto.getEmptyTransitionLabel());
        
        final Map<State,State> oldToNew = new HashMap<State, State>();
        for (final State state: auto.states()) {
            final SL label = auto.stateLabel(state);
            final State newState = result.newState(label);
            oldToNew.put(state, newState);
        }
        
        for (final State initialState: auto.initialStates()) {
            final State newState = oldToNew.get(initialState);
            result.setInitial(newState);
        }
        
        for (final State finalState: auto.finalStates()) {
            final State newState = oldToNew.get(finalState);
            result.setFinal(newState);
        }
        
        for (final Transition trans: auto.transitions()) {
            final State origin = trans.getOrigin();
            final State newOrigin = oldToNew.get(origin);
            
            final State target = trans.getTarget();
            final State newTarget = oldToNew.get(target);
            
            final TL label = auto.transLabel(trans);
            
            result.newTransition(newOrigin, newTarget, label);
        }
        
        return result;
    }
}
