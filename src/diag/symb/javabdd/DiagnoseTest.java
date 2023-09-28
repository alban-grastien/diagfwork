package diag.symb.javabdd;

import diag.symb.SetOfStates;
import diag.symb.SetOfTransitions;
import diag.symb.SymbDiagnose;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import lang.ExplicitState;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDGenericVar;
import lang.YAMLDValue;
import net.sf.javabdd.BDD;
import net.sf.javabdd.JDDFactory;
import util.AlarmLog;
import util.ImmutableAlarmLog;

/**
 * A main file.
 */
public class DiagnoseTest {
    
    public static void main(String [] args) throws Exception {
        final String modfile = "y.mmld";
        final String obsfile = "y.obs";
        
        final Network net = Network.readNetwork(modfile);
        final AlarmLog log = ImmutableAlarmLog.readLogFile(net, obsfile);
        
        final JavaBDDFramework2 frame = new JavaBDDFramework2(JDDFactory.init(0, 100));
        
        
        frame.transitions(net);
        
        final JavaBDDSetOfStates sos = SymbDiagnose.diagnose(frame, net, log, MMLDlightParser.st);
        
        System.out.println("Belief state: ");
        System.out.println(sos);
        System.out.println("End.");
    }
    
    private static void printVariables(Network net, JavaBDDFramework2 frame, PrintStream out) {
        
        for (final YAMLDComponent comp: net.getComponents()) {
            
            for (final YAMLDGenericVar var: comp.vars()) {
                for (final YAMLDValue val: var.domain()) {
                    final Map<YAMLDGenericVar,YAMLDValue> map = new HashMap<YAMLDGenericVar, YAMLDValue>();
                    map.put(var, val);
                    final State state = new ExplicitState(net, map);
                    final SetOfStates sos = frame.state(state);
                    out.print("Variable " + var);
                    out.print("; value " + val);
                    out.println("; " + sos);
                }
            }
            
        }
        
    }
    
    private static void printEvents(Network net, JavaBDDFramework2 frame, PrintStream out) {
        for (final YAMLDComponent comp: net.getComponents()) {
            
            for (final YAMLDEvent ev: comp.events()) {
                final SetOfTransitions sot = frame.eventOccurred(ev);
                out.print("Event " + ev);
                out.println("; " + sot);
            }
        }
    }
    
}
