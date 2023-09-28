package diag.symb.javabdd;

import diag.symb.SetOfTransitions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;

/**
 * A <code>JavaBDDUnionSetOfTransitions</code>, i.e., 
 * a Java BDD union set of transition, is a set of transitions 
 * defined as a (disjunctive) set of BDDs.  
 * 
 * @author Alban Grastien.
 */
public class JavaBDDUnionSetOfTransitions implements SetOfTransitions {

    /**
     * The list of BDDs that represents this set of transitions.  
     */
    private final List<BDD> _bdds;
    
    /**
     * Creates a set of transitions defines as the disjunction 
     * of the specified BDDs.  
     * 
     * @param bdds the BDDs which are unionised 
     * (it is assumed that none of them is null).  
     */
    public JavaBDDUnionSetOfTransitions(Collection<BDD> bdds) {
        _bdds = new ArrayList<BDD>();
        _bdds.addAll(bdds);
    }
    
    /**
     * Creates a set of transitions defines as a single BDD.  
     * 
     * @param bdd the BDD that represents the set of transitions.  
     */
    public JavaBDDUnionSetOfTransitions(BDD bdd) {
        _bdds = new ArrayList<BDD>();
        _bdds.add(bdd);
    }

    /**
     * Computes the union of this set of transitions 
     * with the specified set of transitions.  
     * 
     * @param sot the set of transitions whose union with this set of transitions 
     * must be computed.  
     * @return the union of this set and the specified set.  
     */
    public JavaBDDUnionSetOfTransitions union(
            BDDFactory fact, JavaBDDUnionSetOfTransitions sot) {
        if (this._bdds.isEmpty()) {
            return sot;
        }
        if (sot._bdds.isEmpty()) {
            return this;
        }
        
        final List<BDD> result = new ArrayList<BDD>();
        result.addAll(this._bdds);
        result.addAll(sot._bdds);
        
        return new JavaBDDUnionSetOfTransitions(result);
    }

    /**
     * Computes the intersection of this set of transitions 
     * with the specified set of transitions.  
     * 
     * @param sot the set of transitions whose intersection with this set of transitions 
     * must be computed.  
     * @return the intersection of this set and the specified set.  
     */
    public JavaBDDUnionSetOfTransitions intersection(
            BDDFactory fact, JavaBDDUnionSetOfTransitions sot) {
        // Empty: no intersection. 
        if (this._bdds.isEmpty()) {
            return this;
        }
        if (sot._bdds.isEmpty()) {
            return sot;
        }
        
        // Singleton
        if (this._bdds.size() == 1) {
            return sot.intersectWithSingleton(this._bdds.get(0));
        }
        if (sot._bdds.size() == 1) {
            return this.intersectWithSingleton(sot._bdds.get(0));
        }
        
        final List<BDD> result = new ArrayList<BDD>();
        for (final BDD bdd1: _bdds) {
            for (final BDD bdd2: sot._bdds) {
                final BDD bdd = bdd1.and(bdd2);
                if (bdd.isZero()) {
                    continue;
                }
                result.add(bdd);
            }
        }
        
        return new JavaBDDUnionSetOfTransitions(result);
    }
    
    private JavaBDDUnionSetOfTransitions intersectWithSingleton(BDD otherBdd) {
        final List<BDD> result = new ArrayList<BDD>();
        
        for (final BDD bdd1: _bdds) {
            final BDD inter = bdd1.and(otherBdd);
            if (inter.isZero()) {
                continue;
            }
            result.add(inter);
        }
        
        return new JavaBDDUnionSetOfTransitions(result);
    }

    /**
     * Extracts the set of next states of this set of states.  
     * 
     * @param fact the BDD factory used to generate BDDs.  
     * @param cv the BDD that represents the current states.  
     * @param ev the BDD that represents the future states.  
     * @param ntc the BDD pairing that explains how a next state 
     * is mapped back to current states. 
     */
    public JavaBDDSetOfStates nextStates(BDDFactory fact, BDD cv, BDD ev, BDDPairing ntc) {
        BDD result = fact.zero();
        
        for (final BDD trans: _bdds) {
            final BDD next = trans.exist(cv).exist(ev);
            final BDD newStates = next.replace(ntc);
            result = result.or(newStates);
        }
        
        return new JavaBDDSetOfStates(result);
    }
    
}
