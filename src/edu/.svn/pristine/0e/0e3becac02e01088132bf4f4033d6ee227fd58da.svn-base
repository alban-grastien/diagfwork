/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.changer;

/**
 * An implementation of the label mapper that adds a prefix to StringLabel.
 *
 * @author Alban Grastien
 */
public class StringPrefixAdder implements LabelChanger<String,String> {

    /**
     * The prefix to add to the labels.  
     */
    private String _p;

    /**
     * Creates a label mapper that adds the specified prefix to any string label 
     * it is given.
     *
     * @param p the prefix that will be added to the labels.  
     */
    public StringPrefixAdder(String p) {
        _p = p;
    }

    @Override
    public String getLabel(String key) {
        if (key == null) {
            return key;
        }

        return _p + key;
    }

}
