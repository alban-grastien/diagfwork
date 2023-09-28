package sim;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import util.ExplicitGlobalTransition;
import util.MMLDGlobalTransition;

import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.MMLDTransition;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDFormula;

/**
 * A partial MMLD global transition is a global transition that is partially defined.  
 * For instance, it is decided that a given transition will be triggered, 
 * but the rule that will actually be triggered is not decided yet.  
 * An input event the transition it triggers is not chosen is called <i>unsolved</i>; 
 * a transition the rule it generates is not chosen is called <i>unsolved</i>.  
 * 
 * @author Alban Grastien
 * */
public class PartialMMLDGlobalTransition {

	/**
	 * The transition that triggers the global transition.  
	 * */
	private final MMLDTransition _root;
	/**
	 * The current state.  
	 * */
	private final State _state;
	/**
	 * The set of rules already chosen.  
	 * */
	private final Set<MMLDRule> _assignedRules;
	/**
	 * The set of transitions for which no rule was chosen so far. 
	 * */
	private final Set<MMLDTransition> _unspecifiedTransitions;
	/**
	 * The set of (input) events for which no transition was chosen so far.
	 * */
	private final Set<YAMLDEvent> _unspecifiedEvents;
	/**
	 * The set of components for which something was set (possibly only an event).  
	 * */
	private final Set<YAMLDComponent> _setComps;
	
	/**
	 * Creates a partial MMLD global transition 
	 * corresponding to the triggering of the specified transition 
	 * in the specified state.  
	 * 
	 * @param tr the transition that triggers the global transition.  
	 * @param st the state where <code>tr</code> is triggered.  
	 * */
	public PartialMMLDGlobalTransition(MMLDTransition tr, State st) throws TwoTransitionsOnSameComponent {
		_root = tr;
		_state = st;
		
		_assignedRules = new HashSet<MMLDRule>();
		_unspecifiedEvents = new HashSet<YAMLDEvent>();
		_unspecifiedTransitions = new HashSet<MMLDTransition>();
		_setComps = new HashSet<YAMLDComponent>();
		
		_setComps.add(tr.getComponent());
		trySolve(tr);
	}
	
	// returns true if a rule was found.  
	private boolean trySolve(MMLDTransition tr) throws TwoTransitionsOnSameComponent {
		final YAMLDComponent comp = tr.getComponent();
		
		MMLDRule rule = null; // The last applicable rule. 
		int n = 0; // The number of rules applicable.
		
		for (final MMLDRule rl: tr.getRules()) {
			final YAMLDFormula f = rl.getCondition();
			if (f.satisfied(_state, comp)) {
				n++;
				rule = rl;
				if (n > 1) {
					return false;
				}
			}
		}
		
		if (n == 0) {
			rule = tr.getDefaultRule();
		}
		
		_assignedRules.add(rule);
		for (final YAMLDEvent outputE: rule.getGeneratedEvents()) {
			for (final MMLDSynchro synchro: outputE.getSynchros()) {
				for (final YAMLDEvent inputE: synchro.getSynchronizedEvents()) {
					final YAMLDComponent newComp = inputE.getComponent();
					if (!_setComps.add(newComp)) {
						throw new TwoTransitionsOnSameComponent(this, newComp);
					}
					if (!trySolve(inputE)) {
						_unspecifiedEvents.add(inputE);
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean trySolve(YAMLDEvent e) throws TwoTransitionsOnSameComponent {
		final Collection<MMLDTransition> transes = e.getTriggerableTransitions();
		if (transes.size() > 1) {
			return false;
		}
		if (transes.size() == 0) { // Should not happen. TODO: Generate an exception/an error?
			return false;
		}
		
		final MMLDTransition tr = transes.iterator().next();
		if (!trySolve(tr)) {
			_unspecifiedTransitions.add(tr);
		}
		
		return true;
	}
	
	/**
	 * Indicates whether this partial transition is complete, 
	 * i.e. every transition that should have been chosen indeed has 
	 * and every rule that should have been chosen also has.  
	 * */
	public boolean isComplete() {
		return _unspecifiedEvents.isEmpty() && _unspecifiedTransitions.isEmpty();
	}
	
	/**
	 * Generates a global transition if this partial transition is complete.  
	 * 
	 * @return a global transition if {@link #isComplete()} returns <code>true</code>, 
	 * <code>null</code> otherwise.  
	 * */
	public MMLDGlobalTransition getComplete() {
		if (!isComplete()) {
			return null;
		}
		
		final Map<YAMLDComponent, MMLDRule> map = new HashMap<YAMLDComponent, MMLDRule>();
		for (final MMLDRule rule: _assignedRules) {
			final YAMLDComponent comp = rule.getComponent();
			map.put(comp, rule);
		}
		final MMLDGlobalTransition result = new ExplicitGlobalTransition(map);
		return result;
	}
	
	/**
	 * Returns the list of events that need to be solved.  
	 * 
	 * @return the list of events for which a transition has not been chosen.  
	 * */
	public Collection<YAMLDEvent> unsolvedEvents() {
		return Collections.unmodifiableCollection(_unspecifiedEvents);
	}
	
	/**
	 * Returns the list of transitions that need to be solved.  
	 * 
	 * @return the list of transitions for which a transition has not been chosen.  
	 * */
	public Collection<MMLDTransition> unsolvedTransitions() {
		return Collections.unmodifiableCollection(_unspecifiedTransitions);
	}
	
	/**
	 * Solves the specified event by choosing the specified transition.  
	 * The transition should indeed include the specified event as an input event.  
	 * 
	 * @param evt the event. 
	 * @param tr the transition.  
	 * @return <code>true</code> if the event was solved.  
	 * */
	public boolean solve(YAMLDEvent evt, MMLDTransition tr) throws TwoTransitionsOnSameComponent {
		if (!tr.getTriggeringEvents().contains(evt)) {
			return false;
		}
		
		if (!_unspecifiedEvents.contains(evt)) {
			return false;
		}
		
		_unspecifiedEvents.remove(evt);
		// now try to choose a rule (in case several are possible)
		if (!trySolve(tr)) {
			_unspecifiedTransitions.add(tr);
		}
		
		return true;
	}
	
	/**
	 * Solves the specified transition by choosing the specified rule.  
	 * The rule should indeed be a rule of the transition 
	 * and it should be a rule that may be triggered.  
	 * 
	 * @param tr the transition.  
	 * @param rl the rule.  
	 * @return <code>true</code> if the transition was solved.  
	 * */
	public boolean solve(MMLDTransition tr, MMLDRule rl) throws TwoTransitionsOnSameComponent {
		if (rl.getTransition() != tr) {
			return false;
		}
		
		if (rl == tr.getDefaultRule()) { // If it were, it would have been automatically solved
			return false;
		}
		
		if (!rl.getCondition().satisfied(_state, tr.getComponent())) {
			return false;
		}
		
		if (!_unspecifiedTransitions.contains(tr)) {
			return false;
		}
		
		_unspecifiedTransitions.remove(tr);
		// now try to choose transitions for the synchronised events
		for (final YAMLDEvent outputE: rl.getGeneratedEvents()) {
			for (final MMLDSynchro synchro: outputE.getSynchros()) {
				for (final YAMLDEvent inputE: synchro.getSynchronizedEvents()) {
					final YAMLDComponent newComp = inputE.getComponent();
					if (!_setComps.add(newComp)) {
						throw new TwoTransitionsOnSameComponent(this, newComp);
					}
					if (!trySolve(inputE)) {
						_unspecifiedEvents.add(inputE);
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the list of candidate transitions of the specified event.  
	 * 
	 * @param evt an unsolved event.  
	 * */
}
