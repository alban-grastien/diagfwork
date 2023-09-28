/*
 * TrivialSemantics.java
 *
 * Created on 26 November 2008, 11:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

/**
 * A trivial semantics is a simple variable semantics that contains 
 * its own variable as an argument. 
 *
 * @author Alban Grastien
 */
public class TrivialSemantics implements VariableSemantics {
    
    private int _var;
    
    /** 
     * Creates a new instance of TrivialSemantics.
     *
     * @param var the variable associated with this semantics. 
     */
    public TrivialSemantics(int var) {
        _var = var;
    }
    
    /**
     * Access to the variable of this trivial semantics. 
     *
     * @return the variable of this trivial semantics. 
     */
    public int getVar() {
        return _var;
    }
    
}
