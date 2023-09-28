package diag;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import lang.MMLDlightLexer;
import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDEvent;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import edu.supercom.util.Options;

import util.AlarmLog;
import util.ImmutableAlarmLog;
import util.MMLDGlobalTransition;
import util.Scenario;
import util.TCN;

import lang.State;
import lang.YAMLDComponent;
import lang.YAMLDVar;
import lang.YAMLDValue;
import lang.MMLDRule;

public class Test {

  static void printStateUpdate(State s_last, State s_new)
  {
    final Network net = s_last.getNetwork();
    for (YAMLDComponent comp : net.getComponents()) {
      for (YAMLDVar var : comp.vars()) {
	YAMLDValue v_last = s_last.getValue(var);
	YAMLDValue v_new = s_new.getValue(var);
	if (v_new != v_last) {
	  System.out.println(comp.name() + "." + var.name() + " := " + v_new + ";");
	}
      }
    }
  }

  static void myPrintState(State s)
  {
    final Network net = s.getNetwork();
    for (YAMLDComponent comp : net.getComponents()) {
      System.out.println("// Component " + comp.name());
      for (YAMLDVar var : comp.vars()) {
	YAMLDValue val = s.getValue(var);
	System.out.println(comp.name() + "." + var.name() + " := " + val + ";");
      }
    }
  }

  static void myPrintScenario
    (Scenario sce, AlarmLog log, Collection<YAMLDEvent> faults)
  {
    State s_last = sce.getState(0);
    final Network net = s_last.getNetwork();
    final Collection<YAMLDEvent> obs = net.observableEvents();
    System.out.println("INITIAL STATE:");
    System.out.println();
    myPrintState(s_last);

    for (int i = 0; i < sce.nbTrans(); i++) {
      System.out.println();
      System.out.println("STEP " + i + ":");
      System.out.println();
      final MMLDGlobalTransition gtr = sce.getMMLDTrans(i);
      // System.out.println(gtr);
      for (YAMLDComponent comp : net.getComponents()) {
	MMLDRule rule = gtr.getRule(comp);
	if (rule != null) {
	  boolean is_fault = false;
	  for (YAMLDEvent ev : rule.getGeneratedEvents()) {
	    if (faults.contains(ev)) is_fault = true;
	  }
	  if (is_fault)
	    System.out.println("FIRE " + comp.name() + "." + rule.getName()
			       + "  [FAULT]");
	  else
	    System.out.println("FIRE " + comp.name() + "." + rule.getName());
	  for (YAMLDEvent ev : rule.getGeneratedEvents()) {
	    if (obs.contains(ev)) {
	      AlarmLog.AlarmEntry al = null;
	      for (int j = 0; j < log.nbEntries(); j++)
		if (log.get(j)._events.contains(ev))
		  al = log.get(j);
	      if (al != null)
		System.out.println("OBSERVE " + ev + " AT " + al._time);
	      else
		System.out.println("OBSERVE " + ev + " UNKNOWN");
	    }
	  }
	}
      }
      final State s_new = sce.getState(i + 1);
      System.out.println("STATE CHANGES:");
      printStateUpdate(s_last, s_new);
      s_last = s_new;
    }
  }


	/**
	 * A main file that computes the diagnosis for the specified options.  
	 * The list of options for this main is: 
	 * <ul>
	 * <li><b>net</b>, <b>network</b>, <b>model</b>, or <b>mod</b>: 
	 *   the filename that contains the model,</li>
	 * <li><b>alarms</b> or <b>obs</b>: 
	 *   the filename that contains the alarms/observations,</li>
	 * <li><b>faults</b>: the filename that contains the list of faults (or bad events).</li>
	 * </ul>
	 * 
	 * @see Diag#diag(Network, AlarmLog, Collection, lang.State, Options) for more options.
	 * @see Options for a description of how args may be formatted.  
	 * */
	public static void main(String[] args) {
		final Options opt = new Options(args);
		
		String filename = opt.getOption(true, true, "net", "network", "model", "mod");
		String alarmfilename = opt.getOption(true, true, "alarms", "obs");
		String faultfilename = opt.getOption(true, true, "faults");

		Network net = null;

		try {
			InputStream inputStream = new FileInputStream(filename);
			Reader input = new InputStreamReader(inputStream);
			MMLDlightLexer lexer = new MMLDlightLexer(new ANTLRReaderStream(
					input));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MMLDlightParser parser = new MMLDlightParser(tokens);
			parser.net();

			net = MMLDlightParser.net;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
		
		final AlarmLog log = ImmutableAlarmLog.readLogFile(net, alarmfilename);
		if (log == null) {
			System.err.println("Incorrect log file.");
			return;
		}
		Collection<YAMLDEvent> faults = Util.parseEvents(net, faultfilename);
		if (faults == null) {
			System.err.println("Incorrect fault file.");
			return;
		}

		Collection<YAMLDEvent> obs_ev = net.observableEvents();
//		System.out.println("#observable events = " + obs_ev.size());
		for (int i = 0; i < log.nbEntries(); i++) {
		  AlarmLog.AlarmEntry ent_i = log.get(i);
//		  System.out.println("Log entry " + Integer.toString(i) +
//				     " at time " + Double.toString(ent_i._time)
//				     + ":");
		  for (YAMLDEvent e : ent_i._events) {
//		    System.out.println(" > " + e );
		    if (!obs_ev.contains(e)) {
		      System.out.println("Error: event " + e + " in log is not observable");
		      System.exit(1);
		    }
		  }
		}

		final DiagnosisReport report = Diag.diag(net, log, faults, MMLDlightParser.st,opt);
		final Scenario sce = report.getScenario();
		if ("true".equals(opt.getOption("validateTimeConstraints"))) {
		  final TCN cn = TCN.makeScenarioTCN(sce, log);
		  cn.compute_minimal();
		  boolean ok = cn.consistent();
		  System.out.println("TCN consistent? " + ok);
		  if (ok) {
		    ok = TCN.checkScenarioTriggers(sce, cn);
		    System.out.println("Scenario consistent? " + ok);
		  }
		}
		if ("verbose".equals(opt.getOption("output"))) {
		  myPrintScenario(sce, log, faults);
		}
		else {
			for (int i=0 ; i<sce.nbTrans() ; i++) {
				final MMLDGlobalTransition gtr = sce.getMMLDTrans(i);
				System.out.println(gtr);
				System.out.println(gtr.triggeringComponents());
				System.out.println();
			}
		}

//		final Collection<YAMLDEvent> diagnosis =
//		  Diag.diagnose(net, log, faults, MMLDlightParser.st, stepsPerObs);
//		final Collection<YAMLDEvent> diagnosis = Diag.diagnose(net, log,
//				faults, MMLDlightParser.st);
		
//		for (final YAMLDEvent event: diagnosis) {
//			System.out.println(event.toFormattedString());
//		}

//		for (final YAMLDEvent event: diagnosis) {
//		  System.out.println(event.toFormattedString());
//		}

		System.out.println("Finished");
		double t = (Diag.time_in_sat_calls /  1000000000.0);
		System.out.println("Time in sat calls: " + Double.toString(t));
        
        System.out.println(sce.toFormattedString());
	}

}
