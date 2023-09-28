package diag.reiter2;

import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * A <code>Conflict</code>, i.e., a conflict, 
 * is a set of properties that are inconsistent with the diagnosis problem.  
 */
public class Conflict<H extends Hypothesis> {
    
    /**
     * The list of descent properties in the conflict.  
     */
    private final UnmodifiableSet<Property<H>> _descents;
    
    /**
     * The list of non descent properties in the conflict.  
     */
    private final UnmodifiableSet<Property<H>> _nonDescents;
    
    /**
     * The set of properties.  
     * (Redundant with _descent and _nonDescent, but the overhead should be negligible)
     */
    private final UnmodifiableSet<Property<H>> _properties;
    
    /**
     * Builds a conflict on the specified set of properties.  
     * 
     * @param properties the set of properties 
     * that, together, are inconsistent with the diagnosis problem.  
     */
    public Conflict(Set<Property<H>> properties) {
        final UnmodifiableSetConstructor<Property<H>> descentCon = 
                new UnmodifiableSetConstructor<Property<H>>();
        final UnmodifiableSetConstructor<Property<H>> nonDescentCon = 
                new UnmodifiableSetConstructor<Property<H>>();
        final UnmodifiableSetConstructor<Property<H>> propCon = 
                new UnmodifiableSetConstructor<Property<H>>();
        
        for (final Property<H> p: properties) {
            propCon.add(p);
            if (p.isDescent()) {
                descentCon.add(p);
            } else {
                nonDescentCon.add(p);
            }
        }
        
        _descents = descentCon.getSet();
        _nonDescents = nonDescentCon.getSet();
        _properties = propCon.getSet();
    }
    
    /**
     * Builds a conflict on the specified set of properties.  
     * 
     * @param properties the set of properties 
     * that, together, are inconsistent with the diagnosis problem.  
     */
    public Conflict(Property<H>... properties) {
        final UnmodifiableSetConstructor<Property<H>> descentCon = 
                new UnmodifiableSetConstructor<Property<H>>();
        final UnmodifiableSetConstructor<Property<H>> nonDescentCon = 
                new UnmodifiableSetConstructor<Property<H>>();
        final UnmodifiableSetConstructor<Property<H>> propCon = 
                new UnmodifiableSetConstructor<Property<H>>();
        
        for (final Property<H> p: properties) {
            propCon.add(p);
            if (p.isDescent()) {
                descentCon.add(p);
            } else {
                nonDescentCon.add(p);
            }
        }
        
        _descents = descentCon.getSet();
        _nonDescents = nonDescentCon.getSet();
        _properties = propCon.getSet();
    }
    
    /**
     * Returns the set of descent properties of this conflict.  
     * 
     * @return the set of descent properties of this conflict.  
     */
    public UnmodifiableSet<Property<H>> getDescentProperties() {
        return _descents;
    }
    
    /**
     * Returns the set of non descent properties of this conflict.  
     * 
     * @return the set of non descent properties of this conflict.  
     */
    public UnmodifiableSet<Property<H>> getNonDescentProperties() {
        return _nonDescents;
    }
    
    /**
     * Returns the set of properties in this conflict.  
     * 
     * @return the set of properties that are inconsistent 
     * with the diagnosis problem.  
     */
    public UnmodifiableSet<Property<H>> getProperties() {
        return _properties;
    }

    @Override
    public String toString() {
        return _properties.toString();
    }
}
