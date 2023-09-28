/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import util.Scenario;
import java.util.Map;

/**
 * This class implements an alarm filtering technique that extracts
 * the so-called transient alarms from a scenario.
 * To illustrate what transient alarms are, first we need to introduce
 * nominal and transient states. Assume each component has zero or more
 * nominal states, which can informally be described as acceptable permanent
 * states. All other states are called transient. For example, a nominal
 * state of a voltage sensor would be "voltage = normal", and a transient state
 * would be "voltage = high". We say that one or several alarms are transient if
 * they indicate that at least one component is in a transient state.
 * For example, "alarm voltage high" is a (one-element) sequence of transient alarms,
 * but "alarm voltage high; alarm voltage normal" is not.
 * 
 * Pointing out transient alarms is useful because they indicate
 * an evolution in the system that didn't reach a settling point yet,
 * and thus it might be worthwhile to highlight this evolution to the operator. 
 *
 * @author Abotea
 */
public class ExtractTransientAlarms implements AlarmProcessor {

	private AlarmClusteringAbstract ac;
    private TransientAlarms data;
    
    public ExtractTransientAlarms(AlarmClusteringAbstract a) {
    	ac = a;
    }

    public void process(Scenario sce, Map<String, Object>  extra_info) {
        ac.process(sce, extra_info);
        AlarmClustersAbstract clusters = (AlarmClustersAbstract) ac.getProcessingResults();
        data = clusters.extractTransientAlarms();
    }

    public SummaryFromScenario getProcessingResults() {
        return data;
    }

}
