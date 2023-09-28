package edu.supercom.util.junctionTree.util;

import edu.supercom.util.junctionTree.Cluster;
import edu.supercom.util.junctionTree.Graph;
import java.util.*;
/**
 * @author priscilla
 *This class implements functions to triangulate a moral graph
 */
public class MoralGraphTriangulator {

	Graph _graph;
	/**
	 * constructor method
	 * @param cs, the moral graph to triangulate
	 */
	public MoralGraphTriangulator(Graph G) {
		_graph = (Graph)G.clone();
	}
	
	/*
	 * Returns a list of cliques
	 */
	public List<Cluster> getCliques(){
		final List<Cluster> cliques = new ArrayList<Cluster>();
		final BinaryHeap heap = new BinaryHeap(_graph);
		int i =0;
		while (heap.size() > 0){ //while there are still items in the heap
			final Cluster clus = heap.pop();
			boolean isSubset = false;
            for (final Cluster cl: cliques) {
				if (cl.containsAll(clus)){
					isSubset = true;
					break;
				}
            }
			if (!isSubset){
				cliques.add(clus);
			}
			i++;
		}
		
		return cliques;
	}

}
