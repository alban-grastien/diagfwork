/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import lang.State;
import util.MMLDGlobalTransition;
import util.Scenario;

/**
 * This class implements the interface {@link AlarmProcessor},
 * which means that it implements one particular alarm processing technique.
 * Like all other alarm processing classes in this package (i.e., alarmprocessor),
 * the input that is getting processed is a {@link Scenario} object.
 * The processing implemented in this class partitions all events contained in 
 * a scenario into clusters of related events.
 * See classes {@link AlarmClustersMMLD} and {@link RuleClusterMMLD} for details
 * on the partitioning process. The difference between {@link AlarmClusteringMMLD} 
 * and {@link AlarmClusteringYAMLD} is that the latter works with an obsolete 
 * definition of transitions (i.e., {@link GlobalTransition}).
 * @author Adi Botea
 */
public class AlarmClusteringMMLD extends AlarmClusteringAbstract {

    /**
     * Constructor
     */
    public AlarmClusteringMMLD() {
        clusters = new AlarmClustersMMLD();
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
        AlarmClustersAbstract clusters = new AlarmClustersMMLD(sce);
        for (int i = 0; i < sce.nbTrans(); i++) {
        	// Currently the sat engine can create transitions that have
        	// no effect whatsoever. We ignore those here.
        	if (sce.getMMLDTrans(i).affectedComponents().size() == 0)
        		continue;
        	State s = sce.getState(i);
        	MMLDGlobalTransition t = sce.getMMLDTrans(i);
        	System.out.println("Processing transition " + t.toString());
        	clusters.asMMLDClusters().addTransition(s, t, i);
        	System.out.println("Clusters after parsing the " + i + "-th transition:\n");
        	System.out.print(clusters.toString());
        }
        return clusters;
    }

}
