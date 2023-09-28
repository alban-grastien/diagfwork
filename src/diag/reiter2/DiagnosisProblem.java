package diag.reiter2;

import lang.Network;
import lang.State;
import util.AlarmLog;

/**
 * A <code>DiagnosisProblem</code>, i.e., a diagnosis problem, 
 * is a tuple <code>&lt;Network, State, AlarmLog, HypothesisSpace&gt;</code>.  
 * 
 * @param <H> the type of hypothesis.  
 */
public class DiagnosisProblem<H extends Hypothesis, HS extends HypothesisSpace<H>> {
    
    /**
     * The network.  
     */
    private final Network _net;
    
    /**
     * The initial state.  
     */
    private final State _state;
    
    /**
     * The alarm log.  
     */
    private final AlarmLog _log;
    
    /**
     * The hypothesis space.  
     */
    private final HS _space;
    
    public DiagnosisProblem(Network net, State state, AlarmLog log, HS space) {
        _net = net;
        _state = state;
        _log = log;
        _space = space;
    }
    
    public Network getNetwork() {
        return _net;
    }
    
    public State getInitialState() {
        return _state;
    }
    
    public AlarmLog getAlarmLog() {
        return _log;
    }
    
    public HS getHypothesisSpace() {
        return _space;
    }
}
