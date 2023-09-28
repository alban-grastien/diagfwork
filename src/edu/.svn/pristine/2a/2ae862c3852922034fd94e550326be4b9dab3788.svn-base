/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.changer;

import java.util.Set;

/**
 * An <b>epsiloner</b> is a label changer that changes some specified labels to
 * epsilon and does not change the other.  
 *
 * @author Alban Grastien
 */
public class Epsiloner<L> implements LabelChanger<L,L> {

    private L _epsi;

    private Set<? super L> _toChange;

    /**
     * Creates a label changer that changes any label from the specified set to
     * the specified label.
     *
     * @param epsi the label that any label of <code>toChange</code> will be
     * changed to.
     * @param toChange the set of labels that will be changed into
     * <code>epsi</code>.
     */
    public Epsiloner(L epsi, Set<? super L> toChange) {
        _epsi = epsi;
        _toChange = toChange;
    }

    @Override
    public L getLabel(L old) {
        if (_toChange.contains(old)) {
            return _epsi;
        }

        return old;
    }

}
