/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * An equals synchroniser is an implementation of a synchroniser where the
 * labels are synchronised when they are equal.
 *
 * @author Alban Grastien
 * @version 2.1.2
 * @since 2.1.2
 * @param <L> The type of the label.  
 */
public class EqualSynchroniser<L> extends AbstractSynchroniser<L,L,L,L,L> {

    @Override
    public final L synchroniseLabel(L l1, L l2) {
        if (synchronise(l1, l2)) {
            return l1;
        }
        return null;
    }

    @Override
    public final L projection1(L l) {
        return l;
    }

    @Override
    public final L projection2(L l) {
        return l;
    }

    @Override
    public final boolean synchroniseProjectedLabel1(L l1, L l2) {
        return synchronise(l1, l2);
    }

    @Override
    public final boolean synchroniseProjectedLabel2(L l1, L l2) {
        return synchronise(l1, l2);
    }

    @Override
    public final boolean synchroniseProjectedLabels(L l1, L l2) {
        return synchronise(l1, l2);
    }

    @Override
    public final boolean synchronise(L l1, L l2) {
        return l1 == l2 || l1.equals(l2);
    }

}
