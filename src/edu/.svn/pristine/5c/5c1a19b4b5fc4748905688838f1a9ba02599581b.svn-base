/*
 * Pair.java
 *
 * Created on 26 March 2008, 16:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util;

/**
 * A simple class to manipulate a pair of elements.  Note that a pair is not 
 * immutable and may not be safe in a set or a map.
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 * @todo: make it iterable (the iterator cannot remove elements though).  
 */
public class Pair<C1,C2> implements Comparable<Pair<C1,C2>> {
    
    /** 
     * Creates a pair from the two specified elements.
     * 
     * @param e1 the first element.
     * @param e2 the second element.
     */
    public Pair(C1 e1, C2 e2) {
        _a = e1;
        _b = e2;
    }
    
    /**
     * Static constructor.  
     * 
     * @param e1 the first element of the pair.  
     * @param e2 the second element of the pair.  
     * @param <C1> the type of the first element.  
     * @param <C2> the type of the second element.  
     */
    public static <C1,C2> Pair<C1,C2> newPair(C1 e1, C2 e2) {
        return new Pair<C1,C2>(e1,e2);
    }
    
    /**
     * Returns a hashcode for this pair.  This method calls the 
     * <code>hashcode()</code> method of each object each time it is called.
     *
     * @return a hashcode value for this pair.
     */
    @Override
	public int hashCode() {
		final int prime = 1023;
		int result = 1;
		result = prime * result + ((_a == null) ? 0 : _a.hashCode());
		result = prime * result + ((_b == null) ? 0 : _b.hashCode());
		return result;
	}
    
    /**
     * Indicates whether the specified object equals this pair.  To equal this 
     * pair, the object must be a pair such that its elements equal the elements 
     * of this pair.
     *
     * @param o the object to compare to this pair.
     * @return <code>true</code> if <code>o</code> equals <code>this</code>.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair<?,?>)) {
            return false;
        }
        final Pair<?,?> pair = (Pair<?,?>)o;
        
        // Testing a
        if (this._a != pair._a) {
            if (this._a == null) {
                return false; // since pair._a != this._a
            }
            if (pair._a == null) {
                return false; // since pair._a != this._a
            }
            if (!this._a.equals(pair._a)) {
                return false;
            }
        }
        
        // Testing b
        if (this._b != pair._b) {
            if (this._b == null) {
                return false; // since pair._b != this._b
            }
            if (pair._b == null) {
                return false; // since pair._b != this._b
            }
            if (!this._b.equals(pair._b)) {
                return false;
            }
        }
        
        return true;
    }
    
    /** 
     * Compares this pair to the specified object.  The result is the comparison 
     * of the first elements of both pairs or, if the first elements are equal, 
     * the second elements.
     *
     * @param pair the object to compare to this.
     * @return a negative integer, zero, or a positive integer as this object 
     * is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if <code>o</code> is not a pair, or if the 
     * objects of the pair cannot be compared, or if their comparison throws an 
     * exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(Pair<C1,C2> pair) {
        int result = ((Comparable<C1>)_a).compareTo(pair._a);
        if (result == 0) {
            result = ((Comparable<C2>)_b).compareTo(pair._b);
        }
        
        return result;
    }

	/**
     * Access to the first element of this pair.
     *
     * @return the first element of this pair.
     */
    public C1 first() {
        return _a;
    }
    
    /**
     * Access to the second element of this pair.
     *
     * @return the second element of this pair.
     */
    public C2 second() {
        return _b;
    }
    
    /**
     * Changes the first element of the pair.
     *
     * @param e the new first element.
     */
    public void setFirst(C1 e) {
        _a = e;
    }
    
    /**
     * Changes the second element of the pair.
     *
     * @param e the new second element.
     */
    public void setSecond(C2 e) {
        _b = e;
    }

    /**
     * Returns a string representation of this pair.  The pair is represented 
     * by <i>&lt;e1,e2&gt;</i> where <i>e1</i> and <i>e2</i> are the string 
     * representations of the first and the second element respectively.
     *
     * @return a string representation of this pair.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        
        buf.append("<");
        if (_a != null) {
        	buf.append(_a.toString());
        } else {
        	buf.append("null");
        }
        buf.append(",");
        if (_b != null) {
        	buf.append(_b.toString());
        } else {
        	buf.append("null");
        }
        buf.append(">");
        
        return buf.toString();
    }
    
    /**
     * The first element of the pair.
     */
    private C1 _a;
    
    /**
     * The second element of the pair.
     */
    private C2 _b;

	/**
	 * Returns the hash code value of the specified pair of elements.
	 *
	 * @param p the pair.
	 * @return a hash code value of <code>p</code>.
	 * @see Pair#hashCode()
	 * */
	public static int hashCode(Object e1, Object e2) {
            final int x = (e1 == null)? 0 : e1.hashCode();
            final int y = (e2 == null)? 0 : e2.hashCode();
            final int sum = x+y;

            return ((sum + (sum+1)) >> 1) + x;
	}
    
}
