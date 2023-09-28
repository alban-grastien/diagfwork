package lang;

import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

	/**
	 * Returns the list of paths that match the specified skeleton.  
	 * A skeleton is defined by two components defining the start component
	 * and the end component of the path, a component type which defines the 
	 * type of the components on the path, and a connection type which 
	 * specifies the type of connections between the components on the path.  
	 * 
	 * @param syscomps the collection of components in the system.  
	 * @param orig the component at the beginning of the path.  
	 * @param targ the component at the end of the path.  
	 * @param connt the type of the connections between the components.  
	 */
	public static Collection<Path> getPaths(Collection<YAMLDComponent> syscomps, 
			YAMLDComponent orig, YAMLDComponent targ, YAMLDConnType connt) {
		// The collection that contains the path being built.  
		final Deque<YAMLDComponent> path = new ArrayDeque<YAMLDComponent>();
		// The set of components in the path (to check loops).
		final Set<YAMLDComponent> comps = new HashSet<YAMLDComponent>();
		
		path.add(targ);
		comps.add(targ);
		
		final Collection<Path> result = new ArrayList<Path>();
		//recGetPaths(syscomps, path, comps, targ, connt, result);
		reversedRecGetPaths(path, comps, orig, connt, result);
		
		return result;
	}
	
//	private static void recGetPaths(Collection<YAMLDComponent> syscomps,
//			Deque<YAMLDComponent> currentPath, Set<YAMLDComponent> comps,
//			YAMLDComponent targ, YAMLDConnType connt, Collection<Path> paths) {
//		
//		final YAMLDComponent topComp = currentPath.peek();
//		
//		for (final YAMLDComponent comp: syscomps) { // Try to add the component comp to the path
//			// Right now, we check for all comps if it is connected to the last comp of the path
//			// It is inefficient: the list of comps connected to a given comp 
//			// should be stored somewhere.  
//			
//			// Is comp connected to syscomps through a connection of type connt?
//			{
//				boolean connected = false;
//				for (final YAMLDConnection conn: comp.conns()) {
//					if (conn.target().equals(topComp.name()) 
//							&& conn.type().equals(connt)) {
//						connected = true;
//						break;
//					}
//				}
//				if (!connected) {
//					continue;
//				}
//			}
//			
//			// Is comp already in the path?
//			if (comps.contains(comp)) {
//				continue;
//			}
//			
//			// Adding the component
//			currentPath.push(comp);
//			comps.add(comp);
//			
//			// Is it the end of the path?
//			if (comp.equals(targ)) {
//				paths.add(new ArrayPath(currentPath));
//			} else {
//				recGetPaths(syscomps, currentPath, comps, targ, connt, paths);
//			}
//			
//			// Removing the component
//			currentPath.pop();
//			comps.remove(comp);
//		}
//		
//	}
	
	/**
	 * @param currentPath the current path starting from the end.  
	 * @param pathComps the set of components in <code>currentPath</code>.  
	 * @param orig the component the path should start from.  
	 * @param connt the type of connections we are considering.  
	 * @param paths the collection that stores the paths.  
	 * */
	private static void reversedRecGetPaths(
			Deque<YAMLDComponent> currentPath, Set<YAMLDComponent> pathComps,
			YAMLDComponent orig, YAMLDConnType connt, Collection<Path> paths) {
		/*System.out.println("Util >> ");
		{
			final StringBuilder buf = new StringBuilder();
			buf.append("Current >> ");
			buf.append("?");
			for (final YAMLDComponent comp: currentPath) {
				buf.append(" -> ");
				buf.append(comp.name());
			}
			System.out.println(buf);
		}*/
		
		final YAMLDComponent topComp = currentPath.peek();
		if (topComp == orig) {
			final List<YAMLDComponent> actualPath = 
				new ReversedList<YAMLDComponent>(
						new ArrayList<YAMLDComponent>(currentPath));
			final Path p = new ArrayPath(actualPath);
			paths.add(p);
			return;
		}
		
		for (final YAMLDConnection con: topComp.conns()) {
			if (!con.type().equals(connt)) {
				continue;
			}
			final YAMLDComponent comp = con.target();
			if (pathComps.contains(comp)) {
				continue;
			}
			
			currentPath.push(comp);
			pathComps.add(comp);
			
			reversedRecGetPaths(currentPath, pathComps, orig, connt, paths);
			
			currentPath.pop();
			pathComps.remove(comp);
		}
	}
}

/**
 * A list that is the reversed of a specified list.  
 * */
class ReversedList<X> extends AbstractList<X> {
	
	/**
	 * The list that is reversed.  
	 * */
	private final List<X> _list;
	
	/**
	 * Builds a list that is the reverse of the specified list.  
	 * Any modification to the specified list will also modify 
	 * the reversed list.  The reversed list cannot be modified 
	 * directly.  
	 * 
	 * @param l the list that is reversed.  
	 * */
	public ReversedList(List<X> l) {
		_list = l;
	}

	@Override
	public X get(int index) {
		return _list.get(_list.size() - index -1);
	}

	@Override
	public int size() {
		return _list.size();
	}
	
}
