package diag.reiter.hypos;

import edu.supercom.util.sat.VariableSemantics;
import lang.YAMLDEvent;

/**
 * A variable semantics whose corresponding SAT variable should be true 
 * if and only if a specified event occurred on the scenario.  
 * */
public class EventOccurred implements VariableSemantics {
	
	/**
	 * The event on which this semantics is defined.  
	 * */
	private final YAMLDEvent _e;
	
	/**
	 * Builds a variable semantics that is true 
	 * if the specified event occurred on the scenario.
	 * 
	 * @param e the event.  
	 * */
	public EventOccurred(YAMLDEvent e) {
		_e = e;
	}

	/**
	 * Returns the event this variable semantics is defined on.  
	 * 
	 * @return the event.  
	 * */
	public YAMLDEvent getEvent() {
		return _e;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_e == null) ? 0 : _e.hashCode());
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
		if (!(obj instanceof EventOccurred)) {
			return false;
		}
		EventOccurred other = (EventOccurred) obj;
		if (_e == null) {
			if (other._e != null) {
				return false;
			}
		} else if (!_e.equals(other._e)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EventOccurred [_e=" + _e + "]";
	}
}
