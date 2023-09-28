package edu.supercom.util.sat;

/**
 * A literal shifter is an object that shifts some literal by a specified value.  
 * */
public class LiteralShifter {

	/**
	 * The minimum value the variable should be to be shifted.  (inclusive)
	 * */
	private final int _min;  
	
	/**
	 * The maximum value the variable should be to be shifted.  (exclusive)
	 * */
	private final int _max;
	
	/**
	 * The shift.  
	 * */
	private final int _shift;
	
	/**
	 * Builds a literal shift that will shift all the literal 
	 * by the specified value if the variable corresponding to the literal 
	 * is within the specified range.  
	 * 
	 * @param min the minimum value of the variable for the shift to take place 
	 * (inclusive).  
	 * @param max the maximum value of the variable for the shift to take place 
	 * (exclusive).  
	 * @param shift the value of the shift.  
	 * */
	public LiteralShifter(int min, int max, int shift) {
		_min = min;
		_max = max;
		_shift = shift;
	}
	
	/**
	 * Returns a literal corresponding to the specified literal with the shift 
	 * this shifter was defined for if the variable of the specified literal 
	 * is in the specified range of this shifter, and returns the literal 
	 * otherwise.  
	 * 
	 * @param lit the literal that is being renamed.  
	 * @return a literal corresponding to the conditional shift of <code>lit</code> 
	 * defined by this shifter.  
	 * */
	public int renameLiteral(int lit) {
		final int var = Math.abs(lit);
		if (var >= _max || var < _min) {
			return lit;
		}
		final int newVar = var + _shift;
		if (newVar <= 0) {
			throw new IllegalStateException("Generating a variable with incorrect value: " + newVar);
		}
		final int newLit = (lit > 0)? newVar : -newVar;
		return newLit;
	}
	
}
