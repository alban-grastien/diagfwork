package alarmprocessor;

import java.util.Map;
import util.Scenario;

public abstract class AlarmClusteringAbstract implements AlarmProcessor {

	/**
	 * This data member stores the results of clustering.
	 */
	protected AlarmClustersAbstract clusters;

	public AlarmClusteringAbstract() {
		super();
	}

	@Override
	public SummaryFromScenario getProcessingResults() {
		return clusters;
	}

	@Override
	public void process(Scenario sce, Map<String, Object> extraInfo) {
		clusters = alarmClustering(sce);
		// System.out.println(clusters.toString());
	}

    /**
     * Partitions a scenario into subsets of events.
     * Then, the alarms in one subset will belong to one record in
     * the output, which is a clustering of the entire set of alarms (observations)
     * in the scenario.
     * @param sce
     * @param output
     */
    protected abstract AlarmClustersAbstract alarmClustering(Scenario sce);

}