/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

import edu.supercom.util.Pair;

/**
 * A conditional pair synchroniser is a synchroniser that returns a {@link Pair}
 * if a specified synchroniser agrees that the labels should be synchronised,
 * and does not perform any projection.  
 *
 * @author Alban Grastien
 * @param <L1> the type of the first label.
 * @param <L2> the type of the second label.
 * @version 2.0
 * @since 2.0
 */
public abstract class ConditionalPairSynchroniser<L1,L2>
        extends AbstractSynchroniser<L1,L2,Pair<L1,L2>,L1,L2>
        implements Synchroniser<L1,L2,Pair<L1,L2>,L1,L2> {

    @Override
    public abstract boolean synchronise(L1 l1, L2 l2);

    @Override
    public Pair<L1, L2> synchroniseLabel(L1 l1, L2 l2) {
        if (synchronise(l1, l2)) {
            return new Pair<L1, L2>(l1, l2);
        }
        return null;
    }

    @Override
    public L1 projection1(L1 l) {
        return l;
    }

    @Override
    public L2 projection2(L2 l) {
        return l;
    }

    @Override
    public boolean synchroniseProjectedLabel1(L1 l1, L2 l2) {
        return synchronise(l1, l2);
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 l1, L2 l2) {
        return synchronise(l1, l2);
    }

    @Override
    public boolean synchroniseProjectedLabels(L1 l1, L2 l2) {
        return synchronise(l1, l2);
    }

}
