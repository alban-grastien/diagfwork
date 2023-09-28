package diag.reiter2.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A <code>_Test</code> of SetHypothesis.  
 */
public class _Test {
    
    public static void main(String[] args) {
        
        final Set<String> elements = elements();
        
        final SetHypothesisSpace<String> space = 
                new SetHypothesisSpace<String>(elements);
        
        final SetHypothesis<String> h0 = space.getMinimalHypothesis();
        System.out.println("h0 = " + h0);
        
        final Set<SetHypothesis<String>> childrenOfH0 = space.getChildren(h0);
        System.out.println("children of h0 = " + childrenOfH0);
        
        final SetHypothesis<String> h1, h2;
        {
            final Iterator<SetHypothesis<String>> it = childrenOfH0.iterator();
            h1 = it.next();
            h2 = it.next();
        }
        
        System.out.println("h1 = " + h1);
        System.out.println("h2 = " + h2);
        
        final Set<SetHypothesis<String>> childrenOfH2 = space.getChildren(h2);
        System.out.println("children of h2 = " + childrenOfH2);
        
        for (final SetHypothesis<String> grandChild: childrenOfH2) {
            System.out.println("  grandChild = " + grandChild);
            System.out.println("  common desc with h1 = " + space.minimalCommonDescendants(h1, grandChild));
            System.out.println("  parents = " + space.getParents(grandChild));
            System.out.println();
        }
    }
    
    private static Set<String> elements() {
        final Set<String> result = new HashSet<String>();
        
        result.add("a");
        result.add("b");
        result.add("c");
        result.add("d");
        result.add("e");
        result.add("f");
        
        return result;
    }
    
}
