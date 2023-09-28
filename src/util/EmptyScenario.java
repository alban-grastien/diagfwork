package util;

import lang.State;

/**
 * A simple scenario defined as a simple state.  
 * 
 * @author Alban Grastien 
 * @version 1.0  
 * */
public class EmptyScenario extends AbstractScenario 
	implements Scenario {

	/**
	 * The initial (and only) state.  
	 * */
	private final State _state;
	
	/**
	 * The initial time.  
	 * */
	private final Time _time;
	
	/**
	 * Builds a scenario simply defined as a state with no transition.  
	 * 
	 * @param s the state of the scenario.  
	 * @param t the time when the scenario starts.  
	 * */
	public EmptyScenario(State s, Time t) {
		_state = s;
		_time = t;
	}
	
	@Override
	public State getState(int i) {
		if (i == 0) {
			return _state;
		}
		throw new IndexOutOfBoundsException("Cannot access state " + i + " (contains only 1)");
	}

	@Override
	public Time getTime(int i) {
		if (i == 0) {
			return _time;
		}
		throw new IndexOutOfBoundsException("Cannot access state " + i + " (contains only 1)");
	}

	@Override
	@Deprecated
	public GlobalTransition getTrans(int i) {
		throw new IndexOutOfBoundsException("Cannot access transition " + i + " (contains none)");
	}

	@Override
	public int nbTrans() {
		return 0;
	}

	@Override
	public String toFormattedString() {
		return "State = {\n" + _state.toFormattedString() + "};\n";
	}

	@Override
	public MMLDGlobalTransition getMMLDTrans(int i) {
		throw new IndexOutOfBoundsException("Cannot access transition " + i + " (contains none)");
	}

}
