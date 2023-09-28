package edu.supercom.util.sat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a clause of literals, each literal being
 * represented by an integer.
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 0.5 
 */
public class Clause {

    /**
     * The literals of the clause.
     */
    private final int[] _literals;
    /**
     * The pattern (if required).
     */
    private static Pattern _pat;

    /** 
     * Builds a clause from a specified set of literals.
     *
     * @param literals the set of literals
     */
    public Clause(int[] literals) {
        _literals = new int[literals.length];
        for (int i = 0; i < literals.length; i++) {
            _literals[i] = literals[i];
        }
    }

    /** 
     * Builds a clause from a single literal.
     *
     * @param literal the unique literal
     */
    public Clause(int literal) {
        _literals = new int[1];
        _literals[0] = literal;
    }

    /** 
     * Builds the <i>false</i> clause.
     */
    public Clause() {
        _literals = new int[0];
    }

    /** 
     * Builds a clause as the disjunction between a clause
     * and a literal.
     *
     * @param clause the clause
     * @param literal the literal
     */
    public Clause(Clause clause, int literal) {
        final int[] literals = clause._literals;
        _literals = new int[literals.length + 1];
        for (int i = 0; i < literals.length; i++) {
            _literals[i] = literals[i];
        }
        _literals[literals.length] = literal;
    }

    /** 
     * Builds a clause as the disjunction between a clause
     * and the set a literals.
     *
     * @param clause the clause
     * @param literals the literals
     */
    public Clause(Clause clause, int[] literals) {
        final int[] lit = clause._literals;
        _literals = new int[lit.length + literals.length];
        for (int i = 0; i < lit.length; i++) {
            _literals[i] = lit[i];
        }
        for (int i = 0; i < literals.length; i++) {
            _literals[lit.length + i] = literals[i];
        }
    }

    /** 
     * Builds the disjunction of two clauses.
     *
     * @param d1 the first clause
     * @param d2 the second clause
     */
    public Clause(Clause d1, Clause d2) {
        final int n1 = d1._literals.length;
        final int n2 = d2._literals.length;
        _literals = new int[n1 + n2];
        for (int i = 0; i < n1; i++) {
            _literals[i] = d1._literals[i];
        }
        for (int i = 0; i < n2; i++) {
            _literals[i + n1] = d2._literals[i];
        }
    }

    /**
     * Reads a clause in the specified string line.
     *
     * @param line the line that contains the clause.  The line should follow the 
     * following pattern: \\s*(-?\\d+\\s+)*0\\s*.
     * @exception IllegalArgumentException if the line cannot be read.
     */
    public Clause(String line) {
        if (_pat == null) {
            _pat = Pattern.compile("\\s*(-?\\d+)");
        }
        final Matcher mat = _pat.matcher(line);
        final List<Integer> list = new ArrayList<Integer>();
        for (;;) {
            if (!mat.find()) {
                throw new IllegalArgumentException(line);
            }
            final int lit = Integer.parseInt(mat.group(1));
            if (lit == 0) {
                break;
            }
            list.add(lit);
        }
        _literals = new int[list.size()];
        for (int i = 0; i < _literals.length; i++) {
            _literals[i] = list.get(i).intValue();
        }
    }

    /**
     * Builds a clause from a collection of literals.
     *
     * @param coll the collection of literals.  
     */
    public Clause(Collection<Integer> coll) {
        _literals = new int[coll.size()];
        int i=0;
        for (final Integer lit: coll) {
            _literals[i] = lit;
            i++;
        }
    }

    /**
     * Returns the number of literals in this clause.
     *
     * @return the size of this clause.
     */
    public int size() {
        return _literals.length;
    }

    /**
     * Returns the <i>i</i>th literal of this clause.
     *
     * @param i the number of the literal (starting from 0).
     * @return the <i>i</i>th literal of this clause.
     * @exception IndexOutOfBoundsException if <code>i &lt; 0</code> or 
     * <code>i >= size()</code>.
     */
    public int literal(int i) {
        return _literals[i];
    }

    /** 
     * Returns a copy of the set of literals
     *
     * @return a copy of the set of literals
     */
    public int[] literals() {
        final int[] literals = new int[_literals.length];
        for (int i = 0; i < literals.length; i++) {
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
    public boolean isTrue() {
        return isTrue(_literals);
    }
    
    /**
     * Indicates whether the clause represented by the specified array is trivially true.  
     * A clause is trivially true if it contains a literal <code>l</code> 
     * and its contrary <code>-l</code>.  
     * 
     * @param arr the array of literals.  
     * @return <code>true</code> if the clause represented by <code>arr</code> 
     * is a tautology.  
     * */
    public static boolean isTrue(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == -arr[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Indicates whether the clause represented by the specified list is trivially true.  
     * A clause is trivially true if it contains a literal <code>l</code> 
     * and its contrary <code>-l</code>.  
     * 
     * @param arr the list of literals.  
     * @return <code>true</code> if the clause represented by <code>arr</code> 
     * is a tautology.  
     * */
    public static boolean isTrue(List<Integer> arr) {
    	final int max = arr.size();
        for (int i = 0; i < max; i++) {
        	final int lit1 = arr.get(i);
            for (int j = i + 1; j < max ; j++) {
            	final int lit2 = arr.get(j);
                if (lit1 == -lit2) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Indicates whether the clause represented by the specified collection is trivially true.  
     * A clause is trivially true if it contains a literal <code>l</code> 
     * and its contrary <code>-l</code>.  
     * 
     * @param arr the collection of literals.  
     * @return <code>true</code> if the clause represented by <code>arr</code> 
     * is a tautology.  
     * */
    public static boolean isTrue(Collection<Integer> arr) {
    	final int[] clause = new int[arr.size()];
    	int i=0;
    	for (final int lit: arr) {
    		clause[i] = lit;
    		i++;
    	}
    	return isTrue(clause);
    }

    /**
     * Returns a string representation of this clause.
     *
     * @return <code>lit1 -tab- lit2 -tab- ... -tab- 0</code>
     */
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < _literals.length; i++) {
            result.append(_literals[i]).append("\t");
        }
        return result.append("0").toString();
    }
}