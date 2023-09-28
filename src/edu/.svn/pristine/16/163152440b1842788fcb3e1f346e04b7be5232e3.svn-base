/*
 * DatedLabel.java
 *
 * Created on 15 February 2007, 17:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

/**
 * A dated label is a label (called the <i>core label</i>) which is attached a
 * date intervale.  A date intervale is a pair <i>[min,max]</i> non empty (i.e 
 * such that <i>{@link DatedLabel#MIN} < min < max < {@link DatedLabel#MAX}</i>.  
 * The synchronisation of two dated label is done through the {@link 
 * DatedSynchroniser} class.  A dated label is either a state label or a trans 
 * label.
 *
 * @author Alban Grastien
 * @version 1.0
 * @since 1.0
 */
public class DatedLabel<L> {
    
    /**
     * The minimum value of min.
     */
    public static final int MIN = 0;
    
    /**
     * The maximum value of max.
     */
    public static final int MAX = Integer.MAX_VALUE;
    
    /**
     * The core label.
     */
    private L _coreLabel;
    
    /**
     * The minimum value of the time intervale.
     */
    private int _min;
    
    /**
     * The maximum value of the time intervale.
     */
    private int _max;
    
    /** 
     * Creates a dated label.
     *
     * @param label the core label.
     * @param min the minimum value of the time intervale.
     * @param max the maximum value of the time intervale.
     */
    public DatedLabel(L label, int min, int max)
    {
        this._coreLabel = label;
        this._min = min;
        this._max = max;
    }
    
    /** 
     * Creates a dated label with the minimal and maximal values.
     *
     * @param label the core label.
     */
    public DatedLabel(L label)
    {
        this._coreLabel = label;
        this._min = DatedLabel.MIN;
        this._max = DatedLabel.MAX;
    }
    
    /**
     * Returns the minimum time of the label.
     *
     * @return the minimum time of the label.
     */
    public int getMin()
    {
        return _min;
    }
    
    /**
     * Returns the maximum time of the label.
     *
     * @return the maximum time of the label.
     */
    public int getMax()
    {
        return _max;
    }
    
    /**
     * Returns the core label.
     *
     * @return the core label.
     */
    public L getCore()
    {
        return this._coreLabel;
    }

    /**
     * Sets the minimum time of the label to a new value
     * 
     * @param min the new minimum time
     */
    public void setMin(int min) {
		this._min = min;
	}

    /**
     * Sets the maximum time of the label to a new value
     * 
     * @param max the new maximum time
     */
	public void setMax(int max) {
		this._max = max;
	}

	/**
     * Returns a hashcode value for this dated label.DatedLabel
     *
     * @return a hashcode value for this dated label.
     */
    @Override
    public int hashCode()
    {
        return _min + _max + this._coreLabel.hashCode();
    }
    
    /**
     * Returns whether this label equals the specified object.  A dated label 
     * equals another dated label if their core label are equal and the time 
     * date are identical.
     *
     * @param o the object to compare to this.
     * @return <code>true</code> if <code>o</code> <i>equals</i> this label.
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof DatedLabel) {
            DatedLabel d = (DatedLabel)o;
            if (d.getMax() != this.getMax()) {
                return false;
            }
            if (d.getMin() != this.getMin()) {
                return false;
            }
            return d.getCore().equals(this.getCore());
        }
        
        return false;
    }
    
    /**
     * Returns a string representation of this label.
     *
     * @return a string representation of this label.
     */
    @Override
    public String toString()
    {
        return this._coreLabel + "[" + this._min + "," + this._max + "]";
    }
}
