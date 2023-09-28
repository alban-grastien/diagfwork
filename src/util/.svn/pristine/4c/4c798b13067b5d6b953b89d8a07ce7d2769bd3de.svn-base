package util;

import lang.MMLDTransition;
import lang.Network;
import lang.Period;
import lang.PeriodInterval;
import lang.TimedState;
import lang.YAMLDComponent;
import lang.YAMLDFormula;

/**
 * This class provides useful methods for timed states.  
 */
public class TimedStates {
        
    /**
     * Returns the max delay before the specified formula 
     * forces the specified transition 
     * to trigger in the specified timed state.  
     * 
     * @param s the timed state considered.  
     * @param tr the forced transition.  
     * @param f the formula that triggers the transition.  
     * @return the maximum delay after which <code>tr</code> will be triggered 
     * according to the current state, 
     * or {@link Period#MAX_PERIOD}
     * if the transition is not about to be triggered.  
     */
    public static Period maxForcedDelay(TimedState s, MMLDTransition tr, YAMLDFormula f) {
        final YAMLDComponent comp = tr.getComponent();
        
        if (!f.satisfied(s.getState(), comp)) {
            return Period.MAX_PERIOD;
        }
        
        final PeriodInterval ti = tr.getConditionTime(f);
        final Period satisfiedFor = s.satisfiedFor(comp, f);
        return Period.subtract(ti.getEnd(), satisfiedFor);
    }
    
    /**
     * Returns the max delay before the specified transition 
     * is forced to trigger in the specified timed state.  
     * 
     * @param s the timed state considered.  
     * @param tr the forced time.  
     * @return the maximum delay after which <code>tr</code> will be triggered 
     * according to the current state, 
     * or {@link Period#MAX_PERIOD}
     * if the transition is not about to be triggered.  
     */
    public static Period maxForcedDelay(TimedState s, MMLDTransition tr) {
        Period result = Period.MAX_PERIOD;
        
        //final YAMLDComponent comp = tr.getComponent();
        
        for (final YAMLDFormula f: tr.getPreconditions()) {
            final Period formulaMax = maxForcedDelay(s, tr, f);
            result = Period.shortest(result,formulaMax);
        }
        
        return result;
    }
    
    /**
     * Computes the maximum delay after which 
     * the next forced transition can take place.  
     * 
     * @param s the timed state considered.  
     * @return the maximum delay that one can wait to 
     * before a forced transition have to trigger.  
     */
    public static Period maxDelayNextForcedTransition(TimedState s) {
        Period result = Period.MAX_PERIOD;

        final Network net = s.getState().getNetwork();
        for (final YAMLDComponent c: net.getComponents()) {
            for (final MMLDTransition tr: c.transitions()) {
                final Period transitionMax = maxForcedDelay(s, tr);
                result = Period.shortest(result,transitionMax);
            }
        }

        //if (result < 0) {
        //    return 0;
        //}
        return result;
    }
    
    /**
     * Returns the min delay before the specified formula 
     * forces the specified transition 
     * to trigger in the specified timed state.  
     * 
     * @param s the timed state considered.  
     * @param tr the forced time.  
     * @param f the formula that triggers the transition.  
     * @return the minimum delay before <code>tr</code> can be forced to trigger 
     * according to the current state, 
     * or <code>Double.MAX_VALUE</code> 
     * if the transition is not about to be triggered.  
     */
    public static Period minForcedDelay(TimedState s, MMLDTransition tr, YAMLDFormula f) {
        final YAMLDComponent comp = tr.getComponent();
        
        if (!f.satisfied(s.getState(), comp)) {
            return Period.MAX_PERIOD;
        }
        
        final PeriodInterval ti = tr.getConditionTime(f);
        final Period satisfiedFor = s.satisfiedFor(comp, f);
        final Period minDelayBeforeTriggered = ti.getBeginning();
        
        if (satisfiedFor.isLonger(minDelayBeforeTriggered)) {
            return Period.ZERO_PERIOD;
        }
        
        return Period.subtract(minDelayBeforeTriggered, satisfiedFor);
    }
    
    /**
     * Returns the min delay before the specified transition 
     * is forced to trigger in the specified timed state.  
     * 
     * @param s the timed state considered.  
     * @param tr the forced time.  
     * @return the minimum delay before <code>tr</code> can be forced to trigger 
     * according to the current state, 
     * or <code>Double.MAX_VALUE</code> 
     * if the transition is not about to be triggered.  
     */
    public static Period minForcedDelay(TimedState s, MMLDTransition tr) {
        Period result = Period.MAX_PERIOD;
        
        //final YAMLDComponent comp = tr.getComponent();
        
        for (final YAMLDFormula f: tr.getPreconditions()) {
            final Period formulaMin = minForcedDelay(s, tr, f);
            result = Period.shortest(result,formulaMin);
        }
        
        return result;
    }
    
    /**
     * Computes the minimum delay after which 
     * the next forced transition can take place.  
     * 
     * @param s the timed state considered.  
     * @return the minimum delay that one can wait to 
     * before a forced transition may trigger.  
     */
    public static Period minDelayNextForcedTransition(TimedState s) {
        Period result = Period.MAX_PERIOD;

        final Network net = s.getState().getNetwork();
        for (final YAMLDComponent c: net.getComponents()) {
            for (final MMLDTransition tr: c.transitions()) {
                final Period transitionMin = minForcedDelay(s, tr);
                result = Period.shortest(result,transitionMin);
            }
        }
        
        return result;
    }
}
