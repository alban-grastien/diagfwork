package diag.reiter;

import util.Scenario;

/**
 * A diagnosis candidate is a hypothesis that is consistent with the observations 
 * together with a scenario that proves the hypothesis indeed is.
 * 
 * @param H the type of hypothesis considered.  
 * */
public class Candidate<H extends Hypothesis> {

	/**
	 * The hypothesis of the candidate.  
	 * */
	public final H _h;
	/**
	 * The proof that <code>_h</code> is a candidate.  
	 * */
	public final Scenario _sce;
	
	/**
	 * Builds a candidate corresponding to the specified hypothesis, 
	 * with the specified proof (a scenario) that the hypothesis is a candidate.  
	 * 
	 * @param h the hypothesis.  
	 * @param s the scenario.  
	 * */
	public Candidate(H h, Scenario s) {
		_h = h;
		_sce = s;
	}
	
	/**
	 * Returns the hypothesis.  
	 * 
	 * @return the hypothesis.  
	 * */
	public final H getHypothesis() {
		return _h;
	}
	
	/**
	 * Returns the scenario. 
	 * 
	 * @return the scenario.  
	 * */
	public final Scenario getScenario() {
		return _sce;
	}

	@Override
	public String toString() {
		return "Candidate [_h=" + _h + "]";
	}
}
