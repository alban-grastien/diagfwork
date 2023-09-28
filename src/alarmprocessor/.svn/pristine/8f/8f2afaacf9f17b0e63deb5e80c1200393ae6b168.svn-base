package alarmprocessor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import diag.Util;
import edu.supercom.util.Options;
import util.AlarmLog;
import util.Scenario;
import util.TimedScenario;
import lang.MMLDlightParser;
import lang.YAMLDEvent;

public class AlarmProcessingManager {
	
	private HashMap<TimedScenario, AlarmProcessingSnapshot> snapshots;
	private Collection<YAMLDEvent> faults;

	public AlarmProcessingManager() {
	    snapshots = new HashMap<TimedScenario, AlarmProcessingSnapshot>();
	    Options opt = gui.YAMLDSim.OPTIONS;
	    String filename = opt.getOption(true, true, "faults");
		faults = Util.parseEvents(MMLDlightParser.net, filename);
	}
		
	private AlarmProcessingSnapshot runAlarmProcessing(TimedScenario ts) {
		// build a few helper objects
		Scenario sce = ts.getScenario();
		Map<String, Object> extra_info = new HashMap<String, Object>();
		extra_info.put("faults", faults);
		AlarmClusteringAbstract a = new AlarmClusteringMMLD();

		// extract raw alarms
		AlarmLog log = ts.alarmLog(MMLDlightParser.net.observableEvents());
		String ra = log.toFormattedString();

		// do alarm clustering
		AlarmProcessor ap = new AlarmClusteringMMLD();
		ap.process(sce, null);
		SummaryFromScenario result = ap.getProcessingResults();
		String cls = result.toSimpleString();
		
		// extract catastrophic events
		ap = new ExtractCatastrophicEventsMMLD();
		ap.process(sce, extra_info);
		result = ap.getProcessingResults();
		String fe = result.toSimpleString();
		
		// third, extract fault independent alarms
		ap = new ExtractIncidentIndependentAlarms(a);
		ap.process(sce, extra_info);
		result = ap.getProcessingResults();
		String iia = result.toSimpleString();

		// fourth, show transient alarms 
		ap = new ExtractTransientAlarms(a);
		ap.process(sce, extra_info);
		result = ap.getProcessingResults();
		String ta = result.toSimpleString();
		return new AlarmProcessingSnapshot(ra, cls, ta, iia, fe);		
	}
	
	/**
	 * First tries to fetch the snapshots from an internal cache.
	 * If it doesn't find it, it computes a snapshot, places it in the cache
	 * in case it's needed later, and returns the computed snapshot.
	 * @param sce
	 * @return snapshot data corresponding to <code>sce</code>
	 */
	public AlarmProcessingSnapshot getSnapshot(TimedScenario sce) {
		if (!snapshots.containsKey(sce)) {
			AlarmProcessingSnapshot snapshot = this.runAlarmProcessing(sce);
			snapshots.put(sce, snapshot);
		}
		return snapshots.get(sce);
	}
}
