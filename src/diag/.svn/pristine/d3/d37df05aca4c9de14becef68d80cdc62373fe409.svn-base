package diag.reiter.hypos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.MapVariableAssigner;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;

/**
 * A hypothesis space where the hypotheses are set hypothesis.  
 * 
 * @see SetHypothesis
 * */
public class SetHypothesisSpace implements HypothesisSpace<SetHypothesis, SetConflict> {
	
	/**
	 * The set of (actually unique) minimal hypotheses.  
	 * */
	public final UnmodifiableSet<SetHypothesis> _min;
	
	/**
	 * The set of faulty events.
	 * */
	public final UnmodifiableSet<YAMLDEvent> _faults;
	
	/**
	 * The number of time steps.  
	 * */
	final int _nbTrans;
	
	/**
	 * Builds a space of {@link SetHypothesis} defined on the specified set of faults.  
	 * 
	 * @param faults the set of faults.  
	 * @param nbTrans the number of time steps.  
	 * */
	public SetHypothesisSpace(UnmodifiableSet<YAMLDEvent> faults, int nbTrans) {
		_faults = faults;
		_nbTrans = nbTrans;
		
		final UnmodifiableSetConstructor<SetHypothesis> con = 
			new UnmodifiableSetConstructor<SetHypothesis>();
		con.add(new SetHypothesis(new HashSet<YAMLDEvent>()));
		_min = con.getSet();
	}
	
	/**
	 * Builds a space of {@link SetHypothesis} defined on the specified set of faults.  
	 * 
	 * @param faults the set of faults.  
	 * @param nbTrans the number of time steps.  
	 * */
	public SetHypothesisSpace(Set<YAMLDEvent> faults, int nbTrans) {
		this(new UnmodifiableSetConstructor<YAMLDEvent>(faults).getSet(), nbTrans);
	}

	@Override
	public SetConflict buildConflict(SetHypothesis h, List<Integer> satCon, int n,
			VariableAssigner varass) {
		final UnmodifiableSet<Integer> vars; 
		{
			final UnmodifiableSetConstructor<Integer> varCon = 
				new UnmodifiableSetConstructor<Integer>();
			for (final int lit: satCon) {
				final int var = Math.abs(lit);
				varCon.add(var);
			}
			vars = varCon.getSet();
		}
		

		final UnmodifiableSetConstructor<YAMLDEvent> pos = 
			new UnmodifiableSetConstructor<YAMLDEvent>();
		final UnmodifiableSetConstructor<YAMLDEvent> neg = 
			new UnmodifiableSetConstructor<YAMLDEvent>();
		for (final YAMLDEvent event: _faults) {
			final int var = varass.getVariable(new EventOccurred(event));
			if (vars.contains(var)) {
				if (h.contains(event)) {
					neg.add(event);
				} else {
					pos.add(event);
				}
			}
		}
		
		return new SetConflict(pos.getSet(), neg.getSet());
	}

	@Override
	public List<? extends SetHypothesis> createSubHypotheses(
			SetHypothesis hypothesis, SetConflict c) {
		final List<SetHypothesis> result = new ArrayList<SetHypothesis>();
		for (final YAMLDEvent f: c.getPositiveSet()) {
			result.add(new SetHypothesis(hypothesis, f));
		}
		return result;
	}
	
	private final void createVariable(
			VariableLoader loader, VariableAssigner varass,
			MapVariableAssigner result, YAMLDEvent e) {
		final VariableSemantics sem = new EventOccurred(e);
		int var = 0;
		if (varass != null) {
			var = varass.getVariable(sem);
		}
		if (var == 0) {
			var = loader.allocate(1);
		}
		result.add(sem, var);
	}
	
	private final void createFaultVariables(SetHypothesis h, 
			VariableLoader loader, VariableAssigner varass,
			MapVariableAssigner result) {
		for (final YAMLDEvent evt: h._actualFaults) {
			createVariable(loader, varass, result, evt);
		}
	}
	
	private final void createNonFaultVariables(SetHypothesis h, 
			VariableLoader loader, VariableAssigner varass,
			MapVariableAssigner result) {
		for (final YAMLDEvent evt: _faults) {
			if (h.contains(evt)) {
				continue;
			}
			createVariable(loader, varass, result, evt);
		}
	}

	@Override
	public VariableAssigner createVariables(SetHypothesis h,
			VariableLoader loader, VariableAssigner varass,
			HypothesisSubspaceType hss, SearchType st) {
		final MapVariableAssigner result = new MapVariableAssigner();
		
		if (hss == HypothesisSubspaceType.HYPO) {
			createFaultVariables(h, loader, varass, result);
			createNonFaultVariables(h, loader, varass, result);
		}
		
		if (hss == HypothesisSubspaceType.SUB) {
			createFaultVariables(h, loader, varass, result);
		}
		
		if (hss == HypothesisSubspaceType.SUPER) {
			createNonFaultVariables(h, loader, varass, result);
		}
		
		return result;
	}
	
	private final int generateEventSAT(YAMLDEvent e, 
			VariableAssigner eventAss, VariableAssigner hypoAss, ClauseStream out) {
		int var; 
		{
			final VariableSemantics sem = new EventOccurred(e);
			var = hypoAss.getVariable(sem);
		}
		
		final List<Integer> clause = new ArrayList<Integer>();
		clause.add(-var);
		for (int t=0 ; t<_nbTrans ; t++) {
			final VariableSemantics sem = new EventOccurrence(e, t);
			final int varT = eventAss.getVariable(sem);
			out.put(-varT, var);
			clause.add(varT);
		}
		
		out.put(clause);
		return var;
	}
	
	private final List<Integer> generateFaultSAT(SetHypothesis h,
			VariableAssigner eventAss, VariableAssigner hypoAss,
			ClauseStream out) {
		final List<Integer> result = new ArrayList<Integer>();
		for (final YAMLDEvent evt: h._actualFaults) {
			final int var = generateEventSAT(evt, eventAss, hypoAss, out);
			result.add(var);
		}
		return result;
	}
	
	private final List<Integer> generateNonFaultSAT(SetHypothesis h,
			VariableAssigner eventAss, VariableAssigner hypoAss,
			ClauseStream out) {
		final List<Integer> result = new ArrayList<Integer>();
		for (final YAMLDEvent evt: _faults) {
			if (h._actualFaults.contains(evt)) {
				continue;
			}
			final int var = generateEventSAT(evt, eventAss, hypoAss, out);
			result.add(-var);
		}
		return result;
	}

	@Override
	public List<Integer> generateSAT(SetHypothesis h,
			VariableAssigner eventAss, VariableAssigner hypoAss,
			ClauseStream out, HypothesisSubspaceType hss, SearchType st) {
		final List<Integer> literals;
		
		if (hss == HypothesisSubspaceType.HYPO) {
			final List<Integer> faults = generateFaultSAT(h, eventAss, hypoAss, out);
			final List<Integer> nonFaults = generateNonFaultSAT(h, eventAss, hypoAss, out);
			
			literals = new ArrayList<Integer>();
			literals.addAll(faults);
			literals.addAll(nonFaults);
		} else 
		if (hss == HypothesisSubspaceType.SUB) {
			literals = generateFaultSAT(h, eventAss, hypoAss, out);
		} else // hss == HypothesisSubspaceType.SUPER) 
		{
			literals = generateNonFaultSAT(h, eventAss, hypoAss, out);
		}
		
		if (st == SearchType.INCLUDE) {
			for (int lit: literals) {
				out.put(lit);
			}
		} else 
		if (st == SearchType.EXCLUDE) {
			final List<Integer> clause = new ArrayList<Integer>();
			for (int lit: literals) {
				clause.add(-lit);
			}
			out.put(clause);
		} else // (st == SearchType.CONFLICT) 
		{
//			System.out.println("Assumptions: " + literals);
//			hypoAss.print(System.out);
			return literals;
		}
		
		
		return null;
	}

	@Override
	public SetHypothesis getHypothesis(Scenario sce) {
		final Set<YAMLDEvent> actualFaults = new HashSet<YAMLDEvent>();
		//MMLDGlobalTransition getMMLDTrans(int i);
		for (int i=0 ; i<sce.nbTrans() ; i++) {
			final MMLDGlobalTransition gtrans = sce.getMMLDTrans(i);
			for (final YAMLDComponent comp: gtrans.affectedComponents()) {
				final MMLDRule rule = gtrans.getRule(comp);
				for (final YAMLDEvent event: rule.getGeneratedEvents()) {
					if (_faults.contains(event)) {
						actualFaults.add(event);
					}
				}
			}
		}
		
		return new SetHypothesis(actualFaults);
	}

	@Override
	public boolean hits(SetHypothesis h, SetConflict c) {
		for (final YAMLDEvent f: c.getNegativeSet()) {
			if (!h.contains(f)) {
				return true;
			}
		}
		for (final YAMLDEvent f: c.getPositiveSet()) {
			if (h.contains(f)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean isSubHypothesis(SetHypothesis h1, SetHypothesis h2) {
		return SetHypothesis.isSubHypothesis(h1, h2);
	}

	@Override
	public Set<? extends SetHypothesis> minimalHypotheses() {
		return _min;
	}
}
