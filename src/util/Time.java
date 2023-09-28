package util;

import edu.supercom.util.PseudoRandom;
import lang.Period;

/**
 * A <code>Time</code>, i.e., a time, 
 * is a value of the clock.  <p />
 * The time has been defined primarily to distinguish times and periods.  
 */
public final class Time {
    
    /**
     * The time.  
     */
    private final double _time;
    
    /**
     * The initial time.  
     */
    public static final Time ZERO_TIME = new Time(0);
    
    /**
     * The final time.  
     */
    public static final Time MAX_TIME = new Time(Double.MAX_VALUE);
    
    /**
     * Builds a time that corresponds to the specified clock value.  
     * Notice that the preferred way to build a time is to use 
     * 
     * @param t the time.  
     */
    public Time(double t) {
        _time = t;
    }
    
    /**
     * Builds a time defined as the elapse of the specified period 
     * after the specified time.  
     * 
     * @param t the time.  
     * @param p the period added to <code>t</code>.  
     */
    public Time(Time t, Period p) {
        _time = t._time + p.period();
    }

    /////////////////////////////////////////////////////////////
    // Operations

    /**
     * Indicates whether this time is before the specified time.  
     * 
     * @param t the time compared to this time.  
     * @return <code>true</code> if <code>this</code> is before 
     * (or at the same time as) <code>t</code>.  
     */
    public boolean isBefore(Time t) {
        return _time <= t._time;
    }

    /**
     * Picks a random value between the two specified times.  
     * 
     * @param rand the pseudo random generator.  
     * @param t1 the first time.  
     * @param t2 the second time.  
     * @return the time between <code>t1</code> and <code>t2</code>.  
     */
    public static Time getRandom(PseudoRandom rand, Time t1, Time t2) {
        final Period p = new Period(t1, t2);
        final Period randP = Period.getRandom(rand, Period.ZERO_PERIOD, p);
        return new Time(t1, randP);
    }
    
    /**
     * Removes the specified period to this time.  
     * 
     * @param p the period that is removed from this time.  
     * @return the time corresponding to <code>this</code> minus <code>p</code>.  
     */
    public Time removePeriod(Period p) {
        return new Time(_time - p.period());
    }
    
    /**
     * Returns the maximal time between the specified list of times.  
     * 
     * @param ts the list of times.  
     * @return the maximum time in <code>ts</code>.  
     */
    public static Time max(Time... ts) {
        if (ts.length == 0) {
            return ZERO_TIME;
        }
        
        Time currentMax = ts[0];
        for (final Time t: ts) {
            if (currentMax.isBefore(t)) {
                currentMax = t;
            }
        }
        return currentMax;
    }
    
    /**
     * Returns the minimal time between the specified list of times.  
     * 
     * @param ts the list of times.  
     * @return the minimum time in <code>ts</code>.  
     */
    public static Time min(Time... ts) {
        Time currentMin = MAX_TIME;
        for (final Time t: ts) {
            if (t.isBefore(currentMin)) {
                currentMin = t;
            }
        }
        return currentMin;
    }
    
    /////////////////////////////////////////////////////////////
    // Standard Access

    /**
     * Returns the time.  
     * 
     * @return the clock value at this time.  
     */
    public double time() {
        return _time;
    }
    
    @Override
    public int hashCode() {
        return new Double(_time).hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof Time)) {
            return false;
        }
        
        final Time other = (Time)o;
        
        if (_time != other._time) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return Double.toString(_time);
    }
}
