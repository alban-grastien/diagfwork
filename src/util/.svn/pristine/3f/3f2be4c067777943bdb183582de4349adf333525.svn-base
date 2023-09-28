package util;

import edu.supercom.util.Pair;
import lang.PeriodInterval;

/**
 * A <code>TimeIntervale</code>, i.e., a time intervale, 
 * is a continuous set of times 
 * defined through two bounds.  
 */
public final class TimeIntervale {
    
    /**
     * The minimal time in this intervale.  
     */
    private final Time _min;
    
    /**
     * The maximal time in this intervale.  
     */
    private final Time _max;
    
    /**
     * Builds a time intervale defined as the elapse 
     * of the specified period intervale 
     * after the specified time.  
     * 
     * @param t the time.  
     * @param pi the period intervale that elapses after <code>t</code>.  
     */
    public TimeIntervale(Time t, PeriodInterval pi) {
        _min = new Time(t,pi.getBeginning());
        _max = new Time(t,pi.getEnd());
    }
    
    /**
     * Returns the beginning of this time intervale.  
     * 
     * @return the time at the beginning of this intervale.  
     */
    public Time getBeginning() {
        return _min;
    }
    
    /**
     * Returns the end of this time intervale.  
     * 
     * @return the time at the end of this intervale.  
     */
    public Time getEnd() {
        return _max;
    }
    
    @Override
    public int hashCode() {
        return Pair.hashCode(_min, _max);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof TimeIntervale)) {
            return false;
        }
        
        final TimeIntervale other = (TimeIntervale)o;
        
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
        return "[" + getBeginning().toString() + "," + getEnd().toString() + "]";
    }
}
