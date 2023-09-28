package diag.symb.vars;

import edu.supercom.util.Pair;
import lang.YAMLDValue;
import lang.YAMLDVar;

/**
 * A <code>Assignment</code>, i.e., an assignment, 
 * is a symbolic variable that represents the assignment 
 * of the specified variable to the specified value 
 * in either the current state or the next state.  
 * 
 * @author Alban Grastien
 */
public class Assignment implements SymbolicVariable {
    
    /**
     * The current state variable that is assigned.  
     */
    private final YAMLDVar _var;
    
    /**
     * The value that the state variable is assigned to.  
     */
    private final YAMLDValue _val;
    
    /**
     * A flag that indicates whether the assignment is in the current state 
     * (<i>true</i>) or in the next state (<i>false</i>).  
     */
    private final boolean _isCurrent;
    
    private Assignment(YAMLDVar var, YAMLDValue val, boolean isCurrent) {
        _var = var;
        _val = val;
        _isCurrent = isCurrent;
    }
    
    /**
     * Builds the assignment that indicates that the specified variable 
     * is assigned to the specified value at the specified time.  
     * 
     * @param var the variable that is assigned.  
     * @param val the value that is assigned.  
     * @param isCurrent the time when the assignment is assumed: 
     * <ul>
     * <li><tt>true</tt> if in the current state
     * </li>
     * <li><tt>false</tt> if in the next state
     * </li>
     * </ul>
     * @return the assignment of <tt>var</tt> to <tt>val</tt> 
     * in the current of next state.  
     */
    public static Assignment assignment(YAMLDVar var, YAMLDValue val, boolean isCurrent) {
        return new Assignment(var, val, isCurrent);
    }
    
    /**
     * Builds the assignment that indicates that the specified variable 
     * is assigned to the specified value in the current state.  
     * 
     * @param var the variable that is assigned.  
     * @param val the value that is assigned.  
     * @return the assignment of <tt>var</tt> to <tt>val</tt> 
     * in the current state.  
     */
    public static Assignment assignmentInCurrentState(YAMLDVar var, YAMLDValue val) {
        return new Assignment(var, val, true);
    }
    
    /**
     * Builds the assignment that indicates that the specified variable 
     * is assigned to the specified value in the next state.  
     * 
     * @param var the variable that is assigned.  
     * @param val the value that is assigned.  
     * @return the assignment of <tt>var</tt> to <tt>val</tt> 
     * in the next state.  
     */
    public static Assignment assignmentInNextState(YAMLDVar var, YAMLDValue val) {
        return new Assignment(var, val, false);
    }
    
    /**
     * Returns the variable that is assigned.  
     * 
     * @return the assigned variable.  
     */
    public YAMLDVar getVariable() {
        return _var;
    }
    
    /**
     * Returns the value that the variable is assigned to.  
     * 
     * @return the assignment value.  
     */
    public YAMLDValue getValue() {
        return _val;
    }
    
    /**
     * Returns whether the assignment is done on the current of the next state.  
     * 
     * @return <tt>true</tt> if this assignment pertains to the current state, 
     * <tt>false</tt> otherwise.  
     */
    public boolean getTime() {
        return _isCurrent;
    }
    
    @Override
    public int hashCode() {
        final int h1 = Pair.hashCode(_var, _val);
        return Pair.hashCode(h1, _isCurrent);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        final Assignment a = (Assignment)o;
        
        if (_isCurrent != a._isCurrent) {
            return false;
        }
        
        if (_var != a._var) {
            return false;
        }
        
        if (_val != a._val) {
            return false;
        }
        
        return true;
    }
}
