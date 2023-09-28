/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.changer;

/**
 *
 * @author Alban Grastien
 */
public class ToStringChanger<L> implements LabelChanger<L,String> {

    @Override
    public String getLabel(L old) {
        if (old == null) {
            return "null";
        }
        return old.toString();
    }

}
