package diag.symb.javabdd;

import diag.symb.SymbolicDiagnosis;
import diag.symb.SymbolicFramework;
import edu.supercom.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.MMLDTransition;
import lang.Network;
import lang.State;
import lang.VariableValue;
import lang.YAMLDAndFormula;
import lang.YAMLDAssignment;
import lang.YAMLDComponent;
import lang.YAMLDEqFormula;
import lang.YAMLDEvent;
import lang.YAMLDExpr;
import lang.YAMLDFalse;
import lang.YAMLDFormula;
import lang.YAMLDGenericVar;
import lang.YAMLDNotFormula;
import lang.YAMLDOrFormula;
import lang.YAMLDTrue;
import lang.YAMLDValue;;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;
import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;import lang.YAMLDVar;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;

/**
 * A <code>JavaBDDFramework</code>, i.e., a Java BDD framework, 
 * is a symbolic framework using the Java BDD implementation.  
 * 
 * @author Alban Grastien
 */
@Deprecated
public class JavaBDDFramework 
implements SymbolicFramework<JavaBDDSetOfStates, JavaBDDUnionSetOfTransitions> {

    /**
     * Creates a new Java BDD framework using the specified BDD factory.  
     * 
     * @param fact the factory used to manipulate the BDDs.  
     */
    public JavaBDDFramework(BDDFactory fact) {
        _fact = fact;
        _eventToVariable = new HashMap<YAMLDEvent, Integer>();
        _stateVarToVariable = new HashMap<Pair<YAMLDGenericVar, YAMLDValue>, BDD>();
        _futureStateVarToVariable = new HashMap<Pair<YAMLDGenericVar, YAMLDValue>, BDD>();
        _nextToCurrent = _fact.makePair();
        _current = _fact.one();
        _events = _fact.one();
        _valueOfVariableUnchanged = new HashMap<YAMLDGenericVar, BDD>();
        _valueOfStateUnchanged = new HashMap<YAMLDComponent, BDD>();
    }
    
    /**
     * The mapping YAMLDEvent -> BDDVariable
     */
    private final Map<YAMLDEvent,Integer> _eventToVariable;
    
    /**
     * The list of event variables.  
     */
    private BDD _events;
    
    /**
     * The mapping assignment -> BDDVariable
     */
    private final Map<Pair<YAMLDGenericVar,YAMLDValue>,BDD> _stateVarToVariable;
    
    /**
     * The mapping assignment -> Next BDDVariable
     */
    private final Map<Pair<YAMLDGenericVar,YAMLDValue>,BDD> _futureStateVarToVariable;
    
    /**
     * The set of pairs (next var, current var).
     */
    private final BDDPairing _nextToCurrent;
    
    /**
     * The list of current state variables.  
     */
    private BDD _current;
    
    /**
     * The BDD Factory used to generate BDDs.  
     */
    private final BDDFactory _fact;
    
    /**
     * The BDD that indicates that the value 
     * of the specified variable is unchanged.  
     */
    private final Map<YAMLDGenericVar,BDD> _valueOfVariableUnchanged;
    
    /**
     * The BDD that indicates that the state 
     * of the specified component is unchanged.  
     */
    private final Map<YAMLDComponent,BDD> _valueOfStateUnchanged;
    
    // Creates a new BDD variable
    private int newBDDVariable() {
        return _fact.extVarNum(1);
    }
    
    // Returns the variable for specified event (creates the variable if non existing)
    private int getVariableFromEvent(YAMLDEvent e) {
        {
            final Integer result = _eventToVariable.get(e);
            if (result != null) {
                return result;
            }
        }
        
        final int result = newBDDVariable();
        _eventToVariable.put(e, result);
        _events = _events.and(_fact.ithVar(result));
        
        return result;
    }
    
    // Creates the variables for the binary state variable
    private void createVariablesForBinayStateVariable(YAMLDGenericVar var, List<YAMLDValue> domain) {
        assert (domain.size() == 2);
            
        final int result = newBDDVariable();
        final int nextResult = newBDDVariable();
        _nextToCurrent.set(nextResult, result);
        
        BDD currentBDD = _fact.ithVar(result);
        BDD nextBDD = _fact.ithVar(nextResult);
            
        for (final YAMLDValue val: domain) {
            final Pair<YAMLDGenericVar,YAMLDValue> ass = new Pair<YAMLDGenericVar, YAMLDValue>(var, val);
            _stateVarToVariable.put(ass, currentBDD);
            _futureStateVarToVariable.put(ass,nextBDD);
            currentBDD = currentBDD.not();
            nextBDD = nextBDD.not();
        }
        
        _current = _current.and(currentBDD);
    }
    
    private BDD buildBDDWithOnlyIndexedVariableTrue(int[] array, int index) {
        BDD result = _fact.one();
        
        for (int otherIndex = 0 ; otherIndex < array.length ; otherIndex++) {
            final int variable = array[otherIndex];
            BDD otherBDD = _fact.ithVar(variable);
            if (otherIndex != index) {
                otherBDD = otherBDD.not();
            }
            result = result.and(otherBDD);
        }
        
        return result;
    }
    
    private void createVariablesForNonBinaryStateVariable(YAMLDGenericVar var, List<YAMLDValue> domain) {
        final int domainSize = domain.size();
        
        // Create the variables
        final int[] currentVariables = new int[domainSize];
        final int[] nextVariables = new int[domainSize];
        for (int i=0 ; i<domainSize ; i++) {
            currentVariables[i] = newBDDVariable();
            nextVariables[i] = newBDDVariable();
            
            _current = _current.and(_fact.ithVar(currentVariables[i]));
            _nextToCurrent.set(nextVariables[i],currentVariables[i]);
        }
        
        for (int i=0 ; i<domainSize ; i++) {
            final YAMLDValue val = domain.get(i);
            BDD bddCurrent = buildBDDWithOnlyIndexedVariableTrue(currentVariables, i);
            BDD bddNext = buildBDDWithOnlyIndexedVariableTrue(nextVariables, i);
            final Pair<YAMLDGenericVar,YAMLDValue> ass = 
                    new Pair<YAMLDGenericVar, YAMLDValue>(var, val);
            _stateVarToVariable.put(ass, bddCurrent);
            _futureStateVarToVariable.put(ass,bddNext);
        }
    }
    
    // Returns the BDD for specified state variable (create the variable if non existing)
    private BDD getVariableFromStateVariable(YAMLDGenericVar var, YAMLDValue val) {
        final Pair<YAMLDGenericVar,YAMLDValue> ass = new Pair<YAMLDGenericVar, YAMLDValue>(var, val);
        
        {
            final BDD result = _stateVarToVariable.get(ass);
            if (result != null) {
                return result;
            }
        }
        
        final List<YAMLDValue> domain = var.domain();
        if (domain.size() == 2) {
            createVariablesForBinayStateVariable(var, domain);
        } else {
            createVariablesForNonBinaryStateVariable(var, domain);
        }
        
        return _stateVarToVariable.get(ass);
    }
    
    // Returns the BDD for the futur specified state variable (create the variable if non existing)
    private BDD getFutureVariableFromStateVariable(YAMLDGenericVar var, YAMLDValue val) {
        final Pair<YAMLDGenericVar,YAMLDValue> ass = new Pair<YAMLDGenericVar, YAMLDValue>(var, val);
        
        {
            final BDD result = _futureStateVarToVariable.get(ass);
            if (result != null) {
                return result;
            }
        }
        
        final List<YAMLDValue> domain = var.domain();
        if (domain.size() == 2) {
            createVariablesForBinayStateVariable(var, domain);
        } else {
            createVariablesForNonBinaryStateVariable(var, domain);
        }
        
        return _futureStateVarToVariable.get(ass);
    }
    
    public BDD bddEventOccurred(YAMLDEvent e) {
        assert (e != null);
        final int var = getVariableFromEvent(e);
        final BDD bdd = _fact.ithVar(var);
        return bdd;
    }
    
    @Override
    public JavaBDDUnionSetOfTransitions eventOccurred(YAMLDEvent e) {
        final BDD bdd = bddEventOccurred(e);
        final JavaBDDUnionSetOfTransitions result = new JavaBDDUnionSetOfTransitions(bdd);
        return result;
    }

    private BDD bddAtLeastOneEventOccurred(Collection<YAMLDEvent> es) {
        assert (es != null);
        BDD result = _fact.zero();
        
        for (final YAMLDEvent e: es) {
            final BDD bdd = bddEventOccurred(e);
            result = result.or(bdd);
        }
        return result;
    }
    
    @Override
    public JavaBDDUnionSetOfTransitions atLeastOneEventOccurred(Collection<YAMLDEvent> es) {
        final BDD bdd = bddAtLeastOneEventOccurred(es);
        final JavaBDDUnionSetOfTransitions result = new JavaBDDUnionSetOfTransitions(bdd);
        return result;
    }

    public BDD bddNonOccurrence(Collection<YAMLDEvent> es) {
        BDD result = _fact.one();
        
        for (final YAMLDEvent e: es) {
            final BDD bdd = bddEventOccurred(e);
            result = result.and(bdd.not());
        }
        
        return result;
    }
    
    @Override
    public JavaBDDUnionSetOfTransitions nonOccurrence(Collection<YAMLDEvent> es) {
        final BDD result = bddNonOccurrence(es);
        return new JavaBDDUnionSetOfTransitions(result);
    }

    @Override
    public JavaBDDUnionSetOfTransitions transitionFromState(JavaBDDSetOfStates sos) {
        return new JavaBDDUnionSetOfTransitions(sos.getBDD());
    }

//    @Override
    public JavaBDDSetOfTransitions intersection(Collection<JavaBDDSetOfTransitions> sots) {
        BDD result = _fact.one();
        
        for (final JavaBDDSetOfTransitions sot: sots) {
            result = result.and(sot.getBDD());
        }
        
        return new JavaBDDSetOfTransitions(result);
    }

    @Override
    public JavaBDDSetOfStates nextStates(JavaBDDUnionSetOfTransitions sot) {
        return sot.nextStates(_fact, _current, _events, _nextToCurrent);
    }

//    @Override
    public JavaBDDSetOfStates union(Collection<JavaBDDSetOfStates> soss) {
        BDD result = _fact.zero();
        
        for (final JavaBDDSetOfStates sos: soss) {
            final BDD bdd = sos.getBDD();
            result = result.or(bdd);
        }
        
        return new JavaBDDSetOfStates(result);
    }

    @Override
    public boolean equals(JavaBDDSetOfStates sos1, JavaBDDSetOfStates sos2) {
        final BDD bdd1 = sos1.getBDD();
        final BDD bdd2 = sos2.getBDD();
        return bdd1.equals(bdd2);
    }

    @Override
    public JavaBDDSetOfStates emptySetOfStates() {
        return new JavaBDDSetOfStates(_fact.zero());
    }

    @Override
    public JavaBDDSetOfStates setOfAllStates() {
        return new JavaBDDSetOfStates(_fact.one());
    }

    @Override
    public JavaBDDSetOfStates state(State s) {
        BDD result = _fact.one();
        
        for (final YAMLDGenericVar var: s.getNetwork().getStateVariables()) {
            final YAMLDValue val = s.getValue(var);
            if (val == null) {
                continue;
            }
            
            BDD tmp = getVariableFromStateVariable(var, val);
            result = result.and(tmp);
        }
        
        return new JavaBDDSetOfStates(result);
    }
    
    // Builds the BDD that represents the equality between the following expressions.  
    private BDD eq(YAMLDComponent comp, YAMLDExpr exp1, YAMLDExpr exp2) {
        
        if (exp1.equals(exp2)) {
            return _fact.one();
        }
        
        if (!(exp1 instanceof VariableValue)) {
            throw new UnsupportedOperationException("Can only deal with VariableValue in the first expression of equality "
                    + "(expression is " + exp1 + " = " + exp2 + ")");
        }
        
        if (!(exp2 instanceof YAMLDValue)) {
            throw new UnsupportedOperationException("Can only deal with Boolean constants in the second expression of equality");
        }
        
        final VariableValue vv = (VariableValue)exp1;
        final YAMLDGenericVar var = vv.variable();
        final YAMLDValue val = (YAMLDValue)exp2;
        return getVariableFromStateVariable(var, val);
    }
    
    // Builds the BDD that represents the specified formula in the current state
    public BDD formula(YAMLDComponent comp, YAMLDFormula f) {
        
        if (f instanceof YAMLDFalse) {
            return _fact.zero();
        }
        
        if (f instanceof YAMLDTrue) {
            return _fact.one();
        }
        
        if (f instanceof YAMLDNotFormula) {
            final YAMLDNotFormula not = (YAMLDNotFormula)f;
            return formula(comp, not.getOp()).not();
        }
        
        if (f instanceof YAMLDEqFormula) {
            final YAMLDEqFormula eq = (YAMLDEqFormula)f;
            return eq(comp, eq.expr1(), eq.expr2());
        }
        
        if (f instanceof YAMLDOrFormula) {
            final YAMLDOrFormula or = (YAMLDOrFormula)f;
            final BDD bdd1 = formula(comp, or.getOp1());
            final BDD bdd2 = formula(comp, or.getOp2());
            return bdd1.or(bdd2);
        }
        
        if (f instanceof YAMLDAndFormula) {
            final YAMLDAndFormula and = (YAMLDAndFormula)f;
            final BDD bdd1 = formula(comp, and.getOp1());
            final BDD bdd2 = formula(comp, and.getOp2());
            return bdd1.and(bdd2);
        }
        
        // YAMLDExistsPath or YAMLDStringExistsPath
        
        throw new UnsupportedOperationException("Operator not supported yet: " + f.getClass());
    }
    
    // Builds the BDD that represents: 
    // exactly one event in transEvents
    // exactly all events in ruleEvents
    // no other events in compEvents
    private BDD ruleEventsBDD(Set<YAMLDEvent> transEvents, Set<YAMLDEvent> ruleEvents, Set<YAMLDEvent> compEvents) {
        
        final BDD oneInTrans;
        {
            BDD one = _fact.zero(); // true if exactly one event from ruleEvents (so far) is true
            BDD zero = _fact.one(); // true if exactly no event from ruleEvents (so far) is true
            for (final YAMLDEvent e: transEvents) {
                final BDD eventOccurred = bddEventOccurred(e);
                one = (one.and(eventOccurred.not())).or(zero.and(eventOccurred));
                zero = zero.and(eventOccurred.not());
            }
            oneInTrans = one;
        }
        
        final BDD allInRule;
        {
            BDD tmp = _fact.one();
            for (final YAMLDEvent e: ruleEvents) {
                final BDD eventOccurred = bddEventOccurred(e);
                tmp = tmp.and(eventOccurred);
            }
            allInRule = tmp;
        }
        
        final BDD noOtherEvent;
        {
            BDD tmp = _fact.one();
            for (final YAMLDEvent e: compEvents) {
                if (transEvents.contains(e) || ruleEvents.contains(e)) {
                    continue;
                }
                final BDD eventOccurred = bddEventOccurred(e);
                tmp = tmp.and(eventOccurred.not());
            }
            noOtherEvent = tmp;
        }
        
        return oneInTrans.and(allInRule).and(noOtherEvent);
    }
    
    // Builds the BDD that represents: 
    // exactly all events in ruleEvents
    // no other events in compEvents
    private BDD ruleEventsBDD(Set<YAMLDEvent> ruleEvents, Set<YAMLDEvent> compEvents) {
        
        final BDD allInRule;
        {
            BDD tmp = _fact.one();
            for (final YAMLDEvent e: ruleEvents) {
                final BDD eventOccurred = bddEventOccurred(e);
                tmp = tmp.and(eventOccurred);
            }
            allInRule = tmp;
        }
        
        final BDD noOtherEvent;
        {
            BDD tmp = _fact.one();
            for (final YAMLDEvent e: compEvents) {
                if (ruleEvents.contains(e)) {
                    continue;
                }
                final BDD eventOccurred = bddEventOccurred(e);
                tmp = tmp.and(eventOccurred.not());
            }
            noOtherEvent = tmp;
        }
        
        return allInRule.and(noOtherEvent);
    }
    
    private BDD valueOfStateVariableUnchanged(YAMLDGenericVar var) {
        {
            final BDD result = _valueOfVariableUnchanged.get(var);
            if (result != null) {
                return result;
            }
        }

        final BDD result;
        
        if (var.domain().size() == 2) {
            final YAMLDValue val = var.domain().get(0);
            final BDD bdd1 = getVariableFromStateVariable(var, val);
            final BDD bdd2 = getFutureVariableFromStateVariable(var, val);
            final BDD equal = bdd1.xor(bdd2).not();
            result = equal;
        } else { 
            BDD tmp = _fact.one();
            for (final YAMLDValue val: var.domain()) {
                final BDD bdd1 = getVariableFromStateVariable(var, val);
                final BDD bdd2 = getFutureVariableFromStateVariable(var, val);
                final BDD equal = bdd1.imp(bdd2);
                tmp = tmp.and(equal);
            }
        
            result = tmp;
        }
        
        _valueOfVariableUnchanged.put(var, result);
        return result;
    }
    
    public BDD valueOfStateUnchanged(YAMLDComponent comp) {
        {
            final BDD result = _valueOfStateUnchanged.get(comp);
            if (result != null) {
                return result;
            }
        }

        BDD result = _fact.one();
        for (final YAMLDVar var: comp.vars()) {
            final BDD variableUnchanged = valueOfStateVariableUnchanged(var);
            result = result.and(variableUnchanged);
        }
        
        _valueOfStateUnchanged.put(comp, result);
        
        return result;
    }
    
    // Generate the BDD that represents the effect of the specified rule
    public BDD effect(YAMLDComponent comp, MMLDRule r) {
        BDD result = _fact.one();
        
        for (final YAMLDVar var: comp.vars()) {
            final YAMLDAssignment ass = r.getAssignment(var);
            if (ass == null) {
                final BDD unchanged = valueOfStateVariableUnchanged(var);
                result = result.and(unchanged);
            } else {
                final YAMLDExpr expr = ass.expression();
                if (!(expr instanceof YAMLDValue)) {
                    throw new UnsupportedOperationException("Not supported.");
                }
                final YAMLDValue val = (YAMLDValue)expr;
                final BDD bddAss = getFutureVariableFromStateVariable(var, val);
                result = result.and(bddAss);
            }
        }
        
        return result;
    }
    
    private BDD noTransition(YAMLDComponent comp) {
        BDD result = _fact.one();
        
        for (final YAMLDVar var: comp.vars()) {
            final BDD unchanged = valueOfStateVariableUnchanged(var);
            result = result.and(unchanged);
        }
        
        {
            final BDD noEvent = bddNonOccurrence(comp.events());
            result = result.and(noEvent);
        }
        
        return result;
    }
    
    private BDD transitionOfComponent(YAMLDComponent comp, MMLDTransition trans, Set<YAMLDEvent> eventsOfComponent) {
        BDD result = _fact.zero();
        
        BDD cumulativePreconditionsOfRules = _fact.zero();
        
        final Set<YAMLDEvent> transEvents = trans.getTriggeringEvents();
        for (final MMLDRule rule : trans.getRules()) {
            final YAMLDFormula f = rule.getCondition();

            final BDD rulePrecondition = formula(comp, f);
            cumulativePreconditionsOfRules = cumulativePreconditionsOfRules.or(rulePrecondition);

            final BDD events;
            if (transEvents.isEmpty()) {
                events = ruleEventsBDD(rule.getGeneratedEvents(), eventsOfComponent);
            } else {
                events = ruleEventsBDD(transEvents, rule.getGeneratedEvents(), eventsOfComponent);
            }
            
            final BDD effects = effect(comp, rule);

            final BDD ruleBDD = rulePrecondition.and(events).and(effects);
            result = result.or(ruleBDD);
        }

        if (!transEvents.isEmpty()) {
            // default rule
            final BDD defaultRule;
            {
                final BDD bddPrecondition = cumulativePreconditionsOfRules.not();
                final BDD bddEvents = ruleEventsBDD(transEvents, Collections.EMPTY_SET, eventsOfComponent);
                final BDD bddEffects = effect(comp, trans.getDefaultRule());
                defaultRule = bddPrecondition.and(bddEvents).and(bddEffects);
            }
            
            result = result.or(defaultRule);
        }
        
        return result;
    }
    
    private BDD transitionsOfComponent(YAMLDComponent comp) {
        BDD result = _fact.zero();
        
        final Set<YAMLDEvent> compEvents = new HashSet<YAMLDEvent>(comp.events());
        
        for (final MMLDTransition trans: comp.transitions()) {
            final BDD transBDD = transitionOfComponent(comp, trans, compEvents);
            result = result.or(transBDD);
        }
        
        {
            final BDD transitionWithNoEvent = noTransition(comp);
            result = result.or(transitionWithNoEvent);
        }
        
        return result;
    }

    @Override
    public JavaBDDUnionSetOfTransitions transitions(Network net) {
        BDD result = _fact.one();
        
        // Creates the variables.
        for (final YAMLDVar var: net.getStateVariables()) {
            getVariableFromStateVariable(var, var.domain().get(0));
        }
        
        for (final YAMLDComponent comp: net.getComponents()) {
            final BDD compBDD = transitionsOfComponent(comp);
            result = result.and(compBDD);
        }

        BDD synchros = _fact.one();
        for (final MMLDSynchro syn: net.getSynchros()) {
            final BDD bdd1;
            {
                final YAMLDEvent e1 = syn.getEvent();
                bdd1 = bddEventOccurred(e1);
            }
            
            for (final YAMLDEvent e: syn.getSynchronizedEvents()) {
                final BDD bdd2 = bddEventOccurred(e);
                final BDD bothAtTheSameTime = bdd1.xor(bdd2).not();
                synchros = synchros.and(bothAtTheSameTime);
            }
        }
        result = result.and(synchros);
        
        return new JavaBDDUnionSetOfTransitions(result);
    }

    @Override
    public JavaBDDUnionSetOfTransitions intersection(
            JavaBDDUnionSetOfTransitions sot1, JavaBDDUnionSetOfTransitions sot2) {
        return sot1.intersection(_fact, sot2);
    }

    @Override
    public JavaBDDSetOfStates union(JavaBDDSetOfStates sos1, JavaBDDSetOfStates sos2) {
        final BDD bdd1 = sos1.getBDD();
        final BDD bdd2 = sos2.getBDD();
        
        final BDD result = bdd1.or(bdd2);
        
        return new JavaBDDSetOfStates(result);
    }
 
    public BDDFactory getFactory() {
        return _fact;
    }

    @Override
    public JavaBDDSetOfStates eventOccuredBeforeCurrentState(YAMLDEvent ev) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JavaBDDUnionSetOfTransitions recordOccurrenceOfEvent(YAMLDEvent ev) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SymbolicDiagnosis getDiagnosis(JavaBDDSetOfStates sos, Collection<YAMLDEvent> evs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JavaBDDSetOfStates intersection(JavaBDDSetOfStates sos1, JavaBDDSetOfStates sos2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JavaBDDSetOfStates complement(JavaBDDSetOfStates sos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
}

