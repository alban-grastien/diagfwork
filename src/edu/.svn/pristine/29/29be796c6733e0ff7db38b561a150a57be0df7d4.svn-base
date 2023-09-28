/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * An unmodifiable set is a unmodifiable collection that has the properties of
 * a set.  The purpose of this class is to avoid copies of sets that cannot be
 * modified.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @param <E> The type of elements in the set.
 */
public abstract class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E> {

    @Override
    public abstract UnmodifiableSet<E> addElement(E el);

    @Override
    public abstract UnmodifiableSet<E> removeElement(E el);

    @Override
    public abstract UnmodifiableSet<E> addElements(Collection<? extends E> coll);

    @Override
    public abstract UnmodifiableSet<E> removeElements(Collection<? extends E> coll);

    @Override
    public int hashCode() {
        int result = 0;
        for (final E e: this) {
            result += e.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Set<?>) {
            final Set<?> set = (Set<?>)obj;
            if (size() != set.size()) {
                return false;
            }
            for (final Object o: set) {
                if (!contains(o)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
    
    /**
     * Builds an unmodifiable set defined as the union of the two specified sets.  
     * This method is quite useful to build a union without enumerating all its elements.  
     * Be careful however as it is not very effective: 
     * one should not define a sequence of unions.  
     * 
     * @param set1 a first set.  
     * @param set2 a second set.  
     * @return an unmodifiable set that includes all elements 
     * of <code>set1</code> and <code>set2</code>.  
     * */
    public static <EE> UnmodifiableSet<EE> union(final UnmodifiableSet<EE> set1, 
    		final UnmodifiableSet<EE> set2) {
    	return new UnmodifiableSet<EE>() {

			@Override
			public UnmodifiableSet<EE> addElement(EE el) {
				return set1.addElement(el).addElements(set2);
			}

			@Override
			public UnmodifiableSet<EE> addElements(
					Collection<? extends EE> coll) {
				return set1.addElements(coll).addElements(set2);
			}

			@Override
			public UnmodifiableSet<EE> removeElement(EE el) {
				return set1.addElements(set2).removeElement(el);
			}

			@Override
			public UnmodifiableSet<EE> removeElements(
					Collection<? extends EE> coll) {
				return set1.addElements(set2).removeElements(coll);
			}

			@Override
			public Iterator<EE> iterator() {
				return new Iterator<EE>() {

					Iterator<EE> _it;
					
					EE _next;
					
					boolean _first;
					
					boolean _hasNext;
					
					private void goToNext() {
						if (!_it.hasNext()) {
							if (_first) {
								_first = false;
								_it = set2.iterator();
							} else {
								_hasNext = false;
								_next = null;
								_it = null;
								return;
							}
						}

						if (_first) {
							_next = _it.next();
						} else {
							while (_it.hasNext()) {
								EE next = _it.next();
								if (set1.contains(next)) {
									continue;
								}
								_next = next;
								return;
							}
							_next = null;
							return;
						}
					}
					
					public Iterator<EE> init() {
						_hasNext = true;
						_first = true;
						_it = set1.iterator();
						
						goToNext();
						
						return this;
					}
					
					@Override
					public boolean hasNext() {
						return _hasNext;
					}

					@Override
					public EE next() {
						if (hasNext()) {
							final EE result = _next;
							goToNext();
							return result;
						} 
						
						throw new IllegalStateException("Cannot call next: there is no next.");
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("Unmodifiable");
					}
				}.init();
			}

			@SuppressWarnings("unused") // ee never used, it's ok.
			@Override
			public int size() {
				int result = 0;
				for (final EE ee: this) {
					result++;
				}
				return result;
			}
			
			@Override
			public boolean contains(Object e) {
				return set1.contains(e) || set2.contains(e);
			}
    		
    	};
    }
}
