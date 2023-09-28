/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.supercom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines a list of options for a problem.  There are two types of options:
 * <ul>
 * <li>Key/value: associates a single value to a key; if the pair is defined
 * more than once, the key is associated with the last value defined.  
 * </li>
 * <li>List of values associated with a key: associates a list of values to a
 * key.  By default, the list is empty.  
 * </li>
 * </ul>
 *
 * @author Alban Grastien
 * @version 2.1.1
 * @since 2.1.1
 * @todo Explain the input format.  
 */
public class Options {

    /**
     * The pattern that recognizes an option of form "^(.+)=(.*)$"
     */
    public static final Pattern OPTION_PATTERN = Pattern.compile("^(.+)=(.*)$");
    /**
     * The pattern that recognizes an option that is commented out (only for
     * option files): "^//.*"
     */
    public static final Pattern OPTION_COMMENTED = Pattern.compile("^//.*");
    /**
     * The pattern that must be replaced by the current directory.  
     */
    public static final String OPTION_DIRECTORY = "#PATH#";
    /**
     * The pattern that indicates that the option 
     * is associated with a list of values.  
     * If the name of an option matches this pattern, 
     * then the option contains a list of values.  
     * In the current implementation, an option contains a list of values 
     * if it starts with an "@".  
     * For instance, option "solver" is associated with a single string 
     * and should be accessed through method {@link #getOption(java.lang.String) }; 
     * on the other hand, option "@solvers" is associated with a list of strings 
     * and should be accessed through method {@link #getOptions(java.lang.String) }.  
     * 
     */
    public static final Pattern COLLECTION_OPTION = Pattern.compile("^@");
    /**
     * The list of simple pairs.  
     */
    private final Map<String, String> _pairs;
    /**
     * The list of options associated with each key.
     */
    private final Map<String, Collection<String>> _options;

    /**
     * Creates an empty option object.  
     */
    public Options() {
        _pairs = new HashMap<String, String>();
        _options = new HashMap<String, Collection<String>>();
    }

    public Options(String[] args) {
        this();
        computeOptions(args);
    }

    private void computeOptions(final String[] args) {
        final Iterator<String> it = new Iterator<String>() {

            private int _i = 0;

            @Override
            public boolean hasNext() {
                return _i < args.length;
            }

            @Override
            public String next() {
                final String result = args[_i];
                _i++;
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported.");
            }
        };
        final Deque<String> files = new ArrayDeque<String>();
        files.add("# Arguments");
        try {
            computeOptions(it, new File("./"), files);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            final StringBuilder buf = new StringBuilder();
            while (!files.isEmpty()) {
                final String file = files.pop();
                buf.append("From ").append(file).append("\n");
            }
            System.err.println(buf);
        }
    }

    //
    // Lists options names starts with @
    // keyword #PATH# must be replaced by the current path.
    //
    private void computeOptions(Iterator<String> args, File dir, Deque<String> files) throws IOException {
        while (args.hasNext()) {
            final String line = args.next();
            if (OPTION_COMMENTED.matcher(line).matches()) {
                continue;
            }

            final Matcher mat = OPTION_PATTERN.matcher(line);
            if (mat.matches()) {
                final String key = mat.group(1);
                String val = mat.group(2);

                if (key.equals("optionFile")) {
                    final File newFile;
                    if (val.startsWith("/")) {
                        newFile = new File(val);
                    } else {
                        newFile = new File(dir, val);
                    }
                    if (files.contains(newFile.getAbsolutePath())) { // No loop.
                        continue;
                    }
                    files.add(newFile.getAbsolutePath());
                    final BufferedReader reader = new BufferedReader(new FileReader(newFile));
                    final Iterator<String> newIt = new Iterator<String>() {

                        @Override
                        public boolean hasNext() {
                            try {
                                return reader.ready();
                            } catch (IOException e) {
                                return true;
                            }
                        }

                        @Override
                        public String next() {
                            try {
                                return reader.readLine();
                            } catch (IOException e) {
                                throw new IllegalStateException("Problem reading.");
                            }
                        }

                        @Override
                        public void remove() {
                            throw new UnsupportedOperationException("Not supported.");
                        }
                    };
                    computeOptions(newIt, newFile.getParentFile(), files);
                    reader.close();
                } else {
                    val = val.replaceAll(OPTION_DIRECTORY, dir.getAbsolutePath());
                    if (isCollectiveOption(key)) {
                        addOption(key, val);
                    } else {
                        createOption(key, val);
                    }
                }

            }
        }
        files.pop();
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key the key whose value is required.
     * @return the value associated with <code>key</code>, <code>null</code>
     * otherwise.
     */
    public String getOption(String key) {
        return _pairs.get(key);
    }

    /**
     * Returns the value associated with the specified key with augmented error 
     * handling.
     *
     * @param warning indicates whether a warning should be emitted if no key is
     * found.
     * @param exit indicates whether the program should be stopped if no key is
     * found.
     * @param keys the list of keys that must be tested.  
     */
    public String getOption(boolean warning, boolean exit, String... keys) {
        for (final String key : keys) {
            final String val = getOption(key);
            if (val != null) {
                return val;
            }
        }

        if (warning) {
            final StringBuilder buf = new StringBuilder();
            buf.append("Option not found.  Specify one of the following options: ");
            final int size = keys.length;
            for (int i = 0; i < size; i++) {
                final String key = keys[i];
                buf.append("'").append(key).append("'");
                if (i != size - 1) {
                    buf.append(", ");
                }
            }
            buf.append(".");
            System.err.println(buf);
        }

        if (exit) {
            System.exit(1);
        }

        return null;
    }

    /**
     * Returns the list of values associated with the specified key.
     *
     * @param key the key for which the list of values is requested.
     * @return the list of values associated with <code>key</code>.
     */
    public Collection<String> getOptions(String key) {
        {
            final Collection<String> result = _options.get(key);
            if (result != null) {
                return Collections.unmodifiableCollection(result);
            }
        }
        final Collection<String> actualRes = Collections.emptyList();
        return actualRes;
    }

    /**
     * Maps the specified value to the specified key.
     *
     * @param key the key option.
     * @param val the value associated with the key.
     */
    public void createOption(String key, String val) {
        _pairs.put(key, val);
    }

    /**
     * Adds the specified value to the list of options associated with the
     * specified key.  Applying this method does not necessarily modify the 
     * collection returned by {@link #getOptions(java.lang.String) }.  
     *
     * @param key the key for which a value is added.
     * @param val the value that is added to the value.  
     */
    public void addOption(String key, String val) {
        final Collection<String> options;
        {
            Collection<String> coll = _options.get(key);
            if (coll == null) {
                coll = new ArrayList<String>();
                _options.put(key, coll);
            }
            options = coll;
        }
        options.add(val);
    }

    @Override
    public String toString () {
        return _options.toString() + "\n" +
                _pairs.toString();
    }
    
    /**
     * Indicates whether the specified option represents a collection of values, 
     * or simply one value.  
     * 
     * @param n the name of the option.  
     * @return <code>true</code> if the option 
     * is associated with a collection of values, 
     * <code>false</code> otherwise.  
     */
    public boolean isCollectiveOption(String n) {
        return COLLECTION_OPTION.matcher(n).matches();
    }
}
