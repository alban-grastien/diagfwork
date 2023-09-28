/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import lang.MMLDRule;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import util.MMLDGlobalTransition;
import util.Scenario;
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
public class ExtractCatastrophicEventsMMLD 
extends ExtractCatastrophicEventsAbstract {

    public ExtractCatastrophicEventsMMLD() {
    	super();
    }

	@Override
	protected Set<YAMLDEvent> getEvents(int t, YAMLDComponent comp, Scenario sce) {
		MMLDGlobalTransition trans = sce.getMMLDTrans(t);
		MMLDRule mmldrule = trans.getRule(comp);
		if (mmldrule == null)
			return null;
		return mmldrule.getGeneratedEvents();
	}

}
