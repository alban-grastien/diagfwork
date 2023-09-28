/*
 * Automaton.java
 *
 * Created on 14 February 2007, 13:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.auto;


import edu.supercom.util.auto.changer.LabelChanger;
import edu.supercom.util.auto.op.SimpleRefiner;
import edu.supercom.util.auto.op.AutomatonLabelChanger;
import java.util.NoSuchElementException;
import java.util.Set;


/**
 * An automaton is a set of states and transitions between states.  Both the 
 * states and the transitions are labeled.  A special transition label
 * corresponds to no occurrence of events and is called the epsilon transition
 * label; by default, any state has a loop labeled by this label. No two states
 * should have the same label, although not all implementations test this
 * condition.
 *
 * @author Alban Grastien
 * @author Carole Aujames
 * @version 2.0
 * @since 0.5
 */
public interface Automaton<SL,TL>
 extends AutomatonFactory, ReadableAutomaton<SL,TL>  {
    
    /**
     * Creates a new state with the specified label.  No two states can
     * have the same label.
     *
     * @param label the label of the state.
     * @return the state newly created, <code>null</code> if a state already
     * exists with this label.
     */
    public State newState(SL label);
    
    /**
     * Removes the specified state in this automaton.  This state must have no
     * transition.  If the state has transitions, it is not removed.
     *
     * @param state the state to delete.
     * @return <code>true</code> if the state was removed.
     * @throws NullPointerException if <code>state == null</code>.
     * @throws IllegalArgumentException if the state has transitions.
     * @see #removeAll(edu.supercom.util.auto.State) 
     */
    public boolean remove(State state);
    
    /**
     * Adds the specified state to the set of initial states of this automaton.
     *
     * @param state the state to add to the set of initial states.
     * @return <code>true</code> if the state is added to the set of initial
     * states, <code>false</code> if it is already initial.
     * @throws NullPointerException if <code>state == null</code>
     */
    public boolean setInitial(State state);
    
    /**
     * Removes the specified state from the set of initial states of this automaton.
     *
     * @param state the state to remove from the set of initial states.
     * @return <code>true</code> if the state is removed from the set of initial states.
     */
    public boolean unsetInitial(State state);
    
    /**
     * Adds the specified state to the set of final states of this automaton.
     *
     * @param state the state to add to the set of final states.
     * @return <code>true</code> if the state is added to the set of final states.
     * @throws NullPointerException if <code>state == null</code>
     */
    public boolean setFinal(State state);
    
    /**
     * Removes the specified state from the set of final states of this automaton.
     *
     * @param state the state to remove from the set of final states.
     * @return <code>true</code> if the state is removed from the set of final states.
     */
    public boolean unsetFinal(State state);
    
    /**
     * Creates a new transition between the two specified states with the
     * specified label in this automaton.  If the specified states are not in
     * this automaton, the behaviour is not specified.
     *
     * @param state1 the origin of the transition.
     * @param state2 the target of the transition.
     * @param label the label of the transition.
     * @return the transition created.
     * @throws NullPointerException if <code>state1 == null</code> or
     * <code>state2 == null</code>.
     */
    public Transition newTransition(State state1, State state2, TL label);
    
    /**
     * Removes the specified transition in this automaton.
     *
     * @param trans the transition to remove from the automaton.
     * @return <code>true</code> if the transition was removed.
     */
    public boolean remove(Transition trans);
    
    /**
     * Removes the specified state and all the transitions from or to the state.
     *
     * @param state the state to remove.
     */
    public void removeAll(State state);

    /**
     * Replaces the state label of the specified state with the specified state
     * label.
     *
     * @param s the state whose label is modified.
     * @param l the new label of state <code>s</code>.
     * @return the old state label.  
     * @throws IllegalArgumentException if the new label is already used by
     * another state.
     * @throws NoSuchElementException if <code>s</code> is not a state of this
     * automaton.
     */
    public SL changeStateLabel(State s, SL l);

    /**
     * Replaces the transition label of the specified state with the specified
     * transition label.
     *
     * @param t the transition whose label is modified.
     * @param l the new label of transition <code>l</code>.
     * @return the old transition label. 
     * @throws NoSuchElementException if <code>l</code> is not a transition of
     * this automaton.
     */
    public TL changeTransLabel(Transition t, TL l);

    //////////////// OPERATIONS

    /**
     * Removes the states that cannot be reached from the initial states.  The
     * default implementation uses the class {@link SimpleRefiner}.
     */
    public void irefine();
    
    /**
     * Removes the states that cannot be reached from the final states.  The
     * default implementation uses the class {@link SimpleRefiner}.
     */
    public void frefine();
    
    /**
     * Implements the <i>trim</i> operation on the automaton.  The trim
     * operation consists in removing the states that cannot be reached from an
     * initial state and removing the state that do not lead to a final state.
     */
    public void trim();
    
    /**
     * Changes the trans labels of the automaton.
     *
     * @param cl the map that creates the new labels.
     * @see AutomatonLabelChanger
     */
    public void changeTransLabels(LabelChanger<? super TL, ? extends TL> cl);
    
    /**
     * Changes the state labels of this automaton.
     *
     * @param cl the map that creates the new labels.
     * @see AutomatonLabelChanger
     */
    public void changeStateLabels(LabelChanger<? super SL,? extends SL> cl);

    /**
     * Changes the labels of the states and the transitions of this automaton
     * according to the specified mapper.
     *
     * @param sm the mapper that creates the new state labels.
     * @param tm the mapper that creates the new trans labels.
     * @see AutomatonLabelChanger
     */
    public void changeLabels(LabelChanger<? super SL, ? extends SL> sm,LabelChanger<? super TL, ? extends TL> tm);

    /**
     * Applies a minimization algorithm on this automaton.  
     */
    public void doMinimize();
    
    /**
     * Generates a new automaton that corresponds to the minimization of this 
     * automaton.  
     * 
     * @return a minimized version of this automaton.  
     */
    public Automaton<Set<SL>,TL> minimize();

    public TL setEmptyTransitionLabel(TL empty);
}

