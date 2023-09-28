package edu.supercom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An implicit unmodifiable list is an unmodifiable list that is not implicitly constructed.  
 * When elements are removed from the list, the list is explicitly constructed.  
 * */
public abstract class ImplicitUnmodifiableList<E> extends UnmodifiableList<E> {

	/**
	 * Returns an unmodifiable list constructor 
	 * that contains an explicit representation of this list.  
	 * Because the result is a constructor, elements may be added 
	 * or removed from the list being constructed.  
	 * 
	 * @return an explicit representation of this list 
	 * in the form of a list constructor.  
	 * */
	public abstract UnmodifiableListConstructor<E> makeExplicit();
	
	/**
	 * Returns the type of unmodifiable list that should be used for the list constructor 
	 * when the explicit representation is used.  
	 * This is useful in case {@link #makeExplicit()} is called externally.  
	 * 
	 * @return the class of unmodifiable list.  
	 * */
	@SuppressWarnings("unchecked")
	public Class<? extends List<E>> getListClass() {
	  final Class<ArrayList> cl1 = ArrayList.class;
	  final Class<? extends List> cl2 = cl1;
	  final Class<? extends List<?>> cl3 = (Class<? extends List<?>>)cl2;
	  final Class<? extends List<E>> cl4 = (Class<? extends List<E>>)cl3;
	  return cl4;
	  // return (Class<? extends List<E>>)ArrayList.class;
	}
	
	@Override
    public UnmodifiableList<E> addElements(Collection<? extends E> coll) {
		final UnmodifiableListConstructor<E> con = makeExplicit();
		for (final E el: coll) {
			con.addElement(el);
		}
		return con.getList();
	}
	
	@Override
    public UnmodifiableList<E> addElement(E el) {
		final UnmodifiableListConstructor<E> con = makeExplicit();
		con.addElement(el);
		return con.getList();
	}
	
	@Override
    public UnmodifiableList<E> removeElement(E el) {
		final UnmodifiableListConstructor<E> con = makeExplicit();
		con.removeElement(el);
		return con.getList();
	}

	@Override
    public UnmodifiableList<E> removeElements(Collection<? extends E> coll) {
		final UnmodifiableListConstructor<E> con = makeExplicit();
		for (final E el: coll) {
			con.removeElement(el);
		}
		return con.getList();
	}
}
