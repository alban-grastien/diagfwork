package diag.symb;

import java.util.Collection;
import lang.Network;
import lang.State;
import lang.YAMLDEvent;

/**
 * A <code>SymbolicFramework</code>, i.e., a symbolic framework, 
 * is an object which is able to create symbolic objects 
 * to represent and manipulate states and transitions.  
 * Each symbolic object (or symbolic representation) can be understood as a BDD, 
 * but the approach is more general than just that.  
 * 
 * @author Alban Grastien
 * @param SOS the actual implementation of the set of states.  
 * @param SOT the actual implementation of the set of transitions.  
 */
public interface SymbolicFramework<SOS extends SetOfStates, SOT extends SetOfTransitions> {
    
    // STATES
    
    /**
     * Computes the union of the two specified sets of states.  
     * 
     * @param sos1 the first set of states.  
     * @param sos2 the second set of states.  
     * @return the disjunction of the symbolic representations 
     * in <tt>sos1</tt> and <tt>sos2</tt>.  
     */
    public SOS union(SOS sos1, SOS sos2);
    
    /**
     * Computes the intersection of the two specified sets of states.  
     * 
     * @param sos1 the first set of states.  
     * @param sos2 the second set of states.  
     * @return the conjunction of the symbolic representations 
     * in <tt>sos1</tt> and <tt>sos2</tt>.  
     */
    public SOS intersection(SOS sos1, SOS sos2);
    
    /**
     * Computes the complement of the specified set of states.  
     * 
     * @param sos the set of states whose complement is to be computed.  
     * @return the complement of <tt>sos</tt>.  
     */
    public SOS complement(SOS sos);
    
    /**
     * Returns the empty set of states.  
     * 
     * @return the symbolic representation of the empty set of states.  
     */
    public SOS emptySetOfStates();
    
    /**
     * Returns the set that contains all states.  
     * 
     * @return the symbolic representation of the complete set of states.  
     */
    public SOS setOfAllStates();

    /**
     * Returns the set of states corresponding to the specified state.  
     * 
     * @param s a (partially specified) state.  
     * @return the symbolic representation of <tt>s</tt>.  
     */
    public SOS state(State s);
    
    /**
     * Checks whether the two specified sets of states are equal.  
     * 
     * @param sos1 the first set of states.  
     * @param sos2 the second set of states.  
     * @return <tt>true</tt> if <tt>sos1</tt> equals <tt>sos2</tt>.  
     */
    public boolean equals(SOS sos1, SOS sos2);
    
    // TRANSITIONS
    
    /**
     * Returns the set of transitions corresponding to the specified network.  
     * 
     * @param net a network.  
     * @return the symbolic representation of the transitions in <tt>net</tt>.  
     */
    public SOT transitions(Network net);

    /**
     * Returns the intersection of the two specified sets of transitions.  
     * 
     * @param sot1 the first set of transitions.  
     * @param sot2 the second set of transitions.  
     * @return the conjunction of the symbolic representations in <tt>sots</tt>.  
     */
    public SOT intersection(SOT sot1, SOT sot2);

    // EVENTS -> TRANSITIONS
    
    /**
     * Returns the set of transitions that represents the occurrence 
     * of the specified event.  
     * 
     * @param e the event.  
     * @return the symbolic representation of event <tt>e</tt>.  
     */
    public SOT eventOccurred(YAMLDEvent e);
    
    /**
     * Returns the set of transitions that represents the occurrence 
     * of one of the specified events.  
     * 
     * @param es the set of events.  
     * @return the symbolic representation of the one event of <tt>es</tt>.  
     */
    public SOT atLeastOneEventOccurred(Collection<YAMLDEvent> es);
    
    /**
     * Returns the set of transitions that represents 
     * the fact that no event in the specified set occurred.  
     * 
     * @param es the set of events.  
     * @return the symbolic representation of no event from <tt>es</tt>.  
     */
    public SOT nonOccurrence(Collection<YAMLDEvent> es);
    
    // TRANSITIONS -> STATES
    
    /**
     * Returns the set of next states in the specified set of transitions.  
     * 
     * @param sot a set of transitions.  
     * @return the projection of the symbolic representation of <tt>sot</tt> 
     * on the next variables, then renamed in current variables.  
     */
    public SOS nextStates(SOT sot);
    
    // STATES -> TRANSITIONS
    
    /**
     * Returns the set of transitions that start from the specified set of states.  
     * 
     * @param sos the set of states.  
     * @return the symbolic representation of the transitions 
     * leaving a state of <tt>sos</tt>.  
     */
    public SOT transitionFromState(SOS sos);
    
    // DIAGNOSIS
    
    /**
     * Creates a symbolic set of states that records whether the specified event 
     * occurred before the current state.  
     * 
     * @param ev the event.  
     * @return the symbolic set of states that corresponds to the occurrence 
     * of <tt>ev</tt> before the current state.  
     */
    public SOS eventOccuredBeforeCurrentState(YAMLDEvent ev);
    
    /**
     * Builds the set of transitions that record the status 
     * of the occurrence of the specified event.  
     * Practically, this set of transitions represents: 
     * <center>
     *   event occurred after transitions <---> 
     *   event occurred before transition OR event occurred during transition.
     * </center>
     * 
     * @param ev the event.  
     * @return the set of transitions that record 
     * whether <tt>ev</tt> occurred.  
     */
    public SOT recordOccurrenceOfEvent(YAMLDEvent ev);
    
    /**
     * Transforms the current set of states into a diagnosis.  
     * 
     * @param sos the current set of states.  
     * @param evs the set of events that are tracked.  
     * @return a diagnosis that indicates whether the events from <tt>evs</tt> 
     * occurred before the current set of states.  
     */
    public SymbolicDiagnosis getDiagnosis(SOS sos, Collection<YAMLDEvent> evs);
    
}
