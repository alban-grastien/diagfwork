/*
 * Equivalence.java
 *
 * Created on 21 March 2007, 11:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Models an equivalence relation between elements.  Given a set of objects, the 
 * equivalence relation is a partition of the set so that two elements <i>a</i> 
 * and <i>b</i> are in the same subset iff they are equivalent (<i>a equiv 
 * b</i>).  An equivalence relation has the following properties:
 * <ul>
 * <li>for any element <i>a</i>, <i>a equiv a</i>;<li>
 * <li>for any pair of elements <i>a</i> and <i>b</i>, <i>(a equiv b)</i> 
 * implies <i>(b equiv a)</i> (symmetry);</li>
 * <li>for any triplet <i>a</i>, <i>b</i> and <i>c</i>, <i>(a equiv b)</i> and 
 * <i>(b equiv c)</i> imply <i>(a equiv c)</i> (transitivity).</li>
 * </ul>
 * This implementation is not complete.<p/>
 *
 * This class implements the {@link java.util.Set} interface.  However, if 
 * somebody wants an implementation of a set and is not interested into the 
 * equivalence function, this class should not be used as it is less efficient 
 * than other specified implementations.
 *
 * @author Alban Grastien
 * @version 1.0.1
 * @since 1.0.1
 * @param E the type of events in the equivalence class.  
 */
public class Equivalence<E> implements java.util.Set<E> {

    /**
     * The partition (a set of equivalence classes).
     */
    private Set<EquivalenceClass<E>> _partition;
    /**
     * A mapping that associates each element of the set to its class of 
     * equivalence.
     */
    private Map<E,EquivalenceClass<E>> _objToSet;

    /**
     * Creates an empty equivalence class.
     */
    public Equivalence() {
        _partition = new HashSet<EquivalenceClass<E>>();
    	//_partition = new TreeSet<EquivalenceClass<E>>();
        _objToSet = new HashMap<E, EquivalenceClass<E>>();
    }

    /**
     * Adds an element to the set.  This element is equivalent to no another 
     * element.  If the element is already in the set, nothing is done.
     *
     * @param e the element to add to the set.
     */
    @Override
    public boolean add(E e) {
        if (_objToSet.containsKey(e)) {
            return false;
        }
        final EquivalenceClass<E> classe = new EquivalenceClass<E>(e);
        _partition.add(classe);
        _objToSet.put(e, classe);
        return true;
    }

    /**
     * Adds a new equivalence relation between two elements.  If those two 
     * elements are already equivalent, nothing is done.  The transitivity is 
     * automatically performed.
     * 
     * @param e1 the first element.
     * @param e2 the second element.
     * @see #add(Object)
     * @throws java.util.NoSuchElementException if <code>e1</code> or 
     * <code>e2</code> are not in the set.
     */
    public void equivalent(E e1, E e2) {
        final EquivalenceClass<E> equiv1 = _objToSet.get(e1);
        if (equiv1 == null) {
            throw new java.util.NoSuchElementException("This object is not in the set " + e1);
        }
        final EquivalenceClass<E> equiv2 = _objToSet.get(e2);
        if (equiv2 == null) {
            throw new java.util.NoSuchElementException("This object is not in the set " + e2);
        }

        if (equiv1 == equiv2) {
            return;
        }

        final EquivalenceClass<E> eq1, eq2; // eq1 is the largest
        if (equiv1.size() > equiv2.size()) {
            eq1 = equiv1;
            eq2 = equiv2;
        } else {
            eq1 = equiv2;
            eq2 = equiv1;
        }

        _partition.remove(eq2);
        eq1.addAll(eq2.iterator());
        for (final E obj: eq2) {
            _objToSet.put(obj, eq1);
        }
        eq2.clear(); // To simplify the work of the Garbage Collector.
    }

    /**
     * Merges the equivalent classes of the two specified elements.  If one 
     * element is not in the set, the equivalent class of the 
     * second is removed from the set (this is the difference with method {@link
     * #equivalent(Object,Object)}.
     *
     * @param e1 the first element.
     * @param e2 the second element.
     */
    public void merge(E e1, E e2) {
        final EquivalenceClass<E> equiv1 = _objToSet.get(e1);
        final EquivalenceClass<E> equiv2 = _objToSet.get(e2);

        if ((equiv1 == null) && (equiv2 == null)) {
            return;
        }

        if ((equiv1 == null) || (equiv2 == null)) {
            final EquivalenceClass<E> toremove;
            if (equiv1 == null) {
                toremove = equiv2;
            } else {
                toremove = equiv1;
            }
            removeAll(new HashSet<E>(toremove.toSet()));
            return;
        }

        equivalent(e1, e2);
    }

    /**
     * Returns an iterator on the elements of this set.  Not than the {@link 
     * java.util.Iterator#remove()} operation is not supported by this iterator.
     *
     * @return an iterator on the elements of this set.
     */
    @Override
    public Iterator<E> iterator() {
        return Collections.unmodifiableCollection(_objToSet.keySet()).iterator();
    }

    /**
     * Returns an iterator on the equivalent classes.  Each element returned by
     * the {@link java.util.Iterator#next()} method on the iterator is a
     * EquivalenceClass.
     *
     * @return an iterator of EquivalenceClass.
     * @deprecated Use {@link #classes() } instead.  
     */
    public Iterator<EquivalenceClass<E>> classIterator() {
        return Collections.unmodifiableCollection(_partition).iterator();
    }

    /**
     * Returns the list of equivalent classes.  
     *
     * @return the list of EquivalenceClass.
     */
    public Set<EquivalenceClass<E>> classes() {
        return Collections.unmodifiableSet(_partition);
    }

    /**
     * Returns a copy of the equivalence class of the specified element.
     *
     * @param e the element whose equivalence class is required.
     * @return a copy of the equivalence class of the specified element, 
     * <code>null</code> if <code>!contains(e)</code>.
     */
    public EquivalenceClass<E> equivalenceClass(E e) {
        return _objToSet.get(e);
    }

    /**
     * Removes an element of the set.  Note that, given a set <i>{a,b}</i>, if 
     * one do the following actions:
     * <ul>
     * <li><code>add(c);</code></li>
     * <li><code>equivalent(a,c);</code></li>
     * <li><code>equivalent(b,c);</code></li>
     * <li><code>remove(c);</code></li>
     * </ul>
     * then, <i>a</i> and <i>b</i> are considered equivalent.
     * 
     * @param e the element to remove from the set.
     * @return <code>true</code> if the element was removed, i.e. if the element 
     * was in this set.
     */
    @Override
    public boolean remove(Object e) {
        final EquivalenceClass<E> equiv = _objToSet.get(e);
        if (equiv == null) {
            return false;
        }
        equiv.remove(e);
        if (equiv.isEmpty()) {
            _partition.remove(equiv);
        }
        _objToSet.remove(e);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> coll) {
        boolean result = false;
        for (final E e: coll) {
            boolean local = add(e);
            result = result || local;
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (final Object e: c) {
            boolean local = remove(e);
            result = result || local;
        }
        return result;
    }

    /**
     * Removes all the elements from the specified equivalence class.
     *
     * @param eq the equivalence class.
     * @return <code>true</code> if the set is modified.
     */
    public boolean removeAll(EquivalenceClass<E> eq) {
        if (eq == null) {
            return false;
        }
        // Makes a copy of the class
        final Set<E> copy = new HashSet<E>();
        for (E e: eq) {
            copy.add(e);
        }
        return removeAll(copy);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (final Object o: c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        _partition.clear();
        _objToSet.clear();
    }

    @Override
    public boolean contains(Object e) {
        return _objToSet.containsKey(e);
    }

    @Override
    public int hashCode() {
        int result = 19;
        for (final E e : this) {
            result += e.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Equivalence<?>) {
            Equivalence<?> eq = (Equivalence<?>)o;
            return _objToSet.equals(eq);
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return _objToSet.isEmpty();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        final Collection<E> toremove = new ArrayList<E>();
        for (final E obj: this) {
            if (!c.contains(obj)) {
                toremove.add(obj);
            }
        }
        return removeAll(toremove);
    }

    @Override
    public int size() {
        return _objToSet.size();
    }

    @Override
    public Object[] toArray() {
        return _objToSet.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return _objToSet.keySet().toArray(a);
    }

    /**
     * Returns a string representation of this equivalence. The string 
     * representation consists of the list of equivalent classes in the order 
     * they are returned by <code>classIterator()</code>, enclosed in square 
     * brackets ("[]"). Adjacent classes are separated by the characters ", " 
     * (comma and space).  The representation of each class consists of the list 
     * of elements in the class in the order the <code>iterator()</code> method 
     * returns them, enclosed in square brackets ("[]"). Adjacent elements are 
     * separated by the characters ", " (comma and space).
     *
     * @return a string representation of this equivalence.
     */
    @Override
    public String toString() {
        return _partition.toString();
    }
}