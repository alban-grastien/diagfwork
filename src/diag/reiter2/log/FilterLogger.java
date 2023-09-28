package diag.reiter2.log;

/**
 * A <code>FilterLogger</code>, i.e., a filter logger, 
 * is a logger that filters the logs and transmits only some selected logs 
 * to a specified logger called the internal logger.  
 */
public abstract class FilterLogger implements Logger {
    
    /**
     * The logger that is supposed to receive the filtered logs.  
     */
    private final Logger _logger;
    
    /**
     * Builds a filter logger from the specified logger.  
     * 
     * @param logger the internal logger that will receive the filtered logs.  
     */
    public FilterLogger(Logger logger) {
        _logger = logger;
    }

    @Override
    public void log(Log log) {
        if (filter(log)) {
            _logger.log(log);
        }
    }
    
    /**
     * Indicates whether the specified log is filtered, 
     * i.e., whether it should be transmitted to the internal logger.  
     * 
     * @param log the log that is filtered.  
     * @return <code>true</code> if <code>log</code> 
     * should be transmitted to the internal logger. 
     */
    public abstract boolean filter(Log log);
}