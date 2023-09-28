package lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.YAMLDFormula.THREE_VALUED_BOOL;

import util.MMLDGlobalTransition;
import util.TransStateModification;

import edu.supercom.util.Pair;

/**
 * An explicit state is an implementation of a state that is based on the
 * explicit enumeration of the value of each state variable. However, the value
 * of the dependent variables is not explicitly calculated but calculated in a
 * lazy manner: only if it is necessary; once it has been calculated though, it
 * is stored. This implementation does not do anything fancy about memory
 * management.
 * 
 * @author Alban Grastien
 * @version 1.0
 * @todo split this class in ExplicitPartialState (mostly this implementation) 
 * and ExplicitState (that uses ExplicitPartialState).  
 * */
public class ExplicitState extends AbstractState implements State, PartialState {

	/**
	 * The value of each state variable.
	 * */
	private final Map<YAMLDVar, YAMLDValue> _coreAss;
	/**
	 * The value of the dependent variable that were computed.
	 * */
	private final Map<YAMLDDVar, YAMLDValue> _depAss;
	/**
	 * The network.
	 * */
	private final Network _net;

	/**
	 * Builds a state with the specified assignments. Right now, the constructor
	 * is defensive: the dependent variables of the specified map are pruned; on
	 * the other hand, whether all variables have been instantiated is not
	 * tested.
	 * 
	 * @param net
	 *            the {@link Network} for which this state is defined.
	 * @param ass
	 *            the assignment of the state variables.
	 * */
	public ExplicitState(Network net,
			Map<? extends YAMLDGenericVar, YAMLDValue> ass) {
		_net = net;
		_depAss = new HashMap<YAMLDDVar, YAMLDValue>();
		_coreAss = new HashMap<YAMLDVar, YAMLDValue>();
		for (Map.Entry<? extends YAMLDGenericVar, YAMLDValue> entry : ass
				.entrySet()) {
			final YAMLDGenericVar var = entry.getKey();
			if (!(var instanceof YAMLDVar)) {
				continue;
			}
			final YAMLDVar stateVar = (YAMLDVar) var;
			_coreAss.put(stateVar, entry.getValue());
		}
	}

	/**
	 * Builds a state that differs from the previous explicit state only by the
	 * specified set of assignments.
	 * <p />
	 * <b>Remark</b>: In the current implementation, the map that stores the
	 * state is copied. Furthermore, the assignment of the dependent variables
	 * is empty even if these variables are not affected by the new assignments.
	 * 
	 * @param state
	 *            the previous state.
	 * @param ass
	 *            the assignments erase the previous assignments.
	 * @deprecated Use {@link ExplicitState#ExplicitState(ExplicitState, StateModification)} 
	 * e.g. with {@link MapStateModification}.  
	 * */
	public ExplicitState(ExplicitState state,
			Map<? extends YAMLDGenericVar, YAMLDValue> ass) {
		_net = state._net;
		_depAss = new HashMap<YAMLDDVar, YAMLDValue>();
		_coreAss = new HashMap<YAMLDVar, YAMLDValue>(state._coreAss);
		for (Map.Entry<? extends YAMLDGenericVar, YAMLDValue> entry : ass
				.entrySet()) {
			final YAMLDGenericVar var = entry.getKey();
			if (!(var instanceof YAMLDVar)) {
				continue;
			}
			final YAMLDVar stateVar = (YAMLDVar) var;
			_coreAss.put(stateVar, entry.getValue());
		}
	}

	/**
	 * Builds a state that differs from the previous explicit state only by the
	 * specified state modification.
	 * <p />
	 * <b>Remark</b>: In the current implementation, the map that stores the
	 * state is copied. Furthermore, the assignment of the dependent variables
	 * is empty even if these variables are not affected by the new assignments.
	 * 
	 * @param state
	 *            the previous state.
	 * @param sm
	 *            the modification to the state variables.
	 * */
	public ExplicitState(ExplicitState state,
			StateModification sm) {
		_net = state._net;
		_depAss = new HashMap<YAMLDDVar, YAMLDValue>();
		_coreAss = new HashMap<YAMLDVar, YAMLDValue>(state._coreAss);
		for (Pair<YAMLDVar, YAMLDValue> pair: sm) {
			_coreAss.put(pair.first(), pair.second());
		}
	}

	/**
	 * Builds a state that differs from the previous state only by the specified
	 * set of assignments.
	 * <p />
	 * <b>Remark</b>: In the current implementation, the map that stores the
	 * state is copied. Furthermore, the assignment of the dependent variables
	 * is empty even if these variables are not affected by the new assignments.
	 * 
	 * @param state
	 *            the previous state.
	 * @param ass
	 *            the assignments erase the previous assignments.
	 * @deprecated Use {@link ExplicitState#ExplicitState(ExplicitState, StateModification)} 
	 * e.g. with {@link MapStateModification}.  
	 * */
	public ExplicitState(State state,
			Map<? extends YAMLDGenericVar, YAMLDValue> ass) {
		_net = state.getNetwork();
		_depAss = new HashMap<YAMLDDVar, YAMLDValue>();
		_coreAss = new HashMap<YAMLDVar, YAMLDValue>();
		for (final YAMLDVar var : state.getNetwork().getStateVariables()) {
			YAMLDValue val = ass.get(var);
			if (val == null) {
				state.getValue(var);
			}
			_coreAss.put(var, val);
		}
	}

	@Override
	public YAMLDValue getValue(YAMLDGenericVar var) {
		if (var instanceof YAMLDVar) {
			final YAMLDVar stateVar = (YAMLDVar) var;
			return getValue(stateVar);
		}

		if (var instanceof YAMLDDVar) {
			final YAMLDDVar dvar = (YAMLDDVar) var;
			return getValue(dvar);
		}

		throw new IllegalStateException("Variable '" + var
				+ "' is neither a state variable nor a dependent variable.");
	}

	/**
	 * Returns the value of the specified state variable.
	 * 
	 * @param var
	 *            a state variable.
	 * @return the assignment of <code>var</code>.
	 * */
	public YAMLDValue getValue(YAMLDVar var) {
		return _coreAss.get(var);
	}

	/**
	 * Returns the value of the specified dependent variable.
	 * 
	 * @param var
	 *            a dependent variable.
	 * @return the assignment of <code>var</code>.
	 * */
        public YAMLDValue getValue(YAMLDDVar var) {
		{
			final YAMLDValue result = _depAss.get(var);
			if (result != null) {
				return result;
			}
		}

		for (final YAMLDConstraint con : var.getConstraints()) {
			// Note that the next line may induce new calls to getValue.
			// However this is safe if the variables are stratified.
			if (con.getPrecondition().satisfied(this, var.getComponent())) {
				final YAMLDExpr expr = con.getAssignment();
				final YAMLDValue result = expr.value(this, null);
				_depAss.put(var, result);
				return result;
			}
		}

		throw new IllegalStateException(
				"No constraint is satisfiable for variable '"
						+ var.getComponent().name() + "." + var.name() + "'.");
	}

	@Override
	public Network getNetwork() {
		return _net;
	}

	@Override
	public String completeFormattedString() {
		final StringBuilder buf = new StringBuilder();
		for (final YAMLDComponent comp : _net.getComponents()) {
			buf.append("// Component '").append(comp.name()).append("'\n");
			{
				final Collection<YAMLDVar> vars = comp.vars();
				if (!vars.isEmpty()) {
					buf.append("// State variables\n");
				}
				for (final YAMLDVar var : vars) {
					final YAMLDValue val = this.getValue(var);
					buf.append(comp.name());
					buf.append(".");
					buf.append(var.name());
					buf.append(" := ");
					buf.append(val.toFormattedString());
					buf.append(";");
					buf.append("\n");
				}
			}
			{
				final Collection<YAMLDDVar> dvars = comp.dvars();
				if (!dvars.isEmpty()) {
					buf.append("// Dependent variables\n");
				}
				for (final YAMLDDVar dvar : dvars) {
					final YAMLDValue val = this.getValue(dvar);
					buf.append(comp.name());
					buf.append(".");
					buf.append(dvar.name());
					buf.append(" := ");
					buf.append(val.toFormattedString());
					buf.append(";");
					buf.append("\n");
				}
			}
		}
		return buf.toString();
	}

	@Override
	public String toFormattedString() {
		final StringBuilder buf = new StringBuilder();
		for (final YAMLDComponent comp : _net.getComponents()) {
			buf.append("// Component '").append(comp.name()).append("'\n");
			for (final YAMLDVar var : comp.vars()) {
				final YAMLDValue val = this.getValue(var);
				final StringBuilder tmp = new StringBuilder();
				tmp.append(comp.name());
				tmp.append(".");
				tmp.append(var.name());
				tmp.append(" := ");
				tmp.append(val.toFormattedString());
				tmp.append(";");

				buf.append(tmp).append("\n");
			}
		}
		return buf.toString();
	}

	@Override
	@Deprecated
	public ExplicitState apply(Map<? extends YAMLDVar, YAMLDValue> m) {
		return new ExplicitState(this, m);
	}

	@Override
	public State apply(StateModification stateMod) {
		return new ExplicitState(this, stateMod);
	}
	
	@Override
	public String toString() {
		return _coreAss.toString();
	}

	@Override
	public State applyMMLDGlobalTransitions(List<MMLDGlobalTransition> transes) {
		State curr = this, next;
		for (MMLDGlobalTransition trans : transes) {
			TransStateModification tsm = new TransStateModification(trans, curr);
			next = curr.apply(tsm);
			curr = next;
		}
		return curr;
	}

	@Override
	public YAMLDValue getPartialValue(YAMLDGenericVar var) {
		if (var instanceof YAMLDVar) {
            final YAMLDVar v = (YAMLDVar)var;
			return _coreAss.get(v);
		}
		
		final YAMLDDVar v = (YAMLDDVar)var;
		
		if (_depAss.containsKey(v)){
			return _depAss.get(v);
		}
		
		for (final YAMLDConstraint con : v.getConstraints()) {
			// Note that the next line may induce new calls to getValue.
			// However this is safe if the variables are stratified.
			if (con.getPrecondition().partiallySatisfied(this, var.getComponent()) 
					== THREE_VALUED_BOOL.TRUE) {
				final YAMLDExpr expr = con.getAssignment();
				final YAMLDValue result = expr.value(this, null);
				_depAss.put(v, result);
				return result;
			}
		}

		_depAss.put(v, null);
		return null;
	}
    
    @Override
    public int hashCode() {
        return _coreAss.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        
        if (o == null) {
            return false;
        }
        
        if (o instanceof ExplicitState) {
            final ExplicitState other = (ExplicitState)o;
            return _coreAss.equals(other);
        }
        
        if (o instanceof PartialState) {
            final PartialState other = (PartialState)o;
        
            for (final YAMLDComponent c: _net.getComponents()) {
                for (final YAMLDVar v: c.vars()) {
                    final YAMLDValue v1 = this.getPartialValue(v);
                    final YAMLDValue v2 = other.getPartialValue(v);
                    if (v1 == v2) {
                        continue;
                    }
                    if (v1 == null) {
                        return false;
                    }
                    if (v1.equals(v2)) {
                        continue;
                    }
                    return false;
                }
            }
            
            return true;
        }
        
        if (o instanceof State) {
            final State other = (State)o;
        
            for (final YAMLDComponent c: _net.getComponents()) {
                for (final YAMLDVar v: c.vars()) {
                    final YAMLDValue v1 = this.getPartialValue(v);
                    if (v1 == null) {
                        return false;
                    }
                    
                    final YAMLDValue v2 = other.getValue(v);
                    if (!v1.equals(v2)) {
                        return false;
                    }
                }
            }
            
            return true;
        }
        
        return false;
    }
}
