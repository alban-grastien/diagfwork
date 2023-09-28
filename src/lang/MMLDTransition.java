package lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An MMLDTransition is a transition that defines 
 * how the (non dependent) state of a component 
 * is modified by occurrences of events.  
 * The MMLDTransition is supposed to be the definite model of the system, 
 * and is supposed to replace {@link YAMLDTrans}.
 * 
 * @author Alban Grastien
 * */
public class MMLDTransition {

	/**
	 * The component on which this MMLDTransition is defined.  
	 * */
	private final YAMLDComponent _comp;
	
	/**
	 * The list of rules associated with this transition.  
	 * This excludes the default rule.  
	 * */
	private final List<MMLDRule> _rules;
	
	/**
	 * The default rule.  
	 * */
	private final MMLDRule _defaultRule;
	
	/**
	 * The list of events that can trigger this transition (immediately).  
	 * */
	private final Set<YAMLDEvent> _events;
	
	/**
	 * The list of preconditions that allow a spontaneous triggering.  
	 * */
	private final Map<YAMLDFormula,PeriodInterval> _preconditions;
	
	/**
	 * The name of this transition.  
	 * */
	private String _name;

	/**
	 * Builds a transition which the specified parameters.  
	 * 
	 * @param n the name of this transition.  
	 * @param c the component.  
	 * @param r the list of rules.  
	 * @param e the set of events that can trigger the transition.  
	 * @param p the list of precondition that are sufficient 
	 * to trigger the transition, 
	 * together with the time interval 
	 * during which the precondition needs/may be satisfied.  
	 * */
	public MMLDTransition(String n, YAMLDComponent c, Collection<MMLDRule> r, 
			Set<YAMLDEvent> e, 
			Map<YAMLDFormula,PeriodInterval> p) {
		_name = n;
		_comp = c;
		_rules = new ArrayList<MMLDRule>(r);
		_defaultRule = new MMLDRule(this);
		_events = Collections.unmodifiableSet(new HashSet<YAMLDEvent>(e));
		_preconditions = Collections.unmodifiableMap(
				new HashMap<YAMLDFormula, PeriodInterval>(p));
	}
	
	/**
	 * Returns the {@link YAMLDComponent} on which this transition is defined.  
	 * 
	 * @return the component this transition apply to.  
	 * */
	public YAMLDComponent getComponent() {
		return _comp;
	}
	
	/**
	 * Returns the list of rules associated with this transition.  
	 * The list excludes the default rule.  
	 * 
	 * @return the list of rules associated with this transition.  
	 * */
	public List<MMLDRule> getRules() {
		return _rules;
	}
	
	/**
	 * Returns the default rule associated with this transition.  
	 * The default rule has a trivial condition, no effect.  
	 * It can be applied only when no other rule is applicable.  
	 * This is not strictly essential, but kept for consistency.  
	 * 
	 * @return the default rule.  
	 * */
	public MMLDRule getDefaultRule() {
		return _defaultRule;
	}
	
	/**
	 * Returns the list of events that can trigger this transition.  
	 * 
	 * @return the list of events that can trigger this transition.  
	 * */
	public Set<YAMLDEvent> getTriggeringEvents() {
		return _events;
	}
	
	/**
	 * Indicates whether the specified events can trigger this transition.  
	 * 
	 * @param e the event that is tested.  
	 * @return <code>true</code> if <code>e</code> can trigger this transition, 
	 * <code>false</code> otherwise.  
	 * */
	public boolean canTrigger(YAMLDEvent e) {
		return _events.contains(e);
	}
	
	/**
	 * Returns the list of preconditions that are (each) sufficient 
	 * to trigger this transition.
	 * 
	 * @return the list of preconditions that can trigger this transition 
	 * if one of them is satisfied.  
	 * */
	public Set<YAMLDFormula> getPreconditions() {
		return _preconditions.keySet();
	}
	
	/**
	 * Returns the time interval representing the minimum time 
	 * for which the specified condition needs to be satisfied 
	 * and the maximum time before the transition has to be triggered.  
	 * 
	 * @param f the condition.  
	 * @return the time interval associated with the precondition.  
	 * */
	public PeriodInterval getConditionTime(YAMLDFormula f) {
		return _preconditions.get(f);
	}
	
	/**
	 * Returns the name of this transition.  
	 * 
	 * @return the name of this transition.  
	 * */
	public String getName() {
		return _name;
	}
	
	/**
	 * Indicates whether this transition is spontaneous, i.e. it can spontaneously take place.  
	 * Practically it means the transition has no triggering event 
	 * and no precondition.  
	 * 
	 * @return <code>true</code> if this transition can spontaneously trigger, 
	 * <code>false</code> otherwise.  
	 * */
	public boolean isSpontaneous() {
		return _events.isEmpty() && _preconditions.isEmpty();
	}
	
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		
		result.append("transition ").append(getName()).append(" {\n");
		
		for (final MMLDRule rule: getRules()) {
			result.append(rule.toFormattedString()).append("\n");
		}
		
		result.append("} ");
		result.append("triggeredby ");
		
		{
			result.append("");
			final List<String> triggeringConditions = new ArrayList<String>();
			for (final YAMLDEvent e: getTriggeringEvents()) {
				triggeringConditions.add(e.toFormattedString());
			}
			for (final YAMLDFormula f: getPreconditions()) {
				final PeriodInterval ti = getConditionTime(f);
				triggeringConditions.add(ti.toString() + " " + f.toFormattedString());
			}
			final int size = triggeringConditions.size();
			for (int i= 0 ; i < size-1 ; i++) {
				result.append(triggeringConditions.get(i)).append(", ");
			}
			if (size != 0) {
				result.append(triggeringConditions.get(size-1));
			}
		}
		result.append(";");
		
		return result.toString();
	}

    @Override
	public String toString() {
		return _comp.name() + "." + _name;
	}
}
