/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * A simple implementation of a suffix checker.  
 *
 * @author Alban Grastien
 */
public class SimpleSuffixChecker implements SuffixChecker {

    @Override
    public <S, T> boolean suffixContains(Automaton<S, T> auto, Set<T> labels) {
        Stack<State> open = new Stack<State>();
        Set<State> close = new HashSet<State>();

        { // Enumerate the open states.  
            for (State state: auto.states()) {
                for (Transition trans: auto.outgoingTrans(state)) {
                    T tl = auto.transLabel(trans);
                    if (labels.contains(tl)) {
                        open.push(state);
                        close.add(state);
                        continue;
                    }
                }
            }
        }

        // Backward pass.  
        while (!open.isEmpty()) {
            State state = open.pop();
            for (Transition trans: auto.incomingTrans(state)) {
                State newState = trans.getOrigin();
                if (close.add(newState)) {
                    open.add(state);
                }
            }
        }

        if (close.size() == auto.states().size()) {
            return false;
        }
        
        { // Removes the states.
            for (State state: new ArrayList<State>(auto.states())) {
                if (close.contains(state)) {
                    continue;
                }
                auto.removeAll(state);
            }
        }
        return true;
    }

}
