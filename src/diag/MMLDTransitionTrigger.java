package diag;

import edu.supercom.util.sat.VariableSemantics;
import lang.MMLDTransition;


/**
 * An <b>MMLDTransitionTrigger</b> object is a variable semantics 
 * that is true if a specified {@link MMLDTransition} 
 * is triggered on the system at a specified time.  
 * 
 * @author Alban Grastien
 * */
public class MMLDTransitionTrigger extends DatedSemantic<MMLDTransition> 
implements VariableSemantics {
	
	public MMLDTransitionTrigger(MMLDTransition trans, int time) {
		super(time,trans);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _time;
		result = prime * result + ((_core == null) ? 0 : _core.hashCode());
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
		if (!(obj instanceof MMLDTransitionTrigger)) {
			return false;
		}
		MMLDTransitionTrigger other = (MMLDTransitionTrigger) obj;
		if (_time != other._time) {
			return false;
		}
		if (_core == null) {
			if (other._core != null) {
				return false;
			}
		} else if (!_core.equals(other._core)) {
			return false;
		}
		return true;
	}

	@Override
	public MMLDTransition getCore() {
		return (MMLDTransition)super.getCore();
	}
	
	@Override
	public String toString() {
		return getCore().toFormattedString() + "@" + _time;
	}

}
