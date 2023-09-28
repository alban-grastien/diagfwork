package diag.reiter.hypos;

import java.util.Set;

import diag.reiter.Hypothesis;

import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import lang.YAMLDEvent;

/**
 * An instance of a <code>SetHypothesis</code> models a fault mode 
 * represented by a set of {@link YAMLDEvent}
 * where all events in the set are faults that took place.  
 * 
 * @author Alban Grastien
 * */
public class SetHypothesis implements Hypothesis {
	
	/**
	 * The set of faulty events.  
	 * */
	//public final UnmodifiableSet<YAMLDEvent> _faultyEvents;

	/**
	 * The set of faults that occurred.
	 * */
	public final UnmodifiableSet<YAMLDEvent> _actualFaults;

	/**
	 * Builds a hypothesis where the faulty events are the first specified set 
	 * and the faulty events that actually occurred are the second set.  
	 * 
	 * @param faultyEvents the set of faulty events.  
	 * @param actualFaults the set of faults that actually took place.
	 * @deprecated Use {@link SetHypothesis#SetHypothesis(UnmodifiableSet)} instead.  
	 * */
	@Deprecated
	public SetHypothesis(UnmodifiableSet<YAMLDEvent> faultyEvents, 
			UnmodifiableSet<YAMLDEvent> actualFaults) {
//		_faultyEvents = faultyEvents;
		_actualFaults = actualFaults;
	}

	/**
	 * Builds a hypothesis where the faulty events are the first specified set 
	 * and the faulty events that actually occurred are the second set.  
	 * 
	 * @param faultyEvents the set of faulty events.  
	 * @param actualFaults the set of faults that actually took place.  
	 * @deprecated Use {@link SetHypothesis#SetHypothesis(Set)} instead.
	 * */
	@Deprecated
	public SetHypothesis(UnmodifiableSet<YAMLDEvent> faultyEvents, 
			Set<YAMLDEvent> actualFaults) {
		this(faultyEvents, new UnmodifiableSetConstructor<YAMLDEvent>(actualFaults).getSet());
	}

	/**
	 * Builds a hypothesis as the specified set of faults.    
	 * 
	 * @param actualFaults the set of faults that actually took place.  
	 * */
	public SetHypothesis(UnmodifiableSet<YAMLDEvent> actualFaults) {
		_actualFaults = actualFaults;
	}

	/**
	 * Builds a hypothesis as the specified set of faults.  
	 * 
	 * @param actualFaults the set of faults that actually took place.  
	 * */
	public SetHypothesis(Set<YAMLDEvent> actualFaults) {
		this(new UnmodifiableSetConstructor<YAMLDEvent>(actualFaults).getSet());
	}
	
	/**
	 * Builds a hypothesis that corresponds to the occurrence of the faults 
	 * in the specified set hypothesis plus the specified array of events.  
	 * 
	 * @param h the original hypothesis.  
	 * @param fs the list of events added to <code>h</code>.  
	 * */
	public SetHypothesis(SetHypothesis h, YAMLDEvent ... fs) {
//		_faultyEvents = h._faultyEvents;
		
		final UnmodifiableSetConstructor<YAMLDEvent> con = 
			new UnmodifiableSetConstructor<YAMLDEvent>(h._actualFaults);
		for (final YAMLDEvent f: fs) {
			con.add(f);
		}
		_actualFaults = con.getSet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_actualFaults == null) ? 0 : _actualFaults.hashCode());
//		result = prime * result
//				+ ((_faultyEvents == null) ? 0 : _faultyEvents.hashCode());
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
		if (!(obj instanceof SetHypothesis)) {
			return false;
		}
		SetHypothesis other = (SetHypothesis) obj;
		if (_actualFaults == null) {
			if (other._actualFaults != null) {
				return false;
			}
		} else if (!_actualFaults.equals(other._actualFaults)) {
			return false;
		}
//		if (_faultyEvents == null) {
//			if (other._faultyEvents != null) {
//				return false;
//			}
//		} else if (!_faultyEvents.equals(other._faultyEvents)) {
//			return false;
//		}
		return true;
	}
	
	@Override
	public String toString() {
		return "SetHypothesis [_actualFaults=" + _actualFaults
//				+ ", _faultyEvents=" + _faultyEvents 
				+ "]";
	}

	/**
	 * Indicates whether the first hypothesis is a sub hypothesis of the second hypothesis.  
	 * A hypothesis is a sub hypothesis of h = {f1,...,fk} 
	 * if it contains all fi (plus possibly more).  
	 * 
	 * @param h1 the first hypothesis.  
	 * @param h2 the second hypothesis.  
	 * @return <code>true</code> if h2 is preferable to h1.  
	 * */
	public static boolean isSubHypothesis(SetHypothesis h1, SetHypothesis h2) {
		return (h1._actualFaults.containsAll(h2._actualFaults));
	}
	
	/**
	 * Indicates whether the specified event is in this set hypothesis.  
	 * 
	 * @param f the fault event.  
	 * @return <code>true</code> if <code>f</code> takes place according to this hypothesis.  
	 * */
	public boolean contains(YAMLDEvent f) {
		return _actualFaults.contains(f);
	}
}
