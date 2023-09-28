package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A <code>ElaborateEpsilonReductor</code>, i.e., an elaborate epsilon reductor, 
 * is an implementation of an epsilon reductor.  
 */
public class ElaborateEpsilonReductor implements EpsilonReductor {

    @Override
    public <SL, TL> void runReduction(Automaton<SL, TL> auto) {
        {
            final Set<State> states = new HashSet<State>(auto.states());
            for (final State state: auto.initialStates()) {
                if (!states.contains(state)) {
                    System.out.println(auto.toDot("Culprit"));
                    throw new IllegalStateException();
                }
            }
        }
        
        final Map<State,Set<State>> successors = new HashMap<State, Set<State>>();
        final Map<State,Set<State>> ancestors = new HashMap<State, Set<State>>();
        final TL epsilonLabel = auto.getEmptyTransitionLabel();
        
        for (final State state: auto.states()) {
            successors.put(state, new HashSet<State>());
            ancestors.put(state, new HashSet<State>());
        }
        
        for (final State state: auto.states()) {
            successors.get(state).add(state);
            ancestors.get(state).add(state);
        }
        
        final List<Transition> transitionsToRemove = new ArrayList<Transition>();
        for (final Transition trans: auto.transitions()) {
            final TL transLabel = auto.transLabel(trans);
            if (epsilonLabel.equals(transLabel)) {
                final State orig = trans.getOrigin();
                final List<State> ancestorsOfOrigin = new ArrayList<State>(ancestors.get(orig));
                ancestorsOfOrigin.add(orig);
                
                final State targ = trans.getTarget();
                final List<State> successorsOfTarget = new ArrayList<State>(successors.get(targ));
                successorsOfTarget.add(targ);
                
                for (final State newOrigin: ancestorsOfOrigin) {
                    for (final State newTarget: successorsOfTarget) {
                        successors.get(newOrigin).add(newTarget);
                        ancestors.get(newTarget).add(newOrigin);
                    }
                }
                transitionsToRemove.add(trans);
            }
        }
        
//        for (final Map.Entry<State,Set<State>> ent: successors.entrySet()) {
//            System.out.println(ent);
//        }
//        
//        for (final Map.Entry<State,Set<State>> ent: ancestors.entrySet()) {
//            System.out.println(ent);
//        }
        
        for (final Transition trans: transitionsToRemove) {
            auto.remove(trans);
        }
        
        final List<Transition> transitions = new ArrayList<Transition>(auto.transitions());
        for (final Transition trans: transitions) {
            final State origin = trans.getOrigin();
            final State target = trans.getTarget();
            final TL transLabel = auto.transLabel(trans);
            
            for (final State newOrigin: ancestors.get(origin)) {
                {
                    final State newTarget = target;
//                    System.out.println("Trying to add " + newOrigin + " - " + newTarget + " : " + transLabel);
                    boolean existsTransition = false;
                    final Collection<? extends Transition> existingTransitions;
                    {
                        final Collection<? extends Transition> transitions1 = auto.outgoingTrans(newOrigin);
                        final Collection<? extends Transition> transitions2 = auto.incomingTrans(newTarget);
                        if (transitions1.size() < transitions2.size()) {
                            existingTransitions = transitions1;
                        } else {
                            existingTransitions = transitions2;
                        }
                    }
                    for (final Transition existingTransition: existingTransitions) {
//                        System.out.println("Existing include: " + existingTransition.getOrigin() + " - " + existingTransition.getTarget() + ": " + auto.transLabel(existingTransition));
                        if (newOrigin == existingTransition.getOrigin()
                                && newTarget == existingTransition.getTarget()
                                && transLabel.equals(auto.transLabel(existingTransition))) {
                            existsTransition = true;
                            break;
                        }
                    }
                    
                    if (existsTransition) {
//                        System.out.println("Not added");
                        continue;
                    }
//                    System.out.println("Added");
                    
                    auto.newTransition(newOrigin, newTarget, transLabel);
                }
            }
        }
        
        for (final State initialState: new ArrayList<State>(auto.initialStates())) {
            for (final State successor: successors.get(initialState)) {
                auto.setInitial(successor);
            }
        }
        
        for (final State finalState: new ArrayList<State>(auto.finalStates())) {
            for (final State ancestor: ancestors.get(finalState)) {
                auto.setFinal(ancestor);
            }
        }
    }
    
}
