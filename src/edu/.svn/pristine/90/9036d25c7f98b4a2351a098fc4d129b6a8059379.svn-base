/**
 * 
 */
package edu.supercom.util.junctionTree;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author priscilla
 *
 */
public class Sepset extends Edge implements Comparable<Sepset> {
	
	private final Cluster Sxy;
	private final int mass;
	private final int cost;

	/**
	 * 
	 */
//	public Sepset() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	
	public Sepset(Cluster X, Cluster Y){
        super(X,Y,X.getWeight() + Y.getWeight());
        
        final Set<Node> intersection = new HashSet<Node>();
        intersection.addAll(X.getElements());
        intersection.retainAll(Y.getElements());
        
        Sxy = new Cluster(intersection);
		mass = size();
		cost = X.getWeight() + Y.getWeight();	
	}
	
	public final int size(){
		return Sxy.size();
	}
	
	public int compareTo(Sepset sep){
		if (mass == sep.mass){
			if (cost < sep.cost){
				return -1;
			}
			else if (cost==sep.cost){
				return 0;
			}
			else {
				return 1;
			}
		}
		else if (mass > sep.mass){
			return -1;
		}
		else {
			return 1;
		}
	}
	
	public void printSepset(){
            System.out.println("#Sepset#: " + Sxy.toString());
	}
    
    public Cluster n1() {
        return (Cluster)_n1;
    }
    
    public Cluster n2() {
        return (Cluster)_n2;
    }
}
