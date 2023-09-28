/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.PseudoRandom;
import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.Trajectory;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.auto.label.TransCounter;
import edu.supercom.util.auto.label.TransitionCounter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of a simulator.  
 *
 * @author Alban Grastien
 */
public class SimpleSimulator implements Simulator {

    /**
     * The number of tries before the simulation is stopped.  
     */
    private int _nbSimul;

    /**
     * The number of tries in a specified state before the simulation backtracks.  
     */
    private int _localTries;

    /**
     * A pseudo random generator.
     */
    private PseudoRandom _rand;

    @Override
    public <SL, TL> Trajectory<SL, TL> simulate(Automaton<SL, TL> a, int l) {
        return simulate(a, l, new TransitionCounter());
    }

    @Override
    public <SL, TL> Trajectory<SL, TL> simulate(Automaton<SL, TL> a, int l, TransCounter<? super TL> c) {
        List<Transition> transitions = new ArrayList<Transition>();
        List<Integer> cost = new ArrayList<Integer>();
        cost.add(0);
        List<Integer> nbTries = new ArrayList<Integer>();

// TODO


    throw new UnsupportedOperationException("Not supported yet.");
    }
}
