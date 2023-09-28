package lang;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A {@link YAMLDVar} is a variable that models the state of a component.  
 * Such a variable is often referred to as a <i>state variable</i> 
 * (as opposed to a dependent variable).  
 * 
 * @see YAMLDDVar
 * */
public class YAMLDVar extends YAMLDGenericVar
{
	public YAMLDVar(String newName, YAMLDComponent comp) 
	{
		super(newName,comp);
	}
	
	/**
	 * Computes the list of {@link MMLDRule} that may affect this state variable.  
	 * Please notice that the list is computed and not stored.  
	 * Hence, heavy use of this method should be avoided.  
	 * 
	 * @return the subset of {@link MMLDRule} of this variable component 
	 * such that one assignment of the rule refers to this variable.  
	 * */
	public Collection<MMLDRule> getAffectingRules() {
		final Collection<MMLDRule> result = new ArrayList<MMLDRule>();

		// bizare: the commented-out for-loop raises a runtime error, but
		// the replacement below, which does the same thing, does not.
		YAMLDComponent var_comp = this.getComponent();
		java.util.List<MMLDTransition> comp_trans_list = var_comp.transitions();
		for (final MMLDTransition tr : comp_trans_list) {
		//for (final MMLDTransition tr: this.getComponent().transitions()) {
			for (final MMLDRule r: tr.getRules()) {
				for (final YAMLDAssignment ass: r.getAssignments()) {
					final YAMLDGenericVar var = ass.variable();
					if (var == this) {
						result.add(r);
						break;
					}
				}
			}
		}
		
		return result;
	}
	
}
