/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.Pair;
import edu.supercom.util.auto.AutomatonFactory;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.SimpleAutomaton;
import edu.supercom.util.auto.label.IntersecterSynchroniser;
import edu.supercom.util.auto.label.StringSynchroniser;
import edu.supercom.util.auto.label.Synchroniser;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Simple implementation of the synchroniser.  The two automata are simply
 * explored from the initial states.
 *
 * @author Alban Grastien
 */
public class SimpleSynchroniser extends AbstractSynchroniser implements AutomataSynchroniser {

    @Override
    public <SL1,SL2,SL,SLP1,SLP2,TL1,TL2,TL,TLP1,TLP2> Automaton<SL, TL>
            synchronise(
            ReadableAutomaton<SL1, TL1> auto1,
            ReadableAutomaton<SL2, TL2> auto2,
            Synchroniser<SL1, SL2, SL, SLP1, SLP2> sls,
            Synchroniser<TL1, TL2, TL, TLP1, TLP2> tls,
            AutomatonFactory fact,
            Map<State,Pair<State,State>> stateMap,
            Map<Transition,Pair<Transition,Transition>> transMap) {
        InternalSynchroniser<SL1, SL2, SL, TL1, TL2, TL> internal =
                new InternalSynchroniser<SL1, SL2, SL, TL1, TL2, TL>(auto1, auto2, sls, tls, fact,stateMap,transMap);

        for (final State s1: auto1.initialStates()) {
            SL1 sl1 = auto1.stateLabel(s1);
            for (final State s2: auto2.initialStates()) {
                SL2 sl2 = auto2.stateLabel(s2);
                SL sl = sls.synchroniseLabel(sl1, sl2);
                if (sl == null) {
                    continue;
                }
                State s = internal.getState(s1, s2);
                if (s != null) {
                    internal._result.setInitial(s);
                    continue;
                }
                s = internal.createState(s1, s2, sl);
                internal._result.setInitial(s);
                internal.exploreState(s1, s2, s);
            }
        }

//        internal._result.trim();
        return internal._result;
    }

    private <SL1,SL2,SL,TL1,TL2,TL> void exploreState(
            ReadableAutomaton<SL1, TL1> auto1,
            ReadableAutomaton<SL2, TL2> auto2,
            Synchroniser<SL1, SL2, SL, ?, ?> sls,
            Synchroniser<TL1, TL2, TL, ?, ?> tls,
            Automaton<SL, TL> result,
            State s1,
            State s2,
            State s) {
    }

    class InternalSynchroniser<SL1,SL2,SL,TL1,TL2,TL> {

        ReadableAutomaton<SL1, TL1> _auto1;
        ReadableAutomaton<SL2, TL2> _auto2;
        Automaton<SL, TL> _result;
        Map<Pair<State, State>, State> _localStatesToGlobalState;
        Synchroniser<SL1, SL2, SL, ?, ?> _sls;
        Synchroniser<TL1, TL2, TL, ?, ?> _tls;
        Map<State,Pair<State,State>> _stateMap;
        Map<Transition,Pair<Transition,Transition>> _transMap;

        public InternalSynchroniser(
                ReadableAutomaton<SL1, TL1> auto1,
                ReadableAutomaton<SL2, TL2> auto2,
                Synchroniser<SL1, SL2, SL, ?, ?> sls,
                Synchroniser<TL1, TL2, TL, ?, ?> tls,
                AutomatonFactory fact,
                Map<State,Pair<State, State>> stateMap,
                Map<Transition,Pair<Transition,Transition>> transMap) {
            _auto1 = auto1;
            _auto2 = auto2;
            _sls = sls;
            _tls = tls;
            TL epsilon = tls.synchroniseLabel(auto1.getEmptyTransitionLabel(),
                    auto2.getEmptyTransitionLabel());
            _result = fact.buildAutomaton(epsilon);
//            _result = new Automaton<SL, TL>(epsilon);
            _localStatesToGlobalState = new HashMap<Pair<State, State>, State>();
            _stateMap = stateMap;
            _transMap = transMap;
        }

        /**
         * Returns the state of the automaton corresponding to the specified
         * pair of state.  If the state does not exist (yet), the method returns
         * <code>null</code>.
         *
         * @param s1 the state of the first automaton.
         * @param s2 the state of the second automaton.
         * @return the state <code>&lt;s1,s2&gt;</code> if existing,
         * <code>null</code> otherwise.  
         */
        private State getState(State s1, State s2) {
            return _localStatesToGlobalState.get(new Pair<State, State>(s1, s2));
        }

        /**
         * Creates a state on the resulting automaton from the specified set of
         * local states.
         *
         * @param s1 the state of the first automaton.
         * @param s2 the state of the second automaton.
         * @param l  the label of the state.
         * @return the state <code>&lt;s1,s2&gt;</code>.
         */
        private State createState(State s1, State s2, SL l) {
            State result = _result.newState(l);
            if (_stateMap != null) {
                _stateMap.put(result, new Pair<State, State>(s1, s2));
            }
            _localStatesToGlobalState.put(new Pair<State, State>(s1, s2), result);
            if (_auto1.isFinal(s1) && _auto2.isFinal(s2)) {
                _result.setFinal(result);
            }
            return result;
        }

        /**
         * Tries to synchronise the specified local states.
         *
         * @param s1 the first state.
         * @param s2 the second state.
         * @param l1 the first state label.
         * @param l2 the second state label.
         * @return the label corresponding to the synchronisation of
         * <code>l1</code> and <code>l2</code>.
         */
        private SL synchronise(State s1, State s2, SL1 l1, SL2 l2) {
            // TODO: saves the states that do not synchronise?
            return _sls.synchroniseLabel(l1, l2);
        }

        /**
         * Tries to synchronise the two specified transitions.
         *
         * @param s  the origin state.
         * @param n1 the first local target state.
         * @param n2 the second local target state.
         * @param sl1 the label of the first local target state.
         * @param sl2 the label of the second local target state.
         * @param tl1 the label of the first local transition.
         * @param tl2 the label of the second local transition.
         * @return a global state if a new state was created, <code>null</code>
         * otherwise.  
         */
        private State synchroniseTransitions(State s, State n1, State n2,
                SL1 sl1, SL2 sl2, TL1 tl1, TL2 tl2, Transition trans1, Transition trans2) {
            boolean ret;
            State n = getState(n1, n2); // if n is null, we wait to see if the states and transitions synchronise
            ret = (n == null);

            SL sl = null; // Computed only if the state does not exist.
            if (n == null) {
                sl = synchronise(n1, n2, sl1, sl2);
                if (sl == null) { // The local states do not synchronise
                    return null;
                }
            }

            // Try to synchronise the transitions
            TL tl = _tls.synchroniseLabel(tl1, tl2);
            if (tl == null) {
                return null;
            }

            if (n == null) {
                n = createState(n1, n2, sl);
            }

            Transition t = _result.newTransition(s, n, tl);
            if (_transMap != null) {
                _transMap.put(t, new Pair<Transition,Transition>(trans1, trans2));
            }

            if (ret) {
                return n;
            }
            return null;
        }

        /**
         * Recursively explore the specified state of the result automaton
         * (corresponding to the pair of specified local states).
         *
         *
         */
        public void exploreState(State s1, State s2, State s) {
            //System.out.println("edu.supercom.util.auto.op.SimpleSynchroniser>");
            //System.out.println("Exploring state " + _result.stateLabel(s));
            //System.out.println("                " + _auto1.stateLabel(s1));
            //System.out.println("                " + _auto2.stateLabel(s2));
            // Transitions on s1 and s2
            for (final Transition trans1: _auto1.outgoingTrans(s1)) {
                State n1 = trans1.getTarget();
                TL1 tl1 = _auto1.transLabel(trans1);
                SL1 sl1 = _auto1.stateLabel(n1);
                for (final Transition trans2: _auto2.outgoingTrans(s2)) {
                    State n2 = trans2.getTarget();
                    TL2 tl2 = _auto2.transLabel(trans2);
                    SL2 sl2 = _auto2.stateLabel(n2);
                    State n = synchroniseTransitions(s, n1, n2, sl1, sl2, tl1, tl2, trans1, trans2);
                    if (n != null) {
                        exploreState(n1, n2, n);
                    }
                }
            }

            // Transitions on s1 only
            {
                State n2 = s2;
                TL2 tl2 = _auto2.getEmptyTransitionLabel();
                SL2 sl2 = _auto2.stateLabel(n2);
                for (final Transition trans1: _auto1.outgoingTrans(s1)) {
                    State n1 = trans1.getTarget();
                    TL1 tl1 = _auto1.transLabel(trans1);
                    SL1 sl1 = _auto1.stateLabel(n1);
                    State n = synchroniseTransitions(s, n1, n2, sl1, sl2, tl1, tl2, trans1, null);
                    if (n != null) {
                        exploreState(n1, n2, n);
                    }
                }
            }

            // Transitions on s2 only
            {
                State n1 = s1;
                TL1 tl1 = _auto1.getEmptyTransitionLabel();
                SL1 sl1 = _auto1.stateLabel(n1);
                for (final Transition trans2: _auto2.outgoingTrans(s2)) {
                    State n2 = trans2.getTarget();
                    TL2 tl2 = _auto2.transLabel(trans2);
                    SL2 sl2 = _auto2.stateLabel(n2);
                    State n = synchroniseTransitions(s, n1, n2, sl1, sl2, tl1, tl2, null, trans2);
                    if (n != null) {
                        exploreState(n1, n2, n);
                    }
                }
            }
        }
    }

    public static void main (String [] args) {
        Automaton<String,Set<String>> a1 =
                new SimpleAutomaton<String, Set<String>>(new HashSet<String>());
        {
            State s1 = a1.newState("1");
            State s2 = a1.newState("2");
            State s3 = a1.newState("3");
            State s4 = a1.newState("4");
            State s5 = a1.newState("5");
            State s6 = a1.newState("6");
            {
                Set<String> coll = new HashSet<String>();
                coll.add("a1");
                a1.newTransition(s1, s3, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("s2");
                a1.newTransition(s2, s3, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("a2");
                coll.add("s1");
                a1.newTransition(s3, s3, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("s3");
                a1.newTransition(s3, s4, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("s2");
                a1.newTransition(s3, s5, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("a1");
                a1.newTransition(s5, s6, coll);
            }
            a1.setInitial(s1);
            a1.setInitial(s2);
            a1.setFinal(s4);
            a1.setFinal(s6);
        }

        Automaton<String,Set<String>> a2 =
                new SimpleAutomaton<String, Set<String>>(new HashSet<String>());
        {
            State s1 = a2.newState("1'");
            State s2 = a2.newState("2'");
            State s3 = a2.newState("3'");
            State s4 = a2.newState("4'");
            State s5 = a2.newState("5'");
            State s6 = a2.newState("6'");
            {
                Set<String> coll = new HashSet<String>();
                coll.add("b2");
                a2.newTransition(s1, s2, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("s2");
                a2.newTransition(s2, s4, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("s1");
                a2.newTransition(s1, s3, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("b1");
                a2.newTransition(s3, s5, coll);
            }
            {
                Set<String> coll = new HashSet<String>();
                coll.add("s2");
                a2.newTransition(s5, s6, coll);
            }
            a2.setInitial(s1);
            a2.setFinal(s4);
            a2.setFinal(s6);
        }

        Automaton<String,Set<String>> a;
        {
            Set<String> common = new HashSet<String>();
            common.add("s1");
            common.add("s2");
            common.add("s3");
            a = new SimpleSynchroniser().synchronise(
                    a1, a2, new StringSynchroniser(),
                    new IntersecterSynchroniser<String>(common));
        }

        System.out.println(a1.toDot("A1"));
        System.out.println(a2.toDot("A2"));
        System.out.println(a.toDot("A1 o A2"));
    }
}
