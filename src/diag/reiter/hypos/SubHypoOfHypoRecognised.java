package diag.reiter.hypos;

import diag.reiter.Hypothesis;
import edu.supercom.util.sat.VariableSemantics;

/**
 * A {@link SubHypoOfHypoRecognised} object models the semantics of a SAT variable 
 * that is true if on only if the trajectory from time step 0 to specified time step 
 * corresponds to a sub hypothesis of the specified hypothesis.   
 * */
public class SubHypoOfHypoRecognised<H extends Hypothesis> 
implements VariableSemantics {

	/**
	 * The time step until which the trajectory is considered.  
	 * */
	public final int _t;
	/**
	 * The hypothesis.  
	 * */
	public final H _h;
	
	public SubHypoOfHypoRecognised(H h, int t) {
		_t = t;
		_h = h;
	}

	@Override
	public int hashCode() {
		final int prime = 1023;
		int result = 1;
		result = prime * result + ((_h == null) ? 0 : _h.hashCode());
		result = prime * result + _t;
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
		if (!(obj instanceof SubHypoOfHypoRecognised<?>)) {
			return false;
		}
		final SubHypoOfHypoRecognised<?> other = (SubHypoOfHypoRecognised<?>) obj;
		if (_h == null) {
			if (other._h != null) {
				return false;
			}
		} else if (!_h.equals(other._h)) {
			return false;
		}
		if (_t != other._t) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SubHypoOfHypoRecognised [_h=" + _h + ", _t=" + _t + "]";
	}
	
}
