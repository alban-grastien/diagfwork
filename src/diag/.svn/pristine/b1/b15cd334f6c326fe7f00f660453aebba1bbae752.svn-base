package diag;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.ExplicitState;
import lang.MMLDRule;
import lang.MMLDTransition;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDValue;
import lang.YAMLDVar;

import util.EmptyScenario;
import util.ExplicitGlobalTransition;
import util.IncrementalScenario;
import util.MMLDGlobalTransition;
import util.Scenario;

import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableSemantics;
import util.Time;

/**
 * A diagnosis report is a SAT-based representation of a solution for a diagnostic problem.  
 * 
 * @author Alban Grastien.  
 * */
public class DiagnosisReport {

	/**
	 * The network for which this report is defined.  
	 * */
	private final Network _net;
	
	/**
	 * The number of transitions in the SAT problem.  
	 * */
	private final int _nbTrans;
	
	/**
	 * The literal assigner that indicates how the solution can be read.  
	 * */
	private final VariableAssigner _varAss;
	
	/**
	 * The solution to the SAT problem.  
	 * */
	private final List<Boolean> _solution;
	
	/**
	 * Builds a report.  
	 * 
	 * @param net the network this report is defined for. 
	 * @param n the number of transitions.  
	 * @param l the literal assigner that indicates how to extract the value of a semantics.  
	 * @param s the solution to the SAT problem.  
	 * */
	public DiagnosisReport(Network net, int n, LiteralAssigner l, List<Boolean> s) {
		_net = net;
		_nbTrans = n;
		_varAss = l;
		_solution = new ArrayList<Boolean>(s);
	}
	
	/**
	 * Returns the number of transitions in the SAT problem.  
	 * 
	 * @return the number of transitions in the SAT problem.  
	 * */
	public int getNbTrans() {
		return _nbTrans;
	}
	
	/**
	 * Returns the value of the specified variable semantics.  
	 * 
	 * @param s the variable semantics.  
	 * @return <code>true</code> if <code>s</code> is <i>true</i> in this report, 
	 * <code>false</code> otherwise.  
	 * */
	public boolean ass(VariableSemantics s) {
		final int var = _varAss.getVariable(s);
		if (var > 0) {
			return _solution.get(var-1);
		} else {
			return !_solution.get(-var-1);
		}
	}
	
	/**
	 * Returns the state of the network in this report at specified time step.  
	 * 
	 * @param t the timestep.  
	 * @return the state in this report at timestep <code>t</code>.  
	 * */
	public State getState(int t) {
		final Map<YAMLDVar, YAMLDValue> m = new HashMap<YAMLDVar, YAMLDValue>();
		
		for (final YAMLDComponent c: _net.getComponents()) {
			for (final YAMLDVar v: c.vars()) {
				if (v.domain().isEmpty()) {
					int min = v.getRangeInit();
					int max = v.getRangeEnd();
					for (int i=min ; i<max ; i++) {
						final YAMLDValue value = YAMLDValue.getValue(i);
						final VariableSemantics sem = new Assignment(v, value, t);
						if (ass(sem)) {
							m.put(v, value);
							break;
						}
					}
				} else {
					for (final YAMLDValue value: v.domain()) {
						final VariableSemantics sem = new Assignment(v, value, t);
						if (ass(sem)) {
							m.put(v, value);
							break;
						}
					}
				}
			}
		}
		
		return new ExplicitState(_net, m);
	}
	
	/**
	 * Generates a scenario from this diagnosis report.  
	 * Concurrent events that take place at the same timestep 
	 * are merged in a single {@link MMLDGlobalTransition} 
	 * and a split might be necessary.  
	 * 
	 * @return a scenario corresponding to this report.  
	 * */
	public Scenario getScenario() {
		Scenario result = new EmptyScenario(getState(0), Time.ZERO_TIME);
		
		for (int i=0 ; i<getNbTrans() ; i++) {
			// Computing what rules and what input event took place.
			final Map<YAMLDComponent,MMLDRule> rules = new HashMap<YAMLDComponent, MMLDRule>();
			final Map<YAMLDComponent,YAMLDEvent> inputEvents = new HashMap<YAMLDComponent, YAMLDEvent>();
			for (final YAMLDComponent c: _net.getComponents()) {
				for (final MMLDTransition t: c.transitions()) {
					{
						final VariableSemantics sem = new MMLDTransitionTrigger(t, i);
						if (!ass(sem)) {
							continue;
						}
					}
					boolean foundRule = false;
					for (final MMLDRule r: t.getRules()) {
						final VariableSemantics sem = new RuleTrigger(i, r);
						if (ass(sem)) {
							rules.put(c, r);
							foundRule = true;
							break;
						}
					}
					if (!foundRule) {
						rules.put(c, t.getDefaultRule());
					}
					for (final YAMLDEvent e: t.getTriggeringEvents()) {
						final VariableSemantics sem = new EventOccurrence(e, i);
						if (ass(sem)) {
							inputEvents.put(c, e);
						}
					}
				}
			}
			if (rules.isEmpty()) {
				continue;
			}
			final MMLDGlobalTransition tr = new ExplicitGlobalTransition(rules);

//			// Computing the clusters of local transitions
//			final List<Set<YAMLDComponent>> clusters = new ArrayList<Set<YAMLDComponent>>();
//			final Set<YAMLDComponent> remaining = new HashSet<YAMLDComponent>(rules.keySet());
//			while (!remaining.isEmpty()) {
//				final YAMLDComponent root;
//				final Set<YAMLDComponent> currentCluster = new HashSet<YAMLDComponent>();
//				{
//					YAMLDComponent tmp = null;
//					for (final YAMLDComponent test: remaining) {
//						if (inputEvents.containsKey(test)) {
//							continue;
//						}
//						tmp = test;
//						break;
//					}
//					root = tmp;
//				}
//				currentCluster.add(root);
//				
//				final Set<YAMLDEvent> open = new HashSet<YAMLDEvent>(rules.get(root).getGeneratedEvents());
//				while (!open.isEmpty()) {
//					final YAMLDEvent e = open.iterator().next();
//					open.remove(e);
//					for (final MMLDSynchro s: e.getSynchros()) {
//						for (final YAMLDEvent e2: s.getSynchronizedEvents()) {
//							final YAMLDComponent c = e2.getComponent();
//							currentCluster.add(c);
//							open.addAll(rules.get(c).getGeneratedEvents());
//						}
//					}
//				}
//				
//				clusters.add(Collections.unmodifiableSet(currentCluster));
//			}
			
			//result = new IncrementalScenario(result, tr, getState(i+1));
			//System.out.println("SAT step " + i + " => scenario step " + result.nbTrans());
			result = addSplitedTransition(result, tr);
		}
		
		return result;
	}
	
	// Returns a scenario corresponding to the addition of gtrans to sce.  
	// gtrans is split if possible
	private Scenario addSplitedTransition(Scenario sce, MMLDGlobalTransition gtrans) {
		final List<MMLDGlobalTransition> clusters = new ArrayList<MMLDGlobalTransition>();
		
		for (final YAMLDComponent comp: gtrans.triggeringComponents()) {
			clusters.add(gtrans.cascade(comp));
		}
		
		final Deque<Scenario> scenarios = new ArrayDeque<Scenario>();
		final Deque<MMLDGlobalTransition> transes = new ArrayDeque<MMLDGlobalTransition>();
		
		// Now trying to add each cluster incrementally
		Scenario s = sce;
		for (MMLDGlobalTransition cluster: clusters) {
			while (!s.getState(s.nbTrans()).isApplicable(cluster)) {
				s = scenarios.pop();
				MMLDGlobalTransition old = transes.pop();
				cluster = cluster.merge(old);
			}
			scenarios.push(s);
			transes.push(cluster);
			final State state = s.getState(s.nbTrans());
			s = new IncrementalScenario(s, cluster, state.apply(cluster.getModification(state)));
		}
		
		return s;
	}
	
}
