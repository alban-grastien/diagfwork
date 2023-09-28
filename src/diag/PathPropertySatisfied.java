package diag;

import lang.Path;
import lang.YAMLDFormula;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.VariableSemantics;

/**
 * A variable semantics that is true if a property is true 
 * on all components on a path (except the first and last components).  
 * 
 * @author Alban Grastien
 * */
public class PathPropertySatisfied extends DatedSemantic<Pair<Path, YAMLDFormula>> 
implements VariableSemantics {

	public PathPropertySatisfied(int time, Path path, YAMLDFormula condition) {
		super(time, new Pair<Path, YAMLDFormula>(path, condition));
	}
	
	@Override
	public Pair<Path, YAMLDFormula> getCore() {
		return (Pair<Path, YAMLDFormula>)super.getCore();
	}
	
	public Path path() {
		return getCore().first();
	}
	
	public YAMLDFormula condition() {
		return getCore().second();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _time;
		result = prime * result + ((path() == null) ? 0 : path().hashCode());
		result = prime * result + ((condition() == null) ? 0 : condition().hashCode());
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
		if (!(obj instanceof PathPropertySatisfied)) {
			return false;
		}
		PathPropertySatisfied other = (PathPropertySatisfied) obj;
		if (_time != other._time) {
			return false;
		}
		if (path() == null) {
			if (other.path() != null) {
				return false;
			}
		} else if (!path().equals(other.path())) {
			return false;
		}
		if (condition() == null) {
			if (other.condition() != null) {
				return false;
			}
		} else if (!condition().equals(other.condition())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return path().toString() + "--" 
		+ condition().toFormattedString() 
		+ "@" + _time;
	}
	
}
