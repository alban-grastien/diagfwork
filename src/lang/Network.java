package lang;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.supercom.util.Pair;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;

/**
 * Models the network of the system.  The network knows the list of components, 
 * the list of connections, etc.  
 * 
 * @author Alban Grastien
 * @version 1.0
 * */
public class Network {
	
	/**
	 * The components of the network.  
	 * */
	private Map<String, YAMLDComponent> _comps;
	
	private LinkedList<YAMLDSync> _syncs = new LinkedList<YAMLDSync>();
	
	private final Set<MMLDSynchro> _synchros = new HashSet<MMLDSynchro>();
	
	/**
	 * A map that indicates which events are synchronized to a specific event.    
	 * */
	private Map<YAMLDEvent, YAMLDSync> _syncMap = 
		new HashMap<YAMLDEvent, YAMLDSync>();
	
	/**
	 * A map that indicates the list of synchronized events.  
	 * A synchronized event is an event that is triggered only as a
	 * consequence of the triggering of an event that is synchronized to it.  
	 * */
	private Set<YAMLDEvent> _synchronized = new HashSet<YAMLDEvent>();
	
	/**
	 * The list of observable events.  
	 * */
	private Set<YAMLDEvent> _obsEvents = new HashSet<YAMLDEvent>();
	
	/**
	 * The list of relevant variables.  
	 * */
	private Set<YAMLDGenericVar> _relevant;
	
	/**
	 * Empty constructor.  
	 * */
	public Network() {
		_comps = new HashMap<String, YAMLDComponent>();
	}
	
	/**
	 * Returns the component with specified name.  
	 * 
	 * @param n the name of a component.  
	 * @return the component that is named <code>n</code> if any, 
	 * <code>null</code> otherwise.  
	 * */
	public YAMLDComponent getComponent(String n) {
		return _comps.get(n);
	}
	
	/**
	 * Adds the specified component to this net.  
	 * 
	 * @param comp the component to add to this net.  
	 * */
	public void addComponent(YAMLDComponent comp) {
		_comps.put(comp.name(), comp);
	}
	
	/**
	 * Returns the list of components in the network.  
	 * 
	 * @return the components that compose the network.  
	 * */
	public Collection<YAMLDComponent> getComponents() {
		SortedSet<YAMLDComponent> result = new TreeSet<YAMLDComponent>(_comps.values());
		return result;
		//return _comps.values();
	}

	/**
	 * Returns the variable of specified name on the specified component.  
	 * 
	 * @param comp the component the variable belongs to.    
	 * @param name the name of the variable.  
	 * @return the variable of name <code>name</code> in the component 
	 * <code>comp</code> if existing, <code>null</code> otherwise.
	 * @deprecated Use {@link YAMLDComponent#getVariable(String)} instead.      
	 * */
	public YAMLDGenericVar getVariable(YAMLDComponent comp, String name) {
		return comp.getVariable(name);
	}
	
	/**
	 * Returns the list of constraints attached with the specified dependent variable.  
	 * 
	 * @param var the {@link YAMLDDVar} whose list of constraints is expected.
	 * @return the list of constraints that define how <code>var</code> should be assigned.  
	 * */
	public Collection<YAMLDConstraint> getConstraints(YAMLDDVar var) {
		return var.getConstraints();
	}
	
	/**
	 * Returns the list of state variables in the network.  
	 * 
	 * @return the list of variables that model the state of the network.  
	 * */
	public Collection<YAMLDVar> getStateVariables() {
		final List<YAMLDVar> result = new ArrayList<YAMLDVar>();
        for (final YAMLDComponent comp: getComponents()) {
            result.addAll(comp.vars());
        }
        return result;
	}
	
	private YAMLDSync currentSync;

	@Deprecated
	public void startSync(YAMLDComponent c,YAMLDEvent e) {
		currentSync = new YAMLDSync(e);
		_syncs.add(currentSync);
		_syncMap.put(e, currentSync);
	}

	@Deprecated
	public void addSync(YAMLDComponent c,YAMLDEvent e) {
		currentSync.add(e);
		_synchronized.add(e);
	}

	@Deprecated
	public LinkedList<YAMLDSync> getSyncs() {
		return _syncs;
	}

	@Deprecated
	public YAMLDSync getSync(YAMLDEvent e) {
		return _syncMap.get(e);
	}
	
	/**
	 * Adds the specified synchronisation to this network.  
	 * 
	 * @param s the synchronisation to add to this network.  
	 * */
	public void addSynchro(MMLDSynchro s) {
		_synchros.add(s);
	}

	/**
	 * Returns the list of synchronisation in this network.  
	 * The synchronisation pertaining to a specified {@link YAMLDEvent} 
	 * can be directly accessed from that class.  
	 * 
	 * @return the event synchronisations that apply in this network.  
	 * */
	public Set<MMLDSynchro> getSynchros() {
		Set<MMLDSynchro> result = new HashSet<MMLDSynchro>(_synchros);
		return result;
		//return _synchros;
	}

	public void removeAllComponents()
	{
		_comps.clear();
	}
	
	public boolean isSynchronized(YAMLDEvent e) {
		return _synchronized.contains(e);
	}
	
	/**
     * Returns the set of observable events in this network.  
     * 
     * @return the events that are observable in this network.  
     */
    public Collection<YAMLDEvent> observableEvents() {
		return _obsEvents;
	}
	
    /**
     * Computes the unobservable events of the network.  
     * 
     * @return the events that are unobservable in this network.  
     */
    public List<YAMLDEvent> getUnobservableEvents() {
        final List<YAMLDEvent> unobservableEvents = new ArrayList<YAMLDEvent>();
        final Collection<YAMLDEvent> observableEvents = observableEvents();
        for (final YAMLDComponent comp: getComponents()) {
        for (final YAMLDEvent e: comp.events()) {
            if (!observableEvents.contains(e)) {
                unobservableEvents.add(e);
                }
            }
        }
            
        return unobservableEvents;
    }
    
    
	public void addObservableEvent(YAMLDEvent e) {
		_obsEvents.add(e);
	}
	
	/**
	 * Indicates whether the specified variable is relevant 
	 * in this network.  A variable is relevant 
	 * if one the following properties holds
	 * <ul>
	 * <li>
	 *   the (state) variable appears in the effect of some transition,  
	 * </li>
	 * <li>
	 *   the variable appears in the precondition of some transition, 
	 * </li>
	 * <li>
	 *   the variable appears in the precondition of some constraint 
	 *   defining a relevant variable.  
	 * </li>
	 * </ul>
	 * Irrelevant variables are only useful to show the current state 
	 * of the network; however they are irrelevant for the diagnosis 
	 * and can be safely ignored.  
	 * 
	 * @param var the variable tested.  
	 * @return <code>true</code> if <code>var</code> is relevant.  
	 * */
	public boolean isRelevant(YAMLDGenericVar var) {
		if (_relevant == null) {
			computeRelevant();
		}
		return _relevant.contains(var);
	}
	
	private void computeRelevant() {
		final Set<YAMLDGenericVar> result = new HashSet<YAMLDGenericVar>();
		
		// State variables
		for (final YAMLDComponent comp: getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				for (final MMLDRule rule: trans.getRules()) {
					for (final YAMLDAssignment ass: rule.getAssignments()) {
						result.add(ass._var);
					}
				}
			}
		}
		
		// Dependent variables useful for the transitions
		final Set<YAMLDGenericVar> open = new HashSet<YAMLDGenericVar>();
		for (final YAMLDComponent comp: getComponents()) {
			for (final MMLDTransition trans: comp.transitions()) {
				for (final YAMLDFormula f: trans.getPreconditions()) {
					extractVariables(open, f, comp);
				}
				for (final MMLDRule rule: trans.getRules()) {
					final YAMLDFormula f = rule.getCondition();
					extractVariables(open, f, comp);
				}
			}
		}
		result.addAll(open);
		
		// Determining which dependent variables are useful for other dependent variables
		while (!open.isEmpty()) {
			final YAMLDDVar dvar;
			{
				final YAMLDGenericVar var = open.iterator().next();
				open.remove(var);
				if (var instanceof YAMLDVar) {
					continue;
				}
				dvar = (YAMLDDVar)var;
			}
			
			final Set<YAMLDGenericVar> newVars = new HashSet<YAMLDGenericVar>();
			for (final YAMLDConstraint con: dvar.getConstraints()) {
				final YAMLDFormula f = con.getPrecondition();
				extractVariables(newVars, f, dvar.getComponent());
			}
			
			for (final YAMLDGenericVar var: newVars) {
				if (!result.add(var)) {
					continue;
				}
				if (var instanceof YAMLDVar) {
					continue;
				}
				open.add(var);
			}
		}
		
		_relevant = Collections.unmodifiableSet(result);
	}
	
	// TODO: Make it a method of YAMLDFormula
	private void extractVariables(Set<YAMLDGenericVar> set, YAMLDFormula f, YAMLDComponent comp) {
		if (f instanceof YAMLDTrue) {
			return;
		}
		if (f instanceof YAMLDFalse) {
			return;
		}
		if (f instanceof YAMLDAndFormula) {
			extractVariables(set, ((YAMLDAndFormula)f).getOp1(), comp);
			extractVariables(set, ((YAMLDAndFormula)f).getOp2(), comp);
			return;
		}
		if (f instanceof YAMLDOrFormula) {
			extractVariables(set, ((YAMLDOrFormula)f).getOp1(), comp);
			extractVariables(set, ((YAMLDOrFormula)f).getOp2(), comp);
			return;
		}
		if (f instanceof YAMLDEqFormula) {
			final YAMLDEqFormula eq = (YAMLDEqFormula)f;
			extractVariables(set, eq._lhs, comp);
			extractVariables(set, eq._rhs, comp);
			return;
		}
		if (f instanceof YAMLDExistsPath) {
			final YAMLDExistsPath ex = (YAMLDExistsPath)f;
			final Collection<Path> pathes = ex.getPathes();
			for (final Path p: pathes) {
				for (final YAMLDComponent c: p) {
					extractVariables(set, ex._f, c);
				}
			}
			return;
		}
		if (f instanceof YAMLDStringExistsPath) {
			final YAMLDStringExistsPath e = (YAMLDStringExistsPath)f;
			final YAMLDExistsPath ex = e.simplify(this);
			final Collection<Path> pathes = ex.getPathes();
			for (final Path p: pathes) {
				for (final YAMLDComponent c: p) {
					extractVariables(set, ex._f, c);
				}
			}
			return;
		}
		if (f instanceof YAMLDNotFormula) {
			extractVariables(set, ((YAMLDNotFormula)f).getOp(), comp);
			return;
		}
	}
	
	// TODO: make it a method of YAMLDExpr.  
	private void extractVariables(Set<YAMLDGenericVar> set, YAMLDExpr ex, YAMLDComponent comp) {
		if (ex instanceof VariableValue) {
			set.add(((VariableValue)ex).variable());
			return;
		}
		if (ex instanceof YAMLDAddExpr) {
			final YAMLDAddExpr add = (YAMLDAddExpr)ex;
			extractVariables(set, add.op1, comp);
			extractVariables(set, add.op2, comp);
			return;
		}
		if (ex instanceof YAMLDID) {
			final YAMLDID id = (YAMLDID)ex;
			set.add(id.variable(comp, this));
			return;
		}
//		if (ex instanceof YAMLDNum) {
//			return;
//		}
		if (ex instanceof YAMLDValue) {
			return;
		}
		throw new IllegalArgumentException("Cannot deal with classes of type " + ex.getClass().getName());
	}
	
	/**
	 * Computes the list of preconditions that label forced transitions 
	 * together with the {@link MMLDTransition} they refer to.  
	 * These preconditions need to be monitored as their becoming satisfied 
	 * may trigger a transition.  
	 * 
	 * @return a list of pairs ( {@link MMLDTransition}, {@link YAMLDFormula}) 
	 * such that the formula being satisfied for some time 
	 * will trigger the transition.  Note that the {@link YAMLDComponent} 
	 * on which the formula should be satisfied 
	 * can be retrieved by getting the component on which the transition is defined.  
	 * */
	public List<Pair<MMLDTransition,YAMLDFormula>>
		getForcingPreconditionsWithTransition() {
		final List<Pair<MMLDTransition,YAMLDFormula>> result = 
			new ArrayList<Pair<MMLDTransition,YAMLDFormula>>();
		
		for (final YAMLDComponent c: getComponents()) {
			for (final MMLDTransition tr: c.transitions()) {
				for (final YAMLDFormula f: tr.getPreconditions()) {
					result.add(new Pair<MMLDTransition, YAMLDFormula>(tr, f));
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Computes the list of preconditions that label forced transitions.  
	 * These preconditions need to be monitored as their becoming satisfied 
	 * may trigger a transition.  
	 * 
	 * @return a set of pairs ( {@link YAMLDComponent}, {@link YAMLDFormula}) 
	 * such that the formula being satisfied for some time 
	 * will trigger a transition on the component.  
	 * */
	public Set<Pair<YAMLDComponent,YAMLDFormula>>
		getForcingPreconditionsWithComponent() {
		final Set<Pair<YAMLDComponent,YAMLDFormula>> result = 
			new HashSet<Pair<YAMLDComponent,YAMLDFormula>>();
		
		for (final YAMLDComponent c: getComponents()) {
			for (final MMLDTransition tr: c.transitions()) {
				for (final YAMLDFormula f: tr.getPreconditions()) {
					result.add(new Pair<YAMLDComponent, YAMLDFormula>(c, f));
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this network 
	 * in the format used by {@link MMLDlightParser}.  
	 * 
	 * @return a string representation of this network.  
	 * */
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		
		for (final YAMLDComponent comp: getComponents()) {
			result.append(comp.toFormattedString()).append("\n");
		}
		
		for (final MMLDSynchro s: _synchros) {
			result.append(s.toFormattedString());
		}
		
		for (final YAMLDEvent e: observableEvents()) {
			result.append("observable ").append(e.toFormattedString()).append(";\n");
		}
		
		return result.toString();
	}
	
	/**
	 * Returns the event associated with the specified string.  
	 * The string should be defined as <code>comp_name.event_name</code> 
	 * without spaces.  
	 * 
	 * @param str the string representing the event.  
	 * @param out indicates where a warning should be printed (if any).  
	 * @return the event represented by the string if any, 
	 * <code>null</code> otherwise.  
	 * */
	public YAMLDEvent getEvent(String str, PrintStream out) {
		final Matcher mat = EVENT_PATTERN.matcher(str);
		if (!mat.find()) {
			if (out != null) {
				out.println("String `" + str + "` does not match format.");
			}
			return null;
		}
		
		final YAMLDComponent comp;
		{
			final String name = mat.group(1);
			comp = getComponent(name);
			if (comp == null) {
				if (out != null) {
					out.println("Unknown component `" + name + "`.");
				}
				return null;
			}
		}
		
		final YAMLDEvent event;
		{
			final String name = mat.group(2);
			event = comp.getEvent(name);
			if (event == null) {
				if (out != null) {
					out.println("Unknown event `" + name + 
							"' for component " + comp.name());
				}
				return null;
			}
		}
		
		return event;
	}
	
    public static Network readNetwork(String address) throws Exception {
        final InputStream inputStream = new FileInputStream(address);
        final Reader input = new InputStreamReader(inputStream);
        final MMLDlightLexer lexer = new MMLDlightLexer(new ANTLRReaderStream(
                new BufferedReader(input)));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final MMLDlightParser parser = new MMLDlightParser(tokens);
        parser.net();

        return MMLDlightParser.net;
    }
    
	/**
	 * A pattern to read the events.  
	 * */
	public final static Pattern EVENT_PATTERN = Pattern.compile("([^\\.]*)\\.([^\\.]*)");
    
    /**
     * Reads the list of events in the file at the specified address.  
     * It is assumed that each event is presented in a different line 
     * with format comp_name "." event_name.  
     * Comments are allowed (lines starting with "//").  
     * 
     * @param address the name of the file that contains the list of events.  
     * @return the list of events in file <tt>address</tt>.  
     * @throws Exception if there is any problem reading the file.  
     */
    public List<YAMLDEvent> readEvents(String address) throws Exception {
        final List<YAMLDEvent> result = new ArrayList<YAMLDEvent>();
        
        final BufferedReader reader = new BufferedReader(new FileReader(address));
        while (reader.ready()) {
            final String line = reader.readLine();
            if (line.startsWith("//")) {
                continue;
            }
            final YAMLDEvent event = getEvent(line, null);
            result.add(event);
        }
        
        return result;
    }
}
