package diag;

import lang.YAMLDSync;

public class SynchroTriggered extends DatedSemantic<YAMLDSync> {

	public SynchroTriggered(int time, YAMLDSync core) {
		super(time, core);
	}
	
	public YAMLDSync getCore() {
		return (YAMLDSync)_core;
	}
	
	public int hashCode() {
		return _time + getCore().hashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof SynchroTriggered)) {
			return false;
		}
		
		final SynchroTriggered trig = (SynchroTriggered)obj;
		if (_time != trig._time) {
			return false;
		}
		
		if (getCore() != trig.getCore()) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return getCore().toString() + "@" + _time;
	}

}
