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
 * An MMLDRule is a specific case of a {@link MMLDTransition}.  
 * 
 * @author Alban Grastien
 * */
public class MMLDRule {
	
	/**
	 * The component on which this rule is defined.  
	 * */
	private final YAMLDComponent _comp;

	/**
	 * The list of assignments resulting for the triggering of the rule.  
	 * */
	private final Map<YAMLDVar,YAMLDAssignment> _ass;
	
	/**
	 * Another representation of the assignments.  
	 * */
	private final List<YAMLDAssignment> _assList;
	
	/**
	 * The precondition that needs to be satisfied for this rule 
	 * to be applicable.  
	 * TODO: this formula could be made non final, 
	 * and an operation could be applied to try to simplify the formula when possible.  
	 * */
	private final YAMLDFormula _formula;
	
	/**
	 * The name of this rule.  
	 * */
	private final String _name;
	
	/**
	 * The list of events that are generated if this rule is triggered.  
	 * */
	private final Set<YAMLDEvent> _events;
	
	/**
	 * The transition that contains this rule (initially not set).  
	 * */
	private MMLDTransition _trans;
	
	/**
	 * Builds a rule that can be applied if the specified formula is satisfied 
	 * and that leads to the specified assignments.  
	 *
	 * @param c the component on which the rule is defined.  
	 * @param f the formula that needs to be satisfied.  
	 * @param a the list of assignments resulting from the triggering of the rule.
	 * @param n the name of the rule.  
	 * @param e the list of events generated when the rule is triggered.  
	 * @throws IllegalArgumentException if a variable is assigned more than once in this rule.  
	 * */
	public MMLDRule(YAMLDComponent c, YAMLDFormula f, Collection<YAMLDAssignment> a, 
			Set<YAMLDEvent> e, String n) {
		_comp = c;
		_formula = f;
		_assList = new ArrayList<YAMLDAssignment>(a);
		final Map<YAMLDVar, YAMLDAssignment> assMap = new HashMap<YAMLDVar, YAMLDAssignment>();
		for (final YAMLDAssignment ass: a) {
			final YAMLDVar var = (YAMLDVar)ass._var;
			final YAMLDAssignment old = assMap.put(var, ass);
			if (old != null) {
				throw new IllegalArgumentException("Variable " 
						+ var.toFormattedString() + " already assigned in this rule");
			}
		}
		_ass = Collections.unmodifiableMap(assMap);
		_name = n;
		_events = Collections.unmodifiableSet(new HashSet<YAMLDEvent>(e));
		for (final YAMLDEvent ev: e) {
			ev.addGeneratingRule(this);
		}
	}
	
	/**
	 * Creates a default rule for the specified transition.  
	 * 
	 * @param tr the transition for which this default rule is created.  
	 * */
	public MMLDRule(MMLDTransition tr) {
		_comp = tr.getComponent();
		_formula = YAMLDTrue.TRUE;
		_assList = Collections.emptyList();
		_events = Collections.emptySet();
		_name = tr.getName() + "_default_rule";
		_ass = Collections.emptyMap();
		_trans = tr;
	}
	/**
	 * Returns the condition for this rule to be applicable.  
	 * 
	 * @return the condition that needs to be satisfied for this rule to be applicable.  
	 * */
	public YAMLDFormula getCondition() {
		return _formula;
	}
	
	/**
	 * Returns the list of assignments generated by the application of this rule.  
	 * 
	 * @return the list of assignments applied when this rule is triggered.  
	 * */
	public List<YAMLDAssignment> getAssignments() {
		return _assList;
	}
	
	/**
	 * Returns the list of variables that are assigned by this rule.  
	 * 
	 * @return the list of variables whose assignment may be modified by this rule.  
	 * */
	public Set<YAMLDVar> getVariables() {
		return _ass.keySet();
	}
	
	/**
	 * Returns the assignment that is associated with the specified variable 
	 * when this rule is triggered.  
	 * 
	 * @param var the variable.  
	 * @return the assignment <code>var</code> when this rule is triggered 
	 * if an assignment is associated with the variable, <code>null</code> otherwise.  
	 * */
	public YAMLDAssignment getAssignment(YAMLDVar var) {
		return _ass.get(var);
	}
	
	/**
	 * Returns the component on which this rule is defined.  
	 * 
	 * @return the component that is affected by this rule.  
	 * */
	public YAMLDComponent getComponent() {
		return _comp;
	}
	
	/**
	 * Returns the name of this rule.  
	 * 
	 * @return the name of this rule.  
	 * */
	public String getName() {
		return _name;
	}
	
	/**
	 * Returns a string representation of this rule.  
	 * 
	 * @return a string representation of this rule.  
	 * */
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		
		// TODO: Check the syntax is correct
		result.append(getName()).append(" ");
		result.append(_formula.toFormattedString());
		result.append(" -> ");
		{
			final List<String> consequences = new ArrayList<String>();
			for (final YAMLDAssignment ass: getAssignments()) {
				final String string = ass.variable().name()
					+ " := "
					+ ass.expression().toFormattedString();
				consequences.add(string);
			}
			for (final YAMLDEvent e: getGeneratedEvents()) {
				final String string = e.toFormattedString();
				consequences.add(string);
			}
			result.append("");
			for (int i=0 ; i<consequences.size()-1 ; i++) {
				final String s = consequences.get(i);
				result.append(s).append(", ");
			}
			if (consequences.size() != 0) {
				result.append(consequences.get(consequences.size()-1));
			}
			result.append("");
			result.append(";");
		}
		
		return result.toString();
	}
	
	/**
	 * Returns the list of events generated by this rule.  
	 * 
	 * @return the list of events that take place 
	 * as a consequence of this rule being triggered.  
	 * */
	public Set<YAMLDEvent> getGeneratedEvents() {
		return _events;
	}
	
	/**
	 * Indicates whether the specified event is generated 
	 * by the triggering of this rule.  
	 * 
	 * @param e the event that is tested.  
	 * @return <code>true</code> if <code>e</code> is part of the set 
	 * of generated events of this rule.  
	 * @see #getGeneratedEvents()
	 * */
	public boolean isGenerated(YAMLDEvent e) {
		return _events.contains(e);
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
	/**
	 * Returns the transition associated with this rule.  
	 * 
	 * @return the transition this rule is part of.  
	 * */
	public MMLDTransition getTransition() {
		if (_trans == null) {
			for (final MMLDTransition trans: _comp.transitions()) {
				if (trans.getRules().contains(this)) {
					_trans = trans;
					break;
				}
			}
		}
		
		return _trans;
	}
	
	@Override
	public int hashCode() {
		final int P1 = 121151;
		final int P2 = 180181;
		final int P3 = 108881;
		return P1*this._name.hashCode() + P2*this._comp.hashCode() + 
		P3*this.getTransition().getName().hashCode();
	}
	
}
