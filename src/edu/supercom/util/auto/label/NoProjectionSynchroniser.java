/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * A NoProjectionSynchroniser is an object that is able to synchronise two
 * elements.  It is not able to project these elements.  
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <L1> the class of the first object to synchronise.
 * @param <L2> the class of the second object to synchronise.
 * @param <LS> the class of the synchronisation.  
 * @see Synchroniser
 */
public interface NoProjectionSynchroniser<L1,L2,LS> extends SimpleSynchroniser<L1,L2> {

    /**
     * Synchronises the two specified labels and returns the result of this
     * synchronisation.
     *
     * @param l1 the label of the first transition.
     * @param l2 the label of the second transition.
     * @return the synchronisation of both labels, or <code>null</code> if the
     * labels cannot be synchronised.
     */
    public LS synchroniseLabel(L1 l1, L2 l2);


}
