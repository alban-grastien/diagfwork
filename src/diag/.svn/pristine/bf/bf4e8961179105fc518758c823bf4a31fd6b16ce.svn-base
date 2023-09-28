package diag.reiter;

import java.util.Collection;
import java.util.Iterator;

import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;

public class Diagnosis<H extends Hypothesis> implements Iterable<Candidate<H>> {

	/**
	 * The set of candidates in the diagnosis.  
	 * */
	private final UnmodifiableList<Candidate<H>> _cand;
	
	public Diagnosis(Collection<? extends Candidate<H>> cands) {
		_cand = new UnmodifiableListConstructor<Candidate<H>>(cands).getList();
	}
	
	public Diagnosis(UnmodifiableList<Candidate<H>> cands) {
		_cand = cands;
	}

	@Override
	public Iterator<Candidate<H>> iterator() {
		return _cand.iterator();
	}
	
	/**
	 * Returns the list of candidates in this diagnosis.
	 * 
	 * @return the list of candidates in this diagnosis.  
	 * */
	public UnmodifiableList<Candidate<H>> candidates() {
		return _cand;
	}
}
