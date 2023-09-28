/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.auto.SimpleAutomaton;
import edu.supercom.util.auto.changer.LabelChanger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A <code>simple copier</code> is an automaton copier that makes explicit use
 * of label changers.  
 *
 * @author Alban Grastien
 */
public class SimpleCopier<SL1,TL1,SL2,TL2>
        implements ChangerCopier<SL1, TL1, SL2, TL2> {

    /**
     * The state changer.
     */
    private LabelChanger<? super SL1,? extends SL2> _stateChanger;
    /**
     * The trans changer.
     */
    private LabelChanger<? super TL1,? extends TL2> _transChanger;

    /**
     * Creates a simple copier from the specified changers.
     *
     * @param s the state label changer that specifies how the state label must
     * be changed when the state is copied.
     * @param t the trans label changer that specifies how the state label must
     * be changed when the state is copied.
     */
    public SimpleCopier(
            LabelChanger<? super SL1, ? extends SL2> s,
            LabelChanger<? super TL1, ? extends TL2> t) {
        _stateChanger = s;
        _transChanger = t;
    }

    @Override
    public LabelChanger<? super SL1,? extends SL2> getStateChanger() {
        return _stateChanger;
    }

    @Override
    public LabelChanger<? super TL1,? extends TL2> getTransChanger() {
        return _transChanger;
    }

    @Override
    public Automaton<SL2, TL2> copy(ReadableAutomaton<SL1, TL1> auto) {
        Map<State,State> corr = new HashMap<State, State>();
        return copy(auto, corr);
    }

    @Override
    public Automaton<SL2, TL2> copy(ReadableAutomaton<SL1, TL1> auto, Map<State,State> corr) {
        Automaton<SL2,TL2> result;
        {
            TL1 eps1 = auto.getEmptyTransitionLabel();
            TL2 eps2 = _transChanger.getLabel(eps1);
            result = new SimpleAutomaton<SL2, TL2>(eps2);
        }

        // Copy the states
        for (final State state1: auto.states()) {
            SL1 label1 = auto.stateLabel(state1);
            SL2 label2 = _stateChanger.getLabel(label1);
            State state2 = result.newState(label2);
            if (state2 == null) {
                throw new IllegalArgumentException("Conflicting labels " + label2);
            }
            corr.put(state1, state2);

            if (auto.isInitial(state1)) {
                result.setInitial(state2);
            }
            if (auto.isFinal(state1)) {
                result.setFinal(state2);
            }
        }

        // Copy the transitions
        // Rather than an iteration on the transition, we iterate on the states
        // and then the outgoing transitions of this state so that we don't have to
        // recompute the origine for each transition.
        for (final State orig1: auto.states()) {
            State orig2 = corr.get(orig1);
//            System.out.println("From state " + auto.stateLabel(orig1));
//            for (Transition trans: auto.outgoingTrans(orig1)) {
//                System.out.println("\t" + auto.transLabel(trans) + " to " + auto.stateLabel(trans.getTarget()));
//            }
            for (final Transition trans: auto.outgoingTrans(orig1)) {
                State targ1 = trans.getTarget();
                State targ2 = corr.get(targ1);
                TL1 tl1 = auto.transLabel(trans);
//                System.out.println("Changing transition label " + tl1);
                TL2 tl2 = _transChanger.getLabel(tl1);
                result.newTransition(orig2, targ2, tl2);
            }
        }

        return result;
    }
}
