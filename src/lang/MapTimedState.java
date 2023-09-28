package lang;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.supercom.util.Pair;

/**
 * A <b>Map Timed State</b> is an implementation of a state 
 * that represents the period a formula has been satisfied 
 * in a map.  This class uses the {@link DelayedTimedState}.  
 * 
 * @author Alban Grastien
 * @see DelayedTimedState
 * */
public class MapTimedState 
extends AbstractTimedState implements TimedState {

	/**
	 * The state on which this timed state is defined.  
	 * */
	private final State _state;
	
	/**
	 * A mapping that associates the satisfied preconditions 
	 * with how long they have been satisfied for.  
	 * */
	private final Map<Pair<YAMLDComponent, YAMLDFormula>,Period> _period;
	
	/**
	 * Builds a map timed state defined as the specified state 
	 * and such that any satisfied precondition was just reached.  
	 * 
	 * @param st the current state.  
	 * */
	public MapTimedState(State st) {
		_state = st;
		final Map<Pair<YAMLDComponent, YAMLDFormula>,Period> time = 
			new HashMap<Pair<YAMLDComponent,YAMLDFormula>, Period>();
		for (final Pair<YAMLDComponent, YAMLDFormula> pair: st.getNetwork().getForcingPreconditionsWithComponent()) {
			if (pair.second().satisfied(st, pair.first())) {
				time.put(pair, Period.ZERO_PERIOD);
			}
		}
		_period = Collections.unmodifiableMap(time);
	}
	
	/**
	 * Builds the new timed state reached after the new specified state 
	 * is reached from the specified previous map timed state.  
	 * 
	 * @param mts the map timed state on which the assignments are performed.  
	 * @param s the new state.  
	 * */
	public MapTimedState(MapTimedState mts, State s) {
		_state = s;
		final Map<Pair<YAMLDComponent, YAMLDFormula>,Period> time = 
			new HashMap<Pair<YAMLDComponent,YAMLDFormula>, Period>();
		boolean modified = false;
		for (final Pair<YAMLDComponent, YAMLDFormula> pair: s.getNetwork().getForcingPreconditionsWithComponent()) {
			Period previous = mts.satisfiedFor(pair.first(), pair.second());
			if (pair.second().satisfied(s, pair.first())) {
				if (previous == null) {
					modified = true;
					previous = Period.ZERO_PERIOD;
				}
				time.put(pair, previous);
			} else {
				if (previous != null) {
					modified = true;
				}
			}
		}
		if (!modified) {
			_period = mts._period;
		} else {
			_period = Collections.unmodifiableMap(time);
		}
	}
	
	/**
	 * Builds the new timed state reached after the new specified state 
	 * is reached from the specified previous timed state.  
	 * 
	 * @param mts the map timed state on which the assignments are performed.  
	 * @param s the new state.  
	 * */
	public MapTimedState(TimedState mts, State s) {
		_state = s;
		final Map<Pair<YAMLDComponent, YAMLDFormula>,Period> period = 
			new HashMap<Pair<YAMLDComponent,YAMLDFormula>, Period>();
		for (final Pair<YAMLDComponent, YAMLDFormula> pair: s.getNetwork().getForcingPreconditionsWithComponent()) {
			if (pair.second().satisfied(s, pair.first())) {
				Period previous = mts.satisfiedFor(pair.first(), pair.second());
				if (previous == null) {
					previous = Period.ZERO_PERIOD;
				}
				period.put(pair, previous);
			}
		}
		_period = Collections.unmodifiableMap(period);
	}
	
	/**
	 * Builds the new timed state reached after the new specified state 
	 * is reached exactly after elapsing the specified amount of period.  
	 * 
	 * @param mts the map timed state on which the assignments are performed.  
	 * @param s the new state.  
	 * @param p the period between <code>mts</code> and <code>s</code>.  
	 * TODO Seems to be buggy.  Should be tested or deleted.  
	 * */
	public MapTimedState(TimedState mts, State s, Period p) {
		_state = s;
		final Map<Pair<YAMLDComponent, YAMLDFormula>,Period> period = 
			new HashMap<Pair<YAMLDComponent,YAMLDFormula>, Period>();
		for (final Pair<YAMLDComponent, YAMLDFormula> pair: s.getNetwork().getForcingPreconditionsWithComponent()) {
			if (pair.second().satisfied(s, pair.first())) {
				Period previous = mts.satisfiedFor(pair.first(), pair.second());
				if (previous == null) {
					previous = Period.ZERO_PERIOD;
				} else {
					previous = Period.add(previous, p);
				}
				period.put(pair, previous);
			}
		}
		_period = Collections.unmodifiableMap(period);
	}
	
	@Override
	@Deprecated
	public TimedState apply(Map<? extends YAMLDVar, YAMLDValue> m) {
		return apply(new MapStateModification(m));
	}
	
	@Override
	public TimedState apply(StateModification modif) {
		final State state = _state.apply(modif);
		if (state.equals(_state)) {
			return this;
		}
		
		return new MapTimedState(this, state);
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public Period satisfiedFor(YAMLDComponent c, YAMLDFormula f) {
		final Period result = _period.get(new Pair<YAMLDComponent, YAMLDFormula>(c, f));
		if (result == null) {
			return Period.NO_PERIOD;
		}
		return result;
	}

	/**
	 * Returns the result of elapsing the specified amount of period 
	 * and then applying the specified assignments to this state.  
	 * 
	 * @param m a map of assignments for some state variables, the other variables 
	 * remain the same.  The assignments of variables that are not state variables 
	 * are ignored.  
	 * @param d the delay that elapses between this state 
	 * and the application of the assignments.  
	 * @return a state corresponding to the application of <code>m</code> on this state.
	 * */
	public TimedState apply(Map<? extends YAMLDVar, YAMLDValue> m, Period p) {
		return new MapTimedState(this, getState().apply(new MapStateModification(m)), p);
	}

	@Override
	public DelayedTimedState elapse(Period p) {
		return new DelayedTimedState(this, p);
	}
}
