/*
 * StreamSplitter.java
 *
 * Created on 27 November 2008, 11:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A stream splitter is a clause stream that sends the clauses it receives to 
 * several streams. 
 *
 * @author Alban Grastien
 */
public class StreamSplitter extends AbstractClauseStream {
    
    private final Collection<ClauseStream> _listeners;
    
    /** Creates a new instance of StreamSplitter */
    public StreamSplitter() {
        _listeners = new ArrayList<ClauseStream>();
    }
    
    /**
     * Adds a stream to this splitter.
     *
     * @param stream the stream to add to this splitter.
     */
    public void addStream(ClauseStream stream) {
        _listeners.add(stream);
    }
    
    @Override
    public void close() {
        for (final Iterator<ClauseStream> streamIt = _listeners.iterator() ; streamIt.hasNext() ; ) {
            final ClauseStream stream = streamIt.next();
            stream.close();
        }
    }
    
    @Override
    public void put(Clause clause) {
        for (final Iterator<ClauseStream> streamIt = _listeners.iterator() ; streamIt.hasNext() ; ) {
            final ClauseStream stream = streamIt.next();
            stream.put(clause);
        }
    }
}
