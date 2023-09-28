package diag.reiter.hypos;

import edu.supercom.util.sat.VariableSemantics;

/**
 * Implementation of a variable semantics that indicates that the next fault 
 * at specified time step is the fault at least at specified position 
 * in the current hypothesis.  
 * 
 * @author Alban Grastien
 * */
public class NextFaultIs implements VariableSemantics {

	/**
	 * The number of the time step.  
	 * */
	private final int _t;
	/**
	 * The position of the fault in the current list.  
	 * */
	private final int _pos;
	
	/**
	 * Creates a variable semantics that is <i>true</i> if, at specified time step, 
	 * the next fault in the list is at least at specified position.  
	 * 
	 * @param t the time step where this variable semantics holds.  
	 * @param pos the position of the next fault in the list.  
	 * */
	public NextFaultIs(int t, int pos) {
		_t = t;
		_pos = pos;
	}

	@Override
	public int hashCode() {
		final int prime = 1031;
		int result = 1;
		result = prime * result + _pos;
		result = prime * result + _t;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NextFaultIs)) {
			return false;
		}
		NextFaultIs other = (NextFaultIs) obj;
		if (_pos != other._pos) {
			return false;
		}
		if (_t != other._t) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "NextFaultIs[" + _pos + "]@" + _t;
	}
}
