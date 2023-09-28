package diag.reiter2;

import util.Scenario;

/**
 * A <code>TestResult</code>, i.e., a test result, 
 * is the result of a test.  
 * The result can either be that the test is successful 
 * (in which case a scenario is generated) 
 * or that the test is unsuccessful 
 * (in which case a conflict is generated).  
 * 
 * @param <H> the type of hypothesis the test result is defined on.  
 */
public class TestResult<H extends Hypothesis> {    
    /**
     * Is the test successful?
     */
    private final boolean _success;
    
    /**
     * TODO: The scenario (if successful).  
     */
    private final Scenario _sce;
    
    /**
     * TODO: The hypothesis (if successful).  
     */
    
    /**
     * The conflict (if not successful).  
     */
    private final Conflict<H> _conflict;
    
    private TestResult(Conflict<H> conflict) {
        _success = false;
        _conflict = conflict;
        _sce = null;
    }
    
    private TestResult(Scenario sce) {
        _success = true;
        _conflict = null;
        _sce = sce;
    }
    
    public static <H extends Hypothesis> TestResult<H> failedTest(Conflict<H> con) {
        return new TestResult<H>(con);
    }
    
    public static <H extends Hypothesis> TestResult<H> successfulTest(Scenario sce) {
        return new TestResult<H>(sce);
    }
    
    public boolean isSuccessful() {
        return _success;
    }
    
    public Scenario getScenario() {
        return _sce;
    }
    
    public Conflict<H> getConflict() {
        if (isSuccessful()) {
            throw new IllegalStateException();
        }
        
        return _conflict;
    }

    @Override
    public String toString() {
        if (isSuccessful()) {
            return "Successful test";
        } else {
            return "Unsuccessful test";
        }
    }
}
