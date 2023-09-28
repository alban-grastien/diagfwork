package diag.reiter2.tight;

import diag.reiter2.Hypothesis;
import diag.reiter2.HypothesisSpace;
import diag.reiter2.Property;
import diag.reiter2.TightPropertyComputer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A <code>BasicComputer</code>, i.e., a basic computer, 
 * is a tight property computer 
 * that defines hypothesis <code>h</code> 
 * as the property that the hypothesis is a descendant of <code>h</code> 
 * whilst it is a descendant of none of the children of <code>h</code>.  
 */
public class BasicComputer implements TightPropertyComputer<Hypothesis> {

    @Override
    public <HH extends Hypothesis> Collection<Property<HH>> tightProperties(HypothesisSpace<HH> space, HH h) {
        final List<Property<HH>> result = new ArrayList<Property<HH>>();
        
        result.add(Property.descendant(h));
        
        for (final HH child: space.getChildren(h)) {
            result.add(Property.notDescendant(child));
        }
        
        return result;
    }
    
}
