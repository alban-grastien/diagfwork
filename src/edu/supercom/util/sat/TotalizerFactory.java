/*
 * TotalizerFactory.java
 *
 * Created on 9 May 2008, 16:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A totalizer factory is a class that provides useful methods for building
 * totalizers.
 *
 * @author Alban Grastien
 * @version 2.0
 * @todo rewrite the method.  
 */
public class TotalizerFactory {
    
    /**
     * Generates a node from the specified collection of
     * <code>VariableSemantics</code> or/and <code>Totalizer</code> with a specified
     * encoding.  The node generated is balanced and the order of the
     * variables is the same as the order returned by the method {@link
     * Collection#iterator()} of the collection.
     * 
     * @param coll the collection of variable semantics/nodes.
     * @param varass the variable assigner that indicates which variable
     * corresponds to a variable semantics.
     * @param encoding the encoding used to encode the variables.
     * @return the node created from the collection of variable semantics.
     */
    public static Totalizer createBalancedTotalizer(Collection<? extends Object> coll, 
    		VariableAssigner varass, TotalizerEncoding encoding) {
    	if (coll.isEmpty()) { // Creating a dummy totalizer
    		return new Totalizer() {
				
    			private int _var = -1;
    			
				@Override
				public void upgrade(VariableLoader loader, ClauseStream out) {
					if (_var == -1) {
						_var = loader.allocate(1);
					}
				}
				
				@Override
				public int max() {
					return Integer.MAX_VALUE;
				}
				
				@Override
				public void less(int val, ClauseStream out) {
					// Nothing to do
				}
				
				@Override
				public void ge(int val, ClauseStream out) {
					if (val > 1) { // Generate a contradiction
						out.put(_var);
						out.put(-_var);
					}
				}
			};
    	}
    	
    	// Creates all the nodes.
        final LinkedList<Totalizer> list = new LinkedList<Totalizer>(); // Contains all the nodes.
        for (Iterator<? extends Object> varIt = coll.iterator() ; varIt.hasNext() ; ) {
            list.add(readNode(varIt,varass,encoding));
        }
        
        while (list.size() != 1) { // Empties the list.
            final Totalizer n1 = list.remove(0);
            final Totalizer n2 = list.remove(0);
            final Totalizer n = encoding.encode(n1,n2);
            list.add(n);
        }
        
        return list.remove(0);
    }
    
    /**
     * Creates a node from the specified collection of collections of
     * <code>VariableSemantics</code>.  The node is built incrementally
     * from the list of variables.
     *
     * @param coll the collection of variable semantics/nodes.
     * @param varass the variable assigner that indicates which variable
     * corresponds to a variable semantics.
     * @param encoding the encoding used to encode the variables.
     * @return the node created from the collection of variable semantics.
     */
    public static Totalizer createIncrementalTotalizer(Collection <? extends Object> coll, VariableAssigner varass, TotalizerEncoding encoding) {
        final Iterator<? extends Object> varIt = coll.iterator();
        
        Totalizer current = readNode(varIt,varass,encoding);
        
        while (varIt.hasNext()) {
            current = encoding.encode(current,readNode(varIt,varass,encoding));
        }
        
        return current;
    }
    
    /**
     * Reads the specified iterator that contains a node or a variable semantics 
     * and extracts a node.
     *
     * @param it the iterator.
     * @return the node if the first element of the iterator was a node, a node 
     * created from the specified node encoding if the first element was a 
     * variable semantics.
     */
    private static Totalizer readNode(Iterator<? extends Object> it, VariableAssigner varass, TotalizerEncoding encoding) {
        final Totalizer node;
        {
            final Object next = it.next();
            if (next instanceof Totalizer) {
                node = (Totalizer)next;
            } else {
                VariableSemantics sem = (VariableSemantics)next;
                int val = varass.getVariable(sem);
                node = encoding.encode(val);
            }
        }
        return node;
    }
}
