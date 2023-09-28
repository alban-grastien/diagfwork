/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import lang.State;
import lang.YAMLDEvent;
import util.MMLDGlobalTransition;
import util.Scenario;

/**
 * A class that stores the result of alarm clustering methods. 
 * It works with class {@link AlarmClusteringMMLD}.
 * @author Adi Botea
 */
public class AlarmClustersMMLD extends AlarmClustersAbstract {

	/**
	 * Default constructor.
	 */
    public AlarmClustersMMLD() {
        super();
    }

    /**
     * Constructor that sets the {@link Scenario} data member to the object
     * given as an input argument
     * @param sce
     */
    public AlarmClustersMMLD(Scenario sce) {
        super(sce);
    }

    /**
     * Adds the transition given as a parameter to the set of clusters computed so far.
     * Possible outcomes include: 1) create a new cluster containing the transition 
     * <code>trans</code>; 2) add <code>trans</code> to one existing cluster; and 
     * 3) merge two or more existing clusters and add <code>trans</code> to the
     * resulting cluster.
     * @param state
     * @param trans
     * @param idx is the index of <code>trans</code> in the global scenario
     */
    public void addTransition(State state, MMLDGlobalTransition trans, int idx) {
        Collection<RuleClusterMMLD> relatedClusters = computeRelatedClusters(trans, idx);
        RuleClusterMMLD newCluster = mergeClusters(relatedClusters);
        newCluster.asClusterMMLD().addTransition(trans, idx);
        clusters.add(newCluster);
        clusters.removeAll(relatedClusters);
    }
    
    /**
     * Puts together the contents of all clusters given as input
     * @param relatedClusters
     * @return the resulting cluster
     */
    private RuleClusterMMLD mergeClusters(
			Collection<RuleClusterMMLD> someClusters) {
    	RuleClusterMMLD result = new RuleClusterMMLD(scenario.getState(0));
    	if (someClusters != null)
	    	for (RuleClusterMMLD cluster : someClusters) {
	    		result.includeCluster(cluster);
	    	}
		return result;
	}

    /**
     * Compute a set of clusters (among those built so far) that are related
     * to the transition given as a parameter. Intuitively, we skip clusters that
     * are independent from <code>trans</code> and return all other clusters.
     * There is a detail worth mentioning. If two independent clusters support
     * the same precondition of <code>trans</code>, each of them might pass as
     * being "not supportive" for <code>trans</code> since the other will provide
     * the needed precondition. We need to include one of those clusters in the 
     * set of clusters related to <code>trans</code> because otherwise we would leave
     * one precondition of <code>trans</code> unsupported. The implementation takes care
     * of this.
     * @param trans
     * @return
     */
	private Collection<RuleClusterMMLD> computeRelatedClusters(
			MMLDGlobalTransition trans, int index) {
		// this data member will keep track of clusters not labeled as
		// "not supportive"
		Collection<RuleClusterAbstract> cs = new Vector<RuleClusterAbstract>();
		cs.addAll(this.clusters);
		Collection<RuleClusterMMLD> result = new Vector<RuleClusterMMLD>();
		for (RuleClusterAbstract cluster : this.clusters) {
			if (clusterSupportsTrans(cs, cluster.asClusterMMLD(), trans, index)) {
				result.add(cluster.asClusterMMLD());
			} else {
				// Cluster is independent from trans;
				// therefore we remove cluster from the working collection cs.
				// This way 
				cs.remove(cluster);
			}
		}
		return result;
	}

	/**
	 * Checks whether the cluster given as input supports the
	 * transition given as input. According to this implementation,
	 * the object <code>cluster</code> supports a transition <code>trans</code> iff
     * its contained transitions are needed to satisfy the 
     * preconditions of <code>trans</code>. In other words,
     * <code>cluster</code> is the only element in <code>cs</code>
     * that supports a certain precondition of <code>trans</code>.
     * 
	 * The way we perform this check is the following: 
	 * We apply (from the initial state) all transitions corresponding
	 * to all clusters contained in <code>cs</code> except for the current cluster.
	 * If <code>trans</code> is applicable in the resulting state, then
	 * the current cluster does not support <code>trans</code>.
	 * Otherwise, it does support it. 
	 * 
	 * @param cs
	 * @param cluster
	 * @param trans
	 * @param index, the index of <code>trans</code> in the global scenario
	 * @return true or false
	 */
	private boolean clusterSupportsTrans(Collection<RuleClusterAbstract> cs,
			RuleClusterMMLD cluster,
			MMLDGlobalTransition trans, int maxIndex) {
		State oldState = this.scenario.getState(0), newState;
		List<MMLDGlobalTransition> transes = this.getTransitionsInOrder(cs, cluster, maxIndex);
		newState = oldState.applyMMLDGlobalTransitions(transes);
		return !(newState.isApplicable(trans));
	}
	
	/**
	 * Returns the transitions contained in all clusters in the <code>cs</code> collection, except for <code>cluster</code>.
	 * The transitions are ordered as they appear in the original scenario.
	 * @param cs
	 * @param cluster
	 * @param maxIndex
	 * @return
	 */
	private List<MMLDGlobalTransition> getTransitionsInOrder(
			Collection<RuleClusterAbstract> cs, RuleClusterMMLD cluster, int maxIndex) {
		int maxLength = this.scenario.nbTrans();
		MMLDGlobalTransition[] otherClusterTranses = new MMLDGlobalTransition[maxLength];
		for (RuleClusterAbstract cl: cs) {
			List<MMLDGlobalTransition> transes = 
				cl.asClusterMMLD().getTransitions();
			List<Integer> indexes = cl.asClusterMMLD().getTransitionIndexes();
			//System.out.println("indexes: " + indexes.toString());
			if (cl.equals(cluster)) {
				for (int iter = 0; iter < transes.size(); iter++) {
					otherClusterTranses[indexes.get(iter)] = null;
					//System.out.println("index of null trans: " + iter);
				}
			} else {
				for (int iter = 0; iter < transes.size(); iter++) {
					otherClusterTranses[indexes.get(iter)] = transes.get(iter);
					//System.out.println("index of real trans: " + indexes.get(iter));
				}
			}
		}
		List<MMLDGlobalTransition> result = new Vector<MMLDGlobalTransition>();
		for (int i = 0; i < maxIndex; i++) {
			if (otherClusterTranses[i] != null)
				result.add(otherClusterTranses[i]);
		}
		return result;
	}

	/**
     * Either add the rule to an existing cluster, or create a new cluster
     * and initialise it to that rule.
     * @param state
     * @param trans
     */
    public void addTransition2(State state, MMLDGlobalTransition trans) {
        boolean transAddedToExistingCluster = false;
        for (RuleClusterAbstract cluster : clusters) {
            if (cluster.isComplete()) {
                continue;
            }
            if (cluster.asClusterMMLD().shouldAddTransition(trans)) {
                cluster.asClusterMMLD().addTransition(trans, 0);
                transAddedToExistingCluster = true;
                break;
            }
        }
        if (!transAddedToExistingCluster) {
            RuleClusterMMLD cluster = new RuleClusterMMLD(state);
            cluster.addTransition(trans, 0);
            clusters.add(cluster);
        }
    }

	@Override
	public AlarmClustersMMLD asMMLDClusters() {
		return this;
	}

	@Override
	public AlarmClustersYAMLD asYAMLDClusters() {
		return null;
	}
    
    /**
     * Returns the list of clusters.  
     * 
     * @return the list of clusters.  
     */
    public List<RuleClusterAbstract> getClusters() {
        return Collections.unmodifiableList(clusters);
    }

}
