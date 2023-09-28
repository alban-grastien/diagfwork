package diag.reiter2;

import diag.reiter2.seq.SequentialHypothesis;
import diag.reiter2.seq.SequentialHypothesisSpace;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import test.BugException;

/**
 * A <code>_Test</code>, i.e., 
 */
public class _Test {
    
    public static void testSatisfy() throws BugException {
        final Set<Integer> domain = new HashSet<Integer>();
        domain.add(0);
        domain.add(1);
        domain.add(2);
        domain.add(3);
        domain.add(4);
        domain.add(5);
        
        final HypothesisSpace<SequentialHypothesis<Integer>> space =
                new SequentialHypothesisSpace<Integer>(domain);
        
        // h1 < h2 
        final SequentialHypothesis<Integer> h1 = new SequentialHypothesis<Integer>(1,2);
        final SequentialHypothesis<Integer> h2 = new SequentialHypothesis<Integer>(1);
        final SequentialHypothesis<Integer> h3 = new SequentialHypothesis<Integer>(3);
        
        final Property<SequentialHypothesis<Integer>> p1 = Property.descendant(h1);
        final Property<SequentialHypothesis<Integer>> n1 = Property.notDescendant(h1);
        final Property<SequentialHypothesis<Integer>> p2 = Property.descendant(h2);
        final Property<SequentialHypothesis<Integer>> n2 = Property.notDescendant(h2);
        final Property<SequentialHypothesis<Integer>> p3 = Property.descendant(h3);
        final Property<SequentialHypothesis<Integer>> n3 = Property.notDescendant(h3);
        
        BugException.test("satisfy", true,  Util.satisfy(space, h1, p1));
        BugException.test("satisfy", false, Util.satisfy(space, h1, n1));
        BugException.test("satisfy", true,  Util.satisfy(space, h1, p2));
        BugException.test("satisfy", false, Util.satisfy(space, h1, n2));
        BugException.test("satisfy", false, Util.satisfy(space, h1, p3));
        BugException.test("satisfy", true,  Util.satisfy(space, h1, n3));
        
        BugException.test("satisfy", false, Util.satisfy(space, h2, p1));
        BugException.test("satisfy", true,  Util.satisfy(space, h2, n1));
        BugException.test("satisfy", true,  Util.satisfy(space, h2, p2));
        BugException.test("satisfy", false, Util.satisfy(space, h2, n2));
        BugException.test("satisfy", false, Util.satisfy(space, h2, p3));
        BugException.test("satisfy", true,  Util.satisfy(space, h2, n3));
        
        BugException.test("satisfy", false, Util.satisfy(space, h3, p1));
        BugException.test("satisfy", true,  Util.satisfy(space, h3, n1));
        BugException.test("satisfy", false, Util.satisfy(space, h3, p2));
        BugException.test("satisfy", true,  Util.satisfy(space, h3, n2));
        BugException.test("satisfy", true,  Util.satisfy(space, h3, p3));
        BugException.test("satisfy", false, Util.satisfy(space, h3, n3));
        
    }
    
    @SuppressWarnings("unchecked")
    public static void testHits() throws BugException {
        final Set<Integer> domain = new HashSet<Integer>();
        domain.add(0);
        domain.add(1);
        domain.add(2);
        domain.add(3);
        domain.add(4);
        domain.add(5);
        
        final HypothesisSpace<SequentialHypothesis<Integer>> space =
                new SequentialHypothesisSpace<Integer>(domain);
        
        final SequentialHypothesis<Integer> h = new SequentialHypothesis<Integer>(1,2);
        
        final SequentialHypothesis<Integer> h1 = new SequentialHypothesis<Integer>(1);
        final Property<SequentialHypothesis<Integer>> p1 = Property.descendant(h1);
        final Property<SequentialHypothesis<Integer>> n1 = Property.notDescendant(h1);
        
        {
            final Conflict<SequentialHypothesis<Integer>> con = 
                    new Conflict<SequentialHypothesis<Integer>>(p1);
            BugException.test("hits", false, Util.hits(space, h, con));
        }
        {
            final Conflict<SequentialHypothesis<Integer>> con = 
                    new Conflict<SequentialHypothesis<Integer>>(n1);
            BugException.test("hits", true, Util.hits(space, h, con));
        }
        {
            final Conflict<SequentialHypothesis<Integer>> con = 
                    new Conflict<SequentialHypothesis<Integer>>(n1,p1);
            BugException.test("hits", true, Util.hits(space, h, con));
        }
        
        final SequentialHypothesis<Integer> h2 = new SequentialHypothesis<Integer>(1,3,2);
        final Property<SequentialHypothesis<Integer>> p2 = Property.descendant(h2);
        final Property<SequentialHypothesis<Integer>> n2 = Property.notDescendant(h2);
        
        {
            final Conflict<SequentialHypothesis<Integer>> con = 
                    new Conflict<SequentialHypothesis<Integer>>(p2);
            BugException.test("hits", true, Util.hits(space, h, con));
        }
        {
            final Conflict<SequentialHypothesis<Integer>> con = 
                    new Conflict<SequentialHypothesis<Integer>>(n2);
            BugException.test("hits", false, Util.hits(space, h, con));
        }
        {
            final Conflict<SequentialHypothesis<Integer>> con = 
                    new Conflict<SequentialHypothesis<Integer>>(n2,p2);
            BugException.test("hits", true, Util.hits(space, h, con));
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public static void testSuccessors() throws BugException {
        final Set<Integer> domain = new HashSet<Integer>();
        domain.add(0);
        domain.add(1);
        domain.add(2);
        domain.add(3);
        domain.add(4);
        domain.add(5);
        
        final HypothesisSpace<SequentialHypothesis<Integer>> space =
                new SequentialHypothesisSpace<Integer>(domain);
        
        final SequentialHypothesis<Integer> h1 = 
                new SequentialHypothesis<Integer>(1);
        
        final SequentialHypothesis<Integer> h2 = 
                new SequentialHypothesis<Integer>(2);
        final Property<SequentialHypothesis<Integer>> p1 = Property.notDescendant(h2);
        
        final SequentialHypothesis<Integer> h3 = 
                new SequentialHypothesis<Integer>();
        final Property<SequentialHypothesis<Integer>> p2 = Property.descendant(h3);
        
        final SequentialHypothesis<Integer> h4 = 
                new SequentialHypothesis<Integer>(1,2);
        final SequentialHypothesis<Integer> h5 = 
                new SequentialHypothesis<Integer>(2,1);
        {
            final Conflict<SequentialHypothesis<Integer>> con = new Conflict<SequentialHypothesis<Integer>>(p1,p2);
            final Set<SequentialHypothesis<Integer>> result = 
                    new HashSet<SequentialHypothesis<Integer>>();
            result.add(h4);
            result.add(h5);
            BugException.test("successors", result, Util.successors(space, h1, con));
        }
        {
            final Conflict<SequentialHypothesis<Integer>> con = new Conflict<SequentialHypothesis<Integer>>(p2);
            BugException.test("successors", Collections.EMPTY_SET, Util.successors(space, h1, con));
        }
        
        
    }
    
    public static void main(String [] args) throws BugException {
        testSatisfy();
        testHits();
        testSuccessors();
        
        System.out.println("Tests ok");
    }
    
}
