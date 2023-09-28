package edu.supercom.util.auto.op;

import edu.supercom.util.auto.AbstractAutomaton;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ElaborateAutomaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.auto.changer.LabelChanger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


// A class of equivalent states
/**
 * A <code>NonDeterministicMinimiser</code>, i.e., 
 */
public class NonDeterministicMinimiser implements AutomatonMinimizer {
    
    @Override
    public <SL, TL> Automaton<Set<SL>, TL> minimize(ReadableAutomaton<SL, TL> auto) {
        System.out.println("Minimizing an automaton of size " + auto.transitions().size());
        
        final Automaton<Set<SL>,TL> result = new ElaborateAutomaton<Set<SL>, TL>(auto.getEmptyTransitionLabel());
        
        final Partition partition = computePartition(auto);
        
        // Select the representatives of each class.
        final Map<Class,State> representatives = new HashMap<Class, State>();
        for (final Class cl: partition.getClasses()) {
            final State state = cl.iterator().next();
            representatives.put(cl, state);
        }
        
        final Map<State,Behaviour<TL>> behaviours = new HashMap<State, Behaviour<TL>>();
        for (final Map.Entry<Class,State> ent: representatives.entrySet()) {
            final State representative = ent.getValue();
            final Behaviour<TL> behaviour = computeBehaviour(auto, partition, representative);
            behaviours.put(representative, behaviour);
        }
        
        // Creates the states
        final Map<State, State> oldToNew = new HashMap<State, State>();
        for (final Map.Entry<Class,State> ent: representatives.entrySet()) {
            final Class cl = ent.getKey();
            final State representative = ent.getValue();
            if (auto.isInitial(representative)) {
                continue;
            }
            
            boolean shoudBeInitial = false;
            for (final State otherStateOfSameClass: cl) {
                if (auto.isInitial(otherStateOfSameClass)) {
                    shoudBeInitial = true;
                    break;
                }
            }
            
            final Set<SL> stateLabel = new HashSet<SL>();
            for (final State state: cl) {
                stateLabel.add(auto.stateLabel(state));
            }
            final State newState = result.newState(stateLabel);
            
            oldToNew.put(representative, newState);
            if (shoudBeInitial) {
                result.setInitial(newState);
            }
            if (auto.isFinal(representative)) {
                result.setFinal(newState);
            }
        }
        
        // Creates the new transitions
        for (final Map.Entry<Class,State> ent: representatives.entrySet()) {
            final State representative = ent.getValue();
            final Behaviour<TL> behaviour = behaviours.get(representative);
            
            for (final TL transLabel: behaviour.transitionLabels()) {
                for (final Class targetClass: behaviour.classesReached(transLabel)) {
                    final State targetRepresentative = representatives.get(targetClass);
                    
                    final State newOrigin = oldToNew.get(representative);
                    final State newTarget = oldToNew.get(targetRepresentative);
                    result.newTransition(newOrigin, newTarget, transLabel);
                }
            }
        }
        
        System.out.println("End minimizing");
        
        return result;
    }

    @Override
    public <SL, TL> void doMinimize(Automaton<SL, TL> auto) {
        System.out.println("Minimizing an automaton of size " + 
                auto.states().size() + "*" + auto.transitions().size());
        
        final Partition partition = computePartition(auto);
        
        // Select the representatives of each class.
        final Map<Class,State> representatives = new HashMap<Class, State>();
        for (final Class cl: partition.getClasses()) {
            final State state = cl.iterator().next();
            representatives.put(cl, state);
        }
        
        final Map<State,Behaviour<TL>> behaviours = new HashMap<State, Behaviour<TL>>();
        for (final Map.Entry<Class,State> ent: representatives.entrySet()) {
            final State representative = ent.getValue();
            final Behaviour<TL> behaviour = computeBehaviour(auto, partition, representative);
            behaviours.put(representative, behaviour);
        }
        
        // Sets the initial states
        for (final Map.Entry<Class,State> ent: representatives.entrySet()) {
            final State representative = ent.getValue();
            if (auto.isInitial(representative)) {
                continue;
            }
            
            final Class cl = ent.getKey();
            boolean shoudBeInitial = false;
            for (final State otherStateOfSameClass: cl) {
                if (auto.isInitial(otherStateOfSameClass)) {
                    shoudBeInitial = true;
                    break;
                }
            }
            
            if (shoudBeInitial) {
                auto.setInitial(representative);
            }
        }
        
        // Removes the other states
        for (final Map.Entry<Class,State> ent: representatives.entrySet()) {
            final Class cl = ent.getKey();
            final State representative = ent.getValue();
            
            for (final State otherStateOfSameClass: cl) {
                if (representative == otherStateOfSameClass) {
                    continue;
                }
                auto.removeAll(otherStateOfSameClass);
            }
        }
        
        // Removes the transitions
        for (final Transition trans: new ArrayList<Transition>(auto.transitions())) {
            auto.remove(trans);
        }
        
        // Creates the new transitions
        for (final Map.Entry<Class,State> ent: representatives.entrySet()) {
            final Class cl = ent.getKey();
            final State representative = ent.getValue();
            final Behaviour<TL> behaviour = behaviours.get(representative);
            
            for (final TL transLabel: behaviour.transitionLabels()) {
                for (final Class targetClass: behaviour.classesReached(transLabel)) {
                    final State targetRepresentative = representatives.get(targetClass);
                    auto.newTransition(representative, targetRepresentative, transLabel);
                }
            }
        }
        
        System.out.println("End minimizing");
    }
    
    static <TL> Partition computePartition(ReadableAutomaton<?,TL> auto ) {
        final Partition partition = computeInitialPartition(auto);
        
        boolean stable;
        do {
            stable = true;
            
            final Set<Class> classes = partition.getClasses();
            for (final Class cl: classes) {
                if (!testClass(auto, partition, cl)) {
                    stable = false;
                }
            }
        } while (!stable);
        
        return partition;
    }
    
    private static Partition computeInitialPartition(ReadableAutomaton<?, ?> auto) {
        final Partition result = new Partition(auto.states());
        
        final List<State> finalStates = new ArrayList<State>();
        final List<State> nonFinalStates = new ArrayList<State>();
        for (final State state: auto.states()) {
            if (auto.isFinal(state)) {
                finalStates.add(state);
            } else {
                nonFinalStates.add(state);
            }
        }
        
        final List<List<State>> split = new ArrayList<List<State>>();
        split.add(finalStates);
        split.add(nonFinalStates);
        
        result.split(split);
        
        return result;
    }
    
    private static <TL> Behaviour<TL> computeBehaviour(ReadableAutomaton<?,TL> auto, Partition partition, State s) {
        final BehaviourBuilder<TL> builder = new BehaviourBuilder<TL>();
        
        for (final Transition trans: auto.outgoingTrans(s)) {
            final TL transLabel = auto.transLabel(trans);
            final State newState = trans.getTarget();
            final Class newClass = partition.getClass(newState);
            builder.add(transLabel, newClass);
        }
        
        return builder.getBehaviour();
    }
    
    // Returns true if the class is not changed
    private static <TL> boolean testClass(ReadableAutomaton<?,TL> auto, Partition partition, Class cl) {
        if (cl.size() == 1) {
            return true;
        }
        
        boolean result = true;
        
        final List<Class> classesToDealWith = new ArrayList<Class>();
        final Map<Class,Set<TL>> transitionsDeltWith = new HashMap<Class, Set<TL>>();
        
        classesToDealWith.add(cl);
        transitionsDeltWith.put(cl, new HashSet<TL>());
        
        while (!classesToDealWith.isEmpty()) {
            final Class currentClass = classesToDealWith.remove(0);
            
            if (currentClass.size() == 1) {
                transitionsDeltWith.remove(currentClass);
                continue;
            }
            
            // Make a copy not to affect other classes with same transition labels.  
            final Set<TL> labelsAlreadyConsidered = new HashSet<TL>(transitionsDeltWith.get(currentClass));
            
            // Pick a transition label
            for (final Iterator<TL> labelIt = new ClassTransitionLabelIterator<TL>(auto, currentClass) ; ; ) {
                if (!labelIt.hasNext()) {
                    break;
                }
                final TL currentTransitionLabel = labelIt.next();
                if (labelsAlreadyConsidered.contains(currentTransitionLabel)) {
                    continue;
                }
                labelsAlreadyConsidered.add(currentTransitionLabel);
            
                final Map<Behaviour<TL>,List<State>> behaviours = 
                        computeClassBehaviour(auto, partition, currentClass, currentTransitionLabel);
            
                if (behaviours.size() == 1) {
                    continue;
                }
                
                result = false;
                final Collection<List<State>> newDistribution = behaviours.values();
                partition.split(newDistribution);
                for (final List<State> list: newDistribution) {
                    final State state = list.get(0);
                    final Class classOfState = partition.getClass(state);
                    classesToDealWith.add(classOfState);
                    transitionsDeltWith.put(classOfState, labelsAlreadyConsidered);
                }
                break;
            }
        }
        
        return result;
    }
    
    private static <TL> Map<Behaviour<TL>,List<State>> 
            computeClassBehaviour(ReadableAutomaton<?,TL> auto, Partition partition, Class cl, TL labelConsidered) {
        final Map<Behaviour<TL>,List<State>>  result = new HashMap<Behaviour<TL>, List<State>>();
        
        for (final State state: cl) {
            final BehaviourBuilder<TL> behaviourBuilder = new BehaviourBuilder<TL>();
            for (final Transition trans: auto.outgoingTrans(state)) {
                final TL currentTransitionLabel = auto.transLabel(trans);
                if (!labelConsidered.equals(currentTransitionLabel)) {
                    continue;
                }
                final State target = trans.getTarget();
                final Class targetClass = partition.getClass(target);
                behaviourBuilder.add(labelConsidered, targetClass);
            }
            
            final Behaviour<TL> behaviour = behaviourBuilder.getBehaviour();
            final List<State> equivalentStates;
            {
                final List<State> tmp = result.get(behaviour);
                if (tmp != null) {
                    equivalentStates = tmp;
                } else {
                    equivalentStates = new ArrayList<State>();
                    result.put(behaviour, equivalentStates);
                }
            }
            equivalentStates.add(state);
        }
        
        return result;
    }

    private static <E> E removeOne(Set<E> set) {
        final Iterator<E> it = set.iterator();
        final E result = it.next();
        it.remove();
        return result;
    }
    
    private static <TL> boolean oldTestClass(Automaton<?,TL> auto, Partition partition, Class cl) {
        if (cl.size() == 1) {
            return true;
        }
        
        final Map<Behaviour<TL>,List<State>> behaviours = new HashMap<Behaviour<TL>, List<State>>();
        for (final State state: cl) {
            final Behaviour<TL> behaviour = computeBehaviour(auto, partition, state);
            final List<State> statesWithEquivalentBehaviour;
            {
                final List<State> tmp = behaviours.get(behaviour);
                if (tmp != null) {
                    statesWithEquivalentBehaviour = tmp;
                } else {
                    statesWithEquivalentBehaviour = new ArrayList<State>();
                    behaviours.put(behaviour, statesWithEquivalentBehaviour);
                }
            }
            statesWithEquivalentBehaviour.add(state);
        }
        
        if (behaviours.size() == 1) {
            return true;
        }
        
        final Collection<List<State>> newDistribution = behaviours.values();
        partition.split(newDistribution);
        return false;
    }

}
class Class implements Iterable<State> {
    private final Set<State> _states;
    
    public Class(Set<State> states) {
        _states = states;
    }
    
    public Class(Collection<? extends State> states) {
        _states = new HashSet<State>(states);
    }
    
    public boolean contains(State state) {
        return _states.contains(state);
    }
    
    @Override
    public Iterator<State> iterator() {
        return Collections.unmodifiableCollection(_states).iterator();
    }
    
    public int size() {
        return _states.size();
    }
    
    @Override
    public String toString() {
        return _states.toString();
    }
}

// The partition of states
class Partition {
    private final Map<State,Class> _classes;
    
    public Partition(Collection<? extends State> states) {
        _classes = new HashMap<State, Class>();
        
        final Class singleClass = new Class(states);
        for (final State state: states) {
            _classes.put(state, singleClass);
        }
    }
    
    public Class getClass(State state) {
        return _classes.get(state);
    }
    
    public Set<Class> getClasses() {
        final Set<Class> classes = new HashSet<Class>();
        classes.addAll(_classes.values());
        return classes;
    }
    
    public void split(Collection<? extends Collection<State>> newClasses) {
        for (final Collection<State> equivalentStates: newClasses) {
            // Assert: all states in equivalentStates have same class
            final Class newClass = new Class(equivalentStates);
            for (final State state: newClass) {
                _classes.put(state, newClass);
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        
        for (final Class cl: getClasses()) {
            builder.append(cl).append("\n");
        }
        
        return builder.toString();
    }
}

// A behaviour (i.e., the classes of states hit from a state after some event occurrence.
class Behaviour<TL> {
    private final Map<TL,Set<Class>> _nextClasses;
    
    Behaviour(Map<TL,Set<Class>> nextClasses) {
        _nextClasses = nextClasses;
    }
    
    @Override
    public int hashCode() {
        return _nextClasses.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof Behaviour)) {
            return false;
        }
        
        final Behaviour other = (Behaviour)o;
        
        return _nextClasses.equals(other._nextClasses);
    }
    
    public Set<TL> transitionLabels() {
        return Collections.unmodifiableSet(_nextClasses.keySet());
    }
    
    public Set<Class> classesReached(TL label) {
        final Set<Class> result = _nextClasses.get(label);
        if (result != null) {
            return Collections.unmodifiableSet(result);
        }
        
        return Collections.emptySet();
    }
}

class BehaviourBuilder<TL> {
    private final Map<TL,Set<Class>> _nextClasses;
    private boolean _reached = false;
    
    public BehaviourBuilder() {
        _nextClasses = new HashMap<TL, Set<Class>>();
    }
    
    public void add(TL label, Class classReached) {
        if (_reached) {
            throw new IllegalStateException();
        }
        
        final Set<Class> classesAlreadyReachedByLabel;
        {
            final Set<Class> tmp = _nextClasses.get(label);
            if (tmp != null) {
                classesAlreadyReachedByLabel = tmp;
            } else {
                classesAlreadyReachedByLabel = new HashSet<Class>();
                _nextClasses.put(label, classesAlreadyReachedByLabel);
            }
        }
        
        classesAlreadyReachedByLabel.add(classReached);
    }
    
    public Behaviour<TL> getBehaviour() {
        _reached = true;
        
        return new Behaviour<TL>(_nextClasses);
    }
}

// An iterator on the outgoing transition labels for the states in the specified class
class ClassTransitionLabelIterator<TL> implements Iterator<TL> {
    private final ReadableAutomaton<?,TL> _auto;
    
    private Iterator<State> _stateIt;
    private Iterator<? extends Transition> _transIt;
    private TL _next;
    private boolean _hasNext = true;
    
    public ClassTransitionLabelIterator(ReadableAutomaton<?,TL> auto, Class cl) {
        _auto = auto;
        _stateIt = cl.iterator();
        final State state = _stateIt.next();
        _transIt = auto.outgoingTrans(state).iterator();
        computeNext();
    }
    
    private void computeNext() {
        for (;;) {
            if (_transIt.hasNext()) {
                final Transition nextTrans = _transIt.next();
                _next = _auto.transLabel(nextTrans);
                return;
            }
        
            if (_stateIt.hasNext()) {
                final State nextState = _stateIt.next();
                _transIt = _auto.outgoingTrans(nextState).iterator();
                continue;
            }
            
            _hasNext = false;
            return;
        }
    }

    @Override
    public boolean hasNext() {
        return _hasNext;
    }

    @Override
    public TL next() {
        if (!_hasNext) {
            throw new NoSuchElementException();
        }
        
        final TL result = _next;
        computeNext();
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}