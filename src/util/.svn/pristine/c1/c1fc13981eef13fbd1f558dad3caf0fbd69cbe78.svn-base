package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.AlarmLog.AlarmEntry;

import lang.MMLDRule;
import lang.Network;
import lang.State;
import lang.TimedState;
import lang.YAMLDComponent;
import lang.YAMLDEvent;

/**
 * An incomplete implementation of a timed scenario.  
 * */
public abstract class AbstractTimedScenario implements TimedScenario {

	@Override
	public AlarmLog alarmLog(Collection<YAMLDEvent> obs) {
		return alarmLog(this, obs);
	}
	
    @Override
	public TimedState getLastState() {
		return getStateBeforeTransition(nbTrans());
	}
	
    @Override
	public TimedState getFirstState() {
		return getStateAfterTransition(-1);
	}

	@Override
	public Scenario getScenario() {
		return new Scenario() {
			
			@Override
			public String toFormattedString() {
				// TODO Auto-generated method stub
				throw new UnsupportedOperationException("Not implemented yet");
			}
			
			@Deprecated
			@Override
			public List<YAMLDEvent> observations(Collection<YAMLDEvent> obs) {
				// TODO Auto-generated method stub
				throw new UnsupportedOperationException("Not implemented yet");
			}
			
			@Override
			public int nbTrans() {
				return AbstractTimedScenario.this.nbTrans();
			}
			
			@Override
			@Deprecated
			public GlobalTransition getTrans(int i) {
				return null;
			}
			
			@Override
			@Deprecated
			public Time getTime(int i) {
				return AbstractTimedScenario.this.getTime(i);
			}
			
			@Override
			public State getState(int i) {
				return AbstractTimedScenario.this.getStateBeforeTransition(i).getState();
			}
			
			@Override
			@Deprecated
			public AlarmLog alarmLog(Collection<YAMLDEvent> obs) {
				return AbstractTimedScenario.this.alarmLog(obs);
			}

			@Override
			public MMLDGlobalTransition getMMLDTrans(int i) {
				return AbstractTimedScenario.this.getTrans(i);
			}
		};
	}

	@Override
	public String toFormattedString() {
		// TODO Auto-generated method stub
		return toString();
	}
	
	@Override 
	public String toString() {
		final StringBuilder result = new StringBuilder();
		
		//final Scenario sce = getScenario();
		//result.append(sce.getState(0)).append("\n");
		//for (int i=0 ; i<nbTrans() ; i++) {
		//	result.append(" ---> \n");
		//	result.append(sce.getState(i)).append("\n");
		//}
		result
		  .append(getFirstState().getState())
		  .append("[").append(getTime(-1)).append("-").append(getTime(0)).append("]")
		  .append("\n");
		for (int i=0 ; i<nbTrans() ; i++) {
			result.append(" ---> ").append("\n");
			result.append(getTrans(i)).append("\n");
			result.append(" ---> ").append("\n");
			result
			  .append(getStateBeforeTransition(i+1).getState())
			  .append("[").append(getTime(i)).append("-").append(getTime(i+1)).append("]")
		      .append("\n");
		}
		
		return result.toString();
	}

	@Override 
	public Time getFinalTime() {
		return getTime(nbTrans());
	}

	/**
	 * Returns the alarm log associated with the specified time scenario 
	 * provided the specified set of observable events.  
	 * 
	 * @param sce the scenario.  
	 * @param obs the set of events that should be recorded in the alarm log.  
	 * @return the alarm log of scenario <code>sce</code>.  
	 * */
	public static AlarmLog alarmLog(TimedScenario tsce, Collection<YAMLDEvent> obs) {
		Collection<AlarmEntry> entries = new ArrayList<AlarmEntry>();
		//final Scenario sce = tsce.getScenario();
		final Network net = tsce.getFirstState().getState().getNetwork();
		
		Set<YAMLDEvent> queuedEvents = new HashSet<YAMLDEvent>();
		Time queuedTime = tsce.getInitialTime();
		boolean needSaving = true;
		
		for (int i=0 ; i<tsce.nbTrans() ; i++) {
			final Time time = tsce.getTime(i);
			if ((!time.equals(queuedTime)) && needSaving) {
				entries.add(new AlarmEntry(queuedEvents, queuedTime));
				queuedEvents.clear();
				needSaving = false;
			}
			queuedTime = time;
			
			final MMLDGlobalTransition g = tsce.getTrans(i);
			for (final YAMLDComponent c: net.getComponents()) {
				final MMLDRule t = g.getRule(c);
				if (t == null) {
					continue;
				}
				for (final YAMLDEvent e: t.getGeneratedEvents()) {
					if (obs.contains(e)) {
						queuedEvents.add(e);
						needSaving = true;
					}
				}
			}
		}
		
		if (!queuedEvents.isEmpty()) {
			entries.add(new AlarmEntry(queuedEvents, queuedTime));
		}

		Time finalTime = tsce.getFinalTime();
		if (!queuedTime.equals(finalTime)) {
			entries.add(new AlarmEntry(finalTime));
		}
		
		return new ImmutableAlarmLog(entries);
	}

	@Override
	public Time getInitialTime() {
		return getTime(-1);
	}
	
	public State getState(int i) {
		return getStateAfterTransition(i-1).getState();
	}
}
