/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * An unmodifiable list constructor is an object that is able to build an
 * unmodifiable lsit.  The construction is done as follows: first the user calls
 * {@link #Constructor()} (or a different constructor with, e.g., an already
 * defined list of elements); second, the user adds and removes elements for the
 * collection that is not unmodifiable yet; last, the user accesses the list
 * which automatically becomes unmodifiable (the constructor is then useless).
 * The main advantage of this class is that it is not necessary to store the
 * elements in a list and later copy them: they can be stored in an unmodifiable
 * list from the beginning.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <E> the type of elements in the unmodifiable list.  
 */
public class UnmodifiableListConstructor<E> {

    /**
     * The unmodifiable list.  
     */
    private final UnmodifiableList<E> _result;

    /**
     * The flag that indicates whether the unmodifiable list has been accessed.
     * After it has been, the constructor cannot modify the list any more.  
     */
    private boolean _flag = false;

    /**
     * The list that is stored in _result.  This is used to modify the list
     * until the list is accessed.  
     */
    private final List<E> _list;

    /**
     * Creates a constructor with an empty collection of elements.
     */
    @SuppressWarnings("unchecked")
    public UnmodifiableListConstructor() {
        this((List<E>)Collections.emptyList());
    }

    /**
     * Creates a constructor with a predefined collection of elements.
     *
     * @param coll the collection of elements to put in the unmodifiable list.
     */
    public UnmodifiableListConstructor(Collection<? extends E> coll) {
        _list = new ArrayList<E>(coll);
        _result = new ULCL<E>(_list);
    }
    
    public UnmodifiableListConstructor(Class<? extends List<E>> cl) 
    throws InstantiationException, IllegalAccessException {
        _list = cl.newInstance();
        _result = new ULCL<E>(_list);
    }

    /**
     * Returns the unmodifiable list.  After this method has been called, the
     * constructor cannot modify the list.
     *
     * @return the constructed unmodifiable list.
     */
    public UnmodifiableList<E> getList() {
        _flag = true;
        return _result;
    }

    /**
     * Adds an element to the unmodifiable list being constructed by this
     * constructor.  This method throws an exception if the list has already
     * been accessed.
     *
     * @param el the element to add to this list.
     * @return true if the element was added to the list.
     */
    public boolean addElement(E el) {
        if (_flag) {
            throw new IllegalStateException("Constructed list has been accessed: unmodifiable.");
        }
        return _list.add(el);
    }

    /**
     * Removes an element from the unmodifiable list being constructed by this
     * constructor.  This method throws an exception if the list has already
     * been accessed.
     *
     * @param el the element to remove from this list.
     * @return true if the element was removed from the list.
     */
    public boolean removeElement(E el) {
        if (_flag) {
            throw new IllegalStateException("Constructed list has been accessed: unmodifiable.");
        }
        return _list.remove(el);
    }

}

class ULCL<E> extends ImplicitUnmodifiableList<E> {

    private final List<E> _list;

    public ULCL(List<E> list) {
        _list = Collections.unmodifiableList(list);
    }

    @Override
    public int size() {
        return _list.size();
    }

    @Override
    public E get(int index) {
        return _list.get(index);
    }

	@Override
	public UnmodifiableListConstructor<E> makeExplicit() {
		return new UnmodifiableListConstructor<E>(this);
	}

}
