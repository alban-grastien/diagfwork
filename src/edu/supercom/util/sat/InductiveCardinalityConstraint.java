/*
 * InductiveCardinalityConstraint.java
 *
 * Created on 13 August 2008, 14:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class implements the cardinality constraint by induction.  Basically,
 * for each variable <code>v</code> in the set of variables, for each
 * <code>i</code> in <code>{1,...,k}</code> a propositional variable
 * <code>v_i</code> is created.  If <code>v</code> is <i>true</i>, then there
 * is a <code>v_i</code> that must be <i>true</i>.  Furthermore, at most one
 * element of <code>v_i</code> can be <i>true</i> for a fixed <code>i</code>.
 * <p />
 * <b>Reference</b>: M. B\"uttner and J. Rintanen, Satisfiability Planning with
 * Constraints on the Number of Actions, ICAPS-05, pp. 292--299, 2005.
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 */
public class InductiveCardinalityConstraint {
    
    /**
     * Generates a constraint that the specified collection of variables has
     * at most the specified number of variables assigned to <i>true</i>.
     *
     * @param vars the collection of semantics variables.
     * @param varass the assigner that returns the literal associated with the semantics variables.
     * @param max the maximum number of variables assigned to <i>true</true>.
     * @param loader the variable loader that allows to define new variables.
     * @param out the clause stream where the clauses generated are stored.
     */
    public static void atMost(
            Collection vars,
            VariableAssigner varass,
            int max,
            VariableLoader loader,
            ClauseStream out) {
        int nbvars = vars.size();
        
        int nbAll = nbvars * max;
        int first = loader.allocate(nbAll);
        System.out.println("Allocating " + nbAll + " variables.");
        
        { // var is true => var_j is true for some j
            int i = 0;
            int[] arr = new int[max+1];
            for (Iterator varIt = vars.iterator() ; varIt.hasNext() ; ) {
                {
                    VariableSemantics sem = (VariableSemantics)varIt.next();
                    int var = varass.surelyGetVariable(sem);
                    arr[max] = -var;
                }
                
                for (int j=0 ; j<max ; j++) {
                    arr[j] = first + (i * max) + j;
                }
                out.put(arr);
                i++;
            }
        }
        
        { // At most one var_j is true for a given j
            int [] arr2 = new int[2];
            for (int j=0 ; j<max ; j++) {
                for (int k=0 ; k<nbvars ; k++) {
                    arr2[0] = -(first + (k * max) + j);
                    for (int i=k+1 ; i<nbvars ; i++) {
                        arr2[1] = -(first + (i * max) + j);
                        out.put(arr2);
                    }
                }
            }
        }
    }
    
    /**
     * Generates a constraint that the specified collection of variables has
     * exactly the specified number of variables assigned to <i>true</i>.
     *
     * @param vars the collection of semantics variables.
     * @param varass the assigner that returns the literal associated with the semantics variables.
     * @param max the maximum number of variables assigned to <i>true</true>.
     * @param loader the variable loader that allows to define new variables.
     * @param out the clause stream where the clauses generated are stored.
     */
    public static void exactly(
            Collection vars,
            VariableAssigner varass,
            int max,
            VariableLoader loader,
            ClauseStream out) {
        int nbvars = vars.size();
        
        int nbAll = nbvars * max;
        int first = loader.allocate(nbAll);
        System.out.println("Allocating " + nbAll + " variables.");
        
        { // var is true => var_j is true for exactly one j and conversely
            int i = 0;
            int[] arr = new int[max+1];
            int[] arr2 = new int[2];
            int[] arr22 = new int[2];
            for (Iterator varIt = vars.iterator() ; varIt.hasNext() ; ) {
                {
                    VariableSemantics sem = (VariableSemantics)varIt.next();
                    int var = varass.surelyGetVariable(sem);
                    arr[max] = -var;
                    arr2[0] = var;
                }
                
                for (int j=0 ; j<max ; j++) {
                    arr[j] = first + (i * max) + j;
                    arr2[1] = -arr[j];
                    out.put(arr2);
                    arr22[0] = arr2[1];
                    for (int k=j+1 ; k<max ; k++) {
                        arr22[1] = -(first + (i * max) + k);
                        out.put(arr22);
                    }
                }
                out.put(arr);
                i++;
            }
        }
        
        { // Exactly one var_j is true for a given j
            int[] arr2 = new int[2];
            int[] arr = new int[nbvars];
            for (int j=0 ; j<max ; j++) {
                for (int k=0 ; k<nbvars ; k++) {
                    arr[k] = first + (k * max) + j;
                    arr2[0] = -arr[k];
                    for (int i=k+1 ; i<nbvars ; i++) {
                        arr2[1] = -(first + (i * max) + j);
                        out.put(arr2);
                    }
                }
                out.put(arr);
            }
        }
    }
    
    /**
     * A simple test.
     */
    public static void main(String [] args) {
        MinisatClauseStream mini = new MinisatClauseStream();
        VA va = new VA();
        VariableLoader loader = new VariableLoader();
        loader.allocate(5);
        
        Collection<I> list = new ArrayList<I>();
        for (int i=0 ; i<5 ; i++) {
            list.add(new I(i+1));
        }
        
        int[][] mat = {{1},
                       {-2,-5},{-2,3},{-2,4},
                       {2,-3},{2,4},{2,5}};
        mini.put(mat);
        
        exactly(list,va,5,loader,mini);
        
        if (mini.solve()) {
            for (int i=1 ; i<=loader.numberOfVariables() ; i++) {
                System.out.println(mini.model(i));
            }
            System.out.println("SAT");
        } else {
            System.out.println("UNSAT");
        }
    }
}

class I implements VariableSemantics {
    public int _i;
    public I(int i) {
        _i = i;
    }
}

class VA implements VariableAssigner {
    public void print(java.io.PrintStream out) {
    }
    public int surelyGetVariable(VariableSemantics sem) {
        return ((I)sem)._i;
    }
    public int getVariable(VariableSemantics sem) {
        return ((I)sem)._i;
    }
	@Override
	public void copy(MapVariableAssigner map) {
	}
}
