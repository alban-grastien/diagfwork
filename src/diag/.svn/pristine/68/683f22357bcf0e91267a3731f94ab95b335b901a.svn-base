package diag.reiter.hypos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lang.MMLDRule;
import lang.YAMLDComponent;
import lang.YAMLDEvent;

import util.MMLDGlobalTransition;
import util.Scenario;
import diag.EventOccurrence;
import diag.reiter.HypothesisSpace;
import diag.reiter.HypothesisSubspace.HypothesisSubspaceType;
import diag.reiter.HypothesisSubspace.SearchType;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.MapVariableAssigner;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;

public class MultiSetHypothesisSpace 
implements HypothesisSpace<MultiSetHypothesis, MultiSetConflict> {
	
	/**
	 * The set of faulty events.  
	 * */
	private final UnmodifiableSet<YAMLDEvent> _faults;
	
	/**
	 * The number of time steps.  
	 * */
	private final int _n;
	
	/**
	 * Builds a multi set hypothesis space defined on the specified set of faults.  
	 * 
	 * @param faults the set of events that are considered faulty.
	 * @param n the number of time steps.    
	 * */
	public MultiSetHypothesisSpace(UnmodifiableSet<YAMLDEvent> faults, int n) {
		_faults = faults;
		_n = n;
	}

	/**
	 * Builds a multi set hypothesis space defined on the specified set of faults.  
	 * 
	 * @param faults the set of events that are considered faulty.  
	 * @param n the number of time steps.    
	 * */
	public MultiSetHypothesisSpace(Set<YAMLDEvent> faults, int n) {
		this(new UnmodifiableSetConstructor<YAMLDEvent>(faults).getSet(),n);
	}

	@Override
	public MultiSetConflict buildConflict(MultiSetHypothesis h,
			List<Integer> con, int n, VariableAssigner varass) {
		final Set<Integer> vars = new HashSet<Integer>();
		for (final int lit: con) {
			final int var = Math.abs(lit);
			vars.add(var);
		}
		
		final Map<YAMLDEvent,Integer> s = new HashMap<YAMLDEvent,Integer>(); 
		final Map<YAMLDEvent,Integer> l = new HashMap<YAMLDEvent,Integer>();
		
		for (final YAMLDEvent fault: _faults) {
			final int occ = h.nbOccurrences(fault);
			if (occ != 0) {
				final VariableSemantics sem = new XFaultsAtTimeT(fault, occ, _n);
				final int lit = varass.surelyGetVariable(sem);
				if (vars.contains(lit)) {
					l.put(fault, occ);
				}
			}
			{
				final VariableSemantics sem = new XFaultsAtTimeT(fault, occ+1, _n);
				final int lit = varass.surelyGetVariable(sem);
				if (vars.contains(lit)) {
					s.put(fault, occ);
				}
			}
		}
		
		final MultiSetConflict result = new MultiSetConflict(s, l);
		return result;
	}

	@Override
	public UnmodifiableList<MultiSetHypothesis> createSubHypotheses(
			MultiSetHypothesis hypothesis, MultiSetConflict c) {
		final UnmodifiableListConstructor<MultiSetHypothesis> cons = 
			new UnmodifiableListConstructor<MultiSetHypothesis>();
		
		for (final YAMLDEvent f: c.minSet()) {
			int current = hypothesis.nbOccurrences(f);
			final int min = c.getMin(f);
			MultiSetHypothesis multi = hypothesis;
			while (current <= min) {
				multi = new MultiSetHypothesis(multi, f);
				current++;
			}
			cons.addElement(multi);
		}
		
		return cons.getList();
	}

	@Override
	public VariableAssigner createVariables(MultiSetHypothesis h,
			VariableLoader loader, VariableAssigner varass,
			HypothesisSubspaceType hss, SearchType st) {
		final MapVariableAssigner result = new MapVariableAssigner();
		
		for (final YAMLDEvent f: _faults) {
			final int nb = h.nbOccurrences(f);
			
			for (int i=0 ; i<=nb+1 ; i++) {
				for (int t=0 ; t<=_n ; t++) {
					final VariableSemantics sem = new XFaultsAtTimeT(f, i, t);
					int var = 0; 
					if (varass != null) {
						var = varass.getVariable(sem);
					}
					if (var == 0) {
						var = loader.allocate(1);
					}
					result.add(sem, var);
				}
			}
		}
		
		return result;
	}

	@Override
	public List<Integer> generateSAT(MultiSetHypothesis h,
			VariableAssigner eventAss, VariableAssigner hypoAss,
			ClauseStream out, HypothesisSubspaceType hss, SearchType st) {
		
		// Should be possible to factorize with SequentialHypothesisSpace
		// --> put it in XFaultsAtTimeT.java?
		for (final YAMLDEvent f: _faults) {
			final int nb = h.nbOccurrences(f);
			
			for (int t =0 ; t<=_n ; t++) {
				final VariableSemantics sem = new XFaultsAtTimeT(f, 0, t);
				final int var = hypoAss.surelyGetVariable(sem);
				out.put(var);
			}
			
			for (int i=1 ; i<=nb+1 ; i++) {
				final VariableSemantics sem = new XFaultsAtTimeT(f, i, 0);
				final int var = hypoAss.surelyGetVariable(sem);
				out.put(-var);
			}
			
			for (int i=0 ; i<nb+1 ; i++) {
				for (int t=0 ; t<_n ; t++) {
					final int a, b, c, d;
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, i, t);
						a = hypoAss.surelyGetVariable(sem);
					}
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, i+1, t);
						b = hypoAss.surelyGetVariable(sem);
					}
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, i+1, t+1);
						c = hypoAss.surelyGetVariable(sem);
					}
					{
						final VariableSemantics sem = new EventOccurrence(f, t);
						d = eventAss.surelyGetVariable(sem);
					}

					out.put(-c, b, d);
					out.put(-c, a);
					out.put(-a, -d, c);
					out.put(-b, c);
				}
			}
		}
		
		if (hss == HypothesisSubspaceType.HYPO) {
			if (st == SearchType.CONFLICT) {
				final List<Integer> result = new ArrayList<Integer>();
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);

					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						result.add(var);
					}
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb+1, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						result.add(-var);
					}
				}
				return result;
			} else if (st == SearchType.EXCLUDE) {
				final List<Integer> clause = new ArrayList<Integer>();
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);

					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						clause.add(-var);
					}
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb+1, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						clause.add(var);
					}
				}
				out.put(clause);
				return null;
			} else {
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);

					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						out.put(var);
					}
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb+1, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						out.put(-var);
					}
				}
				return null;
			}
		} else if (hss == HypothesisSubspaceType.SUB) {
			if (st == SearchType.CONFLICT) {
				final List<Integer> result = new ArrayList<Integer>();
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);

					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						result.add(var);
					}
				}
				return result;
			} else if (st == SearchType.EXCLUDE) {
				final List<Integer> clause = new ArrayList<Integer>();
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);

					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						clause.add(-var);
					}
				}
				out.put(clause);
				return null;
			} else // (st == SearchType.INCLUDE) 
			{
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);

					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						out.put(var);
					}
				}
				return null;
			}
		} else //if (hss == HypothesisSubspaceType.SUPER) 
		{
			if (st == SearchType.CONFLICT) {
				final List<Integer> result = new ArrayList<Integer>();
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);
					
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb+1, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						result.add(-var);
					}
				}
				return result;
			} else if (st == SearchType.EXCLUDE) {
				final List<Integer> clause = new ArrayList<Integer>();
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb+1, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						clause.add(var);
					}
				}
				out.put(clause);
				return null;
			} else //if (st == SearchType.INCLUDE)
			{
				for (final YAMLDEvent f: _faults) {
					final int nb = h.nbOccurrences(f);
					{
						final VariableSemantics sem = new XFaultsAtTimeT(f, nb+1, _n);
						final int var = hypoAss.surelyGetVariable(sem);
						out.put(-var);
					}
				}
				return null;
			}
			
		}
	}

	@Override
	public MultiSetHypothesis getHypothesis(Scenario sce) {
		final Map<YAMLDEvent,Integer> map = new HashMap<YAMLDEvent, Integer>();
		
		for (int i=0 ; i<sce.nbTrans() ; i++) {
			final MMLDGlobalTransition gt = sce.getMMLDTrans(i);
			for (final YAMLDComponent comp: gt.affectedComponents()) {
				final MMLDRule r = gt.getRule(comp);
				for (final YAMLDEvent e: r.getGeneratedEvents()) {
					if (!_faults.contains(e)) {
						continue;
					}
					
					final Integer integer = map.get(e);
					final int newValue = (integer == null)? 1 : integer+1;
					map.put(e, newValue);
				}
			}
		}
		
		return new MultiSetHypothesis(map);
	}

	@Override
	public boolean hits(MultiSetHypothesis h, MultiSetConflict c) {
		return !c.satisfied(h);
	}

	@Override
	public boolean isSubHypothesis(MultiSetHypothesis h1, MultiSetHypothesis h2) {
		return MultiSetHypothesis.isSubHypothesis(h1, h2);
	}

	@Override
	public UnmodifiableSet<MultiSetHypothesis> minimalHypotheses() {
		final UnmodifiableSetConstructor<MultiSetHypothesis> c = 
			new UnmodifiableSetConstructor<MultiSetHypothesis>();
		c.add(new MultiSetHypothesis());
		
		return c.getSet();
	}

}
