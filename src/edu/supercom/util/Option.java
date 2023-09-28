package edu.supercom.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A <code>Option</code>, i.e., an option, 
 * is an option together 
 */
public class Option {
    /**
     * The explanation of the option.  
     */
    private final String _explanation;
    /**
     * The list of keywords associated with the option.  
     */
    private final List<String> _keywords;
    
    /**
     * Defines an option. 
     * 
     * @param e the explanation of this option, 
     * i.e., what the option should be used for.  
     * @param ks the list of keywords associated with this option.  
     */
    public Option(String e, String... ks) {
        _explanation = e;
        final List<String> keywords = new ArrayList<String>();
        for (final String k: ks) {
            keywords.add(k);
        }
        _keywords = Collections.unmodifiableList(keywords);
    }
    
    /**
     * Returns the explanation associated with this option.  
     * 
     * @return the explanation why this option should be used.  
     */
    public String getExplanation() {
        return _explanation;
    }
    
    /**
     * Returns the list of keywords associated with this option.  
     * 
     * @return the list of keywords that can be used to find the value 
     * associated with this option.  
     */
    public List<String> getKeywords() {
        return _keywords;
    }
    
    /**
     * Searches for the value associated with this option 
     * in the specified list of options.  
     * 
     * @param opt the list of options (could be <code>null</code>, 
     * which is assumed equivalent to an empty list).  
     * @param warning the print stream where a warning should be emitted 
     * were the option not been found 
     * (<code>null</code> if no warning should be emitted).  
     * @param exit a Boolean that indicates whether the program should stop 
     * were the option not been found.  
     * @param def the default value were the option not been found.  
     * @return the value of this option.  
     */
    public String getOption(Options opt, PrintStream warning, boolean exit, String def) {
        if (opt != null) {
            for (final String k: getKeywords()) {
                final String result = opt.getOption(k);
                if (result != null) {
                    return result;
                }
            }
        }
        
        if (warning != null) {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Warning: option not found.  Expect keywords: ");
            stringBuilder.append(getKeywords());
            
            warning.println(stringBuilder);
        }
        
        if (exit) {
            System.exit(1);
        }
        
        return def;
    }
    
    /**
     * Returns the collection of values associated with this option 
     * in the specified list of options.  
     * 
     * @param opt the list of options.  
     * @return the collection of values in <code>opt</code> 
     * that are associated with this option.  
     */
    public List<String> getOptions(Options opt) {
        final List<String> result = new ArrayList<String>();
        for (final String s: _keywords) {
            if (opt.isCollectiveOption(s)) {
                result.addAll(opt.getOptions(s));
            } else {
                final String val = opt.getOption(s);
                if (val != null) {
                    result.add(val);
                }
            }
        }
        
        return result;
    }
}
