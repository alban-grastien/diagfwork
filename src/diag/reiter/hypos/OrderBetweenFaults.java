package diag.reiter.hypos;

import lang.YAMLDEvent;
import edu.supercom.util.sat.VariableSemantics;

/**
 * An order between faults is a variable semantics that is <i>true</i> 
 * if the specified number of occurrence of a specified event 
 * took place before the specified number of occurrence of another specified event.  
 * In other word, the <i>x</i>th occurrence of <i>f</i> 
 * took place before the <i>x'</i>th occurrence of <i>f'</i>.  
 * 
 * @author Alban Grastien.  
 * */
public class OrderBetweenFaults implements VariableSemantics {

	/**
	 * The first event in the order.  
	 * */
	private final YAMLDEvent _firstEvent;
	/**
	 * The number of occurrence of <code>_firstEvent</code> 
	 * */
	private final int _firstNb;
	/**
	 * The second event in the order.  
	 * */
	private final YAMLDEvent _secondEvent;
	/**
	 * The number of occurrence of <code>_secondEvent</code>.  
	 * */
	private final int _secondNb;
	
	/**
	 * Builds a variable semantics that is <i>true</i> 
	 * if the <code>i</code>th fault of this hypothesis 
	 * takes place before the <code>i+1</code>th fault.  
	 * 
	 * @param h the hypothesis.  
	 * @param i the first fault.  
	 * */
	public OrderBetweenFaults(SequentialHypothesis h, int i) {
		_firstEvent = h.getFault(i);
		_secondEvent = h.getFault(i+1);
		_firstNb = h.nbOccurrencesBefore(i);
		_secondNb = h.nbOccurrencesBefore(i+1);
	}
	
	/**
	 * Returns the first fault of this order.  
	 * 
	 * @return the first fault of this order.  
	 * */
	public YAMLDEvent getFirstFault() {
		return _firstEvent;
	}
	
	/**
	 * Returns the second fault of this order.  
	 * 
	 * @return the second fault of this order.  
	 * */
	public YAMLDEvent getSecondFault() {
		return _secondEvent;
	}

	/**
	 * Returns the position of the first fault.  
	 * 
	 * @return the number of the occurrence of the first fault this defines a constraint on. 
	 * */
	public int getFirstPosition() {
		return _firstNb;
	}

	/**
	 * Returns the position of the second fault.  
	 * 
	 * @return the number of the occurrence of the second fault this defines a constraint on. 
	 * */
	public int getSecondPosition() {
		return _secondNb;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_firstEvent == null) ? 0 : _firstEvent.hashCode());
		result = prime * result + _firstNb;
		result = prime * result
				+ ((_secondEvent == null) ? 0 : _secondEvent.hashCode());
		result = prime * result + _secondNb;
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
		if (!(obj instanceof OrderBetweenFaults)) {
			return false;
		}
		OrderBetweenFaults other = (OrderBetweenFaults) obj;
		if (_firstEvent == null) {
			if (other._firstEvent != null) {
				return false;
			}
		} else if (!_firstEvent.equals(other._firstEvent)) {
			return false;
		}
		if (_firstNb != other._firstNb) {
			return false;
		}
		if (_secondEvent == null) {
			if (other._secondEvent != null) {
				return false;
			}
		} else if (!_secondEvent.equals(other._secondEvent)) {
			return false;
		}
		if (_secondNb != other._secondNb) {
			return false;
		}
		return true;
	}
}
