package lang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link YAMLDEvent} object models an event, i.e., 
 * something that takes place at a specific time in the system.  
 * An event is grounded, i.e., the component 
 * on which it takes place is set.  
 * An event can be an input or an output event.  
 * <p />
 * 
 * An input event is triggered by the simultaneous occurrence 
 * of an output event synchronised with it; 
 * it labels 'triggeredby' conditions of transitions.  
 * An output event is triggered by the occurrence of a transition.  
 * */
public class YAMLDEvent 
{
	private final String _name;
	private final YAMLDComponent _comp;
	
	private boolean _input;
	
	/**
	 * The list of synchronisations this event is part of.  
	 * */
	private final Set<MMLDSynchro> _synchros;
	
	/**
	 * The list of rules that generate this event.  
	 * */
	private final List<MMLDRule> _generatingRules;
	
	/**
	 * Builds an event that takes place on the specified component, 
	 * that has the specified name.  Before the event is used, 
	 * the {@linkplain #setInput(boolean) input status} should be set.  
	 * 
	 * @param comp the component on which the event take place.  
	 * @param newName the name of the event. 
	 * @see #setInput(boolean)
	 * */
	public YAMLDEvent(YAMLDComponent comp, String newName)
	{
		_name = newName;
		_comp = comp;
		_synchros = new HashSet<MMLDSynchro>();
		_generatingRules = new ArrayList<MMLDRule>();
	}
	
	public String name()
	{
		return _name;
	}
	
	/**
	 * Returns the component corresponding to this event.  
	 * 
	 * @return the component this event is defined on.  
	 * */
	public YAMLDComponent getComponent() {
		return _comp;
	}
	
	public String toFormattedString() {
		return getComponent().name() + "." + name();
	}
	
	/**
	 * Sets the input/output status of this event.  
	 * 
	 * @param b <code>true</code> if this event is an input event, 
	 * <code>false</code> otherwise.  
	 * */
	public void setInput(boolean b) {
		_input = b;
	}
	
	/**
	 * Indicates whether this event is an input event.  
	 * 
	 * @return <code>true</code> if this event is an input event.  
	 * */
	public boolean isInput() {
		return _input;
	}
	
	/**
	 * Computes the list of {@link MMLDRule} that produce this event.  
	 * Note that in the current implementation, the list is not stored.  
	 * Heavy use of this method is inappropriate.  
	 * 
	 * @return the subset of {@link MMLDRule} of the component this event 
	 * is defined on such that this event is contained 
	 * in {@link MMLDRule#getGeneratedEvents()}.  
	 * */
	public Collection<MMLDRule> getGeneratingRules() {
		return _generatingRules;
//		
//		final List<MMLDRule> result = new ArrayList<MMLDRule>();
//		
//		java.util.List<MMLDTransition> comp_trans_list = _comp.transitions();
//		for (final MMLDTransition tr : comp_trans_list) {
//		//for (final MMLDTransition tr: _comp.transitions()) {
//			for (final MMLDRule r: tr.getRules()) {
//				if (r.getGeneratedEvents().contains(this)) {
//					result.add(r);
//				}
//			}
//		}
//		
//		return result;
	}
	
	/**
	 * Computes the list of {@link MMLDTransition} that may be triggered by this event.  
	 * Note that in the current implementation, the list is not stored.  
	 * Heavy use of this method is inappropriate.  
	 * 
	 * @return the subset of {@link MMLDTransition} of the component this event 
	 * is defined on such that this event is contained 
	 * in {@link MMLDTransition#getTriggeringEvents()}.  
	 * */
	public List<MMLDTransition> getTriggerableTransitions() {
		final List<MMLDTransition> result = new ArrayList<MMLDTransition>();
		
		for (final MMLDTransition tr: _comp.transitions()) {
			if (tr.getTriggeringEvents().contains(this)) {
				result.add(tr);
			}
		}
		
		return result;
	}
	
	/**
	 * Adds the specified synchro to this event.  
	 * 
	 * @param s the synchronisation this event is part of.  
	 * */
	public void addSynchro(MMLDSynchro s) {
		_synchros.add(s);
	}
	
	/**
	 * Returns the list of {@link MMLDSynchro} this event is part of.
	 *   
	 * @return the synchronisations that define 
	 * how this event is synchronised with other events.  
	 * */
	public Set<MMLDSynchro> getSynchros() {
		return Collections.unmodifiableSet(_synchros);
	}
	
    @Override
	public String toString() {
		return toFormattedString();
	}
	
	/**
	 * Indicates this event that the specified rule generates this event.  
	 * 
	 * @param r the rule.  
	 * */
	void addGeneratingRule(MMLDRule r) {
		_generatingRules.add(r);
	}
	
	@Override
	public int hashCode() {
		final int P1 = 84673;
		final int P2 = 20173;
		// guaranteed to work well when there are less than 100000 components
		return P1*_name.hashCode() + P2*_comp.hashCode();
	}

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
    
    public static Set<YAMLDEvent> readSetOfEvents(Network net, String ad) {
        try {
            final Set<YAMLDEvent> result = new HashSet<YAMLDEvent>();
            
            final Pattern pat = Pattern.compile("^\\s*([^\\s]*)\\s*\\.\\s*([^\\s]*)\\s*$");
            
            final BufferedReader reader = new BufferedReader(new FileReader(ad));
            while (reader.ready()) {
                final String line = reader.readLine();
                if (line.startsWith("//")) {
                    continue;
                }
                final Matcher mat = pat.matcher(line);
                if (mat.matches()) {
                    final String compName = mat.group(1);
                    final String eventName = mat.group(2);
                    final YAMLDComponent comp = net.getComponent(compName);
                    final YAMLDEvent event = comp.getEvent(eventName);
                    if (event == null) {
                        return null;
                    }
                    result.add(event);
                } else {
                    return null;
                }
            }
            
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
