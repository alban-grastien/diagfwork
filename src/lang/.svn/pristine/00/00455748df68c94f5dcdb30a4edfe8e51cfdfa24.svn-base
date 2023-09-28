package lang;

import edu.supercom.util.PseudoRandom;
import util.Time;

/**
 * A <code>Period</code>, i.e., a period, 
 * is an amount of time.  
 * It is generally used to represent time intervale.  
 * <p />
 * The reason we encapsulate an amount of time in a period 
 * is to distinguish a period and a time.  
 */
public final class Period {
    /**
     * The period (in seconds).  
     */
    private final double _period;
    
    /**
     * The period that corresponds to no period.  
     * This period should not be mistaken for the ZERO_PERIOD.  
     * (The ZERO_PERIOD does exist, while the NO_PERIOD is just empty.)
     */
    public static final Period NO_PERIOD = new Period();
    
    /**
     * The period of 0 second.  
     */
    public static final Period ZERO_PERIOD = new Period(0);
    
    /**
     * The maximum period of time.  
     */
    public static final Period MAX_PERIOD = new Period(Double.MAX_VALUE);
    
    /**
     * Builds a period defined as the specified amount of time.  
     * 
     * @param p the amount of time (in seconds).  
     * @throws IllegalArgumentException if <code>p < 0</code>. 
     */
    public Period(double p) {
        if (p < 0) {
            throw new IllegalArgumentException("Negative period: " + p);
        }
        _period = p;
    }
    
    // Used for the no period.
    private Period() {
        _period = -1;
    }
    
    /**
     * Builds a period defined as the addition of the two specified periods.  
     * 
     * @param p1 the first period.  
     * @param p2 the second period.  
     */
    private Period(Period p1, Period p2) {
        _period = p1.period() + p2.period();
    }
    
    /**
     * Define a period as the difference between the two specified times.  
     * 
     * @param begin the beginning of the period.  
     * @param end the end of the period.  
     */
    public Period(Time t1, Time t2) {
        if (!t1.isBefore(t2)) {
            throw new IllegalArgumentException("Trying to create a period between "
                    + t1 + " and " + t2);
        }
        _period = t2.time() - t1.time();
    }
    
    /////////////////////////////////////////////////////////////
    // Operations
    
    /**
     * Returns the period corresponding to the addition of the specified periods.  
     * 
     * @param ps the list of period to add.  
     * @return the period corresponding to the addition of all periods 
     * in <code>ps</code>.  
     */
    public static Period add(Period... ps) {
        Period result = ZERO_PERIOD;
        for (final Period p: ps) {
//            if (p._period == 0) {
//                continue;
//            }
//            if (result == ZERO_PERIOD) {
//                result = p;
//                continue;
//            }
            if (p == NO_PERIOD) {
                return NO_PERIOD;
            }
            result = new Period(result, p);
        }
        return result;
    }
    
    /**
     * Returns the period corresponding to the subtraction 
     * of the first specified period from the second specified period.  
     * 
     * @param p1 the period from which the subtraction is applied.  
     * @param p2 the period subtracted from <code>p1</code>.  
     * @return the period corresponding to the subtraction of <code>p2</code>
     * from <code>p1</code> if existing, 
     * <code>null</code> if <code>p2</code> is longer than <code>p1</code>.  
     */
    public static Period subtract(Period p1, Period p2) {
        if (p1 == NO_PERIOD || p2 == NO_PERIOD) {
            return NO_PERIOD;
        }
        
        final double d1 = p1._period;
        final double d2 = p2._period;
        if (d2 > d1) {
            return null;
        }
        return new Period(d1 - d2);
    }
    
    /**
     * Indicates whether the this period is longer than the specified period.  
     * 
     * @param other the period this period is compared to.  
     * @return <code>true</code> if <code>this</code> is longer than 
     * (or equally long as) <code>other</code>.  
     */
    public boolean isLonger(Period other) {
        return (this._period >= other._period);
    }
    
    /**
     * Returns the longest of the specified list of periods.  
     * 
     * @param ps the list of periods.  
     * @return the maximum period from <code>ps</code>.  
     */
    public static Period longest(Period... ps) {
        Period result = NO_PERIOD;
        for (final Period p: ps) {
            if (p.isLonger(result)) {
                result = p;
            }
        }
        return result;
    }
    
    /**
     * Returns the shortest of the specified list of periods.  
     * 
     * @param ps the list of periods.  
     * @return the shortest period from <code>ps</code>.  
     */
    public static Period shortest(Period... ps) {
        Period result = MAX_PERIOD;
        for (final Period p: ps) {
            if (result.isLonger(p)) {
                result = p;
            }
        }
        return result;
    }

    /**
     * Returns a random period between the two specified periods.  
     * 
     * @param rand the random generator.  
     * @param p1 the first period.  
     * @param p2 the second period.  
     * @return a random value between <code>p1</code> and <code>p2</code>.  
     */
    public static Period getRandom(PseudoRandom rand, Period p1, Period p2) {
        final double diff = p2._period - p1._period;
        final double val = diff * rand.rand(1000) / 1000.0;
        return new Period(p1._period + val);
    }
    
    /////////////////////////////////////////////////////////////
    // Standard Access
    
    /**
     * Returns the amount of time corresponding to this period.  
     * 
     * @return the amount of time (in seconds).  
     */
    public double period() {
        return _period;
    }
    
    @Override
    public String toString() {
        return Double.toString(_period);
    }
    
    @Override
    public int hashCode() {
        return new Double(_period).hashCode();
    }
    
    @Override 
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof Period)) {
            return false;
        }
        
        final Period p = (Period)o;
        if (_period != p._period) {
            return false;
        }
        
        return true;
    }
}
