package diag.reiter.hypos;

import java.util.Map;

import lang.YAMLDEvent;
import diag.reiter.Hypothesis;
import edu.supercom.util.UnmodifiableMap;
import edu.supercom.util.UnmodifiableMapConstructor;
import edu.supercom.util.UnmodifiableSet;

/**
 * An instance of a <code>MultiSetHypothesis</code> models a fault mode 
 * that represents the scenario that includes the specified number of occurrences 
 * for each fault.  
 * */
public class MultiSetHypothesis implements Hypothesis {

	/**
	 * The number of occurrences associated with each fault 
	 * (the value has to be strictly positive).  
	 * */
	private final UnmodifiableMap<YAMLDEvent, Integer> _map;
	
	/**
	 * Builds the multi set hypothesis with no fault.  
	 * */
	public MultiSetHypothesis() {
		_map = new UnmodifiableMapConstructor<YAMLDEvent, Integer>().getMap();
	}
	
	/**
	 * Builds the multi set hypothesis defined as by the addition of an occurrence 
	 * of the specified fault to the specified multi set hypothesis.  
	 * 
	 * @param h the original multi set hypothesis.  
	 * @param f the fault which occurrence is incremented.  
	 * */
	public MultiSetHypothesis(MultiSetHypothesis h, YAMLDEvent f) {
		final UnmodifiableMapConstructor<YAMLDEvent, Integer> c = 
			new UnmodifiableMapConstructor<YAMLDEvent, Integer>();
		boolean found = false;
		for (final YAMLDEvent evt: h._map.keySet()) {
			int nb = h.nbOccurrences(evt);
			if (evt == f) {
				found = true;
				nb++;
			}
			c.addMapping(evt, nb);
		}
		
		if (!found) {
			c.addMapping(f, 1);
		}
		_map = c.getMap();
	}
	
	/**
	 * Builds the multi set hypothesis with no fault with the specified number of occurrences.  
	 * 
	 * @param map the number of occurrences for each fault.  
	 * */
	public MultiSetHypothesis(UnmodifiableMap<YAMLDEvent, Integer> map) {
		boolean noZero = true;
		
		for (final YAMLDEvent e: map.keySet()) {
			final int i = map.get(e);
			if (i < 0) {
				throw new IllegalArgumentException("Cannot have a negative number of occurrences.");
			}
			
			if (i == 0) {
				noZero = false;
			}
		}
		
		if (noZero) {
			_map = map;
		} else {
			final UnmodifiableMapConstructor<YAMLDEvent, Integer> c = 
				new UnmodifiableMapConstructor<YAMLDEvent, Integer>();
			for (final YAMLDEvent e: map.keySet()) {
				final int i = map.get(e);
				if (i != 0) {
					c.addMapping(e, i);
				}
			}
			_map = c.getMap();
		}
	}
	
	/**
	 * Builds the multi set hypothesis with no fault with the specified number of occurrences.  
	 * 
	 * @param map the number of occurrences for each fault.  
	 * */
	public MultiSetHypothesis(Map<YAMLDEvent, Integer> map) {
		this(new UnmodifiableMapConstructor<YAMLDEvent, Integer>(map).getMap());
	}
	
	/**
	 * Returns the number of occurrences of the specified fault 
	 * according to this hypothesis.  
	 * 
	 * @param f the fault.  
	 * @return how many times <code>f</code> occurred according to this hypothesis.  
	 * */
	public int nbOccurrences(YAMLDEvent f) {
		{
			final Integer i = _map.get(f);
			if (i != null) {
				return i;
			}
		}
		
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MultiSetHypothesis)) {
			return false;
		}
		MultiSetHypothesis other = (MultiSetHypothesis) obj;
		if (_map == null) {
			if (other._map != null) {
				return false;
			}
		} else if (!_map.equals(other._map)) {
			return false;
		}
		return true;
	}
	
	public int hashCode() {
		int result = 0;
		for (final YAMLDEvent evt: _map.keySet()) {
			final int nb = nbOccurrences(evt);
//			if (nb == 0) {
//				continue;
//			}
			result += (Math.exp(nb) * evt.hashCode());
		}
		
		return result;
	}
	
	/**
	 * Returns the set of faults for which the number of occurrences in not zero 
	 * in this hypothesis.  
	 * 
	 * @return the set of faults such that {@link #nbOccurrences(YAMLDEvent)} 
	 * returns a different value than <code>0</code>.  
	 * */
	public UnmodifiableSet<YAMLDEvent> occurred() {
		return _map.keySet();
	}

	/**
	 * Indicates whether the first specified hypothesis 
	 * is a sub hypothesis of the second specified hypothesis.  
	 * 
	 * @param h1 the first hypothesis.  
	 * @param h2 the second hypothesis.  
	 * @return <code>true</code> if h2 is preferable to h1.  
	 * */
	public static boolean isSubHypothesis(MultiSetHypothesis h1, MultiSetHypothesis h2) {
		for (final YAMLDEvent e: h2.occurred()) {
			final int i1 = h1.nbOccurrences(e);
			final int i2 = h2.nbOccurrences(e);
			if (i1 < i2) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "MultiSetHypothesis [_map=" + _map + "]";
	}
}
