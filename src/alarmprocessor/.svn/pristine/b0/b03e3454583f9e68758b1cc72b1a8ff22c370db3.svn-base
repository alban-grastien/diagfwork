package alarmprocessor;

import java.util.List;

/**
 * This class stores various types of filtered data computed with alarm processing.
 * All data correspond to the same scenario and state. Therefore, this class
 * is a snapshot of alarm processing results taken at a given time in the evolution of the system.
 * @author abotea
 *
 */
public class AlarmProcessingSnapshot {
	private String rawAlarms;
	private String clusters;
	private String transientAlarms;
	private String incidentIndepAlarms;
	private String faultEvents;
	
	public AlarmProcessingSnapshot(String ra, String cls, 
			String ta, String iia, String fe) {
		rawAlarms = ra;
		clusters = cls;
		transientAlarms = ta;
		incidentIndepAlarms = iia;
		faultEvents = fe;
	}
	
	public String getRawAlarms() {
		return rawAlarms;
	}
	
	public String getAlarmClusters() {
		return clusters;
	}
	
	public String getFaultEvents() {
		return faultEvents;
	}
	
	public String getIncidentIndepAlarms() {
		return incidentIndepAlarms;
	}
	
	public String getTransientAlarms() {
		return transientAlarms;
	}	
}
