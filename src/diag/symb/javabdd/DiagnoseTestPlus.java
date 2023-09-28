package diag.symb.javabdd;

import diag.symb.SetOfStates;
import diag.symb.SetOfTransitions;
import diag.symb.SymbDiagnose;
import diag.symb.SymbolicDiagnosis;
import edu.supercom.util.Options;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lang.ExplicitState;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDGenericVar;
import lang.YAMLDValue;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BuDDyFactory;
import net.sf.javabdd.CUDDFactory;
import net.sf.javabdd.JDDFactory;
import net.sf.javabdd.JFactory;
import util.AlarmLog;
import util.ImmutableAlarmLog;

/**
 * A main file.
 */
public class DiagnoseTestPlus {
    
    public static void main(String [] args) throws Exception {
        
        final Options opt = new Options(args);
        
        final String benchname;
        {
            final String tmp = opt.getOption(true, false, "bench");
            if (tmp != null) {
                benchname = tmp;
            } else {
                benchname = "worker9922";
            }
        }
        
        final String folder;
        {
            final String tmp = opt.getOption(true, false, "folder", "benchmarkfolder");
            if (tmp != null) {
                folder = tmp;
            } else {
                folder = "./";
                System.out.println("Using default folder: " + folder);
            }
        }
        
        final String modfile   = folder + benchname + ".mmld-ground";
        final String obsfile   = folder + benchname + ".obs";
        final String alarmfile   = folder + benchname + ".alarms";
        final String faultfile = folder + benchname + ".faults";
        
        final Network net = Network.readNetwork(modfile);
	AlarmLog log = null;
	log = ImmutableAlarmLog.readLogFile(net, obsfile);
	if (log == null) {
	    log = ImmutableAlarmLog.readLogFile(net, alarmfile);
	}
	    
        final List<YAMLDEvent> faults = net.readEvents(faultfile);
        
        final BDDFactory fact;
        
        final int firstParam;
        {
            final String param = opt.getOption("first");
            if (param == null) {
                firstParam = 3000000;
            } else {
                firstParam = Integer.parseInt(param);
            }
        }
        
        final int secondParam;
        {
            final String param = opt.getOption(true,false,"second");
            if (param == null) {
                secondParam = 3000000;
            } else {
                secondParam = Integer.parseInt(param);
            }
        }

        {
            String factName = opt.getOption(false, false, "fact");
            if (factName == null) {
                //factName = "JDD";
                factName = "BUDDY";
                System.out.println("Using default factory: " + factName);
            }
            if (factName.equals("JDD")) {
                fact = JDDFactory.init(firstParam, secondParam);
            } else 
            if (factName.equals("BUDDY")) {
                fact = BuDDyFactory.init(firstParam, secondParam);
            } else 
            if (factName.equals("CUDD")) {
                fact = CUDDFactory.init(firstParam, secondParam);
            } else 
            if (factName.equals("J")) {
                fact = JFactory.init(firstParam, secondParam);
            } else {
                throw new IllegalStateException("Unknown factory: "
                        + factName);
            }
        }
        
        final JavaBDDFramework2 frame = new JavaBDDFramework2(fact);
        
        // Makes sure the BDD variables are defined in a correct order.
        {
            final Map<YAMLDComponent,List<YAMLDEvent>> faultEventsOfComponents = 
                    new HashMap<YAMLDComponent, List<YAMLDEvent>>();
            for (final YAMLDComponent comp: net.getComponents()) {
                faultEventsOfComponents.put(comp, new ArrayList<YAMLDEvent>());
            }
            for (final YAMLDEvent event: faults) {
                // System.out.println("Event -> " + event);
                faultEventsOfComponents.get(event.getComponent()).add(event);
            }
            
            for (final YAMLDComponent comp: net.getComponents()) {
                frame.createVariables(comp);
                for (final YAMLDEvent event: faultEventsOfComponents.get(comp)) {
                    frame.eventOccuredBeforeCurrentState(event);
                }
            }
        }
        
        //frame.transitions(net);
        
        final SymbolicDiagnosis diag = SymbDiagnose.diagnose(frame, net, log, faults, MMLDlightParser.st);
        
        //printVariables(net, frame, System.out);
        //printEvents(net, frame, System.out);
        
        System.out.println("Diagnosis: ");
        System.out.println(diag.candidates().size());
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
