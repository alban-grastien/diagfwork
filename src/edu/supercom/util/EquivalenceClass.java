/*
 * EquivalenceClass.java
 *
 * Created on 21 March 2007, 16:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implements a class of equivalence.  Two classes that contain the same 
 * elements are not necessarily equal.
 *
 * @author Alban Grastien
 * @version 1.0.1
 * @since 1.0.1
 * @param E the type of object in the equivalence class.  
 */
//public class EquivalenceClass<E> implements Iterable<E>, Comparable<E> {
public class EquivalenceClass<E> implements Iterable<E> {


    /**
     * The set of elements in the equivalence class.
     */
    private final Set<E> _elements;

    /**
     * Returns an iterator on the elements of this equivalence class.
     *
     * @return an iterator on the elements of this equivalence class.
     */
    @Override
    public Iterator<E> iterator() {
        return _elements.iterator();
    }

    /**
     * Returns the number of elements in the class.
     *
     * @return the number of elements in the class.
     */
    public int size() {
        return _elements.size();
    }

    /**
     * Returns whether the class is empty.
     *
     * @return <code>true</code> if <code>this.size() == 0</code>.
     */
    public boolean isEmpty() {
        return _elements.isEmpty();
    }

    /**
     * Returns a copy of the set of elements in this equivalence class.
     *
     * @return a set that contains the same elements as this equivalence class.
     */
    public Set<E> toSet() {
    	return _elements;
    }

    /**
     * Returns a string representation of this equivalence class.  The 
     * representation of each class consists of the list of elements in the 
     * class in the order the <code>iterator()</code> method returns them, 
     * enclosed in square brackets ("[]"). Adjacent elements are separated by 
     * the characters ", " (comma and space).
     */
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("[");
        final Iterator<E> it = iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(", ");
            }
        }
        return result.append("]").toString();
    }

    /**
     * Creates an equivalence class with only one element.
     *
     * @param e the first element in the equivalence class.
     */
    EquivalenceClass(E o) {
        _elements = new HashSet<E>();
        _elements.add(o);
    }

    /**
     * Adds the specified element to this equivalence class.
     *
     * @param e the element to add to this equivalence class.
     */
    void add(E o) {
        _elements.add(o);
    }

    /**
     * Adds all the elements in the specified iterator to this equivalence 
     * class.  After this method is called, the iterator is finished.
     *
     * @param it the iterator whose elements are added to this equivalence class.
     */
    void addAll(Iterator<E> it) {
        while (it.hasNext()) {
            add(it.next());
        }
    }

    /**
     * Clears this equivalence class.  This method should only be used by 
     * {@link Equivalence}.
     */
    void clear() {
        _elements.clear();
    }

    /**
     * Removes an element from this equivalence class.
     *
     * @param e the element to remove.
     */
    void remove(Object e) {
        _elements.remove(e);
    }
    
    @Override
    public int hashCode() {
    	return _elements.hashCode();
    }
    
    /*
     * Quick and ugly hack to make the program behave deterministically. 
    @Override
    public int compareTo(Object o) {
    	return 2;
    }
    */
}
