package edu.supercom.util;

import java.util.ListIterator;

public abstract class UnmodifiableListIterator<E> implements ListIterator<E> {

	@Override
	public final void add(E e) {
		throw new UnsupportedOperationException("Unmodifiable List Iterator");
	}
	
	@Override
	public final void remove() {
		throw new UnsupportedOperationException("Unmodifiable List Iterator");
	}

	@Override
	public final void set(E e) {
		throw new UnsupportedOperationException("Unmodifiable List Iterator");
	}
}
