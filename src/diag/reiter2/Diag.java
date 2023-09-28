package diag.reiter2;

import diag.reiter2.log.CandidateFinding;
import diag.reiter2.log.CandidateList;
import diag.reiter2.log.ClassBasedFilterLogger;
import diag.reiter2.log.ConflictFinding;
import diag.reiter2.log.HypothesisConsideration;
import diag.reiter2.log.HypothesisNotEssentiality;
import diag.reiter2.log.HypothesisSubsumption;
import diag.reiter2.log.Logger;
import diag.reiter2.log.PrintLogger;
import diag.reiter2.log.Test;
import edu.supercom.util.Options;
import edu.supercom.util.sat.VariableLoader;
import java.util.Collection;
import java.util.Set;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.YAMLDEvent;
import util.AlarmLog;

/**
 * A main method for computing diagnoses.  
 */
public class Diag {
    
    private static Logger _logger;
    
    public static void initLogger(Options opt) {
        final Logger internal = new PrintLogger(System.out);
        final ClassBasedFilterLogger logger = new ClassBasedFilterLogger(internal);
        
        logger.add(CandidateList.class);
        logger.add(HypothesisConsideration.class);
        logger.add(HypothesisSubsumption.class);
        logger.add(HypothesisNotEssentiality.class);
        logger.add(CandidateFinding.class);
        logger.add(ConflictFinding.class);
        // logger.add(ScenarioFinding.class);
        logger.add(Test.class);
        logger.add(diag.reiter2.log.State.class);
        
        _logger = logger;
    }
    
    public static Logger getLogger() {
        return _logger;
    }
    
    @SuppressWarnings("unchecked")
    public static void main(String [] args) throws DiagnosisIOException {
        final Options opt = new Options(args);
        
        initLogger(opt);

	SatSolver.setSolverAddress(opt.getOption(true,true,"satsolver"));
	
        final Network net = diag.reiter.Test.readNetwork(opt); // TODO: move the method position
        final AlarmLog log = diag.reiter.Test.readAlarms(net, opt);
		final Set<YAMLDEvent> faults = diag.reiter.Test.readFaults(net, opt);
        final State state = MMLDlightParser.st;
        
        final VariableLoader loader = new VariableLoader();
        final EventSemanticFactory fact = new EventSemanticFactory();
        final SATTranslator trans = PackageOptions.getTranslator(opt);
        final HypothesisSpace space = PackageOptions.getSpace(opt, faults);
        final DiagnosisProblem prob = new DiagnosisProblem(net, state, log, space);
        final int firstTimeStep = 0;
        //final int lastTimeStep = (PackageOptions.nbUnobsPerTimeStep(opt) +1) * log.nbEntries();
        final int lastTimeStep = (PackageOptions.nbUnobsPerTimeStep(opt) +1) * log.nbObs();
        // TODO: Should be nbObs + nbEntries? 
        
        final Tester tester = PackageOptions
                .getTester(opt, loader, fact, trans, prob, firstTimeStep, lastTimeStep, faults);
        final TightPropertyComputer tpc = PackageOptions.getTightPropertyComputer(opt);
        
        final Diagnoser dser = 
                new Diagnoser(space, tester);
        
        final Collection result = dser.diagnose(tpc);
    }
    
}
