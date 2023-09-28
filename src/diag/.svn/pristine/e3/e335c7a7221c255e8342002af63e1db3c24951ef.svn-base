package diag;

import lang.YAMLDComponent;
import lang.YAMLDFormula;
import edu.supercom.util.Pair;
import edu.supercom.util.sat.VariableSemantics;

/**
 * This variable semantics is associated to true 
 * iff the given formula holds at the given time.  
 * */
public class FormulaHolds extends DatedSemantic<Pair<YAMLDFormula,YAMLDComponent>> 
implements VariableSemantics {

	public FormulaHolds(YAMLDFormula form, YAMLDComponent comp, int time) {
		super(time,new Pair<YAMLDFormula,YAMLDComponent>(form,comp));
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
		if (!(obj instanceof FormulaHolds)) {
			return false;
		}
		FormulaHolds other = (FormulaHolds) obj;
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
	public Pair<YAMLDFormula,YAMLDComponent> getCore() {
		return (Pair<YAMLDFormula,YAMLDComponent>)super.getCore();
	}
	
	@Override
	public String toString() {
		final YAMLDFormula f = getCore().first();
		final YAMLDComponent c = getCore().second();
		if (c == null) {
			return "null -> " + f.toFormattedString() + "@" + _time;
		} else {
			return c.name() + " -> " + f.toFormattedString() + "@" + _time;
		}
	}
}
