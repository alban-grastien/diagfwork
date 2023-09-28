package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ElaborateAutomaton;
import edu.supercom.util.auto.State;

/**
 * A <code>_SimpleMinimizer</code>, i.e., 
 */
public class _SimpleMinimizer {

    public static void main(String [] args) {
        final Automaton<Integer,String> auto = new ElaborateAutomaton<Integer, String>("");
        
        final State s0 = auto.newState(0);
        final State s1 = auto.newState(1);
        
        auto.setInitial(s0);
        
        auto.setFinal(s0);
        auto.setFinal(s1);
        
        auto.newTransition(s0, s1, "a");
        auto.newTransition(s1, s1, "a");
        
        System.out.println(auto.toDot("Automaton"));
        
        final AutomatonMinimizer min = new SimpleMinimizer();
        min.doMinimize(auto);
        
        System.out.println(auto.toDot("Minimized automaton"));
    }
    
}
