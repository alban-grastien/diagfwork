package lang;

import java.util.Map;

import edu.supercom.util.Pair;

public abstract class AbstractTimedState implements TimedState {

	@Override
	public TimedState apply(Map<? extends YAMLDVar, YAMLDValue> m) {
		return new MapTimedState(this, getState().apply(new MapStateModification(m)));
	}

	@Override
	public String toFormattedString() {
		return toFormattedString(this);
	}

	public static String toFormattedString(TimedState ts) {
		final StringBuilder result = new StringBuilder();
		
		final State s = ts.getState();
		final Network net = s.getNetwork();
		
		for (final YAMLDComponent c: net.getComponents()) {
			for (final YAMLDVar v: c.vars()) {
				final YAMLDValue val = s.getValue(v);
				result.append(c.name()).append(".")
				      .append(v.name()).append(" := ")
				      .append(val.toFormattedString()).append("\n");
			}
		}
		
//		result.append("\n");

		// TODO: Make a method in TimedState that returns 
		// the collection of enabled transitions
		for (final Pair<YAMLDComponent,YAMLDFormula> pair: 
			net.getForcingPreconditionsWithComponent()) {
			final YAMLDComponent c = pair.first();
			final YAMLDFormula f = pair.second();
			if (f.satisfied(s, c)) {
				final Period d = ts.satisfiedFor(c, f);
				result.append("< ").append(c.name()).append(" , ")
				      .append(f.toFormattedString()).append(" > := ")
				      .append(d).append("\n");
			}
		}
		
		return result.toString();
	}
}
