/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.changer.LabelChanger;

/**
 * An extension of the automaton copier where copy is performed using
 * explicitely a state changer and a transition changer.  
 *
 * @author Alban Grastien
 */
public interface ChangerCopier<SL1,TL1,SL2,TL2>
    extends AutomatonCopier<SL1,TL1,SL2,TL2> {

    /**
     * Returns the map used to change the state labels. 
     *
     * @return the mapping that indicates how the new state label must be
     * computed.
     */
    public LabelChanger<? super SL1,? extends SL2> getStateChanger();

    /**
     * Returns the map used to change the trans labels. 
     *
     * @return the mapping that indicates how the new state label must be
     * computed.
     */
    public LabelChanger<? super TL1,? extends TL2> getTransChanger();

}
