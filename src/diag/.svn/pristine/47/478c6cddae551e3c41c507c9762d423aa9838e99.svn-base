package diag.reiter.hypos;

import java.util.Set;

import diag.reiter.Conflict;

import lang.YAMLDEvent;
import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;

/**
 * A set conflict is a conflict defined as two sets of faults, 
 * the positive and the negative sets.  
 * The conflict rejects the set hypotheses 
 * that include all the faults in the negative set
 * and include none of the faults in the positive set.<p />  
 * 
 * In other words, to make a candidate from a set hypothesis 
 * that hits a set conflict, 
 * a necessary step is to remove a fault from the negative set 
 * or to add a fault from the positive set.  
 * */
public class SetConflict implements Conflict {

	/**
	 * The positive set of faults.  
	 * */
	public final UnmodifiableSet<YAMLDEvent> _p;
	
	/**
	 * The negative set of faults.  
	 * */
	public final UnmodifiableSet<YAMLDEvent> _n;
	
	/**
	 * Builds a conflict defines by the two specified sets of faults.    
	 * 
	 * @param p the positive set of faults.  
	 * @param n the negative set of faults.  
	 * */
	public SetConflict(Set<YAMLDEvent> p, Set<YAMLDEvent> n) {
		_p = new UnmodifiableSetConstructor<YAMLDEvent>(p).getSet();
		_n = new UnmodifiableSetConstructor<YAMLDEvent>(n).getSet();
	}
	
	/**
	 * Returns the positive set of this conflict.  
	 * 
	 * @return the positive set of this conflict.  
	 * */
	public UnmodifiableSet<YAMLDEvent> getPositiveSet() {
		return _p;
	}
	
	/**
	 * Returns the negative set of this conflict.  
	 * 
	 * @return the negative set of this conflict.  
	 * */
	public UnmodifiableSet<YAMLDEvent> getNegativeSet() {
		return _n;
	}

	@Override
	public String toString() {
		return "SetConflict [_n=" + _n + ", _p=" + _p + "]";
	}
}
