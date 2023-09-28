package util;

import lang.StateModification;
import lang.TimedState;

/**
 * Defines a timed scenario incrementally as the triggering of a global transition 
 * from the final state of a timed scenario.  
 * 
 * @author Alban Grastien
 * */
public class IncrementalTimedScenario extends AbstractTimedScenario 
implements TimedScenario {

	/**
	 * The timed scenario from which the global transition takes place.  
	 * */
	private final TimedScenario _scenario;
	
	/**
	 * The global transition.  
	 * */
	private final MMLDGlobalTransition _trans;
	
	/**
	 * The state after the transition (computed only if necessary).  
	 * */
	private TimedState _state;
	
	private final int _nbTrans;
	
	/**
	 * Creates a scenario that corresponds to the occurrence 
	 * of the specified global transition at the end of the specified scenario.  
	 * 
	 * @param s the timed scenario after which the transition takes place.  
	 * @param t the transition that takes place after <code>s</code>.  
	 * */
	public IncrementalTimedScenario(TimedScenario s, MMLDGlobalTransition t) {
		_scenario = s;
		_trans = t;
		_nbTrans = s.nbTrans() +1;
		_state = null;
	}
	
	/**
	 * Creates a scenario that corresponds to the occurrence 
	 * of the specified global transition at the end of the specified scenario 
	 * leading to the specified state.  Whether the reached state 
	 * is indeed the state obtained after applying the transition is not tested.  
	 * 
	 * @param s the timed scenario after which the transition takes place.  
	 * @param t the transition that takes place after <code>s</code>.  
	 * @param s2 the new timed state.  
	 * */
	public IncrementalTimedScenario(TimedScenario s, MMLDGlobalTransition t, TimedState s2) {
		_scenario = s;
		_trans = t;
		_nbTrans = s.nbTrans() +1;
		_state = s2;
	}
	
	private void computeState() {
		if (_state == null) {
			final TimedState previousState = _scenario.getLastState();
			final StateModification modif = 
				new TransStateModification(_trans, previousState.getState());
			_state = previousState.apply(modif);
		}		
	}

	@Override
	public TimedState getStateAfterTransition(int i) {
		if (i >= _nbTrans) {
			throw new IllegalArgumentException(i + " > " + _nbTrans);
		}
		if (i < _nbTrans-1) {
			return _scenario.getStateAfterTransition(i);
		}
		
		computeState();
		return _state;
	}

	@Override
	public TimedState getStateBeforeTransition(int i) {
		if (i > _nbTrans) {
			throw new IllegalArgumentException(i + " > " + _nbTrans);
		}
		if (i < _nbTrans) {
			return _scenario.getStateBeforeTransition(i);
		}

		computeState();
		return _state;
	}

	@Override
	public Time getTime(int i) {
		if (i < -1) {
			throw new IllegalArgumentException("" + i);
		}
		
		final int nbTrans = nbTrans();
		if (i > nbTrans) {
			throw new IllegalArgumentException(i + " > " + (nbTrans()+1));
		}
		
		if (i < nbTrans -1) {
			return _scenario.getTime(i);
		}
		
		// i == nbTrans -1 or nbTrans
		return _scenario.getFinalTime();
	}

	@Override
	public MMLDGlobalTransition getTrans(int i) {
		if (i < 0) {
			throw new IllegalArgumentException("" + i);
		}
		
		if (i >= _nbTrans) {
			throw new IllegalArgumentException(i + " > " + _nbTrans);
		}
		if (i < _nbTrans-1) {
			return _scenario.getTrans(i);
		}
		
		return _trans;
	}

	@Override
	public int nbTrans() {
		return _nbTrans;
	}

	@Override
	public Time getFinalTime() {
		return _scenario.getFinalTime();
	}
	
}
