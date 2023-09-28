package diag.reiter2.log;

import util.Scenario;

/**
 * A <code>ScenarioFinding</code>, i.e., a scenario finding, 
 * is a log that indicates that the specified scenario has been found.  
 */
public class ScenarioFinding implements Log {
    
    /**
     * The scenario that has been found.  
     */
    private final Scenario _scenario;
    
    /**
     * Creates a log that indicates that the specified scenario 
     * has been found.  
     * 
     * @param s the scenario that has been found.  
     */
    public ScenarioFinding(Scenario s) {
        _scenario = s;
    }

    @Override
    public String stringLog() {
        return "  Scenario found:\n" + _scenario.toFormattedString();
    }
}
