/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import java.util.Set;

/**
 * An automaton determiniser is an object that is able to determinise an
 * automaton.
 *
 * @author Alban Grastien
 */
public interface AutomatonDeterminiser {

    /**
     * Determinise the specified automaton.
     *
     * @param auto the automaton that is determinised.
     * @return the deterministic version of <code>auto</code>.
     */
    public <SL, TL>
            Automaton<Set<SL>,TL> determinise(ReadableAutomaton<SL,TL> auto);

}
