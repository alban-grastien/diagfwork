package diag;

import edu.supercom.util.Options;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AlarmLog;

import edu.supercom.util.sat.VariableSemantics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import lang.MMLDRule;
import lang.MMLDTransition;
import lang.MMLDlightLexer;
import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDComponent;
import lang.YAMLDDVar;
import lang.YAMLDEvent;
import lang.YAMLDValue;
import lang.YAMLDVar;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import util.ImmutableAlarmLog;
import util.Time;

public class Util {

	/**
	 * Extracts the list of events from the specified SAT solution.  
	 * 
	 * @param sol a list of assignments that satisfies the SAT problem.  
	 * @param net the network.  
	 * @param nb the number of transitions.  
	 * @param varass the variable assigner that tells what variable 
	 * is associated with an event.  
	 * @param rel the set of relevant events 
	 * (for which we want to know whether they happened).  
	 * */
	public static List<YAMLDEvent> extractEvents(DiagnosisReport sol, 
			Network net,
			Collection<YAMLDEvent> rel) {
		final List<YAMLDEvent> result = new ArrayList<YAMLDEvent>();
		
		for (int i=0 ; i<sol.getNbTrans(); i++) {
			for (final YAMLDComponent comp: net.getComponents()) {
				for (final YAMLDEvent event: comp.events()) {
					final VariableSemantics sem = new EventOccurrence(event, i);
					if (sol.ass(sem) && rel.contains(event)) {
						result.add(event);
					}
				}
			}
		}
		
		return result;
	}
	
	public static void printSolution(DiagnosisReport sol, Network net) {
		//final List<YAMLDEvent> result = new ArrayList<YAMLDEvent>();
		
		for (int k=0 ; k<sol.getNbTrans() ; k++) {
			// State variables
			for (final YAMLDComponent comp: net.getComponents()) {
				for (final YAMLDVar var: comp.vars()) {
					final List<YAMLDValue> domain = var.domain();
					if (!domain.isEmpty()) {
						for (int i = 0; i < domain.size(); i++) {
							if (sol.ass(new Assignment(var, domain.get(i), k))) {
								System.out.println("AT " + k + ": "+ comp.name() + "." + var.name() + " := " + domain.get(i).toString());
							}
						}
					} else {
						final int min = var.getRangeInit();
						final int max = var.getRangeEnd();
						final int size = max - min +1;
						for (int i=0 ; i<size ; i++) {
							if (sol.ass(new Assignment(var, var.getValue(i), k))) {
								System.out.println("AT " + k + ": "+ comp.name() + "." + var.name() + " := " + i);
							}
						}
					}
				} 
			} 
			// Dependent variables
			for (final YAMLDComponent comp: net.getComponents()) {
				for (final YAMLDDVar var: comp.dvars()) {
					final List<YAMLDValue> domain = var.domain();
					if (!domain.isEmpty()) {
						for (int i = 0; i < domain.size(); i++) {
							if (sol.ass(new Assignment(var, domain.get(i), k))) {
								System.out.println("AT " + k + ": "+ comp.name() + "." + var.name() + " := " + domain.get(i).toString());
							}
						}
					} else {
						final int min = var.getRangeInit();
						final int max = var.getRangeEnd();
						final int size = max - min +1;
						for (int i=0 ; i<size ; i++) {
							if (sol.ass(new Assignment(var, var.getValue(i), k))) {
								System.out.println("AT " + k + ": "+ comp.name() + "." + var.name() + " := " + i);
							}
						}
					}
				}
			} 
			// Events
			for (final YAMLDComponent comp: net.getComponents()) {
				for (final YAMLDEvent event: comp.events()) {
					final VariableSemantics sem = new EventOccurrence(event, k);
					if (sol.ass(sem)) {
						System.out.println("AT " + k + ": EVENT "+ event.getComponent().name() + "." + event.name());
					}
				}
			}
			// Transitions and rules
			for (final YAMLDComponent comp: net.getComponents()) {
				for (final MMLDTransition trans: comp.transitions()) {
					{
						final VariableSemantics sem = new MMLDTransitionTrigger(trans, k);
						if (sol.ass(sem)) {
							System.out.println("AT " + k + ": TRANSITION " + trans.getComponent().name() + "." + 
									trans.getName());
						}						
					}
					for (final MMLDRule r: trans.getRules()) {
						final VariableSemantics sem = new RuleTrigger(k, r);
						if (sol.ass(sem)) {
							System.out.println("AT " + k + ": RULE " + trans.getComponent().name() + "." + 
									r.getName());
						} 
					}
				}
			}
		}
	}

    public static File openTemporaryFile(String pref, String suff, File dir) {
        try {
            File tmpFile = File.createTempFile(pref, suff, dir);
            tmpFile.deleteOnExit();
            return tmpFile;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

	public static List<YAMLDEvent> parseEvents(Network net, String fileName) {
		final List<YAMLDEvent> result = new ArrayList<YAMLDEvent>();
		try {
			// final Pattern pat = Pattern.compile("([^\\.]*)\\.([^\\.\\w]*)");
			final Pattern pat = Pattern.compile("([^.]*)\\.([^.]*)");
			final BufferedReader reader = new BufferedReader(new FileReader(
					fileName));
			while (reader.ready()) {
				final String line = reader.readLine();
				final Matcher mat = pat.matcher(line);
				if (mat.matches()) {
					final String compName = mat.group(1);
					final String eventName = mat.group(2);
					final YAMLDComponent comp = net.getComponent(compName);
					if (comp == null) {
						System.out.println("Unrecognized component " + compName);
					}
					final YAMLDEvent event = comp.getEvent(eventName);
					if (event == null) {
						System.out.println("Unrecognized event " + compName
								+ "." + eventName);
					} else {
						result.add(event);
					}
				} else {
					// System.out.println("Test >> did not recognize '" + line +
					// "'");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns an array with the time of each timestep assuming the specified alarm log 
	 * and the specified number of unobservable events between two entries of the log.  
	 * When the time is unknown, this is represented by <code>-1</code>.  
	 * 
	 * @param log the alarm log.  
	 * @param nbUnobs the number of unobservable events.
	 * @return the time of each time step.    
	 * */
	public static Time[] times(AlarmLog log, int nbUnobs) {
		Time[] result = new Time[(nbUnobs+1)*log.nbEntries()];
		
		for (int i=0 ; i<log.nbEntries() ; i++) {
			for (int k=0 ; k<nbUnobs ; k++) {
				result[(i*(nbUnobs+1))+k] = null;
			}
			result[(i*(nbUnobs+1))+nbUnobs] = log.get(i)._time;
		}
		
		return result;
	}
    
    public static Network readNetwork(Options opt, String defaultAddress) {
		try {
			final String filename;
            {
                final boolean mustFind = (defaultAddress == null);
                final String tmp = opt.getOption(mustFind, mustFind, 
                        "network", "model", "mod", "net");
                if (tmp == null) {
                    filename = defaultAddress;
                } else {
                    filename = tmp;
                }
            }
			InputStream inputStream = new FileInputStream(filename);
			Reader input = new InputStreamReader(inputStream);
			MMLDlightLexer lexer = new MMLDlightLexer(new ANTLRReaderStream(
					input));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			MMLDlightParser parser = new MMLDlightParser(tokens);
			parser.net();
            
			return MMLDlightParser.net;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}

	public static AlarmLog readAlarms(Network net, Options opt, String defaultAddress) {
			final String filename;
            {
                final boolean mustFind = (defaultAddress == null);
                final String tmp = opt.getOption(mustFind, mustFind, 
                        "alarm", "obs");
                if (tmp == null) {
                    filename = defaultAddress;
                } else {
                    filename = tmp;
                }
            }
		return ImmutableAlarmLog.readLogFile(net, filename);
	}
	
	
}
