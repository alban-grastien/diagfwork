package lang;

import java.util.Iterator;

/**
 * This class implements methods associated with {@link Path} that are 
 * independent from the actual implementation of a path.  It would be 
 * useful that any implementation of a path extends this class, if possible.
 * 
 * @author Alban Grastien
 * @version 1.0
 * */
public abstract class AbstractPath implements Path {

	/**
	 * The hashcode of a path is defined as the sum of the hashcodes 
	 * of all its components.  
	 * 
	 * @return a hashcode for this path.  
	 * */
	@Override 
	public int hashCode() {
		int result = 0;
		for (final YAMLDComponent comp: this) {
			result += comp.hashCode();
		}
		return result;
	}
	
	/**
	 * Tests the equality between this path and the specified object.  
	 * For the equality to be true, the specified object should be a 
	 * path with the same sequence of components.  
	 * 
	 * @param obj the object to compare to this path.  
	 * @return <code>true</code> if <code>obj</code> <i>equals</i> 
	 * <code>this</code>.
	 * */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof Path) {
			final Path p = (Path)obj;
			
			if (this.size() != p.size()) {
				return false;
			}
			
			Iterator<YAMLDComponent> it1 = this.iterator();
			Iterator<YAMLDComponent> it2 = p.iterator();
			while (it1.hasNext()) {
				final YAMLDComponent c1 = it1.next();
				final YAMLDComponent c2 = it2.next();
				if (!c1.equals(c2)) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
}
