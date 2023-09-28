/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.changer;

import edu.supercom.util.auto.op.AutomatonLabelChanger;

/**
 * A <b>label changer</b> is basically a mapping that maps every old label with
 * a new label. 
 *
 * @author Alban Grastien
 * @param <O> the type of the old label.
 * @param <N> the type of the new label.
 * @see AutomatonLabelChanger
 */
public interface LabelChanger<O,N> {

    /**
     * Indicates the new label associated to the old label.
     *
     * @param old the old label.
     * @return the new label. 
     */
    public N getLabel(O old);

}
