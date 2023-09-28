package alarmprocessor;

import java.util.Collection;
import java.util.Vector;

import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDTrans;

import util.GlobalTransition;
import util.Scenario;

public class RuleClusterYAMLD extends RuleClusterAbstract {
	
    private Vector<GlobalTransition> transitions;

    public RuleClusterYAMLD() {
    	super();
    	transitions = new Vector<GlobalTransition>();
    }
    
    public RuleClusterYAMLD(State init) {
    	super(init);
    	transitions = new Vector<GlobalTransition>();
    }

    public void addTransition(GlobalTransition trans) {
        transitions.add(trans);
        components.addAll(trans.affectedComponents());
        setComplete();
    }

    /**
     * Checks whether a {@link YAMLDEvent} object given as a parameter
     * belongs to this cluster
     * @param a {@link YAMLDEvent} object 
     * @return true iff the current cluster contains the {@link YAMLDEvent}
     * object given as input
     */
    public boolean containsEvent(YAMLDEvent event) {
    	YAMLDComponent comp = event.getComponent();
        if (!components.contains(comp))
            return false;
        for (GlobalTransition trans : this.transitions) {
    		YAMLDTrans yamdltr = trans.getTransition(comp);
    		if (yamdltr == null)
    			continue;
    		YAMLDEvent e = yamdltr.eventTrigger();
    		if (e == null)
    			continue;
    		if (event.equals(e))
    			return true;
        }
        return false;
    }    

    /**
     * Extract and return the list of all observable events in the current
     * cluster that are directly related to a component whose final state
     * (relative to the events in the current cluster) is not nominal.
     * @param sce
     * @return the list of observable events
     */
    public Collection<YAMLDEvent> extractTransientAlarms(Scenario sce) {
        Vector<YAMLDEvent> result = new Vector<YAMLDEvent>();
        State init = getInitState();
        Network net = init.getNetwork();
        State finalState = init.applyGlobalTransitions(this.transitions);
        for (YAMLDComponent comp : components) {
        	if (compIsInNominalState(comp, finalState)) {
                // as this component is not in a nominal state
                // we extract all observable events related to the component
            	for (GlobalTransition trans : this.transitions) {
	        		YAMLDTrans yamdltr = trans.getTransition(comp);
	        		if (yamdltr == null)
	        			continue;
	        		YAMLDEvent e = yamdltr.eventTrigger();
	        		if (e == null)
	        			continue;
	        		if (net.observableEvents().contains(e))
	        			result.add(e);
            	}
            }
        }
        return result;
    }

    /**
     * Tells whether a {@link GlobalTransition} object should be added to this cluster.
     * It answers true if the components affected by the global transition overlap
     * with the components already added to this cluster.
     * @param trans
     * @return
     */
    public boolean shouldAddTransition(GlobalTransition trans) {
        Collection<YAMLDComponent> affectedComps = trans.affectedComponents();
        for (YAMLDComponent comp : affectedComps) {
            if (components.contains(comp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Marks the current {@link RuleClusterAbstract} object as complete when all components 
     * involved in this {@link RuleClusterAbstract} object are in a nominal state.
     * @param sce
     */
    private void setComplete2() {
        State finalState = initState.applyGlobalTransitions(transitions);
        for (YAMLDComponent comp : components) {
        	if (!this.compIsInNominalState(comp, finalState)) {
                complete = false;
                return;
            }
        }
        complete = true;
    }

    /**
     * Marks this cluster as complete when the resulting state does
     * not trigger any new forced transitions that have something to do
     * with the components contained in this cluster.
     */
    protected void setComplete() {
        State init = getInitState();
        State finalState = init.applyGlobalTransitions(transitions);
        complete = (!this.forcedTransitionOnComponents(finalState));
    }

    @Override
    public String toString() {
        String result = "";
        result += this.transitions.toString() + "\n";
        result += this.components.toString() + "\n";
        return result;
    }

    /**
     * Returns true iff, in the current state given as an input parameter,
     * there will be some forced transitions
     * that have something to do with the components contained in this cluster.
     * @param currentState
     * @return
     */
    private boolean forcedTransitionOnComponents(State currentState) {
		for (final YAMLDComponent c: this.components) {
			for (final YAMLDTrans t: c.trans()) {
				if (t.isforced && t.formula().satisfied(currentState, c)) {
					return true;
				}
			}
		}
		return false;
    }

	@Override
	public RuleClusterMMLD asClusterMMLD() {
		return null;
	}

	@Override
	public RuleClusterYAMLD asClusterYAMLD() {
		return this;
	}

	@Override
	protected Collection<YAMLDEvent> getContainedEvents() {
		Vector<YAMLDEvent> result = new Vector<YAMLDEvent>();
		for (GlobalTransition trans : transitions) {
	        for (YAMLDComponent comp : components) {
	            YAMLDTrans yamldtr = trans.getTransition(comp);
	            if (yamldtr != null)
	            	result.add(yamldtr.eventTrigger());
	        }
        }
		return result;
	}
    
}
