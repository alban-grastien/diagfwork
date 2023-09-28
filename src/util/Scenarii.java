package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import lang.Network;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;

/**
 * A <code>Scenarii</code>, i.e., 
 */
public class Scenarii {

    public static Scenario readScenario(String address, Network net) {
        
        try {
            final InputStream inputStream = new FileInputStream(address);
            final Reader input = new InputStreamReader(inputStream);
            final Scenario2Lexer lexer = new Scenario2Lexer(new ANTLRReaderStream(
                new BufferedReader(input)));
            final CommonTokenStream tokens = new CommonTokenStream(lexer);
            final Scenario2Parser parser = new Scenario2Parser(tokens);
            
            return parser.scenario(net);
        } catch (Exception e) {
            return null;
        }
        
    }

    public static void saveScenario(String address, Scenario sce) throws IOException {
        final FileOutputStream fos = new FileOutputStream(address);
        final PrintStream out = new PrintStream(fos);
        out.println(sce.toFormattedString());
    }
    
}
