package diag.symb;

import java.util.Collection;
import java.util.Set;
import lang.YAMLDEvent;

/**
 * A <code>SymbolicDiagnosis</code>, i.e., a symbolic diagnosis, 
 * is a symbolic representation of the diagnosis.  
 * 
 * @author Alban Grastien
 */
public interface SymbolicDiagnosis {
    
    /**
     * Enumerates the candidates of the diagnoses.  
     * 
     * @return the collection of the diagnosis candidates.  
     */
    public Collection<Set<YAMLDEvent>> candidates();
    
    /**
     * Indicates whether the specified set of faults is a candidate.  
     * 
     * @param fs a subset of faults (assumes the other faults did not occur).  
     * @return <tt>true</tt> if <tt>candidates().contains(fs)</tt>, 
     * <tt>false</tt> otherwise.  
     */
    public boolean isCandidate(Set<YAMLDEvent> fs);
    
    /**
     * Indicates whether the two specified of faults 
     * are consistent with this diagnosis, 
     * where the faults in the first set are assumed to have occurred 
     * and the faults in the second set are assumed to have not occurred 
     * (no assumption is made on the other faults).  
     * 
     * @param fso the set of faults which are assumed to have occurred.  
     * @param fsn the set of faults which are assumed to have not occurred.  
     * @return <tt>true</tt> if there exists an object <tt>s</tt> 
     * such that 
     * <ul>
     * <li><tt>candidates().contains(s)</tt>, 
     * </li>
     * <li><tt>s.containsAll(fso)</tt>, 
     * </li>
     * <li><tt>s.retainAll(fsn).isEmpty()</tt>, 
     * </li>
     * </ul>
     * <tt>false</tt> otherwise.  
     */
    public boolean isConsistent(Collection<YAMLDEvent> fso, Collection<YAMLDEvent> fsn);
    
}
