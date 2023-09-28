package diag.reiter.hypos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.MMLDGlobalTransition;
import util.Scenario;

import diag.EventOccurrence;
import diag.reiter.Hypothesis;
import diag.reiter.HypothesisSubspace.HypothesisSubspaceType;
import diag.reiter.HypothesisSubspace.SearchType;

import edu.supercom.util.Pair;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.MapVariableAssigner;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;

import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.YAMLDComponent;
import lang.YAMLDEvent;

/**
 * A diagnosis hypothesis is an ordered sequence of faults.  
 * For instance, a diagnosis hypothesis would be <i>[f1,f2,f1]</i> 
 * stating the fault <i>f1</i> occurred, 
 * followed by fault <i>f2</i> and another occurrence of <i>f1</i>.  
 * The collection of events that are considered faulty is not specified 
 * as it is not strictly necessary.  
 * 
 * @author Alban Grastien
 * */
public class SequentialHypothesis implements Hypothesis { 

	final Set<YAMLDEvent> _faultEvents;
	
	final List<YAMLDEvent> _seq;
	
	final int _nbTrans;
	
	/**
	 * Builds an empty hypothesis: <i>[]</i>.  
	 * 
	 * @param faultEvents the list of faulty events on the model.  
	 * @param nbTrans the number of time steps.  
	 * */
	public SequentialHypothesis(Set<YAMLDEvent> faultEvents, int nbTrans) {
		_faultEvents = faultEvents;
		_seq = new ArrayList<YAMLDEvent>();
		_nbTrans = nbTrans;
	}
	
	/**
	 * Defines the hypothesis as a specified sequence of events.  
	 * 
	 * @param seq the list describing the events of the hypothesis.
	 * @param faultEvents the list of faulty events on the model.  
	 * @param nbTrans the number of time steps.    
	 * */
	public SequentialHypothesis(List<YAMLDEvent> seq, Set<YAMLDEvent> faultEvents, int nbTrans) {
		_faultEvents = faultEvents;
		_seq = new ArrayList<YAMLDEvent>(seq);
		_nbTrans = nbTrans;
	}
	
	/**
	 * Builds the hypothesis the specified scenario belongs to.  
	 * 
	 * @param sce the scenario.  
	 * @param evts the list of faulty events.  
	 * */
	public SequentialHypothesis(Scenario sce, Set<YAMLDEvent> evts, int nbTrans) {
		_faultEvents = evts;
		_seq = new ArrayList<YAMLDEvent>();
		_nbTrans = nbTrans;
		
		// We only look for output events.  
		// Therefore, input events  are replaced by the output events they synchronise with.  
		final List<YAMLDEvent> actualEvents = new ArrayList<YAMLDEvent>();
		for (final YAMLDEvent ev: evts) {
			if (ev.isInput()) {
				for (final MMLDSynchro s: ev.getSynchros()) {
					actualEvents.add(s.getEvent());
				}
			} else {
				actualEvents.add(ev);
			}
		}
		
		for (int i=0 ; i<sce.nbTrans() ; i++) {
			final MMLDGlobalTransition gtr = sce.getMMLDTrans(i);
			for (final YAMLDEvent ev: actualEvents) {
				final YAMLDComponent comp = ev.getComponent();
				final MMLDRule rule = gtr.getRule(comp);
				if (rule == null) {
					continue;
				}
				if (rule.getGeneratedEvents().contains(ev)) {
					_seq.add(ev);
				}
			}
		}
	}
	
	/**
	 * Indicates whether the first specified hypothesis 
	 * is a sub hypothesis of the second specified hypothesis.  
	 * A hypothesis is a sub hypothesis of <i>h = [f1,...,fk]</i> 
	 * it contains all <i>fi</i>s in this order 
	 * (possibly with other <i>f</i>s in between).  
	 * 
	 * @param h1 the first hypothesis.  
	 * @param h2 the second hypothesis.  
	 * @return <code>true</code> if <code>h1</code> is a child of <code>h2</code>.  
	 * */
	public static boolean isSubHypothesis(SequentialHypothesis h1, SequentialHypothesis h2) {
		final int size1 = h1._seq.size();
		final int size2 = h2._seq.size();
		if (size1 < size2) {
			return false;
		}
		
		if (size1 == size2) {
			return h1.equals(h2);
		}
	
		// Trying to find a corresponding event for each event in h2.
		int i1 = 0;
		int i2 = 0;
		while (i1 < size1 && i2 < size2) {
			final YAMLDEvent e1 = h1._seq.get(i1);
			final YAMLDEvent e2 = h2._seq.get(i2);
			if (e1 == e2) {
				i2++;
			}
			i1++;
		}
		
		if (i2 == size2) {
			return true;
		}
		
		return false;
	}
	
//	@Override
//	public void generateSAT(VariableAssigner eventVarass, 
//			VariableAssigner hypoVarass, 
//			ClauseStream out, Set<YAMLDEvent> faults, int n) {
//		// The number of occurrences.  
//		for (final YAMLDEvent ev: faults) {
//			final int occ = nbOccurences(ev);
//			for (int t=0 ; t<n ; t++) {
//				// OCC(0,t)
//				final VariableSemantics varsem = new XFaultsAtTimeT(ev, 0, t);
//				final int lit = hypoVarass.surelyGetVariable(varsem);
//				out.put(lit);
//			}
//			for (int x=2 ; x<= occ+1 ; x++) {
//				// !OCC(x,0)
//				final VariableSemantics varsem = new XFaultsAtTimeT(ev, x, 0);
//				final int lit = hypoVarass.surelyGetVariable(varsem);
//				out.put(-lit);
//			}
//			// OCC(1,0) <-> ev@0
//			{
//				final int var1, var2;
//				{
//					final VariableSemantics varsem = new XFaultsAtTimeT(ev, 1, 0);
//					var1 = hypoVarass.surelyGetVariable(varsem);
//				}
//				{
//					final VariableSemantics varsem = new EventOccurrence(ev, 0);
//					var2 = eventVarass.surelyGetVariable(varsem);
//				}
//				out.put(-var1,  var2);
//				out.put( var1, -var2);
//			}
//			for (int x=1 ; x<= occ+1 ; x++) {
//				for (int t=1 ; t<n ; t++) {
//					// OCC(x,t) <-> OCC(x,t-1) \/ (OCC(x-1,t-1) /\ ev@t)
//					// a <-> b \/ (c /\ d)
//					// Reformulated: 
//					// a -> b \/ c
//					// a -> b \/ d
//					// b -> a
//					// c /\ d -> a
//					final int var1, var2, var3, var4;
//					{
//						final VariableSemantics varsem = new XFaultsAtTimeT(ev, x, t);
//						var1 = hypoVarass.surelyGetVariable(varsem);
//					}
//					{
//						final VariableSemantics varsem = new XFaultsAtTimeT(ev, x, t-1);
//						var2 = hypoVarass.surelyGetVariable(varsem);
//					}
//					{
//						final VariableSemantics varsem = new XFaultsAtTimeT(ev, x-1, t-1);
//						var3 = hypoVarass.surelyGetVariable(varsem);
//					}
//					{
//						final VariableSemantics varsem = new EventOccurrence(ev, t);
//						var4 = eventVarass.surelyGetVariable(varsem);
//					}
//					out.put(-var1, var3); // Improvement here
//					out.put(-var1, var2, var4);
//					out.put(-var2, var1); 
//					out.put(-var3, -var4, var1);
//				}
//			}
//		}
//		
//		// The order
//		for (int i=0 ; i<_seq.size()-1 ; i++) {
//			final int orderLit;
//			{
//				final VariableSemantics sem = new OrderBetweenFaults(this, i);
//				orderLit = hypoVarass.surelyGetVariable(sem);
//			}
//			
//			final YAMLDEvent before = getFault(i);
//			final YAMLDEvent after = getFault(i+1);
//			final int posBefore = nbOccurrencesBefore(i);
//			final int posAfter = nbOccurrencesBefore(i+1);
//			{ // 'after' impossible at time 0
//				final VariableSemantics sem = new XFaultsAtTimeT(after, posAfter, 0);
//				final int lit = hypoVarass.surelyGetVariable(sem);
//				out.put(-orderLit, -lit);
//			}
//			for (int t=1 ; t<n ; t++) {
//				final int litAfter, litBefore;
//				{
//					final VariableSemantics sem = new XFaultsAtTimeT(after, posAfter, t);
//					litAfter = hypoVarass.surelyGetVariable(sem);
//				}
//				{
//					final VariableSemantics sem = new XFaultsAtTimeT(before, posBefore, t-1);
//					litBefore = hypoVarass.surelyGetVariable(sem);
//				}
//				out.put(-orderLit, -litAfter, litBefore);
//			}
//		}
//	}
	
	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + ((_seq == null) ? 0 : _seq.hashCode());
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
		if (!(obj instanceof SequentialHypothesis)) {
			return false;
		}
		SequentialHypothesis other = (SequentialHypothesis) obj;
		if (_seq == null) {
			if (other._seq != null) {
				return false;
			}
		} else if (!_seq.equals(other._seq)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Computes the number of occurrences of the specified event in this hypothesis.  
	 * 
	 * @param e the event.  
	 * @return how many times the specified event is supposed to take place, 
	 * according to this hypothesis.  
	 * */
	public int nbOccurences(YAMLDEvent e) {
		int result = 0;
		for (final YAMLDEvent other: _seq) {
			if (other == e) { result++; }
		}
		return result;
	}
	
	/**
	 * Returns the event at specified position in the hypothesis.  
	 * 
	 * @param i the position in the sequence.  
	 * @return which event is at position <code>x</code> in the sequence.  
	 * */
	public YAMLDEvent getFault(int i) {
		return _seq.get(i);
	}
	
	/**
	 * Returns the number of occurrences of the event at the specified position 
	 * (not strictly) before the event.  For instance, if this hypothesis is 
	 * <i>[f1,f2,f1,f1,f3,f1]</i>, the method should return: 
	 * <ul>
	 * <li>1 if parameter is 0,</li>
	 * <li>1 if parameter is 1,</li>
	 * <li>2 if parameter is 2,</li>
	 * <li>3 if parameter is 3,</li>
	 * <li>1 if parameter is 4, and</li>
	 * <li>4 if parameter is 5.</li>
	 * </ul>
	 * 
	 * @param the position of the fault.  
	 * @return how many times the fault occurred at this position.  
	 * */
	public int nbOccurrencesBefore(int i) {
		final YAMLDEvent ev = getFault(i);
		int result = 1; // Already counting position i
		for (int j=0 ; j<i ; j++) {
			if (getFault(j) == ev) { result++; }
		}
		return result;
	}

	@Override
	public String toString() {
		return _seq.toString();
	}
	
	/**
	 * Returns the number of faults in this hypothesis.  
	 * 
	 * @return the number of faults in this hypothesis.  
	 * */
	public int getNbFaults() {
		return _seq.size();
	}

	public List<SequentialHypothesis> createSubHypotheses(SequentialConflict con) {
		final List<SequentialHypothesis> result = new ArrayList<SequentialHypothesis>();
		for (final Pair<YAMLDEvent,Integer> p: con.getTooSmall()) {
			addSubHypotheses(result, p.first());
		}
		
		return result;
	}
	
	private void addSubHypotheses(List<? super SequentialHypothesis> l, YAMLDEvent e) {
		for (int i=0 ; i<=_seq.size() ; i++) {
			if (i != _seq.size() && _seq.get(i) == e) {
				continue; // This subhypothesis will be generated at the next iteration
			}
			final List<YAMLDEvent> newList = new ArrayList<YAMLDEvent>(_seq);
			newList.add(i, e);
			final SequentialHypothesis h = new SequentialHypothesis(newList, _faultEvents, _nbTrans);
			l.add(h);
		}
	}
	
	/**
	 * Indicates whether this hypothesis hits the specified conflicts.  
	 * The method may be imprecise, i.e., return that this hypothesis 
	 * does hit the conflict although it does not.  
	 * Being able to notify that a hypothesis does not hit a conflict 
	 * is preferable though as it allows to easily dismiss a hypothesis.  
	 * 
	 * @param c the conflict that is tested.  
	 * @return <code>true</code> if the hypothesis hits the specified conflict, 
	 * unspecified (i.e, either <code>true</code> or <code>false</code>) 
	 * otherwise.  
	 * */
	public boolean hits(SequentialConflict c) {
		if (!(c instanceof SequentialConflict)) {
			return true;
		}
		
		final SequentialConflict con = (SequentialConflict)c;
		// Tests whether this hypothesis has at least <code>minI</code> faults of type <code>ev</code>.  
		for (final Pair<YAMLDEvent,Integer> s: con.getTooSmall()) {
			final YAMLDEvent ev = s.first();
			final int minI = s.second();
			final int actualI = this.nbOccurences(ev);
			if (actualI > minI) {
				return true;
			}
		}
		
		// Tests whether this hypothesis has at most <code>maxI</code> faults of type <code>ev</code>.  
		for (final Pair<YAMLDEvent,Integer> l: con.getTooLarge()) {
			final YAMLDEvent ev = l.first();
			final int maxI = l.second();
			final int actualI = this.nbOccurences(ev);
			if (actualI < maxI) {
				return true;
			}
		}
		
//		// Tests the orderings.
//		final OrderBetweenFaults[] obfs = con.getOrdersArray();
//		for (int begin = 0; begin < obfs.length ; begin++) {
//			if (obfs[begin] == null) {
//				continue;
//			}
//			
//			int end = begin;
//			while (end != obfs.length && obfs[end] != null) {
//				end++;
//			}
//			
//			// Check the ordering between begin and end
//			final List<YAMLDEvent> list = new ArrayList<YAMLDEvent>();
//			for (int i=begin ; i<end ; i++) {
//				list.add(obfs[i].getFirstFault());
//			}
//			list.add(obfs[end-1].getSecondFault());
//			final SequentialHypothesis other = new SequentialHypothesis(list);
//			if (!isSubHypothesis(this, other)) {
//				return true;
//			}
//		}
		// Tests the orderings.  
		for (final OrderBetweenFaults obs: con.getOrders()) {
			final YAMLDEvent ev1 = obs.getFirstFault();
			final int nb1 = obs.getFirstPosition();
			if (nbOccurences(ev1) < nb1) {
				return true;
			}
			final YAMLDEvent ev2 = obs.getSecondFault();
			final int nb2 = obs.getSecondPosition();
			if (nbOccurences(ev2) < nb2) {
				return true;
			}
			
			final int index1 = indexOf(ev1, nb1);
			final int index2 = indexOf(ev2, nb2);
			if (index1 > index2) {
				return true;
			}
		}
		
		return false;
	}
	
	public VariableAssigner generateSuperVariable(
			VariableLoader loader, Set<YAMLDEvent> faults, int tmax) {
		final MapVariableAssigner result = new MapVariableAssigner();
//		final int size = _seq.size();
//		final int shift = size+2;
//		
//		for (int pos=0 ; pos<=size+1 ; pos++) {
//			for (int t=0 ; t<=tmax ; t++) {
//				{
//					final NextFaultIs nfi = new NextFaultIs(t, pos);
//					result.add(nfi, loader.allocate(1));
//				}
//				{
//					final NextFaultIs nfiprime = new NextFaultIs(t, pos+shift);
//					result.add(nfiprime, loader.allocate(1));
//				}
//			}
//		}
//		
		return result;
	}

	public void generateSuperSAT(VariableAssigner eventVarass, 
			VariableAssigner hypoVarass, 
			ClauseStream out, Set<YAMLDEvent> faults, int tmax) {
//		final int size = _seq.size();
//		final int shift = size+2;
//		
//		// Initial time step
//		for (int pos=1 ; pos <= size ; pos++) {
//			final VariableSemantics sem = new NextFaultIs(0, pos);
//			final int var = hypoVarass.surelyGetVariable(sem);
//			out.put(-var);
//		}
//		// Position 0
//		for (int t=0 ; t<=tmax ; t++) {
//			final VariableSemantics sem = new NextFaultIs(t, 0);
//			final int var = hypoVarass.surelyGetVariable(sem);
//			out.put(var);
//		}
//		
//		// 
//		for (int pos=1 ; pos<=size ; pos++) {
//			for (int t=1 ; t<=tmax ; t++) {
//				final int a, b, c, d;
//				{
//					final VariableSemantics sem = new NextFaultIs(t, pos);
//					a = hypoVarass.surelyGetVariable(sem);
//				}
//				{
//					final VariableSemantics sem = new NextFaultIs(t-1, pos+shift-1);
//					b = hypoVarass.surelyGetVariable(sem);
//				}
//				{
//					final YAMLDEvent ev = _seq.get(pos-1);
//					{
//						final int prev = precIndex(pos-1)+1; 
//						final VariableSemantics sem = new NextFaultIs(t-1, prev+shift);
//						c = hypoVarass.surelyGetVariable(sem);
//					}
//					{
//						final VariableSemantics sem = new EventOccurrence(ev, t-1);
//						d = eventVarass.surelyGetVariable(sem);
//					}
//				}
//				out.put(-a,c,d);
//				out.put(-a,d);
//				
//			}
//		}
//		
//		
//		
//		// NextFaultIs(t,size+1)
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public int nextIndex(int current) {
		final YAMLDEvent e = _seq.get(current);
		for (int i=current+1 ; i<_seq.size() ; i++) {
			if (_seq.get(i) == e) {
				return i;
			}
		}
		return _seq.size();
	}

	public int precIndex(int current) {
		final YAMLDEvent e = _seq.get(current);
		for (int i=current-1 ; i>=0 ; i--) {
			if (_seq.get(i) == e) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Indicates at which position the specified occurrence of the specified event 
	 * takes place.  
	 * 
	 * @param e the event.  
	 * @param occNb the number of the occurrence of <code>e</code>.  
	 * @return the <i>occNb</i>th smallest number <code>i</code> 
	 * such that <code>getFault(i) == e</code>, {@link #getNbFaults()} 
	 * if no such value exists.  
	 * */
	public int indexOf(YAMLDEvent e, int occNb) {
		int nb = 0;
		for(int i=0 ; i<_seq.size() ; i++) {
			final YAMLDEvent e2 = _seq.get(i);
			if (e != e2) {
				continue;
			}
			nb++;
			if (nb == occNb) {
				return i;
			}
		}
		
		throw new IndexOutOfBoundsException();
	}

	
	/**
	 * Builds a conflict from the specified SAT conflict.  
	 * 
	 * @param con the SAT conflict, i.e., the set of variable 
	 * that cannot be assigned the value this hypothesis suggests.  
	 * @param n the number of time steps.  
	 * @param varass the variable assigner that explain what each SAT variable is about.  
	 * */
	public SequentialConflict buildConflict(List<Integer> con, 
			int n, VariableAssigner varass) {
		final Set<Integer> vars = new HashSet<Integer>();
		for (final int lit: con) {
			final int var = Math.abs(lit);
			vars.add(var);
		}
		
		final List<Pair<YAMLDEvent,Integer>> s = new ArrayList<Pair<YAMLDEvent,Integer>>(); 
		final List<Pair<YAMLDEvent,Integer>> l = new ArrayList<Pair<YAMLDEvent,Integer>>();
		final List<OrderBetweenFaults> i = new ArrayList<OrderBetweenFaults>();
		
		for (final YAMLDEvent fault: _faultEvents) {
			final int occ = nbOccurences(fault);
			if (occ != 0) {
				final VariableSemantics sem = new XFaultsAtTimeT(fault, occ, n-1);
				final int lit = varass.surelyGetVariable(sem);
				if (vars.contains(lit)) {
					l.add(new Pair<YAMLDEvent, Integer>(fault, occ));
				}
			}
			{
				final VariableSemantics sem = new XFaultsAtTimeT(fault, occ+1, n-1);
				final int lit = varass.surelyGetVariable(sem);
				if (vars.contains(lit)) {
					s.add(new Pair<YAMLDEvent, Integer>(fault, occ));
				}
			}
		}
		
		for (int j=0 ; j<getNbFaults()-1 ; j++) {
			final OrderBetweenFaults sem = new OrderBetweenFaults(this, j);
			final int lit = varass.surelyGetVariable(sem);
			if (vars.contains(lit)) {
				i.add(sem);
			}
		}
		
		final SequentialConflict result = new SequentialConflict(s, l, i);
		return result;
		
	}
	
	private void searchOrAllocate(VariableLoader loader, MapVariableAssigner map, 
			VariableAssigner otherMap, VariableSemantics sem) {
		int var = 0;
		if (otherMap != null) {
			var = otherMap.getVariable(sem);
		}
		if (var == 0) {
//			System.out.println("Creating a variable for " + sem);
			var = loader.allocate(1);
		}
		map.add(sem, var);
	}
	
	private VariableAssigner createVariablesForHypo(VariableLoader loader, int n,
			VariableAssigner varass, SearchType st) {
		final MapVariableAssigner result = new MapVariableAssigner();
		
		for (final YAMLDEvent ev: _faultEvents) {
			final int occ = nbOccurences(ev);
			
			for (int t=0 ; t<n ; t++) {
				for (int x=0 ; x<=occ+1 ; x++) {
					final VariableSemantics sem = new XFaultsAtTimeT(ev, x, t);
					searchOrAllocate(loader, result, varass, sem);
				}
			}
		}
		
		for (int i=0 ; i<_seq.size()-1 ; i++) {
			final VariableSemantics sem = new OrderBetweenFaults(this, i);
			int var = 0;
			if (varass != null) {
				var = varass.getVariable(sem);
			}
			if (var == 0) {
				var = loader.allocate(1);
			}
			searchOrAllocate(loader, result, varass, sem);
		}
		
		return result;
	}
	
	private SequentialHypothesis[] getPrefixes() {
		final int size = _seq.size();
		final SequentialHypothesis[] hypos = new SequentialHypothesis[size+1];
		{
			final List<YAMLDEvent> evts = new ArrayList<YAMLDEvent>();
			for (int pos=0 ; pos<size ; pos++) {
				final SequentialHypothesis h = new SequentialHypothesis(evts, _faultEvents, _nbTrans);
				hypos[pos] = h;
				evts.add(_seq.get(pos));
			}
			hypos[size] = this;
		}
		
		return hypos;
	}
	
	private VariableAssigner createVariablesForSubHypo(VariableLoader loader, int n,
			VariableAssigner varass) {
		final MapVariableAssigner result = new MapVariableAssigner();
		
		final int size = _seq.size();
		
		final SequentialHypothesis[] hypos = getPrefixes();
		
		for (int pos=0 ; pos<=size ; pos++) {
			for (int t=0 ; t<=n ; t++) {
				final SubHypoOfHypoRecognised<SequentialHypothesis> sem = 
					new SubHypoOfHypoRecognised<SequentialHypothesis>(hypos[pos], t);
				searchOrAllocate(loader, result, varass, sem);
			}
		}
		
		return result;
	}

	public VariableAssigner createVariables(VariableLoader loader, 
			VariableAssigner varass, HypothesisSubspaceType hss, SearchType st) {
		if (hss == HypothesisSubspaceType.HYPO) {
			return createVariablesForHypo(loader,_nbTrans,varass,st);
		}
		
		if (hss == HypothesisSubspaceType.SUB && 
				(st == SearchType.INCLUDE || st == SearchType.EXCLUDE)) {
			return createVariablesForSubHypo(loader,_nbTrans,varass);
		}
		
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	private List<Integer> generateSATForHypo(VariableAssigner eventAss,
			VariableAssigner hypoAss, ClauseStream out, int n,
			SearchType st) {
		// The number of occurrences.  
		for (final YAMLDEvent ev: _faultEvents) {
			final int occ = nbOccurences(ev);
			for (int t=0 ; t<n ; t++) {
				// OCC(0,t)
				final VariableSemantics varsem = new XFaultsAtTimeT(ev, 0, t);
				final int lit = hypoAss.surelyGetVariable(varsem);
				out.put(lit);
			}
			for (int x=2 ; x<= occ+1 ; x++) {
				// !OCC(x,0)
				final VariableSemantics varsem = new XFaultsAtTimeT(ev, x, 0);
				final int lit = hypoAss.surelyGetVariable(varsem);
				out.put(-lit);
			}
			// OCC(1,0) <-> ev@0
			{
				final int var1, var2;
				{
					final VariableSemantics varsem = new XFaultsAtTimeT(ev, 1, 0);
					var1 = hypoAss.surelyGetVariable(varsem);
				}
				{
					final VariableSemantics varsem = new EventOccurrence(ev, 0);
					var2 = eventAss.surelyGetVariable(varsem);
				}
				out.put(-var1,  var2);
				out.put( var1, -var2);
			}
			for (int x=1 ; x<= occ+1 ; x++) {
				for (int t=1 ; t<n ; t++) {
					// OCC(x,t) <-> OCC(x,t-1) \/ (OCC(x-1,t-1) /\ ev@t)
					// a <-> b \/ (c /\ d)
					// Reformulated: 
					// a -> b \/ c
					// a -> b \/ d
					// b -> a
					// c /\ d -> a
					final int a, b, c, d;
					{
						final VariableSemantics varsem = new XFaultsAtTimeT(ev, x, t);
						a = hypoAss.surelyGetVariable(varsem);
					}
					{
						final VariableSemantics varsem = new XFaultsAtTimeT(ev, x, t-1);
						b = hypoAss.surelyGetVariable(varsem);
					}
					{
						final VariableSemantics varsem = new XFaultsAtTimeT(ev, x-1, t-1);
						c = hypoAss.surelyGetVariable(varsem);
					}
					{
						final VariableSemantics varsem = new EventOccurrence(ev, t);
						d = eventAss.surelyGetVariable(varsem);
					}
					out.put(-a, c); // Improvement here
					out.put(-a, b, d);
					out.put(-b, a); 
					out.put(-c, -d, a);
				}
			}
		}
		
		// The order
		for (int i=0 ; i<_seq.size()-1 ; i++) {
			final int orderLit;
			{
				final VariableSemantics sem = new OrderBetweenFaults(this, i);
				orderLit = hypoAss.surelyGetVariable(sem);
			}
			
			final YAMLDEvent before = getFault(i);
			final YAMLDEvent after = getFault(i+1);
			final int posBefore = nbOccurrencesBefore(i);
			final int posAfter = nbOccurrencesBefore(i+1);
			{ // 'after' impossible at time 0
				final VariableSemantics sem = new XFaultsAtTimeT(after, posAfter, 0);
				final int lit = hypoAss.surelyGetVariable(sem);
				out.put(-orderLit, -lit);
			}
			for (int t=1 ; t<n ; t++) {
				final int litAfter, litBefore;
				{
					final VariableSemantics sem = new XFaultsAtTimeT(after, posAfter, t);
					litAfter = hypoAss.surelyGetVariable(sem);
				}
				{
					final VariableSemantics sem = new XFaultsAtTimeT(before, posBefore, t-1);
					litBefore = hypoAss.surelyGetVariable(sem);
				}
				out.put(-orderLit, -litAfter, litBefore);
			}
		}
		
		final List<Integer> ass = new ArrayList<Integer>();
		for (final YAMLDEvent ev: _faultEvents) {
			final int occ = nbOccurences(ev);
			{
				final VariableSemantics sem = new XFaultsAtTimeT(ev, occ, n-1);
				final int lit = hypoAss.surelyGetVariable(sem);
				ass.add(lit);
			}
			{
				final VariableSemantics sem = new XFaultsAtTimeT(ev, occ+1, n-1);
				final int lit = -hypoAss.surelyGetVariable(sem);
				ass.add(lit);
			}
		}
		for (int i=0 ; i<_seq.size()-1 ; i++) {
			final int orderLit;
			{
				final VariableSemantics sem = new OrderBetweenFaults(this, i);
				orderLit = hypoAss.surelyGetVariable(sem);
			}
			ass.add(orderLit);
		}
			
		if (st == SearchType.CONFLICT) {
			return ass;
		} else if (st == SearchType.INCLUDE) {
			for (final int lit: ass) {
				out.put(lit);
			}
		} else { // EXCLUDE
			for (final int lit: ass) {
				out.put(-lit);
			}
			
		}
		
		return null;
	}
	
	private List<Integer> generateSATForSubHypo(VariableAssigner eventAss,
			VariableAssigner hypoAss, ClauseStream out, int n,
			SearchType st) {
		final int size = _seq.size();
		
		final SequentialHypothesis[] hypos = getPrefixes();
		
		// Initial time step
		for (int pos=1 ; pos <= size ; pos++) {
			final VariableSemantics sem = new SubHypoOfHypoRecognised<Hypothesis>(hypos[pos], 0);
			final int var = hypoAss.surelyGetVariable(sem);
			out.put(-var);
		}
		// Position 0
		for (int t=0 ; t<=n ; t++) {
			final VariableSemantics sem = new SubHypoOfHypoRecognised<Hypothesis>(hypos[0], t);
			final int var = hypoAss.surelyGetVariable(sem);
			out.put(var);
		}
		
		// All other time steps.  
		for (int t=0 ; t<n ; t++) {
			for (int pos=0 ; pos< size ; pos++) {
				final int a, b, c, d;
				{
					final VariableSemantics sem = 
						new SubHypoOfHypoRecognised<Hypothesis>(hypos[pos+1], t+1);
					a = hypoAss.surelyGetVariable(sem);
				}
				{
					final VariableSemantics sem = 
						new SubHypoOfHypoRecognised<Hypothesis>(hypos[pos+1], t);
					b = hypoAss.surelyGetVariable(sem);
				}
				{
					final VariableSemantics sem =  
						new SubHypoOfHypoRecognised<Hypothesis>(hypos[pos], t);
					c = hypoAss.surelyGetVariable(sem);
				}
				{
					final VariableSemantics sem = new EventOccurrence(_seq.get(pos), t);
					d = eventAss.surelyGetVariable(sem);
				}

				out.put(-a, c);
				out.put(-a, b, d);
				out.put(-b, a);
				out.put(-c, -d, a);
			}
		}
		
		{
			final VariableSemantics sem =  
				new SubHypoOfHypoRecognised<Hypothesis>(hypos[size], n);
			final int var = hypoAss.surelyGetVariable(sem);
			if (st == SearchType.INCLUDE) {
				out.put(var);
			} else if (st == SearchType.EXCLUDE) {
				out.put(-var);
			}
		}
		
		return null;
	}

	public List<Integer> generateSAT(VariableAssigner eventAss,
			VariableAssigner hypoAss, ClauseStream out, 
			HypothesisSubspaceType hss, SearchType st) {
		if (hss == HypothesisSubspaceType.HYPO) {
			return generateSATForHypo(eventAss, hypoAss, out, _nbTrans, st);
		}
		
		if (hss == HypothesisSubspaceType.SUB && 
				(st == SearchType.INCLUDE || st == SearchType.EXCLUDE)) {
			return generateSATForSubHypo(eventAss, hypoAss, out, _nbTrans, st);
		}
		
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
}
