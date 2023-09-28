/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.label.Synchroniser;
import edu.supercom.util.auto.label.SynchroniserInverter;

/**
 *
 * @author Alban Grastien
 */
public abstract class AbstractPruner implements AutomatonPruner {

    /**
     * Indicates that no automaton was modified.
     */
    public static final int NO_MODIFICATION = 0;

    /**
     * Indicates that only the first automaton was modified.
     */
    public static final int FIRST_MODIFIED = 1;

    /**
     * Indicates that only the second automaton was modified.
     */
    public static final int SECOND_MODIFIED = 2;

    /**
     * Indicates that both automata were modified.
     */
    public static final int BOTH_MODIFIED = 3;

    /**
     * Reads the result of {@link
     * AutomatonPruner#pruneBoth(edu.supercom.util.auto.Automaton, edu.supercom.util.auto.Automaton, edu.supercom.util.auto.label.Synchroniser, edu.supercom.util.auto.label.Synchroniser) }
     * and indicates whether the first automaton was modified.
     *
     * @param i the result returned by <code>pruneBoth</code>.
     * @return <code>true</code> if the first automaton was modified.
     */
    public static boolean firstModified(int i) {
        return (i & 1) != 0;
    }

    /**
     * Reads the result of {@link
     * AutomatonPruner#pruneBoth(edu.supercom.util.auto.Automaton, edu.supercom.util.auto.Automaton, edu.supercom.util.auto.label.Synchroniser, edu.supercom.util.auto.label.Synchroniser) }
     * and indicates whether the second automaton was modified.
     *
     * @param i the result returned by <code>pruneBoth</code>.
     * @return <code>true</code> if the second automaton was modified.
     */
    public static boolean secondModified(int i) {
        return (i > 1);
    }

    @Override
    public <S1,T1,S2,T2,S,T,SP1,SP2,TP1,TP2> int pruneBoth(
            Automaton<S1,T1> a1, Automaton<S2,T2> a2,
            Synchroniser<S1,S2,S,SP1,SP2> ss, Synchroniser<T1,T2,T,TP1,TP2> st) {
        boolean [] result = new boolean[2];

        Synchroniser<S2,S1,S,SP2,SP1> antiSS = new SynchroniserInverter<S2,S1,S,SP2,SP1>(ss);
        Synchroniser<T2,T1,T,TP2,TP1> antiST = new SynchroniserInverter<T2,T1,T,TP2,TP1>(st);
        result[0] = pruneFirst(a1, a2, ss, st);
        result[1] = pruneFirst(a2, a1, antiSS, antiST);

        boolean modified = result[1];
        boolean first = true; // Indicates which automaton will be modified. 
        while (modified) {
            if (first) {
                modified = pruneFirst(a1, a2, ss, st);
            } else {
                modified = pruneFirst(a2, a1, antiSS, antiST);
            }
            first = !first;
        }

        return (result[0]? 1 : 0) + (result[1]? 2 : 0);
    }
}
