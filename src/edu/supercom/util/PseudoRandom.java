package edu.supercom.util;

/**
 * A <b>Pseudo Random</b> object is an object able to generate a pseudo random
 * integer value.
 *
 * @author Alban Grastien
 */
public interface PseudoRandom {

    /**
     * Returns an integer between 0 and the specified value.
     *
     * @param n the maximum value (+1) allowed for the result.
     * @return a number between 0 and <code>n -1</code>.
     */
    public int rand(int n);

}
