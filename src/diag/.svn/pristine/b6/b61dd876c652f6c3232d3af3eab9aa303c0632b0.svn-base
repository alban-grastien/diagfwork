package diag.reiter2;

import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.TimedSemanticFactory;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import java.util.Collection;

/**
 * A <code>SATTranslator</code>, i.e., a SAT translator, 
 * is an object that is able to produce SAT implementations of properties 
 * and interprete the results.  
 */
public interface SATTranslator<X, H extends Hypothesis, HS extends HypothesisSpace<H>> {
    
    /**
     * Returns the SAT variable that should evaluate to <i>true</i> 
     * if the specified property is satisfied.  
     * This method may generate the SAT variable 
     * (along with other SAT variables) 
     * if the variable does not exist yet.  
     * 
     * @param loader the variable loader 
     * that allows to allocate new SAT variables.  
     * @param firstTimeStep the first time step 
     * the property is defined on (inclusive).  
     * @param lastTimeStep the last time step 
     * the property is defined on (exclusive).  
     * @param p the property for which the SAT variable is created.  
     * @param s the hypothesis space the property is defined on.  
     * @return the SAT variable corresponding to property <code>p</code>.  
     */
    public int getSATVariable(VariableLoader loader, 
            int firstTimeStep, int lastTimeStep, 
            Property<H> p, HS s);
    
    /**
     * Writes in the specified clause stream the SAT clauses 
     * necessary to enforce 
     * that the specified property is satisfied 
     * only if the SAT variable associated 
     * with {@link #getSATVariable(edu.supercom.util.sat.VariableLoader, int, int, diag.reiter2.Property, diag.reiter2.HypothesisSpace)} 
     * is <code>true</code>.  
     * Notice that this translator may remember the clauses 
     * it already wrote, 
     * which means that it may not rewrite redundant clauses 
     * (even if the clause stream is different).  
     * 
     * @param out the clause stream where the clauses must be saved.  
     * @param varass a variable assigner that returns the SAT variable 
     * associated with variables of the form <i>&lt;obj,t&gt;</i> 
     * where <i>obj</i> is a relevant object of the hypothesis space 
     * (for instance an event or an assignment)
     * and <i>t</i> is a time step.  
     * @param fact the factory that allows to access the variable semantics 
     * of the problem that are relevant for the hypotheses.  
     * @param firstTimeStep the first time step 
     * the property is defined on (inclusive).  
     * @param lastTimeStep the last time step 
     * the property is defined on (exclusive).  
     * @param p the property.  
     */
    public void createSATClauses(ClauseStream out, VariableAssigner varass, TimedSemanticFactory<X> fact, 
            int firstTimeStep, int lastTimeStep, Property<H> p);
    
    /**
     * Writes in the specified clause stream the SAT clauses 
     * necessary to enforce 
     * that the specified property is satisfied 
     * only if the SAT variable associated 
     * with {@link #getSATVariable(edu.supercom.util.sat.VariableLoader, int, int, diag.reiter2.Property, diag.reiter2.HypothesisSpace)} 
     * is <code>true</code>.  
     * As opposed to {@link #createSATClauses(edu.supercom.util.sat.ClauseStream, edu.supercom.util.sat.VariableAssigner, edu.supercom.util.sat.TimedSemanticFactory, int, int, diag.reiter2.Property) }, 
     * all clauses are written.  
     * 
     * @param out the clause stream where the clauses must be saved.  
     * @param varass a variable assigner that returns the SAT variable 
     * associated with variables of the form <i>&lt;obj,t&gt;</i> 
     * where <i>obj</i> is a relevant object of the hypothesis space 
     * (for instance an event or an assignment)
     * and <i>t</i> is a time step.  
     * @param fact the factory that allows to access the variable semantics 
     * of the problem that are relevant for the hypotheses.  
     * @param firstTimeStep the first time step 
     * the property is defined on (inclusive).  
     * @param lastTimeStep the last time step 
     * the property is defined on (exclusive).  
     * @param p the property.  
     */
    public void createAllSATClauses(ClauseStream out, VariableAssigner varass, TimedSemanticFactory<X> fact, 
            int firstTimeStep, int lastTimeStep, Collection<Property<H>> ps);
    
    // TODO: createAllSATClauses(ClauseStream out, VariableAssigner varass, TimedSemanticFactory<X> fact, int firstTimeStep, int lastTimeStep, Collection<Property<H>> p)
    
    /**
     * Translates the specified SAT conflict into a conflict.  
     * 
     * @param satConflict a list of SAT variables 
     * that are inconsistent with the diagnosis problem.  
     * @return the conflict corresponding to <code>satConflict</code>.  
     */
    public Conflict<H> SATConflictToDiagnosisConflict(int[] conflict);
}
