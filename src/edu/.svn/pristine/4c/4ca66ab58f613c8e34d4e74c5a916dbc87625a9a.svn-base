/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto;

import edu.supercom.util.auto.label.EmbeddedLabel;

/**
 * A <b>trajectory</b> is an ordered and finite sequence of transitions on an
 * automaton.
 *
 * @author Alban Grastien
 */
public interface Trajectory<SL, TL> {

    /**
     * Returns the original automaton from which the trajectory is extracted.  
     * Note that this automaton may have been modified.  
     *
     * @return the automaton on which this trajectory is computed.  
     */
    public Automaton<SL,TL> originalAutomaton();

    /**
     * Returns the number of transitions in this trajectory.  The number of
     * states is this number plus one.
     *
     * @return the number of transitions in this trajectory.  
     */
    public int nbTransitions();

    /**
     * Returns the <i>i</i>th state of this trajectory.  The first state is the
     * state <code>0</code> and the last one is the state {@link
     * #nbTransitions()}.
     *
     * @param i the number of the state.
     * @return the <i>i</i>th state of this trajectory.
     */
    public State getState(int i);

    /**
     * Returns the state label of the <i>i</i>th state of this trajectory.  This
     * method may be more efficient than using
     * <code>originalAutomaton.stateLabel(getState(i));</code>.
     *
     * @param i the number of the state.
     * @return the label of the <i>i</i>th state of this trajectory.
     */
    public SL getStateLabel(int i);

    /**
     * Returns the <i>i</i>th transition of this trajectory.  The first
     * transition is the number <code>1</code> and the last one is {@link
     * #nbTransitions()}.
     *
     * @param i the number of the transition.
     * @return the <i>i</i>th transition of this trajectory.
     */
    public Transition getTrans(int i);

    /**
     * Returns the trans label of the <i>i</i>th transitionof this trajectory.
     * This method may be more efficient than using
     * <code>originalAutomaton.transLabel(getTrans(i));</code>.
     *
     * @param i the number of the transition.
     * @return the label of the <i>i</i>th transition of this trajectory.
     */
    public TL getTransLabel(int i);

    /**
     * Returns an automaton that represents this trajectory.  Since the
     * trajectory may loop, the same state may be represented several times.
     * Therefore, the state labels have to be embedded.
     *
     * @return an automaton representation of this trajectory.  
     */
    public Automaton<EmbeddedLabel<SL>,TL> automatonRepresentation();
}
