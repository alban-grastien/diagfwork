package diag.reiter2.log;

import java.io.PrintStream;

/**
 * A <code>PrintLogger</code>, i.e., a print logger, 
 * is a logger that prints all the logs it receives in a given print stream.  
 */
public class PrintLogger implements Logger {
    
    /**
     * The print stream where the logs are printed.  
     */
    private final PrintStream _out;
    
    public PrintLogger(PrintStream out) {
        _out = out;
    }

    @Override
    public void log(Log log) {
        _out.println(log.stringLog());
    }
}
