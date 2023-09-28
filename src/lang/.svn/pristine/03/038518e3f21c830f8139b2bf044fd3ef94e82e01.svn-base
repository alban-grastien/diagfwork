package lang;

import java.util.Map;

/**
 * Returns the timed state obtained after a specific time elapsed 
 * since the previous timed state.  This class uses the {@link MapTimedState}.  
 * 
 * @author Alban Grastien
 * @see MapTimedState
 * */
public class DelayedTimedState 
extends AbstractTimedState implements TimedState {
	
	/**
	 * The timed state before the delay.  
	 * */
	private final TimedState _ts;
	
	/**
	 * The delay between <code>_ts</code> and this timed state.  
	 * */
	private final Period _delay;
	
	/**
	 * Builds a delayed timed state defined as the elapse 
	 * of the specified amount of time from the specified timed state.  
	 * 
	 * @param ts the timed state.  
	 * @param delay the delay.  
	 * */
	public DelayedTimedState(TimedState ts, Period delay) {
		_ts = ts;
		_delay = delay;
	}

	@Override
	@Deprecated
	public TimedState apply(Map<? extends YAMLDVar, YAMLDValue> m) {
		return apply(new MapStateModification(m));
	}

	@Override
	public TimedState apply(StateModification modif) {
		final State s = getState().apply(modif);
		return new MapTimedState(this, s);
	}

	@Override
	public State getState() {
		return _ts.getState();
	}

	@Override
	public Period satisfiedFor(YAMLDComponent c, YAMLDFormula f) {
		final Period delayForInternal = _ts.satisfiedFor(c, f);
        if (delayForInternal == Period.NO_PERIOD) {
            return Period.NO_PERIOD;
        }
        return Period.add(delayForInternal, _delay);
		//return new Period(result, _delay);
	}

	@Override
	public TimedState elapse(Period p) {
        final Period newDelay = Period.add(_delay, p);
		return new DelayedTimedState(_ts, newDelay);
	}

}
