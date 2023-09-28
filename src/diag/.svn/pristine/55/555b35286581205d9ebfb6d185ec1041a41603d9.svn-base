package diag;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import util.InclusionOrdering;

import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.MMLDTransition;
import lang.Network;
import lang.Path;
import lang.YAMLDAndFormula;
import lang.YAMLDComponent;
import lang.YAMLDConstraint;
import lang.YAMLDDVar;
import lang.YAMLDEqFormula;
import lang.YAMLDEvent;
import lang.YAMLDExistsPath;
import lang.YAMLDExpr;
import lang.YAMLDFalse;
import lang.YAMLDFormula;
import lang.YAMLDGenericVar;
import lang.YAMLDNotFormula;
import lang.YAMLDOrFormula;
import lang.YAMLDStringExistsPath;
import lang.YAMLDTrue;
import lang.YAMLDValue;
import lang.YAMLDVar;

import edu.supercom.util.Equivalence;
import edu.supercom.util.EquivalenceClass;
import edu.supercom.util.sat.AbstractClauseStream;
import edu.supercom.util.sat.Clause;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.LiteralShifter;
import edu.supercom.util.sat.MapVariableAssigner;
import edu.supercom.util.sat.ShiftClauseStream;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;

/**
 * The variable assigner gives the literal
 * */
public class LiteralAssigner implements VariableAssigner {

	public final int _timeShift;

	public final int _staticShift;

	public final int _stateVars;
	
	public final Map<Object, Integer> _basic;

	public final Network _net;
	
	public final int _firstTime;
	public final int _lastTime;
	
	// For each formula on a path, we build an InclusionOrdering to factorise the computation.  
	public final Map<YAMLDFormula,InclusionOrdering<YAMLDComponent>> _ios;

	public LiteralAssigner(VariableLoader loader, Network net, int maxTime) {
        this(loader, net, 0, maxTime);
    }
	
	public LiteralAssigner(VariableLoader loader, Network net, int firstTime, int lastTime) {
//		_maxTime = maxTime;
        _firstTime = firstTime;
        _lastTime = lastTime;
		_ios = new HashMap<YAMLDFormula, InclusionOrdering<YAMLDComponent>>();
		_net = net;

		final int[] nbVar = { 1 };
		final Map<Object, Integer> basic = new HashMap<Object, Integer>();

		// The state variables.
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final YAMLDVar var : comp.vars()) {
				generateLiteralsForVariable(basic, var, nbVar);
			}
		}

		// The dependent variables.
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final YAMLDDVar var : comp.dvars()) {
				generateLiteralsForVariable(basic, var, nbVar);
			}
		}

		// The constraints formulas
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final YAMLDDVar var : comp.dvars()) {
				if (!net.isRelevant(var)) {
					continue;
				}
				for (final YAMLDConstraint c : var.getConstraints()) {
					final YAMLDFormula f = c.getPrecondition();
					addVariables(basic, f, nbVar, comp);
				}
			}
		}
		
		_stateVars = nbVar[0] - 1;
		
		// The transition and rules formulas
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final MMLDTransition tr: comp.transitions()) {
				for (final YAMLDFormula f: tr.getPreconditions()) {
					addVariables(basic, f, nbVar, comp);
				}
				for (final MMLDRule r: tr.getRules()) {
					addVariables(basic, r.getCondition(), nbVar, comp);
				}
			}
		}
		
		// Creates an equivalence class for each variable semantics.  
		// Equivalence will first be checked and the SAT variables will be created for each class of equivalence.  
		// Two variable semantics are equivalent iff their corresponding SAT variable is identical.  
		final Equivalence<DatedSemantic<?>> eq = new Equivalence<DatedSemantic<?>>();

		initializeEquivalences(eq,net);
		computeEquivalences(eq,net);
		
		// Generates the SAT variables
		for (final EquivalenceClass<DatedSemantic<?>> cl: eq.classes()) {
			final int var = nbVar[0];
			for (final DatedSemantic<?> sem: cl) {
				basic.put(sem.getCore(), var);
			}
			nbVar[0]++;
		}

		_timeShift = nbVar[0] - 1;
		_staticShift = loader.allocate(_timeShift * (_lastTime - _firstTime +1)); // That should be way too many.
//		_staticShift = loader.allocate((_timeShift * maxTime) + _stateVars);
		_basic = Collections.unmodifiableMap(basic);
	}
	
	private void generateLiteralsForVariable(Map<Object, Integer> basic , YAMLDGenericVar var, int[] nbVar) {
		final List<YAMLDValue> domain = var.domain();
		if (domain.size() == 2) {
			final int satVar;
			{
				final YAMLDValue v1 = domain.get(0);
				final Assignment ass1 = new Assignment(var, v1, 0);
				satVar = addVariable(basic, ass1, nbVar);
			}
			{
				final YAMLDValue v2 = domain.get(1);
				final Assignment ass2 = new Assignment(var, v2, 0);
				basic.put(ass2.getCore(), -satVar);
			}
		} else {
			for (final YAMLDValue val : domain) {
				final Assignment ass = new Assignment(var, val, 0);
				addVariable(basic, ass, nbVar);
			}
		}
	}

	private int addVariable(Map<Object, Integer> b, DatedSemantic<?> ds,
			int[] nbVar) {
		final int result = nbVar[0];
		b.put(ds.getCore(), result);
		nbVar[0]++;
		return result;

//		System.out.println("Creating a new variable for ");
//		System.out.println(ds.toString());
//		System.out.println();
	}

	private void addVariables(Map<Object, Integer> b, YAMLDFormula f,
			int[] nbVar, final YAMLDComponent con) {
		final FormulaHolds fh = new FormulaHolds(f, con, 0);
		if (b.containsKey(fh.getCore())) {
			return; // already done
		}

		if (f instanceof YAMLDTrue) {
			addVariable(b, new FormulaHolds(f, con, 0), nbVar);
			return;
		}

		if (f instanceof YAMLDFalse) {
			addVariable(b, new FormulaHolds(f, con, 0), nbVar);
			return;
		}

		if (f instanceof YAMLDEqFormula) { 
			// Assuming the equality is defined like this: var = val or by a trivial val = val
			final YAMLDEqFormula eq = (YAMLDEqFormula)f;
			final YAMLDExpr ex = eq.expr1();
			if (ex instanceof YAMLDValue) {
				addVariables(b, YAMLDTrue.TRUE, nbVar, null);
				addVariables(b, YAMLDFalse.FALSE, nbVar, null);
			} else {
				addVariable(b, new FormulaHolds(f, con, 0), nbVar);
			}
			
			return;
		}

		if (f instanceof YAMLDNotFormula) {
			final YAMLDNotFormula nf = (YAMLDNotFormula) f;
			addVariables(b, nf.getOp(), nbVar, con);
			return;
		}

		addVariable(b, fh, nbVar);
		//System.out.println("fh: " + fh);

		if (f instanceof YAMLDOrFormula) {
			final YAMLDOrFormula or = (YAMLDOrFormula) f;
			addVariables(b, or.getOp1(), nbVar, con);
			addVariables(b, or.getOp2(), nbVar, con);
			return;
		}

		if (f instanceof YAMLDAndFormula) {
			final YAMLDAndFormula and = (YAMLDAndFormula) f;
			addVariables(b, and.getOp1(), nbVar, con);
			addVariables(b, and.getOp2(), nbVar, con);
			return;
		}

		if (f instanceof YAMLDStringExistsPath || f instanceof YAMLDExistsPath) {
			final YAMLDExistsPath ex;
			if (f instanceof YAMLDExistsPath) {
				ex = (YAMLDExistsPath) f;
			} else {
				ex = ((YAMLDStringExistsPath) f).simplify(_net);
			}
			final Collection<Path> pathes = ex.getPathes();
			for (final Path path : pathes) {
				final YAMLDFormula f2 = ex.getCondition();
				final Set<YAMLDComponent> newPath = path.simplify(_net, f2);
				if (newPath == null) {
					continue;
				}
				
				final InclusionOrdering<YAMLDComponent> io;
				{
					InclusionOrdering<YAMLDComponent> tmp = _ios.get(f2);
					if (tmp == null) {
						tmp = new InclusionOrdering<YAMLDComponent>();
						_ios.put(f2, tmp);
					}
					io = tmp;
				}
//				System.out.println("Considering path " + path.toString());
				if (!newPath.isEmpty()) {
					io.addSet(newPath);
				} else {
					addVariables(b, YAMLDTrue.TRUE, nbVar, null);
				}
				
				final AllSatisfy as = new AllSatisfy(0,
						newPath, f2);
				addVariable(b, as, nbVar);
//				final YAMLDComponent first = path.first();
//				final YAMLDComponent last = path.last();
				for (final YAMLDComponent comp : newPath) {
					addVariables(b, f2, nbVar, comp);
				}
			}
			return;
		}

		throw new UnsupportedOperationException(
				"YAMLDFormula type not supported: " + f.getClass().getName());
	}

	@Override
	public int getVariable(VariableSemantics sem) {
		if (!(sem instanceof DatedSemantic<?>)) {
			return 0;
		}

		final DatedSemantic<?> ds = (DatedSemantic<?>) sem;
		final Object core = ds.getCore();
		int coreVar = _basic.get(core);
		if (coreVar == 0) {
			return 0;
		}
		final boolean pos = (coreVar > 0); // Is the literal positive?
		
		final int shift = _timeShift * (ds._time - _firstTime);
		final int var = Math.abs(coreVar) + shift + _staticShift;
		final int result = var * (pos? 1 : -1);
		
		return result;
	}

	@Override
	public void print(PrintStream out) {
		for (int i=_firstTime ; i<=_lastTime ; i++) {
			for (final Map.Entry<Object, Integer> entry : _basic.entrySet()) {
				final Object obj = entry.getKey();
				int coreLit = entry.getValue();
				if (coreLit == 0) {
					continue;
				}
				final boolean pos = (coreLit > 0); // Is the literal positive?
				final int shift = _timeShift * i;
				final int coreVar = Math.abs(coreLit);
				if (coreVar > _stateVars && i == _lastTime) {
					continue;
				}
				final int var = coreVar + shift + _staticShift;
				final int result = var * (pos? 1 : -1);
				System.out.println(obj.toString() + " @ " + i + " -> "+ result);
			}
		}
		
	}

	@Override
	public int surelyGetVariable(VariableSemantics sem) {
		final int result = getVariable(sem);
		if (result == 0) {
			throw new NoSuchElementException(sem.toString());
		}
		return result;
	}

    @Override
	public String toString() {
		return _basic.toString();
	}

	/**
	 * Creates a {@link ShiftClauseStream} from the specified clause stream 
	 * and from time 0 to specified time (excluded).  
	 * */
	public ShiftClauseStream getShiftedStream(ClauseStream out, int maxTime) {
		final Collection<LiteralShifter> shifters = new ArrayList<LiteralShifter>();
		for (int i=0 ; i<maxTime ; i++) {
			final LiteralShifter ls = new LiteralShifter(_staticShift, 
					_staticShift+_timeShift+(2*_stateVars)+1, // +1?  
					_timeShift*i);
			shifters.add(ls);
		}
		return new ShiftClauseStream(out, shifters);
	}
	
	// TODO: Deprecated because apparently buggy.
	@Deprecated
	public ClauseStream newShiftedStream(final ClauseStream out, final int maxTime) {
		return new AbstractClauseStream() {

			final int _min = _staticShift;
			final int _max = _staticShift+_timeShift+(2*_stateVars)+1;

			private final int shift(int lit) {
				final boolean pos = lit>0;
				final int var = pos? lit : -lit;
				if (var >= _max || var < _min) {
					return 0;
				}
				if (pos) {
					return _timeShift;
				}
				return -_timeShift;
			}
			
			@Override
			public boolean solve(Collection<Integer> ass) {
				return out.solve(ass);
			}
			
			@Override
			public boolean solve(int[] ass) {
				return out.solve(ass);
			}
			
			@Override
			public boolean solve() {
				return solve();
			}
			
			@Override
			public void put(int[] tab) {
				final int[] shifts = new int[tab.length];
				boolean shift = false;
				for (int i=0 ; i<tab.length ; i++) {
					shifts[i] = shift(tab[i]);
					shift = shift || (shifts[i] != 0);
				}
				out.put(tab);
				if (!shift) {
					return;
				}
				for (int j=1 ; j<maxTime ; j++) {
					for (int i=0 ; i<tab.length ; i++) {
						tab[i] = tab[i] + shifts[i];
					}
					out.put(tab);
				}
			}
			
			@Override
			public void put(int lit1, int lit2, int lit3) {
				final int shift1 = shift(lit1);
				final int shift2 = shift(lit2);
				final int shift3 = shift(lit3);
				out.put(lit1,lit2,lit3);
				if (shift1 == 0 && shift2 == 0 && shift3 == 0) {
					return;
				}
				for (int i=1 ; i<maxTime ; i++) {
					lit1 += shift1;
					lit2 += shift2;
					lit3 += shift3;
					out.put(lit1,lit2,lit3);
				}
			}
			
			@Override
			public void put(int lit1, int lit2) {
				final int shift1 = shift(lit1);
				final int shift2 = shift(lit2);
				out.put(lit1,lit2);
				if (shift1 == 0 && shift2 == 0) {
					return;
				}
				for (int i=1 ; i<maxTime ; i++) {
					lit1 += shift1;
					lit2 += shift2;
					out.put(lit1,lit2);
				}
			}
			
			@Override
			public void put(int lit) {
				final int shift = shift(lit);
				out.put(lit);
				if (shift == 0) {
					return;
				}
				for (int i=1 ; i<maxTime ; i++) {
					lit += shift;
					out.put(lit);
				}
			}
			
			@Override
			public void put(Clause clause) {
				final int[] tab = new int[clause.size()];
				for (int i=0 ; i<clause.size() ; i++) {
					tab[i] = clause.literal(i);
				}
				put(tab);
			}
			
			@Override
			public boolean[] model(int[] vars) {
				return out.model(vars);
			}
			
			@Override
			public boolean model(int var) {
				return out.model(var);
			}
			
			@Override
			public void close() {
				out.close();
			}
		};
	}
	
	public void testShifter(int time, YAMLDEvent event) {
		final int var1 = surelyGetVariable(new EventOccurrence(event, time));
		final LiteralShifter ls = new LiteralShifter(_staticShift, 
				_staticShift+_timeShift+_stateVars+1, // +1?  
				_timeShift*time);
		final int var2 = ls.renameLiteral(surelyGetVariable(new EventOccurrence(event, 0)));
		if (var1 != var2) {
			System.out.println("Difference!!!");
			System.exit(0);
		}
	}

	private void initializeEquivalences(Equivalence<DatedSemantic<?>> eq, Network net) {
		// The events
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final YAMLDEvent evt : comp.events()) {
				final EventOccurrence eo = new EventOccurrence(evt, 0);
				eq.add(eo);
			}
		}
		
		// The transitions
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final MMLDTransition tr: comp.transitions()) {
				final MMLDTransitionTrigger mtt = new MMLDTransitionTrigger(tr, 0);
				eq.add(mtt);
			}
		}
		
		// The transition rules
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final MMLDTransition tr: comp.transitions()) {
				for (final MMLDRule r: tr.getRules()) {
					final RuleTrigger rt = new RuleTrigger(0, r);
					eq.add(rt);
				}
			}
		}
		
		// The transition triggering
		for (final YAMLDComponent comp : net.getComponents()) {
			for (final MMLDTransition tr: comp.transitions()) {
				for (final YAMLDFormula prec: tr.getPreconditions()) {
					final PreconditionTriggersTransition ptt = new PreconditionTriggersTransition(tr, prec, 0);
					eq.add(ptt);
				}
			}
		}
	}
	
	private void computeEquivalences(Equivalence<DatedSemantic<?>> eq, Network net) {		
		// Equivalence between synchronised events 
		// (when an input event is associated with only one output event)
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final YAMLDEvent evt: comp.events()) {
				if (!evt.isInput()) {
					continue;
				}
				final Set<MMLDSynchro> synchs = evt.getSynchros();
				if (synchs.size() != 1) {
					continue;
				}
				final MMLDSynchro synchro = synchs.iterator().next();
				final YAMLDEvent ev2 = synchro.getEvent();
				final EventOccurrence sem1 = new EventOccurrence(evt, 0);
				final EventOccurrence sem2 = new EventOccurrence(ev2, 0);
				eq.equivalent(sem1, sem2);
			}
		}
		
		// Equivalence between events that take place in the same rules
		for (final YAMLDComponent comp: net.getComponents()) {
			final Map<YAMLDEvent,Set<MMLDRule>> eventToRules = new HashMap<YAMLDEvent, Set<MMLDRule>>();
			final List<YAMLDEvent> outputs = new ArrayList<YAMLDEvent>();
			for (final YAMLDEvent evt: comp.events()) {
				if (evt.isInput()) {
					continue;
				}
				eventToRules.put(evt, new HashSet<MMLDRule>(evt.getGeneratingRules()));
				outputs.add(evt);
			}
			for (int i=0 ; i<outputs.size() ; i++) {
				final YAMLDEvent e1 = outputs.get(i);
				for (int j=i+1 ; j<outputs.size() ; j++) {
					final YAMLDEvent e2 = outputs.get(j);
					if (eventToRules.get(e1).equals(eventToRules.get(e2))) {
						final EventOccurrence sem1 = new EventOccurrence(e1, 0);
						final EventOccurrence sem2 = new EventOccurrence(e2, 0);
						eq.equivalent(sem1, sem2);
					}
				}
			}
		}
		
		// Equivalence between events and rules 
		// (when an output event takes place only in a given rule)
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final YAMLDEvent evt: comp.events()) {
				if (evt.isInput()) {
					continue;
				}
				final Collection<MMLDRule> rules = evt.getGeneratingRules();
				if (rules.size() != 1) {
					continue;
				}
				final MMLDRule rule = rules.iterator().next();
				final EventOccurrence sem1 = new EventOccurrence(evt, 0);
				final RuleTrigger sem2 = new RuleTrigger(0, rule);
				eq.equivalent(sem1, sem2);
			}
		}
		
		// Equivalence between events and transitions 
		// (when an input event triggers only one transition and it is the only triggering thing for this transition)
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final YAMLDEvent evt: comp.events()) {
				if (!evt.isInput()) {
					continue;
				}
				final Collection<MMLDTransition> transes = evt.getTriggerableTransitions();
				if (transes.size() != 1) {
					continue;
				}
				final MMLDTransition trans = transes.iterator().next();
				//System.out.println("trans: " + trans.toFormattedString());
				if (!trans.getPreconditions().isEmpty()) {
					continue;
				}
				if (trans.getTriggeringEvents().size() != 1) {
					continue;
				}
				final EventOccurrence sem1 = new EventOccurrence(evt, 0);
				final MMLDTransitionTrigger sem2 = new MMLDTransitionTrigger(trans, 0);
				eq.equivalent(sem1, sem2);
			}
		}
		
		// Equivalence between rules and transitions 
		// (when a rule precondition is trivially true and it is the only rule of the transition)
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				final List<MMLDRule> rules = trans.getRules();
				if (rules.size() != 1) {
					continue;
				}
				final MMLDRule rule = rules.get(0);
				final YAMLDFormula f = rule.getCondition();
				if (f.isTriviallyTrue(net, comp)) {
					final MMLDTransitionTrigger sem1 = new MMLDTransitionTrigger(trans, 0);
					final RuleTrigger sem2 = new RuleTrigger(0, rule);
					eq.equivalent(sem1, sem2);
				}
			}
		}
		
		// Equivalence between rules and transitions (when the transition is spontaneous and the rule is unique)
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				if (!trans.isSpontaneous()) {
					continue;
				}
				final List<MMLDRule> rules = trans.getRules();
				if (rules.size() != 1) {
					continue;
				}
				final MMLDRule rule = rules.get(0);
				final MMLDTransitionTrigger sem1 = new MMLDTransitionTrigger(trans, 0);
				final RuleTrigger sem2 = new RuleTrigger(0, rule);
				eq.equivalent(sem1, sem2);
			}
		}
		
		// Equivalence between transition and precondition triggering 
		// if only one such triggering can generate the transition
		for (final YAMLDComponent comp: net.getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				if (!trans.getTriggeringEvents().isEmpty()) {
					continue;
				}
				final YAMLDFormula prec;
				{
					final Set<YAMLDFormula> precs = trans.getPreconditions();
					if (precs.size() != 1) {
						continue;
					}
					prec = precs.iterator().next();
				}
				final MMLDTransitionTrigger sem1 = new MMLDTransitionTrigger(trans, 0);
				final PreconditionTriggersTransition sem2 = 
					new PreconditionTriggersTransition(trans, prec, 0);
				eq.equivalent(sem1, sem2);
			}
		}
	}

	@Override
	public void copy(MapVariableAssigner map) {
		System.err.println("LiteralAssigner >> copy >> Not implemented yet");
	}
}
