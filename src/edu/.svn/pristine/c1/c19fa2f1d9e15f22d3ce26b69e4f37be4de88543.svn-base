package edu.supercom.util.sat;

import java.io.PrintStream;

/**
 * This interface presents the methods a variable assigner must
 * implement.  An instance of a <code>VariableAssigner</code> is an
 * object that is able to find the propositional variables associated
 * with some <i>variable semantics</i>: the integer value is the
 * propositional variable and the variable semantics is the semantics
 * of this propositional variable.  The variable assigner must first
 * load the propositional variables in a variable loader.  A variable
 * assigner can only find the propositional variable for some variable
 * semantics.  For the translation of a single problem, different
 * assigners may be used.
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 1.0
 */
public interface VariableAssigner {

    /**
     * Gives the propositional variable associated with the specified
     * variable semantics if existing.  The method {@link
     * #surelyGetVariable(VariableSemantics)} should be used in case the assigner
     * is not allowed not to find the propositional variable.  When this method
     * returns <code>0</code>, it implicitely means that the variable (were it
     * defined) would be false.  
     *
     * @param sem the semantics of the propositional variable that is
     * required.
     * @return the propositional variable (i.e an integer) associated
     * with <code>sem</code>, 0 if the variable assigner does not
     * associate any element to <code>sem</code>.
     */
    public int getVariable(VariableSemantics sem);

    /**
     * Gives the propositional variable associated with the specified
     * variable semantics and throws an exception if the variable is
     * not found.  This method should be preferred to {@link
     * #getVariable(VariableSemantics)} if a propositional variable
     * should be found in all cases.
     *
     * @param sem the semantics of the propositional variable that is
     * required.
     * @return the propositional variable (i.e an integer) associated
     * with <code>sem</code>.
     * @throws NoSuchElementException if no proposition variable is
     * associated with <code>sem</code> in this assigner.
     */
    public int surelyGetVariable(VariableSemantics sem);

    /**
     * Prints a description of all the propositional variables that
     * can be assigned by this assigner in the specified print stream.
     * The description contains one variable per line, and starts with
     * the propositional variable.  For instance:<p />
     *
     * <pre> 1 [Semantics 1]
     * 2 [Semantics 2]
     * 3 [1 OR 2]</pre><p />
     *
     * The implementation of this method should describe the variable
     * in incremental order.
     *
     * @param out the output stream where the description is to be
     * printed.  If this output stream is <code>null</code>, nothing
     * is printed.
     */
    public void print(PrintStream out);
    
    /**
     * Copies some (possibly all, possibly none, possibly something in between) 
     * of the variables from this assigner to the specified assigner.  
     * 
     * @param map the map where the variables are to be copied.  
     * */
    public void copy(MapVariableAssigner map);

} // VariableAssigner