/*
 * MinisatClauseStream.java
 *
 * Created on 23 April 2008, 16:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

/**
 * Implements a clause stream which directs the clauses to a core minisat 
 * solver.  No two instances should be used in parallel, but the solving can be 
 * called several times.  A <code>minisatClauseStream</code> should probably be 
 * used in conjunction with a {@link BufferedClauseStream}.
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 */
public class CoreMinisatClauseStream 
             extends AbstractClauseStream 
            implements ClauseStream {
    
    
    /** Creates a new instance of MinisatClauseStream */
    public CoreMinisatClauseStream() {
        create();
    }
    
    /**
     * Sends the clauses to minisat.  To enable the simultaneous transmission 
     * of several clauses, several clauses can be encoded in the array of 
     * literals, each being separated by a single <code>0</code>.
     *
     * @param lits the literals in the clauses.
     */
    public void put(int [] lits) {
        sendMinisat(lits);
    }
    
    public void put(Clause clause) {
        //System.out.println(clause);
        // Improve please
        int[] lits = clause.literals();
        sendMinisat(lits);
    }
    
    public void put(Clause[] clauses) {
        int size = clauses.length -1;
        for (int i=0 ; i<clauses.length ; i++) {
            Clause clause = clauses[i];
            size += clause.size();
        }
        
        int[] lits = new int[size];
        int i=0, j=0;
        while (i < clauses.length) {
            Clause clause = clauses[i];
            size = clause.size();
            for (int k=0 ; k<size ; k++) {
                lits[j] = clause.literal(k);
                j++;
            }
            j++;
            i++;
        }
        
        put(lits);
    }
    
    public void put(int[][] clauses) {
        int size = clauses.length -1;
        for (int i=0 ; i<clauses.length ; i++) {
            int[] clause = clauses[i];
            size += clause.length;
        }
        
        int[] lits = new int[size];
        int i=0, j=0;
        while (i < clauses.length) {
            int[] clause = clauses[i];
            size = clause.length;
            for (int k=0 ; k<size ; k++) {
                lits[j] = clause[k];
                j++;
            }
            j++;
            i++;
        }
        
        put(lits);
    }
    
    public void close() {}
    
    private native void create();
    
    private native void sendMinisat(int[] lits);
    
    /**
     * Runs the solver on the SAT problem.
     *
     * @return <code>true</code> if the problem is solvable.
     */
    public native boolean solve();
    
    /**
     * Runs the solver on the SAT problem with the specified list of unit 
     * clauses.
     *
     * @param uc an array of literals (integer values) that represent the unit 
     * clauses.
     * @return <code>true</code> if the problem is solvable.
     */
    public native boolean solve(int [] uc);
    
    /**
     * Returns the valuation given to the specified variable last time the 
     * solver was called.
     *
     * @return <code>true</code> if the variable is assigned a positiv value, 
     * false otherwise.
     * @throws Error if the solver was not called.
     * @throws Error if the problem was not solvable.
     */
    public native boolean model(int var);

    public native boolean[] model(int[] vars);

    public native void printDecisions();
    
    static {
        System.loadLibrary("MS");
    }

    public static void main(String [] args) {
//        int[][] mat = {{1,2},{-1,2},{-2,3},{1,-3,4},{-1,-3,4},{-2,-4},{2,-4}};
        int[][] mat = {{1,-2},{-1,-2},{2,3},{1,-3,4},{-1,-3,4}};
//        int[] tab = { 1, -2
//                    , 0, -1, -2
//                    , 0,  2,  3
//                    , 0,  1, -3, 4
//                    , 0, -1, -3, 4
////                    , 0,  2, -4
//                    };
        
        CoreMinisatClauseStream stream = new CoreMinisatClauseStream();
        
        stream.put(mat);
        stream.printDecisions();
        
        stream.solve();
    }

}
