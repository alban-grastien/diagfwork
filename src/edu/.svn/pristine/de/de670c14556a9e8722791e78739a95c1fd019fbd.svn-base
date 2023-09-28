/*
 * Transition.java
 *
 * Created on 14 February 2007, 11:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.auto;

/**
 * This class implements a transition between two states.  A transition is
 * basically a pair of states.
 * 
 * @author Alban Grastien
 * @version 1.0
 * @since 0.5
 */
public interface Transition extends Comparable<Transition> {

    /**
     * Returns the origin of the transition.
     *
     * @return the origin of the transition.
     */
    public State getOrigin();

    /**
     * Returns the target of the transition.
     *
     * @return the target of the transition.
     */
    public State getTarget();

    /**
     * Compares this object with the specified object for order. Returns a 
     * negative integer, zero, or a positive integer as this object is less 
     * than, equal to, or greater than the specified object.
     *
     * @param t the transition to compare to this transition.
     * @return a negative integer, zero, or a positive integer as this object 
     * is less than, equal to, or greater than the specified object.
     * @throws ClassCastException <code>o</code> is not a Transition.
     */
    @Override
    public int compareTo(Transition t);
}
