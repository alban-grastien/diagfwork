/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * A projector is an object that is able to project a label on another label.  
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 2.0
 * @param <L> the original label class.
 * @param <PL> the label class after projection.  
 */
public interface Projector<L,PL> {

    /**
     * Projects the specified label.
     *
     * @param l the label that is projected.
     * @return a projection of label <code>l</code>.  
     */
    public PL project(L l);

}
