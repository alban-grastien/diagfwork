/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.auto.label.Synchroniser;
import edu.supercom.util.auto.label.SynchroniserInverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Implements an automaton pruner that removes the transitions that cannot be
 * synchronised with transitions of the other automaton.
 *
 * @author Alban Grastien
 */
public class TransitionPruner extends AbstractPruner {

    @Override
    public <S1, T1, S2, T2, S, T, SP1, SP2, TP1, TP2> boolean pruneFirst(Automaton<S1, T1> auto1, Automaton<S2, T2> auto2, Synchroniser<S1, S2, S, SP1, SP2> ss, Synchroniser<T1, T2, T, TP1, TP2> ts) {
        Synchroniser<T2,T1,T,TP2,TP1> antiTS = new SynchroniserInverter<T2,T1,T,TP2,TP1>(ts);

        Map<TP1,Collection<Transition>> t1s = getProjectedMap(auto1, ts);
        Collection<TP2> t2s = getProjectedSet(auto2, antiTS);

        return makeConsistent(auto1, t1s, t2s, ts);
    }

    @Override
    public <S1, T1, S2, T2, S, T, SP1, SP2, TP1, TP2> int pruneBoth(Automaton<S1, T1> auto1, Automaton<S2, T2> auto2, Synchroniser<S1, S2, S, SP1, SP2> ss, Synchroniser<T1, T2, T, TP1, TP2> ts) {
        boolean[] result = {false,false};
        Synchroniser<T2,T1,T,TP2,TP1> antiTS = new SynchroniserInverter<T2,T1,T,TP2,TP1>(ts);

        Map<TP1,Collection<Transition>> t1s = getProjectedMap(auto1, ts);
        Map<TP2,Collection<Transition>> t2s = getProjectedMap(auto2, antiTS);

        result[0] = makeConsistent(auto1, t1s, t2s.keySet(), ts);
        result[1] = makeConsistent(auto2, t2s, t1s.keySet(), antiTS);

        boolean modified = result[1];
        boolean first = true; // Indicates which automaton will be modified.
        while (modified) {
            if (first) {
                modified = makeConsistent(auto1, t1s, t2s.keySet(), ts);
            } else {
                modified = makeConsistent(auto2, t2s, t1s.keySet(), antiTS);
            }
            first = !first;
        }

        if ( result[0] &&  result[1]) { return AbstractPruner.BOTH_MODIFIED; }
        if ( result[0] && !result[1]) { return AbstractPruner.FIRST_MODIFIED; }
        if (//!result[0] &&
                result[1]) { return AbstractPruner.SECOND_MODIFIED; }
        //if (!result[0] && !result[1])
            return AbstractPruner.NO_MODIFICATION;
    }

    private static <S1, T1, T2, T, TP1, TP2> Map<TP1,Collection<Transition>>
            getProjectedMap(Automaton<S1,T1> auto, Synchroniser<T1,T2,T,TP1,TP2> ts) {
        Map<TP1,Collection<Transition>> result = new HashMap<TP1, Collection<Transition>>();

        result.put(ts.projection1(auto.getEmptyTransitionLabel()), new HashSet<Transition>());
        for (final Transition trans: auto.transitions()) {
            T1 t1 = auto.transLabel(trans);
            TP1 tp1 = ts.projection1(t1);
            Collection<Transition> transes = result.get(tp1);
            if (transes == null) {
                transes = new ArrayList<Transition>();
                result.put(tp1, transes);
            }
            transes.add(trans);
        }
        return result;
    }

    private static <S1, T1, T2, T, TP1, TP2> Set<TP1>
            getProjectedSet(Automaton<S1,T1> auto, Synchroniser<T1,T2,T,TP1,TP2> ts) {
        Set<TP1> result = new HashSet<TP1>();
        result.add(ts.projection1(auto.getEmptyTransitionLabel()));
        for (final Transition trans: auto.transitions()) {
            T1 t1 = auto.transLabel(trans);
            TP1 tp1 = ts.projection1(t1);
            result.add(tp1);
        }
        return result;
    }

    private static <S1, T1, S2, T2, S, T, SP1, SP2, TP1, TP2> boolean makeConsistent(
            Automaton<S1,T1> auto, Map<TP1,Collection<Transition>> t1s, Collection<TP2> t2s,
            Synchroniser<T1, T2, T, TP1, TP2> ts) {
        boolean result = false;
        for (Iterator<Map.Entry<TP1,Collection<Transition>>> entryIt = t1s.entrySet().iterator() ;
        entryIt.hasNext() ; ) {
            Map.Entry<TP1,Collection<Transition>> entry = entryIt.next();
            TP1 tp1 = entry.getKey();
            boolean synchro = false;
            for (Iterator<TP2> labelIt = t2s.iterator() ; labelIt.hasNext() ; ) {
                TP2 tp2 = labelIt.next();
                if (ts.synchroniseProjectedLabels(tp1, tp2)) {
                    synchro = true;
                    break;
                }
            }
            if (synchro) {
                continue;
            }
            entryIt.remove();
            for (Iterator<Transition> transIt = entry.getValue().iterator() ; transIt.hasNext() ; ) {
                result = true;
                Transition trans = transIt.next();
                auto.remove(trans);
            }
        }
        return result;
    }
}
