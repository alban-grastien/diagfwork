package diag.symb.javabdd;

import diag.symb.SetOfStates;
import net.sf.javabdd.BDD;

/**
 * A <code>JavaBDDSetOfStates</code>, i.e., a Java BDD set of states, 
 * is an implementation of a set of states 
 * that simply encapsulates a Java BDD.  
 * 
 * @author Alban Grastien
 */
public class JavaBDDSetOfStates implements SetOfStates {
    
    /**
     * The BDD representing this set of states.  
     */
    private final BDD _bdd;
    
    /**
     * Creates a set of states defined by the specified BDD.  
     * 
     * @param bdd the BDD that represents the set of states.  
     */
    public JavaBDDSetOfStates(BDD bdd) {
        _bdd = bdd;
    }
    
    /**
     * Returns the BDD used to represent this set of states.  
     * 
     * @return the BDD of the set of states.  
     */
    public BDD getBDD() {
        return _bdd;
    }
    
    @Override
    public String toString() {
        return _bdd.toString();
    }
}
