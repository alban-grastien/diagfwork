/**
 * 
 */
package edu.supercom.util.junctionTree;

/**
 * This class describes a Node in a Graph
 * @author priscilla
 *
 */
public class Node {
	
	final protected String name;
	 
    protected int weight =2 ;  //temporarily set to 1; change later
	 
    /**
     * Creates a new instance of Node
     *
     * @param the object that is considered a node
     */
    public Node(Object obj){
    	name = obj.toString();
    }

//    /*
//     * Constructor method
//     * Creates a node identical to the specified node
//     */
//    public Node(Node n){
//    	name = n.name;
//    	weight = n.weight;
//    }
//
//    @Override
//    public Node clone(){
//    	return new Node(this);
//    }
    
    /**
     * assign a weight value for the Node
     * @param w the value for the node weight
     */
    public void setWeight(int w){
    	weight=w;
    }
    
    public int getWeight(){
		return weight;
	}
    
    @Override
    public boolean equals(Object obj){
        return obj == this;
    }
    
    @Override
    public int hashCode(){
    	return name.hashCode()+5;
    }
   
  
    public String getName(){
    	return name;
    }
    
    /**
     * Returns a string representation of this node.
     *
     * @return a string representation of this node.
     */
    @Override
    public String toString()
    {
        return name;
    }
	
}

