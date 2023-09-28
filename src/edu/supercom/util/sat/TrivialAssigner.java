/*
 * TrivialAssigner.java
 *
 * Created on 26 November 2008, 11:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.io.PrintStream;

/**
 * A variable assigner for any trivial semantics. 
 *
 * @author Alban Grastien 
 * @see TrivialSemantics
 */
public class TrivialAssigner implements VariableAssigner {
    
    /** Creates a new instance of TrivialAssigner */
    public TrivialAssigner() {
    }
    
    public void print(PrintStream out) {
        
    }
    
    public int surelyGetVariable(VariableSemantics var) {
        return ((TrivialSemantics)var).getVar();
    }
    
    public int getVariable(VariableSemantics var) {
        if (var instanceof TrivialSemantics) {
            return ((TrivialSemantics)var).getVar();
        }
        return 0;
    }

	@Override
	public void copy(MapVariableAssigner map) {
	}
    
}
