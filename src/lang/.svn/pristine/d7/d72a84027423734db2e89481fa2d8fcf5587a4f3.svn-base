package lang;

import java.util.Collection;

import edu.supercom.util.Pair;

/**
 * A <b>StateModification</b> is an effect on a state.  
 * It is defined in a static manner (i.e. obtaining the effects 
 * does not require any parameter).  
 * Its purpose is to simplify most implementations 
 * by the use of a single interface.  
 * 
 * @author Alban Grastien
 * */
public interface StateModification extends Iterable<Pair<YAMLDVar,YAMLDValue>> {

	/**
	 * Returns the list of state variables modified.  
	 * 
	 * @return a collection that contains all the variables 
	 * for which a modification is defined.  
	 * */
	public Collection<YAMLDVar> modifiedVariables();
	
	/**
	 * Returns the value of the specified modified variable.  
	 * 
	 * @param var a modified variable.  
	 * @return the value of <code>var</code> if <code>var</code> is modified, 
	 * <code>null</code> otherwise.  
	 * @see #modifiedVariables()
	 * */
	public YAMLDValue getModifiedValue(YAMLDVar var);
}
