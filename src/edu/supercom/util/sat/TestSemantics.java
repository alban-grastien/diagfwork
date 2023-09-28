/*
 * TestSemantics.java
 *
 * Created on 30 October 2007, 17:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

/**
 * A test semantics is an implementation of variable semantics that is used for
 * testing only.  Two test semantics are different if they point to different
 * memory spaces.  
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 1.0
 */
public class TestSemantics implements VariableSemantics {
    
    /**
     * Creates a new instance of TestSemantics
     */
    public TestSemantics() {
    }
    
}
