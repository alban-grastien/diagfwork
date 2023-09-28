package diag;

import lang.YAMLDGenericVar;
import lang.YAMLDValue;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.VariableSemantics;

public class Assignment extends DatedSemantic<Pair<YAMLDGenericVar,YAMLDValue>> 
implements VariableSemantics {

	/**
	 * Builds an assignment that corresponds to the assignment 
	 * of the specified variable to the specified value 
	 * at the specified time.  
	 * */
	public Assignment(YAMLDGenericVar var, YAMLDValue val, int time) {
		super(time, new Pair<YAMLDGenericVar,YAMLDValue>(var,val));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _time;
		result = prime * result + ((val() == null) ? 0 : val().hashCode());
		result = prime * result + ((var() == null) ? 0 : var().hashCode());
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
		if (!(obj instanceof Assignment)) {
			return false;
		}
		Assignment other = (Assignment) obj;
		if (_time != other._time) {
			return false;
		}
		if (val() == null) {
			if (other.val() != null) {
				return false;
			}
		} else if (!val().equals(other.val())) {
			return false;
		}
		if (var() == null) {
			if (other.var() != null) {
				return false;
			}
		} else if (!var().equals(other.var())) {
			return false;
		}
		return true;
	}
	
	@Override
	public Pair<YAMLDGenericVar,YAMLDValue> getCore() {
		return (Pair<YAMLDGenericVar,YAMLDValue>)super.getCore();
	}
	
	public YAMLDGenericVar var() {
		return getCore().first();
	}
	
	public YAMLDValue val() {
		return getCore().second();
	}
	
	public String toString() {
		return var().toFormattedString() 
			+ " = " + val().toFormattedString() 
			+ "@" + _time;
	}
}
