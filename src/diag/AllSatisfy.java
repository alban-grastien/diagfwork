package diag;

import java.util.Set;

import lang.YAMLDComponent;
import lang.YAMLDFormula;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.VariableSemantics;

/**
 * A variable semantics that is true if all the component in the specified state 
 * satisfy the specified property.  
 * 
 * @author Alban Grastien
 * */
public class AllSatisfy extends DatedSemantic<Pair<Set<YAMLDComponent>,YAMLDFormula>> 
implements VariableSemantics {

	public AllSatisfy(int time, Set<YAMLDComponent> comps, YAMLDFormula f) {
		super(time, new Pair<Set<YAMLDComponent>,YAMLDFormula>(comps,f));
	}
	
	@Override
	public Pair<Set<YAMLDComponent>,YAMLDFormula> getCore() {
		return (Pair<Set<YAMLDComponent>,YAMLDFormula>)super.getCore();
	}
	
	public YAMLDFormula formula() {
		return getCore().second();
	}
	
	public Set<YAMLDComponent> components() {
		return getCore().first();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _time;
		result = prime * result + ((components() == null) ? 0 : components().hashCode());
		result = prime * result + ((formula() == null) ? 0 : formula().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AllSatisfy)) {
			return false;
		}
		AllSatisfy other = (AllSatisfy) obj;
		if (_time != other._time) {
			return false;
		}
		if (components() == null) {
			if (other.components() != null) {
				return false;
			}
		} else if (!components().equals(other.components())) {
			return false;
		}
		if (formula() == null) {
			if (other.formula() != null) {
				return false;
			}
		} else if (!formula().equals(other.formula())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return components().toString() + "--" 
		+ formula().toFormattedString() 
		+ "@" + _time;
	}
}
