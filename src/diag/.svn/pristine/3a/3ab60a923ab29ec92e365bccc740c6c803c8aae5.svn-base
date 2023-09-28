package diag.reiter2.tight;

import diag.reiter2.Hypothesis;
import diag.reiter2.HypothesisSpace;
import diag.reiter2.Property;
import diag.reiter2.TightPropertyComputer;
import diag.reiter2.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A <code>AncestorsComputer</code>, i.e., an ancestors computer, 
 * is a tight property computer 
 * that defines hypothesis <code>h</code> 
 * as the property that the hypothesis is a descendant of <code>h</code> 
 * and all its parents, whilst it is a descendant of none 
 * of the children of <code>h</code>.  
 */
public class AncestorsComputer implements TightPropertyComputer<Hypothesis> {

    @Override
    public <HH extends Hypothesis> Collection<Property<HH>> tightProperties(HypothesisSpace<HH> space, HH h) {
        
        final List<Property<HH>> result = new ArrayList <Property<HH>>();
        
        {
            final Collection<HH> parents = Util.ancestors(space, h);
            for (final HH parent: parents) {
                result.add(Property.descendant(parent));
            }
        }
        
        for (final HH child: space.getChildren(h)) {
            result.add(Property.notDescendant(child));
        }
        
        return result;
    }
    
}
