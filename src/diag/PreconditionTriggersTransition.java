package diag;

import lang.MMLDTransition;
import lang.YAMLDFormula;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.VariableSemantics;

/**
 * This variable semantics is associated to true 
 * iff the specified precondition triggers the specified transition 
 * at the specified timestep.  
 * */
public class PreconditionTriggersTransition extends DatedSemantic<Pair<MMLDTransition,YAMLDFormula>> 
implements VariableSemantics {

	/**
	 * Builds a variable semantics that is true 
	 * iff the specified precondition triggers the specified transition 
	 * at the specified timestep.  
	 * */
	public PreconditionTriggersTransition(final MMLDTransition trans, 
			YAMLDFormula prec, int t) {
		super(t, new Pair<MMLDTransition, YAMLDFormula>(trans, prec));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_core == null) ? 0 : _core.hashCode());
		result = prime * result + _time;
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
		if (!(obj instanceof PreconditionTriggersTransition)) {
			return false;
		}
		PreconditionTriggersTransition other = (PreconditionTriggersTransition) obj;
		if (_core == null) {
			if (other._core != null) {
				return false;
			}
		} else if (!_core.equals(other._core)) {
			return false;
		}
		if (_time != other._time) {
			return false;
		}
		return true;
	}
	
	@Override 
	public String toString() {
		return _core.first().getName() + " triggered by " 
		+ _core.second().toFormattedString() + "@" + _time;
	}
}
