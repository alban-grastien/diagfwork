/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import java.util.List;

import lang.State;
import util.GlobalTransition;
import util.Scenario;

/**
 * A class that stores the result of alarm clustering methods. 
 * It works with class {@link AlarmClusteringMMLD}.
 * @author Adi Botea
 */
public class AlarmClustersYAMLD extends AlarmClustersAbstract {

    public AlarmClustersYAMLD() {
    	super();
    }

    public AlarmClustersYAMLD(Scenario sce) {
    	super(sce);
    }

    /**
     * Either add the rule to an existing cluster, or create a new cluster
     * and initialise it to that rule.
     * @param state
     * @param trans
     */
    public void addTransition(State state, GlobalTransition trans) {
        boolean transAddedToExistingCluster = false;
        for (RuleClusterAbstract cluster : clusters) {
            if (cluster.isComplete()) {
                continue;
            }
            if (cluster.asClusterYAMLD().shouldAddTransition(trans)) {
                cluster.asClusterYAMLD().addTransition(trans);
                transAddedToExistingCluster = true;
                break;
            }
        }
        if (!transAddedToExistingCluster) {
            RuleClusterAbstract cluster = new RuleClusterYAMLD(state);
            cluster.asClusterYAMLD().addTransition(trans);
            clusters.add(cluster);
        }
    }

	@Override
	public AlarmClustersMMLD asMMLDClusters() {
		return null;
	}

	@Override
	public AlarmClustersYAMLD asYAMLDClusters() {
		return this;
	}


}
