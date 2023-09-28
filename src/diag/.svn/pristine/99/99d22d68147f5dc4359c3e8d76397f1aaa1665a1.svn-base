package diag.reiter2.seq;

import diag.reiter2.Conflict;
import diag.reiter2.Property;
import edu.supercom.util.Pair;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import edu.supercom.util.sat.BufferedPrintClauseStream;
import edu.supercom.util.sat.CNF;
import edu.supercom.util.sat.Clause;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.MapVariableAssigner;
import edu.supercom.util.sat.TimedSemanticFactory;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import test.BugException;


/**
 * A <code>_Test</code>, i.e., 
 */
public class _Test {

    public static void testIsDescendant() throws BugException {
        final SequentialHypothesis<Integer> h1 =
                new SequentialHypothesis<Integer>();

        final SequentialHypothesis<Integer> h2;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            h2 = new SequentialHypothesis<Integer>(list);
        }

        BugException.test("isDescendant", true, SequentialHypothesis.isDescendant(h2, h1));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h1, h2));

        final SequentialHypothesis<Integer> h3;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(2);
            h3 = new SequentialHypothesis<Integer>(list);
        }

        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h2, h3));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h3, h2));

        final SequentialHypothesis<Integer> h4;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(2);
            list.add(2);
            h4 = new SequentialHypothesis<Integer>(list);
        }

        BugException.test("isDescendant", true, SequentialHypothesis.isDescendant(h4, h3));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h3, h4));

        final SequentialHypothesis<Integer> h5;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            list.add(2);
            h5 = new SequentialHypothesis<Integer>(list);
        }

        final SequentialHypothesis<Integer> h6;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(0);
            list.add(1);
            list.add(2);
            h6 = new SequentialHypothesis<Integer>(list);
        }

        BugException.test("isDescendant", true, SequentialHypothesis.isDescendant(h6, h5));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h5, h6));

        final SequentialHypothesis<Integer> h7;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            list.add(0);
            list.add(2);
            h7 = new SequentialHypothesis<Integer>(list);
        }

        BugException.test("isDescendant", true, SequentialHypothesis.isDescendant(h7, h5));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h5, h7));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h6, h7));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h7, h6));

        final SequentialHypothesis<Integer> h8;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            list.add(2);
            list.add(0);
            h8 = new SequentialHypothesis<Integer>(list);
        }

        BugException.test("isDescendant", true, SequentialHypothesis.isDescendant(h8, h5));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h5, h8));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h6, h8));
        BugException.test("isDescendant", false, SequentialHypothesis.isDescendant(h8, h6));
    }

    public static void testEquals() throws BugException {
        final SequentialHypothesis<Integer> h1;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            h1 = new SequentialHypothesis<Integer>(list);
        }
        final SequentialHypothesis<Integer> h2;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            h2 = new SequentialHypothesis<Integer>(list);
        }

        BugException.test("hashCode", h1.hashCode(), h2.hashCode());
        BugException.test("equals", h1, h2);
    }

    public static void testChildren() throws BugException {
        final Set<Integer> domain = new HashSet<Integer>();
        {
            domain.add(1);
            domain.add(2);
            domain.add(3);
            domain.add(4);
            domain.add(5);
        }

        final SequentialHypothesis<Integer> h = new SequentialHypothesis<Integer>();

        final Set<SequentialHypothesis<Integer>> childrenOfH = new HashSet<SequentialHypothesis<Integer>>();
        for (final Integer integer : domain) {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(integer);
            final SequentialHypothesis<Integer> child = new SequentialHypothesis<Integer>(list);
            childrenOfH.add(child);
        }
        BugException.test("equals", domain.size(), childrenOfH.size());
        BugException.test("equals or children", childrenOfH, h.children(domain));

        final SequentialHypothesis<Integer> h1;
        {
            final List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            h1 = new SequentialHypothesis<Integer>(list);
        }
        BugException.test("children", (domain.size() * 2) - 1, h1.children(domain).size());
    }

    public static void testParents() throws BugException {
        {
            final SequentialHypothesis<Integer> h = new SequentialHypothesis<Integer>(1, 2, 2, 3, 1);
            final Set<SequentialHypothesis<Integer>> parents = new HashSet<SequentialHypothesis<Integer>>();
            parents.add(new SequentialHypothesis<Integer>(2, 2, 3, 1));
            parents.add(new SequentialHypothesis<Integer>(1, 2, 3, 1));
            parents.add(new SequentialHypothesis<Integer>(1, 2, 2, 1));
            parents.add(new SequentialHypothesis<Integer>(1, 2, 2, 3));
            BugException.test("parents", parents, h.parents());
        }

        {
            final SequentialHypothesis<Integer> h = new SequentialHypothesis<Integer>();
            BugException.test("parents", 0, h.parents().size());
        }
    }

    public static void testMinimalCommonDescendants() throws BugException {
        final SequentialHypothesis<Integer> h1 = new SequentialHypothesis<Integer>(1, 2, 1);
        final SequentialHypothesis<Integer> h2 = new SequentialHypothesis<Integer>(3, 1);

        {
            final Set<SequentialHypothesis<Integer>> result = new HashSet<SequentialHypothesis<Integer>>();
            result.add(new SequentialHypothesis<Integer>(1, 3, 2, 1));
            result.add(new SequentialHypothesis<Integer>(1, 2, 3, 1));
            result.add(new SequentialHypothesis<Integer>(3, 1, 2, 1));
            BugException.test("minimalCommonDescendants", result, SequentialHypothesis.minimalCommonDescendants(h1, h2));
            BugException.test("minimalCommonDescendants", result, SequentialHypothesis.minimalCommonDescendants(h2, h1));
        }

        final SequentialHypothesis<Integer> h3 = new SequentialHypothesis<Integer>(1, 2, 1, 2);
        {
            final Set<SequentialHypothesis<Integer>> result = new HashSet<SequentialHypothesis<Integer>>();
            result.add(new SequentialHypothesis<Integer>(3, 1, 2, 1, 2));
            result.add(new SequentialHypothesis<Integer>(1, 3, 2, 1, 2));
            result.add(new SequentialHypothesis<Integer>(1, 2, 3, 1, 2));
            result.add(new SequentialHypothesis<Integer>(1, 2, 1, 3, 2, 1));
            result.add(new SequentialHypothesis<Integer>(1, 2, 1, 2, 3, 1));
            BugException.test("minimalCommonDescendants", result, SequentialHypothesis.minimalCommonDescendants(h3, h2));
            BugException.test("minimalCommonDescendants", result, SequentialHypothesis.minimalCommonDescendants(h2, h3));
        }

        final SequentialHypothesis<Integer> h4 = new SequentialHypothesis<Integer>(1, 2, 3);
        final SequentialHypothesis<Integer> h5 = new SequentialHypothesis<Integer>(1, 2, 4);
        {
            final Set<SequentialHypothesis<Integer>> result = new HashSet<SequentialHypothesis<Integer>>();
            result.add(new SequentialHypothesis<Integer>(1, 2, 3, 4));
            result.add(new SequentialHypothesis<Integer>(1, 2, 4, 3));
            BugException.test("minimalCommonDescendants", result, SequentialHypothesis.minimalCommonDescendants(h4, h5));
            BugException.test("minimalCommonDescendants", result, SequentialHypothesis.minimalCommonDescendants(h5, h4));
        }
    }

    public static void testAlphaTranslator() throws BugException {
        try {
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                
                testTranslator2(true, sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(true, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(true, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(true, sems, new SequentialHypothesis<String>("A", "A"), false);
            }
                
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 1));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 1));
                sems.add(new StringSemantic("B", 2));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 1));
                sems.add(new StringSemantic("B", 1));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 1));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("C", 3));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("C", 3));
                sems.add(new StringSemantic("B", 4));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("C", 2));
                sems.add(new StringSemantic("A", 3));
                sems.add(new StringSemantic("B", 4));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 3));
                sems.add(new StringSemantic("C", 4));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), false);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("A", 3));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), true);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("A", 3));
                sems.add(new StringSemantic("B", 4));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), true);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 3));
                sems.add(new StringSemantic("A", 4));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), true);
            }

            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("B", 2));
                sems.add(new StringSemantic("A", 3));
                sems.add(new StringSemantic("A", 4));

                testTranslator2(true,sems, new SequentialHypothesis<String>(), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(true,sems, new SequentialHypothesis<String>("A", "A"), true);
            }

        } catch (IOException e) {
            throw new BugException(e.getMessage());
        }
    }
    
    public static void testOmegaTranslator() throws BugException {
        try {
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>(); // the scenario (here, no fault)
                
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 3));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 2));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("B", 2));
                sems.add(new StringSemantic("A", 2));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("C", 9));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("C", 5));
                sems.add(new StringSemantic("B", 8));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("C", 2));
                sems.add(new StringSemantic("A", 5));
                sems.add(new StringSemantic("B", 8));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 5));
                sems.add(new StringSemantic("C", 8));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("A", 5));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), false);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("A", 5));
                sems.add(new StringSemantic("B", 8));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), false);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 5));
                sems.add(new StringSemantic("A", 8));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), false);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("B", 2));
                sems.add(new StringSemantic("A", 3));
                sems.add(new StringSemantic("A", 8));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), true);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), false);
            }
            
            {
                final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
                sems.add(new StringSemantic("A", 2));
                sems.add(new StringSemantic("B", 4));
                sems.add(new StringSemantic("C", 6));
               
                testTranslator2(false, sems, new SequentialHypothesis<String>(), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "B"), false);
                testTranslator2(false, sems, new SequentialHypothesis<String>("A", "A"), true);
            }
            
        } catch (IOException e) {
            throw new BugException(e.getMessage());
        }
    }

    public static void testConflict() throws BugException {
        try {
            final int FIRST = 0;
            final int LAST = 10;
            final UnmodifiableList<String> strings;
            {
                final UnmodifiableListConstructor<String> con = new UnmodifiableListConstructor<String>();
                con.addElement("A");
                con.addElement("B");
                con.addElement("C");
                con.addElement("D");
                con.addElement("E");
                strings = con.getList();
            }

            final VariableAssigner varass;
            final VariableLoader loader = new VariableLoader();

            {
                final MapVariableAssigner tmp = new MapVariableAssigner();
                for (int t = FIRST; t < LAST; t++) {
                    for (final String s : strings) {
                        final VariableSemantics sem = new StringSemantic(s, t);
                        final int var = loader.allocate(1);
                        tmp.add(sem, var);
                    }
                }
                varass = tmp;
            }

            final TimedSemanticFactory<String> fact = StringSemantic.getFactory();

            final SequentialHypothesisSpace<String> space = new SequentialHypothesisSpace<String>(new HashSet<String>(strings));

            final SequentialTranslator<String> trans = new SequentialTranslator<String>();

            final SequentialHypothesis<String> h = new SequentialHypothesis<String>();

            final String filename = "/tmp/a";
            final ClauseStream out = new BufferedPrintClauseStream(filename);

            {
                final Set<VariableSemantics> negs = new HashSet<VariableSemantics>();
                for (int t = FIRST; t < LAST; t++) {
                    for (final String s : strings) {
                        final VariableSemantics sem = fact.buildSemantics(s, t);
                        negs.add(sem);
                    }
                }
                
                final VariableSemantics sem1 = fact.buildSemantics("A", 1);
                final VariableSemantics sem2 = fact.buildSemantics("B", 2);

                negs.remove(sem1);
                negs.remove(sem2);

                for (final VariableSemantics sem : negs) {
                    final int var = varass.surelyGetVariable(sem);
                    out.put(-var);
                }
                out.put(varass.surelyGetVariable(sem1),varass.surelyGetVariable(sem2));
            }

            final List<Integer> assumptions = new ArrayList<Integer>();
            {
                {
                    final Property<SequentialHypothesis<String>> p = Property.propertyDescendant(true,h);
                    assumptions.add(trans.getSATVariable(loader, FIRST, LAST, p, space));
                    trans.createSATClauses(out, varass, fact, FIRST, LAST, p);
                }
                
                for (final SequentialHypothesis<String> child: space.getChildren(h)) {
                    final Property<SequentialHypothesis<String>> p = Property.propertyDescendant(false,child);
                    assumptions.add(trans.getSATVariable(loader, FIRST, LAST, p, space));
                    trans.createSATClauses(out, varass, fact, FIRST, LAST, p);
                }
            }
            //out.put(assVar);

            out.close();
            
            
            final String assfilename = "/tmp/b";
            final PrintStream assout = new PrintStream(new FileOutputStream(assfilename));
            for (final int ass: assumptions) {
                assout.println(ass);
            }
            assout.close();
            
            final Pair<List<Integer>,List<Boolean>> result = 
                    CNF.solveWithAssumption2("/usr/local/bin/multisat_core", assfilename, filename);
            if (result.first() != null) {
                final List<Integer> list = result.first();
                final int[] satConflict = new int[list.size()];
                for (int i=0 ; i<list.size() ; i++) {
                    satConflict[i] = list.get(i);
                }
                final Conflict<SequentialHypothesis<String>> conflict = 
                        trans.SATConflictToDiagnosisConflict(satConflict);
                System.out.println(conflict);
            }

            
        } catch (IOException e) {
            throw new BugException(e.getMessage());
        } catch (InterruptedException e)  {
            throw new BugException(e.getMessage());
        }
    }
    
    public static void testParse() throws BugException {
        final Set<String> domain = new HashSet<String>();
        final String s1 = "zongo";
        final String s2 = "zo\\ngo";
        final String s3 = "zo\'ngo";
        final String s4 = "zon  go";
        final String s5 = "zongo ";
        final String s6 = " zongo ";
        domain.add(s1);
        domain.add(s2);
        domain.add(s3);
        domain.add(s4);
        domain.add(s5);
        domain.add(s6);
        
        final SequentialHypothesisSpace<String> space = new SequentialHypothesisSpace<String>(domain);
        
        testSingleParse(space, new SequentialHypothesis<String>());
        testSingleParse(space, new SequentialHypothesis<String>(s1));
        testSingleParse(space, new SequentialHypothesis<String>(new ArrayList<String>(domain)));
        testSingleParse(space, new SequentialHypothesis<String>(s1,s1));
    }
    
    public static void main(String[] args) throws BugException {
        testEquals();
        testIsDescendant();
        testChildren();
        testParents();
        testMinimalCommonDescendants();
        testAlphaTranslator();
        testOmegaTranslator();
//        testConflict();
        testParse();

        System.out.println("Test ok for " + _Test.class);
    }
    
    private static void testTranslator2(boolean isDescent, Set<VariableSemantics> pos, SequentialHypothesis<String> h, boolean result)  
            throws BugException, IOException {
        final Set<Set<VariableSemantics>> setSets = new HashSet<Set<VariableSemantics>>();
        
        for (final VariableSemantics sem: pos) {
            final Set<VariableSemantics> sems = new HashSet<VariableSemantics>();
            sems.add(sem);
            setSets.add(sems);
        }
        
        testTranslator3(isDescent, setSets, h, result);
    }

    private static void testTranslator3(boolean isDescent, Set<Set<VariableSemantics>> pos, SequentialHypothesis<String> h, boolean result) 
            throws BugException, IOException {
        final int FIRST = 0;
        final int LAST = 10;
        final UnmodifiableList<String> strings;
        {
            final UnmodifiableListConstructor<String> con = new UnmodifiableListConstructor<String>();
            con.addElement("A");
            con.addElement("B");
            con.addElement("C");
            con.addElement("D");
            con.addElement("E");
            strings = con.getList();
        }

        final VariableAssigner varass;
        final VariableLoader loader = new VariableLoader();

        {
            final MapVariableAssigner tmp = new MapVariableAssigner();
            for (int t = FIRST; t < LAST; t++) {
                for (final String s : strings) {
                    final VariableSemantics sem = new StringSemantic(s, t);
                    final int var = loader.allocate(1);
                    tmp.add(sem, var);
                }
            }
            varass = tmp;
        }

        final TimedSemanticFactory<String> fact = StringSemantic.getFactory();

        final SequentialHypothesisSpace<String> space = new SequentialHypothesisSpace<String>(new HashSet<String>(strings));

        final SequentialTranslator<String> trans = new SequentialTranslator<String>();

        final Property<SequentialHypothesis<String>> p = Property.propertyDescendant(isDescent,h);

        final int assVar;
        {
            assVar = trans.getSATVariable(loader, FIRST, LAST, p, space);
        }

        final String filename = "/tmp/a";
        final ClauseStream out = new BufferedPrintClauseStream(filename);

        {
            final Set<VariableSemantics> negs = new HashSet<VariableSemantics>();
            for (int t = FIRST; t < LAST; t++) {
                for (final String s : strings) {
                    final VariableSemantics sem = fact.buildSemantics(s, t);
                    negs.add(sem);
                }
            }

            for (final Set<VariableSemantics> sems: pos) {
                negs.removeAll(sems);
            }
            //negs.remove(fact.buildSemantics("C", 4));

            for (final VariableSemantics sem : negs) {
                final int var = varass.surelyGetVariable(sem);
                out.put(-var);
            }
            for (final Set<VariableSemantics> sems: pos) {
                Clause cl = new Clause();
                for (final VariableSemantics sem : sems) {
                    final int var = varass.surelyGetVariable(sem);
                    cl = new Clause(cl, var);
                }
                out.put(cl);
            }
        }
        out.put(assVar);
        trans.createSATClauses(out, varass, fact, FIRST, LAST, p);

        out.close();

        BugException.test("translation", result, CNF.runSolver("minisat_core", filename));
    }
    
    private static <X> void testSingleParse(SequentialHypothesisSpace<X> space, SequentialHypothesis<X> h) 
            throws BugException
    {
        final String string = h.toString();
        try {
            final SequentialHypothesis<X> parsedHypothesis = space.parse(new StringReader(string));
            BugException.test("parse", h, parsedHypothesis);
        } catch (IOException e) {
            throw new BugException(e.getMessage());
        }
    }
}
class StringSemantic implements VariableSemantics {

    private final String _string;
    private final int _t;

    public StringSemantic(String s, int t) {
        if (s == null) {
            throw new NullPointerException();
        }
        _string = s;
        _t = t;
    }

    @Override
    public int hashCode() {
        return Pair.hashCode(_string, _t);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof StringSemantic)) {
            return false;
        }

        final StringSemantic other = (StringSemantic) o;

        if (_t != other._t) {
            return false;
        }

        if (!(_string.equals(other._string))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return _string + "@" + _t;
    }

    public static TimedSemanticFactory<String> getFactory() {
        return new TimedSemanticFactory<String>() {

            @Override
            public VariableSemantics buildSemantics(String x, int t) {
                return new StringSemantic(x, t);
            }
        };
    }
}