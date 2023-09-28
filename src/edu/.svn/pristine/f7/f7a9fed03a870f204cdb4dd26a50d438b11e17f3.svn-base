package edu.supercom.util.junctionTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

/**
 * This class describes a graph
 * It is based on Graph.java from the package edu.supercom.diag.auto (Author: A. Grastien);
 * A Graph consists of a set of nodes linked by edges
 * This abstract class allows for a generic definition of a "node"
 * What a node actually consists of can be specified in a child class
 * This abstract class allows for a generic definition of an "edge"
 * What an edge actually consists of can be specified in a child class
 * @author priscilla
 *
 */
public class Graph {

    /**
     * The list of nodes in the graph.
     */
    private final Set<Node> _nodes;
    
    /**
     * The list of edges in the graph.
     */
    private final Set<Edge> _edges;
    
    /**
     * The set of transitions in the graph.
     */
    private final Map<Node,Set<Edge>> _edgesMap;
    
    /** Creates a new instance of Graph */
    public Graph() {
        _nodes = new HashSet<Node>();
        _edges = new HashSet<Edge>();
        _edgesMap = new HashMap<Node, Set<Edge>>();
    }
    
    /**
     * Makes a copy of the specified graph.
     *
     * @param G the graph to copy.
     */
    public Graph(Graph g){
        _nodes = new HashSet<Node>();
        _edges = new HashSet<Edge>();
        _edgesMap = new HashMap<Node, Set<Edge>>();
        
    	for (final Node node: g._nodes) {
            newNode(node);
        }

        for (final Edge edge: g._edges) {
            newEdge(edge);
        }
    }
    
    public final void newNode(Node n) {
        _nodes.add(n);
        _edgesMap.put(n, new HashSet<Edge>());
    }
    
    public final void newEdge(Edge e) {
        final Node[] nodes = e.getNodes();
        
        if (isNeighbour(nodes[0], nodes[1])) { 
            return; // edge alreadty exists
        }
        
        _edges.add(e);
        for (int i=0 ; i<2 ; i++) {
            final Node n = nodes[i];
            _edgesMap.get(n).add(e);
        }
    }
    
    public Set<? extends Node> nodes() {
        return Collections.unmodifiableSet(_nodes);
    }
    
    public Iterator<Node> nodeIterator(){
    	return Collections.unmodifiableCollection(_nodes).iterator();
    }
    
    public Collection<Edge> getEdges(Node n){
    	return Collections.unmodifiableCollection(_edgesMap.get(n));
    }
    
    public Collection<Edge> getEdges(){
    	return Collections.unmodifiableCollection(_edges); 
    }
    
    /**
     * Returns a copy of this graph.
     *
     * @return a graph which is the exact copy of this graph.
     */
    @Override
    public Graph clone(){
    	return new Graph(this);
    }
    
    /**
     * Adds the specified node to the list of nodes in this graph.
     *
     * @param n the node to add.
     * @deprecated  use newNode
     */
    public void addNode(Node n){
        newNode(n);
    }
    
    /**
     * Adds the specified object as a node.  This method should be removed.
     *
     * @param o the object to add.
     * @deprecated use addNode(Node)
     */
    public void add(Object o){
        addNode((Node)o);
    }
    
    /**
     * Adds the nodes and edges from the specified graph in this graph.
     *
     * @param G the graph whose nodes and edges are added.
     */
    public void addGraph(Graph g){
        for (final Node n: g.nodes()) {
            newNode(n);
        }
        
        for (final Edge e: g.getEdges()) {
            newEdge(e);
        }
    }
    
    public void removeNode(Node n){
        final Collection<Edge> edges = _edgesMap.get(n);
        while (!edges.isEmpty()) {
            final Edge e = edges.iterator().next();
            removeEdge(e);
        }
        
        _nodes.remove(n);
        _edgesMap.remove(n);
    }
    
    /**
     * Removes the specified edge.
     *
     * @param e the edge to remove.
     */
    public void removeEdge(Edge e) {
        _edges.remove(e);
        
        final Node[] nodes = e.getNodes();
        for (int i=0 ; i<2 ; i++) {
            final Node n = nodes[i];
            _edgesMap.get(n).remove(e);
        }
    }

    /**
     * Adds the specified edge to the set of edges.
     *
     * @param e the edge to add.
     * @deprecated use newEdge()
     */
    public void addEdge(Edge e){
        newEdge(e);
    }
    
//    public void removeEdge(Edge e){
//    	Node[] narray = e.GetNodes(); //get the nodes associated with e
//    	Node n1 = narray[0];
//    	Node n2 = narray[1];
//    	Collection n1_edges = (Collection)_edgesMap.get(n1);
//    	n1_edges.remove(e);
//    	Collection n2_edges = (Collection)_edgesMap.get(n2);
//    	n2_edges.remove(e);   	
//    	_edges.remove(e);
//    }
    
    public Iterator<Edge> edgeIterator(){
		return getEdges().iterator();
	}
    
    /**
     * Returns the neighbours of a node n
     *
     * @param n the node we want the neighbours for
     * @return an array list of the neighbours, <code>null</code> if the node is 
     * not in the graph.
     */
    public List<? extends Node> getNeighbours(Node n){
    	final List<Node> neighbours = new ArrayList<Node>();
        
        for (final Edge edge: getEdges(n)) {
            final Node otherNode = edge.otherNode(n);
            neighbours.add(otherNode);
        }
        
    	return neighbours;
    }
    
    /**
     * Indicates whether the specified nodes are neighbours (i.e. whether this
     * graph contains an edge between the two specified nodes).
     *
     * @param n1 the first node.
     * @param n2 the second node.
     * @return <code>true</code> if <code>n1</code> and <code>n2</code> are 
     * neighbours.
     */
    public boolean isNeighbour(Node n1, Node n2){
        for (final Edge edge: getEdges(n1)) {
            final Node otherNode = edge.otherNode(n1);
            if (otherNode == n2) {
                return true;
            }
        }
    	
        return false;
    }
    
    /**
     * Returns a dot representation of this graph.
     *
     * @return a dot representation of this graph.
     */
    public String dotRepresentation(){
		final StringBuffer s= new StringBuffer("digraph G { \n");
		
		for (final Edge e: _edges){
			final Node[] narray = e.getNodes();
			final String n1 = narray[0].getName();
			final String n2 = narray[1].getName();
            final String line = "\"" + n1 + "\"" + "->" + "\"" + n2 + "\"" + " [arrowhead=none];";
			s.append(line).append("\n");
		}
		s.append("}");
                
        return s.toString();
    }

    /**
     * Saves a dot representation of this graph in the file at the specified 
     * location.
     *
     * @filename the location where the file must be stored.
     */
    public void dot(String filename){
        try {
            String s = this.dotRepresentation();
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(s);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Prints a description of this graph.
     */
    public void printDescription()
    {
        System.out.println("#Graph#: printDescription()");
        for (Iterator it = _nodes.iterator() ; it.hasNext() ; ) {
            Node node = (Node)it.next();
            System.out.println("#Graph#: node " + node + " -> " + getNeighbours(node));
        }
        System.out.println(_edges);
    }
}

