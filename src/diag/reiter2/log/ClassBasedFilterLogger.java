package diag.reiter2.log;

import java.util.HashSet;
import java.util.Set;

/**
 * A <code>ClassBasedFilterLogger</code>, i.e., a class-based filter logger, 
 * is a filter logger that uses the class of the log to determine 
 * whether the log should be filtered.  
 */
public class ClassBasedFilterLogger extends FilterLogger {
    
    /**
     * The log classes that are filtered.  
     */
    private final Set<Class<? extends Log>> _filteredClasses;
    
    /**
     * Builds a class-based filter logger 
     * that transmits the filtered logs to the specified logger.  
     * 
     * @param logger the internal logger.  
     */
    public ClassBasedFilterLogger(Logger logger) {
        super(logger);
        _filteredClasses = new HashSet<Class<? extends Log>>();
    }

    @Override
    public boolean filter(Log log) {
        final Class<? extends Log> logClass = log.getClass();
        return _filteredClasses.contains(logClass);
    }
    
    /**
     * Adds the specified log class to the collection of log classes
     * that this filter does filter.  
     * 
     * @param cl the class of logs that are now filtered.  
     */
    public void add(Class<? extends Log> cl) {
        _filteredClasses.add(cl);
    }
    
    /**
     * Removes the specified log class from the collection of log classes
     * that this filter does filter.  
     * 
     * @param cl the class of logs that are no longer filtered.  
     */
    public void removeLogClass(Class<? extends Log> cl) {
        _filteredClasses.remove(cl);
    }
    
    /**
     * Adds all the specified log classes to the collection of log classes
     * that this filter does filter.  
     * 
     * @param cls the classes of logs that are now filtered.  
     */
    public void addAll(Class<? extends Log>... cls) {
        for (final Class<? extends Log> cl: cls) {
            add(cl);
        }
    }
}
