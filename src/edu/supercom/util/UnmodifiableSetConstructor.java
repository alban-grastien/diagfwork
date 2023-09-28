/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * An unmodifiable set constructor is an object that is able to build an
 * unmodifiable set.  The construction is done as follows: first the user calls
 * {@link #Constructor()} (or a different constructor with, e.g., an already
 * defined collection of elements); second, the user adds and removes elements
 * for the collection that is not unmodifiable yet; last, the user accesses the
 * set which automatically becomes unmodifiable (the constructor is then
 * useless).  The main advantage of this class is that it is not necessary to
 * store the elements in a set and copy them: they can be stored in an
 * unmodifiable set from the beginning.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <E> the class of objects in the set constructed.
 * @todo override the hashcode methods for the constructor and the set, and for
 * similar classes.
 */
public class UnmodifiableSetConstructor<E> {

    /**
     * The unmodifiable collection that will be returned when the set is
     * accessed.
     */
    private final UnmodifiableSet<E> _result;

    /**
     * A flag indicating if the set has been accessed.  If so, the set can no
     * longer be modified.
     */
    private boolean _flag = false;

    /**
     * The set of elements in <code>_coll</code>.  The user can modify it until
     * <code>_flag</code> is set to <code>true</code>.  
     */
    private Set<E> _set;

    /**
     * Builds an unmodifiable set constructor for an empty unmodifiable set.
     */
    @SuppressWarnings("unchecked")
    public UnmodifiableSetConstructor() {
        this((Collection<E>)Collections.emptySet());
    }

    /**
     * Builds an unmodifiable set constructor for an empty unmodifiable set with 
     * the specific class of set for the internal implementation.
     *
     * @param cl the class of set that should be used internally.  
     */
    public UnmodifiableSetConstructor(Class<? extends Set<E>> cl) throws InstantiationException, IllegalAccessException {
        _set = cl.newInstance();
        _result = new USCS<E>(_set);
    }

    /**
     * Builds an unmodifiable set constructor for an unmodifiable set with the
     * specified set of elements.
     *
     * @param set the set of elements in the unmodifiable set at the
     * construction.
     */
    public UnmodifiableSetConstructor(Collection<? extends E> set) {
        _set = new HashSet<E>(set);
        _result = new USCS<E>(_set);
    }

    /**
     * Builds an unmodifiable set constructor that contains the specified
     * elements.
     *
     * @param set the elements in the unmodifiable set at the construction.
     */
    public UnmodifiableSetConstructor(E... set) {
        _set = new HashSet<E>();
        _result = new USCS<E>(_set);
        for (final E e: set) {
            _set.add(e);
        }
    }

    /**
     * Adds the specified element to the unmodifiable set.  If the set is no
     * longer modifiable, this method throws an {@link IllegalStateException}.
     *
     * @param e the element to add to the set.
     * @return true if the element was successfully added. 
     * @see #getSet()
     */
    public boolean add(E e) {
        if (_flag) {
            throw new IllegalStateException("Cannot add an element after the set has been accessed.");
        }
        return _set.add(e);
    }

    /**
     * removes the specified element from the unmodifiable set.  If the set is 
     * no longer modifiable, this method throws an {@link IllegalStateException}.
     *
     * @param e the element to remove to the set.
     * @return true if the element was successfully removed.
     * @see #getSet()
     */
    public boolean remove(E e) {
        if (_flag) {
            throw new IllegalStateException("Cannot add an element after the set has been accessed.");
        }
        return _set.remove(e);
    }

    /**
     * Returns the unmodifiable set.  After this method has been called, the
     * methods {@link #add(java.lang.Object)} and {@link
     * #remove(java.lang.Object)} can no longer be called.
     */
    public UnmodifiableSet<E> getSet() {
        _flag = true;
        return _result;
    }
}

class USCS<E> extends UnmodifiableSet<E> {
    /**
     * The set that contains the elements.  
     */
    private final Set<E> _set;

    public USCS(Set<E> set) {
        _set = Collections.unmodifiableSet(set);
    }

    @Override
    public UnmodifiableSet<E> addElement(E el) {
        final UnmodifiableSetConstructor<E> usc = new UnmodifiableSetConstructor<E>(_set);
        usc.add(el);
        return usc.getSet();
    }

    @Override
    public UnmodifiableSet<E> addElements(Collection<? extends E> coll) {
        final UnmodifiableSetConstructor<E> usc = new UnmodifiableSetConstructor<E>(_set);
        for (final E el: coll) {
            usc.add(el);
        }
        return usc.getSet();
    }

    @Override
    public UnmodifiableSet<E> removeElement(E el) {
        final UnmodifiableSetConstructor<E> usc = new UnmodifiableSetConstructor<E>(_set);
        usc.remove(el);
        return usc.getSet();
    }

    @Override
    public UnmodifiableSet<E> removeElements(Collection<? extends E> coll) {
        final UnmodifiableSetConstructor<E> usc = new UnmodifiableSetConstructor<E>(_set);
        for (final E el: coll) {
            usc.remove(el);
        }
        return usc.getSet();
    }

    @Override
    public Iterator<E> iterator() {
        return _set.iterator();
    }

    @Override
    public int size() {
        return _set.size();
    }

}