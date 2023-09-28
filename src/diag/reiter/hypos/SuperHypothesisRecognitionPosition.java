package diag.reiter.hypos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lang.YAMLDEvent;
import diag.EventOccurrence;
import diag.reiter.Hypothesis;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableSemantics;

/**
 * A super hypothesis recognition position is a variable semantics 
 * that indicates the current position while testing 
 * whether the (SAT-encoded) path on the system 
 * is part of a super hypothesis of a specified hypothesis.  
 * Practically, let <code>h = [f_1,...,f_n]</code> be a sequential hypothesis, 
 * then, SHRP (h,i,t) is <i>true</i> 
 * if, according at the value associated with {@link EventOccurrence}s 
 * for <i>t' <= t</i>, 
 * the path will be in a super hypothesis of <code>h</code> 
 * only if the path from time step <i>t</i> if a super hypothesis of 
 * [f_{i+1},...,f_n].  
 * 
 * @author Alban Grastien
 * */
public class SuperHypothesisRecognitionPosition implements VariableSemantics {

	/**
	 * The hypothesis we test whether the path is a super hypothesis.  
	 * */
	private final Hypothesis _h;
	/**
	 * The position in the recognition.
	 * */
	private final int _pos;
	/**
	 * The current time step.  
	 * */
	private final int _t;
	
	public SuperHypothesisRecognitionPosition(Hypothesis h, int pos, int t) {
		_h = h;
		_pos = pos;
		_t = t;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_h == null) ? 0 : _h.hashCode());
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
		if (!(obj instanceof SuperHypothesisRecognitionPosition)) {
			return false;
		}
		SuperHypothesisRecognitionPosition other = (SuperHypothesisRecognitionPosition) obj;
		if (_h == null) {
			if (other._h != null) {
				return false;
			}
		} else if (!_h.equals(other._h)) {
			return false;
		}
		if (_pos != other._pos) {
			return false;
		}
		if (_t != other._t) {
			return false;
		}
		return true;
	}
	
	public static void generateClauses(ClauseStream out, VariableAssigner eventAss, 
			VariableAssigner shrpAss, int tmax, 
			SequentialHypothesis h, Set<YAMLDEvent> faults) {
		final int posMax = h.getNbFaults();
		// Initial time step
		{
			final VariableSemantics sem = new SuperHypothesisRecognitionPosition(h, 0, 0);
			final int var = shrpAss.getVariable(sem);
			out.put(var);
		}
		for (int pos=1 ; pos<=posMax+1 ; pos++){
			final VariableSemantics sem = new SuperHypothesisRecognitionPosition(h, 0, 0);
			final int var = shrpAss.getVariable(sem);
			out.put(-var);
		}
		
		// POSi@t-1 => POSi@t
		for (int pos = 0 ; pos <= posMax+1 ; pos++) {
			for (int t=0 ; t<tmax ; t++) {
				final int a, b;
				{
					final VariableSemantics sem = new SuperHypothesisRecognitionPosition(h, pos, t);
					a = shrpAss.getVariable(sem);
				}
				{
					final VariableSemantics sem = new SuperHypothesisRecognitionPosition(h, pos, t+1);
					b = shrpAss.getVariable(sem);
				}
				out.put(-a, b);
			}
		}
		
		// POSi@t-1 /\ EVT@t => POSk@t (for all k < j)
		// // Sub case: POSi == 0
		for (final YAMLDEvent evt: faults){
			// The minimal position in the recognition if evt takes place.
			final int pos = h.indexOf(evt, 1); 
			posIAndEventImpliesPosJ(out, eventAss, shrpAss, tmax, 0, pos, evt, h);
		}
		// // Other sub cases
		for (int pos = 0 ; pos < posMax ; pos++) {
			final YAMLDEvent evt = h.getFault(pos);
			final int num = h.nbOccurrencesBefore(pos);
			final int nextPos = h.indexOf(evt, num+1);
			posIAndEventImpliesPosJ(out, eventAss, shrpAss, tmax, pos, nextPos, evt, h);
		}
		
		// POSi@t => ...
		for (int pos = 1 ; pos <= posMax+1 ; pos++) {
			final Set<YAMLDEvent> remainingFaults = new HashSet<YAMLDEvent>(faults);
			dependent(out, eventAss, shrpAss, tmax, pos, pos, remainingFaults, h);
			for (int previousPos = pos-1 ; previousPos >= 0 ; previousPos--) {
				final YAMLDEvent evt = h.getFault(previousPos);
				if (!remainingFaults.remove(evt)) {
					continue;
				}
				dependent(out, eventAss, shrpAss, tmax, pos, previousPos, remainingFaults, h);
			}
		}
	}
	
	private static void dependent(ClauseStream out, 
			VariableAssigner eventAss, VariableAssigner shrpAss, 
			int tmax, int currentPos, int previousPos, 
			Set<YAMLDEvent> events, SequentialHypothesis h) {
		for (int t=0 ; t<tmax ; t++) {
			final List<Integer> clause = new ArrayList<Integer>();
			
			{
				final VariableSemantics sem = 
					new SuperHypothesisRecognitionPosition(h, currentPos, t+1);
				final int var = shrpAss.surelyGetVariable(sem);
				clause.add(-var);
			}
			
			{
				final VariableSemantics sem = 
					new SuperHypothesisRecognitionPosition(h, previousPos, t);
				final int var = shrpAss.surelyGetVariable(sem);
				clause.add(var);
			}
			
			for (final YAMLDEvent evt: events) {
				final VariableSemantics sem = new EventOccurrence(evt, t);
				final int var = eventAss.surelyGetVariable(sem);
				clause.add(var);
			}
			
			out.put(clause);
		}
	}
	
	private static void posIAndEventImpliesPosJ(ClauseStream out, 
			VariableAssigner eventAss, VariableAssigner shrpAss, 
			int tmax, int i, int j, YAMLDEvent evt, 
			SequentialHypothesis h) {
		for (int k=i ; k<=j ; k++) {
			for (int t=0 ; t<tmax ; t++) {
				final int a, b, c;
				{
					final VariableSemantics sem = new SuperHypothesisRecognitionPosition(h, i, t);
					a = shrpAss.surelyGetVariable(sem);
				}
				{
					final VariableSemantics sem = new EventOccurrence(evt, t);
					b = eventAss.surelyGetVariable(sem);
				}
				{
					final VariableSemantics sem = new SuperHypothesisRecognitionPosition(h, k+1, t+1);
					c = shrpAss.surelyGetVariable(sem);
				}
				out.put(-a, -b, c);
			}
		}
	}
	
	public static void main (String[] args) {
//		final Options opt = new Options(args);
//		final Network net = Test.readNetwork(opt);
//		final SequentialHypothesis hypo = Test.readHypothesis(net, "@h", opt);
//		
//		generateClauses(null, null, null, 10, hypo, null);
	}
}
