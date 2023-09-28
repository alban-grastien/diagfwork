/**
 * 
 */
package edu.supercom.util.junctionTree.util;

import edu.supercom.util.junctionTree.Cluster;
import edu.supercom.util.junctionTree.Edge;
import edu.supercom.util.junctionTree.Graph;
import edu.supercom.util.junctionTree.Node;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * This class implements a binary heap
 * for our purpose we use min-ordering, ie smallest item at highest level
 * @author priscilla
 *
 */
public class BinaryHeap {

	private final List<Node> _heap;
	private final NodeComparator _nodecomp;
	
	/*
	 * graph whose nodes we want to keep in the heap
	 */
	private final Graph _graph;
	
	
	/**
	 * Constructor method
	 */
//	public BinaryHeap()
//	{
//		_heap = new ArrayList<Node>();
//	}
	
//	public BinaryHeap(List<Node> _li){
//		_heap = new ArrayList<Node>();
//		int s = _li.size();
//		for (int i=0; i<s; i++){
//			_heap.add(_li.get(i));
//		}
//		heapify();
//	}
	
	public BinaryHeap(Graph g){
		_heap = new ArrayList<Node>();
		_graph = g.clone();
		_nodecomp = new NodeComparator(_graph);
		
        final Set<? extends Node> nodes = g.nodes();
		for (final Node node: nodes){
			_heap.add(node);
		}
		heapify();
		Collections.sort(_heap, _nodecomp);
	}
	
	public int size(){
		return _heap.size();
	}
	/*
	 * Reorganise the Heap (if necessary)
	 */
	private void heapify(){
		final int halfSize = (_heap.size()+1)/2-1;
		for (int i=halfSize;i>=0;i--){
			percolateDown(i);
		}
	}
	
	/*
	 * pop operation to get smallest item out of the heap
	 * @param smallest_node, the node which adds the min #edges and produces the smallest cluster weight
	 */
    public Cluster pop(){
		final Node smallest = _heap.get(0);
		final List<Node> neighbours = new ArrayList<Node>(_graph.getNeighbours(smallest));
        neighbours.add(smallest);
        final Cluster clus = new Cluster(neighbours);

        if (_heap.size()==1){
            _heap.remove(0);
		} else {
			updateNeighbourReferences(smallest);
			_heap.remove(0);
			Collections.sort(_heap,_nodecomp);
		}
        
		return clus;
	}
	
	/*
	 * private routine used in pop
	 */
	private void updateNeighbourReferences(Node n){
		
		//add edges if necessary
		//in the case that the node to popped 
		final List<? extends Node> neighbours = _graph.getNeighbours(n);
		final int nb_size = neighbours.size(); //done outside loop for opt
		final int minus_one = nb_size-1; //for opt
		for (int i=0;i<minus_one;i++){
			final Node nb_i = (Node)neighbours.get(i);
			for (int j=i+1;j<nb_size;j++){
				final Node next_nb = (Node)neighbours.get(j);
				if (!(_graph.isNeighbour(nb_i,next_nb))){
					_graph.newEdge(new Edge(nb_i,next_nb,1));
				}
			}
		}
		
		//remove edges associated with n
		final Collection<Edge> nE = new ArrayList<Edge>(_graph.getEdges(n));
        for (final Edge edge: nE) {
            _graph.removeEdge(edge);
        }
	}
	
	/*
	 * function to get the primary key of node for sorting in heap
	 * Primary key is given by the number of edges that would be added to the graph if Node n is chosen
	 * @param Node n the chosen node in the graph
	 */
	private int getPrimaryKey(Node n){
		
		int key=0;
		final List<? extends Node> neighbours = _graph.getNeighbours(n);
		int nb_size = neighbours.size();
		int minus_one = nb_size-1; //for opt
		for (int i=0;i<minus_one;i++){
			Node nb_i = neighbours.get(i);
			for (int j=i+1;j<nb_size;j++){
				Node next_nb = (Node)neighbours.get(j);
				if (!(_graph.isNeighbour(nb_i,next_nb))){
					key++;
				}
			}
		}
        
		return key;
	}
	
	/*
	 * function to get the secondary key of node for sorting in heap
	 * Secondary key is given by the weight of induced cluster if Node n is chosen
	 * @param Node n the chosen node in the graph
	 */
	private int getSecondaryKey(Node n){
		
		int cluster_weight=n.getWeight();
        for (final Node nb: _graph.getNeighbours(n)) {
			cluster_weight = cluster_weight*nb.getWeight();
		}
		
		return cluster_weight;
	}
	
	/*
	 * Function to restore the heap property after an item is popped
	 */
	private void percolateDown(int i){
		int leftChild = 2*i+1;
		
		if (leftChild>_heap.size()){
			final Node node_i = _heap.get(i); //node at position i in the heap
			int ati_prim = getPrimaryKey(node_i); //primary key for node at i
			int ati_sec = getSecondaryKey(node_i); //secondary key for node at i
			
			int rightChild = leftChild +1;
			Node node_left = _heap.get(leftChild); //left child node
			//Node node_right = (Node)_heap.elementAt(rightChild); //right child node
			int atLeft_prim = getPrimaryKey(node_left);
			int atLeft_sec = getSecondaryKey(node_left);
			
			int atRight_prim = getPrimaryKey(node_left);
			//int atRight_sec = GetSecondaryKey(node_left);
						
			if (rightChild == _heap.size()){
				//if the right child is the last node, then we only need to compare with the left child
				

				//sort of primary key first
				if (ati_prim > atLeft_prim){
					swap(node_i,node_left);
				}
				//if primary keys are the same, then sort on secondary key
				if ((ati_prim == atLeft_prim) && (ati_sec > atLeft_sec) ){
					swap(node_i,node_left);
				}
				
			}
			else {
				compareAndSwap(i,leftChild,rightChild,"primary");
				//if primary keys are same then sort on secondary key
				if ((ati_prim == atLeft_prim) || (ati_prim == atRight_prim)){
					compareAndSwap(i,leftChild,rightChild,"secondary");
				}
			}
		
		}
	}
	

	private void swap(Node _this, Node _that){
		int this_i = _heap.indexOf(_this);
		int that_i = _heap.indexOf(_that);
		_heap.set(that_i, _this);
		_heap.set(this_i, _that);
	}

	/*
	 * This function is called by the 'PercolateDown" function several times
	 * @param i, the element at which we are doing the percolation down
	 * @param left_c, the left child of element i
	 * @param right_c, the right child of element i
	 * @param _key, which key to use for comparison from the set{"primary","secondary"}
	 * We use min-compare since it's a min-heap
	 */
	private void compareAndSwap(int i, int left_c, int right_c, String _key){
		
		Node node_i = _heap.get(i); //node at position i
		Node node_left = _heap.get(left_c); //left child node
		Node node_right = _heap.get(right_c); //right child node
		int ati=0, atLeft=0, atRight = 0;
		
		//get the right key
		if (_key.equals("primary")){
			ati = getPrimaryKey(node_i);
			atLeft = getPrimaryKey(node_left);
			atRight = getPrimaryKey(node_right);
		}
		
		else if (_key.equals("secondary")){
			ati = getSecondaryKey(node_i);
			atLeft = getSecondaryKey(node_left);
			atRight = getSecondaryKey(node_right);
		}
		else{
			System.out.println("incorrect key name");
		}

		// the actual compare and swap
		if (ati > atLeft){
			if (ati > atRight){
				if (atLeft> atRight){
					swap(node_i,node_right);
				}
				else {
					swap(node_i,node_left);
				}
			}
			else {
				swap(node_i,node_left);
			}
		}
		else if (ati > atRight){
			swap(node_i,node_right);
		}
		
	}
	
	public void printHeap(){
		Iterator it = _heap.iterator();
		System.out.println("Printing Heap: ");
		int i = 0;
		while (it.hasNext()){
			Node n = (Node)it.next();
			System.out.println("heap element "+i+":"+n.getName()+" primary key "+getPrimaryKey(n)+ " secondary key "+getSecondaryKey(n));
			i++;			
		}
	}

}
