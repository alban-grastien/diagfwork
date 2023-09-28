/*
 * BufferedPrintClauseStream.java
 *
 * Created on 18 June 2007, 10:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

/**
 * Implementation of a clause stream.  This implementation prints the clauses in
 * a print stream.  Contrary to the {@link PrintClauseStream} class, this
 * implementation bufferize the clauses and print them only when the number of
 * clauses stored is bigger than the size of the buffer.  The default size for
 * the buffer is {@link #DEFAULT_BUFFER_SIZE}. Do not forget to call the
 * {@link #close()} method at the end.
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.4
 */
public class BufferedPrintClauseStream
        extends AbstractClauseStream
        implements ClauseStream {
    
    /**
     * The print stream where the clauses are printed.
     */
    private java.io.PrintStream _out;
    
    /**
     * Number of clauses printed.
     */
    private int _nb;
    
    /**
     * The size of the buffer.
     */
    private int _bufsize;
    
    /**
     * The string buffer that contains the clauses to be printed in the print stream.
     */
    private StringBuilder _buffer;
    
    /**
     * Number of clauses in the buffer;
     */
    private int _bufcontent;
    
    /**
     * Default size of the buffer (100).
     */
    public static final int DEFAULT_BUFFER_SIZE = 100;
    
    /**
     * Builds the clause stream.  The size of the buffer is set to
     * <code>100</code>.
     *
     * @param out the stream where the clauses are printed.
     */
    public BufferedPrintClauseStream(java.io.PrintStream out) {
        _out = out;
        _nb = 0;
        _bufsize = DEFAULT_BUFFER_SIZE;
        _buffer = new StringBuilder();
        _bufcontent = 0;
    }

    /**
     * Builds a buffered print stream in the specified filename.
     *
     * @param filename the name of the file.
     * @throws IOException in case the file with the specified name cannot be
     * opened.
     */
    public BufferedPrintClauseStream(String filename) throws IOException {
        this(new File(filename));
    }

    /**
     * Builds a buffered print stream in the specified file.
     *
     * @param file the file.  
     * @throws IOException in case the file with the specified name cannot be
     * opened.
     */
    public BufferedPrintClauseStream(File file) throws IOException {
        this(new PrintStream(new FileOutputStream(file)));
    }
    
    /**
     * Gets a clause.  This clause stream prints the clause in its
     * print stream.
     *
     * @param clause the clause.
     */
    @Override
    public void put(Clause clause) {
        if (_bufcontent == _bufsize) {
            empty();
        }
        
        _buffer.append(clause + "\n");
        
        _bufcontent++;
        _nb++;
    }
    
    @Override
    public void put(int lit) {
        if (_bufcontent == _bufsize) {
            empty();
        }
        
        _buffer.append(lit).append("\t0\n");
        
        _bufcontent++;
        _nb++;
    }
    
    @Override
    public void put(int lit1, int lit2) {
        if (_bufcontent == _bufsize) {
            empty();
        }
        
        _buffer.append(lit1).append("\t").append(lit2).append("\t0\n");
        
        _bufcontent++;
        _nb++;
    }
    
    @Override
    public void put(int lit1, int lit2, int lit3) {
        if (_bufcontent == _bufsize) {
            empty();
        }

        _buffer.append(lit1).append("\t").
                append(lit2).append("\t").
                append(lit3).append("\t0\n");
        
        _bufcontent++;
        _nb++;
    }
    
    @Override
    public void put(int[] lits) {
        if (_bufcontent == _bufsize) {
            empty();
        }
        
        for (int i=0 ; i<lits.length ; i++) {
            _buffer.append(lits[i]).append("\t");
        }
        _buffer.append("0\n");
        
        _bufcontent++;
        _nb++;
    }
    
    /**
     * Gives the number of clauses put in this stream.
     *
     * @return the number of calls to {@link #put(Clause)} on this stream.
     */
    public int getNumber() {
        return this._nb;
    }
    
    /**
     * Closes the stream.  This method should be invoked when no more clause
     * will be added to the stream.
     */
    @Override
    public void close() {
        empty();
        _out.close();
    }
    
    /**
     * Returns the size of the buffer.
     *
     * @return the size of the buffer.
     */
    public int getBufferSize() {
        return this._bufsize;
    }
    
    /**
     * Changes the size of the buffer.  This empties the buffer.
     *
     * @param size the new size of the buffer.
     * @throws IllegalArgumentException if <code>size < 0</code>.
     */
    public void setBufferSize(int size) {
        empty();
        if (size < 0) {
            throw new IllegalArgumentException("The size of the buffer cannot be less than 0.");
        }
        _bufsize = size;
    }
    
    /**
     * Returns the number of clauses buffered.
     *
     * @return the number of clauses buffered.
     */
    public int numberOfBufferedClauses() {
        return this._bufcontent;
    }
    
    /**
     * Empties the buffer.
     */
    private void empty() {
        _out.print(_buffer);
        _buffer.delete(0,_buffer.length());
//        _buffer = new StringBuilder();
        _bufcontent = 1;
    }

    /**
     * Flushes the buffer.
     */
    public void flush() {
        empty();
    }

	@Override
	public void put(Collection<Integer> tab) {
        if (_bufcontent == _bufsize) {
            empty();
        }
        
        for (final int lit: tab) {
        	_buffer.append(lit).append("\t");
        }
        _buffer.append("0\n");
        _bufcontent++;
	}
}
