/**
 * 
 */
package edu.supercom.util.junctionTree.util;

import edu.supercom.util.junctionTree.Graph;
import edu.supercom.util.junctionTree.Node;
import java.util.Comparator;
import java.util.List;

/**
 * @author priscilla
 *A class to compare two node objects based on a primary key and a secondary key
 *
 */
public class NodeComparator implements Comparator<Node> {

	/**
	 * 
	 */
	private Graph _graph;
	
	
	public NodeComparator(Graph G) {
		//super();
		_graph = G;
	}
	
	/*
	 * function to get the primary key of node for sorting in heap
	 * Primary key is given by the number of edges that would be added to the graph if Node n is chosen
	 * @param Node n the chosen node in the graph
	 */
	private int getPrimaryKey(Node n){
		
		int key=0;
		List neighbours;
		
		neighbours = _graph.getNeighbours(n);
		//System.out.println("neighbours: " + neighbours.size());
		int nb_size = _graph.getNeighbours(n).size(); //done outside loop for opt
		int minus_one = nb_size-1; //for opt
		for (int i=0;i<minus_one;i++){
			Node nb_i = (Node)neighbours.get(i);
			for (int j=i+1;j<nb_size;j++){
				Node next_nb = (Node)neighbours.get(j);
				if (!(_graph.isNeighbour(nb_i,next_nb))){
					key++;
				}
			}
		}
		//System.out.println("key: "+key);
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
	
    @Override
	public int compare(Node obj1, Node obj2) {
		Node n1 = (Node)obj1;
		Node n2 = (Node)obj2;
		
		//get the keys
        {
            int pk1 = getPrimaryKey(n1);
            int pk2 = getPrimaryKey(n2);
            if (pk1 < pk2){return -1;}
            if (pk1 > pk2){return 1;}
        }
                
		int sk1 = getSecondaryKey(n1);
		int sk2 = getSecondaryKey(n2);
		if (sk1 < sk2){return -1;}
		if (sk1 > sk2){return 1;}
                
		return 0;
	}
	
	
	
}
