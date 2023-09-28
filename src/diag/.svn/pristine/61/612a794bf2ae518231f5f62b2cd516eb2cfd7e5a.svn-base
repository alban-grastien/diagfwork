package diag;

import edu.supercom.util.sat.VariableSemantics;

public abstract class DatedSemantic<E> implements VariableSemantics {

	public final int _time;
	
	protected final E _core;
	
	public DatedSemantic(int time, E core) {
		if (core == null) {
			throw new IllegalArgumentException("Cannot enter a NULL core");
		}
		_time = time;
		_core = core;
	}
	
	public E getCore() {
		return _core;
	}
	
	@Override
	public int hashCode() {
		return 13*_time + _core.hashCode();
	}
}
