/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.op;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.State;
import edu.supercom.util.auto.Transition;
import edu.supercom.util.auto.changer.LabelChanger;

/**
 *
 * @author Alban Grastien
 */
public class SimpleLabelChanger implements AutomatonLabelChanger {

    @Override
    public <TL> void changeTransLabels(
            Automaton<?, TL> auto,
            LabelChanger<? super TL, ? extends TL> mapper) {
        auto.setEmptyTransitionLabel(mapper.getLabel(auto.getEmptyTransitionLabel()));
        for (final Transition t: auto.transitions()) {
            TL tl = auto.transLabel(t);
            TL tl2 = mapper.getLabel(tl);
            auto.changeTransLabel(t, tl2);
        }
    }

    @Override
    public <SL> void changeStateLabels(
            Automaton<SL, ?> auto,
            LabelChanger<? super SL, ? extends SL> mapper) {
        // An issue here is that it is may be the case that state label /a/ is
        // replaced by /b/ and /b/ by /a/.  Therefore, we first replace
        // everything by an integer (which, admitively, does not satisfy the 
        // generics requirement) and then replace by the real value.
        final int size = auto.states().size();
        final Object [] newLabels = new Object[size];
        final State  [] states    = new State[size];

        {
            int i = 0;
            for (final State state: auto.states()) {
                SL sl = (SL)new MyIntegerLabel(i);
                SL old = auto.changeStateLabel(state, sl);
                newLabels[i] = mapper.getLabel(old);
                states[i] = state;
                i++;
            }
        }

        for (int i=0 ; i<size ; i++) {
            State state = states[i];
            SL label = (SL)newLabels[i];
            auto.changeStateLabel(state, label);
        }
    }

    @Override
    public <SL,TL> void changeLabels(
            Automaton<SL, TL> auto,
            LabelChanger<? super SL, ? extends SL> stateMap,
            LabelChanger<? super TL, ? extends TL> transMap) {
        changeTransLabels(auto,transMap);
        changeStateLabels(auto,stateMap);
    }

}

class MyIntegerLabel {

    /**
     * The int value of this label.
     */
    private int _val;

    /**
     * Creates an integer label with the specified value.
     *
     * @param val the value of this label.
     */
    MyIntegerLabel(int val) {
        _val = val;
    }

    /**
     * Returns a hashcode value for this label.
     *
     * @return a hashcode value for this label.
     */
    @Override
    public int hashCode() {
        return _val;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyIntegerLabel other = (MyIntegerLabel) obj;
        if (this._val != other._val) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + _val;
    }

}