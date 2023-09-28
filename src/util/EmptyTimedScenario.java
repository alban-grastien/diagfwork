package util;

import lang.TimedState;

/**
 * <b>Empty timed scenario</b> is an implementation of {@link TimedScenario} 
 * that contains only one state and no event.  
 * 
 * @author Alban Grastien
 * */
public class EmptyTimedScenario extends AbstractTimedScenario 
implements TimedScenario {
	
	/**
	 * The timed state.  
	 * */
	private final TimedState _ts; 
	
	/**
	 * The initial time.  
	 * */
	private final Time _time;
	
	/**
	 * Builds a timed scenario defined as the specified state 
	 * at the specified time.  
	 * 
	 * @param s the unique timed state of the timed scenario.  
	 * @param t the current time.  
	 * */
	public EmptyTimedScenario(TimedState s, Time t) {
		_ts = s;
		_time = t;
	}

	@Override
	public TimedState getStateAfterTransition(int i) {
		if (i == -1) {
			return _ts;
		}
		
		throw new IllegalArgumentException("" + i);
	}

	@Override
	public TimedState getStateBeforeTransition(int i) {
		if (i == 0) {
			return _ts;
		}
		
		throw new IllegalArgumentException("" + i);
	}

	@Override
	public Time getTime(int i) {
		if (i == -1) {
			return _time;
		}
		
		if (i == 0) {
			return _time;
		}
		
		throw new IllegalArgumentException("" + i);
	}

	@Override
	public MMLDGlobalTransition getTrans(int i) {
		throw new IllegalArgumentException("" + i);
	}

	@Override
	public int nbTrans() {
		return 0;
	}

	@Override
	public Time getFinalTime() {
		return _time;
	}

}
