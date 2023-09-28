package diag.reiter.hypos;

import edu.supercom.util.ImplicitUnmodifiableList;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;

/**
 * Useful static methods.  
 * */
public class Util {

	/**
	 * Concatenates two unmodifiable lists.  
	 * 
	 * @param l1 the first list.  
	 * @param l2 the second list.  
	 * @return the concatenation of both lists.  
	 * */
	public static <E> UnmodifiableList<E> concat(final UnmodifiableList<? extends E> l1, 
			final UnmodifiableList<? extends E> l2) {
		return new ImplicitUnmodifiableList<E>() {

			public UnmodifiableListConstructor<E> makeExplicit() {
				final UnmodifiableListConstructor<E> con = 
					new UnmodifiableListConstructor<E>(l1);
				for (final E e: l2) {
					con.addElement(e);
				}
				return con;
			}

			@Override
			public int size() {
				return l1.size() + l2.size();
			}

			@Override
			public E get(int index) {
				final int size1 = l1.size();
				if (index < size1) {
					return l1.get(index);
				}
				
				return l2.get(index-size1);
			}
		};
	}
	
	/**
	 * Returns a list of integer where each number of the specified list is inverted, 
	 * i.e., the value <code>x</code> is replaced by <x>-1</x>.  
	 * 
	 * @param l a list of integers.  
	 * @return a list of inverted integers.  
	 * */
	public static UnmodifiableList<Integer> invert(final UnmodifiableList<Integer> l) {
		return new ImplicitUnmodifiableList<Integer>() {

			public UnmodifiableListConstructor<Integer> makeExplicit() {
				final UnmodifiableListConstructor<Integer> con =
					new UnmodifiableListConstructor<Integer>();
				for (final int i: l) {
					con.addElement(-i);
				}
				return con;
			}

			@Override
			public int size() {
				return l.size();
			}

			@Override
			public Integer get(int index) {
				return -l.get(index);
			}
		};
	}
	
}
