/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto;

/**
 * The abstract automaton class implements many redundant methods.  
 *
 * @author Alban Grastien
 * @since 2.0
 * @version 2.0
 */
public abstract class AbstractAutomaton<SL,TL> implements Automaton<SL,TL> {

    @Override
    public boolean isInitial(State state) {
        return initialStates().contains(state);
    }

    @Override
    public boolean isFinal(State state) {
        return finalStates().contains(state);
    }
}
