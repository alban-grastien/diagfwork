package diag.reiter2.set;

import diag.reiter2.Conflict;
import diag.reiter2.Property;
import diag.reiter2.SATTranslator;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.TimedSemanticFactory;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A <code>SetTranslator</code>, i.e., a set translator, 
 * is a SAT translator for set hypotheses.  
 * 
 * @author Alban Grastien
 * @param X the type of element in the SHS.  
 */
public class SetTranslator<X> implements 
        SATTranslator<X, SetHypothesis<X>, SetHypothesisSpace<X>> {
    
    /**
     * The mapping that indicates the SAT variable 
     * which is true iff the specified element occurred.  
     */
    private final Map<X,Integer> _occurred;
    
    /**
     * A mapping set hypothesis -> variable that is true 
     * iff the current hypo is a descendant of the specified hypo.  
     */
    private final Map<SetHypothesis<X>,Integer> _prop;
    
    /**
     * A mapping variable that is true iff the current hypo is a descendant of h
     * -> h
     */
    private final Map<Integer,SetHypothesis<X>> _intToHypo;
    
    public SetTranslator() {
        _occurred = new HashMap<X, Integer>();
        _prop = new HashMap<SetHypothesis<X>, Integer>();
        _intToHypo = new HashMap<Integer, SetHypothesis<X>>();
    }

    @Override
    public int getSATVariable(VariableLoader loader, 
        int firstTimeStep, int lastTimeStep, 
        Property<SetHypothesis<X>> p, SetHypothesisSpace<X> s) {
        return propertySatisfied(loader, s, p);
    }

    @Override
    public void createSATClauses(ClauseStream out, 
        VariableAssigner varass, TimedSemanticFactory<X> fact, 
        int firstTimeStep, int lastTimeStep, 
        Property<SetHypothesis<X>> p) {
        final Set<X> implementedElements = new HashSet<X>();
        printSATClausesForProperty(out, varass, fact, firstTimeStep, lastTimeStep, p, implementedElements);
    }

    @Override
    public void createAllSATClauses(ClauseStream out, 
        VariableAssigner varass, TimedSemanticFactory<X> fact, 
        int firstTimeStep, int lastTimeStep, 
        Collection<Property<SetHypothesis<X>>> ps) {
        //System.out.println("+++ createAllSATClauses for " + ps);
        
        final Set<X> implementedElements = new HashSet<X>();
        
        for (final Property<SetHypothesis<X>> p: ps) {
            printSATClausesForProperty(out, varass, fact, firstTimeStep, lastTimeStep, p, implementedElements);
        }
    }
    
    @Override
    public Conflict<SetHypothesis<X>> SATConflictToDiagnosisConflict(int[] conflict) {
        final Set<Property<SetHypothesis<X>>> props = 
                new HashSet<Property<SetHypothesis<X>>>();
        
        for (final int satVar: conflict) {
            final Property<SetHypothesis<X>> p = getProperty(-satVar);
            props.add(p);
        }
        
        return new Conflict<SetHypothesis<X>>(props);
    }
    
    private Property<SetHypothesis<X>> getProperty(int lit) {
        final int var = Math.abs(lit);
        final SetHypothesis<X> h = _intToHypo.get(var);
        if (lit > 0) {
            return Property.descendant(h);
        } else {
            return Property.notDescendant(h);
        }
    }
 
    // Returns the SAT variable that is true iff the specified property is satisfied
    private int propertySatisfied(VariableLoader loader, SetHypothesisSpace<X> space, 
            Property<SetHypothesis<X>> p) {
        final SetHypothesis<X> h = p.getHypothesis();
        {
            final Integer result = _prop.get(h);
            if (result != null) {
                return result;
            }
        }
        
        // Makes sure all variables are defined
        for (final X e: h.occurred()) {
            elementOccurred(loader, e);
        }
        
        final int result = loader.allocate(1);
        _prop.put(h, result);
        _intToHypo.put(result, h);
        if (p.isDescent()) {
            return result;
        } else {
            return -result;
        }
    }
    
    // Returns the SAT variable that is true iff the specified element occurred
    private int elementOccurred(VariableLoader loader, X e) {
        {
            final Integer result = _occurred.get(e);
            if (result != null) {
                return result;
            }
        }
        
        final int result = loader.allocate(1);
        _occurred.put(e, result);
        return result;
    }
    
    // Prints the SAT clause defining the property p 
    // (and recursively the clause defining the elements that occurred).  
    // Param elementsImplemented indicates the elements 
    // for which the clause need not be written (already written before)
    private void printSATClausesForProperty(ClauseStream out, 
            VariableAssigner varass, TimedSemanticFactory<X> fact, 
            int firstTimeStep, int lastTimeStep, 
            Property<SetHypothesis<X>> p, Set<X> implementedElements) {
        //System.out.println("+++ printSATClausesForProperty " + p);
        
        final SetHypothesis<X> h = p.getHypothesis();
        final Set<X> elements = h.occurred();
        
        final int propVar = _prop.get(h);
        final int nbElements = elements.size();
        final int[] elementVariables = new int[nbElements+1];
        
        int index = 0;
        for (final X e: elements) {
            final int varX = _occurred.get(e);
            
            elementVariables[index] = -varX;
            index++;
            
            out.put(-propVar, varX);
            
            if (!implementedElements.add(e)) { // Already implemented
                continue;
            }
            
            printSATClausesForElement(out, varass, fact, firstTimeStep, lastTimeStep, e);
        }
        
        elementVariables[nbElements] = propVar;
        out.put(elementVariables);
    }
    
    private void printSATClausesForElement(ClauseStream out, 
            VariableAssigner varass, TimedSemanticFactory<X> fact, 
            int firstTimeStep, int lastTimeStep, 
            X e) {
        final int elementVar = _occurred.get(e);
        
        final int nbTimeSteps = lastTimeStep - firstTimeStep + 1;
        final int[] localOccurrence = new int[nbTimeSteps + 1];
        
        for (int tsIndex = 0 ; tsIndex < nbTimeSteps ; tsIndex++) {
            final int t = firstTimeStep + tsIndex;
            final VariableSemantics sem = fact.buildSemantics(e, t);
            final int var = varass.getVariable(sem);
            out.put(-var, elementVar);
            localOccurrence[tsIndex] = var;
        }
        
        localOccurrence[nbTimeSteps] = -elementVar;
        out.put(localOccurrence);
    }
}