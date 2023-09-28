package util;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An inclusion ordering is a collection of sets 
 * that (partially) orders them according to inclusion.  
 * More formally, for all set <i>S</i> in the collection, 
 * <ul>
 * <li>for all element <i>e</i> in <i>S</i>, 
 * the singleton <i>{e}</i> is in the collection, </li>
 * <li>the empty set is forbidden in the collection,</li>
 * <li>for all set <i>S</i> in the collection, 
 * for all <i>S'</i> in the collection s.t. <i>S</i> is included in <i>S'</i>, 
 * if there is no <i>S''</i> in the collection 
 * s.t. <i>S</i> is included in <i>S''</i> 
 * and <i>S''</i> is included in <i>S'</i>, 
 * then <i>S</i> is a <b>direct child</b> of <i>S'</i> 
 * and <i>S'</i> is a <b>direct parent</b> of <i>S</i>.</li>
 * </ul>
 * Note that, by definition, all sets <i>S</i> in the collection 
 * are defined as the union of all their children.  
 * 
 * @author Alban Grastien
 * */
public class InclusionOrdering<E> {

	/**
	 * The set of sets in the collection.  
	 * */
	private final Set<Set<E>> _collection;
	
	/**
	 * The set of direct children associated with each set.  
	 * */
	private final Map<Set<E>, Set<Set<E>>> _children;
	
	/**
	 * The set of direct parents associated with each set.  
	 * */
	private final Map<Set<E>, Set<Set<E>>> _parents;
	
	/**
	 * Creates an empty inclusion ordering.  
	 * */
	public InclusionOrdering() {
		_collection = new HashSet<Set<E>>();
		_children = new HashMap<Set<E>, Set<Set<E>>>();
		_parents = new HashMap<Set<E>, Set<Set<E>>>();
	}

	/**
	 * Adds the specified set to this collection.  
	 * 
	 * @param set the set that is added to the collection.  
	 * @return <code>false</code> if <code>set</code> 
	 * was already part of this collection, 
	 * <code>true</code> otherwise.  
	 * @throws IllegalArgumentException if <code>set</code> is empty.  
	 * */
	public boolean addSet(Set<? extends E> set) {
//		System.out.println("Adding set " + set);
		
		if (_collection.contains(set)) {
			return false;
		}
		
		if (set.size() == 0) {
			throw new IllegalArgumentException("Cannot give an empty set.");
		}
		
		// We now work with a copy of s to make sure the set cannot be modified.  
		final Set<E> s = Collections.unmodifiableSet(new HashSet<E>(set));
		
		if (s.size() == 1) {
			_collection.add(s);
			_children.put(s, new HashSet<Set<E>>());
			_parents.put(s, new HashSet<Set<E>>());
			return true;
		}
		
		// The direct children of set
		final Set<Set<E>> children = new HashSet<Set<E>>();
		// The suspect direct children of set 
		// (we need to check whether they have parent that are children of set).  
		final Deque<Set<E>> suspectedChildren = new ArrayDeque<Set<E>>();
		// The set of suspects that are or have been suspected (kept to avoid resuspecting them).  
		final Set<Set<E>> studied = new HashSet<Set<E>>();
		for (final E e: s) {
			final Set<E> singleton = new HashSet<E>();
			singleton.add(e);
			if (addSet(singleton)) { // singleton is new and has no parent so far.
//				System.out.println("I know " + singleton + " is a direct child.");
				children.add(singleton);
			} else { // Already existed: suspect
//				System.out.println("Suspecting " + singleton);
				suspectedChildren.push(singleton);
				studied.add(singleton);
			}
		}
		
		while (!suspectedChildren.isEmpty()) {
			final Set<E> suspect = suspectedChildren.pop();
			boolean isDirect = true;
			// Look at all the direct parents of suspect
			for (final Set<E> parentOfSuspect: _parents.get(suspect)) {
				if (studied.contains(parentOfSuspect)) {
					continue;
				}
				if (isStrictlyIncluded(parentOfSuspect, s)) {
					isDirect = false;
					suspectedChildren.push(parentOfSuspect);
				}
			}
			if (isDirect) {
				children.add(suspect);
			}
		}
		
		// The direct parents of set.
		final Set<Set<E>> parents = new HashSet<Set<E>>();
		// The direct parents of set are parents of children and, 
		// so far, direct parents of them.  So we look at them.  
		final Set<Set<E>> rejectedParents = new HashSet<Set<E>>();
		for (final Set<E> child: children) {
			for (final Set<E> suspectParent: directParents(child)) {
				if (rejectedParents.contains(suspectParent)) {
					continue;
				}
				if (parents.contains(suspectParent)) {
					continue;
				}
				if (isStrictlyIncluded(s, suspectParent)) {
					parents.add(suspectParent);
				} else {
					rejectedParents.add(suspectParent);
				}
			}
		}
		
		// Redefine the parents / children.
		for (final Set<E> child: children) {
			_parents.get(child).removeAll(parents);
			_parents.get(child).add(s);
		}
		for (final Set<E> parent: parents) {
			_children.get(parent).removeAll(children);
			_children.get(parent).add(s);
		}
		_parents.put(s, parents);
		_children.put(s, children);
		_collection.add(s);
		
		return true;
	}
	
	/**
	 * Returns the set of direct parents of the specified set.  
	 * Technically, the method should return an object 
	 * of type <code>Set<Set<? extends E>></code>; having said that, 
	 * the inner set is unmodifiable which means the inner sets 
	 * are equivalent to <code>Set<E></code>.  
	 * 
	 * @param set the set whose direct parents need to be retrieved.  
	 * @return the set of direct parents of <code>set</code>.  
	 * @throws IllegalArgumentException if <code>set</code> is not in this collection.  
	 * */
	public Set<Set<E>> directParents(Set<? extends E> set) {
		final Set<Set<E>> result = _parents.get(set);
		if (result == null) {
			throw new IllegalArgumentException("The specified set is not in this collection");
		}
		return Collections.unmodifiableSet(result);
	}
	
	/**
	 * Returns the set of direct children of the specified set.  
	 * Technically, the method should return an object 
	 * of type <code>Set<Set<? extends E>></code>; having said that, 
	 * the inner set is unmodifiable which means the inner sets 
	 * are equivalent to <code>Set<E></code>.  
	 * 
	 * @param set the set whose direct children need to be retrieved.  
	 * @return the set of direct children of <code>set</code>.  
	 * @throws IllegalArgumentException if <code>set</code> is not in this collection.  
	 * */
	public Set<Set<E>> directChildren(Set<? extends E> set) {
		final Set<Set<E>> result = _children.get(set);
		if (result == null) {
			throw new IllegalArgumentException("The specified set is not in this collection");
		}
		return Collections.unmodifiableSet(result);
	}
	
	/**
	 * Tests whether the first specified set is strictly included 
	 * in the second specified set.  
	 * 
	 * @param set1 the first set.  
	 * @param set2 the second set.  
	 * @return <code>true</code> if all elements of <code>set1</code> are in <code>set2</code> 
	 * but <code>set1</code> and <code>set2</code> are not equal.  
	 * */
	public static <E> boolean isStrictlyIncluded(
			Set<? extends E> set1, Set<? extends E> set2) {
		if (set1.size() >= set2.size()) {
			return false;
		}
		
		for (final E e: set1) {
			if (!set2.contains(e)) {
				return false;
			}
		}
		
		return true;
	}
	
	public String toString() {
		final StringBuilder result = new StringBuilder();
		
		result.append(_collection).append("\n\n");
		
		for (final Set<E> set: _collection) {
			result.append(set).append("\n");
			result.append("children -> ").append(directChildren(set)).append("\n");
			result.append("parents -> ").append(directParents(set)).append("\n");
			result.append("\n");
		}
		
		return result.toString();
	}
	
	public String toDot() {
		final StringBuilder result = new StringBuilder();

		result.append("digraph G {\n");
		
		final Map<Set<? extends E>,Integer> nodes = new HashMap<Set<? extends E>, Integer>();
		{
			int i=0;
			for (final Set<? extends E> set: _collection) {
				nodes.put(set, i);
				result.append("  " + i + "  [label=\"" + set.toString() + "\"];\n");
				i++;
			}
		}
		result.append("\n");
		
		for (final Set<? extends E> set: _collection) {
			for (final Set<? extends E> parent: directParents(set)) {
				final int i1 = nodes.get(set);
				final int i2 = nodes.get(parent);
				result.append("  " + i1 + " -> " + i2 + ";\n");
			}
		}
		
		result.append("}");
		return result.toString();
	}

	// A test main.  
	public static void main(String [] args) {
		InclusionOrdering<Integer> io = new InclusionOrdering<Integer>();
		
		{
			final Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(2);
			set.add(3);
			io.addSet(set);
		}
		
		{
			final Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(4);
			set.add(5);
			io.addSet(set);
		}
		
		{
			final Set<Integer> set = new HashSet<Integer>();
			set.add(2);
			set.add(4);
			io.addSet(set);
		}
		
		{
			final Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(4);
			io.addSet(set);
		}
		
		{
			final Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(5);
			io.addSet(set);
		}
		
		{
			final Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(2);
			set.add(3);
			set.add(4);
			set.add(5);
			io.addSet(set);
		}
		
		{
			final Set<Integer> set = new HashSet<Integer>();
			set.add(1);
			set.add(2);
			set.add(3);
			set.add(4);
			io.addSet(set);
		}
		
		System.out.println(io.toDot());
	}
}
