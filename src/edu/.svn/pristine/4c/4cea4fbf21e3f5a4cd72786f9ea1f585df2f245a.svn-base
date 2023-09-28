/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A no trans merger merges the states separated with empty transitions and with
 * similar state labels.  
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 */
public class NoTransMerger {

    /**
     * Adds the specified transition if it does not already exist.
     *
     * @param auto the automaton.  
     * @param s1 the origin state.
     * @param s2 the target state.
     * @param lbl the transition label.  
     */
    public static <TL> void tryToAddTransition(Automaton<?,TL> auto, State s1, State s2, TL lbl) {
        final Collection<? extends Transition> toCompare;
        {
            final Collection<? extends Transition> first  = auto.outgoingTrans(s1);
            final Collection<? extends Transition> second = auto.incomingTrans(s2);
            if (first.size() < second.size()) {
                toCompare = first;
            } else {
                toCompare = second;
            }
        }

        for (final Transition tr: toCompare) {
            if (tr.getOrigin() != s1) {
                continue;
            }
            if (tr.getTarget() != s2) {
                continue;
            }
            if (!lbl.equals(auto.transLabel(tr))) {
                continue;
            }
            return; // The transition already exists.
        }

        auto.newTransition(s1, s2, lbl);
    }

    /**
     * Merges the two specified states in the specified automaton.
     *
     * @param auto the automaton.  
     * @param s1 the first state.
     * @param s2 the second state which is replaced by <code>s1</code>.  
     */
    public static <SL,TL> void merge(Automaton<SL,TL> auto, State s1, State s2) {
        final TL empty = auto.getEmptyTransitionLabel();

        {
            final Set<Transition> transes = new HashSet<Transition>(auto.incomingTrans(s2));
            for (final Transition trans: transes) {
                final State orig;
                {
                    State s = trans.getOrigin();
                    if (s == s2) {
                        s = s1;
                    }
                    orig = s;
                }
                final TL label = auto.transLabel(trans);
                auto.remove(trans);
                if (orig == s1 && (label == empty || label.equals(empty))) {
                    // No loop on s1 with EMPTY.
                    continue;
                }
                tryToAddTransition(auto, orig, s1, label);
            }
        }

        {
            final Set<Transition> transes = new HashSet<Transition>(auto.outgoingTrans(s2));
            for (final Transition trans: transes) {
                final State targ;
                {
                    State s = trans.getTarget();
                    if (s == s2) {
                        s = s1;
                    }
                    targ = s;
                }
                final TL label = auto.transLabel(trans);
                auto.remove(trans);
                if (targ == s1 && (label == empty || label.equals(empty))) {
                    // No loop on s1 with EMPTY.
                    continue;
                }
                tryToAddTransition(auto, s1, targ, label);
            }
        }

        final boolean isFin = auto.isFinal(s2);

        if (isFin) {
            auto.setFinal(s1);
        }

        auto.removeAll(s2);
    }

    /**
     * Merges the states of the specified automaton separated with empty
     * transitions and with similar state labels.
     *
     * @param auto the automaton on which this method is applied.  
     */
    public <SL,TL> void mergeEquivalentStates (Automaton<SL,TL> auto) {
        final Collection<State> states = new ArrayList<State>(auto.states());
        for (final State state: states) {
            final Collection<Transition> toRemove = new ArrayList<Transition>();
            for (final Transition trans: auto.incomingTrans(state)) {
                if (!auto.transLabel(trans).equals(auto.getEmptyTransitionLabel())) {
                    continue;
                }
                final State other = trans.getOrigin();
                if (other == state) {
                    toRemove.add(trans);
                    continue;
                }
                merge(auto, other, state);
                toRemove.clear(); // No need to remove transitions as state was deleted.  
                break; // No need to look at other transitions as state was deleted.  
            }
            for (final Transition trans: toRemove) {
                auto.remove(trans);
            }
        }
    }

}
