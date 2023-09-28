import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import lang.MMLDRule;
import lang.MMLDTransition;
import lang.Network;
import lang.MMLDlightLexer;
import lang.MMLDlightParser;

import lang.State;
import lang.YAMLDAssignment;
import lang.YAMLDComponent;
import lang.YAMLDConstraint;
import lang.YAMLDDVar;
import lang.YAMLDFormula;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/* This is for testing the MMLD parser / syntax-checking an MMLD file . */
public class MMLDParseTest {

    public static boolean test(Network net, State s) {
        // Testing whether the formulas generate exception.  
        // Ideally, the formula methods should have a test method.  
        for (final YAMLDComponent c: net.getComponents()) {
            for (final MMLDTransition tr: c.transitions()) {
                for (final YAMLDFormula f: tr.getPreconditions()) {
                    f.test(c,net);
                }
                for (final MMLDRule r: tr.getRules()) {
                    r.getCondition().test(c,net);
                }
            }
            for (final YAMLDDVar v: c.dvars()) {
                for (final YAMLDConstraint con: net.getConstraints(v)) {
                    con.getPrecondition().test(c,net);
                }
            }
        }
        
        // Assignments.
        for (final YAMLDComponent c: net.getComponents()) {
            for (final MMLDTransition tr: c.transitions()) {
                for (final MMLDRule r: tr.getRules()) {
                    for (final YAMLDAssignment a: r.getAssignments()) {
                        a.expression().test(c, net);
                    }
                }
            }
        }
        
        // TODO: Check that there is a transition for each input event.  
        
        return true;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("MMLDParseTest <model file>");
            System.exit(1);
        }
        final String modelFile = args[0];

        Network net = null;
        State state = null;

        try {
            System.out.println("parsing " + modelFile + "...");
            InputStream inputStream = new FileInputStream(modelFile);
            Reader input = new InputStreamReader(inputStream);
            MMLDlightLexer lexer = new MMLDlightLexer(new ANTLRReaderStream(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MMLDlightParser parser = new MMLDlightParser(tokens);
            parser.net();
            net = MMLDlightParser.net;
            state = MMLDlightParser.st;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RecognitionException e) {
            throw new RuntimeException(e);
        }
        if (test(net,state)) {
            System.out.println("ok.");
        }
    }
}
