/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

import edu.supercom.util.Pair;

/**
 * This class implements a synchroniser.
 * The state label synchroniser2 synchronises two state labels,
 * given the state label synchroniser given in parameter. If the
 * synchronisation is possible, the result is a pair state label
 * with the 2 state labels.
 *
 * @author Alban Grastien
 * @since 2.0
 * @version 2.0
 */
public class EmbeddedPairSynchroniser <L1,L2,LP1,LP2>
        extends AbstractSynchroniser<L1,L2,Pair<L1,L2>,LP1,LP2>
        implements Synchroniser <L1,L2,Pair<L1,L2>,LP1,LP2> {
	/**
	 * The label synchroniser that determines whether the labels synchronise.  
	 */
	private Synchroniser<L1,L2,?,LP1,LP2> _sls;


	/**
     * Creates a StringStateLabelSynchroniser2.
     *
     * @param sls the string state label synchroniser.
     */
	public EmbeddedPairSynchroniser(Synchroniser<L1,L2,?,LP1,LP2> sls){
		_sls = sls;
	}


	/**
	 * Synchronises the two specified state labels.
	 *
	 * @param lb1 the label of the first state.
	 * @param lb2 the label of the second state.
	 * @return a pair state label of the two state labels, or
	 * <code>null</code> if the labels cannot be synchronised.
	 */
    @Override
	public Pair<L1,L2> synchroniseLabel(L1 lb1, L2 lb2) {
		if (_sls.synchronise(lb1, lb2)) {
			return new Pair<L1,L2>(lb1, lb2);
		} else {
			return null;
		}

	}

    @Override
    public LP1 projection1(L1 lb) {
        return _sls.projection1(lb);
    }

    @Override
    public boolean synchroniseProjectedLabel1(LP1 proj, L2 lb2) {
        return _sls.synchroniseProjectedLabel1(proj, lb2);
    }

    @Override
    public LP2 projection2(L2 lb) {
        return _sls.projection2(lb);
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 lb1, LP2 proj) {
        return _sls.synchroniseProjectedLabel2(lb1, proj);
    }

    @Override
    public boolean synchroniseProjectedLabels(LP1 proj1, LP2 proj2) {
        return _sls.synchroniseProjectedLabels(proj1, proj2);
    }

    @Override
    public boolean synchronise(L1 l1, L2 l2) {
        return _sls.synchronise(l1, l2);
    }

}
