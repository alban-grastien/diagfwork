/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util.auto.label;

import edu.supercom.util.Pair;

/**
 * A <b>pair synchroniser</b> is a synchroniser that synchronises separately
 * different objects of a pair.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.0
 */
public class PairSynchroniser<L11, L21, LP1, P11, P21, L12, L22, LP2, P12, P22>
        extends AbstractSynchroniser<Pair<L11, L12>, Pair<L21, L22>, Pair<LP1, LP2>, Pair<P11, P12>, Pair<P21, P22>>
        implements Synchroniser<Pair<L11, L12>, Pair<L21, L22>, Pair<LP1, LP2>, Pair<P11, P12>, Pair<P21, P22>> {

    /**
     * The synchroniser for the first elements of the pair.
     */
    private final Synchroniser<L11, L21, LP1, P11, P21> _firstSynchro;
    /**
     * The synchroniser for the second elements of the pair.
     */
    private final Synchroniser<L12, L22, LP2, P12, P22> _secondSynchro;

    /**
     * Builds a pair synchroniser that synchronises pairs of elements by using
     * the first specified synchroniser for the first elements of the pairs, and
     * the second specified synchroniser for the second elements of the pairs.
     *
     * @param s1 the synchroniser of the first elements in the pairs to
     * synchronise.
     * @param s2 the synchroniser of the second elements in the pairs to
     * synchronise.
     */
    public PairSynchroniser(
            Synchroniser<L11, L21, LP1, P11, P21> s1,
            Synchroniser<L12, L22, LP2, P12, P22> s2) {
        _firstSynchro = s1;
        _secondSynchro = s2;
    }

    public static <L11, L21, LP1, P11, P21, L12, L22, LP2, P12, P22> PairSynchroniser buildSynchroniser(
            Synchroniser<L11, L21, LP1, P11, P21> s1,
            Synchroniser<L12, L22, LP2, P12, P22> s2) {
        return new PairSynchroniser<L11, L21, LP1, P11, P21, L12, L22, LP2, P12, P22>(s1,s2);
    }

    @Override
    public Pair<LP1, LP2> synchroniseLabel(Pair<L11, L12> l1, Pair<L21, L22> l2) {
        final LP1 lp1 = _firstSynchro.synchroniseLabel(l1.first(), l2.first());
        if (lp1 == null) {
            return null;
        }
        final LP2 lp2 = _secondSynchro.synchroniseLabel(l1.second(), l2.second());
        if (lp2 == null) {
            return null;
        }

        return new Pair<LP1, LP2>(lp1,lp2);
    }

    @Override
    public Pair<P11, P12> projection1(Pair<L11, L12> l) {
        return new Pair<P11, P12>(
                _firstSynchro.projection1(l.first()),
                _secondSynchro.projection1(l.second()));
    }

    @Override
    public Pair<P21, P22> projection2(Pair<L21, L22> l) {
        return new Pair<P21, P22>(
                _firstSynchro.projection2(l.first()),
                _secondSynchro.projection2(l.second()));
    }

    @Override
    public boolean synchroniseProjectedLabel1(Pair<P11, P12> l1, Pair<L21, L22> l2) {
        return _firstSynchro.synchroniseProjectedLabel1(l1.first(), l2.first()) &&
                _secondSynchro.synchroniseProjectedLabel1(l1.second(), l2.second());
    }

    @Override
    public boolean synchroniseProjectedLabel2(Pair<L11, L12> l1, Pair<P21, P22> l2) {
        return _firstSynchro.synchroniseProjectedLabel2(l1.first(), l2.first()) &&
                _secondSynchro.synchroniseProjectedLabel2(l1.second(), l2.second());
    }

    @Override
    public boolean synchroniseProjectedLabels(Pair<P11, P12> l1, Pair<P21, P22> l2) {
        return _firstSynchro.synchroniseProjectedLabels(l1.first(), l2.first()) &&
                _secondSynchro.synchroniseProjectedLabels(l1.second(), l2.second());
    }

    @Override
    public boolean synchronise(Pair<L11, L12> l1, Pair<L21, L22> l2) {
        {
            final boolean b = _firstSynchro.synchronise(l1.first(), l2.first());
            if (!b) {
                return false;
            }
        }
        {
            final boolean b = _secondSynchro.synchronise(l1.second(), l2.second());
            if (!b) {
                return false;
            }
        }
        
        return true;
    }
}
