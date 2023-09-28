/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.changer.LabelChanger;

/**
 * An <code>automaton label changer</code> is an object that is able to change
 * the labels of the specified automaton.
 *
 * @author Alban Grastien
 */
public interface AutomatonLabelChanger {

    /**
     * Changes the transition labels of the specified automaton according to the
     * specified trans label mapper.
     *
     * @param auto the automaton whose transition labels are to be modified.
     * @param mapper the mapper that indicates how the labels must be modified.
     */
    public <TL> void changeTransLabels(
            Automaton<?,TL> auto,
            LabelChanger<? super TL, ? extends TL> mapper);

    /**
     * Changes the state labels of the specified automaton according to the
     * specified state label mapper.
     *
     * @param auto the automaton whose state labels are to be modified.
     * @param mapper the mapper that indicates how the labels must be modified.
     * @throws IllegalArgumentException in case the modification gives the same 
     * labels to different states.  In this case, the automaton may be
     * corrupted.
     */
    public <SL> void changeStateLabels(
            Automaton<SL,?> auto,
            LabelChanger<? super SL, ? extends SL> mapper);

    /**
     * Changes both the transition and the state labels of the specified
     * automaton according to the specified label mapper.
     *
     * @param auto the automaton whose labels are to be modified.
     * @param stateMap the mapper that indicates how the state labels must be
     * modified.
     * @param transMap the mapper that indicates how the transition labels must
     * be modified.
     * @throws IllegalArgumentException in case the modification gives the same
     * labels to different states.  In this case, the automaton may be
     * corrupted.
     */
    public <SL, TL> void changeLabels(
            Automaton<SL,TL> auto,
            LabelChanger<? super SL, ? extends SL> stateMap,
            LabelChanger<? super TL, ? extends TL> transMap
            );

}
