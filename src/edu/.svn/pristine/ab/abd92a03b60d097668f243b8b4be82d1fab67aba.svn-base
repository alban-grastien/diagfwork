/**
 * 
 */
package edu.supercom.util.junctionTree;

/**
 * This class describes an edge in a Graph
 * @author priscilla
 *
 */
public class Edge {

    protected final Node _n1;
    protected final Node _n2;
    private final int _weight;

    //private Object _obj;
    /**
     * Builds an edge with the two specified nodes.
     *
     * @param n1 the first node.
     * @param n2 the second node.
     */
    public Edge(Node n1, Node n2, int w) {
        _n1 = n1;
        _n2 = n2;
        _weight = w;
    }

    public Node[] getNodes() {
        Node[] nodeArray = {_n1, _n2};
        return nodeArray;
    }

    public boolean hasNode(Node n) {
        if (_n1 == n) {
            return true;
        }

        if (_n2 == n) {
            return true;
        }

        return false;
    }

    /*
     * given Node n, give me the other node of the edge
     */
    public Node otherNode(Node n) {
        if (_n1 == n) {
            return _n2;
        }
        if (_n2 == n) {
            return _n1;
        }
        throw new IllegalArgumentException(n.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Edge)) {
            return false;
        }

        Edge e = (Edge) obj;

        if (_n1 != e._n1) {
            return false;
        }

        if (_n2 != e._n2) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return _n1.hashCode() + _n2.hashCode();
    }
}
