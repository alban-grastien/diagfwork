/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.State;
import java.util.Map;

/**
 * An automaton copier is an object that is able to make copies of automata.
 * The copied automaton is an isomorphism of the specified automaton but the
 * copier may modify the labels.  
 *
 * @author Alban Grastien
 */
public interface AutomatonCopier <SL1,TL1,SL2,TL2> {

    /**
     * Makes a copy of the specified automaton.
     *
     * @param auto the automaton to copy.
     * @return a copy of the automaton.
     * @throws IllegalArgumentException if the copy would generate two states
     * with the same label.
     */
    public Automaton<SL2,TL2> copy(ReadableAutomaton<SL1,TL1> auto);

    /**
     * Makes a copy of the specified automaton and saves the correspondance
     * between the states in the specified map.
     *
     * @param auto the automaton to copy.
     * @param corr the (originaly empty) map that will contain the pair
     * <code>&lt;s1,s2&gt;</code> such that the state <code>s1</code> of
     * <code>auto</code> was copied in the returned automaton as the state
     * <code>s2</code>.  
     * @return a copy of the automaton.
     * @throws IllegalArgumentException if the copy would generate two states
     * with the same label.
     */
    public Automaton<SL2,TL2> copy(ReadableAutomaton<SL1,TL1> auto, Map<State,State> corr);
}
