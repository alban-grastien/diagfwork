package diag.symb.javabdd;

import diag.symb.SymbolicDiagnosis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lang.YAMLDEvent;
import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

/**
 * A <code>JavaBDDDiagnosis</code>, i.e., a Java BDD diagnosis, 
 * is a representation of the diagnosis as a BDD.  
 * 
 * @author Alban Grastien
 */
public class JavaBDDDiagnosis implements SymbolicDiagnosis {
    
    /**
     * The list of faults.  
     */
    private final List<YAMLDEvent> _faults;
    
    /**
     * The mapping that indicates how a specified fault is modeled. 
     */
    private final Map<YAMLDEvent,BDD> _eventToBDD;
    
    /**
     * The BDD that represents the diagnosis.  
     */
    private final BDD _bdd;
    
    /**
     * Builds a diagnosis represented by a Java BDD.  
     * 
     * @param m a mapping that indicates how a specified fault is modeled. 
     * @param bdd the BDD that represents the diagnosis.  
     */
    public JavaBDDDiagnosis(Map<YAMLDEvent,BDD> m, BDD bdd) {
        _eventToBDD = m;
        _bdd = bdd;
        _faults = new ArrayList<YAMLDEvent>(m.keySet());
    }

    @Override
    public boolean isCandidate(Set<YAMLDEvent> fs) {
        final BDDFactory fact = _bdd.getFactory();
        
        BDD bddCandidate = fact.one();
        for (final Map.Entry<YAMLDEvent,BDD> entry: _eventToBDD.entrySet()) {
            final YAMLDEvent event = entry.getKey();
            final BDD bdd = entry.getValue();
            if (fs.contains(event)) {
                bddCandidate = bddCandidate.and(bdd);
            } else {
                bddCandidate = bddCandidate.and(bdd.not());
            }
        }
        
        final BDD consistencyTest = bddCandidate.and(_bdd);
        if (consistencyTest.isZero()) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean isConsistent(Collection<YAMLDEvent> fso, Collection<YAMLDEvent> fsn) {
        final BDDFactory fact = _bdd.getFactory();
        
        BDD bddCandidate = fact.one();
        for (final Map.Entry<YAMLDEvent,BDD> entry: _eventToBDD.entrySet()) {
            final YAMLDEvent event = entry.getKey();
            final BDD bdd = entry.getValue();
            if (fso.contains(event)) {
                bddCandidate = bddCandidate.and(bdd);
            }
            if (fsn.contains(event)) {
                bddCandidate = bddCandidate.and(bdd.not());
            }
        }
        
        final BDD consistencyTest = bddCandidate.and(_bdd);
        if (consistencyTest.isZero()) {
            return false;
        }
        
        return true;
    }

    @Override
    public Collection<Set<YAMLDEvent>> candidates() {
        final List<Set<YAMLDEvent>> result = new ArrayList<Set<YAMLDEvent>>();
        
        {
            final Set<YAMLDEvent> positiveEvents = new HashSet<YAMLDEvent>();
            final Set<YAMLDEvent> negativeEvents = new HashSet<YAMLDEvent>();
            populateEnumDiagnosis(result, positiveEvents, negativeEvents, 0);
        }
        
        return result;
    }
    
    
    private void populateEnumDiagnosis(
            List<Set<YAMLDEvent>> result, 
            Set<YAMLDEvent> positiveEvents, 
            Set<YAMLDEvent> negativeEvents, 
            int currentPosition
            ) {
        if (!isConsistent(positiveEvents, negativeEvents)) {
            return;
        }
        
        if (currentPosition == _faults.size()) {
            result.add(positiveEvents);
            return;
        }
        
        final YAMLDEvent nextEvent = _faults.get(currentPosition);
        
        {
            final Set<YAMLDEvent> newPositiveEvents = new HashSet<YAMLDEvent>(positiveEvents);
            newPositiveEvents.add(nextEvent);
            final Set<YAMLDEvent> newNegativeEvents = negativeEvents;
            populateEnumDiagnosis(result, newPositiveEvents, newNegativeEvents, currentPosition+1);
        }
        
        {
            final Set<YAMLDEvent> newPositiveEvents = positiveEvents;
            final Set<YAMLDEvent> newNegativeEvents = new HashSet<YAMLDEvent>(negativeEvents);
            newNegativeEvents.add(nextEvent);
            populateEnumDiagnosis(result, newPositiveEvents, newNegativeEvents, currentPosition+1);
        }
    }
}
