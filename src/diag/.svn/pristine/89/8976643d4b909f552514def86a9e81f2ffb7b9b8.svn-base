package diag.reiter.hypos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import diag.reiter.Conflict;

import lang.YAMLDEvent;

import edu.supercom.util.Pair;

/**
 * A conflict is a disjunction of reasons why a diagnosis hypothesis 
 * cannot be a diagnosis candidate.  
 * At least one of these reasons is necessary for a hypothesis 
 * to be a candidate.  
 * Possible reasons are:
 * <ul> 
 * <li>the number of occurrences of some fault is too small 
 * (modelled by <code>&lt;e,i&gt;</code> 
 * where <code>e</code> is the event 
 * and <code>i</code> is the number (too small) of occurrences.</li>
 * <li>the number of occurrences of some fault is too big</li>
 * (modelled by <code>&lt;e,i&gt;</code>);</li>
 * <li>the order between two events is impossible 
 * (modelled by a {@link OrderBetweenFaults});</li>
 * </ul>
 * Notice that the same event <code>e</code> may appear in the first two lists.  
 * 
 * @author Alban Grastien
 * */
public class SequentialConflict implements Conflict {

	/**
	 * The list of faults for which the number of occurrences is too small.  
	 * */
	private final List<Pair<YAMLDEvent,Integer>> _tooSmall;
	/**
	 * The list of faults for which the number of occurrences is too large.  
	 * */
	private final List<Pair<YAMLDEvent,Integer>> _tooLarge;
	/**
	 * The list of inconsistent orders.  
	 * */
	private final List<OrderBetweenFaults> _impossibleOrders;
	
	/**
	 * Creates a conflict defined as a list of conditions that are impossible together.  
	 * 
	 * @param s the list of faults for which the value is too small. 
	 * @param l the list of faults for which the value is too large. 
	 * @param i the list of orders that are impossible.  
	 * */
	public SequentialConflict(
			List<Pair<YAMLDEvent,Integer>> s, 
			List<Pair<YAMLDEvent,Integer>> l, 
			List<OrderBetweenFaults> i) {
		_tooSmall = Collections.unmodifiableList(new ArrayList<Pair<YAMLDEvent,Integer>>(s));
		_tooLarge = Collections.unmodifiableList(new ArrayList<Pair<YAMLDEvent,Integer>>(l));
		_impossibleOrders = Collections.unmodifiableList(new ArrayList<OrderBetweenFaults>(i));
	}
	
	/**
	 * Returns the list of faults for which the value is too small. 
	 * 
	 * @return the list of faults for which the value is too small.
	 * */
	public List<Pair<YAMLDEvent,Integer>> getTooSmall() {
		return _tooSmall;
	}
	
	/**
	 * Returns the list of faults for which the value is too large. 
	 * 
	 * @return the list of faults for which the value is too large.
	 * */
	public List<Pair<YAMLDEvent,Integer>> getTooLarge() {
		return _tooLarge;
	}
	
	/**
	 * Returns the list of orders that are incompatible.  
	 * 
	 * @return the list of orders that are incompatible.
	 * */
	public List<OrderBetweenFaults> getOrders() {
		return _impossibleOrders;
	}
	
//	/**
//	 * Returns an array representation of the list of orders of {@link #getOrders()}.  
//	 * The <i>i</i>th element of the array is the {@link OrderBetweenFaults} 
//	 * between the <i>i</i>th fault and the <i>i+1</i>th fault if it is in {@link #getOrders()}, 
//	 * <code>null</code> otherwise.  
//	 * 
//	 * @return an array representation of {@link #getOrders()} (possibly empty).
//	 * */
//	public OrderBetweenFaults[] getOrdersArray() {
//		if (_impossibleOrders.isEmpty()) {
//			return new OrderBetweenFaults[0];
//		}
//		
//		final SequentialHypothesis h = _impossibleOrders.get(0).getHypothesis();
//		final OrderBetweenFaults[] result = new OrderBetweenFaults[h.getNbFaults()-1];
//		for (final OrderBetweenFaults obf: _impossibleOrders) {
//			result[obf.getFirst()] = obf;
//		}
//		
//		return result;
//	}

	@Override
	public String toString() {
		return "Conflict [_impossibleOrders=" + _impossibleOrders
				+ ", _tooLarge=" + _tooLarge + ", _tooSmall=" + _tooSmall + "]";
	}
}
