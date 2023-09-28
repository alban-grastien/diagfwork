package diag.symb.javabdd;

import diag.symb.SetOfStates;
import diag.symb.SetOfTransitions;
import diag.symb.SymbolicFramework;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDGenericVar;
import net.sf.javabdd.CUDDFactory;
import net.sf.javabdd.JDDFactory;

/**
 * A <code>Test</code>, i.e., 
 */
public class Test {
    
    public static void main(String [] args) throws Exception {
        final String file = "y.mmld";
        
        final Network net = Network.readNetwork(file);
        
        //final JavaBDDFramework2 frame = new JavaBDDFramework2(JDDFactory.init(0, 100));
        final JavaBDDFramework2 frame = new JavaBDDFramework2(CUDDFactory.init(100000, 100000000));
        testXYZ(frame,net);
        
        
        System.out.println("End.");
    }
    
    public static <SOS extends SetOfStates, SOT extends SetOfTransitions> 
            void testABC(SymbolicFramework<SOS,SOT> frame, Network net) {
        final SOT sot = frame.transitions(net);
        
        //System.err.println("Transitions: " + sot);
        
        final YAMLDComponent comp = net.getComponent("c");
        
        final YAMLDGenericVar var_a = comp.getVariable("A");
        final YAMLDGenericVar var_b = comp.getVariable("B");
        final YAMLDGenericVar var_c = comp.getVariable("C");
        
        final YAMLDEvent event_b = comp.getEvent("b");
        final YAMLDEvent event_c = comp.getEvent("c");
        final YAMLDEvent event_e = comp.getEvent("e");
        final YAMLDEvent event_f = comp.getEvent("f");
        final YAMLDEvent event_r = comp.getEvent("r");
        
        
        final SOS initialState;
        {
            final State state = MMLDlightParser.st;
            initialState = frame.state(state);
        }
        
//        System.err.println("Initial state: " + initialState);
//        System.err.println("Empty state: " + frame.emptySetOfStates());
//        System.err.println("Complete state: " + frame.setOfAllStates());

        final SOT sot_b = frame.eventOccurred(event_b);
        final SOT sot_c = frame.eventOccurred(event_c);
        final SOT sot_e = frame.eventOccurred(event_e);
        final SOT sot_f = frame.eventOccurred(event_f);
        final SOT sot_r = frame.eventOccurred(event_r);
        
        SOS sos = initialState;
        
        System.out.println("From state " + sos + " --> ");
        sos = transition(frame, sos, sot, sot_c);
        sos = transition(frame, sos, sot, sot_b);
        sos = transition(frame, sos, sot, sot_e);
        //sos = transition(frame, sos, sot, sot_c);
        //sos = transition(frame, sos, sot, sot_b);
        //sos = transition(frame, sos, sot, sot_r);
        System.out.println("--> Leads to state " + sos);
    }
    
    public static <SOS extends SetOfStates, SOT extends SetOfTransitions> 
            void testXYZ(SymbolicFramework<SOS,SOT> frame, Network net) {
        
        //System.out.println("TEST CALLING TRANSITIONS");
        final SOT transitions = frame.transitions(net);
        //System.out.println("END TEST CALLING TRANSITIONS");
        
        final YAMLDComponent comp_x = net.getComponent("x");
        final YAMLDComponent comp_y = net.getComponent("y");
        final YAMLDComponent comp_z = net.getComponent("z");
        
        final YAMLDEvent event_f1 = comp_x.getEvent("f1");
        final YAMLDEvent event_c1 = comp_x.getEvent("c1");
        
        
        final SOT sot_f1 = frame.eventOccurred(event_f1);
        final SOT sot_c1 = frame.eventOccurred(event_c1);
        
        final SOS initialState;
        {
            final State state = MMLDlightParser.st;
            initialState = frame.state(state);
        }
                
        SOS sos = initialState;
        System.out.println("Initial state" + initialState);
        
        sos = transition(frame, sos, transitions, sot_c1);
        System.out.println("Leads to -> " + sos);
    }
    
    public static <SOS extends SetOfStates, SOT extends SetOfTransitions> 
            SOS transition(SymbolicFramework<SOS,SOT> frame, SOS sos, SOT... sots) {
        SOT currentTransitions = frame.transitionFromState(sos);
        for (final SOT sot: sots) {
            currentTransitions = frame.intersection(currentTransitions, sot);
        }
        
        return frame.nextStates(currentTransitions);
    }
}
