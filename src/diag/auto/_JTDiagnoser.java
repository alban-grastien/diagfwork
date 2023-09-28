package diag.auto;

import edu.supercom.util.Options;
import lang.MMLDlightParser;
import lang.Network;
import util.AlarmLog;
import util.ImmutableAlarmLog;

/**
 * A <code>_JTDiagnoser</code>, i.e., 
 */
public class _JTDiagnoser {
    
    public static void main(String [] args) throws Exception {
        final Options opt = new Options(args);
        
        final String networkAddress = "benchmarks/icaps14/worker1009.mmld-ground";
        final Network net = diag.Util.readNetwork(opt, networkAddress);
        
        final String logAddress = "benchmarks/icaps14/worker1009.obs";
        final AlarmLog log = diag.Util.readAlarms(net, opt, logAddress);
        
        final Diagnosis diag = new Diagnosis(net, MMLDlightParser.st, log);
        
        final JTDiagnoser dser = new JTDiagnoser(net);
        dser.updateDiagnosis(diag);
        
        System.out.println(diag);
    }
    
}
