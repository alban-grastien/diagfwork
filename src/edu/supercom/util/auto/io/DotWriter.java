/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util.auto.io;

import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The dot writer is an implementation of the automaton writer that returns
 * a representation in dot (GraphViz) format.  
 *
 * @author Alban Grastien
 */
public class DotWriter implements AutomatonWriter {

    @Override
    public String toString(ReadableAutomaton auto) {
        StringBuffer result = new StringBuffer();
        result.append("digraph G { \n");

        // the states
        int nbStates = 0;
        Map<State, Integer> stateNum = new HashMap<State, Integer>();
        {
            final Collection<? extends State> states = auto.states();
            for (final State state : states) {
                final int num = nbStates++;
                stateNum.put(state, num);
                final String label = auto.stateLabel(state).toString();
                result.append("  ").append(num).append(" [label=\"").append(label).append("\"");
                if (auto.isFinal(state)) {
                    if (auto.isInitial(state)) {
                        result.append(" color=blue");
                    } else {
                        result.append(" color=red");
                    }
                } else if (auto.isInitial(state)) {
                    result.append(" color=green");
                }
                result.append("]\n");
            }
        }

        result.append("\n");

        {
            final Collection<? extends Transition> transes = auto.transitions();
            for (final Transition trans : transes) {
                result.append("  ").append(stateNum.get(trans.getOrigin())).
                        append(" -> ").append(stateNum.get(trans.getTarget())).
                        append(" [label=\"").append(auto.transLabel(trans)).append("\"]\n");
            }
        }

        result.append("}");
        return result.toString();
    }
}
