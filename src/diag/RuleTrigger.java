package diag;

import lang.MMLDRule;
import edu.supercom.util.sat.VariableSemantics;

/**
 * A <b>RuleTrigger</b> object is a variable semantics that is true if a specified rule 
 * is triggered on the system at a specified time.  
 * 
 * @author Alban Grastien
 * */
public class RuleTrigger extends DatedSemantic<MMLDRule> implements VariableSemantics{

	/**
	 * Builds a variable semantics that is true 
	 * if the specified rule is triggered at specified time.  
	 * 
	 * @param time the time when the rule should be triggered.  
	 * @param r the rule that should be triggered.  
	 * */
	public RuleTrigger(int time, MMLDRule rule) {
		super(time, rule);
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
		if (!(obj instanceof RuleTrigger)) {
			return false;
		}
		RuleTrigger other = (RuleTrigger) obj;
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
	public MMLDRule getCore() {
		return (MMLDRule)super.getCore();
	}
	
	@Override
	public String toString() {
		return getCore().getName() + "@" + _time;
	}

}
