package lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YAMLDComponent implements Comparable<YAMLDComponent> {
	private String _name = null;
	private ArrayList<YAMLDEvent> _events = new ArrayList<YAMLDEvent>();
	private Map<String, YAMLDEvent> _eventMap = new HashMap<String, YAMLDEvent>();
	private ArrayList<YAMLDVar> _vars = new ArrayList<YAMLDVar>();
	private ArrayList<YAMLDDVar> _dvars = new ArrayList<YAMLDDVar>();
	private ArrayList<YAMLDConnection> _conns = new ArrayList<YAMLDConnection>();
	private VisOptions _visOptions = new VisOptions();
	private final Map<String, YAMLDGenericVar> _varMap = new HashMap<String, YAMLDGenericVar>();
	private final Map<String, YAMLDComponent> _connectedComponent = new HashMap<String, YAMLDComponent>();
	
	/**
	 * The list of transitions on the component.  Set by {@link #addTransition(MMLDTransition)}, 
	 * accessed by {@link #transitions()}.  
	 * */
	private final List<MMLDTransition> _transitions;

	private int _x = -1;
	private int _y = -1;

	public YAMLDComponent() {
		_transitions = new ArrayList<MMLDTransition>();
	}
	
	public void setName(String newName) {
		_name = newName;
	}

	public String name() {
		return _name;
	}

	public void addEvent(YAMLDEvent event) {
		_events.add(event);
		_eventMap.put(event.name(), event);
	}

	public ArrayList<YAMLDEvent> events() {
		return _events;
	}

	public YAMLDEvent getEvent(String event) {
		return _eventMap.get(event);
	}

	public void addVar(YAMLDVar var) {
		_vars.add(var);
		_varMap.put(var.name(), var);
	}

	public ArrayList<YAMLDVar> vars() {
		return _vars;
	}

	public void addDVar(YAMLDDVar var) {
		_dvars.add(var);
		_varMap.put(var.name(), var);
	}

	public ArrayList<YAMLDDVar> dvars() {
		return _dvars;
	}

	/**
	 * Adds a transition to the list of transitions of this component. This
	 * method will eventually replace {@link #addTrans(YAMLDTrans)}.
	 * */
	public void addTransition(MMLDTransition trans) {
		_transitions.add(trans);
	}

	@Deprecated
	public void addTrans(YAMLDTrans newTrans) {
        throw new UnsupportedOperationException();
//		_trans.add(newTrans);
//		_eventTrans.put(newTrans.eventTrigger(), newTrans);
	}

	/**
	 * Returns the list of {@link MMLDTransition} that represent how the state
	 * of this component may change. This method will eventually replace
	 * {@link #trans()}.
	 * */
	public List<MMLDTransition> transitions() {
		//return _transitions;
		return Collections.unmodifiableList(_transitions);
	}

	@Deprecated
	public ArrayList<YAMLDTrans> trans() {
        throw new UnsupportedOperationException();
//		return _trans;
	}

	public void addConn(YAMLDConnection newConn) {
		_conns.add(newConn);
		_connectedComponent.put(newConn.name(), newConn.target());
	}

	public YAMLDComponent getConnectedComponent(String connName) {
		return _connectedComponent.get(connName);
	}

	public ArrayList<YAMLDConnection> conns() {
		return _conns;
	}

	public VisOptions visOptions() {
		return _visOptions;
	}

	/**
	 * Returns the variable in this component with specified name.
	 * 
	 * @param n
	 *            the name of the variable.
	 * @return the variable that is named <code>n</code> in this component,
	 *         <code>null</code> if no such variable exists.
	 * */
	public YAMLDGenericVar getVariable(String n) {
		return _varMap.get(n);
	}

	/**
	 * This method is not optimised for speed, but it is meant only to output
	 * debugging information, so speed is a non-issue.
	 * 
	 * @return formatted string representation of component
	 */
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		result.append("component " + _name + " = {\n");

		for (YAMLDEvent ev : _events) {
			result.append("event " + ev.name() + ";\n");
		}

		for (YAMLDGenericVar var : _vars) {
			result.append("var " + var.name() + " : ");
			for (int i = 0; !var.hasRange() && i < var.domain().size(); i++)
				result.append(var.domain().get(i).toString()
						+ (i == var.domain().size() - 1 ? " " : ", "));

			if (var.hasRange()) {
				result.append("[" + var.getRangeInit() + ".." + 
						var.getRangeEnd() + "];");
			}
				
			result.append("\n");
		}
		
		for (YAMLDGenericVar var : _dvars) {
			result.append("dvar " + var.name() + " : ");
			for (int i = 0; !var.hasRange() && i < var.domain().size(); i++)
				result.append(var.domain().get(i).toString()
						+ (i == var.domain().size() - 1 ? " " : ", "));

			if (var.hasRange()) {
				result.append(var.getRangeInit() + ".." + var.getRangeEnd());
			}
				
			result.append("\n");
		}
		
		for (YAMLDConnection conn : _conns) {
			result.append(conn.toFormattedString() + "\n");
		}
		
		for (YAMLDDVar dvar : dvars()) {
			for (YAMLDConstraint con : dvar.getConstraints()) {
				result.append(con.toFormattedString() + "\n");
			}
		}
		
		for (MMLDTransition trans : _transitions) {
			result.append(trans.toFormattedString() + "\n");
		}
			
		result.append("}");
		
		return result.toString();
	}

	public void setCoords(int x, int y) {
		_x = x;
		_y = y;
	}

	/**
	 * Returns the <code>x</code> coordinate of this component set through
	 * {@link #setCoords(int, int)}.
	 * 
	 * @return the abscissa for this component set for the GUI if set,
	 *         <code>-1</code> otherwise.
	 * */
	public int getX() {
		return _x;
	}

	/**
	 * Returns the <code>y</code> coordinate of this component set through
	 * {@link #setCoords(int, int)}.
	 * 
	 * @return the ordinate for this component set for the GUI if set,
	 *         <code>-1</code> otherwise.
	 * */
	public int getY() {
		return _y;
	}

	/**
	 * Returns the transition with the specified name.  
	 * 
	 * @param name the name of the transition.  
	 * @return the transition which specified name.  
     * @throws IllegalArgumentException if the component has no transition 
     * with the specified name.  
	 * */
	public MMLDTransition getTransition(String name) {
        for (final MMLDTransition trans: transitions()) {
            if (trans.getName().equals(name)) {
                return trans;
            }
        }
        
        throw new IllegalArgumentException("No transition with such name.");
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
	@Override
	public int hashCode() {
		return _name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int compareTo(YAMLDComponent arg0) {
		return toString().compareTo((arg0).toString());
	}
    
    /**
     * Returns the rule of this component with the specified name.  
     * 
     * @param ruleName the name of the rule.  
     * @return the rule of name <code>ruleName</code> if any, 
     *  <code>null</code> if no such rule exists.  
     */
    public MMLDRule getRule(String ruleName) {
        for (final MMLDTransition tr: transitions()) {
            for (final MMLDRule r: tr.getRules()) {
                if (r.getName().equals(ruleName)) {
                    return r;
                }
            }
        }
        
        return null;
    }
}
