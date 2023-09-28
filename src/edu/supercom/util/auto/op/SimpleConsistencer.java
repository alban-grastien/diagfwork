/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util.auto.op;

import edu.supercom.util.Pair;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.SimpleAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.auto.changer.LabelChanger;
import edu.supercom.util.auto.io.DotWriter;
import edu.supercom.util.auto.label.EmbodimentSynchroniser;
import edu.supercom.util.auto.label.IntersecterSynchroniser;
import edu.supercom.util.auto.label.KeepFirstSynchroniser;
import edu.supercom.util.auto.label.MyEmbeddedLabel;
import edu.supercom.util.auto.label.StringSynchroniser;
import edu.supercom.util.auto.label.Synchroniser;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A simple implementation of a consistencer.  The consistencer works by
 * synchronising the two automata and keeping only the labels of the first
 * automaton.  A {@linkplain AutomatonMinimizer minimization} after this
 * consistencer is used.  
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.0
 */
public class SimpleConsistencer implements Consistencer {

    @Override
    public <SL1, SL2, SL, SLP1, SLP2, TL1, TL2, TL, TLP1, TLP2> Automaton<SL1, TL1> makeConsistent(
            ReadableAutomaton<SL1, TL1> auto1,
            ReadableAutomaton<SL2, TL2> auto2,
            Synchroniser<SL1, SL2, SL, SLP1, SLP2> stateSync,
            Synchroniser<TL1, TL2, TL, TLP1, TLP2> transSync) {
        final AutomataSynchroniser synchro = new SimpleSynchroniser();
        final Synchroniser<SL1,SL2,SL1,SLP1,SLP2> actualStateSync = new KeepFirstSynchroniser<SL1, SL2, SLP1, SLP2>(stateSync);
        final Synchroniser<TL1,TL2,TL1,TLP1,TLP2> actualTransSync = new KeepFirstSynchroniser<TL1, TL2, TLP1, TLP2>(transSync);

        return synchro.synchronise(auto1, auto2, actualStateSync, actualTransSync, auto1);
    }

    @Override
    public <SL1, SL2, SLS, SLP1, SLP2, TL1, TL2, TLS, TLP1, TLP2> Automaton<SL1, TL1> makeConsistentOrFail(
            ReadableAutomaton<SL1, TL1> auto1,
            ReadableAutomaton<SL2, TL2> auto2,
            Synchroniser<SL1, SL2, SLS, SLP1, SLP2> stateSync,
            Synchroniser<TL1, TL2, TLS, TLP1, TLP2> transSync) {
        final AutomataSynchroniser synchro = new SimpleSynchroniser();
        final Synchroniser<SL1,SL2,SL1,SLP1,SLP2> actualStateSync = new KeepFirstSynchroniser<SL1, SL2, SLP1, SLP2>(stateSync);
        final Synchroniser<TL1,TL2,TL1,TLP1,TLP2> actualTransSync = new KeepFirstSynchroniser<TL1, TL2, TLP1, TLP2>(transSync);
        final Map<State, Pair<State, State>> statePairs = new HashMap<State, Pair<State, State>>();
        final Map<Transition, Pair<Transition, Transition>> transPairs = new HashMap<Transition, Pair<Transition, Transition>>();


        final Automaton<SL1, TL1> auto = synchro.synchronise(auto1, auto2, actualStateSync, actualTransSync, auto1, statePairs, transPairs);

        // If for some state of the result, the set of output transition is
        // different from that of the corresponding state of auto1,
        // then it means the result is different.

        // First computes the equivalence classes of the result automaton.  
        final Map<State, Set<State>> classes = new HashMap<State, Set<State>>();
        for (final State state : auto.states()) {
            if (classes.containsKey(state)) {
                continue;
            }
            final Set<State> open = new HashSet<State>();
            final Set<State> cla = new HashSet<State>();
            open.add(state);
            while (!open.isEmpty()) {
                final Iterator<State> it = open.iterator();
                final State s = it.next();
                it.remove();
                if (cla.contains(s)) {
                    continue;
                }
                cla.add(s);
                // searches for states of s that are reached from a null transition.
                for (final Transition trans : auto.outgoingTrans(s)) {
                    final Transition trans1 = transPairs.get(trans).first();
                    if (trans1 != null) {
                        continue;
                    }
                    final State targ = trans.getTarget();
                    if (open.contains(targ) || cla.contains(targ)) {
                        continue;
                    }
                    final Set<State> classeOfTarg = classes.get(targ);
                    if (classeOfTarg == null) {
                        open.add(targ);
                    } else {
                        cla.addAll(classeOfTarg);
                    }
                }
            }
            for (final State s : cla) {
                classes.put(s, cla);
            }
        }

        for (final Set<State> cla : classes.values()) {
            final State s1 = statePairs.get(cla.iterator().next()).first();
            final Set<Transition> transes = new HashSet<Transition>();
            for (final State state : cla) {
                transes.addAll(auto.outgoingTrans(state));
            }
            for (final Transition trans1 : auto1.outgoingTrans(s1)) {
                boolean found = false;
                for (final Iterator<Transition> transIt = transes.iterator(); transIt.hasNext();) {
                    Transition trans = transIt.next();
                    Transition origTrans = transPairs.get(trans).first();
                    if (origTrans == null) {
                        transIt.remove();
                    }
                    if (origTrans == trans1) {
                        transIt.remove();
                        found = true;
                    }
                }
                if (!found) {
                    return auto;
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Automaton<String, Set<String>> auto1 =
                new SimpleAutomaton<String, Set<String>>(new HashSet<String>());

        {
            State s1 = auto1.newState("1");
            State s2 = auto1.newState("2");
            State s3 = auto1.newState("3");
            State s4 = auto1.newState("4");
            State s5 = auto1.newState("5");
            State s6 = auto1.newState("6");
            auto1.setInitial(s1);
            auto1.setFinal(s5);
            auto1.setFinal(s6);
            {
                Set<String> set = new HashSet();
                set.add("s1");
                auto1.newTransition(s1, s2, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("a");
                auto1.newTransition(s2, s1, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("s2");
                auto1.newTransition(s2, s3, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("b");
                auto1.newTransition(s3, s4, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("s3");
                auto1.newTransition(s4, s5, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("c");
                auto1.newTransition(s3, s6, set);
            }
        }

        Automaton<String, Set<String>> auto2 =
                new SimpleAutomaton<String, Set<String>>(new HashSet<String>());

        {
            State s1 = auto2.newState("1'");
            State s2 = auto2.newState("2'");
            State s3 = auto2.newState("3'");
            State s4 = auto2.newState("4'");
            State s5 = auto2.newState("5'");
            State s6 = auto2.newState("6'");
            State s7 = auto2.newState("7'");
            State s8 = auto2.newState("8'");
            auto2.setInitial(s1);
            auto2.setFinal(s5);
            auto2.setFinal(s7);

            {
                Set<String> set = new HashSet();
                set.add("s1");
                auto2.newTransition(s1, s2, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("s1");
                auto2.newTransition(s2, s8, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("d");
                auto2.newTransition(s8, s3, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("s2");
                auto2.newTransition(s3, s6, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("s3");
                auto2.newTransition(s6, s7, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("s2");
                auto2.newTransition(s3, s4, set);
            }
            {
                Set<String> set = new HashSet();
                set.add("s3");
                auto2.newTransition(s4, s5, set);
            }
        }

        Synchroniser<String, String, String, String, String> ss = new StringSynchroniser();
        Synchroniser<Set<String>, Set<String>, Set<String>, Set<String>, Set<String>> ts;
        {
            Set<String> synch = new HashSet<String>();
            synch.add("s1");
            synch.add("s2");
            synch.add("s3");
            ts = new IntersecterSynchroniser(synch);
        }

//        AutomataSynchroniser as = new SimpleSynchroniser();
//        Automaton<String,Set<String>> auto = as.synchronise(auto1, auto2, ss,ts);

        Consistencer cons = new SimpleConsistencer();
        Automaton auto = cons.makeConsistentOrFail(auto1, auto2, ss, ts);
        System.out.println(new DotWriter().toString(auto));
        LabelChanger map = MyEmbeddedLabel.embedChanger();
        auto2.changeStateLabels((LabelChanger<String, String>) map);
        Automaton auto2prime = auto2;

        Automaton autoPrime = cons.makeConsistentOrFail(auto, auto2prime, new EmbodimentSynchroniser<String, String, String, String, String>(ss), ts);
        if (autoPrime == null) {
            System.out.println("Consistent!");
        } else {
            System.out.println(new DotWriter().toString(autoPrime));
        }


    }
}

