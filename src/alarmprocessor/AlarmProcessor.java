/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import util.Scenario;
import java.util.Map;

/**
 * Common interface for alarm processing techniques, including
 * root cause analysis, clustering, and so on.
 * Currently the interface assumes that scenarios are processed one by one
 * (i.e., one scenario at a time). However, there are cases when a group of
 * scenarios needs to be processed as one entity. An example is the case
 * when there are a few equally likely scenarios. So, in the future,
 * the ability to process several scenarios at a time should be added.
 * @author Abotea
 */
public interface AlarmProcessor {

    /**
     * Takes a scenario as input and extracts summary information
     * @param sce
     */
    public void process(Scenario sce, Map<String, Object> extra_info);

    /**
     * Provides the results of processing.
     * Should be invoked after {@link process}.
     * @return the results of processing
     */
    public SummaryFromScenario getProcessingResults();
}
