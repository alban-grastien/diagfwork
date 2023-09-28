package util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.MMLDTransition;
import lang.State;
import lang.StateModification;
import lang.YAMLDComponent;
import lang.YAMLDEvent;

/**
 * {@link ExplicitGlobalTransition} is an implementation of {@link MMLDGlobalTransition} 
 * where the {@link MMLDRule} associated with each component are explicitly enumerated.  
 * 
 * @author Alban Grastien 
 * @version 1.0
 * */
public class ExplicitGlobalTransition implements MMLDGlobalTransition {
	
	/**
	 * A map that associates each affected component with the rule that affects it.  
	 * */
	private final Map<YAMLDComponent, MMLDRule> _map;
	
	/**
	 * Builds an explicit global transition with the specified map of effects.  
	 * 
	 * @param eff a map that associates each affected component with the rule that affects it.
	 * */
	public ExplicitGlobalTransition(Map<YAMLDComponent, MMLDRule> map) {
        for (final YAMLDComponent c: map.keySet()) {
            final MMLDRule r = map.get(c);
            if (r == null) {
                throw new Error("Null rule associated with component " + c.name());
            }
        }
		_map = new HashMap<YAMLDComponent, MMLDRule>(map);
	}

	@Override
	public MMLDRule getRule(YAMLDComponent c) {
		return _map.get(c);
	}

	@Override
	public boolean isAffected(YAMLDComponent c) {
		return _map.containsKey(c);
	}

	@Override 
	public String toString() {
		return _map.toString();
	}

	@Override
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		
		for (final YAMLDComponent comp: affectedComponents()) {
			final MMLDRule rule = getRule(comp);
			result.append(comp.name()).append(".").append(rule.getName()).append(";\n");
		}
		
		return result.toString();
	}

	@Override
	public Collection<YAMLDComponent> affectedComponents() {
		return _map.keySet();
	}

	@Override
	public Collection<YAMLDComponent> triggeringComponents() {
		final Collection<YAMLDComponent> result = new HashSet<YAMLDComponent>(affectedComponents());
		
		for (final Iterator<YAMLDComponent> compIt = result.iterator() ; compIt.hasNext() ; ) {
			final YAMLDComponent inputC = compIt.next();
			final MMLDRule inputR = getRule(inputC);
            if (inputR == null) {
                System.out.println("Component: " + inputC.name());
            }
            final MMLDTransition mmldTrans = inputR.getTransition();
            if (mmldTrans == null) {
                System.err.println(inputR.getName());
            }
			final Collection<YAMLDEvent> inputEvents = mmldTrans.getTriggeringEvents();
			
			// Testing if one of the <code>events</code> synchronises with an output event.
			boolean synchronises = false;
			for (final YAMLDEvent inputE : inputEvents) {
				final Set<MMLDSynchro> synchros = inputE.getSynchros();
				for (final MMLDSynchro syn : synchros) {
					final YAMLDEvent outputE = syn.getEvent();
					final YAMLDComponent outputC = outputE.getComponent();
					final MMLDRule outputR = getRule(outputC);
					if (outputR == null) {
						continue;
					}
					final Collection<YAMLDEvent> outputEvents = outputR.getGeneratedEvents();
					if (outputEvents.contains(outputE)) {
						synchronises = true;
						break;
					}
				}
			}
			// modified by Adi
			if (synchronises) { // original
			//if (!synchronises) { // Adi's modification
				compIt.remove();
			}
		}
		
		return result;
	}

	@Override
	public MMLDGlobalTransition cascade(YAMLDComponent comp) {
		final Map<YAMLDComponent,MMLDRule> map = new HashMap<YAMLDComponent, MMLDRule>();
		pCascade(map, comp);
		final MMLDGlobalTransition result = new ExplicitGlobalTransition(map);
		return result;
	}
	
	private void pCascade(Map<YAMLDComponent,MMLDRule> map, YAMLDComponent comp) {
		final MMLDRule rule = getRule(comp);
		if (rule == null) {
			return;
		}
		map.put(comp, rule);
		
		for (final YAMLDEvent outputE: rule.getGeneratedEvents()) {
			for (final MMLDSynchro synchro: outputE.getSynchros()) {
				for (final YAMLDEvent inputE: synchro.getSynchronizedEvents()) {
					final YAMLDComponent newComp = inputE.getComponent();
					pCascade(map,newComp);
				}
			}
		}
	}

	@Override
	public MMLDGlobalTransition merge(MMLDGlobalTransition trans) {
		final Map<YAMLDComponent,MMLDRule> result = new HashMap<YAMLDComponent, MMLDRule>(_map);
		for (final YAMLDComponent comp: trans.affectedComponents()) {
			result.put(comp, trans.getRule(comp));
		}
		return new ExplicitGlobalTransition(result);
	}

	@Override
	public StateModification getModification(State s) {
		return new TransStateModification(this, s);
	}
}
