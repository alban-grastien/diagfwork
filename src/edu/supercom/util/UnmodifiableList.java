/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * An unmodifiable set is a unmodifiable collection that has the properties of
 * a list.  The purpose of this class is to avoid copies of lists that cannot be
 * modified.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <E> The type of elements in the list.
 */
public abstract class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {

	@Override
	public final void add(int index, E element) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
	}

	@Override
	public final boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
	}

	@Override
	public final E remove(int index) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
	}

	@Override
	public final E set(int index, E element) {
        throw new UnsupportedOperationException("Unmodifiable collection.");
	}
	
	@Override
    public abstract UnmodifiableList<E> addElements(Collection<? extends E> coll);
	
	@Override
    public abstract UnmodifiableList<E> addElement(E el);
	
	@Override
    public abstract UnmodifiableList<E> removeElement(E el);

	@Override
    public abstract UnmodifiableList<E> removeElements(Collection<? extends E> coll);
	
	@Override 
	public Iterator<E> iterator() {
		return new UnmodifiableIterator<E>() {
			private int _pos = 0;
			
			@Override
			public boolean hasNext() {
				return _pos != size();
			}

			@Override
			public E next() {
				final E result = get(_pos);
				_pos++;
				return result;
			}
		};
	}
	
	@Override
	public UnmodifiableList<E> subList(int fromIndex, int toIndex) {
		return new UnmodifiableSubList<E>(this, fromIndex, toIndex);
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}
	
	@Override
	public ListIterator<E> listIterator(int index) {
		return new ULI<E>(this, index);
	}

	@Override
	public int indexOf(Object o) {
		final int size = size();
		for (int i=0 ; i<size ; i++) {
			if (get(i).equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		for (int i=size()-1 ; i>=0 ; i--) {
			if (get(i).equals(o)) {
				return i;
			}
		}
		return -1;
	}
    
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator e2 = ((List) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }
}

class UnmodifiableSubList<E> extends ImplicitUnmodifiableList<E> {

	final int _min; 
	
	final int _max;
	
	final UnmodifiableList<E> _list;
	
	public UnmodifiableSubList(UnmodifiableList<E> list, int min, int max) {
		_min = min;
		_max = max;
		_list = list;
	}
	
	@Override
	public UnmodifiableListConstructor<E> makeExplicit() {
		final Class<? extends List<E>> cl = getListClass();
		UnmodifiableListConstructor<E> con; 
		try {
			con = new UnmodifiableListConstructor<E>(cl);
		} catch (InstantiationException e) {
			con = new UnmodifiableListConstructor<E>();
		} catch (IllegalAccessException e) {
			con = new UnmodifiableListConstructor<E>();
		}
		
		for (int i=_min ; i<_max ; i++) {
			con.addElement(_list.get(i));
		}
		return con;
	}

	@Override
	public int size() {
		return _max - _min;
	}

	@Override
	public E get(int index) {
		return _list.get(index + _min);
	}
	
}

class ULI<E> implements ListIterator<E> {

	private final UnmodifiableList<E> _list;
	
	private int _cursor;
	
	public ULI(UnmodifiableList<E> list, int cursor) {
		_list = list;
		_cursor = cursor;
	}
	
	@Override
	public void add(E e) {
		throw new UnsupportedOperationException("Unmodifiable List");
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Unmodifiable List");
	}

	@Override
	public void set(E e) {
		throw new UnsupportedOperationException("Unmodifiable List");
	}

	@Override
	public boolean hasNext() {
		return _cursor < _list.size();
	}

	@Override
	public boolean hasPrevious() {
		return _cursor > 0;
	}

	@Override
	public E next() {
		final E next = _list.get(_cursor);
		_cursor++;
		return next;
	}

	@Override
	public int nextIndex() {
		return _cursor;
	}

	@Override
	public E previous() {
		_cursor--;
		return _list.get(_cursor);
	}

	@Override
	public int previousIndex() {
		return _cursor-1;
	}
	
}