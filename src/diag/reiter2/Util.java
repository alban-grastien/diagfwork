package diag.reiter2;

import diag.Assignment;
import diag.EventOccurrence;
import diag.LiteralAssigner;
import diag.MMLDTransitionTrigger;
import diag.MMLDTranslator;
import diag.RuleTrigger;
import edu.supercom.util.Options;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.BufferedPrintClauseStream;
import edu.supercom.util.sat.ClausePruner;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import lang.ExplicitState;
import lang.MMLDRule;
import lang.MMLDTransition;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDValue;
import lang.YAMLDVar;
import util.AlarmLog;
import util.EmptyScenario;
import util.ExplicitGlobalTransition;
import util.IncrementalScenario;
import util.MMLDGlobalTransition;
import util.Scenario;
import util.Time;

/**
 * Useful methods for manipulating hypotheses.  
 */
public class Util {
    
    /**
     * Indicates whether the specified hypothesis satisfy the specified property.  
     * 
     * @param s the hypothesis space the property is defined on.  
     * @param h the hypothesis.  
     * @param p the property.  
     * @param <H> the type of hypothesis.  
     * @return <code>true</code> if <code>p</code> is a property of <code>h</code>, 
     * <code>false</code> otherwise.  
     */
    public static <H extends Hypothesis> boolean satisfy(HypothesisSpace<H> s, H h, Property<H> p) {
        final H other = p.getHypothesis();
        if (p.isDescent()) {
            return s.isDescendant(h, other);
        } else {
            return !s.isDescendant(h, other);
        }
    }
    
    /**
     * Indicates whether the specified hypothesis 
     * ``hits'' the specified conflict.  
     * In other words, this method returns <code>false</code> 
     * if the set of hypotheses of the conflict
     * does not contain the specified hypothesis.  
     * Or, since the conflict represents a set of non candidates, 
     * if the method returns <code>false</code>, 
     * then the specified hypothesis is not a candidate.  
     * 
     * @param s the hypothesis space on which the hypothesis is defined.  
     * @param h the hypothesis.  
     * @param c the conflict.  
     * @return <code>true</code> if <code>h</code> hits the conflict.  
     */
    public static <H extends Hypothesis> boolean hits(HypothesisSpace<H> s, H h, Conflict<H> c) {
        for (final Property<H> property: c.getProperties()) {
            if (!satisfy(s, h, property)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns the list of minimal hypotheses in the specified list.  
     * 
     * @param s the hypothesis space on which the hypothesis is defined.  
     * @param hs the set of hypothesis.  
     * @return the minimal subset of <code>hs</code>.  
     */
    public static <H extends Hypothesis> Set<H> minimalHypotheses(HypothesisSpace<H> s, Set<H> hs) {
        final Set<H> result = new HashSet<H>();
        
        for (final H h: hs) {
            boolean minimal = true;
            for (final H other: hs) {
                if (h == other) {
                    continue;
                }
                if (s.isDescendant(h, other)) {
                    minimal = false;
                    break;
                }
            }
            if (minimal) {
                result.add(h);
            }
        }
        
        return result;
    }
    
    /**
     * Returns the list of successors of the specified hypothesis 
     * through the specified conflict.  
     * 
     * @param s the hypothesis space on which the hypothesis is defined.  
     * @param h the hypothesis.  
     * @param c the conflict.  
     * @return the list of successors of <code>h</code>.  
     */
    public static <H extends Hypothesis> Set<H> successors(HypothesisSpace<H> s, H h, Conflict<H> c) {
        final Set<H> result = new HashSet<H>();
        
        for (final Property<H> alpha: c.getNonDescentProperties()) {
            final H other = alpha.getHypothesis();
            result.addAll(s.minimalCommonDescendants(h, other));
        }
        
        return minimalHypotheses(s, result);
    }

    /**
     * Returns the collection of ancestors of the specified hypothesis.  
     * 
     * @param space the hypothesis space.  
     * @param descendant the hypothesis whose ancestors are computed.  
     * @return the set of ancestors of <code>h</code> in <code>space</code>.  
     */
    public static <H extends Hypothesis> Collection<H> ancestors(HypothesisSpace<H> space, H descendant) {
        final Set<H> result = new HashSet<H>();
        final Stack<H> stack = new Stack<H>();
        
        result.add(descendant);
        stack.add(descendant);
        
        while (!stack.isEmpty()) {
            final H h = stack.pop();
            
            for (final H parent: space.getParents(h)) {
                if (result.add(parent)) {
                    stack.push(parent);
                }
            }
        }
        
        return result;
    }
    
    // OTHER
    /**
     * Creates an empty file.  
     * 
     * @param temporary indicates whether the file should be temporary.
     * @return an empty file.  
     */
    public static File generateFile(boolean temporary) throws DiagnosisIOException {
        final int MAX_NB_TRIES = 10;
        
        for (int i=0 ; i<MAX_NB_TRIES ; i++) {
            try {
                final File result = File.createTempFile("aaa", "aaa");
                if (temporary) {
                    result.deleteOnExit();
                }
                return result;
            } catch (IOException e) {
                // Will try another file.
            }
        }
        
        throw new DiagnosisIOException("Could not generate an empty file.");
    }
    
	public static Pair<File,LiteralAssigner> generateModAndObs(
            VariableLoader loader, 
			Options opt,
			Network net, 
			State st, 
			int firstTimeStep,
			int lastTimeStep,
			AlarmLog log, 
            Collection<YAMLDEvent> faults
			) throws DiagnosisIOException {
		// Allocating the variables
		final int stepsPerObs = nbStepsPerObs(opt);
		final int nbTrans = lastTimeStep - firstTimeStep;
		final LiteralAssigner litAss = new LiteralAssigner(loader, net, firstTimeStep, lastTimeStep);

		if ("true".equals(opt.getOption("showLiterals"))) {
			System.out.println("== literal mapping ==");
			litAss.print(System.out);
			System.out.println("== end mapping ==");
		}

		final File modAndObs = Util.generateFile(false);
		final String modAndObsCNFFile = modAndObs.getPath();
		// Building the cnf file that encodes the model and the observations.
		try {
			final BufferedPrintClauseStream out1 = new BufferedPrintClauseStream(
					new PrintStream(new File(modAndObsCNFFile)));
			out1.setBufferSize(10000);
			final ClauseStream out = new ClausePruner(out1);
			final MMLDTranslator tl = new MMLDTranslator(litAss._ios);
			tl.translateNetwork(out, litAss, net, nbTrans);
			tl.noSimultaneousOccurrence(litAss, out, faults, nbTrans);
			if ("true".equals(opt.getOption("encodeTime"))) {
				Time[] times = new Time[nbTrans];
				for (int i=0 ; i<nbTrans ; i++) {
					times[i] = null;
				}
				for (int i=0 ; i<log.nbEntries() ; i++) {
					times[((i+1)*stepsPerObs)-1] = log.get(i)._time;
				}
				tl.timedForcedTransitions(litAss, out, net, times, nbTrans);
			}
			tl.state(litAss, out, st);
			// final SpyClauseStream spyout = new SpyClauseStream(out,
			// System.out);
			if ("true".equals(opt.getOption("maxTimeLag"))) {
				diag.Diag.maxTimeLag(net, log, stepsPerObs, out, litAss, tl);
			}
            int nbObsSoFar = 0;
			for (int i = 0; i < log.nbEntries(); i++) {
                final AlarmLog.AlarmEntry entry = log.get(i);
                final int nbNewObs = entry._events.size();
				tl.observationsInInterval(litAss, out, net.observableEvents(),
//						log.get(i)._events, stepsPerObs * i, stepsPerObs);
						entry._events, stepsPerObs * nbObsSoFar, (stepsPerObs+1)*nbNewObs);
                nbObsSoFar+= nbNewObs;
			}
			out.close();
		} catch (FileNotFoundException e) {
			throw new DiagnosisIOException(e.getMessage());
		}
		
		return new Pair<File, LiteralAssigner>(modAndObs, litAss);
	}
    
    public static int nbStepsPerObs(Options opt) {
		final int stepsPerObs;
		{
			final String nb = opt.getOption("stepsPerObs");
			if (nb == null) { // Default value
				stepsPerObs = 6;
			} else {
				stepsPerObs = Integer.parseInt(nb);
			}
		}
		return stepsPerObs;
	}

    
    public static boolean ass(VariableAssigner varass, List<Boolean> solution, VariableSemantics s) {
		final int var = varass.getVariable(s);
		if (var > 0) {
			return solution.get(var-1);
		} else {
			return !solution.get(-var-1);
		}
	}

    
	public static State getState(
            VariableAssigner varass, List<Boolean> solution, 
            Network net, 
            int t) {
		final Map<YAMLDVar, YAMLDValue> m = new HashMap<YAMLDVar, YAMLDValue>();
		
		for (final YAMLDComponent c: net.getComponents()) {
			for (final YAMLDVar v: c.vars()) {
				if (v.domain().isEmpty()) {
					int min = v.getRangeInit();
					int max = v.getRangeEnd();
					for (int i=min ; i<max ; i++) {
						final YAMLDValue value = YAMLDValue.getValue(i);
						final VariableSemantics sem = new Assignment(v, value, t);
						if (ass(varass, solution, sem)) {
							m.put(v, value);
							break;
						}
					}
				} else {
					for (final YAMLDValue value: v.domain()) {
						final VariableSemantics sem = new Assignment(v, value, t);
						if (ass(varass, solution, sem)) {
							m.put(v, value);
							break;
						}
					}
				}
			}
		}
		
		return new ExplicitState(net, m);
	}
	
	public static Scenario getScenario(
            VariableAssigner varass, List<Boolean> solution, 
            Network net, int firstTimeStep, int lastTimeStep) {
		Scenario result = new EmptyScenario(getState(varass, solution, net, firstTimeStep), Time.ZERO_TIME);
		
		for (int i=firstTimeStep ; i<lastTimeStep ; i++) {
			// Computing what rules and what input event took place.
			final Map<YAMLDComponent,MMLDRule> rules = new HashMap<YAMLDComponent, MMLDRule>();
			final Map<YAMLDComponent,YAMLDEvent> inputEvents = new HashMap<YAMLDComponent, YAMLDEvent>();
			for (final YAMLDComponent c: net.getComponents()) {
				for (final MMLDTransition t: c.transitions()) {
					{
						final VariableSemantics sem = new MMLDTransitionTrigger(t, i);
						if (!ass(varass, solution, sem)) {
							continue;
						}
					}
					boolean foundRule = false;
					for (final MMLDRule r: t.getRules()) {
						final VariableSemantics sem = new RuleTrigger(i, r);
						if (ass(varass, solution, sem)) {
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
						if (ass(varass, solution, sem)) {
							inputEvents.put(c, e);
						}
					}
				}
			}
			if (rules.isEmpty()) {
				continue;
			}
			final MMLDGlobalTransition tr = new ExplicitGlobalTransition(rules);
			result = addSplitedTransition(result, tr);
		}
		
		return result;
	}

	private static Scenario addSplitedTransition(Scenario sce, MMLDGlobalTransition gtrans) {
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
