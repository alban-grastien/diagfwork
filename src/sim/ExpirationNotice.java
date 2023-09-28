package sim;

import edu.supercom.util.Pair;
import lang.MMLDTransition;
import lang.YAMLDFormula;
import util.Time;

/**
 * An expiration notice is an object 
 * that keeps a timer for each forced transition.  
 * Notice that a notice is <i>not</i> immutable.  
 * 
 * @author Alban Grastien
 * */
public interface ExpirationNotice {

	/**
	 * Indicates when the specified precondition will trigger 
	 * the specified transition (provided the precondition is not falsified before).  
	 * If the specified formula is not currently satisfied, 
	 * returns a negative value.  
	 * The result is also unspecified if the specified formula 
	 * is not a precondition of the specified transition 
	 * (and it may throw an exception).  
	 * 
	 * @param pr the precondition.  
	 * @param tr the transition.  
	 * @return the time <code>f</code> will be satisfied for long enough 
	 * so that it will trigger the transition <code>t</code>.  
	 * */
	public Time willTrigger(YAMLDFormula pr, MMLDTransition tr);
	
	/**
	 * Sets when the specified precondition of the specified transition will trigger.  
	 * Whether this assignment makes sense is not tested 
	 * (for instance, if the transition is not satisfied).  
	 * 
	 * @param pr the precondition.  
	 * @param tr the transition.  
	 * @param t  the time.  
	 * */
	public void setTrigger(YAMLDFormula pr, MMLDTransition tr, Time t);
	
	/**
	 * Returns the pair <code>(precondition,transition)</code> 
	 * that is the next one supposed to trigger in this notice.  
	 * 
	 * @return a pair such that {@link #willTrigger(YAMLDFormula, MMLDTransition)} 
	 * returns the minimal value.  
	 * */
	public Pair<YAMLDFormula, MMLDTransition> nextTrigger();
}
