package diag.symb;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import lang.Network;
import lang.State;
import lang.YAMLDEvent;
import util.AlarmLog;

/**
 * A main method to diagnose with symbolic tools.  
 */
public class SymbDiagnose {
    
    public static <SOS extends SetOfStates,SOT extends SetOfTransitions> 
            SOS diagnose(SymbolicFramework<SOS,SOT> rep, 
            Network net, AlarmLog log,
			//Collection<YAMLDEvent> faults, 
            State state) {
        
        SOS currentStates = rep.state(state);
        
        final SOT transitions = rep.transitions(net);
        final SOT unobservableTransitions;
        final Collection<YAMLDEvent> observableEvents = net.observableEvents();
        {
            //final List<YAMLDEvent> unobservableEvents = net.getUnobservableEvents();
            final SOT tmp = rep.nonOccurrence(observableEvents);
//            final List<SOT> list = new ArrayList<SOT>();
//            list.add(transitions);
//            list.add(tmp);
            unobservableTransitions = rep.intersection(transitions, tmp);
        }
        
        for (final AlarmLog.AlarmEntry et: log) {
            SOS newStates = rep.emptySetOfStates();
            final Collection<List<YAMLDEvent>> orderingsOfEntry = et.getOrderings();
            for (final List<YAMLDEvent> listOfAlarms: orderingsOfEntry) {
                SOS statesForThisOrdering = currentStates;
                for (final YAMLDEvent alarm: listOfAlarms) {
                    final SOS silentClosure = 
                            closure(rep, statesForThisOrdering, unobservableTransitions);
                    final SOT transitionsStartingFromSilentClosure = 
                            rep.transitionFromState(silentClosure);
                    final SOT obsSOT = observation(rep, observableEvents, alarm);
                    final SOT actualTransitions = 
                            rep.intersection(
                                rep.intersection(transitions, obsSOT)
                                , transitionsStartingFromSilentClosure);
                    statesForThisOrdering = rep.nextStates(actualTransitions);
                }
                newStates = rep.union(newStates,statesForThisOrdering);
            }
            currentStates = newStates;
        }
        
        
        return currentStates;
    }
    
    public static <SOS extends SetOfStates,SOT extends SetOfTransitions> 
            SymbolicDiagnosis diagnose(SymbolicFramework<SOS,SOT> rep, 
            Network net, AlarmLog log,
			Collection<YAMLDEvent> faults, 
            State state) {
        
        // NO FAULT OCCURRED BEFORE THE CURRENT STATE
        SOS currentStates = rep.state(state);
        for (final YAMLDEvent event: faults) {
            final SOS faultyState = rep.eventOccuredBeforeCurrentState(event);
            final SOS noFault = rep.complement(faultyState);
            currentStates = rep.intersection(currentStates, noFault);
        }
        
        final SOT transitions;
        {
            SOT tmp = rep.transitions(net);
            for (final YAMLDEvent event: faults) {
                final SOT recordFaults = rep.recordOccurrenceOfEvent(event);
                tmp = rep.intersection(tmp, recordFaults);
            }
            transitions = tmp;
        }
        
        final SOT unobservableTransitions;
        final Collection<YAMLDEvent> observableEvents = net.observableEvents();
        {
            //final List<YAMLDEvent> unobservableEvents = net.getUnobservableEvents();
            final SOT tmp = rep.nonOccurrence(observableEvents);
//            final List<SOT> list = new ArrayList<SOT>();
//            list.add(transitions);
//            list.add(tmp);
            unobservableTransitions = rep.intersection(transitions, tmp);
        }
        
        for (final AlarmLog.AlarmEntry et: log) {
            SOS newStates = rep.emptySetOfStates();
            final Collection<List<YAMLDEvent>> orderingsOfEntry = et.getOrderings();
            for (final List<YAMLDEvent> listOfAlarms: orderingsOfEntry) {
                SOS statesForThisOrdering = currentStates;
                for (final YAMLDEvent alarm: listOfAlarms) {
                    final SOS silentClosure = 
                            closure(rep, statesForThisOrdering, unobservableTransitions);
                    final SOT transitionsStartingFromSilentClosure = 
                            rep.transitionFromState(silentClosure);
                    final SOT obsSOT = observation(rep, observableEvents, alarm);
                    final SOT actualTransitions = 
                            rep.intersection(
                                rep.intersection(transitions, obsSOT)
                                , transitionsStartingFromSilentClosure);
                    statesForThisOrdering = rep.nextStates(actualTransitions);
                }
                newStates = rep.union(newStates,statesForThisOrdering);
            }
            currentStates = newStates;
        }
        
        return rep.getDiagnosis(currentStates, faults);
    }
    
    /**
     * Computes the closure of a set of states over a set of transitions.  
     * 
     * @param rep the BDD representation that explains 
     * how to interprete the sets of states and the sets of transitions.  
     * @param sos the set of states to start with.  
     * @param sot the set of transitions that can be used.  
     * @return the set of states that can be reached from <tt>sos</tt> 
     * by taking 0, 1, or more transitions from <tt>sot</tt>.  
     */
    public static <SOS extends SetOfStates,SOT extends SetOfTransitions> 
            SOS closure(SymbolicFramework<SOS,SOT> rep, SOS sos, SOT sot) {
        SOS oldResult = rep.emptySetOfStates();
        SOS result = sos;
        
        while (!rep.equals(result, oldResult)) {
            oldResult = result;
            final SOT transitionsStartingFromOldResult = 
                    rep.transitionFromState(oldResult);
            final SOT actualTransitions = rep.intersection(sot,transitionsStartingFromOldResult);
            final SOS newStates = rep.nextStates(actualTransitions);
            
            result = rep.union(newStates,oldResult);
        }
        
        return result;
    }
    
    /**
     * Returns the set of transitions that represents the specified observation 
     * (and consequently no other observation).  
     * 
     * @param frame the symbolic framework.  
     * @param oe the set of observable events.  
     * @param e the observed event.  
     */
    public static <SOS extends SetOfStates, SOT extends SetOfTransitions> 
            SOT observation(SymbolicFramework<SOS,SOT> frame, Collection<YAMLDEvent> oe, YAMLDEvent e) {
        SOT result = frame.eventOccurred(e);
        
        for (final YAMLDEvent other: oe) {
            if (other.equals(e)) {
                continue;
            }
            final SOT notEvent = frame.nonOccurrence(new AbstractList<YAMLDEvent>() {

                @Override
                public YAMLDEvent get(int index) {
                    if (index == 0) {
                        return other;
                    }
                    
                    throw new IndexOutOfBoundsException("size = 1; index = " + index);
                }

                @Override
                public int size() {
                    return 1;
                }
            });
            result = frame.intersection(result, notEvent);
        }
        
        return result;
    }
}
