package diag.boundedauto;

import diag.Assignment;
import diag.EventOccurrence;
import diag.MMLDTransitionTrigger;
import diag.RuleTrigger;
import edu.supercom.util.Pair;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.SimpleAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lang.MMLDRule;
import lang.Network;
import lang.PartialState;
import lang.YAMLDComponent;
import lang.YAMLDEvent;

/**
 * A <code>LocalDiagnosis</code>, i.e., 
 * a (super) set of component trajectories.  
 */
public class LocalDiagnosis {
    
    /**
     * The component.  
     */
    private final YAMLDComponent _comp;
    
    /**
     * The minimal number of time steps.  
     */
    private final int _min;
    
    /**
     * The maximal number of time steps.  
     */
    private final int _max;
    
    /**
     * The automaton of this local diagnosis.  
     */
    private final Automaton<StateLabel,TransLabel> _auto;
    
    public LocalDiagnosis(Network net, YAMLDComponent comp, int min, int max) {
        _comp = comp;
        _min = min;
        _max = max;
        _auto = computeAutomaton();
    }
    
    private static Automaton<StateLabel,TransLabel> computeTmpAutomaton() {
        
        throw new UnsupportedOperationException();
    }
    
    private static void createStates(ReadableAutomaton<StateLabel,TransLabel> tmpAuto, 
            Automaton<StateLabel,TransLabel> result, 
            Map<StateLabel,State> stateOfTimeStep, 
            int t) {
        for (final State state: tmpAuto.states()) {
            final StateLabel label = tmpAuto.stateLabel(state);
            final StateLabel actualLabel = new StateLabel(t, label.state());
            final State actualState = result.getState(actualLabel);
            stateOfTimeStep.put(label, state);
            if (tmpAuto.isInitial(state)) {
                result.setInitial(actualState);
            }
        }
    }
    
    private Automaton<StateLabel,TransLabel> computeAutomaton() {
        
        final ReadableAutomaton<StateLabel,TransLabel> tmpAuto = computeTmpAutomaton();
        final Automaton<StateLabel,TransLabel> result = new SimpleAutomaton<StateLabel, TransLabel>(null); // TODO: change
        
        {
            Map<StateLabel,State> stateOfPreviousTimeStep = new HashMap<StateLabel, State>();
            createStates(tmpAuto, result, stateOfPreviousTimeStep, _min);
            {
                for (final State state: tmpAuto.initialStates()) {
                    final StateLabel label = tmpAuto.stateLabel(state);
                    final State actualState = stateOfPreviousTimeStep.get(label);
                    result.setInitial(actualState);
                }
            }
            
            for (int t = _min+1 ; t <= _max ; t++) {
                final Map<StateLabel,State> stateOfNextTimeStep = new HashMap<StateLabel, State>();
                createStates(tmpAuto, result, stateOfNextTimeStep, t);
                
                for (final Transition trans: tmpAuto.transitions()) {
                    final State actualOrigin;
                    final State actualTarget;
                    
                    {
                        final State origin = trans.getOrigin();
                        final StateLabel originLabel = tmpAuto.stateLabel(origin);
                        actualOrigin = stateOfPreviousTimeStep.get(originLabel);
                    }
                    
                    {
                        final State target = trans.getTarget();
                        final StateLabel targetLabel = tmpAuto.stateLabel(target);
                        actualTarget = stateOfNextTimeStep.get(targetLabel);
                    }
                    
                    final TransLabel transLabel = tmpAuto.transLabel(trans);
                    result.newTransition(actualOrigin, actualTarget, transLabel);
                }
                
                stateOfPreviousTimeStep = stateOfNextTimeStep;
            }
            
            for (final State state: stateOfPreviousTimeStep.values()) {
                result.setFinal(state);
            }
        }
        
        return result;
    }
    
    /**
     * Tries to refine the current local diagnosis 
     * using the specified local diagnosis.  
     * 
     * @param other the diagnosis that is used to refine this diagnosis.  
     * @return <code>true</code> if this diagnosis has been modified.  
     */
    public boolean refineWith(LocalDiagnosis other) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Indicates whether the specified assignment is possible
     * according to this local diagnosis.  
     * 
     * @param ass the assignment.  
     * @return <code>true</code> if the assignment is possible.  
     */
    public boolean isAssignmentPossible(Assignment ass) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Indicates whether the specified rule trigger is possible 
     * according this local diagnosis.  
     * 
     * @param rt the rule trigger.  
     * @return <code>true</code> if the rule trigger is possible.  
     */
    public boolean isRuleTriggerPossible(RuleTrigger rt) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Indicates whether the specified transition trigger is possible 
     * according this local diagnosis.  
     * 
     * @param tt the transition trigger.  
     * @return <code>true</code> if the rule trigger is possible.  
     */
    public boolean isTransitionTriggerPossible(MMLDTransitionTrigger tt) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Indicates whether the specified event occurrence is possible 
     * according this local diagnosis.  
     * 
     * @param ee the event occurrence.  
     * @return <code>true</code> if the event occurrence is possible.  
     */
    public boolean isEventOccurrencePossible(EventOccurrence rt) {
        throw new UnsupportedOperationException();
    }
}

class StateLabel {
    
    /**
     * The time step.  
     */
    private final int _t;
    
    /**
     * The value of each variable.  
     */
    private final PartialState _state;
    
    /**
     * The hashcode value of this label.  
     */
    private final int _hashcode;
    
    public StateLabel(int t, PartialState state) {
        _t = t;
        _state = state;
        _hashcode = Pair.hashCode(_t, _state);
    }
    
    public int t() {
        return _t;
    }
    
    public PartialState state() {
        return _state;
    }
    
    @Override
    public int hashCode() {
        return _hashcode;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof StateLabel)) {
            return false;
        }
        
        final StateLabel other = (StateLabel)o;
        
        if (this._t != other._t) {
            return false;
        }
        
        if (!this._state.equals(other._state)) {
            return false;
        }
        
        return true;
    }
}

class TransLabel {
    
    /**
     * The MMLD rule that corresponds to this transition.  
     */
    private final MMLDRule _rule;
    
    /**
     * The set of synchronisation events.  
     */
    private final Set<Pair<YAMLDEvent,YAMLDEvent>> _synchroEvents;
    
    public TransLabel(MMLDRule rule, Set<Pair<YAMLDEvent,YAMLDEvent>> synchroEvents) {
        _rule = rule;
        _synchroEvents = new HashSet<Pair<YAMLDEvent, YAMLDEvent>>(synchroEvents);
    }
    
    @Override
    public int hashCode() {
        return Pair.hashCode(_rule, _synchroEvents);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof TransLabel)) {
            return false;
        }
        
        final TransLabel other = (TransLabel)o;
        
        if (_rule != other._rule) {
            return false;
        }
        
        if (!_synchroEvents.equals(other._synchroEvents)) {
            return false;
        }
        
        return true;
    }
    
}