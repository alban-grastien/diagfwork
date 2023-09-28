/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * A <i>keep-first synchroniser</i> whose product of the synchronisation
 * (specified by a synchroniser given as a parameter of the keep-first
 * synchroniser) is the first label.  <p />
 *
 * E.g., given a synchroniser <code>s</code>,
 * two labels <code>l1</code> and <code>l2</code>, and a synchroniser
 * <code>kfs = new KeepFirstSynchroniser</code>, the result of
 * <code>kfs.synchroniseLabels(l1,l2)</code> will be <code>null</code> if
 * <code>s.synchroniseLabels(l1,l2)</code> is <code>null</code>, and
 * <code>l1</code> otherwise.  The result of all other methods of
 * <code>kfs</code> will be the same as respective methods of <code>s</code>.  
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <L1> the type of the first label to synchronise.
 * @param <L2> the type of the second label to synchronise.
 * @param <LP1> the type of the object resulting from the projection of an
 * object of type <code>L1</code>.
 * @param <LP2> the type of the object resulting from the projection of an
 * object of type <code>L2</code>.
 */
public class KeepFirstSynchroniser<L1,L2,LP1,LP2> implements Synchroniser<L1,L2,L1,LP1,LP2> {

    /**
     * The actual synchroniser.  
     */
    private final Synchroniser<L1,L2,?,LP1,LP2> _s;

    /**
     * Builds a keep first synchroniser based on the specified synchroniser.
     *
     * @param s the synchroniser on which the keep first synchoniser is built.  
     */
    public KeepFirstSynchroniser(Synchroniser<L1,L2,?,LP1,LP2> s) {
        _s = s;
    }

    @Override
    public L1 synchroniseLabel(L1 l1, L2 l2) {
        if (_s.synchronise(l1, l2)) {
            return l1;
        }
        return null;
    }

    @Override
    public LP1 projection1(L1 l) {
        return _s.projection1(l);
    }

    @Override
    public LP2 projection2(L2 l) {
        return _s.projection2(l);
    }

    @Override
    public boolean synchroniseProjectedLabel1(LP1 l1, L2 l2) {
        return _s.synchroniseProjectedLabel1(l1, l2);
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 l1, LP2 l2) {
        return _s.synchroniseProjectedLabel2(l1, l2);
    }

    @Override
    public boolean synchroniseProjectedLabels(LP1 l1, LP2 l2) {
        return _s.synchroniseProjectedLabels(l1, l2);
    }

    @Override
    public boolean synchronise(L1 l1, L2 l2) {
        return _s.synchronise(l1, l2);
    }

    @Override
    public Projector<L1, LP1> projector1() {
        return _s.projector1();
    }

    @Override
    public Projector<L2, LP2> projector2() {
        return _s.projector2();
    }

}
