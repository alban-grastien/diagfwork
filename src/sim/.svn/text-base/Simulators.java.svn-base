package sim;

import edu.supercom.util.PseudoRandom;
import lang.MMLDTransition;
import lang.Network;
import lang.Period;
import lang.State;
import lang.TimedState;
import lang.YAMLDComponent;
import lang.YAMLDFormula;
import util.Time;
import util.TimedScenario;
import util.TimedStates;

/**
 * This class implements a number of static methods for simulators.  
 */
public class Simulators {
    
    // To make sure this class is not instantiated.
    private Simulators() {}
    
    /**
     * Pushes the scheduled triggering time of all forced transition 
     * after the specified time.  
     * 
     * @param sim the simulator for which the scheduled time is modified.  
     * @param min the minimum time before the forced transitions 
     * are allowed to trigger.  
     * @throws IllegalArgumentException if the triggering time 
     * cannot be pushed that far.  
     */
    public static void pushAllTriggerTime(Simulator sim, Time min) {
        final TimedScenario ts = sim.getScenario();
        final TimedState tstate = ts.getLastState();
        final State state = tstate.getState();
        final Network net = state.getNetwork();
        
        for (final YAMLDComponent comp: net.getComponents()) {
            for (final MMLDTransition trans: comp.transitions()) {
                for (final YAMLDFormula f: trans.getPreconditions()) {
                    if (f.satisfied(state, comp)) {
                        pushTriggeringTime(sim, trans, f, min);
                    }
                }
            }
        }
    }
    
    /**
     * Pushes the scheduled triggering time of the specified transition 
     * by the specified formula after the specified time.  
     * If the triggering time is already more that the specified value, 
     * then it remains unmodified.  
     * 
     * @param sim the simulator for which the scheduled time is modified.  
     * @param tr the transition that is scheduled.  
     * @param f the formula that will trigger the transition. 
     * @param min the minimum time before the formula triggers the transition.  
     * @throws IllegalArgumentException if the triggering time 
     * cannot be pushed that far.  
     */
    public static void pushTriggeringTime(
            Simulator sim, MMLDTransition tr, 
            YAMLDFormula f, Time min) {
        {
            final Time scheduled = sim.willTrigger(f, tr);
            if (min.isBefore(scheduled)) {
                return;
            }
        }
        
        final TimedState s = sim.getScenario().getLastState();
        
        //final double max = TimedStates.maxForcedDelay(s, tr, f) + sim.getCurrentTime();
        final Time time = sim.getCurrentTime();
        final Period maxDelay = TimedStates.maxForcedDelay(s, tr, f);
        final Time maxTime = new Time(time, maxDelay);
//        final double max = TimedStates.maxForcedDelay(s, tr, f) + sim.getCurrentTime();
        
        if (maxTime.isBefore(min)) {
            throw new IllegalArgumentException("Has to trigger before " + maxTime 
                    + " (asking to trigger after " + min + ").");
        }

        final Time newTime = Time.getRandom(sim.getRandom(),min,maxTime);
        sim.setTriggeringTime(tr, f, newTime);
    }
    
    /**
     * Returns a random double between the specified values.  
     * 
     * @param rand the random generator.  
     * @param min the minimum value. 
     * @param max the maximum value. 
     * @return a value within <code>[min, max[</code>.  
     */
    public static double random(PseudoRandom rand, double min, double max) {
        final int granularity = 1000;
        return min + ((max - min) * rand.rand(granularity) / granularity);
    }

    
    /**
     * Indicates whether the specified time 
     * could be the time the specified formula 
     * triggers the specified transition.  
     * 
     * @param sim the simulator on which the question is asked.  
     * @param tr the transition that will be triggered.  
     * @param f the formula that will trigger the transition.  
     * @param t the time the formula is set to.  
     * @return <code>true</code> if <code>t</code> is within the expected range, 
     * <code>false</code> otherwise.  
     */
    public static boolean isPossibleTriggeringTime(Simulator sim, MMLDTransition tr, 
            YAMLDFormula f, Time t) {
        final TimedState s = sim.getScenario().getLastState();
        
        {
            final Time max = new Time(sim.getCurrentTime(), TimedStates.maxForcedDelay(s, tr, f));
            if (max.isBefore(t)) {
                return false;
            }
        }
        
        {
            final Time min = new Time(sim.getCurrentTime(), TimedStates.minForcedDelay(s, tr, f));
            if (min.isBefore(t)) {
                return false;
            }

        }
        
        return true;
    }
    
    /**
     * Computes the maximum time after which 
     * the next forced transition can take place in the specified simulator.  
     * 
     * @param sim the simulator for which the question is asked.  
     * @return the maximum time that one can wait to 
     * before a forced transition have to trigger.  
     */
    public static Time maxTimeNextForcedTransition(Simulator sim) {
        final Time currentTime = sim.getCurrentTime();
        final Period maxDelay = TimedStates.maxDelayNextForcedTransition(sim.getScenario().getLastState());
        return new Time(currentTime,maxDelay);
    }

    /**
     * Computes the minimum time after which 
     * the next forced transition may take place in the specified simulator.  
     * 
     * @param sim the simulator for which the question is asked.  
     * @return the minimum time that one has to wait 
     * before a forced transition may trigger.  
     */
    public static Time minTimeNextForcedTransition(Simulator sim) {
        final Time currentTime = sim.getCurrentTime();
        final Period minDelay = TimedStates.minDelayNextForcedTransition(sim.getScenario().getLastState());
        return new Time(currentTime, minDelay);
    }
    
    /**
     * Changes the scheduled triggering times to make the specified formula 
     * trigger the specified transition first.  
     * 
     * @param sim the simulator this is applied to.  
     * @param tr the transition that is triggered.  
     * @param f the formula that triggers the transition.  
     * @return <code>true</code> if the trigger is successful, 
     * <code>false</code> otherwise.  
     */
    public static boolean setFirst(Simulator sim, PseudoRandom rand, 
            MMLDTransition tr, YAMLDFormula f) {
        final Network net = sim.getNetwork();
                    
		final Time nextTime;
		{
            // TODO: min should be computed based on <tr,f>
            final Time min = minTimeNextForcedTransition(sim);
            final Time max = maxTimeNextForcedTransition(sim);
            
			if (!max.isBefore(min)) {
				return false;
			}
            nextTime = Time.getRandom(rand, min, max);
		}
        
        //final TimedState s = sim.getScenario().getFinalState();
        for (final YAMLDComponent c2: net.getComponents()) {
            for (final MMLDTransition tr2: c2.transitions()) {
                for (final YAMLDFormula f2: tr2.getPreconditions()) {
//                    final double scheduled = _currentScenario._msim.willTrigger(f2, tr2);
//                    if (scheduled > nextTime) {
//                        continue;
//                    }
                    
                    Simulators.pushTriggeringTime(sim, tr2, f2, nextTime);
                }
            }
        }
        
        sim.setTriggeringTime(tr, f, nextTime);
        return true;
    }
    
}
