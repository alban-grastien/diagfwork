package diag.symb.javabdd;

import diag.symb.SetOfTransitions;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;

/**
 * A <code>JavaBDDSetOfTransitions</code>, i.e., a Java BDD set of transitions, 
 * is an implementation of a set of transitions 
 * that simply encapsulates a Java BDD.  
 * This implementation has level 0.  
 * 
 * @author Alban Grastien
 */
public class JavaBDDSetOfTransitions implements SetOfTransitions {
    
    /**
     * The BDD representing this set of transitions.  
     */
    private final BDD _bdd;
    
    /**
     * Creates a set of transitions defined by the specified BDD.  
     * 
     * @param bdd the BDD that represents the set of transitions.  
     */
    public JavaBDDSetOfTransitions(BDD bdd) {
        _bdd = bdd;
    }
    
    /**
     * Returns the BDD used to represent this set of transitions.  
     * 
     * @return the BDD of the set of transitions.  
     */
    public BDD getBDD() {
        return _bdd;
    }
    
    @Override
    public String toString() {
        return _bdd.toString();
    }
    
    public JavaBDDSetOfTransitions union(
            BDDFactory fact, JavaBDDSetOfTransitions sot) {
        final BDD bdd1 = _bdd;
        final BDD bdd2 = ((JavaBDDSetOfTransitions)sot)._bdd;
        return new JavaBDDSetOfTransitions(bdd1.or(bdd2));
    }

    public JavaBDDSetOfTransitions intersection(BDDFactory fact, JavaBDDSetOfTransitions sot) {
        final BDD bdd1 = _bdd;
        final BDD bdd2 = ((JavaBDDSetOfTransitions)sot)._bdd;
        return new JavaBDDSetOfTransitions(bdd1.and(bdd2));
    }

    public JavaBDDSetOfStates nextStates(BDDFactory fact, BDD cv, BDD ev, BDDPairing ntc) {
        final BDD trans = getBDD();
        final BDD next = trans.exist(cv).exist(ev);
        final BDD result = next.replace(ntc);
        
        return new JavaBDDSetOfStates(result);
    }
}
