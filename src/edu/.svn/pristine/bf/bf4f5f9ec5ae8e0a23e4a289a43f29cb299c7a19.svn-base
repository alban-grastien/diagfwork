/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto;

import edu.supercom.util.auto.label.EmbeddedLabel;
import edu.supercom.util.auto.label.MyEmbeddedLabel;

/**
 * Simple implementation of a trajectory.  
 *
 * @author Alban Grastien
 */
public class SimpleTrajectory<SL, TL>
        implements Trajectory<SL,TL> {

    /**
     * The original automaton of this trajectory.
     */
    private Automaton<SL,TL> _auto;

    /**
     * The list of transitions in this trajectory.  
     */
    private Transition[] _trans;

    @Override
    public Automaton<SL, TL> originalAutomaton() {
        return _auto;
    }

    @Override
    public int nbTransitions() {
        return _trans.length;
    }

    @Override
    public State getState(int i) {
        if (i == _trans.length) {
            return _trans[i-1].getTarget();
        }
        return _trans[i].getOrigin();
    }

    @Override
    public SL getStateLabel(int i) {
        return _auto.stateLabel(getState(i));
    }

    @Override
    public Transition getTrans(int i) {
        return _trans[i-1];
    }

    @Override
    public TL getTransLabel(int i) {
        return _auto.transLabel(getTrans(i));
    }

    @Override
    public Automaton<EmbeddedLabel<SL>, TL> automatonRepresentation() {
        return automatonRepresentation(this);
    }

    public static <SL, TL> 
            Automaton<EmbeddedLabel<SL>, TL> automatonRepresentation(Trajectory<SL,TL> traj) {
        Automaton<SL,TL> auto = traj.originalAutomaton();
        Automaton<EmbeddedLabel<SL>,TL> result = auto.buildAutomaton(auto.getEmptyTransitionLabel());

        int nbTrans = traj.nbTransitions();
//        State[] states = new State[nbTrans];
        State currentState;
        {
            State autoState = traj.getState(0);
            SL autoSl = auto.stateLabel(autoState);
            EmbeddedLabel<SL> sl = MyEmbeddedLabel.embed(autoSl);
            currentState = result.newState(sl);
            auto.setInitial(autoState);
        }

        for (int i=0 ; i<nbTrans ; i++) {
            Transition trans = traj.getTrans(i+1);
            TL transLabel = auto.transLabel(trans);
            State autoNextState = trans.getTarget();
            SL autoSl = auto.stateLabel(autoNextState);
            EmbeddedLabel<SL> sl = MyEmbeddedLabel.embed(autoSl);
            State nextState = result.newState(sl);
            result.newTransition(currentState, nextState, transLabel);
            currentState = nextState;
        }

        result.setFinal(currentState);

        return result;
    }

}
