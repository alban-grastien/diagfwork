/*
 * MiscSemantics.java
 *
 * Created on 16 May 2008, 16:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

/**
 * Implementation of variable semantics that is basically used for tests.
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 */
public class MiscSemantics implements VariableSemantics, Comparable {
    
    /** Creates a new instance of MiscSemantics */
    public MiscSemantics() {
    }
    
    @Override
    public int compareTo(Object o) {
        return hashCode() - o.hashCode();
    }
    
}
