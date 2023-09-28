/**
 * 
 */
package edu.supercom.util.junctionTree;

import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;
//import java.util.Set;

/**
 * @author priscilla
 *This class implements a cluster
 *A cluster is a set of nodes
 */
public class Cluster extends Node implements Iterable<Node> {

	private final UnmodifiableList<Node> _nodes;
	
	/*
	 * Creates a cluster of nodes 
	 * @param ns List of nodes to create cluster from
	 */
	public Cluster(Collection<? extends Node> ns){
        super(ns.toString());
        
        final UnmodifiableListConstructor<Node> con = new UnmodifiableListConstructor<Node>();
        for (final Node n: ns) {
            con.addElement(n);
        }
        _nodes = con.getList();
	}
	
	public boolean contains(Node o){
		return _nodes.contains(o);
	}
	
	public boolean containsAll(Cluster c){
        for (final Node n: c._nodes) {
            if (!_nodes.contains(n)) {
                return false;
            }
        }
        
        return true;
	}
	
	public List<Node> getElements(){
		return _nodes;
	}
	
    @Override
	public Iterator<Node> iterator(){
		return _nodes.iterator();
	}
	
	public int size(){
		return _nodes.size();
	}
	
    @Override
	public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (!(obj instanceof Cluster)) {
            return false;
        }
        
        final Cluster c = (Cluster)obj;
		return _nodes.equals(c._nodes);
	}
	
    @Override
	public int hashCode(){
		return _nodes.hashCode();
	}
	
	public void printCluster(){
        System.out.println(this);
	}
	
    @Override
    public String toString() {
        return _nodes.toString();
    }
}
