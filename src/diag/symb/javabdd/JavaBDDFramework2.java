package diag.symb.javabdd;

import diag.symb.SymbolicDiagnosis;
import diag.symb.SymbolicFramework;
import edu.supercom.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lang.MMLDRule;
import lang.MMLDSynchro;
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


/**
 * A <code>JavaBDDFramework</code>, i.e., a Java BDD framework, 
 * is a symbolic framework using the Java BDD implementation.  
 * 
 * @author Alban Grastien
 */
public class JavaBDDFramework2 
implements SymbolicFramework<JavaBDDSetOfStates, JavaBDDUnionSetOfTransitions> {

    /**
     * Creates a new Java BDD framework using the specified BDD factory.  
     * 
     * @param fact the factory used to manipulate the BDDs.  
     */
    public JavaBDDFramework2(BDDFactory fact) {
        _fact = fact;
        _eventToVariable = new HashMap<YAMLDEvent, Integer>();
        _stateVarToVariable = new HashMap<Pair<YAMLDGenericVar, YAMLDValue>, BDD>();
        _futureStateVarToVariable = new HashMap<Pair<YAMLDGenericVar, YAMLDValue>, BDD>();
        _nextToCurrent = _fact.makePair();
        _current = _fact.one();
        _events = _fact.one();
        _valueOfVariableUnchanged = new HashMap<YAMLDGenericVar, BDD>();
        _valueOfStateUnchanged = new HashMap<YAMLDComponent, BDD>();
        _eventOccurredInCurrentState = new HashMap<YAMLDEvent, BDD>();
        _eventOccurredInNextState = new HashMap<YAMLDEvent, BDD>();
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

    /**
     * The variables that record whether the specified event occurred in the current state.  
     */
    private final Map<YAMLDEvent,BDD> _eventOccurredInCurrentState;

    /**
     * The variables that record whether the specified event occurred in the next state.  
     */
    private final Map<YAMLDEvent,BDD> _eventOccurredInNextState;
    
    /// LOWEST LEVEL: BDD VARIABLES
 
    /**
     * Returns the factory of this java BDD framework.  
     * 
     * @return the factory used by this framework to generate BDDs.  
     */
    public BDDFactory getFactory() {
        return _fact;
    }
    
    // Creates a new BDD variable
    private int newBDDVariable() {
        //System.out.println(_fact.varNum());
        return _fact.extVarNum(1);
    }
    
    // Creates the variables for the specified binary state variable
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
    
    // Given a set of BDD variables, 
    // computes the BDD that associates each variable to false, 
    // except the one at the specified index.  
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
    
    // Creates the BDD variables for the specified non binary state variable
    private void createVariablesForNonBinaryStateVariable(YAMLDGenericVar var, List<YAMLDValue> domain) {
        //System.out.println("JBDDF2::createVariablesForNonBinaryStateVariable");
        final int domainSize = domain.size();
        
        // Create the variables
        final int[] currentVariables = new int[domainSize];
        final int[] nextVariables = new int[domainSize];
        for (int i=0 ; i<domainSize ; i++) {
            //System.out.println("JBDDF2::createVariablesForNonBinaryStateVariable 1");
            currentVariables[i] = newBDDVariable();
            //System.out.println("JBDDF2::createVariablesForNonBinaryStateVariable 2");
            nextVariables[i] = newBDDVariable();
            //System.out.println("JBDDF2::createVariablesForNonBinaryStateVariable 3");
            
            _current = _current.and(_fact.ithVar(currentVariables[i]));
            //System.out.println("JBDDF2::createVariablesForNonBinaryStateVariable 4");
            _nextToCurrent.set(nextVariables[i],currentVariables[i]);
            //System.out.println("JBDDF2::createVariablesForNonBinaryStateVariable 5");
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

    // Creates the BDD variable for specified event (returns the event)
    private int createVariableForEvent(YAMLDEvent e) {
        final int result = newBDDVariable();
        _eventToVariable.put(e, result);
        _events = _events.and(_fact.ithVar(result));
        return result;
    }

    /**
     * Ensures the variables of the specified component have been generated.  
     * 
     * @param comp the component.  
     */
    public void createVariables(YAMLDComponent comp) {
        
        for (final YAMLDVar var: comp.vars()) {
            bddStateAssignment(var, var.domain().get(0));
        }
        
        for (final YAMLDEvent ev: comp.events()) {
            bddEventOccurred(ev);
        }
        
    }
    
    // STATE VARIABLES AND STATES
    
    // Returns the BDD for specified state variable (create the variable if non existing)
    private BDD bddStateAssignment(YAMLDGenericVar var, YAMLDValue val) {
        final Pair<YAMLDGenericVar,YAMLDValue> ass = new Pair<YAMLDGenericVar, YAMLDValue>(var, val);
        
        {
            //System.out.println("JBDDF2::bddStateAssignment searching");
            final BDD result = _stateVarToVariable.get(ass);
            //System.out.println("JBDDF2::bddStateAssignment found");
            if (result != null) {
                //System.out.println("JBDDF2::bddStateAssignment found found");
                return result;
            }
            //System.out.println("JBDDF2::bddStateAssignment found not found");
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
    private BDD bddNextStateAssignment(YAMLDGenericVar var, YAMLDValue val) {
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
    
    // Builds the BDD that represents the equality between the following expressions in the current state.  
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
        return bddStateAssignment(var, val);
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

    // Builds the BDD that represents the fact that a variable can have only one value
    private BDD variableMustHaveOneValue(YAMLDGenericVar var) {
        BDD zero = _fact.one();
        BDD one = _fact.zero();
        
        for (final YAMLDValue val: var.domain()) {
            final BDD bdd = bddStateAssignment(var, val);
            one = (one.and(bdd.not())).or(zero.and(bdd));
            zero = zero.and(bdd.not());
        }
        
        return one;
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
    public JavaBDDSetOfStates complement(JavaBDDSetOfStates sos) {
        final BDD bdd = sos.getBDD();
        
        final BDD result = bdd.not();
        
        return new JavaBDDSetOfStates(result);
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
            
            BDD tmp = bddStateAssignment(var, val);
            result = result.and(tmp);
        }
        
        return new JavaBDDSetOfStates(result);
    }
    
    @Override
    public JavaBDDSetOfStates intersection(JavaBDDSetOfStates sos1, JavaBDDSetOfStates sos2) {
        final BDD bdd1 = sos1.getBDD();
        final BDD bdd2 = sos2.getBDD();
        
        final BDD result = bdd1.and(bdd2);
        
        return new JavaBDDSetOfStates(result);
    }

    @Override
    public JavaBDDSetOfStates union(JavaBDDSetOfStates sos1, JavaBDDSetOfStates sos2) {
        final BDD bdd1 = sos1.getBDD();
        final BDD bdd2 = sos2.getBDD();
        
        final BDD result = bdd1.or(bdd2);
        
        return new JavaBDDSetOfStates(result);
    }
 
    // EVENTS 
    
    /**
     * Returns the BDD that represents the occurrence of the specified event.  
     * 
     * @param e the event.  
     * @return a BDD that says that <tt>e</tt> is currently occurring.  
     */
    public BDD bddEventOccurred(YAMLDEvent e) {
        assert (e != null);
        
        final int var;
        {
            final Integer tmp = _eventToVariable.get(e);
            if (tmp != null) {
                var = tmp;
            } else {
                var = createVariableForEvent(e);
            }
        }
        
        final BDD result = _fact.ithVar(var);
        return result;
    }
    
    @Override
    public JavaBDDUnionSetOfTransitions eventOccurred(YAMLDEvent e) {
        final BDD bdd = bddEventOccurred(e);
        final JavaBDDUnionSetOfTransitions result = new JavaBDDUnionSetOfTransitions(bdd);
        return result;
    }
    
    /**
     * Returns the BDD that specifies that one event 
     * in the specified collection occurred.  
     * 
     * @param es a collection of events.  
     * @return the BDD that specifies that one of the events in <tt>es</tt> 
     * is currently taking place.  
     */
    public BDD bddAtLeastOneEventOccurred(Collection<YAMLDEvent> es) {
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

    /**
     * Returns the BDD that specifies that no event 
     * in the specified collection occurred.  
     * 
     * @param es a collection of events.  
     * @return the BDD that specifies that none of the events in <tt>es</tt> 
     * is currently taking place.  
     */
    public BDD bddNonOccurrence(Collection<YAMLDEvent> es) {
        return bddAtLeastOneEventOccurred(es).not();
    }
    
    @Override
    public JavaBDDUnionSetOfTransitions nonOccurrence(Collection<YAMLDEvent> es) {
        final BDD bdd = bddNonOccurrence(es);
        final JavaBDDUnionSetOfTransitions result = new JavaBDDUnionSetOfTransitions(bdd);
        return result;
    }

    // TRANSITIONS

    // Builds the BDD that represents the fact that the assignment of the specified variable is unchanged. 
    private BDD bddValueOfStateVariableUnchanged(YAMLDGenericVar var) {
        //System.out.println("JBDDF2::bddValueOfStateVariableUnchanged " + var);
        
        {
            final BDD result = _valueOfVariableUnchanged.get(var);
            if (result != null) {
                return result;
            }
        }

        final BDD result;
        
        if (var.domain().size() == 2) {
            final YAMLDValue val = var.domain().get(0);
            final BDD bdd1 = bddStateAssignment(var, val);
            final BDD bdd2 = bddNextStateAssignment(var, val);
            final BDD equal = bdd1.xor(bdd2).not();
            result = equal;
        } else { 
            BDD tmp = _fact.one();
            for (final YAMLDValue val: var.domain()) {
                //System.out.println("JBDDF2::bddValueOfStateVariableUnchanged 1");
                final BDD bdd1 = bddStateAssignment(var, val);
                //System.out.println("JBDDF2::bddValueOfStateVariableUnchanged 2");
                final BDD bdd2 = bddNextStateAssignment(var, val);
                //System.out.println("JBDDF2::bddValueOfStateVariableUnchanged 3");
                final BDD equal = bdd1.imp(bdd2);
                tmp = tmp.and(equal);
                //System.out.println("JBDDF2::bddValueOfStateVariableUnchanged 4");
            }
            
            result = tmp.and(variableMustHaveOneValue(var));
        }
        
        _valueOfVariableUnchanged.put(var, result);
        return result;
    }
    
    /**
     * Returns the BDD that represents the fact that the value of the state 
     * of the specified component is unchanged.  
     * 
     * @param comp the component upon which this property is tested.  
     * @return a BDD that states that the state of the component 
     * can be anything but that it is not modified by the transition.  
     */
    public BDD valueOfStateUnchanged(YAMLDComponent comp) {
        {
            final BDD result = _valueOfStateUnchanged.get(comp);
            if (result != null) {
                return result;
            }
        }

        BDD result = _fact.one();
        for (final YAMLDVar var: comp.vars()) {
            //System.out.println("JBDDF2::valueOfStateUnchanged " + var);
            final BDD variableUnchanged = bddValueOfStateVariableUnchanged(var);
            result = result.and(variableUnchanged);
        }
        //System.out.println("JBDDF2::valueOfStateUnchanged variables done.");
        _valueOfStateUnchanged.put(comp, result);
        
        return result;
    }
    
    /**
     * Builds the BDD that represents the effect of the specified rule.  
     * 
     * @param comp the component.  
     * @param r the rule.  
     * @return the BDD that gives the next state (given the current state) 
     * assuming that the rule <tt>r</tt> is triggered.  
     */
    public BDD bddEffect(YAMLDComponent comp, MMLDRule r) {
        BDD result = _fact.one();
        
        for (final YAMLDVar var: comp.vars()) {
            final YAMLDAssignment ass = r.getAssignment(var);
            if (ass == null) {
                final BDD unchanged = bddValueOfStateVariableUnchanged(var);
                result = result.and(unchanged);
            } else {
                final YAMLDExpr expr = ass.expression();
                if (!(expr instanceof YAMLDValue)) {
                    throw new UnsupportedOperationException("Not supported.");
                }
                final YAMLDValue val = (YAMLDValue)expr;
                final BDD bddAss = bddNextStateAssignment(var, val);
                result = result.and(bddAss);
            }
        }
        
        return result;
    }
    
    @Override
    public JavaBDDUnionSetOfTransitions transitionFromState(JavaBDDSetOfStates sos) {
        return new JavaBDDUnionSetOfTransitions(sos.getBDD());
    }

    @Override
    public JavaBDDSetOfStates nextStates(JavaBDDUnionSetOfTransitions sot) {
        return sot.nextStates(_fact, _current, _events, _nextToCurrent);
    }
    
    @Override
    public JavaBDDUnionSetOfTransitions intersection(
            JavaBDDUnionSetOfTransitions sot1, JavaBDDUnionSetOfTransitions sot2) {
        return sot1.intersection(_fact, sot2);
    }

    @Override
    public JavaBDDUnionSetOfTransitions transitions(Network net) {
        
        final Map<YAMLDComponent,LocalTransition> defaultRules = new HashMap<YAMLDComponent, LocalTransition>();
        final Map<YAMLDComponent,List<LocalTransition>> automaticRules = new HashMap<YAMLDComponent, List<LocalTransition>>();
        final Map<YAMLDEvent,List<LocalTransition>> rulesPerInputEvent = new HashMap<YAMLDEvent, List<LocalTransition>>();
        
        
        // Initialise the maps
        for (final YAMLDComponent comp: net.getComponents()) {
            //System.out.println("JBDDF2::TRANSITIONS Dealing with component " + comp);
            final List<LocalTransition> automaticRulesOfComp = new ArrayList<LocalTransition>();
            automaticRules.put(comp, automaticRulesOfComp);
            for (final YAMLDEvent ev: comp.events()) {
                //System.out.println("JBDDF2::TRANSITIONS Dealing with event " + ev);
                if (ev.isInput()) {
                    rulesPerInputEvent.put(ev, new ArrayList<LocalTransition>());
                }
            }
            //System.out.println("JBDDF2::TRANSITIONS all events dealt with");
            //System.out.println();
            
            { // Default rule
                //System.out.println("JBDDF2::TRANSITIONS building default rule");
                final LocalTransition lt = LocalTransition.defaultRule(this, comp);
                defaultRules.put(comp, lt);
                //System.out.println("JBDDF2::TRANSITIONS rule built");
                //System.out.println();
            }
            
            for (final LocalTransition lt: LocalTransition.rulesOfComponent(this, comp)) {
                final YAMLDEvent input = lt.getInput();
                if (input == null) {
                    automaticRulesOfComp.add(lt);
                } else {
                    final List<LocalTransition> rulesOfThisInputEvent = rulesPerInputEvent.get(input);
                    rulesOfThisInputEvent.add(lt);
                }
            }
        }
        
        final List<BDD> allBDDS = new ArrayList<BDD>();
        for (final YAMLDComponent triggeringComp: net.getComponents()) {
            for (final LocalTransition triggeringTransition: automaticRules.get(triggeringComp)) {
                
                final List<Map<YAMLDComponent,LocalTransition>> listOfLocalTransitions 
                        = new ArrayList<Map<YAMLDComponent, LocalTransition>>();
                {
                    final Map<YAMLDComponent,LocalTransition> localTransitions = new HashMap<YAMLDComponent, LocalTransition>();
                    localTransitions.put(triggeringComp, triggeringTransition);
                    final List<YAMLDEvent> inputEventsToSatisfy = computeNewInputEvents(triggeringTransition.getOutputs());
                    populateListOfLocalTransitions(net, listOfLocalTransitions, inputEventsToSatisfy, localTransitions, rulesPerInputEvent);
                }
                addDefaultRulesToLocalTransitions(net, defaultRules, listOfLocalTransitions);
                
                for (final Map<YAMLDComponent, LocalTransition> localTransitions: listOfLocalTransitions) {
                    BDD tmp = _fact.one();
                    for (final YAMLDComponent comp: net.getComponents()) {
                        final LocalTransition lt = localTransitions.get(comp);
                        final BDD bdd = lt.getBDD();
                        tmp = tmp.and(bdd);
                    }
                    allBDDS.add(tmp);
//                    System.out.println("JBDDF2 -> " + tmp);
                }
            }
        }
        
        return new JavaBDDUnionSetOfTransitions(allBDDS);
    }
    
    /// REST
    
    // Recursively computes and stores global transitions from the specified partial global transition.  
    // A global transition is a mapping Map<YAMLDComponent,LocalTransition> that associates a transition to some components.  
    // Input: 
    // net the network
    // listOfLocalTransitions: the list of complete global transitions 
    // inputEventsToSatisfy: the list of events triggered by the other transitions and not synchronised yet
    // localTransitions: the current (partial) global transition
    // rulesPerInputEvent: a mapping that already contains the list of local transitions 
    // that can be triggered as a consequence of a given input event
    private void populateListOfLocalTransitions(
            Network net, 
            List<Map<YAMLDComponent,LocalTransition>> listOfLocalTransitions, 
            List<YAMLDEvent> inputEventsToSatisfy, 
            Map<YAMLDComponent,LocalTransition> localTransitions, 
            Map<YAMLDEvent,List<LocalTransition>> rulesPerInputEvent
            ) {
        
        if (inputEventsToSatisfy.isEmpty()) {
            listOfLocalTransitions.add(localTransitions);
            return;
        }
        
        final YAMLDEvent inputEventBeingSatisfied = inputEventsToSatisfy.remove(0);
        final YAMLDComponent componentBeingSatisfied = inputEventBeingSatisfied.getComponent();
        if (localTransitions.containsKey(componentBeingSatisfied)) {
            return;
        }
        
        final List<LocalTransition> possibleSolutions = rulesPerInputEvent.get(inputEventBeingSatisfied);
        for (final LocalTransition solutionChosen: possibleSolutions) {
            final Map<YAMLDComponent,LocalTransition> newLocalTransitions = 
                    new HashMap<YAMLDComponent, LocalTransition>(localTransitions);
            newLocalTransitions.put(componentBeingSatisfied, solutionChosen);
            
            final List<YAMLDEvent> newInputEventsToSatisfy = new ArrayList<YAMLDEvent>(inputEventsToSatisfy);
            newInputEventsToSatisfy.addAll(computeNewInputEvents(solutionChosen.getOutputs()));
            
            populateListOfLocalTransitions(net, listOfLocalTransitions, newInputEventsToSatisfy, newLocalTransitions, rulesPerInputEvent);
        }
    }
    
    // Fills the local transition (if no local transition is associated 
    // with a component, it is replaced by the default rule)
    private void addDefaultRulesToLocalTransitions(
            Network net, 
            Map<YAMLDComponent,LocalTransition> defaultRules,
            List<Map<YAMLDComponent,LocalTransition>> listOfLocalTransitions
            ) {
        for (final Map<YAMLDComponent,LocalTransition> localTransitions: listOfLocalTransitions) {
            for (final YAMLDComponent comp: net.getComponents()) {
                if (localTransitions.containsKey(comp)) {
                    continue;
                }
                final LocalTransition defaultRule = defaultRules.get(comp);
                localTransitions.put(comp, defaultRule);
            }
        }
    }
    
    // Computes the input events that are generated as a consequence of the specified set of output events)
    private List<YAMLDEvent> computeNewInputEvents(List<YAMLDEvent> outputEvents) {
        final List<YAMLDEvent> result = new ArrayList<YAMLDEvent>();
        for (final YAMLDEvent outputEvent: outputEvents) {
            for (final MMLDSynchro synchro: outputEvent.getSynchros()) {
                result.addAll(synchro.getSynchronizedEvents());
            }
        }
        
        return result;
    }

//    public JavaBDDSetOfTransitions intersection(Collection<JavaBDDSetOfTransitions> sots) {
//        BDD result = _fact.one();
//        
//        for (final JavaBDDSetOfTransitions sot: sots) {
//            result = result.and(sot.getBDD());
//        }
//        
//        return new JavaBDDSetOfTransitions(result);
//    }

//    public JavaBDDSetOfStates union(Collection<JavaBDDSetOfStates> soss) {
//        BDD result = _fact.zero();
//        
//        for (final JavaBDDSetOfStates sos: soss) {
//            final BDD bdd = sos.getBDD();
//            result = result.or(bdd);
//        }
//        
//        return new JavaBDDSetOfStates(result);
//    }

    // DIAGNOSIS
    
    private void createVariablesForEventOccurrence(YAMLDEvent e) {
        final int current = newBDDVariable();
        final BDD bddCurrent = _fact.ithVar(current);
        _eventOccurredInCurrentState.put(e, bddCurrent);
        
        final int next = newBDDVariable();
        final BDD bddNext = _fact.ithVar(next);
        _eventOccurredInNextState.put(e, bddNext);
        
        _nextToCurrent.set(next, current);
        _current = _current.and(bddCurrent);
    }
    
    private BDD bddEventOccurredBeforeCurrent(YAMLDEvent ev) {
        {
            final BDD result = _eventOccurredInCurrentState.get(ev);
            if (result != null) {
                return result;
            }
        }
        
        createVariablesForEventOccurrence(ev);
        
        final BDD result = _eventOccurredInCurrentState.get(ev);
        return result;
    }
    
    private BDD bddEventOccurredBeforeOrDuringCurrent(YAMLDEvent ev) {
        {
            final BDD result = _eventOccurredInNextState.get(ev);
            if (result != null) {
                return result;
            }
        }
        
        createVariablesForEventOccurrence(ev);
        
        final BDD result = _eventOccurredInNextState.get(ev);
        return result;
    }
    
    @Override
    public JavaBDDSetOfStates eventOccuredBeforeCurrentState(YAMLDEvent ev) {
        final BDD bdd = bddEventOccurredBeforeCurrent(ev);
        final JavaBDDSetOfStates result = new JavaBDDSetOfStates(bdd);
        return result;
    }

    @Override
    public JavaBDDUnionSetOfTransitions recordOccurrenceOfEvent(YAMLDEvent ev) {
        final BDD before = bddEventOccurredBeforeCurrent(ev);
        final BDD beforeOrDuring = bddEventOccurredBeforeOrDuringCurrent(ev);
        final BDD during = bddEventOccurred(ev);
        
        final BDD notEqual = beforeOrDuring.xor(before.or(during));
        final BDD equal = notEqual.not();
        
        final JavaBDDUnionSetOfTransitions result = new JavaBDDUnionSetOfTransitions(equal);
        return result;
    }

    @Override
    public SymbolicDiagnosis getDiagnosis(JavaBDDSetOfStates sos, Collection<YAMLDEvent> evs) {
        final Map<YAMLDEvent,BDD> eventRecognition = new HashMap<YAMLDEvent, BDD>();
        for (final YAMLDEvent ev: evs) {
            final BDD bddEv = bddEventOccurredBeforeCurrent(ev);
            eventRecognition.put(ev, bddEv);
        }
        final JavaBDDDiagnosis result = new JavaBDDDiagnosis(eventRecognition, sos.getBDD());
        return result;
    }

}
