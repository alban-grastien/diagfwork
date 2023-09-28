package diag;

import edu.supercom.util.sat.VariableSemantics;
import lang.YAMLDEvent;

public class EventOccurrence extends DatedSemantic<YAMLDEvent> implements VariableSemantics {

	public EventOccurrence(YAMLDEvent event, int time) {
		super(time, event);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_core == null) ? 0 : _core.hashCode());
		result = prime * result + _time;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EventOccurrence)) {
			return false;
		}
		EventOccurrence other = (EventOccurrence) obj;
		if (_core == null) {
			if (other._core != null) {
				return false;
			}
		} else if (!_core.equals(other._core)) {
			return false;
		}
		if (_time != other._time) {
			return false;
		}
		return true;
	}
	
	@Override
	public YAMLDEvent getCore() {
		return (YAMLDEvent)super.getCore();
	}
	
	@Override 
	public String toString() {
		return getCore().toFormattedString() + "@" + _time;
	}
	
}
