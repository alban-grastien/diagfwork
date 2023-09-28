package diag.auto;

import diag.reiter.Test;
import edu.supercom.util.Options;
import java.util.HashSet;
import java.util.Set;
import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDEvent;
import util.AlarmLog;

/**
 * The main class for a diagnoser.  
 */
public class Diag {
    
    public static void main(String [] args) throws Exception {
        final Options opt = new Options(args);
        
        final String networkAddress = "YAMLDSim/data/dx07/dx07.mmld";
        final Network net = diag.Util.readNetwork(opt, networkAddress);
        
        final String logAddress = "YAMLDSim/data/dx07/obs1";
        final AlarmLog log = diag.Util.readAlarms(net, opt, logAddress);
        
        final JTDiagnoser dser = new JTDiagnoser(net);

        final Diagnosis diag = new Diagnosis(net, MMLDlightParser.st, log);
        
        final Alphabet importantEvents;
        {
            final Set<SynchronousEvent> events = new HashSet<SynchronousEvent>();
            for (final YAMLDEvent ev: Test.readFaults(net, opt)) {
                final SynchronousEvent s = new SynchronousEvent(ev, ev, false);
                events.add(s);
            }
            importantEvents = new Alphabet(events);
        }
        
        //dser.updateDiagnosis(diag);        
        
        System.out.println(dser.diagnosisLanguage(diag, importantEvents));
    }
}
