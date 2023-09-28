package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lang.MMLDlightLexer;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.YAMLDEvent;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import alarmprocessor.AlarmClusteringAbstract;
import alarmprocessor.AlarmClusteringMMLD;
import alarmprocessor.AlarmProcessor;
import alarmprocessor.ExtractCatastrophicEventsMMLD;
import alarmprocessor.ExtractIncidentIndependentAlarms;
import alarmprocessor.ExtractTransientAlarms;
import alarmprocessor.SummaryFromScenario;

import diag.Diag;
import diag.Util;
import edu.supercom.util.Options;

import util.AlarmLog;
import util.ImmutableAlarmLog;
import util.Scenario;
import util.Time;

public class AlarmProcessingTest {

	public static void main(String[] args) {
		final Options opt = new Options(args);

		String filename = opt.getOption(true, true, "net", "network", "model");
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
		System.out.println("#observable events = " + obs_ev.size());
		for (int i = 0; i < log.nbEntries(); i++) {
		  AlarmLog.AlarmEntry ent_i = log.get(i);
          final Time t = ent_i._time;
		  System.out.println("Log entry " + Integer.toString(i) +
				     " at time " + t
				     + ":");
		  for (YAMLDEvent e : ent_i._events) {
		    System.out.println(" > " + e );
		    if (!obs_ev.contains(e)) {
		      System.out.println("Error: event " + e + " in log is not observable");
		      System.exit(1);
		    }
		  }
		}

		// now run all alarm processing methods available

		// build a few helper objects 
		Map<String, Object> extra_info = new HashMap<String, Object>();
		extra_info.put("faults", faults);
		AlarmClusteringAbstract a = new AlarmClusteringMMLD();

		// first, do alarm clustering
		State s = MMLDlightParser.st;
		final Scenario sce = Diag.diag(net, log, faults, s, opt).getScenario();
		AlarmProcessor ap = new AlarmClusteringMMLD();
		ap.process(sce, null);
		SummaryFromScenario result = ap.getProcessingResults(); 
		result.writeToFile("alarm-clusters.txt");
		
		// second, extract catastrophic events
		ap = new ExtractCatastrophicEventsMMLD();
		ap.process(sce, extra_info);
		result = ap.getProcessingResults();
		result.writeToFile("fault-events.txt");
		
		// third, extract fault independent alarms
		ap = new ExtractIncidentIndependentAlarms(a);
		ap.process(sce, extra_info);
		result = ap.getProcessingResults();
		result.writeToFile("incident-indep-events.txt");

		// fourth, show transient alarms 
		ap = new ExtractTransientAlarms(a);
		ap.process(sce, extra_info);
		result = ap.getProcessingResults();
		result.writeToFile("transient-alarms.txt");

		System.out.println("Finished");
	}

}
