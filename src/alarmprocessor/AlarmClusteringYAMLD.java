/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import util.Scenario;
import java.util.Map;

/**
 * This class implements the abstract class {@link AlarmProcessor},
 * which means that it implements one particular alarm processing technique.
 * Like all other alarm processing classes in this package (i.e., alarmprocessor),
 * the input that is getting processed is a {@link Scenario} object.
 * The processing implemented in this class partitions all events contained in 
 * a scenario into clusters of related events.
 * See classes {@link AlarmClustersYAMLD} and {@link RuleClusterYAMLD}
 * for details on the partitioning process. 
 * @author Abotea
 */
public class AlarmClusteringYAMLD extends AlarmClusteringAbstract {

    /**
     * Constructor
     */
    public AlarmClusteringYAMLD() {
        clusters = new AlarmClustersYAMLD();
    }    

    /**
     * Partitions a scenario into subsets of events.
     * Then, the alarms in one subset will belong to one record in
     * the output, which is a clustering of the entire set of alarms (observations)
     * in the scenario.
     * @param sce
     * @param output
     */
    protected AlarmClustersAbstract alarmClustering(Scenario sce) {
        AlarmClustersAbstract clusters = new AlarmClustersYAMLD(sce);
        for (int i = 0; i < sce.nbTrans(); i++) {
        	clusters.asYAMLDClusters().addTransition(sce.getState(i), sce.getTrans(i));
        }
        return clusters;
    }

}
