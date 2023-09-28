package edu.supercom.util;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A main method that runs a problem with a time out.  
 */
public class TimeOut {
    
    final Thread _actualProcess;
    
    final PrintStream _out;
    
    final long _beginning;
    
    public TimeOut(final Method m, final long timeout, final Options opt, PrintStream out, final String[] args) {
        _out = out;
        _beginning = System.currentTimeMillis();
        if (_out != null) {
            System.setOut(_out);
            System.setErr(_out);
        }
        
        {
            final Runnable run = new Runnable() {

                @Override
                public void run() {
                    try {
                        final Object[] param = { args };
                        m.invoke(null, param);
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("Finished running.");
                    quit();
                }
            };
            _actualProcess = new Thread(run);
            _actualProcess.start();
        }
    }
    
    public void stopProcess() {
        _actualProcess.interrupt();
    }
    
//    public void stopTimer() {
//        _timer.interrupt();
//    }
    
    public void quit() {
        final long end = System.currentTimeMillis();
        System.out.println("Total time (ms) = " + (end - _beginning));
        System.out.println("Finished.");
        if (_out != null) {
            _out.close();
        }
        System.exit(0);
    }
    
    // THE STATIC STUFF
    
    final static Option TIME_OUT = new Option(
            "The maximal time the command is allowed to run (in seconds).", 
            "timeout-timeout", "timeouttout");
    
    final static Option COMMAND = new Option(
            "The java file that contains the command to be run.", 
            "timeout-command", "timeout-main");
    
    final static Option OUT = new Option(
            "The output where the result of the command should be saved (optional).", 
            "timeout-output");
    
    private static long readTimeout(Options opt) {
        final String str = TIME_OUT.getOption(opt, System.err, true, null);
        return Long.parseLong(str);
    }
    
    @SuppressWarnings("unchecked")
    private static Method readMainMethod(Options opt) throws ClassNotFoundException, NoSuchMethodException {
        final String className = COMMAND.getOption(opt, System.err, true, null);
        final Class cl = Class.forName(className);
        final Method result = cl.getMethod("main", (new String[0]).getClass());
        return result;
    }
    
    private static PrintStream readPrintStream(Options opt) throws FileNotFoundException {
        final String outfile = OUT.getOption(opt, null, false, null);
        if (outfile == null) {
            return null;
        }
        
        final PrintStream out = new PrintStream(outfile);
        return out;
    }
    
    public static void main(String [] args) throws ClassNotFoundException, NoSuchMethodException, FileNotFoundException {
        final Options opt = new Options(args);
        
        final long timeout = readTimeout(opt);
        final Method mainMethod = readMainMethod(opt);
        final PrintStream out = readPrintStream(opt);

        final TimeOut to = new TimeOut(mainMethod, timeout, opt, out, args);
        
        {
            try {
                Thread.sleep(timeout * 1000);
                to.stopProcess();
                System.out.println("Finished timeout");
                to.quit();
            } catch (InterruptedException e) {
                // No need to do nothing.
            }
            
        }
    }
    
}
