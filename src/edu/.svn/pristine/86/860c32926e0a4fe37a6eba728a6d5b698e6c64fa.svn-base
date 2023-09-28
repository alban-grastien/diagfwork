/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 2.0
 */
public class EmbodimentSynchroniser<L1,L2,L,LP1,LP2> 
        extends AbstractSynchroniser<
            EmbeddedLabel<L1>,EmbeddedLabel<L2>,
            EmbeddedLabel<L>,
            EmbeddedLabel<LP1>,EmbeddedLabel<LP2>
            >
        implements Synchroniser<
            EmbeddedLabel<L1>,EmbeddedLabel<L2>,
            EmbeddedLabel<L>,
            EmbeddedLabel<LP1>,EmbeddedLabel<LP2>
            > {

    public EmbodimentSynchroniser(Synchroniser<L1,L2,L,LP1,LP2> synchr) {
        _synch = synchr;
    }

    /**
     * The synchroniser.
     */
    private Synchroniser<L1,L2,L,LP1,LP2> _synch;

    @Override
    public EmbeddedLabel<L> synchroniseLabel(EmbeddedLabel<L1> l1, EmbeddedLabel<L2> l2) {
        if (l1 == null || l2 == null) {
            return null;
        }
        L l = _synch.synchroniseLabel(l1.getLabel(), l2.getLabel());
        if (l == null) {
            return null;
        }
        return MyEmbeddedLabel.embed(l);
    }

    @Override
    public EmbeddedLabel<LP1> projection1(EmbeddedLabel<L1> tl) {
        return MyEmbeddedLabel.embed(_synch.projection1(tl.getLabel()));
    }

    @Override
    public EmbeddedLabel<LP2> projection2(EmbeddedLabel<L2> tl) {
        return MyEmbeddedLabel.embed(_synch.projection2(tl.getLabel()));
    }

    @Override
    public boolean synchroniseProjectedLabel1(EmbeddedLabel<LP1> proj, EmbeddedLabel<L2> tl2) {
        if (proj == null || tl2 == null) {
            return false;
        }
        return _synch.synchroniseProjectedLabel1(proj.getLabel(), tl2.getLabel());
    }

    @Override
    public boolean synchroniseProjectedLabel2(EmbeddedLabel<L1> tl1, EmbeddedLabel<LP2> proj) {
        if (proj == null || tl1 == null) {
            return false;
        }
        return _synch.synchroniseProjectedLabel2(tl1.getLabel(),proj.getLabel());
    }

    @Override
    public boolean synchroniseProjectedLabels(EmbeddedLabel<LP1> proj1, EmbeddedLabel<LP2> proj2) {
        if (proj1 == null || proj2 == null) {
            return false;
        }
        return _synch.synchroniseProjectedLabels(proj1.getLabel(),proj2.getLabel());
    }

    @Override
    public boolean synchronise(EmbeddedLabel<L1> l1, EmbeddedLabel<L2> l2) {
        return _synch.synchronise(l1.getLabel(), l2.getLabel());
    }
}
