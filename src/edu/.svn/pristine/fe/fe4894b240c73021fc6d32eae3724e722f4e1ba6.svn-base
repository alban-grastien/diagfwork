/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.label.Synchroniser;

/**
 * A <i>consistencer</i> is an object that is able to compute a sub-automaton of
 * the specified automaton that contains exactly all trajectories are consistent
 * with the specified automaton.
 *
 * @author Alban Grastien
 * @version 2.0
 */
public interface Consistencer {

    /**
     * Computes a sub-automaton of the first specified automaton that is
     * consistent with the second specified automaton.  The returned automaton
     * may not be minimal.
     *
     * @param auto1 the first automaton whose consistency is computed.
     * @param auto2 the second automaton whose consistency is computed.
     * @param stateSync the synchroniser between the states of auto1 and auto2.
     * @param transSync the synchroniser between the transitions of auto1 and
     * auto2.
     * @return a consistent sub-automaton of <code>auto1</code>. 
     */
    public <SL1,SL2,SLS,SLP1,SLP2,TL1,TL2,TLS,TLP1,TLP2>
            Automaton<SL1,TL1> makeConsistent(
            ReadableAutomaton<SL1,TL1> auto1,
            ReadableAutomaton<SL2,TL2> auto2,
            Synchroniser<SL1,SL2,SLS,SLP1,SLP2> stateSync,
            Synchroniser<TL1,TL2,TLS,TLP1,TLP2> transSync);

    /**
     * Computes if necessary a sub-automaton of the first specified automaton
     * that is consistent with the second specified automaton.  The returned
     * automaton may not be minimal.  In case the first automaton is already
     * consistent with the second automaton, returns <code>null</code>.
     *
     * @param auto1 the first automaton whose consistency is computed.
     * @param auto2 the second automaton whose consistency is computed.
     * @param stateSync the synchroniser between the states of auto1 and auto2.
     * @param transSync the synchroniser between the transitions of auto1 and
     * auto2.
     * @return a consistent sub-automaton of <code>auto1</code> if different
     * from <code>auto1</code>, <code>null</code> otherwise.
     */
    public <SL1,SL2,SLS,SLP1,SLP2,TL1,TL2,TLS,TLP1,TLP2>
            Automaton<SL1,TL1> makeConsistentOrFail(
            ReadableAutomaton<SL1,TL1> auto1,
            ReadableAutomaton<SL2,TL2> auto2,
            Synchroniser<SL1,SL2,SLS,SLP1,SLP2> stateSync,
            Synchroniser<TL1,TL2,TLS,TLP1,TLP2> transSync);

}
