package diag.reiter2;

import diag.LiteralAssigner;
import edu.supercom.util.Options;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import java.io.File;
import java.util.Collection;
import lang.YAMLDEvent;

/**
 * A <code>AbstractTester</code>, i.e., 
 */
public abstract class AbstractTester<X, H extends Hypothesis, HS extends HypothesisSpace<H>> implements Tester<H> {
  
    /**
     * The variable assigner.  
     */
    protected final VariableAssigner _varass;
    
    protected final String _modAndObsCNF;
    
    public AbstractTester(VariableLoader loader, DiagnosisProblem<H,HS> prob, 
            int firstTimeStep, int lastTimeStep, Collection<YAMLDEvent> faults) throws DiagnosisIOException
    {
//        _loader = loader;
//        _fact = fact;
//        _cnfs = new ArrayList<String>();
//        _trans = trans;
//        _prob = prob;
//        _firstTimeStep = firstTimeStep;
//        _lastTimeStep = lastTimeStep;
//        _propertiesGenerated = new HashSet<Property<H>>();
        
        { // The SAT encoding of MOD and OBS
            final Pair<File,LiteralAssigner> pair = 
                    Util.generateModAndObs(loader, new Options(), 
                    prob.getNetwork(), prob.getInitialState(), 
                    firstTimeStep, lastTimeStep, prob.getAlarmLog(), faults);
            final File file = pair.first();
            _modAndObsCNF = file.getAbsolutePath();
            _varass = pair.second();
        }
    }
    
}
