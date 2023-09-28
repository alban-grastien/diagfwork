/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.SimpleAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.Pair;
import edu.supercom.util.auto.ReadableAutomaton;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Alban Grastien
 */
public class SimpleDeterminiser implements AutomatonDeterminiser {

    @Override
    public <SL, TL> Automaton<Set<SL>, TL> determinise(ReadableAutomaton<SL, TL> auto) {
        InternalDeterminiser<SL, TL> id = new InternalDeterminiser<SL, TL>(auto);
        Set<State> set = new HashSet<State>();
        Set<SL> stateLabels = new HashSet<SL>();
        for (final State state: auto.initialStates()) {
            set.add(state);
            stateLabels.add(auto.stateLabel(state));
        }
        State state = id.createState(set, new HashSet<SL>(stateLabels));
        id._result.setInitial(state);
        id.exploreSet(state, set);
        return id._result;
    }

    class InternalDeterminiser<SL, TL> {

        ReadableAutomaton<SL, TL> _auto;
        Automaton<Set<SL>, TL> _result;
        Map<Set<State>, State> _fromAutoToResult;

        public InternalDeterminiser(ReadableAutomaton<SL, TL> auto) {
            _auto = auto;
            _result = auto.buildAutomaton(auto.getEmptyTransitionLabel());
//            _result = new Automaton<SetStateLabel<SL>, TL>(auto.getEmptyTransitionLabel());
            _fromAutoToResult = new HashMap<Set<State>, State>();
        }

        private State getState(Set<State> set) {
            return _fromAutoToResult.get(set);
        }

        private State createState(Set<State> set, Set<SL> l) {
            State result = _result.newState(l);
            for (Iterator<State> it = set.iterator(); it.hasNext();) {
                if (_auto.isFinal(it.next())) {
                    _result.setFinal(result);
                    break;
                }
            }
            _fromAutoToResult.put(set, result);
            return result;
        }

        private void exploreSet(State state, Set<State> set) {
//            System.out.println("Exploring belief state " + set);
            Map<TL, Pair<Set<State>, Set<SL>>> transLabelMap = new HashMap<TL, Pair<Set<State>, Set<SL>>>();
            for (Iterator<State> stateIt = set.iterator(); stateIt.hasNext();) {
                State orig = stateIt.next();
                for (final Transition trans: _auto.outgoingTrans(orig)) {
//                    System.out.println("\tExploring transition " + _auto.toString(trans));
                    TL transLabel = _auto.transLabel(trans);
                    State targ = trans.getTarget();
                    SL stateLabel = _auto.stateLabel(targ);
                    Pair<Set<State>, Set<SL>> pair = transLabelMap.get(transLabel);
                    if (pair == null) {
                        pair = new Pair<Set<State>, Set<SL>>(new HashSet<State>(), new HashSet<SL>());
                        transLabelMap.put(transLabel, pair);
                    }
                    pair.first().add(targ);
                    pair.second().add(stateLabel);
                }
            }

            for (Iterator<Map.Entry<TL, Pair<Set<State>, Set<SL>>>> entryIt = transLabelMap.entrySet().iterator(); entryIt.hasNext();) {
                Map.Entry<TL, Pair<Set<State>, Set<SL>>> entry = entryIt.next();
                TL transLabel = entry.getKey();
//                System.out.println("\tExploring successor through " + transLabel);
                Set<State> newSet = entry.getValue().first();
//                System.out.println("\t\tNew set " + newSet);
                Set<SL> newLabel = entry.getValue().second();
                State newState = getState(newSet);
                if (newState == null) {
                    newState = createState(newSet, new HashSet<SL>(newLabel));
                    exploreSet(newState, newSet);
                }
                _result.newTransition(state, newState, transLabel);
            }
        }
    }

    public static void main(String args[]) {
        Automaton<String, String> auto =
                new SimpleAutomaton<String, String>("");
        {
            State sa = auto.newState("A");
            State sb = auto.newState("B");
            State sc = auto.newState("C");
            auto.newTransition(sa, sb, "1");
            auto.newTransition(sb, sa, "0");
            auto.newTransition(sa, sc, "1");
            auto.newTransition(sc, sb, "0");
            auto.setInitial(sa);
            auto.setFinal(sc);
        }
        System.out.println(auto.toDot("Automaton"));
        System.out.println();

        Automaton auto2 = new SimpleDeterminiser().determinise(auto);
        System.out.println(auto2.toDot("Determinized automaton"));
    }
}
