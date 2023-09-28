package diag.reiter.hypos;

import java.util.Map;

import diag.reiter.Conflict;

import lang.YAMLDEvent;
import edu.supercom.util.UnmodifiableMap;
import edu.supercom.util.UnmodifiableMapConstructor;
import edu.supercom.util.UnmodifiableSet;

/**
 * A multi set conflict is a conflict defined on a multi set hypothesis.  
 * It is defined as a set of properties, one of which should not be satisfied.  
 * The properties can be: 
 * <ul>
 * <li>a specified event should not occur more than the specified number, 
 * 	or 
 * </li>
 * <li>a specified event should not occur less than the specified number. 
 * </li>
 * </ul>
 * */
public class MultiSetConflict implements Conflict {
	/**
	 * The list of minimum values 
	 * (a number of occurrences of specified fault strictly bigger than this value 
	 * makes the conflict unsatisfied).  
	 * */
	private final UnmodifiableMap<YAMLDEvent, Integer> _min;
	/**
	 * The list of maximum values 
	 * (a number of occurrences of specified fault strictly smaller than this value 
	 * makes the conflict unsatisfied).  
	 * */
	private final UnmodifiableMap<YAMLDEvent, Integer> _max;
	
	/**
	 * Builds a conflict defined as the specified values.  
	 * 
	 * @param min the list of minimum values: 
	 * a number of occurrences of specified fault strictly bigger than this value 
	 * makes the conflict unsatisfied.
	 * @param max the list of maximum values: 
	 * a number of occurrences of specified fault strictly smaller than this value 
	 * makes the conflict unsatisfied.
	 * */
	public MultiSetConflict(Map<YAMLDEvent,Integer> min, Map<YAMLDEvent, Integer> max) {
		{
			final UnmodifiableMapConstructor<YAMLDEvent, Integer> c = 
				new UnmodifiableMapConstructor<YAMLDEvent, Integer>();
			for (final Map.Entry<YAMLDEvent, Integer> ent: min.entrySet()) {
				final int val = ent.getValue();
				if (val < 0) {
					throw new IllegalArgumentException("Cannot have a min value < 0.");
				}
				c.addMapping(ent.getKey(), val);
			}
			_min = c.getMap();
		}
		{
			final UnmodifiableMapConstructor<YAMLDEvent, Integer> c = 
				new UnmodifiableMapConstructor<YAMLDEvent, Integer>();
			for (final Map.Entry<YAMLDEvent, Integer> ent: max.entrySet()) {
				final int val = ent.getValue();
				if (val <= 0) {
					throw new IllegalArgumentException("Cannot have a max value <= 0.");
				}
				c.addMapping(ent.getKey(), val);
			}
			_max = c.getMap();
		}
	}
	
	/**
	 * Returns the minimum number of occurrences of the specified fault.  
	 * 
	 * @param f the fault.  
	 * @return the number such that if a scenario includes 
	 * strictly more than this number of occurrences of <code>f</code>, 
	 * it is not rejected by this conflict.
	 * */
	public int getMin(YAMLDEvent evt) {
		final Integer i = _min.get(evt);
		if (i != null) {
			return i;
		}
		return Integer.MAX_VALUE;
	}
	
	/**
	 * Returns the maximum number of occurrences of the specified fault.  
	 * 
	 * @param f the fault.  
	 * @return the number such that if a scenario includes 
	 * strictly less than this number of occurrences of <code>f</code>, 
	 * it is not rejected by this conflict.
	 * */
	public int getMax(YAMLDEvent evt) {
		final Integer i = _max.get(evt);
		if (i != null) {
			return i;
		}
		return 0;
	}
	
	/**
	 * Returns the list of faults for which a minimum number is defined.  
	 * 
	 * @return the list of faults adding occurrences of which 
	 * makes a scenario no longer rejected by this conflict.  
	 * */
	public UnmodifiableSet<YAMLDEvent> minSet() {
		return _min.keySet();
	}
	
	/**
	 * Returns the list of faults for which a maximum number is defined.  
	 * 
	 * @return the list of faults removing occurrences of which 
	 * makes a scenario no longer rejected by this conflict.  
	 * */
	public UnmodifiableSet<YAMLDEvent> maxSet() {
		return _max.keySet();
	}
	
	/***/
	
	/**
	 * Indicates whether the specified multi set hypothesis satisfies this conflict.  
	 * 
	 * @param h the hypothesis that is tested.  
	 * @return <code>true</code> if <code>h</code> is rejected by this conflict.  
	 * */
	public boolean satisfied(MultiSetHypothesis h) {
		for (final YAMLDEvent evt: UnmodifiableSet.union(_min.keySet(),_max.keySet())) {
			final int actual = h.nbOccurrences(evt);
			final int min = getMin(evt);
			final int max = getMax(evt);

			if (actual > min) {
				return false;
			}
			if (actual < max) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "MultiSetConflict [_max=" + _max + ", _min=" + _min + "]";
	}
}
