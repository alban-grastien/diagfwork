package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ElaborateAutomaton;
import edu.supercom.util.auto.State;

/**
 * Test class for <code>NonDeterministicMinimiser</code>.  
 */
public class _NonDeterministicMinimiser {
    
    public static void main (String [] args) {
        final Automaton<String,String> auto = new ElaborateAutomaton<String, String>("");
        
        final State s0 = auto.newState("0");
        final State s1 = auto.newState("1");
        final State s2 = auto.newState("2");
        final State s3 = auto.newState("3");
        final State s4 = auto.newState("4");
        final State s5 = auto.newState("5");
        final State s6 = auto.newState("6");
        final State s7 = auto.newState("7");
        
        auto.setInitial(s0);
        auto.setInitial(s2);
        
        auto.setFinal(s6);
        auto.setFinal(s7);
        
        auto.newTransition(s0, s4, "d");
        auto.newTransition(s0, s7, "e");
        
        auto.newTransition(s1, s4, "d");
        auto.newTransition(s1, s5, "d");
        auto.newTransition(s1, s7, "e");
        
        auto.newTransition(s2, s4, "d");
        
        auto.newTransition(s3, s6, "b");
        
        auto.newTransition(s4, s7, "b");
        auto.newTransition(s4, s7, "c");
        
        auto.newTransition(s5, s6, "c");
        auto.newTransition(s5, s7, "b");
        
        auto.newTransition(s6, s6, "a");
        
        auto.newTransition(s7, s7, "a");
        
        System.out.println(auto.toDot("Non minimized"));
        
        //final Partition partition = NonDeterministicMinimiser.computePartition(auto);
        //System.out.println(partition);
        
        final AutomatonMinimizer min = new NonDeterministicMinimiser();
        min.doMinimize(auto);
        
        System.out.println(auto.toDot("Minimized"));
    }
    
}
