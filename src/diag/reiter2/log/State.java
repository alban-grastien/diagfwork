package diag.reiter2.log;

/**
 * A <code>State</code>, i.e., 
 */
public class State implements Log {

    private final String _state;
    
    public State(String state) {
        _state = state;
    }
    
    @Override
    public String stringLog() {
        return _state;
    }
    
}
