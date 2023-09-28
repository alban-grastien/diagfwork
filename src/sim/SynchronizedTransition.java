package sim;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lang.Network;
import lang.State;
import lang.YAMLDAssignment;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDSync;
import lang.YAMLDTrans;
import lang.YAMLDValue;
import lang.YAMLDVar;

/**
 * A synchronized transition is a group of transitions that take place together
 * (hence the 'synchronized'). There is one triggering transition and a
 * (possibly empty) group of synchronized transitions. The synchronized
 * transitions is the list of transitions such that their associated event is
 * synchronized with the event associated with the triggering transition. The
 * triggering event is not 'synchronized' (i.e. this label is not associated
 * with the event in the model) while the events of the synchronized transitions
 * are.
 * <p />
 * 
 * The group is enabled whenever the triggering transition is enabled. The
 * transitions that take place then are the triggering transitions plus any
 * synchronized transition that is enabled. Note that consequently:
 * <ul>
 * <li>if there is no transition enabled for a synchronized event, then no
 * transition take place on the corresponding component;</li>
 * <li>if there are several transitions enabled on the same component, they may
 * conflict; how this conflict is resolved is not specified.</li>
 * </ul>
 * The two cases above should be delt with at the modeling level.
 * 
 * @author Jussi Rintanen
 * @author Alban Grastien
 * */
@Deprecated
public class SynchronizedTransition {
	private final YAMLDComponent component;
	private final YAMLDTrans transition;

	private final Set<YAMLDTrans> synchronizesWith;

	/**
	 * Builds a transition record from the specified forced transition. This
	 * constructor deals with computing the transitions that synchronize with
	 * the forced one.
	 * 
	 * @param net
	 *            the network on which the transition is defined.
	 * @param trans
	 *            the forced transition.
	 * */
	public SynchronizedTransition(Network net, YAMLDTrans trans) {
		component = trans.getComponent();
		transition = trans;

		final Set<YAMLDTrans> sw = new HashSet<YAMLDTrans>();

		final YAMLDEvent e1 = trans.eventTrigger();

		YAMLDSync s = net.getSync(e1);
		if (s != null) {
//			for (YAMLDEvent e : s.events()) {
				// TODO: There should probably be a way to access
				// the transitions of a specified event directly
//				for (YAMLDTrans str : e.getComponent().trans()) {
//					if (str.eventTrigger() == e) {
//						sw.add(str);
//					}
//				}
//			}
		}
		synchronizesWith = Collections.unmodifiableSet(sw);
	}

	/**
	 * Returns the list of synchronized transition.
	 * */
	public Collection<YAMLDTrans> synchronizedTransitions() {
		return synchronizesWith;
	}

	/**
	 * Returns the triggering transition of this SynchronizedTransition.
	 * */
	public YAMLDTrans transition() {
		return transition;
	}

	public String toString() {
		return component.name() + "." + transition.eventTrigger().name();
	}

	/**
	 * Stores the effects of this synchronized transition on the specified state
	 * in the specified map.
	 * 
	 * @param s
	 *            the state from which the synchronized transition is performed.
	 * @param m
	 *            the map that stores the assignments.
	 * */
	public void storeEffects(State s, Map<YAMLDVar, YAMLDValue> m) {
		for (final YAMLDAssignment ass : transition.assignments()) {
			m.put((YAMLDVar) ass.variable(), ass.expression().value(s,
					transition.getComponent()));
		}
		for (final YAMLDTrans tr : synchronizesWith) {
			if (tr.formula().satisfied(s, tr.getComponent())) {
				for (final YAMLDAssignment ass : tr.assignments()) {
					m.put((YAMLDVar) ass.variable(), ass.expression().value(s,
							tr.getComponent()));
				}
			}
		}
	}
	
	/**
	 * Stores the transition that is triggered on each component if this 
	 * synchronized transition takes place.  
	 * 
	 * @param s
	 *            the state from which the synchronized transition is performed.
	 * @param m
	 *            the map that stores the triggered events.
	 * */
	public void storeTransitions(State s, Map<YAMLDComponent,YAMLDTrans> m) {
		m.put(transition.getComponent(), transition);
		for (final YAMLDTrans tr : synchronizesWith) {
			if (tr.formula().satisfied(s, tr.getComponent())) {
				m.put(tr.getComponent(), tr);
			}
		}
	}
}
