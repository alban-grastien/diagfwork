/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

import edu.supercom.util.Pair;

/**
 * An <b>automatic pair synchroniser</b> is simply a synchroniser that
 * synchronises any pair of labels by creating a {@link Pair}.  Any pair of
 * labels can be synchronised.  
 *
 * @author Alban Grastien
 * @param <L1> the type of the elements from the left element to be synchronised.
 * @param <L2> the type of the elements from the right element to be synchronised.
 * @version 2.0
 * @since 2.0
 * @todo Replace the projection by a Boolean?
 */
public class AutomaticPairSynchroniser <L1,L2>
        extends AbstractSynchroniser <L1,L2,Pair<L1,L2>,L1,L2>
        implements Synchroniser <L1,L2,Pair<L1,L2>,L1,L2> {

    /** 
     * Creates a new instance of PairStateLabelSynchroniser.
     */
    public AutomaticPairSynchroniser() {
    }

    /**
     * A buffer to return the projection.  Since the projection is not relevant,
     * we return the first element ever received.  This may improve the
     * implementations as it is usually preferrable to have a limited number of
     * projections.  
     */
    private L1 _proj1 = null;
    private L2 _proj2 = null;

    /**
     * Synchronise the two specified labels.
     *
     * @param lb1 the first label.
     * @param lb2 the second label.
     * @return the synchronisation of both labels, or <code>null</code> if the
     * labels cannot be synchronised.
     * @throws ClassCastException if this synchroniser can deal with
     * <code>lb1</code> and <code>lb2</code>'s classes.
     */
    @Override
    public Pair<L1,L2> synchroniseLabel(L1 lb1, L2 lb2) {
        if (lb1 == null) {
            return null;
        }
        if (lb2 == null) {
            return null;
        }
        return new Pair<L1, L2>(lb1,lb2);
    }

    @Override
    public L1 projection1(L1 lb) {
        if (lb == null) {
            return null;
        }
        if (_proj1 == null) {
            _proj1 = lb;
        }

        return _proj1;
    }

    @Override
    public boolean synchroniseProjectedLabel1(L1 proj, L2 lb2) {
        return (proj != null) && (lb2 != null);
    }

    @Override
    public L2 projection2(L2 lb) {
        if (lb == null) {
            return null;
        }
        if (_proj2 == null) {
            _proj2 = lb;
        }

        return _proj2;
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 lb1, L2 proj) {
        return (proj != null) && (lb1 != null);
    }

    @Override
    public boolean synchroniseProjectedLabels(L1 proj1, L2 proj2) {
        return (proj1 != null) && (proj2 != null);
    }

    @Override
    public boolean synchronise(L1 l1, L2 l2) {
        return (l1 != null) && (l2 != null);
    }


}
