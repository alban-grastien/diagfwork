/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

import java.util.HashMap;
import java.util.Map;

/**
 * A single result synchroniser is a synchroniser that makes sure it does not
 * return different versions of the same object.  In other words, if
 * <code>method(a1,...,an)</code> and <code>method(b1,...,bn)</code> return
 * objects that are equal (both in term of {@link Object#hashCode()} and {@link
 * Object#equals(java.lang.Object)}), then the synchroniser returns the same
 * object.  This is particularly useful if many similar objects are expected to
 * be returned.  
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <L1> the type of the first label to synchronise.
 * @param <L2> the type of the second label to synchronise.
 * @param <LS> the type of the synchronised label.
 * @param <P1> the type of the projection of the first label.
 * @param <P2> the type of the projection of the second label.
 */
public class SingleResultSynchroniser<L1,L2,LS,P1,P2> implements Synchroniser<L1,L2,LS,P1,P2> {

    /**
     * The synchroniser used to compute the solutions.  
     */
    private final Synchroniser<L1,L2,LS,P1,P2> _int;
    /**
     * The map of objects of type LS.
     */
    private final Map<LS,LS> _lsMap;
    /**
     * The map of objects of type P1.
     */
    private final Map<P1,P1> _p1Map;
    /**
     * The map of objects of type P2.
     */
    private final Map<P2,P2> _p2Map;

    /**
     * Builds a single result synchroniser that uses the specified synchroniser
     * to compute the solutions.
     *
     * @param syn the synchroniser that is used to compute the solutions.  
     */
    public SingleResultSynchroniser(Synchroniser<L1,L2,LS,P1,P2> syn) {
        _int = syn;
        _lsMap = new HashMap<LS, LS>();
        _p1Map = new HashMap<P1, P1>();
        _p2Map = new HashMap<P2, P2>();
    }

    @Override
    public LS synchroniseLabel(L1 l1, L2 l2) {
        final LS solution = _int.synchroniseLabel(l1, l2);
        {
            final LS result = _lsMap.get(solution);
            if (result != null) {
                return result;
            }
        }
        _lsMap.put(solution, solution);
        return solution;
    }

    @Override
    public P1 projection1(L1 l) {
        final P1 solution = _int.projection1(l);
        {
            final P1 result = _p1Map.get(solution);
            if (result != null) {
                return result;
            }
        }
        _p1Map.put(solution, solution);
        return solution;
    }

    @Override
    public P2 projection2(L2 l) {
        final P2 solution = _int.projection2(l);
        {
            final P2 result = _p2Map.get(solution);
            if (result != null) {
                return result;
            }
        }
        _p2Map.put(solution, solution);
        return solution;
    }

    @Override
    public boolean synchroniseProjectedLabel1(P1 l1, L2 l2) {
        return _int.synchroniseProjectedLabel1(l1, l2);
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 l1, P2 l2) {
        return _int.synchroniseProjectedLabel2(l1, l2);
    }

    @Override
    public boolean synchroniseProjectedLabels(P1 l1, P2 l2) {
        return _int.synchroniseProjectedLabels(l1, l2);
    }

    @Override
    public Projector<L1, P1> projector1() {
        return _int.projector1();
    }

    @Override
    public Projector<L2, P2> projector2() {
        return _int.projector2();
    }

    @Override
    public boolean synchronise(L1 l1, L2 l2) {
        return _int.synchronise(l1, l2);
    }

}
