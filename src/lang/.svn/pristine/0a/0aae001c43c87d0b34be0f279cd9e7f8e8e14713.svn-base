package lang;

import java.util.Collection;
import java.util.Map;

import util.GlobalTransition;
import util.MMLDGlobalTransition;

/**
 * Implements general methods for a state. 
 * */
public abstract class AbstractState implements State {
	
//	private boolean _forcedTransitionEnabled = false;
	
//	private boolean _forcedTransitionEnabledComputed = false;

	/**
	 * Returns the list of transitions in the specified component 
	 * that can take place according to the specified component 
	 * state.  
	 * 
	 * @param s the state where the transitions' precondition must be satisfied.  
	 * @param c the component for which the transitions are tested.  
	 * @return the list of transitions that are enabled for component <code>c</code>.  
	 * */
	@Deprecated
	public static Collection<YAMLDTrans> getLocalTransitionsEnabled(State s, YAMLDComponent c) {
//		final Collection<YAMLDTrans> result = new LinkedList<YAMLDTrans>(c.trans());
//		
//		{
//			final Iterator<YAMLDTrans> it = result.iterator();
//			while (it.hasNext()) {
//				final YAMLDTrans trans = it.next();
//				final YAMLDFormula cond = trans.formula();
//				if (!cond.satisfied(s, c)) {
//					it.remove();
//				}
//			}
//		}
//		
//		return result;
		return null;
	}

	@Override
	@Deprecated
	public boolean hasEnabledForcedTransition() {
//		if (_forcedTransitionEnabledComputed) {
//			return _forcedTransitionEnabled;
//		}
//		
//		boolean forced = false;
//		for (final YAMLDComponent c: getNetwork().getComponents()) {
//			for (final YAMLDTrans t: c.trans()) {
//				if (t.isforced && t.formula().satisfied(this, c)) {
//					forced = true;
//					break;
//				}
//			}
//		}
//
//		_forcedTransitionEnabled = forced;
//		_forcedTransitionEnabledComputed = true;
//		return _forcedTransitionEnabled;
		return false;
	}
	
	@Deprecated
	public State apply(Collection<YAMLDTrans> transes) {
//		// Computes the list of new assignments.  
//		final Map<YAMLDVar,YAMLDValue> m = new HashMap<YAMLDVar, YAMLDValue>();
//		
//		for (final YAMLDTrans trans: transes) {
//			for (final YAMLDAssignment ass: trans.assignments()) {
//				final YAMLDVar var = (YAMLDVar)ass.variable();
//				final YAMLDExpr expr = ass.expression();
//				final YAMLDValue val = expr.value(this, var.getComponent());
//				if (val.equals(this.getValue(var))) {
//					continue;
//				}
//				if (m.put(var, val) != null) {
//					throw new IllegalArgumentException("Cannot apply specified set of transitions: "
//							+ "the transition conflict on the assignment of variable " 
//							+ var.toFormattedString());
//				}
//			}
//		}
//		
//		if (m.isEmpty()) {
//			return this;
//		}
//		
//		return apply(m);
		return null;
	}
	
	@Deprecated
	public State applyGlobalTransitions(Collection<GlobalTransition> transitions) {
//        Collection<YAMLDTrans> yamldts = new Vector<YAMLDTrans>();
//        Network net = this.getNetwork();
//        for (GlobalTransition trans : transitions) {
//        	for (YAMLDComponent comp : net.getComponents()) {
//	    		YAMLDTrans yamldtr = trans.getTransition(comp);
//	    		if (yamldtr != null)
//	    			yamldts.add(yamldtr);
//        	}
//        }
//        // apply list of transitions to obtain final state
//        State finalState = this.apply(yamldts);
//        return finalState;
		return null;
	}

	@Override
	@Deprecated
	public State apply(Map<? extends YAMLDVar, YAMLDValue> m) {
//		return apply(new MapStateModification(m));
		return null;
	}

	@Override
	public boolean isApplicable(MMLDGlobalTransition trans) {
		// check the preconditions of the MMLDTrans objects 
		// contained in trans, excepting those MMLDTrans objects
		// that are triggered by local events
		if (!MMLDTransPrecsHold(trans))
			return false;
		// check the preconditions of the MMLDRule objects
		if (!MMLDRulePrecsHold(trans))
			return false;
		return true;
	}

	/**
	 * Checks if the preconditions of the {@link MMLDRule} objects
	 * contained in the input transition hold 
	 * @param trans
	 * @return true or false
	 */
	protected boolean MMLDRulePrecsHold(MMLDGlobalTransition trans) {
		Collection<YAMLDComponent> components = trans.affectedComponents();
		for (YAMLDComponent comp : components) {
			MMLDRule rule = trans.getRule(comp);
			YAMLDFormula prec = rule.getCondition();
			if (!prec.satisfied(this, comp))
				return false;
		}
		return true;
	}

	/**
	 * Checks if the preconditions of the {@link MMLDTransition} objects
	 * contained in the input transition hold.
	 * @param trans
	 * @return
	 */
	protected boolean MMLDTransPrecsHold(MMLDGlobalTransition trans) {
		// Tests only the transitions not triggered by some event.  
		final Collection<YAMLDComponent> mmldcomps = trans.triggeringComponents();
		
		for (final YAMLDComponent comp: mmldcomps) {
			final MMLDTransition tr = trans.getRule(comp).getTransition();
			if (tr.getPreconditions().isEmpty() && tr.getTriggeringEvents().isEmpty()) {
				// No need to test: spontaneous transition
				continue;
			}
			// Try to find one satisfying assignment for trans.
			boolean sat = false;
			for (final YAMLDFormula f: tr.getPreconditions()) {
				if (f.satisfied(this, comp)) {
					sat = true;
					break;
				}
			}
			if (!sat)
				return false;
		}
		return true;
	}
}
