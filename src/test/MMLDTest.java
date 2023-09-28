package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import lang.MMLDlightLexer;
import lang.MMLDlightParser;
import lang.MapTimedState;
import lang.State;
import lang.TimedState;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import edu.supercom.util.SeededPseudoRandom;

import lang.Period;
import sim.MMLDSim;
import sim.Simulator;
import util.Time;

public class MMLDTest {

	public static void main(String args[]) {

		final String filename = args[0];
//		final String filename = 
//		"/tmp/test/zou.mmld";
//		final String filename = 
//			"YAMLDSim/data/ban-test/test.mmld";
		
		try {
			InputStream inputStream = new FileInputStream(filename);
			Reader input = new InputStreamReader(inputStream);
			MMLDlightLexer lexer = new MMLDlightLexer(new ANTLRReaderStream(
					input));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MMLDlightParser parser = new MMLDlightParser(tokens);
			parser.net();
			
			//Network net = MMLDlightParser.net;
			//System.out.println(net.toFormattedString());
			
			final State state = MMLDlightParser.st;
			final TimedState s = new MapTimedState(state);
			final Simulator sim = new MMLDSim(s, Time.ZERO_TIME, new SeededPseudoRandom(12));
			
			for (int i=0 ; i<10 ; i++) {
				System.out.println("-------------------------------");
				System.out.println(sim.getScenario().toFormattedString());
				System.out.println();
				sim.elapseTime(new Period(10));
				System.out.println(sim.getScenario().toFormattedString());
				System.out.println("-------------------------------");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}
}
