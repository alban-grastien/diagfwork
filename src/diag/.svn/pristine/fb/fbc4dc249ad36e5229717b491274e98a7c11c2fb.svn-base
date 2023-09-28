package diag.auto;

import edu.supercom.util.Pair;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.AutomatonFactory;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.changer.Abstraction;
import edu.supercom.util.auto.changer.LabelChanger;
import edu.supercom.util.auto.label.AutomaticPairSynchroniser;
import edu.supercom.util.auto.label.Synchroniser;
import edu.supercom.util.auto.op.AutomataSynchroniser;
import edu.supercom.util.auto.op.AutomatonLabelChanger;
import edu.supercom.util.auto.op.AutomatonMinimizer;
import edu.supercom.util.auto.ElaborateAutomaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.changer.CopyAvoider;
import edu.supercom.util.auto.label.AbstractSynchroniser;
import edu.supercom.util.auto.op.AutomatonDeterminiser;
import edu.supercom.util.auto.op.ElaborateEpsilonReductor;
import edu.supercom.util.auto.op.EpsilonReductor;
import edu.supercom.util.auto.op.NonDeterministicMinimiser;
import edu.supercom.util.auto.op.SimpleDeterminiser;
import edu.supercom.util.auto.op.SimpleLabelChanger;
import edu.supercom.util.auto.op.SimpleMinimizer;
import edu.supercom.util.auto.op.SimpleSynchroniser;
import java.util.ArrayList;
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
import lang.VariableValue;
import lang.YAMLDAddExpr;
import lang.YAMLDAndFormula;
import lang.YAMLDAssignment;
import lang.YAMLDComponent;
import lang.YAMLDDVar;
import lang.YAMLDEqFormula;
import lang.YAMLDEvent;
import lang.YAMLDExistsPath;
import lang.YAMLDExpr;
import lang.YAMLDFalse;
import lang.YAMLDFormula;
import lang.YAMLDGenericVar;
import lang.YAMLDID;
import lang.YAMLDNotFormula;
import lang.YAMLDOrFormula;
import lang.YAMLDStringExistsPath;
import lang.YAMLDTrue;
import lang.YAMLDValue;
import lang.YAMLDVar;
import util.AlarmLog;

/**
 * A <code>Language</code>, i.e., a language, 
 * is an automaton defined on a set of events.  
 */
public final class Language {

    public static boolean log = false;
    
    /**
     * The set of events.  
     */
    private final Alphabet _events;
    
    /**
     * The automaton defined on the set of events.  
     */
    private final ReadableAutomaton<?,TransLabel> _auto;
    
    private static final AutomatonFactory FACTORY = new AutomatonFactory() {

        @Override
        public <SL, TL> Automaton<SL, TL> buildAutomaton(TL tl) {
            return new ElaborateAutomaton<SL, TL>(tl);
        }
    };

    /**
     * An empty language.  
     */
    public Language() {
        _events = new Alphabet();
        final Automaton<String,TransLabel> auto = FACTORY.buildAutomaton(TransLabel.epsilonTransLabel());
        final State s = auto.newState("");
        auto.setInitial(s);
        auto.setFinal(s);
        _auto = auto;
    }
    
    /**
     * Creates a language defined as the specified set of events 
     * and the specified automaton.  
     * 
     * @param es the set of events.  
     * @param auto the automaton.  
     */
    private Language(Alphabet alpha, Automaton<?, TransLabel> auto) {
        _events = alpha;
        _auto = auto;
    }
    
    /**
     * Builds the language modeling the specified component.  
     * 
     * @param comp the component whose model is generated.  
     */
    public Language(Network net, YAMLDComponent comp, lang.State networkState) {
        final List<ComponentState> componentStates = buildComponentStates(comp);
        final List<State> states = new ArrayList<State>();
        final Map<ComponentState,State> componentStateToState = new HashMap<ComponentState, State>();
        
        final Set<SynchronousEvent> events = new HashSet<SynchronousEvent>();
        
        final Map<YAMLDEvent, Set<SynchronousEvent>> pairsOfEvents = new HashMap<YAMLDEvent, Set<SynchronousEvent>>();
        for (final YAMLDEvent event: comp.events()) {
            final Set<SynchronousEvent> pairsOfEvent = new HashSet<SynchronousEvent>();
            for (final MMLDSynchro sync: net.getSynchros()) {
                if (sync.getEvent() == event) {
                    for (final YAMLDEvent otherEvent: sync.getSynchronizedEvents()) {
                        final SynchronousEvent pair = new SynchronousEvent(event, otherEvent, false);
                        events.add(pair);
                        pairsOfEvent.add(pair);
                    }
                }
                if (sync.getSynchronizedEvents().contains(event)) {
                    final YAMLDEvent otherEvent = sync.getEvent();
                    final SynchronousEvent pair = new SynchronousEvent(otherEvent, event, false);
                    events.add(pair);
                    pairsOfEvent.add(pair);
                }
            }
            
            // Not synchronised events
            if (pairsOfEvent.isEmpty()) {
                final SynchronousEvent pair = new SynchronousEvent(event, event, event.isInput());
                events.add(pair);
                pairsOfEvent.add(pair);
            }
            pairsOfEvents.put(event, pairsOfEvent);
        }
        
        final Automaton<Integer,TransLabel> auto = FACTORY.buildAutomaton(TransLabel.epsilonTransLabel());
        
        // States
        for (final ComponentState componentState: componentStates) {
            final State state = auto.newState(states.size());
            states.add(state);
            auto.setFinal(state);
            componentStateToState.put(componentState, state);
        }
        
        // Transitions 
        for (final MMLDTransition tr: comp.transitions()) {
            for (int stateIndex = 0 ; stateIndex < states.size() ; stateIndex++) {
                final ComponentState componentState = componentStates.get(stateIndex);
                final State state = states.get(stateIndex);
                
                final Set<TransLabel> setOfTransitionEvents = 
                        componentState.canTrigger(net,tr,pairsOfEvents);
                
                if (setOfTransitionEvents.isEmpty()) {
                    continue;
                }
                
                final Set<Pair<TransLabel,List<YAMLDAssignment>>> ruleEffects = 
                        new HashSet<Pair<TransLabel, List<YAMLDAssignment>>>();
                
                for (final MMLDRule rule: tr.getRules()) {
                    final YAMLDFormula formula = rule.getCondition();
                    final boolean ruleSatisfied = componentState.satisfies(formula);
                    if (ruleSatisfied) {
                        final Set<SynchronousEvent> ruleEvents = new HashSet<SynchronousEvent>();
                        for (final YAMLDEvent event: rule.getGeneratedEvents()) {
                            ruleEvents.addAll(pairsOfEvents.get(event));
                        }
                        final TransLabel ruleLabel = new TransLabel(ruleEvents);
//                        ruleEvents.addAll(rule.getGeneratedEvents());
                        final List<YAMLDAssignment> ruleAsses = new ArrayList<YAMLDAssignment>();
                        ruleAsses.addAll(rule.getAssignments());
//                        final Set<Set<SynchronousEvent>> combinationsOfRuleEffect = generateCombinations(pairsOfEvents, ruleEvents);
//                        for (final Set<SynchronousEvent> combination: combinationsOfRuleEffect) {
                            ruleEffects.add(new Pair<TransLabel, List<YAMLDAssignment>>(ruleLabel, ruleAsses));
//                        }
                    }
                }
                // Default rule (if there is no other possible rule)
                if (ruleEffects.isEmpty()) {
                    ruleEffects.add(new Pair<TransLabel, List<YAMLDAssignment>>(TransLabel.epsilonTransLabel(), noAssignment()));
                }
                
                for (final TransLabel transitionEvents: setOfTransitionEvents) {
                    for (final Pair<TransLabel,List<YAMLDAssignment>> pair: ruleEffects) {
                        final TransLabel ruleEvents = pair.first();
                        final List<YAMLDAssignment> ruleAss = pair.second();
                        
                        final TransLabel transLabel = transitionEvents.union(ruleEvents);
                        final ComponentState newComponentState = new ComponentState(componentState, ruleAss);
                        final State newState = componentStateToState.get(newComponentState);
                        
                        if (state == newState && transLabel.isEmpty()) {
                            continue; // Epsilon transition
                        }
                        
                        auto.newTransition(state, newState, transLabel);
                    }
                }
            }
        }
        
        {
            //int nbInit = 0;
            for (final ComponentState componentState: componentStates) {
                if (componentState.satisfies(networkState)) {
                    final State state = componentStateToState.get(componentState);
                    auto.setInitial(state);
            //        nbInit++;
                }
            }
            //System.out.println("Nb init = " + nbInit);
//            ComponentState initialComponentState = new ComponentState(comp);
//            for (final YAMLDVar var: comp.vars()) {
//                final YAMLDValue val = networkState.getValue(var);
//                initialComponentState = new ComponentState(initialComponentState, var, val);
//            }
//            final State state = componentStateToState.get(initialComponentState);
//            auto.setInitial(state);
        }
        
        auto.trim();
        _auto = auto;
        _events = new Alphabet(events);

        System.out.println("Component " + comp.name() + " -- nb states: " + auto.states().size() 
                + " -- init: " + auto.initialStates().size()
                + " -- trans: " + auto.transitions().size()
                );
        
        //_auto = canon(auto);
    }
    
    /**
     * Computes the observation automaton of the specified component 
     * given the specified alarm log.  
     * 
     * @param comp the component.  
     * @param log the alarm log.  
     */
    public Language(Network net, YAMLDComponent comp, AlarmLog log) {
        {
            final Set<SynchronousEvent> events = new HashSet<SynchronousEvent>();
            for (final YAMLDEvent event: net.observableEvents()) {
                if (event.getComponent() == comp) {
                    events.add(new SynchronousEvent(event, event, false));
                }
            }
            _events = new Alphabet(events);
        }
        
        final List<YAMLDEvent> obs = new ArrayList<YAMLDEvent>();
        for (final AlarmLog.AlarmEntry entry: log) {
            for (final YAMLDEvent event: entry._events) {
                if (event.getComponent() == comp) {
                    obs.add(event);
                }
            }
        }
        
        final Automaton<Integer, TransLabel> auto = FACTORY.buildAutomaton(TransLabel.epsilonTransLabel());
        int i=0;
        State currentState = auto.newState(i);
        auto.setInitial(currentState);
        for (final YAMLDEvent event: obs) {
            final Set<SynchronousEvent> set = new HashSet<SynchronousEvent>();
            set.add(new SynchronousEvent(event, event, false));
            final TransLabel label = new TransLabel(set);
            i++;
            final State newState = auto.newState(i);
            auto.newTransition(currentState, newState, label);
            currentState = newState;
        }
        auto.setFinal(currentState);
        
        _auto = auto;
    }
    
    /**
     * Intersects the two specified languages.  
     * 
     * @param l1 the first language.  
     * @param l2 the second language.  
     */
    public static Language intersect(Language l1, Language l2) {
        return intersect(l1, l2, true);
    }
    
    @SuppressWarnings("unchecked")
    private static Language intersect(Language l1, Language l2, boolean canonicalForm) {
        final Synchroniser stateSynchroniser = new AutomaticPairSynchroniser();
        final Synchroniser<TransLabel, TransLabel, TransLabel, TransLabel, TransLabel> transSynchroniser;
        {
            final Alphabet commonAlphabet = l1.getAlphabet().project(l2.getAlphabet());
            transSynchroniser = commonAlphabet.getSynchroniser();
        }
        
        final AutomataSynchroniser as = new SimpleSynchroniser();
        Automaton<?,TransLabel> auto = as.synchronise(l1._auto, l2._auto, stateSynchroniser, transSynchroniser, FACTORY);
        
        {
            final LabelChanger lc = new Abstraction();
            final AutomatonLabelChanger alc = new SimpleLabelChanger();
            alc.changeStateLabels(auto, lc);
        }
        
        if (canonicalForm) {
            auto = canon(auto);
        } else {
            auto = simplify(auto);
        }
        
        final Alphabet union = l1.getAlphabet().union(l2.getAlphabet());
        
        return new Language(union, auto);
    }

    Alphabet getAlphabet() {
        return _events;
    }
    
    Language project(Alphabet alpha) {
        final Automaton<?,TransLabel> auto = _auto.copy();
        
        {
            final LabelChanger<TransLabel, TransLabel> lc = alpha.getProjector();
            final AutomatonLabelChanger alc = new SimpleLabelChanger();
            alc.changeTransLabels(auto, lc);
        }
        
        final Alphabet projectedAlpha = getAlphabet().project(alpha);
        
        return new Language(projectedAlpha, canon(auto));
    }
    
    private static List<ComponentState> buildComponentStates(YAMLDComponent comp) {
        List<ComponentState> result = new ArrayList<ComponentState> ();
        result.add(new ComponentState(comp));
        
        for (final YAMLDVar var: comp.vars()) {
            List<ComponentState> newResult = new ArrayList<ComponentState>();
            for (final YAMLDValue val: var.domain()) {
                for (final ComponentState oldState: result) {
                    final ComponentState newState = new ComponentState(oldState, var, val);
                    newResult.add(newState);
                }
            }
            result = newResult;
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        return _auto.toDot("");
    }
    
    public String toString(String name) {
        return _auto.toDot(name);
    }
    
    @SuppressWarnings("unchecked")
    public static List<YAMLDAssignment> noAssignment() {
        return (List<YAMLDAssignment>)Collections.EMPTY_LIST;
    }
    
    @SuppressWarnings("unchecked")
    static Automaton<?,TransLabel> canon(Automaton<?, TransLabel> auto) {
        if (log) {System.out.println(auto.toDot("CANON 1"));}
        
        auto.trim();
        
        {
            final LabelChanger<TransLabel,TransLabel> lc = new CopyAvoider<TransLabel>();
            final AutomatonLabelChanger alc = new SimpleLabelChanger();
            alc.changeTransLabels(auto, lc);
        }

        if (log) {System.out.println(auto.toDot("CANON 2"));}
        
        {
            final EpsilonReductor eps = new ElaborateEpsilonReductor();
            eps.runReduction(auto);
        }
        
        if (log) {System.out.println(auto.toDot("CANON 3"));}
        
        final Automaton<?,TransLabel> resultingAutomaton;
        {
            final AutomatonDeterminiser det = new SimpleDeterminiser();
            resultingAutomaton = det.determinise(auto);
        }
        
        {
            final LabelChanger lc = new Abstraction();
            final AutomatonLabelChanger alc = new SimpleLabelChanger();
            alc.changeStateLabels(resultingAutomaton, lc);
        }
        
        if (log) {System.out.println(auto.toDot("CANON 4"));}
        
        resultingAutomaton.trim();
        
        if (log) {System.out.println(auto.toDot("CANON 5"));}
        
        {
            final AutomatonMinimizer min = new SimpleMinimizer();
            min.doMinimize(resultingAutomaton);
        }
        return resultingAutomaton;
//        
//        {
//            final AutomatonMinimizer min = new NonDeterministicMinimiser();
//            min.doMinimize(auto);
//        }
//        
//        if (log) {System.out.println(auto.toDot("CANON 6"));}
//        
//        auto.trim();
//        
//        {
//            final LabelChanger lc = new Abstraction();
//            final AutomatonLabelChanger alc = new SimpleLabelChanger();
//            alc.changeStateLabels(auto, lc);
//        }
//        
//        if (log) {System.out.println(auto.toDot("CANON 7"));}
//        
//        return auto;
    }
    
    @SuppressWarnings("unchecked")
    static Automaton<?,TransLabel> simplify(Automaton<?, TransLabel> auto) {
        auto.trim();
        
        {
            final LabelChanger<TransLabel,TransLabel> lc = new CopyAvoider<TransLabel>();
            final AutomatonLabelChanger alc = new SimpleLabelChanger();
            alc.changeTransLabels(auto, lc);
        }

        {
            final EpsilonReductor eps = new ElaborateEpsilonReductor();
            eps.runReduction(auto);
        }
        
        return auto;
    }
    
    public static Language computeLocalDiagnosis(Network net, YAMLDComponent comp, lang.State networkState, AlarmLog log, boolean canonicalForm) {
        final Language mod = new Language(net, comp, networkState);
        final Language obs = new Language(net, comp, log);
        
        final Language diag = intersect(mod, obs, canonicalForm);
        return diag;
    }
}

class ComponentState {
    final YAMLDComponent _comp;
    final Map<YAMLDVar,YAMLDValue> _ass;
    final Map<YAMLDDVar,YAMLDValue> _depAss;
    
    public ComponentState(YAMLDComponent comp) {
        _comp = comp;
        _ass = new HashMap<YAMLDVar, YAMLDValue>();
        _depAss = new HashMap<YAMLDDVar, YAMLDValue>();
    }
    
    public ComponentState(ComponentState state, YAMLDVar var, YAMLDValue val) {
        _comp = state._comp;
        _ass = new HashMap<YAMLDVar, YAMLDValue>(state._ass);
        _ass.put(var, val);
        _depAss = new HashMap<YAMLDDVar, YAMLDValue>();
    }
    
    public ComponentState(ComponentState state, Pair<YAMLDVar,YAMLDValue>... pairs) {
        _comp = state._comp;
        _ass = new HashMap<YAMLDVar, YAMLDValue>(state._ass);
        for (final Pair<YAMLDVar, YAMLDValue> p: pairs) {
            _ass.put(p.first(), p.second());
        }
        _depAss = new HashMap<YAMLDDVar, YAMLDValue>();
    }
    
    public ComponentState(ComponentState state, YAMLDAssignment... asses) {
        _comp = state._comp;
        _ass = new HashMap<YAMLDVar, YAMLDValue>(state._ass);
        for (final YAMLDAssignment ass: asses) {
            final YAMLDGenericVar gvar = ass.variable();
            if (gvar instanceof YAMLDVar) {
                final YAMLDVar var = (YAMLDVar)gvar;
                final YAMLDValue val = evaluate(ass.expression());
                _ass.put(var,val);
            }
            
        }
        _depAss = new HashMap<YAMLDDVar, YAMLDValue>();
    }
    
    public ComponentState(ComponentState state, Collection<YAMLDAssignment> asses) {
        _comp = state._comp;
        _ass = new HashMap<YAMLDVar, YAMLDValue>(state._ass);
        for (final YAMLDAssignment ass: asses) {
            final YAMLDGenericVar gvar = ass.variable();
            if (gvar instanceof YAMLDVar) {
                final YAMLDVar var = (YAMLDVar)gvar;
                final YAMLDValue val = evaluate(ass.expression());
                _ass.put(var,val);
            }
            
        }
        _depAss = new HashMap<YAMLDDVar, YAMLDValue>();
    }
    
    @Override
    public final int hashCode() {
        return _ass.hashCode();
    }
    
    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof ComponentState)) {
            return false;
        }
        
        final ComponentState cs = (ComponentState)o;
        return cs._ass.equals(_ass);
    }
    
    private static int UNASSIGNED = 0;
    private static int TRUE = 1;
    private static int FALSE = 2;
    
    public final boolean satisfies(YAMLDFormula f) {
//        System.out.println("Language >> " + f.toFormattedString());
        
        int result = UNASSIGNED;
        
        if (f instanceof YAMLDStringExistsPath) {
            result = TRUE; // NOT IMPLEMENTED HERE
        }
        if (f instanceof YAMLDEqFormula) {
            final YAMLDEqFormula eq = (YAMLDEqFormula)f;
            final YAMLDValue val1 = evaluate(eq.expr1());
            final YAMLDValue val2 = evaluate(eq.expr2());
            if (val1.equals(val2)) {
                result = TRUE;
            } else {
                result = FALSE;
            }
        }
        if (f instanceof YAMLDOrFormula) {
            final YAMLDOrFormula or = (YAMLDOrFormula)f;
            if (satisfies(or.getOp1())) {
                result = TRUE;
            } else if (satisfies(or.getOp2())) {
                result = TRUE;
            } else {
                result = FALSE;
            }
            
        }
        if (f instanceof YAMLDAndFormula) {
            final YAMLDAndFormula and = (YAMLDAndFormula)f;
            if (!satisfies(and.getOp1())) {
                result = FALSE;
            } else if (satisfies(and.getOp2())) {
                result = TRUE;
            } else {
                result = FALSE;
            }
        }
        if (f instanceof YAMLDExistsPath) {
            result = TRUE; // NOT IMPLEMENTED HERE
        }
        if (f instanceof YAMLDTrue) {
            result = TRUE;
        }
        if (f instanceof YAMLDNotFormula) {
            if (satisfies(((YAMLDNotFormula)f).getOp())) {
                result = FALSE;
            } else {
                result = TRUE;
            }
        }
        if (f instanceof YAMLDFalse) {
            result = FALSE;
        }
        
        if (result == UNASSIGNED) {
            throw new IllegalArgumentException(f.toFormattedString());
        }
        
        return (result == TRUE);
    }
    
    public final YAMLDValue evaluate(YAMLDExpr expr) {
        if (expr instanceof VariableValue) {
            final VariableValue vv = (VariableValue)expr;
            final YAMLDGenericVar gen = vv.variable();
            return value(gen);
        }
        if (expr instanceof YAMLDID) {
            final YAMLDID id = (YAMLDID)expr;
            final String name = id.get_id();
            final YAMLDGenericVar var = _comp.getVariable(name);
            return value(var);
        }
        if (expr instanceof YAMLDAddExpr) {
            final YAMLDAddExpr add = (YAMLDAddExpr)expr;
            final YAMLDValue val1 = evaluate(add.getOp1());
            final YAMLDValue val2 = evaluate(add.getOp2());
            return YAMLDValue.add(val1,val2);
            
        }
        if (expr instanceof YAMLDValue) {
            return (YAMLDValue)expr;
        }
//        if (expr instanceof YAMLDNum) {
//            // DEPRECATED
//        }
        
        throw new IllegalArgumentException(expr.toFormattedString());
    }
    
    public final YAMLDValue value(YAMLDGenericVar var) {
        if (var instanceof YAMLDVar) {
            return _ass.get((YAMLDVar)var);
        }
        
//        final YAMLDDVar dvar = (YAMLDDVar)var;
//        
//        {
//            final YAMLDValue val = _depAss.get(dvar);
//            if (val != null) {
//                return val;
//            }
//        }
//        
        throw new UnsupportedOperationException(var.toString());
    }
    
    // Set of sets of events that label the transitions.
    public final Set<TransLabel> canTrigger(Network net, MMLDTransition tr, Map<YAMLDEvent, Set<SynchronousEvent>> pairsOfEvents) {
//        System.out.println("+++++++++++++++++++++++++++++++++");
//        System.out.println(this);
//        System.out.println(tr.getName());
        
        final Set<TransLabel> result = new HashSet<TransLabel>();
        
        if (tr.isSpontaneous()) {
            // Can trigger automatically
            final TransLabel noEvent = TransLabel.epsilonTransLabel();
            result.add(noEvent);
        }
        
        for (final YAMLDEvent event: tr.getTriggeringEvents()) {
            for (final SynchronousEvent synchronousEvent: pairsOfEvents.get(event)) {
                final Set<SynchronousEvent> set = new HashSet<SynchronousEvent>();
                set.add(synchronousEvent);
                final TransLabel label = new TransLabel(set);
                result.add(label);
            }
        }
        
        for (final YAMLDFormula formula: tr.getPreconditions()) {
            if (satisfies(formula)) {
                final TransLabel noEvent = TransLabel.epsilonTransLabel();
                result.add(noEvent);
                break; // Continuing this loop will not produce a different result
            }
        }
        
//        System.out.println(result);
        return result;
    }
    
    @Override
    public String toString() {
        return _ass.toString();
    }
    
    public boolean satisfies(lang.State state) {
        for (final Map.Entry<YAMLDVar,YAMLDValue> ent: _ass.entrySet()) {
            final YAMLDVar var = ent.getKey();
            final YAMLDValue val = ent.getValue();
            final YAMLDValue actualVal = state.getValue(var);
            if (actualVal == null) { // no value
                continue;
            }
            if (actualVal.equals(val)) {
                continue;
            }
            return false;
        }
        
        return true;
    }
}

class SynchronousEvent {
    //private final Pair<YAMLDEvent,YAMLDEvent> _pair;
    private final YAMLDEvent _event;
    
    private final boolean _spontaneousEvent;
    
    public SynchronousEvent(YAMLDEvent e1, YAMLDEvent e2, boolean isSpontaneous) {
        //_pair = new Pair<YAMLDEvent, YAMLDEvent>(e1, e2);
        _event = e1;
        _spontaneousEvent = isSpontaneous;
    }
    
    @Override
    public String toString() {
        return _event.toFormattedString();
    }
    
    @Override
    public int hashCode() {
        return _event.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof SynchronousEvent)) {
            return false;
        }
        
        final SynchronousEvent other = (SynchronousEvent)o;
        return _event.equals(other._event);
    }
    
    public boolean isSpontaneous() {
        return _spontaneousEvent;
    }
}

class Alphabet {
    private final Set<SynchronousEvent> _set;

    public Alphabet() {
        _set = new HashSet<SynchronousEvent>();
    }
    
    public Alphabet(Set<SynchronousEvent> set) {
        _set = set;
    }
    
    public Set<SynchronousEvent> events() {
        return Collections.unmodifiableSet(_set);
    }
    
    public Alphabet union(Alphabet alpha) {
        final Set<SynchronousEvent> result = new HashSet<SynchronousEvent>();
        result.addAll(events());
        result.addAll(alpha.events());
        return new Alphabet(result);
    }
    
    public Alphabet project(Alphabet alpha) {
        final Set<SynchronousEvent> result = new HashSet<SynchronousEvent>();
        result.addAll(events());
        result.retainAll(alpha.events());
        return new Alphabet(result);
    }
    
    public Alphabet remove(Alphabet alpha) {
        final Set<SynchronousEvent> result = new HashSet<SynchronousEvent>(events());
        result.removeAll(alpha.events());
        return new Alphabet(result);
    }
    
    public int size() {
        return _set.size();
    }

    public Synchroniser<TransLabel,TransLabel,TransLabel,TransLabel,TransLabel> getSynchroniser() {
        return new AbstractSynchroniser<TransLabel, TransLabel, TransLabel, TransLabel, TransLabel>() {
            
            private final Map<Pair<TransLabel,TransLabel>,Pair<TransLabel,TransLabel>> _buffer = 
                    new HashMap<Pair<TransLabel, TransLabel>, Pair<TransLabel, TransLabel>>();

            @Override
            public TransLabel synchroniseLabel(TransLabel l1, TransLabel l2) {
//                if (l1.containsSpontaneous() && l2.containsSpontaneous()) {
//                    return null;
//                }
                final Pair<TransLabel,TransLabel> key = new Pair<TransLabel, TransLabel>(l1, l2);
                {
                    final Pair<TransLabel,TransLabel> result = _buffer.get(key);
                    if (result != null) {
                        return result.first();
                    }
                }
                
                final TransLabel result;
                {
                    final TransLabel p1 = projection1(l1);
                    final TransLabel p2 = projection2(l2);
                    if (synchroniseProjectedLabels(p1, p2)) {
                        result = l1.union(l2);
                    } else {
                        result = null;
                    }
                }
                
                _buffer.put(key,new Pair<TransLabel,TransLabel>(result,result));
                
                return result;
            }

            @Override
            public TransLabel projection1(TransLabel l) {
                return l.project(Alphabet.this);
            }

            @Override
            public TransLabel projection2(TransLabel l) {
                return l.project(Alphabet.this);
            }

            @Override
            public boolean synchroniseProjectedLabels(TransLabel l1, TransLabel l2) {
                return l1.equals(l2);
            }
        };
    }
    
    public LabelChanger<TransLabel, TransLabel> getProjector() {
        return new LabelChanger<TransLabel, TransLabel>() {

            private final Map<TransLabel, TransLabel> _buffer = new HashMap<TransLabel, TransLabel>();

            @Override
            public TransLabel getLabel(TransLabel old) {
                {
                    final TransLabel result = _buffer.get(old);
                    if (result != null) {
                        return result;
                    }
                }

                final TransLabel result = old.project(Alphabet.this);
                _buffer.put(old, result);
                return result;
            }
        };
    }
}

class TransLabel {
    
    private final Set<SynchronousEvent> _set;
//    private final boolean _containsSpontaneous;
    private static final int DEFAULT_HASH = -1;
    private int _hash = DEFAULT_HASH;
    private static final TransLabel EPSILON_TRANS_LABEL = new TransLabel(new HashSet<SynchronousEvent>());
    
    public TransLabel(Set<SynchronousEvent> events) {
        _set = events;
//        boolean containsSpontaneous = false;
//        for (final SynchronousEvent e: events) {
//            if (e.isSpontaneous()) {
//                containsSpontaneous = true;
//                break;
//            }
//        }
//        _containsSpontaneous = containsSpontaneous;
    }
 
    public static TransLabel epsilonTransLabel() {
        return EPSILON_TRANS_LABEL;
    }
    
    @Override
    public int hashCode() {
        if (_hash == DEFAULT_HASH) {
            _hash = computeHashCode();
        }
        
        return _hash;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof TransLabel)) {
            return false;
        }
        
        final TransLabel tl = (TransLabel)o;
        return _set.equals(tl._set);
    }
    
    @Override
    public String toString() {
        return _set.toString();
    }
    
    public TransLabel project(Alphabet alpha) {
        final Set<SynchronousEvent> set = new HashSet<SynchronousEvent>();
        set.addAll(_set);
        set.retainAll(alpha.events());
        return new TransLabel(set);
    }
    
    public TransLabel union(TransLabel l) {
        final Set<SynchronousEvent> result = new HashSet<SynchronousEvent>();
        result.addAll(_set);
        result.addAll(l._set);
        return new TransLabel(result);
    }
    
    public boolean isEmpty(){
        return _set.isEmpty();
    }
    
//    public boolean containsSpontaneous() {
//        return _containsSpontaneous;
//    }
    
    private int computeHashCode() {
        return _set.hashCode();
    }
}