/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDTrans;
import util.GlobalTransition;
import util.Scenario;
import java.util.HashSet;
import java.util.Set;

/**
 * As all other classes that implement {@link AlarmProcessor},
 * this class provides functionality to summarize data contained in 
 * a {@link Scenario} object.
 * Specifically, this class allows extracting specific types of events,
 * given as input to the {@link process} method, from a scenario.
 * See the documentation of the {@link process} method for further details. 
 * 
 * @author Abotea
 */
public class ExtractCatastrophicEventsYAMLD 
extends ExtractCatastrophicEventsAbstract {

    public ExtractCatastrophicEventsYAMLD() {
    	super();
    }

	@Override
	protected Set<YAMLDEvent> getEvents(int t, YAMLDComponent comp, Scenario sce) {
		GlobalTransition trans = sce.getTrans(t);
		YAMLDTrans yamdltr = trans.getTransition(comp);
		if (yamdltr == null)
			return null;
		Set<YAMLDEvent> result = new HashSet<YAMLDEvent>();
		result.add(yamdltr.eventTrigger());
		return result;
	}

}
