/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmprocessor;

import util.Scenario;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDValue;
import lang.YAMLDVar;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Adi Botea
 */
public abstract class RuleClusterAbstract {

    protected HashSet<YAMLDComponent> components;
    /**
     * Boolean flag telling whether this cluster is complete or we should still
     * consider adding transitions (and components if needed) to it.
     */
    protected boolean complete;
    /**
     * The initial state of a cluster is the global state in the scenario
     * where we started building the current cluster.
     */
    protected State initState;

    public RuleClusterAbstract() {
        components = new HashSet<YAMLDComponent>();
        complete = false;
    }

    public RuleClusterAbstract(State init) {
        components = new HashSet<YAMLDComponent>();
        complete = false;
        initState = init;
    }

    /**
     * Returns true if all components are in the "normal" state.
     * As a first implementation, the normal state is defined as the
     * initial state. This is something that should be fixed in the future.
     * @return
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Compare the two state arguments and return true if they differ.
     * @param component
     * @param current state of component
     * @param nominal state of component
     * @return true iff the component is in the nominal state
     */
    private boolean compStatesDiffer(YAMLDComponent comp, State s1, State s2) {
    	// comp.vars() gives state variables but not derived variables
        for (YAMLDVar var : comp.vars()) { 
        	YAMLDValue v1 = s1.getValue(var);
        	YAMLDValue v2 = s2.getValue(var);
        	if (v1 == null && v2 != null)
        		return true;
        	if (v2 != null && v1 == null)
        		return true;
        	if (!v1.equals(v2))
        		return true;
        }
        return false;
    }

    /**
     * Returns true iff, in the global state currentState,
     * the component comp is in a nominal state.
     * Since the model currently does not include a notion of nominal states,
     * we rely on a stupid implementation which says that the nominal state of a
     * component is the local state of the component
     * in the global initial state of the cluster.
     * @param comp
     * @param currentState
     * @return
     */
    protected boolean compIsInNominalState(YAMLDComponent comp, State currentState) {
        return (!compStatesDiffer(comp, initState, currentState));
	}

	/**
     * This method returns the initial state of the cluster.
     * The initial state of a cluster is the global state in the scenario where
     * we started building the current cluster. 
     * @return
     */
    protected State getInitState() {
        return this.initState;
    }

    public String getAlarmsAsString(Scenario sce) {
    	String result = "";
    	Network net = getInitState().getNetwork();
        for (YAMLDEvent event : getContainedEvents()) {
            if (net.observableEvents().contains(event)) {
            	result += event.toFormattedString() + "\n";
            }
        }
        return result;
    }

    public void writeAlarmsToFile(Scenario sce, PrintStream ps) {
    	Network net = getInitState().getNetwork();
    	for (YAMLDEvent event : getContainedEvents()) { 
            if (net.observableEvents().contains(event)) {
                ps.print(event.getComponent().name());
                ps.print(" ");
                ps.println(event.toFormattedString());
            }
    	}
    }
    
	public String getEventsAsString() {
    	String result = "";
        for (YAMLDEvent event : getContainedEvents()) {
        	//result += event.getComponent().name();
        	//result += " ";
        	result += event.toFormattedString() + "\n";
        }
        return result;
	}

    /**
     * Checks whether a {@link YAMLDEvent} object given as a parameter
     * belongs to this cluster
     * @param a {@link YAMLDEvent} object 
     * @return true iff the current cluster contains the {@link YAMLDEvent}
     * object given as input
     */
    public abstract boolean containsEvent(YAMLDEvent event);
    public abstract Collection<YAMLDEvent> extractTransientAlarms(Scenario sce);
    public abstract RuleClusterMMLD asClusterMMLD();
    public abstract RuleClusterYAMLD asClusterYAMLD();
    protected abstract Collection<YAMLDEvent> getContainedEvents();


}
