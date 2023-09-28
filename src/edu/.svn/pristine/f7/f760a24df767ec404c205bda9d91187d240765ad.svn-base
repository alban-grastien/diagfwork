/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * A synchroniser is an object that is able to synchronise two labels to
 * generate an object that corresponds to the product of both labels (method
 * {@link #synchroniseLabel(java.lang.Object, java.lang.Object) }.  The interface
 * provides additional methods to simplify the computation.  For instance, the
 * method <code>synchronise(l1,L2)</code> indicates whether the objects
 * synchronise but does not generate the synchronisation.  The projection allows 
 * to keep the information that is sufficient to check whether the labels 
 * synchronise; it is therefore possible to test the synchronisation between
 * projected objects, but result from the synchronisation cannot be retrieved
 * after a projection was applied.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.0
 * @param <L1> the type of the first label to synchronise.
 * @param <L2> the type of the second label to synchronise.
 * @param <LS> the type of the object resulting from the synchronisation between
 * an object of type <code>L1</code> with an object of type <code>L2</code>.
 * @param <LP1> the type of the object resulting from the projection of an
 * object of type <code>L1</code>.
 * @param <LP2> the type of the object resulting from the projection of an
 * object of type <code>L2</code>.
 * @todo: Check for all subclass that they implement all methods optimally.  
 */
public interface Synchroniser<L1,L2,LS,LP1,LP2> extends SimpleSynchroniser<L1,L2> {

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

    /**
     * Projects the specified label of the first automaton on the second
     * one.
     *
     * @param l the label to project.
     * @return the projection of the label.
     */
    public LP1 projection1(L1 l);

    /**
     * Projects the specified label of the second automaton on the first
     * one.
     *
     * @param l the label to project.
     * @return the projection of the trans label.
     */
    public LP2 projection2(L2 l);

    /**
     * Determines whether the specified projected label of the first automaton
     * synchronises with the specified label of the second automaton.
     *
     * @param l1 the projected label.
     * @param l2 the label of the second automaton.
     * @return <code>true</code> if they synchronise, <code>false</code>
     * otherwise.
     */
    public boolean synchroniseProjectedLabel1(LP1 l1, L2 l2);

    /**
     * Determines whether the specified projected label of the second automaton
     * synchronises with the specified label of the first automaton.
     *
     * @param l1 the label of the first automaton.
     * @param l2 the projected label.
     * @return <code>true</code> if they synchronise, <code>false</code>
     * otherwise.
     */
    public boolean synchroniseProjectedLabel2(L1 l1, LP2 l2);

    /**
     * Determines whether the two specified projected labels synchronise.
     *
     * @param l1 the first projected label.
     * @param l2 the second projected label.
     * @return <code>true</code> if they synchronise, <code>false</code>
     * otherwise.
     */
    public boolean synchroniseProjectedLabels(LP1 l1, LP2 l2);

    /**
     * Returns a projector that indicates how to project labels from the first
     * labels.
     *
     * @return a projector of labels of type <code>L1</code>.
     */
    public Projector<L1,LP1> projector1();

    /**
     * Returns a projector that indicates how to project labels from the first
     * labels.
     *
     * @return a projector of labels of type <code>L1</code>.
     */
    public Projector<L2,LP2> projector2();
}
