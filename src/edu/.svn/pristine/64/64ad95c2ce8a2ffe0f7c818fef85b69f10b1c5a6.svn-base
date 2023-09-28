/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import java.util.Set;

/**
 * A suffix checker is an object that is able to remove states of an automaton
 * that do not satisfy a certain suffix property.  
 *
 * @author Alban Grastien
 */
public interface SuffixChecker {

    /**
     * Keeps the states of the specified automaton that can be followed by a
     * label in the specified set.
     *
     * @param <S> the type of state label.
     * @param <T> the type of transition label.
     * @param auto the automaton on which the operation is defined.
     * @param labels the set of transition labels that should follow the states
     * of the automaton.  This set only is only required to implement the {@link
     * Set#contains(java.lang.Object)} method.
     * @return <code>true</code> if the automaton was modified by the method,
     * <code>false</code> otherwise.  
     */
    public <S,T> boolean suffixContains(Automaton<S,T> auto, Set<T> labels);

}
