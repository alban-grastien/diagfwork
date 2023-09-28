/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * Synchronises two strings by concatenating them with the specified middle
 * string.
 *
 * @author Alban Grastien
 */
public class StringSynchroniser extends AbstractSynchroniser<String,String,String,String,String>
        implements Synchroniser<String,String,String,String,String> {

    /**
     * The default middle value : <code>"-"</code>. 
     */
    public static final String DEFAULT_MIDDLE = "-";

    private static final String DEFAULT_STRING = "";

    private final String _middle;

    /**
     * Creates a string synchroniser with default middle value of {@link
     * #DEFAULT_MIDDLE}.
     */
    public StringSynchroniser() {
        this(DEFAULT_MIDDLE);
    }

    /**
     * Creates a string synchroniser with specified middle value.
     *
     * @param mid the middle value.
     */
    public StringSynchroniser(String mid) {
        _middle = mid;
    }

    @Override
    public String synchroniseLabel(String l1, String l2) {
        return l1 + _middle + l2;
    }

    @Override
    public String projection1(String tl) {
        if (tl == null) {
            return null;
        }
        return DEFAULT_STRING;
    }

    @Override
    public String projection2(String tl) {
        if (tl == null) {
            return null;
        }
        return DEFAULT_STRING;
    }

    @Override
    public boolean synchroniseProjectedLabel1(String proj, String tl2) {
        return proj != null && tl2 != null;
    }

    @Override
    public boolean synchroniseProjectedLabel2(String tl1, String proj) {
        return proj != null && tl1 != null;
    }

    @Override
    public boolean synchroniseProjectedLabels(String proj1, String proj2) {
        return proj1 != null && proj2 != null;
    }

    @Override
    public boolean synchronise(String l1, String l2) {
        return (l1 != null) && (l2 != null);
    }

}
