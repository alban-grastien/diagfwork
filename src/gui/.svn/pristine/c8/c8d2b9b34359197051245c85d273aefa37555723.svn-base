package gui;

import edu.supercom.util.Options;
import edu.supercom.util.PseudoRandom;
import edu.supercom.util.SeededPseudoRandom;
import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lang.MMLDTransition;
import lang.MMLDlightParser;
import lang.MapTimedState;
import lang.Network;
import lang.Period;
import lang.TimedState;
import lang.VisMap;
import lang.YAMLDComponent;
import lang.YAMLDEvent;
import lang.YAMLDFormula;
import sim.MMLDSim;
import sim.Simulator;
import sim.Simulators;
import util.MMLDGlobalTransition;
import util.Time;
import util.TimedScenario;

/**
 * A <code>ScenarioSelecter</code>, i.e., a scenario selecter, 
 * is an object that is used to swap between scenarios.  
 * Each scenario is represented by a simulator and identified by a string key.  
 */
public class ScenarioSelecter implements NetworkListener, InteractiveInterface {
    
    /**
     * The list of simulators (mapped with the key).  
     */
    final Map<String,Simulator> _sims;
    /**
     * The key of the current simulator.  
     */
    private String _currentKey;
    /**
     * The list of scenario selecter listeners 
     * that monitor the scenario selected.  
     */
    private final Set<ScenarioSelecterListener> _list;
    
    private final Set<StateListener> _stateListeners;
    
    private final Set<NetworkListener> _networkListeners;
    
    private int _pos = 0;
    
    private final PseudoRandom _rand;

    private final Map<Network,UnmodifiableSet<YAMLDEvent>> _impossibleEvents;
    
    private final Options _opt;
    
    private Network _currentNetwork;

    /**
     * Creates an empty scenario selecter.  
     */
    public ScenarioSelecter(Options opt) {
        _opt = opt;
        _sims = new HashMap<String, Simulator>();
        _currentKey = null;
        _list = new HashSet<ScenarioSelecterListener>();
        _stateListeners = new HashSet<StateListener>();
        {
            final String sseed = GuiOptions.GUI_SEED.getOption(opt, null, false, "NOTHING");
            final int seed = sseed.hashCode();
            _rand = new SeededPseudoRandom(seed);
        }
        _impossibleEvents = new HashMap<Network, UnmodifiableSet<YAMLDEvent>>();
        _networkListeners = new HashSet<NetworkListener>();
    }
    
    /**
     * Returns the current simulator.  
     * 
     * @return the current simulator, 
     * or <code>null</code> if there is none.  
     */
    public Simulator getCurrentSimulator() {
        if (_currentKey == null) {
            return null;
        }
        
        return _sims.get(_currentKey);
    }
    
    /**
     * Returns the key of the current simulator.  
     * 
     * @return the key of current simulator, 
     * or <code>null</code> if there is none.  
     */
    public String getCurrentKey() {
        return _currentKey;
    }
    
    /**
     * Returns the list of keys in this scenario selecter.  
     * 
     * @return all the keys that can be accessed through this selecter.  
     */
    public Set<String> getKeys() {
        return Collections.unmodifiableSet(_sims.keySet());
    }
    
    /**
     * Sets the current scenario selecter to the one with the specified key.  
     * If there is no scenario associated with the specified key, 
     * the scenario is not changed.  
     * 
     * @param simKey the key of the scenario we want to select.  
     * @return the simulator selected at the end 
     */
    public Simulator selectSimulator(String simKey) {
        if (!_sims.containsKey(simKey)) {
            return getCurrentSimulator();
        }

        final Network oldNetwork = getNetwork();
        _currentKey = simKey;
        final Network newNetwork = getNetwork();
        if (newNetwork != oldNetwork) {
            _currentNetwork = newNetwork;
            notifyNetworkChanged();
        }
        notifySimulatorSelected();
        last();
        return getCurrentSimulator();
    }
    
    /**
     * Adds the specified simulator to this selecter.  
     * Notice that the newly added simulator 
     * is <b>not</b> automatically selected.  
     * 
     * @param sim the simulator added to the selecter.  
     * @param key the key used for the specified simulator.  
     * @return <code>true</code> if the simulator is successfully added, 
     * <code>false</code> otherwise (if the key is already associated 
     * with another simulator).  
     */
    public boolean addSimulator(Simulator sim, String key) {
        if (_sims.containsKey(key)) {
            return false;
        }
        
        _sims.put(key, sim);
        notifySimulatorAdded(key);
        return true;
    }
    
    public boolean addSimulator(TimedScenario ts, String name) {
        final Simulator sim = new MMLDSim(ts, _rand);
        return addSimulator(sim, name);
    }
    
    /**
     * Removes the specified simulator to this selecter.  
     * 
     * @param key the key of the simulator removed.  
     * @return <code>true</code> if the simulator is successfully removed, 
     * <code>false</code> otherwise (if the key does not exist).  
     */
    public boolean removeSimulator(String key) {
        if (!_sims.containsKey(key)) {
            return false;
        }
        
        _sims.remove(key);
        notifySimulatorRemoved();
        return true;
    }
    
    /**
     * Adds the specified listener to the list of scenario selecter listener 
     * that should monitor this selecter.  
     * 
     * @param l the listener to add.  
     * @return <code>true</code> if successfully added, 
     * <code>false</code> otherwise (if the listener 
     * already belongs to the list).  
     */
    public boolean addScenarioSelecterListener(ScenarioSelecterListener l) {
        return _list.add(l);
    }
    
    /**
     * Removes the specified listener from the list of scenario selecter listener 
     * that should monitor this selecter.  
     * 
     * @param l the listener to remove.  
     * @return <code>true</code> if successfully removed, 
     * <code>false</code> otherwise (if the listener 
     * did not belong to the list).  
     */
    public boolean removeScenarioSelecterListener(ScenarioSelecterListener l) {
        return _list.remove(l);
    }

    @Override
    public void addStateListener(StateListener l) {
        if (l == null) {
            throw new NullPointerException();
        }
        
        _stateListeners.add(l);
    }

    @Override
    public void removeStateListener(StateListener l) {
        if (l == null) {
            throw new NullPointerException();
        }
        
        _stateListeners.remove(l);
    }
    
    /**
     * Changes the name of the specified simulator.  
     * 
     * @param oldName the current name of the simulator that is changed.  
     * @param newName the new name of the simulator.  
     * @return <code>true</code> if the change was possible, 
     * <code>false</code> otherwise (for instance, 
     * if another simulator has the same name).  
     */
    public boolean changeSimulatorName(String oldName, String newName) {
        if (newName == null) {
            System.out.println("SS >> newName = null");
            return false;
        }
        
        if (oldName.equals(newName)) {
            System.out.println("SS >> same name");
            return false;
        }
        
        if (_sims.containsKey(newName)) {
            System.out.println("SS >> newName exists");
            return false;
        }
        
        final Simulator sim = _sims.remove(oldName);
        if (sim == null) {
            return false;
        }
        
        _sims.put(newName, sim);
        if (_currentKey.equals(oldName)) {
            _currentKey = newName;
        }
        notifySimulatorNameChanged(oldName, newName, sim);
        return true;
    }
    
    @Override
    public void newNetwork(Network n) {
        _currentNetwork = n;
        
        final TimedState initialState = new MapTimedState(MMLDlightParser.st);
        
        final List<String> pats = GuiOptions.IMPOSSIBLE_EVENTS_PATTERNS.getOptions(_opt);
        final Set<YAMLDEvent> impossibleEvents = new HashSet<YAMLDEvent>();
        impossibleEvents.addAll(util.Util.findEvents(n, pats));
        final UnmodifiableSetConstructor<YAMLDEvent> con = 
                new UnmodifiableSetConstructor<YAMLDEvent>(impossibleEvents);
        _impossibleEvents.put(n, con.getSet());
        
        notifyNetworkChanged();
        
        final Simulator _msim = new MMLDSim(initialState, Time.ZERO_TIME, _rand);
        final String initialKey = "Initial scenario";
        addSimulator(_msim, initialKey);
        selectSimulator(initialKey);
    }
    
    public void first() {
        final TimedScenario sce = getCurrentSimulator().getScenario();
        _pos = 0;
        notifyStateChanged();
    }
    
    public void previous() {
        if (_pos != 0) {
            _pos--;
        }
        notifyStateChanged();
    }
    
//    public void next() {
//        final TimedScenario sce = getCurrentSimulator().getScenario();
//        if (_pos != sce.nbTrans()) {
//            _pos++;
//        }
//        notifyStateChanged();
//    }
    
    public void last() {
        final TimedScenario sce = getCurrentSimulator().getScenario();
        _pos = sce.nbTrans();
        notifyStateChanged();
    }

    @Override
    public boolean canBeTriggered(MMLDTransition tr) {
        final Network n = getNetwork();
        final Simulator sim = getCurrentSimulator();
        final TimedScenario sce = sim.getScenario();
        final TimedState state = sce.getLastState();
        final UnmodifiableSet<YAMLDEvent> impossibleEvents = _impossibleEvents.get(n);
        final MMLDGlobalTransition gt = util.Util.computeGlobalTransition(
                tr, n, state.getState(), impossibleEvents, _rand);
        return gt != null;
    }

    @Override
    public void trigger(MMLDTransition tr) {
        final Network n = getNetwork();
        final Simulator sim = getCurrentSimulator();
        final TimedScenario sce = sim.getScenario();
        final TimedState state = sce.getLastState();
        final UnmodifiableSet<YAMLDEvent> impossibleEvents = _impossibleEvents.get(n);
        final MMLDGlobalTransition gt = util.Util.computeGlobalTransition(
                tr, n, state.getState(), impossibleEvents, _rand);
        if (gt != null) {
            sim.trigger(gt);
            _pos++;
        }
        
        notifyStateChanged();
    }

    @Override
    public void elapseTime(Period t) {
        final Simulator sim = getCurrentSimulator();
		Period time = Period.ZERO_PERIOD;
		do {
			final Period elapsing = sim.elapseTime(t);
//			System.out.println("ScenarioManager >> Elapsing " + elapsing);
			time = Period.add(time,elapsing);
		} while (t.isLonger(time));
		last();
    }

    @Override
    public void nextEvent() {
        if (isInFinalState()) {
            final Simulator sim = getCurrentSimulator();
            sim.triggerNext();
        }
        
        final TimedScenario sce = getCurrentSimulator().getScenario();
        if (_pos != sce.nbTrans()) {
            _pos++;
            notifyStateChanged();
        }
    }

    @Override
    public boolean elapseOrOneForcedTransition(Period t) {
        final Simulator sim = getCurrentSimulator();
		boolean result = (sim.elapseTime(t) == t);
		last();
		return result;
    }

    @Override
    public boolean setFirst(MMLDTransition tr, YAMLDFormula f) {
        final Simulator sim = getCurrentSimulator();
        final Network net = getNetwork();
                    
		final Time nextTime;
		{
            final Time min = Simulators.minTimeNextForcedTransition(sim);
            final Time max = Simulators.maxTimeNextForcedTransition(sim);
            
            if (!min.isBefore(max)) {
                return false;
            }
            nextTime = Time.getRandom(_rand, min, max);
		}
        
        final TimedState s = getFinalState();
        for (final YAMLDComponent c2: net.getComponents()) {
            for (final MMLDTransition tr2: c2.transitions()) {
                for (final YAMLDFormula f2: tr2.getPreconditions()) {
                    if (f2.satisfied(s.getState(), c2)) {
                        Simulators.pushTriggeringTime(sim, tr2, f2, nextTime);
                    }
                }
            }
        }
        
        sim.setTriggeringTime(tr, f, nextTime);
        return true;
    }

    @Override
    public Network getNetwork() {
        return _currentNetwork;
    }

    @Override
    public boolean isInFinalState() {
        final Simulator sim = getCurrentSimulator();
        final TimedScenario sce = sim.getScenario();
        return sce.nbTrans() == _pos;
    }

    @Override
    public TimedState getFinalState() {
        final Simulator sim = getCurrentSimulator();
        final TimedScenario sce = sim.getScenario();
        return sce.getLastState();
    }

    @Override
    public Time getCurrentTime() {
        final Simulator sim = getCurrentSimulator();
        final TimedScenario sce = sim.getScenario();
        return sce.getTime(_pos);
    }

    public TimedState getCurrentState() {
        final Simulator sim = getCurrentSimulator();
        final TimedScenario sce = sim.getScenario();
        return sce.getStateBeforeTransition(_pos);
    }

    public Time getFinalTime() {
        final Simulator sim = getCurrentSimulator();
        final TimedScenario sce = sim.getScenario();
        return sce.getFinalTime();
    }

    @Override
    public Time willTrigger(YAMLDFormula pr, MMLDTransition tr) {
        final Simulator sim = getCurrentSimulator();
        return sim.willTrigger(pr, tr);
    }

    @Override
    public void addNetworkListener(NetworkListener sl) {
        if (sl == null) {
            throw new NullPointerException();
        }
        _networkListeners.add(sl);
    }

    @Override
    public void removeNetworkListener(NetworkListener sl) {
        if (sl == null) {
            throw new NullPointerException();
        }
        _networkListeners.remove(sl);
    }

    @Override
    public void setSchedule(MMLDTransition trans, YAMLDFormula f, Time d) {
        final Simulator sim = getCurrentSimulator();
        sim.setTriggeringTime(trans, f, d);
    }

    @Override
    public void chooseComponent(YAMLDComponent c) {
        // TODO: Useless?
        //_currentComponent = c;
    }
    
    private void notifySimulatorSelected() {
        final Simulator currentSim = getCurrentSimulator();
        for (final ScenarioSelecterListener ssl: _list) {
            ssl.simulatorSelected(_currentKey,currentSim);
        }
    }
    
    private void notifySimulatorAdded(String key) {
        final Simulator addedSim = _sims.get(key);
        for (final ScenarioSelecterListener ssl: _list) {
            ssl.simulatorAdded(key,addedSim);
        }
    }
    
    private void notifySimulatorRemoved() {
        final Simulator currentSim = getCurrentSimulator();
        for (final ScenarioSelecterListener ssl: _list) {
            ssl.simulatorRemoved(_currentKey,currentSim);
        }
    }
    
    private void notifySimulatorNameChanged(
            String oldName, String newName, Simulator sim) {
        for (final ScenarioSelecterListener ssl: _list) {
            ssl.simulatorKeyChanged(oldName, newName, sim);
        }
    }

    public void notifyStateChanged() {
        final TimedScenario sce = getCurrentSimulator().getScenario();
        final TimedState state = sce.getStateBeforeTransition(_pos);
        
        VisMap.getSingletonObject().determineChangedShapes(state.getState());
        for (final StateListener l : _stateListeners) {
            l.newStateHandler(state);
        }
    }
    
    public void notifyNetworkChanged() {
        final Network net = getNetwork();
        for (final NetworkListener l: _networkListeners) {
            l.newNetwork(net);
        }
    }

    @Override
    public int nbTransitions() {
        return getCurrentSimulator().getScenario().nbTrans();
    }

    @Override
    public int getCurrentTransitionPosition() {
        return _pos;
    }
    
    /**
     * Generates a copy of the current simulator.  
     */
    public void copyCurrent() {
        final Simulator sim = getCurrentSimulator();
        final Simulator newSimulator = sim.copy();
        
        final String currentSimName = getCurrentKey();
        final String newName;
        {
            int i = 1;
            while (_sims.containsKey(currentSimName + "(" + i + ")")) {
                i++;
            }
            newName = currentSimName + "(" + i + ")";
        }
        
        addSimulator(newSimulator, newName);
        selectSimulator(newName);
    }
    
    /**
     * Cuts the current simulator at its current position.  
     */
    public void cut() {
        final Simulator sim = getCurrentSimulator();
        sim.cut(getCurrentTransitionPosition());
        notifyStateChanged();
    }
}
 