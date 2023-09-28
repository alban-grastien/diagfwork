package lang;

/**
 * A formula that is satisfied if there exists a path between two specified components, 
 * possibly through another specified component, such that all components 
 * (except the first and last components) on the path satisfy a specified formula.  
 * The difference with {@link YAMLDExistsPath} is that this class represents the 
 * components by their names (because at parsing time, the components may not have been 
 * created yet).  This is mostly an implementation issue that should be improved 
 * some time.
 * 
 * @author Alban Grastien 
 * @version 1.0
 * */
public class YAMLDStringExistsPath implements YAMLDFormula {
	
	/**
	 * The YAMLDExistsPath corresponding to this YAMLDFormula 
	 * (computed as soon as possible).  
	 * */
	private YAMLDExistsPath _ex = null;
	/**
	 * The name of the first component.  
	 * */
	private final String _first;
	/**
	 * The name of the last component.  
	 * */
	private final String _last;
	/**
	 * The name of the middle component (if any).  
	 * */
	private final String _mid;
	/**
	 * The type of the connections on the path.  
	 * */
	private final YAMLDConnType _connt;
	/**
	 * The formula that must be satisfied on the path.  
	 * */
	private final YAMLDFormula _f;
	
	public YAMLDStringExistsPath(String first, String last, String mid, 
			YAMLDConnType connt, YAMLDFormula f) {
		_first = first;
		_last = last;
		_mid = mid;
		_connt = connt;
		_f = f;
	}
	
	public YAMLDStringExistsPath(String first, String last,  
			YAMLDConnType connt, YAMLDFormula f) {
		_first = first;
		_last = last;
		_mid = null;
		_connt = connt;
		_f = f;
	}
	
	@Override
	public boolean satisfied(State state, YAMLDComponent con) {
		final YAMLDExistsPath e = simplify(state.getNetwork());
		return e.satisfied(state, con);
	}

	@Override
	public YAMLDExistsPath simplify(Network net) {
		if (_ex != null) {
			return _ex;
		}
		
		final YAMLDFormula f = _f.simplify(net);
		final YAMLDComponent first = net.getComponent(_first);
		final YAMLDComponent last = net.getComponent(_last);
		
		final YAMLDExistsPath result;
		if (_mid != null) {
			final YAMLDComponent mid = net.getComponent(_mid);
			result = new YAMLDExistsPath(net, first, last, mid, _connt, f);
		} else {
			result = new YAMLDExistsPath(net, first, last, _connt, f);
		}
		_ex = result;
		
		return result;
	}

	@Override
	public String toFormattedString() {
		return "exists " + _connt + "(" + _first + ".." + 
		((_mid != null)? _mid + ".." : "") 
		+ _last + ")" + "(" + _f.toFormattedString() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_connt == null) ? 0 : _connt.hashCode());
		result = prime * result + ((_f == null) ? 0 : _f.hashCode());
		result = prime * result + ((_first == null) ? 0 : _first.hashCode());
		result = prime * result + ((_last == null) ? 0 : _last.hashCode());
		result = prime * result + ((_mid == null) ? 0 : _mid.hashCode());
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
		if (!(obj instanceof YAMLDStringExistsPath)) {
			return false;
		}
		YAMLDStringExistsPath other = (YAMLDStringExistsPath) obj;
		if (_connt == null) {
			if (other._connt != null) {
				return false;
			}
		} else if (!_connt.equals(other._connt)) {
			return false;
		}
		if (_f == null) {
			if (other._f != null) {
				return false;
			}
		} else if (!_f.equals(other._f)) {
			return false;
		}
		if (_first == null) {
			if (other._first != null) {
				return false;
			}
		} else if (!_first.equals(other._first)) {
			return false;
		}
		if (_last == null) {
			if (other._last != null) {
				return false;
			}
		} else if (!_last.equals(other._last)) {
			return false;
		}
		if (_mid == null) {
			if (other._mid != null) {
				return false;
			}
		} else if (!_mid.equals(other._mid)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isTriviallyFalse(Network net, YAMLDComponent c) {
		final YAMLDExistsPath e = simplify(net);
		return e.isTriviallyFalse(net, c);
	}

	@Override
	public boolean isTriviallyTrue(Network net, YAMLDComponent c) {
		final YAMLDExistsPath e = simplify(net);
		return e.isTriviallyTrue(net, c);
	}

	@Override
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state,
			YAMLDComponent comp) {
		final YAMLDExistsPath e = simplify(state.getNetwork());
		return e.partiallySatisfied(state, comp);
	}

    @Override
    public void test(YAMLDComponent c, Network net) {
        simplify(net).test(c, net);
    }

}
