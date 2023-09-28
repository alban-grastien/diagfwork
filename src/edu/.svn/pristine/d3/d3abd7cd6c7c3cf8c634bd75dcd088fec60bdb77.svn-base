/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.SimpleAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple implementation of the minimizer.  
 *
 * @author Alban Grastien
 * @version 2.1.1
 */
public class SimpleMinimizer implements AutomatonMinimizer {

    /**
     * Computes classes of equivalent states.  
     */
    private <SL, TL> Map<State, Set<State>> computeClasses(ReadableAutomaton<SL, TL> auto) {
        // Computes the original classes of equivalence.
        final Map<State, Set<State>> result = new HashMap<State, Set<State>>();
        {
            final Set<State> finalStates = new HashSet<State>();
            final Set<State> nonFinalStates = new HashSet<State>();
            for (final State state: auto.states()) {
                if (auto.isFinal(state)) {
                    finalStates.add(state);
                    result.put(state, finalStates);
                } else {
                    nonFinalStates.add(state);
                    result.put(state, nonFinalStates);
                }
            }
//            final Map<SL, List<Set<State>>> states = new HashMap<SL, List<Set<State>>>();
//            for (final State state : auto.states()) {
//                final SL label = auto.stateLabel(state);
//                List<Set<State>> list = states.get(label);
//                if (list == null) {
//                    list = new ArrayList<Set<State>>();
//                    list.add(new HashSet<State>());
//                    list.add(new HashSet<State>());
//                    states.put(label, list);
//                }
//
//                final Set<State> equiv;
//                if (auto.isFinal(state)) {
//                    equiv = list.get(0);
//                } else {
//                    equiv = list.get(1);
//                }
//                equiv.add(state);
//                result.put(state, equiv);
//            }
        }
        
//        System.out.println(result);



        // Stabilise the computation.
        boolean stable;
        do {
            stable = true;

            // Will go through all equivalence classes.
            final Collection<Set<State>> equivColl = new ArrayList<Set<State>>(result.values());

            for (final Set<State> set : equivColl) {

                // The map next line tells us what states have a given behaviour.
                // The 'behaviour' is defined by a Map<TL,Set<State>> telling us that
                // a specified label leads to the specified set of equivalent states.  
                final Map<Map<TL, Set<State>>, Set<State>> behaviourToStates = new HashMap<Map<TL, Set<State>>, Set<State>>();

                for (final State state : set) {
                    final Map<TL, Set<State>> behaviour = new HashMap<TL, Set<State>>(); // The behaviour of states
                    for (final Transition trans : auto.outgoingTrans(state)) {
                        behaviour.put(auto.transLabel(trans), result.get(trans.getTarget()));
                    }
                    Set<State> sameBehaviours = behaviourToStates.get(behaviour);
                    if (sameBehaviours == null) {
                        sameBehaviours = new HashSet<State>();
                        behaviourToStates.put(behaviour, sameBehaviours);
                    }
                    sameBehaviours.add(state);
                }
                if (behaviourToStates.size() == 1) {
                    continue;
                }
                stable = false;

                for (final Set<State> newSet : behaviourToStates.values()) {
                    for (final State state : newSet) {
                        result.put(state, newSet);
                    }
                }
            }


//           final Collection<Set<State>> equivColl = new ArrayList<Set<State>>(result.values());
//           for (final Set<State> set: equivColl) {
//               final Map<Map<TL,State>,Set<State>> mapOfSucc = new HashMap<Map<TL, State>, Set<State>>();
//                for (final State state: set) {
//                    final Map<TL,State> succ = new HashMap<TL, State>();
//                    for (final Transition trans: auto.outgoingTrans(state)) {
//                        succ.put(auto.transLabel(trans), trans.getTarget());
//                    }
//                    Set<State> sameGuys = mapOfSucc.get(succ);
//                    if (sameGuys == null) {
//                        sameGuys = new HashSet<State>();
//                        mapOfSucc.put(succ, sameGuys);
//                    }
//                    sameGuys.add(state);
//                }
//                if (mapOfSucc.size() == 1) {
//                    continue;
//                }
//                stable = false;
//                for (final Set<State> newSet: mapOfSucc.values()) {
//                    for (final State state: newSet) {
//                        result.put(state, newSet);
//                    }
//                }
//            }
//
        } while (!stable);

        return result;
    }

    @Override
    public <SL, TL> Automaton<Set<SL>, TL> minimize(ReadableAutomaton<SL, TL> auto) {
        final Automaton<Set<SL>, TL> result = auto.buildAutomaton(auto.getEmptyTransitionLabel());
//        Automaton<SetStateLabel<SL>,TL> result = new Automaton<SetStateLabel<SL>, TL>(auto.getEmptyTransitionLabel());

        // Computes the original classes of equivalence.
        final Map<State, Set<State>> equiv = computeClasses(auto);

        // Creates the states.
        final Map<State, State> oldToNew = new HashMap<State, State>();
        final Collection<Set<State>> values = new HashSet<Set<State>>(equiv.values());
        for (final Set<State> cl : values) {
            final Set<SL> labelSet = new HashSet<SL>();
            for (final State state : cl) {
                labelSet.add(auto.stateLabel(state));
            }
            final Set<SL> clLabel = new HashSet<SL>(labelSet);
            final State newState = result.newState(clLabel);
            boolean fin = false;
            boolean ini = false;
            for (final State state : cl) {
                oldToNew.put(state, newState);
                fin |= (auto.isFinal(state));
                ini |= (auto.isInitial(state));
            }
            if (fin) {
                result.setFinal(newState);
            }
            if (ini) {
                result.setInitial(newState);
            }
        }

        // Writes the transitions.
        for (final Set<State> cl : values) {
            final State old1 = cl.iterator().next();
            final State new1 = oldToNew.get(old1);
            for (final Transition trans : auto.outgoingTrans(old1)) {
                final TL label = auto.transLabel(trans);
                final State old2 = trans.getTarget();
                final State new2 = oldToNew.get(old2);
                result.newTransition(new1, new2, label);
            }
        }

        return result;
    }

    @Override
    public <SL, TL> void doMinimize(Automaton<SL, TL> auto) {
        // Computes the original classes of equivalence.
        final Map<State, Set<State>> equiv = computeClasses(auto);

        final Collection<Set<State>> values = new HashSet<Set<State>>(equiv.values());

        // Determines which state from each class of equivalence should be kept.
        final Map<State, State> kept = new HashMap<State, State>();
        final Collection<State> toRemove = new ArrayList<State>();
        for (final Set<State> cl : values) {
            final Iterator<State> stateIt = cl.iterator();
            final State keptState = stateIt.next();
            kept.put(keptState, keptState);
            while (stateIt.hasNext()) {
                final State state = stateIt.next();
                kept.put(state, keptState);
                if (auto.isInitial(state)) {
                    auto.setInitial(keptState);
                }
                // Not necessary to final states because they are equivalent
                toRemove.add(state);
            }
        }

        // Copies the transitions between the chosen states.
        for (Iterator<Set<State>> clIt = values.iterator(); clIt.hasNext();) {
            Set<State> cl = clIt.next();
            State origKeptState = kept.get(cl.iterator().next());
            for (Iterator<Transition> transIt =
                    new ArrayList<Transition>(auto.outgoingTrans(origKeptState)).iterator();
                    transIt.hasNext();) {
                Transition trans = transIt.next();
                State targState = trans.getTarget();
                State targKeptState = kept.get(targState);
                if (targState == targKeptState) {
                    continue;
                }
                auto.newTransition(origKeptState, targKeptState, auto.transLabel(trans));
            }
        }

        // Removes the states and transitions
        for (Iterator<State> stateIt = toRemove.iterator(); stateIt.hasNext();) {
            State state = stateIt.next();
            auto.removeAll(state);
        }
    }

    public static void main(String args[]) {
        String eps = "eps";
        Automaton<String, String> auto =
                new SimpleAutomaton<String, String>(eps);
        State s0 = auto.newState("0");
        State s1 = auto.newState("1");
        State s2 = auto.newState("2");
        State s3 = auto.newState("3");
        State s4 = auto.newState("4");
        State s5 = auto.newState("5");
        State s6 = auto.newState("6");
        State s7 = auto.newState("7");

        auto.newTransition(s0, s1, "a");
        auto.newTransition(s4, s1, "a");
        auto.newTransition(s5, s1, "a");
        auto.newTransition(s1, s5, "a");
        auto.newTransition(s2, s3, "a");
        auto.newTransition(s3, s3, "a");
        auto.newTransition(s6, s3, "a");
        auto.newTransition(s7, s3, "a");

        auto.newTransition(s0, s4, "b");
        auto.newTransition(s4, s4, "b");
        auto.newTransition(s5, s4, "b");
        auto.newTransition(s1, s2, "b");
        auto.newTransition(s2, s6, "b");
        auto.newTransition(s7, s6, "b");
        auto.newTransition(s6, s7, "b");
        auto.newTransition(s3, s3, "b");

        auto.setInitial(s0);
        auto.setFinal(s2);
        auto.setFinal(s7);

        System.out.println(auto.toDot("Automaton"));

        Automaton result = new SimpleMinimizer().minimize(auto);
        new SimpleMinimizer().doMinimize(auto);

        System.out.println(result.toDot("Minimized automaton"));
//        System.out.println(auto.toDot());
    }
}
