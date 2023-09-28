package lang;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * An MMLDSynchro is a synchronisation as defined in the MMLD model.  
 * It will eventually replace the {@link YAMLDSync} class.  
 * 
 * @author Alban Grastien
 * */
public class MMLDSynchro {
	
	/**
	 * The event that generates the other events.  
	 * */
	private final YAMLDEvent _event;
	
	/**
	 * The set of events that are causally generated by <code>_event</code>.  
	 * */
	private final Set<YAMLDEvent> _events;
	
	/**
	 * Creates a synchronisation where the specified event 
	 * generates the set of specified events.  
	 * 
	 * @param e the (output) event that generates the other events.  
	 * @param es the list of (input) events that are generated 
	 * when the event <code>e</code> takes place.  
	 * */
	public MMLDSynchro(YAMLDEvent e, Collection<YAMLDEvent> es) {
		_event = e;
		_events = Collections.unmodifiableSet(new HashSet<YAMLDEvent>(es));
	}
	
	/**
	 * Creates a synchronisation where the specified event 
	 * generates the second specified event.  
	 * 
	 * @param e1 the (input) event that generates the other event.  
	 * @param e2 the (output) event that takes place when <code>e1</code> 
	 * takes place.  
	 * */
	public MMLDSynchro(YAMLDEvent e1, YAMLDEvent e2) {
		_event = e1;
		final Set<YAMLDEvent> set = new HashSet<YAMLDEvent>();
		set.add(e2);
		_events = Collections.unmodifiableSet(set);
	}

	/**
	 * Returns the (output) event that generates the other (input) events.  
	 * 
	 * @return the event that is responsible for this synchronisation.  
	 * */
	public YAMLDEvent getEvent() {
		return _event;
	}
	
	/**
	 * Returns the list of (input) events that are generated 
	 * by the occurrence of {@link #getEvent()}.  
	 * 
	 * @return the list of events that are synchronised.  
	 * */
	public Set<YAMLDEvent> getSynchronizedEvents() {
		return _events;
	}
	
	/**
	 * Returns a string representation of this {@link MMLDSynchro}
	 * in the format used by {@link MMLDlightParser}.  
	 * 
	 * @return a string representation of this synchronisation.  
	 * */
	public String toFormattedString() {
		final StringBuilder result = new StringBuilder();
		
		result.append("synchronize ");
		result.append(_event.toFormattedString());
		for (final YAMLDEvent evt: _events) {
			result.append(", ");
			result.append(evt.toFormattedString());
		}
		result.append(";\n");
		
		return result.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_event == null) ? 0 : _event.hashCode());
		result = prime * result + ((_events == null) ? 0 : _events.hashCode());
		return result;
	}

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}