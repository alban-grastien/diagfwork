package edu.supercom.util.auto.op;

import edu.supercom.util.auto.ElaborateAutomaton;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;

/**
 * A <code>_ElaborateEpsilonReductor</code>, i.e., 
 */
public class _ElaborateEpsilonReductor {
    
    public static void main(String [] args) {
        final Automaton<Integer,String> auto = new ElaborateAutomaton<Integer, String>("");
        
        final State s0 = auto.newState(0);
        final State s1 = auto.newState(1);
        final State s2 = auto.newState(2);
        final State s3 = auto.newState(3);
        final State s4 = auto.newState(4);
        final State s5 = auto.newState(5);
        final State s6 = auto.newState(6);
        final State s7 = auto.newState(7);
        
        auto.setInitial(s0);
        auto.setFinal(s7);
        
        auto.newTransition(s0, s1, "");
        auto.newTransition(s0, s3, "b");
        auto.newTransition(s1, s2, "a");
        auto.newTransition(s1, s3, "");
        auto.newTransition(s2, s4, "d");
        auto.newTransition(s3, s0, "");
        auto.newTransition(s3, s2, "c");
        auto.newTransition(s4, s5, "");
        auto.newTransition(s5, s6, "");
        auto.newTransition(s6, s7, "e");
        auto.newTransition(s7, s5, "");
        
        System.out.println(auto.toDot("Automaton"));
        
        final EpsilonReductor red = new ElaborateEpsilonReductor();
        red.runReduction(auto);
        System.out.println(auto.toDot("Reduced automaton"));
    }
}
