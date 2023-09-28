/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import lang.YAMLDEvent; 
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Vector;

/**
 * A state to store the results of alarm processing functions that
 * extract a set of catastrophic events from a cluster of alarms.
 * @author Abotea
 */
public class CatastrophicEvents implements SummaryFromScenario {

    private Vector<YAMLDEvent> events;

    public CatastrophicEvents() {
        events = new Vector<YAMLDEvent>();
    }

    /**
     * Write the contents of this object into a file.
     * @param the name of the file
     */
    public void writeToFile(String filename) {
       try {
            final PrintStream ps = new PrintStream(new FileOutputStream(filename));
            ps.println("FAULT EVENTS:");
            ps.println(events.toString());
            ps.println("");
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Add a new event to the list of events stored in this object.
     * @param the event to be added
     */
    public void addEvent(YAMLDEvent event) {
        events.add(event);
    }
    
    public String toString() {
    	return "FAULT EVENTS:\n" + events.toString() + "\n";
    }

    @Override
    public String toSimpleString() {
    	String result = "";
    	for (YAMLDEvent event : events) {
    		result += event.toString();
    		result += "\n";
    	}
    	return result;
    }

}
