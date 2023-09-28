/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alarmprocessor;

import lang.YAMLDEvent;

//import edu.supercom.csdl.Event;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Vector;
import java.util.List;

/**
 *
 * @author Abotea
 */
public class TransientAlarms implements SummaryFromScenario {

    private Collection<YAMLDEvent> data;

    public TransientAlarms() {
        data = new Vector<YAMLDEvent>();
    }

    void addAll(Collection<YAMLDEvent> alarms) {
        data.addAll(alarms);
    }

    @Override
    public void writeToFile(String filename) {
       try {
            final PrintStream ps = new PrintStream(new FileOutputStream(filename));
            ps.println("Transient alarms:");
            ps.println(data.toString());
            ps.print("\n\n");
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    @Override
    public String toString() {
    	return "Transient alarms:\n" + data.toString() + "\n\n";
    }
    
    @Override
    public String toSimpleString() {
    	String result = "";
    	for (YAMLDEvent event : data) {
    		result += event.toString();
    		result += "\n";
    	}
    	return result;
    }

}
