/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * Implementation of a label that embeds a label.  This implementation allows to
 * differenciate labels that would be considered identical.
 *
 * @author Alban Grastien
 */
public interface EmbeddedLabel<SL> {

    /**
     * Returns the state label that is embedded in this label.
     *
     * @return the state label actually represented by this label.  
     */
    public SL getLabel();

}