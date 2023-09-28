package util;

import edu.supercom.util.PseudoRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.MMLDTransition;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDFormula;

/**
 * A class with a number of useful methods.  
 */
public class Util {

    
    /**
     * Tries to compute a global transition from the specified state 
     * triggered by the specified transition 
     * and avoiding the specified set of events.  <p />
     * The purpose of the set of events is to implement a feedback 
     * from the components affected by other components.  
     * The MMLD semantics is such that, if component A emits an event a 
     * that synchronizes with event b of component B, 
     * then component B has to handle event b somewhat its current state 
     * (if the transition of B triggered by b has no rule 
     * with a satisfiable precondition, the default rule is triggered).  
     * Technically, if one wants B to prevent the transition on A 
     * when B is in certain states, the following trick can be used: 
     * <ul>
     * <li>define an "impossible event", </li>
     * <li>define a rule such that, 
     * when event b must be triggered in one of the forbidden state, 
     * then the impossible event is emitted, </li>
     * <li>use the following method to generate the MMLDGlobalTransition: 
     * this method will ensure the impossible event is not generated; 
     * if no such GlobalTransition exists, 
     * then the method returns <code>null</code>.</li>
     * </ul>
     * 
     * @param tr the transition that triggers the global transition.  
     * @param net the network on which the global transition takes place.  
     * @param impossible the set of impossible events.  
     * @param rand a pseudo random generator used to decide 
     * which rule should be used.  
     * @param st the current state.  
     * @return a global transition induced by <code>tr</code> 
     * that does not generate an event from <code>impossible</code>, 
     * <code>null</code> if there exists one such.  
     */
    public static MMLDGlobalTransition computeGlobalTransition(
            MMLDTransition tr, Network net, State st, 
            Set<YAMLDEvent> impossible, PseudoRandom rand) {
        
        final List<MMLDRule> rules = getTriggerableRulesFromTransition(tr, st);
        final Map<YAMLDComponent,MMLDRule> map = new HashMap<YAMLDComponent, MMLDRule>(); // The result as a map
        final boolean success = computeGTList(rules, tr.getComponent(), net, st, impossible, rand, map);
        
        if (!success) {
            return null;
        }
        
        return new ExplicitGlobalTransition(map);
    }

    // Generates what is happening from e and stores it in existingMap
    // Returns true if successful.
    private static boolean computeGTEvent(
            YAMLDEvent e, Network net, State st, 
            Set<YAMLDEvent> impossible, PseudoRandom rand, 
            Map<YAMLDComponent,MMLDRule> existingMap) {
        final YAMLDComponent comp = e.getComponent();
        if (existingMap.containsKey(comp)) {
            throw new IllegalStateException("Component " + comp.name() + 
                    " already assigned a rule.");
        }
        
        // The list of rules that could be triggered by <code>e</code>.  
        final List<MMLDRule> rules = getTriggerableRulesFromEvent(e, st);
        
        return computeGTList(rules, comp, net, st, impossible, rand, existingMap);
    }
    
    // Triggers one of the specified (modifiable) list of rules.  
    // Returns true if successful. 
    private static boolean computeGTList(
            List<MMLDRule> rules, YAMLDComponent comp, Network net, State st, 
            Set<YAMLDEvent> impossible, PseudoRandom rand, 
            Map<YAMLDComponent,MMLDRule> existingMap) {
        while (!rules.isEmpty()) {
            final int position = rand.rand(rules.size());
            final MMLDRule rule = rules.remove(position);
            existingMap.put(comp, rule);
            
            // Does this rule generates one of the specified events
            if (generates(rule, impossible) != null) {
                continue;
            }
            
            // Recursively
            boolean successful = true;
            for (final YAMLDEvent out: rule.getGeneratedEvents()) {
                for (final MMLDSynchro sy: out.getSynchros()) {
                    for (final YAMLDEvent in: sy.getSynchronizedEvents()) {
                        successful = computeGTEvent(in, net, st, impossible, rand, existingMap);
                        if (!successful) {
                            break;
                        }
                    }
                    if (!successful) {
                        break;
                    }
                }
                if (!successful) {
                    break;
                }
            }
            if (successful) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Indicates whether the specified rule generates one of the specified events.  
     * 
     * @param r the rule.  
     * @param es the set of events.  
     * @return the event generated by <code>r</code> that belongs 
     * to set <code>es</code> if any, 
     * <code>null</code> otherwise.  
     */
    public static YAMLDEvent generates(MMLDRule r, Set<YAMLDEvent> es) {
        for (final YAMLDEvent e: r.getGeneratedEvents()) {
            if (es.contains(e)) {
                return e;
            }
        }
        
        return null;
    }
    
    /**
     * Returns the list of rules associated with the specified incoming event 
     * satisfied in the specified state.  
     * 
     * @param in the incoming event.  
     * @param s the current state of the network.  
     * @return the list of transitions that could be triggered 
     * if incoming event <code>in</code> was to be triggered 
     * in state <code>s</code>.  
     * @throws IllegalStateException if there is no transition triggered by in.
     */
    public static List<MMLDRule> getTriggerableRulesFromEvent(YAMLDEvent in, State s) {
        final List<MMLDRule> result = new ArrayList<MMLDRule>();
        final YAMLDComponent comp = in.getComponent();
        
        {
            for (final MMLDTransition tr: comp.transitions()) {
                if (!tr.getTriggeringEvents().contains(in)) {
                    continue;
                }
                final List transitionRules = getTriggerableRulesFromTransition(tr, s);
                result.addAll(transitionRules);
            }
        }
        
        if (result.isEmpty()) {
            throw new IllegalStateException("Could not find a transition " 
                    + "triggered by event " + in);
        }
        
        return result;
    }

    /**
     * Returns the list of rules associated with the specified incoming event 
     * satisfied in the specified state.  
     * 
     * @param in the incoming event.  
     * @param s the current state of the network.  
     * @return the list of transitions that could be triggered 
     * if incoming event <code>in</code> was to be triggered 
     * in state <code>s</code>.  
     * @throws IllegalStateException if there is no transition triggered by in.
     */
    public static List<MMLDRule> getTriggerableRulesFromTransition(MMLDTransition tr, State s) {
        final List<MMLDRule> result = new ArrayList<MMLDRule>();
        final YAMLDComponent comp = tr.getComponent();
        
        boolean foundOneRule = false; // if no rule is satisfied, use default
        
        for (final MMLDRule r : tr.getRules()) {
            final YAMLDFormula f = r.getCondition();
            if (!f.satisfied(s, comp)) {
                continue;
            }
            foundOneRule = true;
            result.add(r);
        }

        if (!foundOneRule) {
            result.add(tr.getDefaultRule());
        }
        
        return result;
    }
    
    /**
     * Returns the list of events in the specified network 
     * that match one of the specified patterns.  
     * 
     * @param net the network.  
     * @param ps the patterns in string format as specified in {@link Pattern}.  
     * @return the list of events of <code>net</code> that match <code>ps</code>.  
     */
    public static Set<YAMLDEvent> findEvents(Network net, Collection<String> ps) {
        final Set<YAMLDEvent> result = new HashSet<YAMLDEvent>();
        
        final List<Pattern> pats = new ArrayList<Pattern>();
        for (final String p: ps) {
            final Pattern pat = Pattern.compile(p);
            pats.add(pat);
        }
        
        for (final YAMLDComponent comp: net.getComponents()) {
            for (final YAMLDEvent e: comp.events()) {
                for (final Pattern pat: pats) {
                    if (pat.matcher(e.name()).matches()) {
                        result.add(e);
                        break;
                    }
                }
            }
        }
        
        return result;
    }
}
