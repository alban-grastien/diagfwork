/*
 * TotalizerEncoding.java
 *
 * Created on 16 May 2008, 17:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

/**
 * A totalizer encoding is a class that is able to generate nodes for a {@link Totalizer}.  
 *
 * @author Alban Grastien
 */
public interface TotalizerEncoding {
    
    /**
     * Encodes a node for the specified variable.
     *
     * @param n the number of the variable.
     */
    public Totalizer encode(int n);
    
    /**
     * Encodes a node corresponding to the two specified subnodes.
     *
     * @param n1 the first node.
     * @param n2 the second node.
     */
    public Totalizer encode(Totalizer n1, Totalizer n2);
    
}
