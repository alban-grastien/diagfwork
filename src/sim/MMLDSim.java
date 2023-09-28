package sim;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lang.MMLDRule;
import lang.MMLDSynchro;
import lang.MMLDTransition;
import lang.Network;
import lang.State;
import lang.PeriodInterval;
import lang.TimedState;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDFormula;
import edu.supercom.util.Pair;
import edu.supercom.util.PseudoRandom;
import lang.Period;
import util.DelayedScenario;
import util.EmptyTimedScenario;
import util.ExplicitGlobalTransition;
import util.IncrementalTimedScenario;
import util.MMLDGlobalTransition;
import util.Time;
import util.TimedScenario;

/**
 * Implements a simulator for the MMLD model. A simulator is composed of a timed
 * scenario and an expiration notice: the timed scenario indicates what happened
 * so far, the expiration notice forecasts what is supposed to happen next
 * (forced transitions). To allow branching, the expiration notices are kept.
 * */
public final class MMLDSim implements Simulator {

	/**
	 * The random generator used to generate the scenario.
	 * */
	private final PseudoRandom _random;

	/**
	 * The current scenario.
	 * */
	private TimedScenario _sce;

	/**
	 * The expiration notice that indicates what will happen next.
	 * */
	private ExpirationNotice _currentNotice;

	/**
	 * The list of notices so far (including {@link #_currentNotice}.
	 * TODO: Replace with a stack
	 * */
	private final List<ExpirationNotice> _notices;

	/**
	 * The list of scenarios so far.
	 * TODO: Replace with a stack
	 * */
	private final List<TimedScenario> _scenarios;

	/**
	 * Creates a simulator starting in the specified state at the specified time
	 * with the specified random generator.
	 * 
	 * @param s
	 *            the initial state.
	 * @param t
	 *            the initial time.
	 * @param r
	 *            the pseudo random generator used to break non deterministic
	 *            choices.
	 */
	public MMLDSim(TimedState s, Time t, PseudoRandom r) {
		_sce = new EmptyTimedScenario(s, t);
		_random = r;
		// _triggeringTime = new HashMap<Pair<MMLDTransition,YAMLDFormula>,
		// Double>();
		_currentNotice = new MapExpirationNotice();
		_notices = new ArrayList<ExpirationNotice>();
		_scenarios = new ArrayList<TimedScenario>();

		computeForced();
	}

	/**
	 * Creates a copy of this simulator.
	 * 
	 * @param sim
	 *            the simulator that is copied.
	 * */
	public MMLDSim(MMLDSim sim) {
		_sce = sim._sce;
		_random = sim._random;
		_notices = new ArrayList<ExpirationNotice>();
		_scenarios = new ArrayList<TimedScenario>();
		for (int i = 0; i < sim._notices.size(); i++) {
			final ExpirationNotice xp = sim._notices.get(i);
			final TimedScenario s = sim._scenarios.get(i);
			final State state = s.getLastState().getState();
			_currentNotice = new MapExpirationNotice(xp, state);
			_notices.add(_currentNotice);
			_scenarios.add(s);
		}

		if (_currentNotice == null) {
			System.err.println("No current notice!");
		}
	}

    /**
     * Builds a simulator for the specified scenario.  
     * The expiration notices are set to match the scenario.  
     * 
     * @param sce the scenario.  
     * @param r the pseudo random generator.  
     */
    public MMLDSim(TimedScenario ts, PseudoRandom r) {
        this(ts.getStateAfterTransition(-1),ts.getTime(-1),r);
        
        Time time = ts.getTime(-1);
        for (int i=0 ; i<ts.nbTrans() ; i++) {
            final Time nextTime = ts.getTime(i);
            Simulators.pushAllTriggerTime(this, nextTime);
            
            final MMLDGlobalTransition glob = ts.getTrans(i);
            //doElapseTime(nextTime - time);
            doElapseTime(new Period(time, nextTime));
            trigger(glob);
            time = nextTime;
        }
        
        final Time nextTime = ts.getTime(ts.nbTrans());
        Simulators.pushAllTriggerTime(this, nextTime);
        doElapseTime(new Period(time, nextTime));
    }
    
	// Computes when the forced transitions should take place
	// and determines which transition should take place first.
	private void computeForced() {
		final ExpirationNotice old = _currentNotice;
		_currentNotice = new MapExpirationNotice();
		_notices.add(_currentNotice);
		_scenarios.add(_sce);

		final TimedState currentState = _sce.getStateBeforeTransition(_sce.nbTrans());
		final Network net = currentState.getState().getNetwork();
		final Time currentTime = getCurrentTime();

		for (final Pair<MMLDTransition, YAMLDFormula> pair : net
				.getForcingPreconditionsWithTransition()) {
			final MMLDTransition trans = pair.first();
			final YAMLDFormula form = pair.second();
			final Time existing = old.willTrigger(form, trans);
			if (form.satisfied(currentState.getState(), trans.getComponent())) {
				final Time newVal;
				//if (existing < 0) {
				if (existing == null) {
					final PeriodInterval ti = trans.getConditionTime(form);
					newVal = new Time(currentTime, Period.getRandom(_random, ti.getBeginning(), ti.getEnd()));
                    //        currentTime + 
                    //        Simulators.random(_random, ti.getBeginning(), ti.getEnd());
				} else {
					newVal = existing;
				}
                safeSetTriggeringTime(trans, form, newVal);
			}
		}

	}

	// Elapses time without looking at what is supposed to happen.
	private void doElapseTime(Period d) {
		_sce = new DelayedScenario(_sce, d);
		//computeForced();
	}

    @Override
	public void trigger(MMLDGlobalTransition tr) {
		_sce = new IncrementalTimedScenario(_sce, tr);
		computeForced();
	}

	// Chooses non deterministically a rule from the specified transition.
	private MMLDRule chooseRule(MMLDTransition tr, State s) {
		final YAMLDComponent c = tr.getComponent();
		final List<MMLDRule> rules = new ArrayList<MMLDRule>();
		for (final MMLDRule r : tr.getRules()) {
			if (r.getCondition().satisfied(s, c)) {
				rules.add(r);
			}
		}

		if (rules.isEmpty()) {
			rules.add(tr.getDefaultRule());
		}

		final int n = _random.rand(rules.size());
		return rules.get(n);
	}

	// Chooses non deterministically a rule from the input event.
	private MMLDRule chooseRule(YAMLDEvent in, State s) {
		final List<MMLDTransition> transes = in.getTriggerableTransitions();
		final MMLDTransition tr = transes.get(_random.rand(transes.size()));
		return chooseRule(tr, s);
	}

	private void chooseRules(Map<YAMLDComponent, MMLDRule> map,
			Deque<YAMLDEvent> events) {
		final State s = _sce.getLastState().getState();
		while (!events.isEmpty()) {
			final YAMLDEvent out = events.pop();

			for (final MMLDSynchro sy : out.getSynchros()) {
				for (final YAMLDEvent in : sy.getSynchronizedEvents()) {
					final MMLDRule r = chooseRule(in, s);
					final MMLDRule oldR = map.put(in.getComponent(), r);
					if (oldR != null) {
						throw new Error(
								"An event was already chosen for component "
										+ in.getComponent().name());
					}
					for (final YAMLDEvent newOut : r.getGeneratedEvents()) {
						events.push(newOut);
					}
				}
			}
		}
	}

    @Override
	public Period nextForcedTransitionDelay() {
		final Pair<YAMLDFormula, MMLDTransition> p = _currentNotice
				.nextTrigger();
		if (p == null) {
            return Period.NO_PERIOD;
			//return -1;
		}
		return new Period(_sce.getFinalTime(),_currentNotice.willTrigger(p.first(), p.second()));

		// return _nextTriggerTime - _sce.getFinalTime();
	}

    @Override
	public Period elapseTime(Period period) {
		final Time currentTime = getCurrentTime();
		final Pair<YAMLDFormula, MMLDTransition> p = 
                _currentNotice.nextTrigger();
		if (p == null) {
			doElapseTime(period);
			return period;
		}

		final Time nextTrigger = 
                _currentNotice.willTrigger(p.first(), p.second());

        // TODO: Get rid of this 0.001
		if (new Time(currentTime,period).isBefore(new Time(nextTrigger,new Period(0.001)))) {
        	doElapseTime(period);
			return period;
		}

		final Period result = new Period(currentTime,nextTrigger);
		doElapseTime(result);
		triggerTransition(p.second());
		return result;
	}

	/**
	 * Triggers the specified transition at the current time. A precondition of
	 * the transition should be satisfied and it may be a forced transition if
	 * the precondition has been satisfied for long enough. Triggering the
	 * transition may lead to a cascade of other transitions that are chosen not
	 * deterministically.
	 * 
	 * @param tr
	 *            the transition that is triggered.
	 * */
	private void triggerTransition(MMLDTransition tr) {
		final Map<YAMLDComponent, MMLDRule> map = new HashMap<YAMLDComponent, MMLDRule>();

		final State s = _sce.getLastState().getState();
		final MMLDRule r = chooseRule(tr, s);
		map.put(tr.getComponent(), r);
		final Deque<YAMLDEvent> open = new ArrayDeque<YAMLDEvent>();
		for (final YAMLDEvent out : r.getGeneratedEvents()) {
			open.push(out);
		}

		chooseRules(map, open);
		final MMLDGlobalTransition gtrans = new ExplicitGlobalTransition(map);
		trigger(gtrans);
	}

	/**
	 * Triggers the specified set of rules at the current time. The first rule
	 * should correspond to a spontaneous or a forced transition. The other ones
	 * should be logical consequences of one of the previous rules. The list of
	 * rules may be incomplete, in which case the other rules are chosen non
	 * deterministically.
	 * 
	 * @param rls
	 *            the rules that are triggered.
	 * */
    // TODO: Remove ?
	private void triggerTransition(MMLDRule... rls) {
		final Map<YAMLDComponent, MMLDRule> map = new HashMap<YAMLDComponent, MMLDRule>();
		for (final MMLDRule rl : rls) {
			map.put(rl.getComponent(), rl);
		}
		final Deque<YAMLDEvent> open = new ArrayDeque<YAMLDEvent>();
		for (final MMLDRule rl : rls) {
			for (final YAMLDEvent newOut : rl.getGeneratedEvents()) {
				if (map.containsKey(newOut.getComponent())) {
					continue;
				}
				open.push(newOut);
			}
		}

		chooseRules(map, open);
		final MMLDGlobalTransition gtrans = new ExplicitGlobalTransition(map);
		trigger(gtrans);
	}

    @Override
	public Time getCurrentTime() {
		return _sce.getTime(_sce.nbTrans());
	}

    @Override
	public TimedScenario getScenario() {
		return _sce;
	}

    @Override
	public void triggerNext() {
		final Pair<YAMLDFormula, MMLDTransition> p = _currentNotice
				.nextTrigger();
		if (p == null) {
			return;
		}

		final Period delay = new Period(
                _sce.getFinalTime(), 
                _currentNotice.willTrigger(p.first(), p.second())
                );

		doElapseTime(delay);
		triggerTransition(p.second());
	}

    @Override
	public void cut(int i) {
		int size = _scenarios.size();
		while (size > i + 1) {
			size--;
			_scenarios.remove(size);
			_notices.remove(size);
		}

		_sce = _scenarios.get(size-1);
		_currentNotice = _notices.get(size-1);
	}
	
    @Override
	public Time willTrigger(YAMLDFormula f, MMLDTransition t) {
		return _currentNotice.willTrigger(f, t);
	}
	
    @Override
	public TimedScenario get(int pos) {
		return _scenarios.get(pos);
	}

    @Override
    public void setTriggeringTime(MMLDTransition tr, YAMLDFormula f, Time t) {
        if (!Simulators.isPossibleTriggeringTime(this,tr, f, t)) {
            throw new IllegalArgumentException(
                    "Cannot set triggering time to " + t 
                    + " for transition " + tr.getName() 
                    + " in component " + tr.getComponent().name());
        }
        
        safeSetTriggeringTime(tr, f, t);
    }
    
    // Safe version (assumes the values are within range)
    private void safeSetTriggeringTime(MMLDTransition tr, YAMLDFormula f, Time t) {
        assert Simulators.isPossibleTriggeringTime(this,tr, f, t);
        
        _currentNotice.setTrigger(f, tr, t);
    }
    
    @Override
    public PseudoRandom getRandom() {
        return _random;
    }

    @Override
    public MMLDTransition nextForcedTransition() {
		final Pair<YAMLDFormula, MMLDTransition> p = _currentNotice
				.nextTrigger();
		if (p == null) {
			return null;
		}
        
        return p.second();
    }

    @Override
    public Network getNetwork()  {
        return getScenario().getLastState().getState().getNetwork();
    }

    @Override
    public Simulator copy() {
        return new MMLDSim(this);
    }
}
