package edu.supercom.util.junctionTree;

import edu.supercom.util.junctionTree.util.MoralGraphTriangulator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * @author priscilla
 *This class implements a graph of a composite system
 *Nodes consist of system components
 *Transitions consist of connections between the nodes
 */
public class JunctionTree extends Graph {
    //private Map nodeClusterMap = new HashMap();

    /**
     * Create composite system graph of specified component
     * @param CompositeSystem the composite system whose graph we want
     */
    public JunctionTree() {
        super();
    }

    public JunctionTree(Graph g) {
        super();
        
        for (final Node n: g.nodes()) {
            newNode(n);
        }
        
        for (final Edge e: g.getEdges()) {
            newEdge(e);
        }
    }

    public static JunctionTree graphToJtree(Graph g) {
        if (g.nodes().isEmpty()) {
            return new JunctionTree();
        }

        final Graph gTree = g.clone();
        final MoralGraphTriangulator tr = new MoralGraphTriangulator(gTree);
        final List<Cluster> cliques = tr.getCliques(); //get the cliques
        //System.err.println("#JunctionTree# The cliques are " + cliques);

        final List<Sepset> sepsets = new ArrayList<Sepset>();
        //create candidate sepsets
        //For each pair of clusters (X,Y), create a sepset Sxy.
        for (int i = 0 ; i < cliques.size() ; i++) {
            final Cluster ci = cliques.get(i);
            for (int j = i + 1; j < cliques.size(); j++) {
                final Cluster cj = cliques.get(j);
                final Sepset Sxy = new Sepset(ci, cj);
                sepsets.add(Sxy);
            }
        }

        //sort sepsets in order of largest mass and smallest cost
        Collections.sort(sepsets);
        //Begin with a set of n trees, each consisting of a single clique.
//        final Collection<Graph> trees = new ArrayList<Graph>();
        final Map<Cluster,Graph> map = new HashMap<Cluster,Graph>();
        for (final Cluster cluster: cliques) {
            final Graph tree = new Graph();
            tree.newNode(cluster);
//            trees.add(tree);
            map.put(cluster, tree);
        }

        //repeat until all (n-1) sepsets have been inserted into the forest
        int n = cliques.size() - 1;
        final Iterator<Sepset> itS = sepsets.iterator();
        //System.out.println("sepset:"+ sepsets.size());
        while (itS.hasNext() && n > 0) {
            final Sepset Sxy = itS.next();
            final Cluster X = Sxy.n1();
            final Cluster Y = Sxy.n2();

            //System.out.println("#JT#: merging " + X.toString() + " and " + Y.toString());

            //insert the sepset Sxy between X and Y *only if*
            //X and Y are on different trees in the forest
            //otherwise a cycle will form
            Graph TX = (Graph) map.get(X);
            Graph TY = (Graph) map.get(Y);
            if (TX != TY) {
                // BAN: I modified: TX.add(TY);
                TX.addGraph(TY);
                //Edge e = (Edge)Sxy;
                //TX.addEdge(e);
                TX.newEdge(Sxy);
                //TX.printDescription();
//                trees.remove(TY);
                for (final Node node: TY.nodes()) {
                    final Cluster cluster = (Cluster)node;
                    map.put(cluster, TX);
                }
                n--;
            }
        }
        
        //get the only tree left in the forest
        final Graph graph = map.values().iterator().next();
        
        final JunctionTree jt = new JunctionTree(graph);
        //jt.printDescription();
        //System.out.println("jt test "+ jt.NodeTotal());
        //System.out.println("test2 "+ jt._nodes.get(0));
        return jt;
    }

    public void printSepsets(List<Sepset> sepsets) {
        for (final Sepset sepset: sepsets) {
            sepset.printSepset();
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Cluster> getNeighbours(Node n){
        return (List<Cluster>)super.getNeighbours(n);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Set<Cluster> nodes() {
        return (Set<Cluster>)super.nodes();
    }
}
