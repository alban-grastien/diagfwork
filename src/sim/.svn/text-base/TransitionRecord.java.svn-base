package sim;

import java.util.Random;

import lang.Network;
import lang.State;
import lang.YAMLDTrans;

/**
 * A transition record deals with forced transition by keeping track 
 * of how long this transition has been enabled for.  The transition record 
 * contains the time when the transition <i>can</i> be triggered, the time 
 * when the transition <i>must</i> be triggered, and also randomly choose a
 * time when the time could be triggered; the user may or may not choose 
 * this latter time as the triggering time.  
 * 
 * @author Jussi Rintanen
 * @author Alban Grastien: Made it an external class.  
 * */
@Deprecated
public class TransitionRecord extends SynchronizedTransition {		
	/**
	 * Builds a transition record from the specified forced transition.  
	 * This constructor deals with computing the transitions that synchronize 
	 * with the forced one.  
	 * 
	 * @param net the network on which the transition is defined.  
	 * @param trans the forced transition.  
	 * */
	public TransitionRecord(Network net, YAMLDTrans trans) {
		super(net,trans);
		status = TransitionStatus.PASSIVE;
	}
	
	/**
	 * Tests the status of this forced transition for the specified state 
	 * at the specified time.  This generates an exception if a forced event 
	 * was supposed to take place.  
	 * 
	 * @param state the state of the system.  
	 * @param time the time when the state is reached.  
	 * */
	public void testStatus(State state, double time) {
		if (status == TransitionStatus.SCHEDULED && time > lateTime) {
			throw new IllegalArgumentException("Forced transition for event "
					+ transition().getComponent().name() + "." 
					+ transition().eventTrigger().name() 
					+ " was supposed to take place before " 
					+ lateTime 
					+ " (current time is " + time + ")");
		}
		
		if (transition().formula().satisfied(state, transition().getComponent())) {
			if (status == TransitionStatus.SCHEDULED) {
				// Nothing to do, still on schedule
			} else {
				status = TransitionStatus.SCHEDULED;
				earlyTime = time + transition().earliest;
				lateTime = time + transition().latest;
				scheduledTime = earlyTime
					+ (randomg.nextDouble() * (transition().latest - transition().earliest));
			}
		} else {
			status = TransitionStatus.PASSIVE;
		}
	}
	
	/**
	 * Tests the status of this forced transition for the specified state 
	 * at the specified time given the current time.  This generates an exception 
	 * if a forced event was supposed to take place.  
	 * 
	 * @param state the state of the system.  
	 * @param time the time when the state is reached.  
	 * @param currentTime the current time in the system (implicitly means that 
	 * the scheduled time should not be less than this value).  
	 * */
	public void testStatus(State state, double time, double currentTime) {
		if (status == TransitionStatus.SCHEDULED && time > lateTime) {
			throw new IllegalArgumentException("Forced transition for event "
					+ transition().getComponent().name() + "." 
					+ transition().eventTrigger().name() 
					+ " was supposed to take place before " 
					+ lateTime 
					+ " (current time is " + time + ")");
		}
		
		if (transition().formula().satisfied(state, transition().getComponent())) {
			if (status == TransitionStatus.SCHEDULED) {
				// Nothing to do, still on schedule
			} else {
				status = TransitionStatus.SCHEDULED;
				earlyTime = time + transition().earliest;
				lateTime = time + transition().latest;
				if (lateTime < currentTime) {
					throw new IllegalArgumentException(
							"Forced transition should have taken place at time " 
							+ lateTime + " (is now " + currentTime + "): " 
							+ transition().toFormattedString());
				}
				final double actualEarlyTime = Math.max(currentTime, earlyTime);
				scheduledTime = actualEarlyTime
					+ (randomg.nextDouble() * (lateTime - actualEarlyTime));
			}
		} else {
			status = TransitionStatus.PASSIVE;
		}
	}
	
	/**
	 * Indicates whether this forced transition is scheduled.  
	 * 
	 * @return <code>true</code> if this forced transition is supposed to 
	 * take place after some time.  
	 * */
	public boolean scheduled() {
		return status == TransitionStatus.SCHEDULED;
	}
	
	/**
	 * Returns the minimum time before this forced transition can be triggered 
	 * provided {@link #scheduled()} is true.  
	 * */
	public double earlyTime() {
		return earlyTime;
	}
	
	/**
	 * Returns the maximum time before this forced transition is triggered 
	 * provided {@link #scheduled()} is true.  
	 * */
	public double lateTime() {
		return lateTime;
	}
	
	/**
	 * Returns the time this forced transition is scheduled to be triggered 
	 * provided {@link #scheduled()} is true.  
	 * */
	public double scheduledTime() {
		return scheduledTime;
	}
	
	/*
	 * PASSIVE: precondition is false and transition not scheduled to fire.
	 * UNSCHEDULED: precondition is true, but has not been so for long
	 * enough. (OBSOLETE) SCHEDULED: precondition was true long enough, and
	 * the transition will fire.
	 */

	private TransitionStatus status;

	private double earlyTime;
	private double scheduledTime;
	private double lateTime;

	static Random randomg = new Random(13);
	
	public enum TransitionStatus {
		PASSIVE, SCHEDULED
	};



}
