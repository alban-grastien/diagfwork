package sim;

import java.util.HashMap;
import java.util.Map;

import lang.MMLDTransition;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDFormula;

import edu.supercom.util.Pair;
import util.Time;

public final class MapExpirationNotice implements ExpirationNotice {

	/**
	 * A map that keeps the expirations.  
	 * */
	private final Map<Pair<YAMLDFormula, MMLDTransition>,Time> _map;
	
	/**
	 * The next formula to come.  
	 * */
	private Pair<YAMLDFormula,MMLDTransition> _next;
	
	/**
	 * The time the next formula will come.  
	 * */
	private Time _nextTime;

	/**
	 * Creates an empty expiration notice.  
	 * This notice has to be filled 
	 * through {@link #setTrigger(YAMLDFormula, MMLDTransition, double)}.  
	 * */
	public MapExpirationNotice() {
		_map = new HashMap<Pair<YAMLDFormula,MMLDTransition>, Time>();
		_next = null;
	}
	
	/**
	 * Creates an expiration notice that is a copy of this notice.  
	 * The copy can then be modified with affecting the original notice.  
	 * 
	 * @param exp the expiration notice that is copied.  
	 * @param s the state where the notice is defined.  
	 * */
	public MapExpirationNotice(ExpirationNotice exp, State s) {
		this();
		
		final Network net = s.getNetwork();
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				for (final YAMLDFormula f: trans.getPreconditions()) {
					if (f.satisfied(s, comp)) {
						final Time d = exp.willTrigger(f, trans);
						setTrigger(f, trans, d);
					}
				}
			}
		}
	}
	
	/**
	 * Creates an expiration notice that is a copy of this {@link MapExpirationNotice}.  
	 * The copy can then be modified with affecting the original notice.  
	 * 
	 * @param exp the expiration notice that is copied.  
	 * @param s the state where the notice is defined.  
	 * */
	public MapExpirationNotice(MapExpirationNotice exp, State s) {
		this();
		_map.putAll(exp._map);
		_next = exp._next;
		_nextTime = exp._nextTime;
	}

	@Override
	public Pair<YAMLDFormula, MMLDTransition> nextTrigger() {
		return _next;
	}

	@Override
	public void setTrigger(YAMLDFormula pr, MMLDTransition tr, Time t) {
		final Pair<YAMLDFormula,MMLDTransition> p = 
			new Pair<YAMLDFormula, MMLDTransition>(pr, tr);
		_map.put(p, t);
		
		if (_next == null) {
			// There was no next and there is now
			_next = new Pair<YAMLDFormula, MMLDTransition>(pr, tr);
			_nextTime = t; 
		} else if (_next.equals(p) && _nextTime.isBefore(t)) {
			// We postponed the former next.  Need to recompute.
			computeNext();  
		} else if (t.isBefore(_nextTime)) {
			// There was a next but it was just beaten
			_next = new Pair<YAMLDFormula, MMLDTransition>(pr, tr);
			_nextTime = t; 
		} else {
			// There is a next and it is better than that.  
		}
	}

	@Override
	public Time willTrigger(YAMLDFormula pr, MMLDTransition tr) {
		final Pair<YAMLDFormula,MMLDTransition> p = 
			new Pair<YAMLDFormula, MMLDTransition>(pr, tr);
		final Time d = _map.get(p);
		
		if (d == null) {
			return null;
		}
		
		return d;
	}
	
	private void computeNext() {
		Time nextTime = Time.MAX_TIME;
		Pair<YAMLDFormula,MMLDTransition> next = null;
		
		for (final Map.Entry<Pair<YAMLDFormula,MMLDTransition>,Time> e: 
			_map.entrySet()) {
			final Time d = e.getValue();
			if (d.isBefore(nextTime)) {
				nextTime = d;
				next = e.getKey();
			}
		}
		
		_next = next;
		_nextTime = nextTime;
	}
}
