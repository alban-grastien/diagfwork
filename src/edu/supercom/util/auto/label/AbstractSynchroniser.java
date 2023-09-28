/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * A class that implements simple methods for synchroniser.  
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractSynchroniser<L1,L2,LS,LP1,LP2> implements Synchroniser<L1,L2,LS,LP1,LP2> {

    @Override
    public boolean synchronise(L1 l1, L2 l2) {
        final LP1 lp1 = projection1(l1);
        final LP2 lp2 = projection2(l2);
        return synchroniseProjectedLabels(lp1, lp2);
    }

    @Override
    public boolean synchroniseProjectedLabel1(LP1 lp1, L2 l2) {
        final LP2 lp2 = projection2(l2);
        return synchroniseProjectedLabels(lp1, lp2);
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 l1, LP2 lp2) {
        final LP1 lp1 = projection1(l1);
        return synchroniseProjectedLabels(lp1, lp2);
    }

    @Override
    public Projector<L1,LP1> projector1() {
        return new Projector<L1, LP1>() {

            @Override
            public LP1 project(L1 l) {
                return projection1(l);
            }
        };
    }

    @Override
    public Projector<L2,LP2> projector2() {
        return new Projector<L2, LP2>() {

            @Override
            public LP2 project(L2 l) {
                return projection2(l);
            }
        };
    }

}
