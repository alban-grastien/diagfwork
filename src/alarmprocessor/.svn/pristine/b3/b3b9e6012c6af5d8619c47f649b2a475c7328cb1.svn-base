package alarmprocessor;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Vector;
import lang.YAMLDEvent;
import util.Scenario;

public abstract class AlarmClustersAbstract implements SummaryFromScenario {

	/**
	 * This data member stores the clusters of alarms.
	 */
	protected Vector<RuleClusterAbstract> clusters;
	/**
	 * This data member is the scenario that is processed
	 * to extract the clusters.
	 */
	protected Scenario scenario;

	public AlarmClustersAbstract() {
		super();
		clusters = new Vector<RuleClusterAbstract>();
	}

	public AlarmClustersAbstract(Scenario sce) {
		super();
		clusters = new Vector<RuleClusterAbstract>();
		scenario = sce;
	}

	/**
     * Computes the list of transient alarms from all clusters.
     * Clusters that are complete (according to their complete flag)
     * have no transient alarms at all.
     * From incomplete clusters, extract only the observable events
     * directly related to components that end up in a transient state
     * at the end of executing the events in the cluster at hand.
     */
    public TransientAlarms extractTransientAlarms() {
        TransientAlarms result = new TransientAlarms();
        for (RuleClusterAbstract cluster : clusters) {
            if (!cluster.isComplete())
                result.addAll(cluster.extractTransientAlarms(scenario));
        }
        return result;
    }
    
    /**
	 * Remove from the list of clusters of this object those clusters
	 * that contain at least one event from the collection given as input.
	 * @param a collection of {@link YAMLDEvent} objects
	 */
	public void filterOutIncidentClusters(Collection<YAMLDEvent> faults) {
	    Vector<RuleClusterAbstract> newresult = new Vector<RuleClusterAbstract>();
	    for (RuleClusterAbstract cluster : clusters) {
	        boolean keep_cluster = true;
	        for (YAMLDEvent event : faults) {
	            if (cluster.containsEvent(event)) {
	                keep_cluster = false;
	                break;
	            }
	        }
	        if (keep_cluster)
	            newresult.add(cluster);
	    }
	    this.clusters = newresult;
	}

	/**
	 *
	 * @param filename
	 */
	public void writeToFile(String filename) {
	   try {
	        final PrintStream ps = new PrintStream(new FileOutputStream(filename));
        	ps.print(this.toString());
	        ps.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.exit(1);
	    }
	 }

	public String toString() {
		String result = "CLUSTERS (" + clusters.size() + " in total):\n\n";
		int i = 0;
	    for (RuleClusterAbstract cluster : clusters) {
        	result += "====== CLUSTER " + i + " ======\n";
        	result += "==ALARMS==\n";
        	result += cluster.getAlarmsAsString(scenario);
        	result += "==ALL EVENTS (BOTH ALARMS AND OTHER EVENTS)==\n";
        	result += cluster.getEventsAsString();
        	result += "==TRANSITIONS AND COMPONENTS==\n";
        	result += cluster.toString();
        	result += "\n\n";
        	i++;
	    }
	    return result;
	}
	
	public String toSimpleString() {
		String result = "";
	    for (RuleClusterAbstract cluster : clusters) {
        	result += cluster.getAlarmsAsString(scenario);
        	result += "\n\n\n";
	    }
	    return result;
	}

	public abstract AlarmClustersMMLD asMMLDClusters();
	public abstract AlarmClustersYAMLD asYAMLDClusters();

}
