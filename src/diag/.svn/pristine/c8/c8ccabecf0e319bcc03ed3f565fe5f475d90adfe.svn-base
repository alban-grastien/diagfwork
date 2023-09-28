package diag.reiter2.log;

import diag.reiter2.Property;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import java.util.Collection;

/**
 * A <code>Test</code>, i.e., a test, 
 * is the log that indicates that the specified properties will be tested.  
 */
public class Test implements Log {
    
    /**
     * The set of properties that are tested.  
     */
    private final UnmodifiableList<Property> _props;
    
    /**
     * Creates a log that indicates 
     * that the specified properties will be tested.  
     */
    public Test(Collection<? extends Property> props) {
        final UnmodifiableListConstructor<Property> con = 
                new UnmodifiableListConstructor<Property>();
        for (final Property p: props) {
            con.addElement(p);
        }
        _props = con.getList();
    }

    @Override
    public String stringLog() {
        return "  Testing: " + _props.toString();
    }
    
}
