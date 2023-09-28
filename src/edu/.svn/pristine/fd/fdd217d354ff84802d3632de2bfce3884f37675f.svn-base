/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.Pair;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.AutomatonFactory;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.auto.label.Synchroniser;
import java.util.Map;

/**
 * An automata synchroniser is an object that is able to perform a
 * synchronisation between two automata.  The synchronisation is an automaton
 * with the set of states defined as the Cartesian product of the two local sets
 * of states and the set of transitions defined as the Cartesian product of the
 * two local sets of transitions.  The states and the transitions must be
 * synchronised according to the two specified synchronisers.  
 *
 * @author Alban Grastien
 */
public interface AutomataSynchroniser {

    /**
     * Performs the synchronisation of the two specified automata and saves the
     * maping between the states and the transitions of the original states and
     * transitions.
     *
     * @param auto1 the first automaton to synchronise.
     * @param auto2 the second automaton to synchronise.
     * @param sls the synchroniser used to synchronise the state labels.
     * @param tls the synchroniser used to synchronise the transition labels.
     * @param fact the factory that is used to generate the result automaton.
*/
    public <SL1,SL2,SL,SLP1,SLP2,TL1,TL2,TL,TLP1,TLP2> Automaton<SL,TL>
            synchronise(
            ReadableAutomaton<SL1,TL1> auto1,
            ReadableAutomaton<SL2,TL2> auto2,
            Synchroniser<SL1,SL2,SL,SLP1,SLP2> sls,
            Synchroniser<TL1,TL2,TL,TLP1,TLP2> tls,
            AutomatonFactory fact,
            Map<State,Pair<State,State>> statePairs,
            Map<Transition,Pair<Transition,Transition>> transPairs
            );


    /**
     * Performs the synchronisation of the two specified automata.
     *
     * @param auto1 the first automaton to synchronise.
     * @param auto2 the second automaton to synchronise.
     * @param sls the synchroniser used to synchronise the state labels.
     * @param tls the synchroniser used to synchronise the transition labels.
     * @param fact the factory that is used to generate the result automaton. 
     */
    public <SL1,SL2,SL,SLP1,SLP2,TL1,TL2,TL,TLP1,TLP2> Automaton<SL,TL>
            synchronise(
            ReadableAutomaton<SL1,TL1> auto1,
            ReadableAutomaton<SL2,TL2> auto2,
            Synchroniser<SL1,SL2,SL,SLP1,SLP2> sls,
            Synchroniser<TL1,TL2,TL,TLP1,TLP2> tls,
            AutomatonFactory fact);

    /**
     * Performs the synchronisation of the two specified automata with default
     * automaton factory of the first automaton.  
     *
     * @param auto1 the first automaton to synchronise.
     * @param auto2 the second automaton to synchronise.
     * @param sls the synchroniser used to synchronise the state labels.
     * @param tls the synchroniser used to synchronise the transition labels.
     */
    public <SL1,SL2,SL,SLP1,SLP2,TL1,TL2,TL,TLP1,TLP2> Automaton<SL,TL>
            synchronise(
            ReadableAutomaton<SL1,TL1> auto1,
            ReadableAutomaton<SL2,TL2> auto2,
            Synchroniser<SL1,SL2,SL,SLP1,SLP2> sls,
            Synchroniser<TL1,TL2,TL,TLP1,TLP2> tls);

}
