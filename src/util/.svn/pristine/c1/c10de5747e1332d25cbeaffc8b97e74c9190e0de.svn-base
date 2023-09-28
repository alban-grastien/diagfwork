package util;

import lang.State;

/**
 * Defines a scenario incrementally as the addition of a transition to an existing scenario.  
 * This class is especially useful if the scenario is built incrementally 
 * or if many scenarios share the same prefix.  
 * Access to parts of the scenario may be a bit slow (linear in the number of transitions).    
 * 
 * @author Alban Grastien 
 * @version 1.0
 * */
public class IncrementalScenario  extends AbstractScenario 
	implements Scenario {
	
	/**
	 * The prefix scenario.  
	 * */
	private final Scenario _sce;
	
	/**
	 * The additional transition.  
	 * */
	private final GlobalTransition _trans;

	/**
	 * The MMLD Transition at the end of this scenario.  
	 * */
	private final MMLDGlobalTransition _mmldTrans;
	
	/**
	 * The time <code>_trans</code> takes place.  
	 * */
	private final Time _time;
	
	/**
	 * The size (in terms of transitions) of this scenario.  
	 * This is redundant (basically equals <code>_sce.nbTrans() +1</code>) 
	 * but necessary to reduce the complexity.  
	 * */
	private final int _size;
	
	/**
	 * The new state after <code>_trans</code> has taken place.  
	 * */
	private final State _state;
	
	public IncrementalScenario(Scenario sce, MMLDGlobalTransition trans, State state) {
		_sce = sce;
		_trans = null;
		_mmldTrans = trans;
		_state = state;
		_time = Time.ZERO_TIME;
		_size = sce.nbTrans() + 1;
	}
	
	@Deprecated
	public IncrementalScenario(Scenario sce, GlobalTransition trans, Time time, State state) {
		final int size = sce.nbTrans();
		//if (time <= sce.getTime(size)) {
		if (time.isBefore(sce.getTime(size))) {
			throw new IllegalArgumentException("Time this transition takes place is too little "
					+ "(before the previous transition)");
		}
		_sce = sce;
		_trans = trans;
		_time = time;
		_state = state;
		_size = size +1;
		_mmldTrans = null;
	}

	@Override
	public State getState(int i) {
		if (i > _size) {
			throw new IndexOutOfBoundsException("Cannot access state " + i 
					+ " (contains only " + (_size+1) + ")");
		}
		
		if (i == _size) {
			return _state;
		}
		
		return _sce.getState(i);
	}

	@Override
	@Deprecated
	public Time getTime(int i) {
		if (i > _size) {
			throw new IndexOutOfBoundsException("Cannot access state " + i 
					+ " (contains only " + (_size+1) + ")");
		}
		
		if (i == _size) {
			return _time;
		}
		
		return _sce.getTime(i);
	}

	@Override
	@Deprecated
	public GlobalTransition getTrans(int i) {
		if (i >= _size) {
			throw new IndexOutOfBoundsException("Cannot access transition " + i 
					+ " (contains only " + _size + ")");
		}

		if (i == _size-1) {
			return _trans;
		}
		
		return _sce.getTrans(i);
	}
	
	@Override
	public MMLDGlobalTransition getMMLDTrans(int i) {
		if (i < 0) {
			throw new IndexOutOfBoundsException("Cannot access transition " + i);		
		}
		
		if (i >= _size) {
			throw new IndexOutOfBoundsException("Cannot access transition " + i 
					+ " (contains only " + _size + ")");
		}

		if (i == _size-1) {
			return _mmldTrans;
		}
		
		return _sce.getMMLDTrans(i);
	}

	@Override
	public int nbTrans() {
		return _size;
	}

	@Override
	public String toFormattedString() {
		return _sce.toFormattedString() + 
		  "\nTransition " + _time + " = {\n" + _mmldTrans.toFormattedString() + "};\n" 
		  + "\nState = {\n" + _state.toFormattedString() + "};\n";
	}

	
}
