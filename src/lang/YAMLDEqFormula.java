package lang;

public class YAMLDEqFormula implements YAMLDFormula {
	YAMLDExpr _lhs, _rhs;

	public YAMLDEqFormula(YAMLDExpr newOp1, YAMLDExpr newOp2) {
		_lhs = newOp1;
		_rhs = newOp2;
	}

	@Override
	public String toFormattedString() {
		return _lhs.toFormattedString() + " = " + _rhs.toFormattedString();
	}

	@Override
	public boolean satisfied(State state, YAMLDComponent con) {
		// It should be possible to do equality testing with ==
		// (and this is what happens in effects with the current implementation)
		// but {link Object#equals(Object)} is cleaner.
		final YAMLDValue val1 = _lhs.value(state, con);
		final YAMLDValue val2 = _rhs.value(state, con);
		return val1.equals(val2);
	}

	@Override
	public YAMLDFormula simplify(Network net) {
		return this;
	}

	public YAMLDExpr expr1() {
		return _lhs;
	}

	public YAMLDExpr expr2() {
		return _rhs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_lhs == null) ? 0 : _lhs.hashCode());
		result = prime * result + ((_rhs == null) ? 0 : _rhs.hashCode());
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
		if (!(obj instanceof YAMLDEqFormula)) {
			return false;
		}
		YAMLDEqFormula other = (YAMLDEqFormula) obj;
		if (_lhs == null) {
			if (other._lhs != null) {
				return false;
			}
		} else if (!_lhs.equals(other._lhs)) {
			return false;
		}
		if (_rhs == null) {
			if (other._rhs != null) {
				return false;
			}
		} else if (!_rhs.equals(other._rhs)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isTriviallyFalse(Network net, YAMLDComponent c) {
		final YAMLDValue val1 = _lhs.getTrivialValue(net, c);
		if (val1 == null) {
			return false;
		}
		final YAMLDValue val2 = _rhs.getTrivialValue(net, c);
		if (val2 == null) {
			return false;
		}

		return val1 != val2;
	}

	@Override
	public boolean isTriviallyTrue(Network net, YAMLDComponent c) {
		final YAMLDValue val1 = _lhs.getTrivialValue(net, c);
		if (val1 == null) {
			return false;
		}
		final YAMLDValue val2 = _rhs.getTrivialValue(net, c);
		if (val2 == null) {
			return false;
		}

		return val1 == val2;
	}

    @Override
	public String toString() {
		return toFormattedString();
	}

	@Override
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state,
			YAMLDComponent comp) {
		// It should be possible to do equality testing with ==
		// (and this is what happens in effects with the current implementation)
		// but {link Object#equals(Object)} is cleaner.
		final YAMLDValue val1 = _lhs.partialValue(state, comp);
		if (val1 == null) {
			return THREE_VALUED_BOOL.UNDEFINED;
		}
		
		final YAMLDValue val2 = _rhs.partialValue(state, comp);
		if (val2 == null) {
			return THREE_VALUED_BOOL.UNDEFINED;
		}
		
		if (val1.equals(val2)) {
			return THREE_VALUED_BOOL.TRUE;
		}
		
		return THREE_VALUED_BOOL.FALSE;
	}

    @Override
    public void test(YAMLDComponent c, Network net) {
        _lhs.test(c,net);
        _rhs.test(c,net);
    }
}
