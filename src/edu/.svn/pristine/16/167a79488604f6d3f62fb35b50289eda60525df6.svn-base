package edu.supercom.util.auto.io;

import edu.supercom.util.auto.Automaton;
import edu.supercom.util.auto.AutomatonFactory;
import edu.supercom.util.auto.ReadableAutomaton;
import edu.supercom.util.auto.SimpleAutomaton;
import edu.supercom.util.auto.State;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class creates the automaton that is described in a file.
 * (The file can be compiled with fsmcompile, and drawn with fsmdraw)
 * The description is a list of simple transitions and of final states
 *
 * @author Carole Aujames
 * @author Alban Grastien
 * @version 2.0
 * @since 1.0
 */
public class FsmToAutomaton {

    /**
     * The automaton to create.
     */
    private ReadableAutomaton<String, String> _auto;

    private void set(BufferedReader in, AutomatonFactory fact) throws IOException {
        Automaton<String, String> result = fact.buildAutomaton("");
        String line;
        boolean initial = true;
        Map<String,State> states = new HashMap<String, State>();

        while ((line = in.readLine()) != null) {
            String[] sline = line.split("\\s");
            if (sline.length == 3) {
                String s1 = sline[0];
                String s2 = sline[1];

                State origin = states.get(s1);
                State target = states.get(s2);

                //tests if the states have already been created
                //if no, creates the states
                if (result.stateLabel(origin) == null) {
                    origin = result.newState(s1);
                    states.put(s1, origin);
                }
                if (result.stateLabel(target) == null) {
                    target = result.newState(s2);
                    states.put(s2, target);
                }

                //creates the transition
                result.newTransition(origin, target, sline[2]);

                //the initial state is the first state written in the file
                if (initial) {
                    result.setInitial(origin);
                    initial = false;
                }
            } else {
                //final states
                try {
                    result.setFinal(result.getState(sline[0]));
                } catch (Exception e) {
                    //nothing on the line
                }
            }
        }
        in.close();
        _auto = result;
    }

    /** Creates a new Automaton that is described in the specified reader.
     *
     * @param in the buffered reader.
     * @throws IOException if reading the reader generates an exception.
     */
    public FsmToAutomaton(BufferedReader in) throws IOException {
        set(in, new AutomatonFactory() {

            @Override
            public <SL, TL> Automaton<SL, TL> buildAutomaton(TL tl) {
                return new SimpleAutomaton<SL, TL>(tl);
            }
        });
    }

    /** Creates a new Automaton that is described in the specified reader.
     *
     * @param in the buffered reader.
     * @throws IOException if reading the reader generates an exception.
     */
    public FsmToAutomaton(BufferedReader in, AutomatonFactory fact) throws IOException {
        set(in, fact);
    }

    /** Creates a new Automaton that is described in a file.
     *  The method getAutomaton() returns the new automaton.
     *
     * @param fileName the file that contains the description of the automaton
     */
    public FsmToAutomaton(String fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            set(in, new AutomatonFactory() {

                @Override
                public <SL, TL> Automaton<SL, TL> buildAutomaton(TL tl) {
                    return new SimpleAutomaton<SL, TL>(tl);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Creates a new Automaton that is described in a file.
     *  The method getAutomaton() returns the new automaton.
     *
     * @param fileName the file that contains the description of the automaton
     */
    public FsmToAutomaton(String fileName, AutomatonFactory fact) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            set(in, fact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Returns the automaton.
     *
     * @return the automaton newly created. 
     */
    public ReadableAutomaton getAutomaton() {
        return _auto;
    }
}
