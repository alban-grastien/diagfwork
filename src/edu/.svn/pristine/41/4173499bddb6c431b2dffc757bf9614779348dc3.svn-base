/*
 * DatedSynchroniser.java
 *
 * Created on 15 February 2007, 18:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.auto.label;

/**
 * This class implements the StateSynchroniser and TransSynchroniser interfaces.  
 * It can synchronise dated label as long as the synchroniser of the core labels 
 * is provided.  The synchronisation of two dated label is possible if the 
 * synchronisation between the core labels is possible and if the intersection 
 * of their time intervals is not empty.  The synchronisation is the dated label 
 * defined by the synchronisation of the core labels with the time intervale 
 * corresponding to the intersection between the time intervale of the two 
 * dated labels.
 *
 * @author Alban Grastien
 * @author Carole Aujames
 * @since 2.0
 * @version 1.0
 */
public class DatedSynchroniser<L1, L2, LS, LP1, LP2>
        extends AbstractSynchroniser<DatedLabel<? extends L1>, DatedLabel<? extends L2>, DatedLabel<LS>, DatedLabel<LP1>, DatedLabel<LP2>> 
        implements Synchroniser<DatedLabel<? extends L1>, DatedLabel<? extends L2>, DatedLabel<LS>, DatedLabel<LP1>, DatedLabel<LP2>> {

    /**
     * The core synchroniser.
     */
    private Synchroniser<? super L1, ? super L2, LS, LP1, LP2> _coreSynchroniser;

    /** 
     * Creates a new DatedSynchroniser that uses the specified core 
     * label synchroniser.
     *
     * @param core the core synchroniser.
     */
    public DatedSynchroniser(Synchroniser<? super L1, ? super L2, LS, LP1, LP2> core) {
        _coreSynchroniser = core;
    }

    @Override
    public DatedLabel<LS> synchroniseLabel(DatedLabel<? extends L1> lb1, DatedLabel<? extends L2> lb2) {
        if (lb1 == null || lb2 == null) {
            return null;
        }
        if (lb1.getMax() <= lb2.getMin()) {
            return null;
        }
        if (lb2.getMax() <= lb1.getMin()) {
            return null;
        }
        L1 core1 = lb1.getCore();
        L2 core2 = lb2.getCore();
        LS core = _coreSynchroniser.synchroniseLabel(core1, core2);
        if (core == null) {
            return null;
        }
        int min = Math.max(lb1.getMin(), lb2.getMin());
        int max = Math.min(lb1.getMax(), lb2.getMax());

        return new DatedLabel<LS>(core, min, max);
    }

    @Override
    public DatedLabel<LP1> projection1(DatedLabel<? extends L1> l) {
        L1 core = l.getCore();
        LP1 newcore = _coreSynchroniser.projection1(core);
        if (newcore == null) {
            return null;
        }
        return new DatedLabel<LP1>(newcore, l.getMin(), l.getMax());
    }

    @Override
    public boolean synchroniseProjectedLabel1(DatedLabel<LP1> proj, DatedLabel<? extends L2> l2) {
        if (proj == null || l2 == null) {
            return false;
        }
        if (proj.getMax() <= l2.getMin()) {
            return false;
        }
        if (l2.getMax() <= proj.getMin()) {
            return false;
        }
        return _coreSynchroniser.synchroniseProjectedLabel1(proj.getCore(), l2.getCore());
    }

    @Override
    public DatedLabel<LP2> projection2(DatedLabel<? extends L2> l) {
        L2 core = l.getCore();
        LP2 newcore = _coreSynchroniser.projection2(core);
        if (newcore == null) {
            return null;
        }
        return new DatedLabel<LP2>(newcore, l.getMin(), l.getMax());
    }

    @Override
    public boolean synchroniseProjectedLabel2(DatedLabel<? extends L1> l1, DatedLabel<LP2> proj) {
        if (proj == null || l1 == null) {
            return false;
        }
        if (proj.getMax() <= l1.getMin()) {
            return false;
        }
        if (l1.getMax() <= proj.getMin()) {
            return false;
        }
        return _coreSynchroniser.synchroniseProjectedLabel2(l1.getCore(), proj.getCore());
    }

    @Override
    public boolean synchroniseProjectedLabels(DatedLabel<LP1> proj1, DatedLabel<LP2> proj2) {
        if (proj1 == null || proj2 == null) {
            return false;
        }
        if (proj1.getMax() <= proj2.getMin()) {
            return false;
        }
        if (proj1.getMax() <= proj2.getMin()) {
            return false;
        }
        return _coreSynchroniser.synchroniseProjectedLabels(proj1.getCore(), proj2.getCore());
    }
}
