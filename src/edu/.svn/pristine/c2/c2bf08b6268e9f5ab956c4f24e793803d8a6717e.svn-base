/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.AutomatonFactory;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.label.Synchroniser;

/**
 *
 * @author Alban Grastien
 */
public abstract class AbstractSynchroniser implements AutomataSynchroniser {

    @Override
        public <SL1,SL2,SL,SLP1,SLP2,TL1,TL2,TL,TLP1,TLP2> Automaton<SL,TL>
            synchronise(
            ReadableAutomaton<SL1,TL1> auto1,
            ReadableAutomaton<SL2,TL2> auto2,
            Synchroniser<SL1,SL2,SL,SLP1,SLP2> sls,
            Synchroniser<TL1,TL2,TL,TLP1,TLP2> tls,
            AutomatonFactory fact) {
            return this.synchronise(
                    auto1,
                    auto2,
                    sls,
                    tls,
                    fact,
                    null,
                    null);
        }

    @Override
    public <SL1,SL2,SL,SLP1,SLP2,TL1,TL2,TL,TLP1,TLP2> Automaton<SL,TL>
            synchronise(
            ReadableAutomaton<SL1,TL1> auto1,
            ReadableAutomaton<SL2,TL2> auto2,
            Synchroniser<SL1,SL2,SL,SLP1,SLP2> sls,
            Synchroniser<TL1,TL2,TL,TLP1,TLP2> tls) {
        return this.synchronise(auto1, auto2, sls, tls, auto1);
    }

}
