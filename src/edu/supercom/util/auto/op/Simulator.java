/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.Trajectory;
import edu.supercom.util.auto.label.TransCounter;

/**
 * A simulator is an object that is able to simulate an automaton.  It can
 * generate trajectories.  
 *
 * @author Alban Grastien
 */
public interface Simulator {

    /**
     * Generates a trajectory of the specified automaton with the specified
     * number of transitions.  
     *
     * @param a the automaton on which the simulat
     * @param l the length of the trajectory.
     * @return a trajectory of specified length or <code>null</code> if no such
     * trajectory was found.
     * @see #simulate(edu.supercom.util.auto.Automaton, int, edu.supercom.util.auto.label.TransCounter) 
     */
    public <SL, TL>
            Trajectory<SL,TL> simulate(Automaton<SL,TL> a, int l);

    /**
     * Generates a trajectory of the specified automaton with the specified
     * counting on the specified counter.  The count may be exceeded in case the
     * last transition counts as more than one.<p />
     *
     * Because the automaton may contain deadlocks or contain no trajectory of
     * the specified size, this method may require too much time or even fail.
     * Therefore, the result of this method is <code>null</code> if the
     * simulator was not able to produce a trajectory of specified length.  This
     * does not imply in general that there is no solution, only that the method
     * could not find any.  Because of stochastic dynamics, additional calls to
     * this method may be successful.
     *
     * @param a the automaton on which the simulat
     * @param l the count of the trajectory.
     * @param c the counter.  
     * @return a trajectory of count <code>l</code> or <code>null</code> if no
     * such trajectory was found.
     */
    public <SL, TL>
            Trajectory<SL,TL> simulate(Automaton<SL,TL> a, int l, TransCounter<? super TL> c);

    // Local models!

}
