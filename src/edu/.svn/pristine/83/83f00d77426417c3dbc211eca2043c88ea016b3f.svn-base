package edu.supercom.util;

import java.util.Iterator;

/**
 * An unmodifiable iterator is an iterator where the method {@link #remove()} cannot be called.  
 * 
 * @author Alban Grastien
 * */
public abstract class UnmodifiableIterator<E> implements Iterator<E> {

	@Override
	public final void remove() {
		throw new IllegalArgumentException("Unmodifiable Iterator");
	}
}
