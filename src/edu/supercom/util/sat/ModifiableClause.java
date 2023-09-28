/*
 * ModifiableClause.java
 *
 * Created on 4 January 2008, 17:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

/**
 * A modifiable clause is a clause that is mutable.  
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 */
public class ModifiableClause extends Clause {
    
    /** Creates a new instance of ModifiableClause */
    public ModifiableClause() {
        _nb = 0;
        _literals = new int[1];
    }
    
    /**
     * Empties the set of literals in this clause.
     */
    public void clear() {
        _nb = 0;
    }
    
    /**
     * Adds the specified literal to this clause.
     *
     * @param lit the literal to add to this clause.
     */
    public void add(int lit) {
        if (_nb >= _literals.length) {
            increaseCapacityAndKeep(_nb); // Doubles the capacity.  Should be enough.
        }
        doAdd(lit);
    }
    
    /**
     * Adds the specified literal to this clause without regard to its capacity.
     *
     * @param lit the literal to add.
     * @exception IndexOutOfBoundsException if the capacity is not enough.
     */
    private void doAdd(int lit) {
        _literals[_nb] = lit;
        _nb++;
    }
    
    /**
     * Returns the number of literals in this clause.
     *
     * @return the size of this clause.
     */
    @Override
    public int size() {
        return _nb;
    }
    
    /**
     * Returns the <i>i</i>th literal of this clause.
     *
     * @param i the number of the literal (starting from 0).
     * @return the <i>i</i>th literal of this clause.
     * @exception IndexOutOfBoundsException if <code>i < 0</code> or 
     * <code>i >= size()</code>.
     */
    @Override
    public int literal(int i) {
        if (i >= _nb) {
            throw new IndexOutOfBoundsException();
        }
        return _literals[i];
    }
    
    /**
     * Increases the capacity of this clause.  The literals in the clause are 
     * loss.
     *
     * @param plus the capacity that is added to this clause.
     */
    private void increaseCapacity(int plus) {
        //assert plus >= 0;
        _literals = new int[_literals.length + plus];
    }
    
    /**
     * Increases the capacity of this clause while keeping the existing literals 
     * of the clause.
     *
     * @param plus the capacity that is added to this clause.
     */
    private void increaseCapacityAndKeep(int plus) {
        //assert plus >= 0;
        int[] old = _literals;
        _literals = new int[_literals.length + plus];
        for (int i=0 ; i<old.length ; i++) {
            _literals[i] = old[i];
        }
    }
    
    /**
     * Returns a copy of the set of literals
     *
     * @return a copy of the set of literals
     */
    @Override
    public int[] literals() {
        int[] literals = new int [_nb];
        for (int i=0 ; i<_nb ; i++){
            literals[i] = _literals[i];
        }
        return literals;
    }
    
    /**
     * Returns whether the clause is trivially true.  A clause is trivially
     * true if it contains a literal <code>l</code> and its contrary
     * <code>-l</code>.
     *
     * @return true if the clause is obviously true.
     */
    @Override
    public boolean isTrue() {
        for (int i=0 ; i<_nb ; i++) {
            for (int j=i+1 ; j<_nb ; j++) {
                if (this._literals[i] == -this._literals[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Returns a string representation of this clause.
     *
     * @return <code>lit1 -tab- lit2 -tab- ...</code>
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i=0 ; i<_nb ; i++) {
            result.append(_literals[i]).append("\t");
        }
        return result.append("0").toString();
    }
    
    /**
     * The array of literals.
     */
    private int [] _literals;
    
    /**
     * The number of literals actually used on this clause.
     */
    private int _nb;
}
