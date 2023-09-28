/*
 * SeededPseudoRandom.java
 *
 * Created on 30 November 2008, 15:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util;

/**
 * A pseudo random implementation based on a seed.  Two identical sequences of
 * calls on two instances of this class with the same seed will generate the
 * same output.  
 *
 * @author Alban Grastien
 */
public class SeededPseudoRandom implements PseudoRandom {
    
    /**
     * The current state of the randomiser. 
     */
    private int _rand;
    
    /** 
     * Creates a new instance of SeededPseudoRandom.
     *
     * @param seed the seed of this randomiser.
     */
    public SeededPseudoRandom(int seed) {
        _rand = seed;
    }
    
    @Override
    public int rand(int n) {
        _rand = (_rand * 1103515245) + 12345;
        int result = _rand % n;
        while (result< 0) {
            result += n;
        }
        return result;
    }
}
