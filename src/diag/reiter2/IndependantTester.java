package diag.reiter2;

import diag.reiter2.log.Test;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.BufferedPrintClauseStream;
import edu.supercom.util.sat.CNF;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.TimedSemanticFactory;
import edu.supercom.util.sat.VariableLoader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lang.YAMLDEvent;
import util.Scenario;

/**
 * A <code>IndependantTester</code>, i.e., 
 */
public class IndependantTester<X, H extends Hypothesis, HS extends HypothesisSpace<H>> 
extends AbstractTester<X,H,HS> implements Tester<H> {
    
    /**
     * The first time step.  
     */
    private final int _firstTimeStep;
    
    /**
     * The last time step.  
     */
    private final int _lastTimeStep;
    
    /**
     * The variable loader.  
     */
    private final VariableLoader _loader;
    
    /**
     * The factory.  
     */
    private final TimedSemanticFactory<X> _fact;
    
    final SATTranslator<X, H, HS> _trans;
    
    /**
     * The diagnosis problem.  
     */
    private final DiagnosisProblem<H,HS> _prob;
    
    /**
     * The list of properties that were encoded.  
     */
    private final Set<Property<H>> _propertiesGenerated;
    
    public IndependantTester(VariableLoader loader, TimedSemanticFactory<X> fact, 
            SATTranslator<X, H, HS> trans, 
            DiagnosisProblem<H,HS> prob, 
            int firstTimeStep, int lastTimeStep, Collection<YAMLDEvent> faults) throws DiagnosisIOException
    {
        super(loader, prob, firstTimeStep, lastTimeStep, faults);
        _loader = loader;
        _fact = fact;
        _trans = trans;
        _prob = prob;
        _firstTimeStep = firstTimeStep;
        _lastTimeStep = lastTimeStep;
        _propertiesGenerated = new HashSet<Property<H>>();
        
//        { // The SAT encoding of MOD and OBS
//            final Pair<File,LiteralAssigner> pair = Util.generateModAndObs(loader, new Options(), prob.getNetwork(), prob.getInitialState(), firstTimeStep, lastTimeStep, prob.getAlarmLog(), faults);
//            final File file = pair.first();
//            _cnfs.add(file.getAbsolutePath());
//            _varass = pair.second();
//        }
    }

    @Override
    public TestResult<H> test(Collection<Property<H>> testedProperties) throws DiagnosisIOException {
        Diag.getLogger().log(new Test(testedProperties));
        
        final List<Integer> assumptions = new ArrayList<Integer>();
        final HS space = _prob.getHypothesisSpace();
        
        Pair<List<Integer>,List<Boolean>> satResult;
        try {
            final File propertiesFile = Util.generateFile(false);
            final ClauseStream out;
            {
                ClauseStream tmp = null;
                try {
                    tmp = new BufferedPrintClauseStream(propertiesFile);
                } catch (IOException e) {
                    throw new DiagnosisIOException(e.getMessage());
                }
                out = tmp;
            }
            for (final Property<H> prop: testedProperties) {
                final int var = _trans.getSATVariable(_loader, _firstTimeStep, _lastTimeStep, prop, space);
                assumptions.add(var);
            }
            _trans.createAllSATClauses(out, _varass, _fact, _firstTimeStep, _lastTimeStep, testedProperties);
            out.close();
        
        
            {
                // TODO: Make it more general.
		final String solver = SatSolver.solverAddress();
                final String assFile = Util.generateFile(true).getAbsolutePath();
                try {
                    final PrintStream assPrintStream = new PrintStream(assFile);
                    for (final int ass: assumptions) {
                        assPrintStream.println(ass);
                    }
                    assPrintStream.close();
                } catch (IOException e) {
                    throw new DiagnosisIOException(e.getMessage());
                }
                
                satResult = CNF.solveWithAssumption2(solver, assFile, _modAndObsCNF, propertiesFile.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new DiagnosisIOException(e.getMessage());
        } catch (InterruptedException e) {
            throw new DiagnosisIOException(e.getMessage());
        }
        
        if (satResult.first() == null) {
            final List<Boolean> satAssignment = satResult.second();
            final Scenario sce = Util.getScenario(_varass, satAssignment, _prob.getNetwork(), _firstTimeStep, _lastTimeStep);
            
            return TestResult.successfulTest(sce);
        }
        
        final Conflict<H> con;
        {
            final List<Integer> list = satResult.first();
            final int[] assArray = new int[list.size()];
            for (int i=0 ; i<assArray.length ; i++) {
                assArray[i] = list.get(i);
            }
            con = _trans.SATConflictToDiagnosisConflict(assArray);
        }
        
        return TestResult.failedTest(con);
    }
    
}
