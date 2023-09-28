package lang;

import edu.supercom.util.Pair;

/**
 * A period intervale is a continuous set of periods 
 * defined through two bounds.  
 * 
 * @author Alban Grastien
 * */
public final class PeriodInterval {

	/**
	 * The beginning of the period interval.  
	 * */
	private final Period _min;
	
	/**
	 * The end of the period interval.  
	 * */
	private final Period _max;
	
	/**
	 * The instantaneous period interval.  
	 * */
	public static final PeriodInterval INSTANTANEOUS = 
            new PeriodInterval(Period.ZERO_PERIOD, Period.ZERO_PERIOD);
	
	/**
	 * Builds a period interval with specified beginning and end.  
	 * 
	 * @param b the beginning of the period interval.  
	 * @param e the end of period interval.  
     * @throws IllegalArgumentException if period <code>b</code> is strictly 
     * stronger than period <code>e</code>.
	 * */
	public PeriodInterval(Period b, Period e) {
        if (b.period() > e.period()) {
			throw new IllegalArgumentException(
					"Cannot build an interval with the end before the beginning ("
					+ b + " > " + e + ")");
        }
		_min = b;
		_max = e;
	}
	
	/**
	 * Returns the beginning of the period interval.  
	 * 
	 * @return the period at which the intervale starts.  
	 * */
	public Period getBeginning() {
		return _min;
	}
	
	/**
	 * Returns the end of the period interval.  
	 * 
	 * @return the period at which the intervale ends.  
	 * */
	public Period getEnd() {
		return _max;
	}
	
	/**
	 * Indicates whether the specified period is in the period interval.  
	 * 
	 * @param p the period.  
	 * @return <code>true</code> if <code>p &lt;= {@link #getEnd()}</code> 
	 * and <code>p &gt;= {@link #getBeginning()}</code>.  
	 * */
	public boolean contains(Period p) {
        if (p.period() < _min.period()) {
            return false;
        }
        if (p.period() > _max.period()) {
            return false;
        }
        return true;
	}

	@Override
	public int hashCode() {
        return Pair.hashCode(_min, _max);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PeriodInterval)) {
			return false;
		}
		final PeriodInterval other = (PeriodInterval) obj;
		
        if (!_min.equals(other._min)) {
            return false;
        }
        if (!_max.equals(other._max)) {
            return false;
        }
        
		return true;
	}
	
	@Override
	public String toString() {
		return "[" + _min + " .. " + _max + "]";
	}
}
