package alarmprocessor;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import util.Scenario;
import lang.YAMLDComponent;
import lang.YAMLDEvent;

public abstract class ExtractCatastrophicEventsAbstract implements AlarmProcessor {

    /**
     * This data member stores the result of root cause analysis.
     */
    protected CatastrophicEvents rootCauseEvents;
    
    public ExtractCatastrophicEventsAbstract() {
    	rootCauseEvents = new CatastrophicEvents();
    }

    public SummaryFromScenario getProcessingResults() {
        return rootCauseEvents;
    }

    /**
     * Extracts a restricted set of root cause events from a scenario.
     * Specifically, the result is the intersection between all events in the
     * scenario and all events given in the second input parameter.
     * Normally, the second parameter will contain all catastrophic events.
     * Hence, this method will return the catastrophic events that are part
     * of the scenario at hand.
     * @param a scenario to be analysed
     * @param a map that contains at least the pair ("faults", collection of events)
     */
    public void process(Scenario sce, Map<String, Object>  extra_info) {
        Collection<YAMLDEvent> faults =
                (Collection<YAMLDEvent>) extra_info.get("faults");
    	for (YAMLDEvent event : faults) {
    		YAMLDComponent comp = event.getComponent();
     		for (int i = 0; i < sce.nbTrans(); i++) {
        		Set<YAMLDEvent> events = getEvents(i, comp, sce);
        		if (events == null)
        			continue;
        		for (YAMLDEvent e : events) {
	        		if (e == null)
	        			continue;
	        		if (event.equals(e))
	        			rootCauseEvents.addEvent(event);
        		}
        	}
        }
    }

    /**
     * 
     * @param t
     * @param comp
     * @param sce
     * @return a set of events that occur in component comp at time t in scenario sce
     */
    protected abstract Set<YAMLDEvent> getEvents(int t, 
    		YAMLDComponent comp, Scenario sce);
    
}
