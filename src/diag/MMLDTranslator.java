package diag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.InclusionOrdering;

import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.MMLDTransition;
import lang.Network;
import lang.Path;
import lang.State;
import lang.PeriodInterval;
import lang.VariableValue;
import lang.YAMLDAndFormula;
import lang.YAMLDAssignment;
import lang.YAMLDComponent;
import lang.YAMLDConstraint;
import lang.YAMLDDVar;
import lang.YAMLDEqFormula;
import lang.YAMLDEvent;
import lang.YAMLDExistsPath;
import lang.YAMLDExpr;
import lang.YAMLDFalse;
import lang.YAMLDFormula;
import lang.YAMLDGenericVar;
import lang.YAMLDID;
import lang.YAMLDNotFormula;
import lang.YAMLDOrFormula;
import lang.YAMLDStringExistsPath;
import lang.YAMLDTrue;
import lang.YAMLDValue;
import lang.YAMLDVar;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.ClausePruner;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableSemantics;
import util.Time;

/**
 * An implementation that translates the system model into a SAT problem.  
 * This implementation should eventually replace the {@link Translator} class.
 * 
 * @author Alban Grastien
 * */
public class MMLDTranslator {
	
	private final Map<Pair<YAMLDFormula,YAMLDComponent>,Integer> _formulaBuffer = 
		new HashMap<Pair<YAMLDFormula,YAMLDComponent>, Integer>();
	
	private final Map<Pair<Set<YAMLDComponent>,YAMLDFormula>,Integer> _setFormulas = 
		new HashMap<Pair<Set<YAMLDComponent>,YAMLDFormula>, Integer>();

	private Network _net;
	
	private Map<YAMLDFormula,InclusionOrdering<YAMLDComponent>> _ios;

	public MMLDTranslator(Map<YAMLDFormula,InclusionOrdering<YAMLDComponent>> ios) {
		_ios = ios;
	}
	
	/**
	 * Translates the network in a SAT problem.  
	 * 
	 * @param out the clause stream where the clauses generated are to be stored.  
	 * @param varass the assigner that returns the SAT variables associated with the problem.  
	 * @param net the network which must be translated.  
	 * @param maxTime the last time step (start with 0).  
	 * */
	public void translateNetwork(ClauseStream out, LiteralAssigner varass, Network net, int maxTime) {
	  // store the network that we're translating in the local variable _net; I suspect
	  // that's what Alban intended, but didn't do? (it's needed to make the calls to
	  // simplify work)
	  _net = net;
		{
			final ClauseStream stateOut = new ClausePruner(varass.getShiftedStream(out, maxTime+1));
			oneState(varass, stateOut, net);
			constraintsHold(varass, stateOut, net);
		}
		
		{
			final ClauseStream transOut = new ClausePruner(varass.getShiftedStream(out, maxTime));
			rulesImplyPrecondition(varass, transOut, net);
			rulesGenerateEvent(varass, transOut, net);

			rulesImplyEffect(varass, transOut, net);
			stateAssignmentChangeImpliesRule(varass, transOut, net);
			
			outputEventsImplyRule(varass, transOut, net);
			inputEventsImplyTransition(varass, transOut, net);
			synchros(varass, transOut, net);
			
			rulesImplyTransition(varass, transOut, net);
			noRuleImpliesNoPrecondition(varass, transOut, net);
			transitionTriggeringConditionsAreSatisfied(varass, transOut, net);
			noTwoRulesForAnyTransition(varass, transOut, net);
			transitionTriggersImplyTransition(varass,transOut,net);
			
			noTwoTransitionsOnTheSameComponent(varass, transOut, net);
		}
	}

	/**
	 * Creates the list of clauses that ensures that no two rules 
	 * of the specified transition are triggered at the time t = 0.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param tr the transition for which the clauses are generated.  
	 * */
	public void noTwoRulesForTransition(VariableAssigner varass, ClauseStream out, MMLDTransition tr) {
		final List<MMLDRule> rules = tr.getRules();
		for (int i=0 ; i<rules.size() ; i++) {
			final MMLDRule r1 = rules.get(i);
			final int lit1 = varass.getVariable(new RuleTrigger(0, r1));
			for (int j=i+1 ; j < rules.size() ; j++) {
				final MMLDRule r2 = rules.get(j);
				final int lit2 = varass.getVariable(new RuleTrigger(0, r2));
				out.put(-lit1, -lit2);
			}
		}
	}

	/**
	 * Creates the list of clauses that ensures that 
	 * if a {@link PreconditionTriggersTransition} is true for this transition, 
	 * then this transition indeed triggers.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param tr the transition for which the clauses are generated.  
	 * */
	public void transitionTriggerImpliesTransition
	(VariableAssigner varass, ClauseStream out, MMLDTransition tr) {
		final int trLit;
		{
			final VariableSemantics sem = new MMLDTransitionTrigger(tr, 0);
			trLit = varass.getVariable(sem);
		}
		for (final YAMLDFormula prec: tr.getPreconditions()) {
			final VariableSemantics sem = new PreconditionTriggersTransition(tr, prec, 0);
			final int precLit = varass.getVariable(sem);
			out.put(-precLit, trLit);
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that no two rules 
	 * of any transition are triggered at the time t = 0.
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network of components over which the constraint is defined.  
	 * */
	public void noTwoRulesForAnyTransition(VariableAssigner varass, ClauseStream out, Network n) {
		for (final YAMLDComponent comp: n.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				noTwoRulesForTransition(varass, out, trans);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that when a {@link PreconditionTriggersTransition} 
	 * is true, the corresponding transition is triggered.
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network of components over which the constraint is defined.  
	 * */
	public void transitionTriggersImplyTransition
	(VariableAssigner varass, ClauseStream out, Network n) {
		for (final YAMLDComponent comp: n.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				transitionTriggerImpliesTransition(varass, out, trans);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures 
	 * that the precondition of the specified rule is satisfied at time t=0
	 * in case the rule is triggered at time t=0.
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param r the rule that is triggered.  
	 * */
	public void ruleImpliesPrecondition(VariableAssigner varass, ClauseStream out, MMLDRule r) {
		final int ruleLit = varass.getVariable(new RuleTrigger(0, r));
		final YAMLDFormula prec = r.getCondition();
		final int precLit = formulaHolds(varass,out,prec,r.getComponent());
		out.put(-ruleLit, precLit);
	}
	
	/**
	 * Creates the list of clauses that ensures 
	 * that the precondition of any rule is satisfied at time 
	 * in case the rule triggered at time t=0.
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network of components over which the constraint is defined.  
	 * */
	public void rulesImplyPrecondition(VariableAssigner varass, ClauseStream out, Network n) {
		for (final YAMLDComponent comp: n.getComponents()) {
			// System.out.println(comp.toString());
			for (final MMLDTransition trans: comp.transitions()) {
				// System.out.println(trans.toFormattedString());
				for (final MMLDRule rule: trans.getRules()) {
					ruleImpliesPrecondition(varass, out, rule);
				}
			}
		}
	}
	
//	/**
//	 * Creates the list of clauses that ensures 
//	 * the default rule of the specified transition is triggered only when no rule is possible.  
//	 * 
//	 * @param varass the variable assigner that indicates 
//	 * the value of the SAT variables.  
//	 * @param out the clause stream where the generated clauses will be stored.  
//	 * @param tr the transition for which these clauses are defined.  
//	 * */
//	public void defaultRuleImpliesNoPreconditionSatisfied(VariableAssigner varass, ClauseStream out, MMLDTransition tr) {
//		Collection<Integer> defaultRule = new ArrayList<Integer>();
//		
//		for (final MMLDRule r: tr.getRules()) {
//			final RuleTrigger rt = new RuleTrigger(0, r);
//			final int lit = varass.getVariable(rt);
//			defaultRule.add(lit);
//		}
//		
//		for (final MMLDRule r: tr.getRules()) {
//			final YAMLDFormula f = r.getCondition();
//			final int lit = formulaHolds(varass, out, f, r.getComponent());
//			final Collection<Integer> clause = new ArrayList<Integer>(defaultRule);
//			clause.add(-lit);
//			out.put(clause);
//		}
//	}
//	
//	/**
//	 * Creates the list of clauses that ensures 
//	 * the default rule of any transition in the specified network 
//	 * is triggered only when no rule is possible.  
//	 * 
//	 * @param varass the variable assigner that indicates 
//	 * the value of the SAT variables.  
//	 * @param out the clause stream where the generated clauses will be stored.  
//	 * @param n the network for which the clauses are generated.  
//	 * */
//	public void defaultRulesImplyNoPreconditionSatisfied(VariableAssigner varass, ClauseStream out, Network n) {
//		for (final YAMLDComponent c: n.getComponents()) {
//			for (final MMLDTransition t: c.transitions()) {
//				defaultRuleImpliesNoPreconditionSatisfied(varass, out, t);
//			}
//		}
//	}
	
	/**
	 * Creates the list of clauses that ensures the triggering 
	 * of the specified transition rule at time t=0 
	 * produces the effects at time t=1 of the specified rule.  
	 * At the moment, it is assumed the effect is defined statically.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param r the rule for which the clauses are generated.  
	 * */
	public void ruleImpliesEffect(VariableAssigner varass, ClauseStream out, MMLDRule r) {
		final int ruleLit = varass.getVariable(new RuleTrigger(0, r));
		for (final YAMLDAssignment ass: r.getAssignments()) {
			final YAMLDGenericVar var = ass.variable();
			final YAMLDValue val = (YAMLDValue)ass.expression();
			final int assLit = varass.surelyGetVariable(new Assignment(var, val, 1));
            out.put(-ruleLit, assLit);
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that the triggering 
	 * on any transition rule of the network at time t=0
	 * produces the effects at time t=1 of the rule.  
	 * At the moment, it is assumed the effect is defined statically.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.  
	 * */
	public void rulesImplyEffect(VariableAssigner varass, ClauseStream out, Network n) {
		for (final YAMLDComponent comp: n.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				for (final MMLDRule rule: trans.getRules()) {
					ruleImpliesEffect(varass, out, rule);
				}
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that the triggering 
	 * of the specified rule generates the events associated with the rule.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param r the rule for which the clauses are generated.  
	 * */
	public void ruleGeneratesEvent(VariableAssigner varass, ClauseStream out, MMLDRule r) {
		final int ruleLit = varass.getVariable(new RuleTrigger(0, r));
		for (final YAMLDEvent e: r.getGeneratedEvents()) {
			final int eventLit = varass.getVariable(new EventOccurrence(e, 0));
			if (eventLit != 0) { // In case no SAT variable is created for the event.  
				out.put(-ruleLit, eventLit);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that the triggering 
	 * of any rule generates the events associated with the rule.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.  
	 * */
	public void rulesGenerateEvent(VariableAssigner varass, ClauseStream out, Network n) {
		for (final YAMLDComponent comp: n.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				for (final MMLDRule rule: trans.getRules()) {
					ruleGeneratesEvent(varass, out, rule);
				}
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that a rule of the specified transition 
	 * is triggered only when the transition is triggered as well.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param tr the transition for which the clauses are generated.    
	 * */
	public void rulesImplyTransition(VariableAssigner varass, ClauseStream out, MMLDTransition tr) {
		final int transLit = varass.getVariable(new MMLDTransitionTrigger(tr, 0));
		for (final MMLDRule r: tr.getRules()) {
			final int ruleLit = varass.getVariable(new RuleTrigger(0, r));
			out.put(-ruleLit,transLit);
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that a rule of any transition 
	 * is triggered only when the transition is triggered as well.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.    
	 * */
	public void rulesImplyTransition(VariableAssigner varass, ClauseStream out, Network n) {
		for (final YAMLDComponent comp: n.getComponents()) {
			for (final MMLDTransition tr: comp.transitions()) {
				rulesImplyTransition(varass, out, tr);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures 
	 * that the specified transition generates no rule 
	 * only if no rule precondition is satisfied.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param tr the transition for which the clauses are generated.  
	 * */
	public void noRuleImpliesNoPrecondition(VariableAssigner varass, 
			ClauseStream out, MMLDTransition tr) {
		// Let r1,...,rn be the set of rules. 
		// If tr is triggered and none of the ri is triggered, 
		// then the precondition of rj is unsatisfied (for all rj)
		
		// partialClause contains the if statement above.  
		final List<Integer> partialClause = new ArrayList<Integer>();
		{
			final int trLit = varass.getVariable(new MMLDTransitionTrigger(tr, 0));
			partialClause.add(-trLit);
		}
		for (final MMLDRule rule: tr.getRules()) {
			final int ruleLit = varass.getVariable(new RuleTrigger(0, rule));
			partialClause.add(ruleLit);
		}
		
		// the then statement
		for (final MMLDRule rule: tr.getRules()) {
			final int precLit = formulaHolds(varass, out, rule.getCondition(),rule.getComponent());
			final List<Integer> clause = new ArrayList<Integer>(partialClause);
			clause.add(-precLit);
			out.put(clause);
		}
	}
	
	/**
	 * Creates the list of clauses that ensures 
	 * that any transition generates no rule 
	 * only if no rule precondition is satisfied.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.  
	 * */
	public void noRuleImpliesNoPrecondition(VariableAssigner varass, 
			ClauseStream out, Network n) {
		for (final YAMLDComponent c: n.getComponents()) {
			for (final MMLDTransition tr: c.transitions()) {
				noRuleImpliesNoPrecondition(varass, out, tr);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that the transitions
	 * in the specified network  
	 * are triggered only if one of the triggering event takes place 
	 * or if one of its precondition is satisfied.  
	 * Note that, by definition, if the set of triggering events 
	 * and the set of preconditions are empty, 
	 * then the transition can spontaneously take place.  
	 * Furthermore, this method does not test 
	 * that the precondition was satisfied for at least the minimum; 
	 * only the state before the transition is tested.
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param net the network for which the clauses are generated.  
	 * */
	public void transitionTriggeringConditionsAreSatisfied(VariableAssigner varass, 
			ClauseStream out, Network net) {
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final MMLDTransition tr: comp.transitions()) {
				transitionTriggeringConditionIsSatisfied(varass, out, tr);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that the specified transition 
	 * is triggered only if one of the triggering event takes place 
	 * or if one of its precondition is satisfied.  
	 * Note that, by definition, if the set of triggering events 
	 * and the set of preconditions are empty, 
	 * then the transition can spontaneously take place.  
	 * Furthermore, this method does not test 
	 * that the precondition was satisfied for at least the minimum; 
	 * only the state before the transition is tested.
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param tr the transition for which the clauses are generated.  
	 * */
	public void transitionTriggeringConditionIsSatisfied(VariableAssigner varass, 
			ClauseStream out, MMLDTransition tr) {
		if (tr.isSpontaneous()) {
			return;
		}
		
		final List<Integer> clause = new ArrayList<Integer>();
		{
			final int transLit = varass.getVariable(new MMLDTransitionTrigger(tr, 0));
			clause.add(-transLit);
		}
		
		for (final YAMLDFormula prec: tr.getPreconditions()) {
			final int precLit = formulaHolds(varass, out, prec, tr.getComponent());
			final int pttLit = varass.getVariable(new PreconditionTriggersTransition(tr, prec, 0));
			clause.add(pttLit);
			out.put(-pttLit, precLit);
		}
		
		for (final YAMLDEvent evt: tr.getTriggeringEvents()) {
			final int evtLit = varass.getVariable(new EventOccurrence(evt, 0));
			clause.add(evtLit);
		}
		
		out.put(clause);
	}
	
	/**
	 * Creates the list of clauses that ensures the specified event 
	 * takes place only if a rule generates it (provided the event is an output).  
	 * If the event is an input event, this method does nothing.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param e the event for which the clauses are generated.  
	 * */
	public void outputEventImpliesRule(VariableAssigner varass, 
			ClauseStream out, YAMLDEvent e) {
		if (e.isInput()) {
			return;
		}
		
		final Collection<MMLDRule> rules = e.getGeneratingRules();
		final List<Integer> clause = new ArrayList<Integer>();
		{
			final int evtLit = varass.getVariable(new EventOccurrence(e, 0));
			clause.add(-evtLit);
		}
		
		for (final MMLDRule r: rules) {
			final int rleLit = varass.getVariable(new RuleTrigger(0, r));
			clause.add(rleLit);
		}
		
		out.put(clause);
	}
	
	/**
	 * Creates the list of clauses that ensures any event 
	 * take place only if a rule generates it 
	 * (provided the event is an output event).  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.
	 * */
	public void outputEventsImplyRule(VariableAssigner varass, 
			ClauseStream out, Network n) {
		for (final YAMLDComponent c: n.getComponents()) {
			for (final YAMLDEvent e: c.events()) {
				outputEventImpliesRule(varass, out, e);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures the specified event 
	 * triggers one of the transitions it is defined for 
	 * (provided the event is an input event).  
	 * If the event is an output event, the method does nothing.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param e the event for which the clauses are generated.
	 * */
	public void inputEventImpliesTransition(VariableAssigner varass, 
			ClauseStream out, YAMLDEvent e) {
		if (!e.isInput()) {
			return;
		}
		
		final Collection<MMLDTransition> transes = e.getTriggerableTransitions();
		final List<Integer> clause = new ArrayList<Integer>();
		{
			final int evtLit = varass.getVariable(new EventOccurrence(e, 0));
			clause.add(-evtLit);
		}
		
		for (final MMLDTransition tr: transes) {
			final int trLit = varass.getVariable(new MMLDTransitionTrigger(tr, 0));
			clause.add(trLit);
		}
		
		out.put(clause);
	}
	
	/**
	 * Creates the list of clauses that ensures any event 
	 * triggers one of the transitions it is defined for 
	 * (provided the event is an input event).  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.
	 * */
	public void inputEventsImplyTransition(VariableAssigner varass, 
			ClauseStream out, Network n) {
		for (final YAMLDComponent c: n.getComponents()) {
			for (final YAMLDEvent e: c.events()) {
				inputEventImpliesTransition(varass, out, e);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that the value 
	 * associated with the specified state variables changes 
	 * only as a consequence of transition occurring.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param var the variable for which the clauses are generated.  
	 * */
	public void stateAssignmentChangeImpliesRule(VariableAssigner varass, 
			ClauseStream out, YAMLDVar var) {
		// Writes a partial clause that says one of the rules has to be satisfied.  
		final List<Integer> partialClause;
		{
			final List<Integer> tmp = new ArrayList<Integer>();
			final Collection<MMLDRule> rules = var.getAffectingRules();
			for (final MMLDRule r: rules) {
				final int rleLit = varass.getVariable(new RuleTrigger(0, r));
				tmp.add(rleLit);
			}
			partialClause = Collections.unmodifiableList(tmp); 
			// partialClause is made unmodif to make sure the rest of the code 
			// does not screw things up by modifying partialClause. 
		}
		
		// Creates the clause for each possible value.  
//		if (!var.domain().isEmpty()) {
			for (final YAMLDValue val: var.domain()) {
				final List<Integer> clause = new ArrayList<Integer>(partialClause);
				{
					final int varLit = varass.getVariable(new Assignment(var, val, 0));
					clause.add(varLit);
				}
				{
					final int primeVarLit = varass.getVariable(new Assignment(var, val, 1));
					clause.add(-primeVarLit);
				}
				out.put(clause);
			}
//		} else {
//			final int max = var.getRangeEnd();
//			final int min = var.getRangeInit();
//			for (int i = min ; i <= max ; i++) {
//				final YAMLDValue val = var.getValue(i);
//				final List<Integer> clause = new ArrayList<Integer>(partialClause);
//				{
//					final int varLit = varass.getVariable(new Assignment(var, val, 0));
//					clause.add(varLit);
//				}
//				{
//					final int primeVarLit = varass.getVariable(new Assignment(var, val, 1));
//					clause.add(-primeVarLit);
//				}
//				out.put(clause);
//			}
//		}
	}
	
	/**
	 * Creates the list of clauses that ensures that the value 
	 * associated with any state variables changes 
	 * only as a consequence of transition occurring.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.  
	 * */
	public void stateAssignmentChangeImpliesRule(VariableAssigner varass, 
			ClauseStream out, Network n) {
		for (final YAMLDComponent c: n.getComponents()) {
			for (final YAMLDVar v: c.vars()) {
				stateAssignmentChangeImpliesRule(varass, out, v);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that no two {@link MMLDTransition}
	 * are triggered on the specified component at the same time.  
	 * Note: the implementation might be improved by factorising 
	 * this cardinality constraint.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param c the component for which the clauses are generated.
	 * */
	public void noTwoTransitionsOnTheSameComponent(VariableAssigner varass, 
			ClauseStream out, YAMLDComponent c) {
		final List<MMLDTransition> transes = new ArrayList<MMLDTransition>(c.transitions());
		for (int i = 0 ; i<transes.size() ; i++) {
			final int lit1 = varass.getVariable(new MMLDTransitionTrigger(transes.get(i), 0));
			for (int j = i+1 ; j<transes.size() ; j++) {
				final int lit2 = varass.getVariable(new MMLDTransitionTrigger(transes.get(j), 0));
				out.put(-lit1,-lit2);
			}
		}
	}
	
	/**
	 * Creates the list of clauses that ensures that no two {@link MMLDTransition}
	 * are triggered on any component at the same time.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param n the network for which the clauses are generated.
	 * */
	public void noTwoTransitionsOnTheSameComponent(VariableAssigner varass, 
			ClauseStream out, Network n) {
		for (final YAMLDComponent c: n.getComponents()) {
			noTwoTransitionsOnTheSameComponent(varass, out, c);
		}
	}
	
	
	// TODO: 
	// Transition --> triggering event OR precondition satisfied ***for at least min time***
	// Precondition satisfied for more than max time --> transition
	// Is that all?
	
	
	/**
	 * Returns the literal that ensures the specified formula is satisfied at time 0.  
	 * If necessary (if the formula is new), clauses are generated 
	 * to ensure the returned literal has the correct semantics.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param f the formula.  
	 * @param c the component on which the formula is defined.  
	 * @return a literal that is true if the specified formula is true at time 0.  
	 * */
	public int formulaHolds(VariableAssigner varass, ClauseStream out, 
			YAMLDFormula f, YAMLDComponent c) {
		{
			final Integer bufferedResult = _formulaBuffer.get(
					new Pair<YAMLDFormula, YAMLDComponent>(f, c));
			if (bufferedResult != null) {
				return bufferedResult.intValue();
			}
		}
		
		final int result = unbufferedFormulaHolds(varass, out, f, c);
		_formulaBuffer.put(
				new Pair<YAMLDFormula, YAMLDComponent>(f, c), result);
		return result;
	}

	public int formulaHoldsForAll(VariableAssigner varass, ClauseStream out, 
			YAMLDFormula f, Set<YAMLDComponent> comps) {
		if (comps.isEmpty()) {
			return formulaHolds(varass, out, YAMLDTrue.TRUE, null);
		}
		
		if (comps.size() == 1) {
			final YAMLDComponent comp = comps.iterator().next();
			return formulaHolds(varass, out, f, comp);
		}
		
		final Pair<Set<YAMLDComponent>,YAMLDFormula> pair = 
				new Pair<Set<YAMLDComponent>, YAMLDFormula>(comps, f);
			
		{
			final Integer result = _setFormulas.get(pair);
			if (result != null) {
				return result.intValue();
			}
		}
		
		final VariableSemantics pps = new AllSatisfy(0, comps, f);
		final int setLit = varass.surelyGetVariable(pps);
		_setFormulas.put(pair, setLit);
		
		// Recursive calls
		// The formula holds on comps iff it holds on all its direct children
		final List<Integer> clause = new ArrayList<Integer>();
		clause.add(setLit);
		final InclusionOrdering<YAMLDComponent> io = _ios.get(f);
		for (final Set<YAMLDComponent> child: io.directChildren(comps)) {
			final int childLit = formulaHoldsForAll(varass, out, f, child);
			out.put(-setLit, childLit);
			clause.add(-childLit);
		}
//		for (final YAMLDComponent child: comps) {
//			final int childLit = formulaHolds(out, varass, net, f, child);
//			out.put(-setLit, childLit);
//			clause.add(-childLit);
//		}
		out.put(clause);

		return setLit;
	}

	// Does compute and generate the literal for the specified precondition.  
	private int unbufferedFormulaHolds(VariableAssigner varass, ClauseStream out, 
			YAMLDFormula f, YAMLDComponent c) {
		if (f instanceof YAMLDTrue) {
			final FormulaHolds fh = new FormulaHolds(f, null, 0);
			final int result = varass.surelyGetVariable(fh);
			out.put(result);
			return result;
		}
		
		if (f instanceof YAMLDFalse) {
			final FormulaHolds fh = new FormulaHolds(f, null, 0);
			final int result = varass.surelyGetVariable(fh);
			out.put(-result);
			return result;
		}
		

		// Assuming the equality is defined like this: var = val
		if (f instanceof YAMLDEqFormula) {
			final YAMLDEqFormula eq = (YAMLDEqFormula)f;

			YAMLDExpr expr1 = eq.expr1();
			YAMLDExpr expr2 = eq.expr2();

			if (!(expr2 instanceof YAMLDValue)) {
			  System.out.println("in component: " + c.name());
			  System.out.println("error: " + expr2.toString()
					     + " in " + f.toString()
					     + " is not value");
			}
			final YAMLDValue val = (YAMLDValue)expr2;

			if (expr1 instanceof VariableValue) {
				final YAMLDGenericVar var = ((VariableValue)expr1).variable();
				return varass.surelyGetVariable(new Assignment(var, val, 0));
			}
			
			if (expr1 instanceof YAMLDID) {
			        final YAMLDGenericVar var = ((YAMLDID)expr1).variable(c, _net);
				return varass.surelyGetVariable(new Assignment(var, val, 0));
			}
			
			if (expr1 instanceof YAMLDValue) {
				// TODO: What if YAMLDTrue and YAMLDFalse are not used????
				if (expr1 == expr2) {
					return formulaHolds(varass, out, YAMLDTrue.TRUE, c);
				} else {
					return formulaHolds(varass, out, YAMLDFalse.FALSE, c);
				}
			}
			
			throw new IllegalArgumentException("Cannot deal with expression " 
					+ expr1.toFormattedString());  
		}

		if (f instanceof YAMLDNotFormula) {
			final YAMLDFormula neg = ((YAMLDNotFormula)f).getOp();
			return -formulaHolds(varass, out, neg, c);
		}
		
		if (f instanceof YAMLDOrFormula) {
			final YAMLDOrFormula or = (YAMLDOrFormula)f;
			final YAMLDFormula f1 = or.getOp1();
			final YAMLDFormula f2 = or.getOp2();
			final int lit1 = formulaHolds(varass, out, f1, c);
			final int lit2 = formulaHolds(varass, out, f2, c);
			final int lit = varass.surelyGetVariable(new FormulaHolds(f, c, 0));
			out.put(-lit1, lit);
			out.put(-lit2, lit);
			out.put(-lit, lit1, lit2);
			return lit;
		}
		
		if (f instanceof YAMLDAndFormula) {
			final YAMLDAndFormula and = (YAMLDAndFormula)f;
			final YAMLDFormula f1 = and.getOp1();
			final YAMLDFormula f2 = and.getOp2();
			final int lit1 = formulaHolds(varass, out, f1, c);
			final int lit2 = formulaHolds(varass, out, f2, c);
			final int lit = varass.surelyGetVariable(new FormulaHolds(f, c, 0));
			out.put(lit1, -lit);
			out.put(lit2, -lit);
			out.put(lit, -lit1, -lit2);
			return lit;
		}
		
		
		if (f instanceof YAMLDStringExistsPath || f instanceof YAMLDExistsPath) {
			final YAMLDExistsPath ex;
			if (f instanceof YAMLDExistsPath) {
				ex = (YAMLDExistsPath)f;
			} else {
				ex = ((YAMLDStringExistsPath)f).simplify(_net);
			}
			
			final VariableSemantics sem = new FormulaHolds(f, c, 0);
			
			final Collection<Path> pathes = ex.getPathes();
			final Collection<Integer> pathLits = new ArrayList<Integer>(pathes.size());
			final int lit = varass.surelyGetVariable(sem);
			pathLits.add(-lit);

			for (final Path path: pathes) {
				final YAMLDFormula f2 = ex.getCondition();
				//final Path newPath = path;
				final Set<YAMLDComponent> newPath = path.simplify(_net, f2);
				if (newPath == null) {
					continue;
				}
				final int pathLit = formulaHoldsForAll(varass, out, f2, newPath);
				pathLits.add(pathLit);
				out.put(-pathLit,lit);
			}
			out.put(pathLits);
			
			return lit;
		}
		
		throw new UnsupportedOperationException(
				"YAMLDFormula type not supported: " + f.getClass().getName());
	}
	
	public void oneAssignmentForVariable(VariableAssigner varass, ClauseStream out, YAMLDGenericVar var) {
		if (!_net.isRelevant(var)) {
			return;
		}
		
		final int[] lits; // the literal for each assignment
		{
			final List<YAMLDValue> domain = var.domain();
			if (!domain.isEmpty()) {
				lits = new int[domain.size()];
				for (int i = 0; i < domain.size(); i++) {
					lits[i] = varass.surelyGetVariable(
							new Assignment(var, domain.get(i), 0));
				}
			} else {
				final int min = var.getRangeInit();
				final int max = var.getRangeEnd();
				final int size = max - min +1;
				lits = new int[size];
				for (int i=0 ; i<size ; i++) {
					lits[i] = varass.surelyGetVariable(
							new Assignment(var, var.getValue(min + i), 0));
				}
			}
		}
		// At least one value
		out.put(lits);
		// At most one value
		for (int i = 0; i < lits.length; i++) {
			final int lit1 = lits[i];
			for (int j = i+1 ; j < lits.length ; j++) {
				final int lit2 = lits[j];
				out.put(-lit1, -lit2);
			}
		}
	}
	
	public void oneState(
			VariableAssigner varass, ClauseStream out, Network net) {
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final YAMLDGenericVar var : comp.vars()) {
				oneAssignmentForVariable(varass, out, var);
			}
			// TODO: Is it really necessary?  If not, is it useful?  
			for (final YAMLDGenericVar var : comp.dvars()) {
				oneAssignmentForVariable(varass, out, var);
			}
		}
	}

	/**
	 * Creates the SAT clauses that ensure that the constaints on the network 
	 * are satisfied.  In order word, this ensures the computation of the dependent variables.   
	 * 
	 * @param out the clause stream where the clauses are stored.    
	 * @param varass the assigner that returns the SAT variables associated with the problem.  
	 * @param net the network for which the clauses must be generated.  
	 * */
	public void constraintsHold(
			VariableAssigner varass, ClauseStream out, Network net) {
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final YAMLDDVar var: comp.dvars()) {
//				if (!net.isRelevant(var)) {
//					continue;
//				}
				for (final YAMLDConstraint c: var.getConstraints()) {
					constraintHolds(varass, out, net, c, comp);
				}
			}
		}
	}
	
	public void constraintHolds(VariableAssigner varass, ClauseStream out, 
			Network net, YAMLDConstraint con, YAMLDComponent comp) {
		final YAMLDGenericVar var = con.getVariable();
		if (!_net.isRelevant(var)) {
			return;
		}
		
		final int preLit = formulaHolds(varass, out, con.getPrecondition(), comp);
		final YAMLDValue val = (YAMLDValue)con.getAssignment();
		final int postLit = varass.surelyGetVariable(
				new Assignment(var, val, 0));
        
		out.put(-preLit, postLit);
	}
	
	/**
	 * Creates the list of clauses that ensures that the synchronisations 
	 * associated with the specified network are satisfied.  
	 * 
	 * @param varass the variable assigner that indicates 
	 * the value of the SAT variables.  
	 * @param out the clause stream where the generated clauses will be stored.  
	 * @param net the network for which the clauses are generated.  
	 * */
	public void synchros(VariableAssigner varass, ClauseStream out, Network net) {
		for (final YAMLDComponent c: net.getComponents()) {
			for (final YAMLDEvent e: c.events()) {
				synchro(varass, out, e);
			}
		}
	}
	
	public void synchro(VariableAssigner varass, ClauseStream out, YAMLDEvent evt) {
		final Collection<MMLDSynchro> synchros = evt.getSynchros();
		
		if (evt.isInput()) {
			// evt takes place only if one of the output event of some synchro take place
			
			final List<Integer> clause = new ArrayList<Integer>();
			for (final MMLDSynchro s: synchros) {
				final YAMLDEvent outputEvt = s.getEvent();
				final int evtLit = varass.getVariable(new EventOccurrence(outputEvt, 0));
				clause.add(evtLit);
			}
			
			// Cannot have two events synchro with evt at the same time 
			// (conflict)
			for (int i= 0 ; i < clause.size() ; i++) {
				for (int j= i+1 ; j < clause.size() ; j++) {
					out.put(-clause.get(i), -clause.get(j));
				}
			}
			
			{
				final int evtLit = varass.getVariable(new EventOccurrence(evt, 0));
				clause.add(-evtLit);
			}
			out.put(clause);
		} else {
			// if evt takes place, all input events of any synchro take place
			final int evtLit1 = varass.getVariable(new EventOccurrence(evt, 0));
			
			for (final MMLDSynchro s: synchros) {
				for (final YAMLDEvent inputEvt: s.getSynchronizedEvents()) {
					final int evtLit2 = varass.getVariable(new EventOccurrence(inputEvt, 0));
					out.put(-evtLit1,evtLit2);
				}
			}
		}
	}


	/**
	 * Creates the clauses that forces the specified state at time step 0.  
	 * 
	 * @param out the clause stream where the clauses are stored.    
	 * @param varass the assigner that returns the SAT variables associated with the problem.  
	 * @param state the state that should be enforced.  
	 * */
	public void state(VariableAssigner varass, ClauseStream out, State state) {
		final Network net = state.getNetwork();
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final YAMLDVar var: comp.vars()) {
				// System.out.println(var.toFormattedString());
				final YAMLDValue val = state.getValue(var);
				if (val == null) {
				  // I don't think this is necessary:
				  // oneAssignmentForVariable(varass, out, var);
				}
				else {
				  final VariableSemantics sem = new Assignment(var, val, 0);
				  out.put(varass.surelyGetVariable(sem));
				  // System.out.println(sem.toString());
				}
			}
		}
	}

	/**
	 * Creates the clauses that ensures the observations generated by the scenario 
	 * returned by the SAT solver are consistent with the observation 
	 * of the specified observation at the specified time.
	 * 
	 * @param out the clause stream where the clauses are stored.    
	 * @param varass the assigner that returns the SAT variables associated with the problem.  
	 * @param observable the list of events that are observable.  
	 * @param observed the list of events observed at the specified time 
	 * (other observable events did not take place).  
	 * @param time the time step when the observation was made.  
	 * */
	public void observationAtTime(VariableAssigner varass, ClauseStream out, 
			Collection<YAMLDEvent> observable, Collection<YAMLDEvent> observed, int time) {
		for (final YAMLDEvent event: observable) {
			final VariableSemantics sem = new EventOccurrence(event, time);
			final int var = varass.surelyGetVariable(sem);
			final int lit;
			if (observed.contains(event)) {
				//System.out.println("Setting observed: " + event.toFormattedString() + "@" + time);
				lit = var;
			} else {
				//System.out.println("Setting unobserved: " + event.toFormattedString() + "@" + time);
				lit = -var;
			}
			out.put(lit);
		}
	}

	/**
	 * Creates the clauses that ensure the given set of observations
	 * (and no others) are observed in the time interval [t0,t0+l]
	 * (inclusive).
	 * 
	 * @param out the clause stream where the clauses are stored.
	 * @param varass the assigner that returns the SAT variables
	 *  associated with the problem.  
	 * @param observable the list of events that are observable. 
	 * @param observed the list of observed events.
	 * @param t0 time step where interval begins.
	 * @param l length of the time interval
	 * */
	public void observationsInInterval
	  (VariableAssigner varass,
	   ClauseStream out, 
	   Collection<YAMLDEvent> observable,
	   Collection<YAMLDEvent> observed,
	   int t0,
	   int l)
        {
	  for (final YAMLDEvent event: observable) {
		  // System.out.println("Event: " + event.toFormattedString());
	    int vars[] = new int[l];
	    for (int k = 0; k < l; k++) {
	      final VariableSemantics sem = new EventOccurrence(event, t0+k);
	      //System.out.println("Semantics: " + sem);
	      //System.out.println("varass: " + varass);
	      vars[k] = varass.surelyGetVariable(sem);
	      // System.out.println("vars[k] = " + vars[k]);
	    }
	    if (observed.contains(event)) {
	      // event observed: must happen exactly once in the interval
	      // at least once:
	      List<Integer> clause = new ArrayList<Integer>();
	      for (int k = 0; k < l; k++)
		clause.add(vars[k]);
	      //System.out.println(clause.toString());
	      out.put(clause);
	      // at most once: -e@k | -e@j, for each k != j
	      for (int k = 0; k < l; k++)
		for (int j = k + 1; j < l; j++) {
		  out.put(-vars[k], -vars[j]);
	      // System.out.println("-" + vars[k] + " -" + vars[j]);
		}
	    }
	    else {
	      // event not observed: just put -e@t for each t in interval
	      for (int k = 0; k < l; k++) {
		int lit = -vars[k];
		out.put(lit);
	      }
	    }
	  }
	}

	/**
	 * Creates a list of clauses that ensures no two events 
	 * from the specified set take place at the same time step.  
	 * 
	 * @param varass the variable assigner that indicates the SAT variable 
	 * associated with each event occurrence.  
	 * @param out the output stream where the clauses should be stored.  
	 * @param events the list of events.  
	 * @param n the number of time steps.  
	 * */
	public void noSimultaneousOccurrence(LiteralAssigner varass,
			   ClauseStream out, 
			   Collection<YAMLDEvent> events, 
			   int n) {
		final ClauseStream transOut = new ClausePruner(varass.getShiftedStream(out, n));
		
		final List<YAMLDEvent> list = new ArrayList<YAMLDEvent>(events);
		for (int i=0 ; i<list.size() ; i++) {
			final int lit1;
			{
				final YAMLDEvent event = list.get(i);
				final VariableSemantics sem = new EventOccurrence(event, 0);
				lit1 = varass.surelyGetVariable(sem);
			}
			for (int j=i+1 ; j<list.size() ; j++) {
				final YAMLDEvent event = list.get(j);
				final VariableSemantics sem = new EventOccurrence(event, 0);
				final int lit2 = varass.surelyGetVariable(sem);
				transOut.put(-lit1,-lit2);
			}
		}
	}
	
	/**
	 * Creates a list of clauses that ensures (in a permissive way) 
	 * that the precondition has been satisfied for an acceptable delay.
	 * 
	 * @param varass the variable assigner that indicates the SAT variable 
	 * associated with each event occurrence.  
	 * @param out the output stream where the clauses should be stored.
	 * @param net the network for which this is defined.  
	 * @param times the time associated with each timestep (-1 if unknown).  
	 * @param n the number of time steps.
	 * */
	public void timedForcedTransitions(LiteralAssigner varass, 
			ClauseStream out, Network net,  
			Time[] times, 
			int n) {
		System.out.println("Calling timedForcedTransition with " + Arrays.toString(times));
		
		final Time[] min = new Time[times.length];
		final Time[] max = new Time[times.length];
		{
			Time currentMin = Time.ZERO_TIME;
			for (int t = 0 ; t<times.length ; t++) {
				if (times[t] != null) {
					currentMin = times[t];
				}
				min[t] = currentMin;
			}
		}
		{
			Time currentMax = Time.MAX_TIME;
			for (int t = times.length-1 ; t >= 0 ; t--) {
				if (times[t] != null) {
					currentMax = times[t];
				}
				max[t] = currentMax;
			}
		}
		System.out.println("min is  " + Arrays.toString(min));
		System.out.println("max is  " + Arrays.toString(max));
		
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final MMLDTransition tr: comp.transitions()) {
				for (final YAMLDFormula prec: tr.getPreconditions()) {
					final PeriodInterval ti = tr.getConditionTime(prec);
					for (int t=0 ; t<n ; t++) {
						final int pttLit;
						{
							final VariableSemantics sem = new PreconditionTriggersTransition(tr, prec, t);
							pttLit = varass.surelyGetVariable(sem);
						}
						
						// If prec is satisfied since before beginDate, it should have triggered
						final Time beginDate = min[t];
                        //final double beginDate = min[t] - ti.getEnd();
						final int beginTimeStep = findFirstBigger(max, beginDate);
						if (beginTimeStep != -1) {
							final List<Integer> clause = new ArrayList<Integer>();
							for (int tprime = beginTimeStep ; tprime <= t ; tprime++) {
								final VariableSemantics sem = new FormulaHolds(prec, comp, tprime);
								final int lit = varass.surelyGetVariable(sem);
								clause.add(-lit);
							}
							clause.add(pttLit);
							out.put(clause);
						}
						
						// If prec triggers, it is satisfied since endDate
						//final double endDate = max[t] - ti.getBeginning();
						final Time endDate = max[t].removePeriod(ti.getBeginning());
						final int endTimeStep = findFirstBigger(min, endDate);
						for (int tprime = endTimeStep ; tprime < t; tprime++) {
							final VariableSemantics sem = new FormulaHolds(prec, comp, tprime);
							final int lit = varass.surelyGetVariable(sem);
							out.put(-pttLit, lit);
						}
						
						System.out.println("Dealing with precondition " + prec.toFormattedString());
						System.out.println("Time step " + t);
						System.out.println("Current time is " + times[t]);
						System.out.println("Begin date is " + beginDate);
						System.out.println("Begin timestep is " + beginTimeStep);
						System.out.println("End date is " + endDate);
						System.out.println("End timestep is " + endTimeStep);
						System.out.println();
					}
				}
			}
		}
	}
	
	/**
	 * Finds the first index i of tab such that tab[i] > val.  
	 * 
	 * @param tab an array of increasing values.  
	 * */
	public static int findFirstBigger(Time[] tab, Time val) {
		// Dichotomic search
		int min = -1;
		int max = tab.length;
		while (max - min != 1) {
			final int next = (min + max) / 2;
			if (val.isBefore(tab[next])) {
//                    tab[next] > val) {
				max = next;
			} else {
				min = next;
			}
		}
		
		return min;
	}
}
