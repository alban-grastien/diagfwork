package diag.reiter;

/**
 * A hypothesis subspace is a subspace of a hypothesis space 
 * that is defined wrt a single hypothesis.  
 * Given a hypothesis <code>h</code>, 
 * define <code>H</code> as the set of hypotheses <code>h'</code> such that  
 * <ul>
 * <li>
 *   <code>h = h'</code> ({@link HypothesisSubspaceType#HYPO}), 
 * </li>
 * <li>
 *   <code>h'<code> covers <code>h</code> ({link {@link HypothesisSubspaceType#SUPER}), 
 *   or  
 * </li>
 * <li>
 *   <code>h</code> covers <code>h'</code>  ({link {@link HypothesisSubspaceType#SUB}).  
 * </li>
 * </ul>
 * The subspace defined by an instance of this class is : 
 * <ul>
 * <li>
 *   <code>H</code> ({@link SearchType#INCLUDE} or {@link SearchType#CONFLICT}), 
 *   or
 * </li>
 * <li>
 *   all hypotheses but <code>H</code> ({@link SearchType#EXCLUDE}).  
 * </li>
 * Furthermore, if the option is {@link SearchType#CONFLICT}, 
 * a conflict on the hypothesis should be returned if no solution could be found.  
 * */
public class HypothesisSubspace<H extends Hypothesis> {
	/**
	 * A HypothesisSubspaceType defines the type of hypothesis subspace.    
	 * <ul>
	 * <li>
	 * HypothesisSubspaceType.HYPO + h: { h }
	 * </li>
	 * <li>
	 * HypothesisSubspaceType.SUPER + h: { h' | h' <= h }
	 * </li>
	 * <li>
	 * HypothesisSubspaceType.HYPO + h: { h' | h <= h' }
	 * </li>
	 * </ul>
	 * */
	public enum HypothesisSubspaceType { HYPO, SUPER, SUB };
	
	/**
	 * A search type indicates whether the HSS is included or excluded from the search.  
	 * CONFLICT represents the fact that the search is inclusive, 
	 * but that the procedure should return a conflict if the search fails.  
	 * */
	public enum SearchType { INCLUDE, EXCLUDE, CONFLICT };

	/**
	 * The hypothesis this sub space is defined with respect to.  
	 * */
	public final H _h;
	
	/**
	 * The type of subspace.  
	 * */
	public final HypothesisSubspaceType _t;
	
	/**
	 * The search type.  
	 * */
	public final SearchType _st;
	
	/**
	 * Builds a hypothesis sub space defined as the set of hypotheses 
	 * sharing the specified relation with the specified hypothesis.  
	 * 
	 * @param h the hypothesis.  
	 * @param t the type of hypothesis subspace.  
	 * @param st the type of search.  
	 * */
	public HypothesisSubspace(H h, HypothesisSubspaceType t, SearchType st) {
		_h = h;
		_t = t;
		_st = st;
	}

	public H getHypothesis() {
		return _h;
	}

	public HypothesisSubspaceType getType() {
		return _t;
	}

	public SearchType getSearchType() {
		return _st;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_h == null) ? 0 : _h.hashCode());
		result = prime * result + ((_st == null) ? 0 : _st.hashCode());
		result = prime * result + ((_t == null) ? 0 : _t.hashCode());
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
		if (!(obj instanceof HypothesisSubspace<?>)) {
			return false;
		}
		HypothesisSubspace<?> other = (HypothesisSubspace<?>) obj;
		if (_h == null) {
			if (other._h != null) {
				return false;
			}
		} else if (!_h.equals(other._h)) {
			return false;
		}
		if (_st == null) {
			if (other._st != null) {
				return false;
			}
		} else if (!_st.equals(other._st)) {
			return false;
		}
		if (_t == null) {
			if (other._t != null) {
				return false;
			}
		} else if (!_t.equals(other._t)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "HypothesisSubspace [_h=" + _h + ", _st=" + _st + ", _t=" + _t
				+ "]";
	}
	
}
