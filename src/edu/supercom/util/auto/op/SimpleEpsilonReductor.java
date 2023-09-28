/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.Pair;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Alban Grastien
 */
public class SimpleEpsilonReductor implements EpsilonReductor {

    public <SL, TL> void
            runReduction(Automaton<SL, TL> auto, Set<? super TL> epsi) {
        // Computes the set of pairs of states linked by a sequence of epsilon transitions.
        Map<State,Set<State>> linkedStates = new HashMap<State, Set<State>>();
        Collection<Transition> toRemove = new ArrayList<Transition>();
        for (final Transition trans: auto.transitions()) {
            TL label = auto.transLabel(trans);
            if (epsi.contains(label)) {
                toRemove.add(trans);
                State orig = trans.getOrigin();
                State targ = trans.getTarget();
                if (orig == targ) {
                    continue;
                }
                Set<State> succ = linkedStates.get(orig);
                if (succ == null) {
                    succ = new HashSet<State>();
                    linkedStates.put(orig, succ);
                }
                succ.add(targ);
            }
        }
        // Runs the transitive closure.
        Set<State> origStates = linkedStates.keySet(); // states with outgoing epsilon transitions
        for (;;) {
            boolean modified = false;

            for (Iterator<State> stateIt = origStates.iterator() ; stateIt.hasNext() ; ) {
                State state = stateIt.next();
                Set<State> targetStates = linkedStates.get(state);
                Set<State> newTargetStates = new HashSet<State>();
                for (Iterator<State> targIt = targetStates.iterator() ; targIt.hasNext() ; ) {
                    State targ = targIt.next();
                    Set<State> nextTarg = linkedStates.get(targ);
                    if (nextTarg == null) {
                        continue;
                    }
                    newTargetStates.addAll(nextTarg);
                }
                newTargetStates.remove(state);
                modified |= targetStates.addAll(newTargetStates);
            }

            if (!modified) {
                break;
            }
        }

        // Removes the epsilon transitions.
        for (Iterator<Transition> transIt = toRemove.iterator() ; transIt.hasNext() ; ) {
            Transition trans = transIt.next();
            auto.remove(trans);
        }

        // Sets final and initial states.
        for (final State init: auto.initialStates()) {
            Set<State> next = linkedStates.get(init);
            if (next == null) {
                continue;
            }
            for (Iterator<State> stateIt = next.iterator() ; stateIt.hasNext() ; ) {
                State state = stateIt.next();
                auto.setInitial(state);
            }
        }
        for (final State fin: auto.finalStates()) {
            Set<State> next = linkedStates.get(fin);
            if (next == null) {
                continue;
            }
            for (Iterator<State> stateIt = next.iterator() ; stateIt.hasNext() ; ) {
                State state = stateIt.next();
                auto.setFinal(state);
            }
        }

        // Add the transitions.
        Collection<Pair<State,Pair<State,TL>>> toAdd = new HashSet<Pair<State, Pair<State, TL>>>();
        for (Iterator<State> middleIt = origStates.iterator() ; middleIt.hasNext() ; ) {
            State middle = middleIt.next();
            Set<State> epsiStates = linkedStates.get(middle);
            for (Iterator<State> targIt = epsiStates.iterator() ; targIt.hasNext() ; ) {
                State targ = targIt.next();
                for (final Transition trans: auto.incomingTrans(middle)) {
                    TL label = auto.transLabel(trans);
                    State orig = trans.getOrigin();
                    // Does the transition exists?
                    boolean exists = false;
                    for (final Transition out: auto.outgoingTrans(orig)) {
                        if (out.getTarget() != targ) {
                            continue;
                        }
                        if (!auto.transLabel(out).equals(label)) {
                            continue;
                        }
                        exists = true;
                    }
                    if (!exists) {
                        toAdd.add(new Pair<State, Pair<State, TL>>(orig, new Pair<State, TL>(targ, label)));
                    }
                }
            }
        }
        for (Iterator<Pair<State,Pair<State,TL>>> it = toAdd.iterator() ; it.hasNext() ; ) {
            Pair<State,Pair<State,TL>> pair = it.next();
            auto.newTransition(pair.first(), pair.second().first(), pair.second().second());
        }

        // Removes the states that need to.
        auto.trim();
    }

    @Override
    public <SL, TL> void
            runReduction(Automaton<SL, TL> auto) {
        TL epsi = auto.getEmptyTransitionLabel();
        // Computes the set of pairs of states linked by a sequence of epsilon transitions.
        Map<State,Set<State>> linkedStates = new HashMap<State, Set<State>>();
        Collection<Transition> toRemove = new ArrayList<Transition>();
        for (final Transition trans: auto.transitions()) {
            TL label = auto.transLabel(trans);
            if (epsi.equals(label)) {
                toRemove.add(trans);
                State orig = trans.getOrigin();
                State targ = trans.getTarget();
                if (orig == targ) {
                    continue;
                }
                Set<State> succ = linkedStates.get(orig);
                if (succ == null) {
                    succ = new HashSet<State>();
                    linkedStates.put(orig, succ);
                }
                succ.add(targ);
            }
        }
        // Runs the transitive closure.
        Set<State> origStates = linkedStates.keySet(); // states with outgoing epsilon transitions
        for (;;) {
            boolean modified = false;

            for (Iterator<State> stateIt = origStates.iterator() ; stateIt.hasNext() ; ) {
                State state = stateIt.next();
                Set<State> targetStates = linkedStates.get(state);
                Set<State> newTargetStates = new HashSet<State>();
                for (Iterator<State> targIt = targetStates.iterator() ; targIt.hasNext() ; ) {
                    State targ = targIt.next();
                    Set<State> nextTarg = linkedStates.get(targ);
                    if (nextTarg == null) {
                        continue;
                    }
                    newTargetStates.addAll(nextTarg);
                }
                newTargetStates.remove(state);
                modified |= targetStates.addAll(newTargetStates);
            }

            if (!modified) {
                break;
            }
        }

        // Removes the epsilon transitions.
        for (Iterator<Transition> transIt = toRemove.iterator() ; transIt.hasNext() ; ) {
            Transition trans = transIt.next();
            auto.remove(trans);
        }

        // Sets final and initial states.
        for (final State init: auto.initialStates()) {
            Set<State> next = linkedStates.get(init);
            if (next == null) {
                continue;
            }
            for (Iterator<State> stateIt = next.iterator() ; stateIt.hasNext() ; ) {
                State state = stateIt.next();
                auto.setInitial(state);
            }
        }
        for (final State fin: auto.finalStates()) {
            Set<State> next = linkedStates.get(fin);
            if (next == null) {
                continue;
            }
            for (Iterator<State> stateIt = next.iterator() ; stateIt.hasNext() ; ) {
                State state = stateIt.next();
                auto.setFinal(state);
            }
        }

        // Add the transitions.
        Collection<Pair<State,Pair<State,TL>>> toAdd = new HashSet<Pair<State, Pair<State, TL>>>();
        for (Iterator<State> middleIt = origStates.iterator() ; middleIt.hasNext() ; ) {
            State middle = middleIt.next();
            Set<State> epsiStates = linkedStates.get(middle);
            for (Iterator<State> targIt = epsiStates.iterator() ; targIt.hasNext() ; ) {
                State targ = targIt.next();
                for (final Transition trans: auto.incomingTrans(middle)) {
                    TL label = auto.transLabel(trans);
                    State orig = trans.getOrigin();
                    // Does the transition exists?
                    boolean exists = false;
                    for (final Transition out: auto.outgoingTrans(orig)) {
                        if (out.getTarget() != targ) {
                            continue;
                        }
                        if (!auto.transLabel(out).equals(label)) {
                            continue;
                        }
                        exists = true;
                    }
                    if (!exists) {
                        toAdd.add(new Pair<State, Pair<State, TL>>(orig, new Pair<State, TL>(targ, label)));
                    }
                }
            }
        }
        for (Iterator<Pair<State,Pair<State,TL>>> it = toAdd.iterator() ; it.hasNext() ; ) {
            Pair<State,Pair<State,TL>> pair = it.next();
            auto.newTransition(pair.first(), pair.second().first(), pair.second().second());
        }

        // Removes the states that need to.
//        auto.trim();
    }

}
