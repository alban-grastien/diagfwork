package diag.reiter;

/**
 * A hypothesis models a set of scenarios that are identical from a diagnosis point of view.  
 * Although some implementations of hypothesis may be self-sufficient, 
 * a hypothesis, in general, is used in conjunction with a {@link HypothesisSpace}.  
 * 
 * @see HypothesisSpace
 * */
public interface Hypothesis {
	
//	/**
//	 * Builds a SAT representation of this hypothesis.  
//	 * 
//	 * @param eventVarass the variable assigner that allows to determine 
//	 * which SAT variable is associated with a specific event.  
//	 * @param hypoVarass the variable assigner that allows to determine 
//	 * which SAT variable is associated with a hypothesis item 
//	 * (this should be generated with 
//	 * {@link #generateVariables(VariableLoader, Set, int)}).  
//	 * @param out the output stream where the clauses are saved.  
//	 * @param faults the list of faults in the network.  
//	 * @param n the number of time steps.  
//	 * */
//	@Deprecated
//	public void generateSAT(VariableAssigner eventVarass, 
//			VariableAssigner hypoVarass, 
//			ClauseStream out, Set<YAMLDEvent> faults, int n);
}
