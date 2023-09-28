/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util;

import java.util.AbstractCollection;
import java.util.Collection;

/**
 * An unmodifiable collection is a collection of elements that cannot be
 * modified.  This property cannot be enforced and is merely a contract that
 * any extension of this abstract class should satisfy.  If the extension of
 * this class fails to satisfy the property, the behaviour of the software is
 * unspecified.  The purpose of this class is to avoid copies of collections
 * that cannot be modified.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <E> The type of elements in the collection.  
 */
public abstract class UnmodifiableCollection<E> extends AbstractCollection<E> implements Collection<E> {

    @Override
    public final boolean add(E e) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
    }

    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException("Unmodifiable collection.");
    }

    /**
     * Returns an unmodifiable collection that contains the elements of this
     * collection, plus the specified element.
     *
     * @param el the element to add to the new created collection.
     * @return an unmodifiable collection that contains the elements in this
     * collection and <code>el</code>.
     */
    public abstract UnmodifiableCollection<E> addElement(E el);

    /**
     * Returns an unmodifiable collection that contains the elements of this
     * collection, plus all the elements in the specified collection.
     *
     * @param coll a collection of elements to add to the new created collection.  
     * @return an unmodifiable collection that contains the elements in this
     * collection and all elements of <code>coll</code>.  
     */
    public abstract UnmodifiableCollection<E> addElements(Collection<? extends E> coll);

    /**
     * Returns an unmodifiable collection that contains the elements of this
     * collection, minus the specified element.
     *
     * @param el the element to add to the new created collection.
     * @return an unmodifiable collection that contains all the elements in this
     * collection except <code>el</code>.
     */
    public abstract UnmodifiableCollection<E> removeElement(E el);

    /**
     * Returns an unmodifiable collection that contains all the elements of this
     * collection, minus all the elements in the specified collection.  
     *
     * @param coll a collection of elements to remove from the new created
     * collection.
     * @return an unmodifiable collection that contains all the elements in this
     * collection except all the elements of <code>coll</code>.
     */
    public abstract UnmodifiableCollection<E> removeElements(Collection<? extends E> coll);
}
