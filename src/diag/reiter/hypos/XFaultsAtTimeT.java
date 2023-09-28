package diag.reiter.hypos;

import lang.YAMLDEvent;
import edu.supercom.util.sat.VariableSemantics;

/**
 * This variable semantics represents the fact that some specified fault 
 * occurred at least a certain number of time at some specified time.  
 * 
 * @author Alban Grastien
 * */
public class XFaultsAtTimeT implements VariableSemantics {

	/**
	 * The fault.  
	 * */
	private final YAMLDEvent _event;
	/**
	 * The number of times <code>_events</code> occurred.
	 * */
	private final int _x;
	/**
	 * The time step considered.  
	 * */
	private final int _t;
	
	/**
	 * Builds a variable semantics that is <i>true</i> 
	 * if the specified event occurred the specified number of times 
	 * (not strictly) before the specified time.  
	 * 
	 * @param e the event.  
	 * @param x the number of occurrences.  
	 * @param t the time step when this holds. 
	 * */
	public XFaultsAtTimeT(YAMLDEvent e, int x, int t) {
		_event = e;
		_x = x;
		_t = t;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_event == null) ? 0 : _event.hashCode());
		result = prime * result + _t;
		result = prime * result + _x;
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
		if (!(obj instanceof XFaultsAtTimeT)) {
			return false;
		}
		final XFaultsAtTimeT other = (XFaultsAtTimeT) obj;
		if (_event == null) {
			if (other._event != null) {
				return false;
			}
		} else if (_event != other._event) {
			return false;
		}
		if (_t != other._t) {
			return false;
		}
		if (_x != other._x) {
			return false;
		}
		return true;
	}
	
	/**
	 * Accesses the event.  
	 * 
	 * @return the event.  
	 * */
	public YAMLDEvent getEvent() {
		return _event;
	}
	
	/**
	 * Accesses the number of occurrences.  
	 * 
	 * @return the number of occurrences.  
	 * */
	public int getX() {
		return _x;
	}
	
	/**
	 * Accesses the time step.  
	 * 
	 * @return the time step.  
	 * */
	public int getTimeStep() {
		return _t;
	}

	@Override
	public String toString() {
		return "XFaultsAtTimeT [_event=" + _event + ", _t=" + _t + ", _x=" + _x
				+ "]";
	}
}
