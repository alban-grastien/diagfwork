/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import lang.YAMLDEvent;
import util.Scenario;
import java.util.Collection;
import java.util.Map;

/**
 * This class implements an alarm filtering technique that extracts
 * the incident-independent alarms from a scenario. These are the alarms
 * that are not caused by a catastrophic event. This feature is useful
 * when a catastrophic incident generates lots of alarms and the unrelated
 * alarms get lost among the many incident-related alarms.
 * This filtering technique has been suggested by TransGrid staff.  
 * @author Abotea
 */
public class ExtractIncidentIndependentAlarms implements AlarmProcessor {

    /**
     * Even though it's not strictly required, store the result
     * as a collection of clusters, rather than a flat collection of alarms.
     * This way, we add more structure to the result and also reuse existing
     * functionality for alarm clustering.
     */
    private AlarmClustersAbstract clusters;
    private AlarmClusteringAbstract ac;
    
    public ExtractIncidentIndependentAlarms(AlarmClusteringAbstract a) {
    	ac = a;
    }

    /**
     * Perform the processing required to extract the incident-independent alarms.
     * @param the scenario where the information is extracted from
     * @param a map that provides a collection of catastrophic events under the
     * "faults" string label
     */
    public void process(Scenario sce, Map<String, Object> extra_info) {
        Collection<YAMLDEvent> faults =
                (Collection<YAMLDEvent>) extra_info.get("faults");
        // first do clustering
        ac.process(sce, extra_info);
        clusters = (AlarmClustersAbstract) ac.getProcessingResults();
        // then, filter out clusters that contain fault events
        clusters.filterOutIncidentClusters(faults);
    }

    public SummaryFromScenario getProcessingResults() {
        return clusters;
    }

}
