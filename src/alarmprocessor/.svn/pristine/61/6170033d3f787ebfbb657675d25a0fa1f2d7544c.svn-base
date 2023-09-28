package alarmprocessor;

import java.util.Collection;
import java.util.List;
import java.util.Vector;
import lang.MMLDRule;
import lang.Network;
import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import util.MMLDGlobalTransition;
import util.Scenario;

public class RuleClusterMMLD extends RuleClusterAbstract {

    private List<MMLDGlobalTransition> transitions;
    private List<Integer> transIndexInScenario;

    /**
     * Default constructor.
     */
    public RuleClusterMMLD() {
    	super();
    	transitions = new Vector<MMLDGlobalTransition>();
    	transIndexInScenario = new Vector<Integer>();
    }

    /**
     * Constructor that initializes the initial state data member to the {@State} object
     * given as an argument. 
     * @param init
     */
    public RuleClusterMMLD(State init) {
    	super(init);
    	transitions = new Vector<MMLDGlobalTransition>();
    	transIndexInScenario = new Vector<Integer>();
    }

    /**
     * Adds a transition to this cluster.
     * @param trans, the transition to add
     * @param index, the index of the transition in the global scenario that
     * is being processed
     */
    public void addTransition(MMLDGlobalTransition trans, int index) {
        transitions.add(trans);
        transIndexInScenario.add(index);
        components.addAll(trans.affectedComponents());
        setComplete();
    }
    
    /**
     * Adds the contents of a cluster given as an argument to the 
     * current cluster. 
     * @param cluster
     */
    public void includeCluster(RuleClusterMMLD cluster) {
    	this.transitions.addAll(cluster.transitions);
    	this.components.addAll(cluster.components);
    	this.transIndexInScenario.addAll(cluster.transIndexInScenario);
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
        for (MMLDGlobalTransition trans : this.transitions) {
        	MMLDRule mmldrule = trans.getRule(comp);
        	if (mmldrule == null)
        		continue;
        	if (mmldrule.getGeneratedEvents().contains(event))
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
        State finalState = init.applyMMLDGlobalTransitions(this.transitions);
        for (YAMLDComponent comp : components) {
        	if (!compIsInNominalState(comp, finalState)) {
                // as this component is not in a nominal state
                // we extract all observable events related to the component
            	for (MMLDGlobalTransition trans : this.transitions) {
            		MMLDRule mmldrule = trans.getRule(comp);
            		if (mmldrule == null)
            			continue;
            		for (YAMLDEvent e : mmldrule.getGeneratedEvents()) {
		        		if (e == null)
		        			continue;
		        		if (net.observableEvents().contains(e))
		        			result.add(e);
            		}
            	}
            }
        }
        return result;
    }

    /**
     * Tells whether a {@link MMLDGlobalTransition} object should be added 
     * to this cluster.
     * It answers true if the components affected by the global transition overlap
     * with the components already added to this cluster.
     * @param trans
     * @return
     */
    public boolean shouldAddTransition(MMLDGlobalTransition trans) {
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
    private void setComplete() {
        State finalState = initState.applyMMLDGlobalTransitions(transitions);
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
    protected void setComplete2() {
        State finalState = getInitState().applyMMLDGlobalTransitions(transitions);
        complete = (!this.forcedTransitionOnComponents(finalState));
    }

    @Override
    public String toString() {
        String result = "";
        result += this.transitions.toString() + "\n";
        result += this.components.toString() + "\n";
        return result;
    }
     
    @Override
    protected Collection<YAMLDEvent> getContainedEvents() {
    	Vector<YAMLDEvent> result = new Vector<YAMLDEvent>();
        for (MMLDGlobalTransition trans : transitions) {
            for (YAMLDComponent comp : components) {
            	MMLDRule mmldrule = trans.getRule(comp);
            	if (mmldrule == null)
            		continue;
            	result.addAll(mmldrule.getGeneratedEvents());
            }
        }
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
    	return false;
    	/*
		for (final YAMLDComponent c: this.components) {
			for (final YAMLDTrans t: c.trans()) {
				if (t.isforced && t.formula().satisfied(currentState, c)) {
					return true;
				}
			}
		}
		return false;
		*/
    }

	@Override
	public RuleClusterMMLD asClusterMMLD() {
		return this;
	}

	@Override
	public RuleClusterYAMLD asClusterYAMLD() {
		return null;
	}
	
	public List<MMLDGlobalTransition> getTransitions() {
		return this.transitions;
	}
	
	public List<Integer> getTransitionIndexes() {
		return this.transIndexInScenario;
	}
    
}
