package lang;

import java.util.LinkedList;

@Deprecated
public class YAMLDSync {

	public final YAMLDEvent event;

	private LinkedList<YAMLDEvent> _sync;
	
	YAMLDSync(YAMLDEvent e) {
		event = e;
		_sync = new LinkedList<YAMLDEvent>();	
	}

	public void add(YAMLDEvent e) {
		_sync.add(e);
	}
	
	public LinkedList<YAMLDEvent> events() {
		return _sync;
	}
	
	public String toString() {
		final StringBuilder result = new StringBuilder("[");
		for (final YAMLDEvent event: _sync) {
			result.append(event.getComponent().name())
			      .append(".")
			      .append(event.name())
			      .append(";");
		}
		return result.append("]").toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((_sync == null) ? 0 : _sync.hashCode());
		return result;
	}
}
