/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.changer;

/**
 *
 * @author Alban Grastien
 */
public class CompositionalChanger<L1,L2,L3> implements LabelChanger<L1,L3> {

    final LabelChanger<L1,L2> _c1;
    final LabelChanger<L2,L3> _c2;
    
    public CompositionalChanger(LabelChanger<L1,L2> c1, LabelChanger<L2,L3> c2) {
        _c1 = c1;
        _c2 = c2;
    }

    @Override
    public L3 getLabel(L1 old) {
        final L2 l2 = _c1.getLabel(old);
        return _c2.getLabel(l2);
    }
}
