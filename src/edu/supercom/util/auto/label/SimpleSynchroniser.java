/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * A synchroniser is an object that is able to synchronise two labels to
 * generate an object that corresponds to the product of both labels (method
 * {@link #synchroniseLabel(java.lang.Object, java.lang.Object) }.  However, it
 * does not specify what the result of the synchronisation or what the result of
 * a projection is.  
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <L1> the class of the first object to synchronise.
 * @param <L2> the class of the second object to synchronise.
 * @see Synchroniser
 */
public interface SimpleSynchroniser<L1,L2> {


    /**
     * Tests if the two specified labels synchronise.
     *
     * @param l1 the label of the first transition.
     * @param l2 the label of the second transition.
     * @return <code>true</code> if the two labels synchronise,
     * <code>false</code> otherwise.
     */
    public boolean synchronise(L1 l1, L2 l2);

}
