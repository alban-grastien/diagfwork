/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.label.Synchroniser;

/**
 * An <b>automaton pruner</b> is an object that is able to prune (i.e. remove
 * some transitions and states) of two automata that must be synchronised.  
 *
 * @author Alban Grastien
 */
public interface AutomatonPruner {

    /**
     * Prunes the first specified automaton that should be synchronised with the
     * second automaton.
     *
     * @param a1 the first automaton.
     * @param a2 the second automaton.
     * @param ss the state synchroniser.
     * @param st the transition synchroniser.
     * @param <S1> the class of the first automaton state label.
     * @param <S2> the class of the second automaton state label.
     * @param <T1> the class of the first automaton transition label.
     * @param <T2> the class of the second automaton transition label.
     * @param <S>  the class of the synchronisation state label.
     * @param <T>  the class of the synchronisation transition label.
     * @param <SP1> the class of the projection of the first automaton state label.
     * @param <SP2> the class of the projection of the second automaton state label.
     * @param <TP1> the class of the projection of the first automaton transition label.
     * @param <TP2> the class of the projection of the second automaton transition label.
     * @return <code>true</code> if the automaton was modified.
     */
    public <S1,T1,S2,T2,S,T,SP1,SP2,TP1,TP2> boolean pruneFirst(
            Automaton<S1,T1> a1, Automaton<S2,T2> a2,
            Synchroniser<S1,S2,S,SP1,SP2> ss, Synchroniser<T1,T2,T,TP1,TP2> st);

    /**
     * Prunes the both specfied automata that should be synchronised with each
     * other.  
     *
     * @param a1 the first automaton.
     * @param a2 the second automaton.
     * @param ss the state synchroniser.
     * @param st the transition synchroniser.
     * @param <S1> the class of the first automaton state label.
     * @param <S2> the class of the second automaton state label.
     * @param <T1> the class of the first automaton transition label.
     * @param <T2> the class of the second automaton transition label.
     * @param <S>  the class of the synchronisation state label.
     * @param <T>  the class of the synchronisation transition label.
     * @param <SP1> the class of the projection of the first automaton state label.
     * @param <SP2> the class of the projection of the second automaton state label.
     * @param <TP1> the class of the projection of the first automaton transition label.
     * @param <TP2> the class of the projection of the second automaton transition label.
     * @return <ul>
     * <li>{@link AbstractPruner#NO_MODIFICATION} if no automaton was modified,</li>
     * <li>{@link AbstractPruner#FIRST_MODIFIED} if the first automaton was modified,</li>
     * <li>{@link AbstractPruner#SECOND_MODIFIED} if the second automaton was modified,</li>
     * <li>{@link AbstractPruner#BOTH_MODIFIED} if both automata were modified.
     */
    public <S1,T1,S2,T2,S,T,SP1,SP2,TP1,TP2> int pruneBoth(
            Automaton<S1,T1> a1, Automaton<S2,T2> a2,
            Synchroniser<S1,S2,S,SP1,SP2> ss, Synchroniser<T1,T2,T,TP1,TP2> st);
}
