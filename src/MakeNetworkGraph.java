import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.lang.*;
import java.util.*;

import lang.*;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/* Make a graph of the components in an MMLD model. */

public class MakeNetworkGraph {

    private static int findComponent(String name, YAMLDComponent[] comps) {
	for (int i = 0; i < comps.length; i++) {
	    if (comps[i].name().equals(name))
		return i;
	}
	return -1;
    }

    private static void addLinksFromExpression
	(YAMLDExpr e, YAMLDComponent[] comps, int i, boolean[][] links) {
	try {
	    YAMLDID eid = (YAMLDID)e;
	    String n = eid.owner();
	    if (n != null) {
		int j = findComponent(n, comps);
		links[i][j] = true;
		links[j][i] = true;
	    }
	    return;
	}
	catch (ClassCastException cce) { }
    }

    private static void addLinksFromCondition
	(YAMLDFormula f, YAMLDComponent[] comps, int i, boolean[][] links) {
	try {
	    YAMLDAndFormula fand = (YAMLDAndFormula)f;
	    addLinksFromCondition(fand.getOp1(), comps, i, links);
	    addLinksFromCondition(fand.getOp2(), comps, i, links);
	    return;
	}
	catch (ClassCastException e) { }
	try {
	    YAMLDNotFormula fnot = (YAMLDNotFormula)f;
	    addLinksFromCondition(fnot.getOp(), comps, i, links);
	    return;
	}
	catch (ClassCastException e) { }
	try {
	    YAMLDEqFormula feq = (YAMLDEqFormula)f;
	    addLinksFromExpression(feq.expr1(), comps, i, links);
	    addLinksFromExpression(feq.expr2(), comps, i, links);
	    return;
	}
	catch (ClassCastException e) { }
    }

    private static void addLinksFromRules
	(YAMLDComponent comp, YAMLDComponent[] comps, int i, boolean[][] links) {
	for (MMLDTransition t : comp.transitions()) {
	    for (MMLDRule r : t.getRules()) {
		addLinksFromCondition(r.getCondition(), comps, i, links);
	    }
	}
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("MakeNetworkGraph <model file>");
            System.exit(1);
        }
        final String modelFile = args[0];

        Network net = null;
        //State state = null;

        try {
//            System.out.println("parsing " + modelFile + "...");
            InputStream inputStream = new FileInputStream(modelFile);
            Reader input = new InputStreamReader(inputStream);
            MMLDlightLexer lexer = new MMLDlightLexer(new ANTLRReaderStream(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MMLDlightParser parser = new MMLDlightParser(tokens);
            parser.net();
            net = MMLDlightParser.net;
            //state = MMLDlightParser.st;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RecognitionException e) {
            throw new RuntimeException(e);
        }

	Collection<YAMLDComponent> comp_collection = net.getComponents();
	YAMLDComponent[] comps = new YAMLDComponent[comp_collection.size()];
	comp_collection.toArray(comps);
	int ncomp = comps.length;
	boolean[][] links = new boolean[ncomp][ncomp];
	for (int i = 0; i < ncomp; i++) {
	    addLinksFromRules(comps[i], comps, i, links);
	}
	for (MMLDSynchro s : net.getSynchros()) {
	    int co = findComponent(s.getEvent().getComponent().name(), comps);
	    for (YAMLDEvent e : s.getSynchronizedEvents()) {
            int ci = findComponent(e.getComponent().name(), comps);
            links[co][ci] = true;
            links[ci][co] = true;
	    }
	}

	System.out.println("graph example {");
	System.out.println("node [shape=circle, width=0.1];");

	for (int i = 0; i < ncomp; i++) {
	    System.out.println("  " + comps[i].name() + " [label=\"" +
			       comps[i].name().substring(0, 2) +  "\"];");
	}

	for (int i = 0; i < ncomp; i++)
	    for (int j = i+1; j < ncomp; j++)
		if (links[i][j] || links[j][i])
		    System.out.println("  " + comps[i].name() +
				       " -- " + comps[j].name() + " [];");

	System.out.println("}");
    }
}
