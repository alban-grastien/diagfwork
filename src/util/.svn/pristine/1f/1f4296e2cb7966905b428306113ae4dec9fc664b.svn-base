package util;

import lang.Period;
import lang.TimedState;

/**
 * Defines a timed scenario as the occurrence of a delay 
 * after a specified scenario.  
 * 
 * @author Alban Grastien
 * */
public class DelayedScenario extends AbstractTimedScenario 
implements TimedScenario {
	
	/**
	 * The scenario after which the delay takes place.  
	 * */
	private final TimedScenario _scenario;
	
	/**
	 * The delay after the scenario {@link #_scenario}.  
	 * */
	private final Period _delay;
	
	/**
	 * The final state of this scenario (computed only if required).  
	 * */
	private TimedState _state;
	
	/**
	 * The final time of this scenario.  
	 * */
	private final Time _time;
	
	/**
	 * Creates a scenario defined as the elapse of the specified delay 
	 * in the specified scenario.  
	 * 
	 * @param s the scenario after which the delay takes place.  
	 * @param d the delay that takes place after <code>s</code>.  
	 * */
	public DelayedScenario(TimedScenario s, Period d) {
		_scenario = s;
		_delay = d;
		_state = null; // not computed at this stage.
		_time = new Time(_scenario.getFinalTime(), _delay);
	}

	@Override
	public TimedState getStateAfterTransition(int i) {
		return _scenario.getStateAfterTransition(i);
	}

	@Override
	public TimedState getStateBeforeTransition(int i) {
		final int nbTrans = nbTrans();
		if (i < 0) {
			throw new IllegalArgumentException("" + i);
		}
		
		if (i > nbTrans) {
			throw new IllegalArgumentException(i + " > " + nbTrans);
		}
		
		if (i < nbTrans) {
			return _scenario.getStateBeforeTransition(i);
		}
		
		computeLastState();
		return _state;
	}
	
	private void computeLastState() {
		if (_state == null) {
			final TimedState state = _scenario.getLastState();
			_state = state.elapse(_delay);
		}
	}
	
	@Override
	public TimedState getLastState() {
		computeLastState();
		return _state;
	}

	@Override
	public Time getTime(int i) {
		final int nbTrans = nbTrans();
		
		if (i < -1) {
			throw new IllegalArgumentException("" + i);
		}
		
		if (i > nbTrans) {
			throw new IllegalArgumentException(i + " > " + nbTrans);
		}
		
		if (i < nbTrans) {
			return _scenario.getTime(i);
		}
		
		// i == nbTrans
		return _time;
	}

	@Override
	public MMLDGlobalTransition getTrans(int i) {
		return _scenario.getTrans(i);
	}

	@Override
	public int nbTrans() {
		return _scenario.nbTrans();
	}

	@Override
	public Time getFinalTime() {
		return _time;
	}

}
