/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * Inverts the labels in a synchroniser.  This transforms a synchroniser
 * between As and Bs into a synchroniser between Bs and As. 
 *
 * @author Alban Grastien
 */
public class SynchroniserInverter<L1,L2,L,LP1,LP2> 
        extends AbstractSynchroniser<L1,L2,L,LP1,LP2>
        implements Synchroniser<L1,L2,L,LP1,LP2> {

    /**
     * The internal synchroniser.
     */
    private Synchroniser<L2,L1,L,LP2,LP1> _synch;

    /**
     * Builds an inverter.
     *
     * @param s the synchroniser that is inverted.
     */
    public SynchroniserInverter(Synchroniser<L2,L1,L,LP2,LP1> s) {
        _synch = s;
    }

    @Override
    public L synchroniseLabel(L1 l1, L2 l2) {
        return _synch.synchroniseLabel(l2, l1);
    }

    @Override
    public LP1 projection1(L1 tl) {
        return _synch.projection2(tl);
    }

    @Override
    public LP2 projection2(L2 tl) {
        return _synch.projection1(tl);
    }

    @Override
    public boolean synchroniseProjectedLabel1(LP1 proj, L2 tl2) {
        return _synch.synchroniseProjectedLabel2(tl2, proj);
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 tl1, LP2 proj) {
        return _synch.synchroniseProjectedLabel1(proj, tl1);
    }

    @Override
    public boolean synchroniseProjectedLabels(LP1 proj1, LP2 proj2) {
        return _synch.synchroniseProjectedLabels(proj2, proj1);
    }

    @Override
    public boolean synchronise(L1 l1, L2 l2) {
        return _synch.synchronise(l2, l1);
    }

}
