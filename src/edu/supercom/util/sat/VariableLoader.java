package edu.supercom.util.sat;

/**
 * This class is used to allocate propositional variables.  A
 * propositional variable is simply an integer value and the boolean
 * negation of a propositional value is the negation of the integer
 * value of the propositional value.  Remember that the maximum value
 * of an integer is 2<sup>31</sup> (but no SAT problem should have
 * more than several millions of propositional variables).<p />
 * 
 * When a <code>VariableAssigner</code> wants to assign <i>n</i>
 * variables, then it simply needs to call the
 * <code>allocate(n)</code> method which returns the number of the
 * first variable allocated.  The <i>i</i>th variable allocated is the
 * propositional variable <code>allocate(n) + i - 1</code>.<p />
 *
 * Obviously, a SAT problem can be created only with propositional
 * variables allocated by a <b>single</b> variable loader.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @see VariableAssigner
 * @since 1.0
 */
public class VariableLoader {

	// TODO: By default, allocate a variable that is always true and a variable that is always false.
	
    /** Int value that corresponds to the number of variables already
     * allocated.
     */
    private int _n;

    /**
     * Builds a variable loader.
     */
    public VariableLoader() {
        _n = 0;
    }

    /**
     * Make a copy of the specified variable loader.  Note that allocations on a 
     * variable loader and its copy can create conflicts.
     *
     * @param loader the loader whose copy is required.
     */
    public VariableLoader(VariableLoader loader) {
        _n = loader._n;
    }

    /**
     * Returns the number of propositional variables already
     * allocated.
     *
     * @return the number of variables allocated.
     */
    public int numberOfVariables() {
        return _n;
    }

    /**
     * Allocates the specified number of propositional variables.  The
     * variables are allocated by block-booking: if the first variable
     * has number <i>k</i>, then the next one has number <i>k+1</i>,
     * <i>etc</i>.
     *
     * @param n the number of variables allocated.
     * @return the number of the first variable allocated.  The
     * <i>i</i>th variable allocated can be computed by adding
     * <i>i-1</i> to the result given by the method.
     * @throws IllegalArgumentException if <code>n < 0</code>.
     */
    synchronized public int allocate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Must allocate a positiv number of variables.");
        }
        int result = _n + 1;
        _n += n;
        return result;
    }
}