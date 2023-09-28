package diag.reiter.hypos;

import diag.reiter.Hypothesis;
import edu.supercom.util.sat.VariableSemantics;

/**
 * A TrajectoryOfHypothesis object models 
 * a variable semantics that is <i>true</i> 
 * if and only if the trajectory returned by the SAT problem 
 * belongs to the specified hypothesis.  
 * */
public class TrajectoryOfHypothesis<H extends Hypothesis> implements VariableSemantics {

	/**
	 * The hypothesis associated with this variable semantics.
	 * */
	public final H _h;
	
	/**
	 * Builds a variable semantics that is true 
	 * iff the trajectory encoded by the SAT assignment 
	 * belongs to the specified hypothesis.  
	 * 
	 * @param h the hypothesis.  
	 * */
	public TrajectoryOfHypothesis(H h) {
		_h = h;
	}

	@Override
	public int hashCode() {
		final int prime = 131;
		int result = 1;
		result = prime * result + ((_h == null) ? 0 : _h.hashCode());
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
		if (!(obj instanceof TrajectoryOfHypothesis<?>)) {
			return false;
		}
		final TrajectoryOfHypothesis<?> other = (TrajectoryOfHypothesis<?>) obj;
		if (_h == null) {
			if (other._h != null) {
				return false;
			}
		} else if (!_h.equals(other._h)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TrajectoryOfHypothesis [_h=" + _h + "]";
	}
}
