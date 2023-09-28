package diag.reiter2;

import diag.reiter2.seq.SequentialHypothesis;
import diag.reiter2.seq.SequentialHypothesisSpace;
import edu.supercom.util.Options;
import edu.supercom.util.sat.VariableLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.YAMLDEvent;
import util.AlarmLog;

/**
 * A main to test a single hypothesis.  
 */
public class Test {
    
    @SuppressWarnings("unchecked")
    public static void main(String [] args) throws DiagnosisIOException {
        final Options opt = new Options(args);
        
        Diag.initLogger(opt);
        
        final Network net = diag.Util.readNetwork(opt, null); 
        final AlarmLog log = diag.Util.readAlarms(net, opt, null);
		final Set<YAMLDEvent> faults = diag.reiter.Test.readFaults(net, opt);
        final State state = MMLDlightParser.st;
        
        final VariableLoader loader = new VariableLoader();
        final EventSemanticFactory fact = new EventSemanticFactory();
        final SATTranslator trans = PackageOptions.getTranslator(opt);
        final HypothesisSpace space = PackageOptions.getSpace(opt, faults);
        final DiagnosisProblem prob = new DiagnosisProblem(net, state, log, space);
        final int firstTimeStep = 0;
        final int lastTimeStep = (PackageOptions.nbUnobsPerTimeStep(opt) +1) * log.nbEntries();
        
        final Tester tester = PackageOptions
                .getTester(opt, loader, fact, trans, prob, firstTimeStep, lastTimeStep, faults);

        final Collection<Property> props = PackageOptions.getProperties(opt, space);
        
        final TestResult result = tester.test(props);
        System.out.println(result);
    }
    
}
