package edu.supercom.util.junctionTree;

/**
 * A main to test the JunctionTree implementation.  
 */
public class _JunctionTree {
    
    public static void main(String[] args) {        
        final Graph g = new Graph();
        
        final Node na = new Node("A");
        final Node nb = new Node("B");
        final Node nc = new Node("C");
        final Node nd = new Node("D");
        final Node ne = new Node("E");
        final Node nf = new Node("F");
        final Node ng = new Node("G");
        final Node nh = new Node("H");
        
        final Node[] nodes = { 
            na, nb, nc, nd, ne, nf, ng, nh
        };
        
        for (final Node node: nodes) {
            g.newNode(node);
        }
        
        g.newEdge(new Edge(na, nb, 1));
        g.newEdge(new Edge(na, nc, 1));
        g.newEdge(new Edge(nb, nd, 1));
        g.newEdge(new Edge(nc, ne, 1));
        g.newEdge(new Edge(nc, ng, 1));
        g.newEdge(new Edge(nd, nf, 1));
        g.newEdge(new Edge(ne, nf, 1));
        g.newEdge(new Edge(ne, nh, 1));
        g.newEdge(new Edge(ng, nh, 1));
        
        final String s = g.dotRepresentation();
        System.out.println(s);
        
        final JunctionTree jt = JunctionTree.graphToJtree(g);
        final String s2 = jt.dotRepresentation();
        System.out.println(s2);
    }
    
}
