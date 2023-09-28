package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import lang.MMLDModelLoader;
import lang.MMLDlightParser;
import lang.Network;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/**
 * Provides useful methods for timed scenarii.  
 */
public class TimedScenarii {
    
    /**
     * Reads the timed scenario in the file at the specified address.
     * 
     * @param net the network for which the timed scenario is defined.  
     * @param filename the name of the file where the timed scenario is stored.  
     * @return the timed scenario read.  
     */
    public static TimedScenario readScenario(Network net, String filename) throws Exception {
        final InputStream inputStream = new FileInputStream(filename);
        final InputStreamReader reader = new InputStreamReader(inputStream);
        final ANTLRReaderStream stream = new ANTLRReaderStream(reader);
        final TimedScenarioLexer lexer = new TimedScenarioLexer(stream);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final TimedScenarioParser parser = new TimedScenarioParser(tokens);
        return parser.scenario(net);
    }

}
