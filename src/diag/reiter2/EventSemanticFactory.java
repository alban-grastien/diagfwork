package diag.reiter2;

import diag.EventOccurrence;
import edu.supercom.util.sat.TimedSemanticFactory;
import edu.supercom.util.sat.VariableSemantics;
import lang.YAMLDEvent;

/**
 * A <code>EventSemanticFactory</code>, i.e., an event semantic factory, 
 * is a time semantic factory that generates event semantics.  
 */
public class EventSemanticFactory implements TimedSemanticFactory<YAMLDEvent> {

    @Override
    public VariableSemantics buildSemantics(YAMLDEvent x, int t) {
        return new EventOccurrence(x, t);
    }
    
}
//
//class EventSemantic implements VariableSemantics {
//    
//    private final YAMLDEvent _event;
//    private final int _time;
//    
//    public EventSemantic(YAMLDEvent event, int time) {
//        if (event == null) {
//            throw new NullPointerException();
//        }
//        _event = event;
//        _time = time;
//    }
//    
//    @Override
//    public int hashCode() {
//        return Pair.hashCode(_event, _time);
//    }
//    
//    @Override
//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        }
//        
//        if (!(o instanceof EventSemantic)) {
//            return false;
//        }
//        
//        final EventSemantic other = (EventSemantic) o;
//        if (_time != other._time) {
//            return false;
//        }
//        if (!_event.equals(other._event)) {
//            return false;
//        }
//
//        return true;
//    }
//    
//    @Override
//    public String toString() {
//        return _event.toFormattedString() + "@" + _time;
//    }
//    
//}