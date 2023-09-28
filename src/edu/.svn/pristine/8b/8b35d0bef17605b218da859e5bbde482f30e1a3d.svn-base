package edu.supercom.util.sat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Clause stream implementation that prints the clauses in a print
 * stream.  The print clause stream counts the number of clauses.
 *
 * @author Alban Grastien
 * @version 1.0
 * @since 1.0
 */
public class PrintClauseStream 
        extends AbstractClauseStream
        implements ClauseStream {

    /** 
     * The print stream where the clauses are printed.
     */
    private PrintStream _out;

    /** 
     * Numbers of clauses printed.
     */
    private int _nb;
    
    /**
     * Builds the print clause stream.
     *
     * @param out the print stream where the clauses are printed.
     */
    public PrintClauseStream (PrintStream out)
    {
	_out = out;
        _nb = 0;
    }
    
    /**
     * Builds a print stream in the specified file.
     *
     * @param filename the name of the file.
     * @throws IOException in case the file with the specified name cannot be 
     * opened.
     */
    public PrintClauseStream(String filename) throws IOException {
        this(new PrintStream(new FileOutputStream(new File(filename))));
    }

    /**
     * Gets a clause.  This clause stream prints the clause in its
     * print stream.
     *
     * @param clause the clause.
     */
    public void put(Clause clause)
    {
	_out.println(clause);
        _nb++;
    }
    
    public void put(int lit) {
        _out.println(lit + "\t0");
        _nb++;
    }
    
    public void put(int lit1, int lit2) {
        _out.println(lit1 + "\t" + lit2 + "\t0");
        _nb++;
    }
    
    public void put(int lit1, int lit2, int lit3) {
        _out.println(lit1 + "\t" + lit2 + "\t" + lit3 + "\t0");
        _nb++;
    }
    
    public void put(int[] tab) {
        StringBuilder buf = new StringBuilder();
        for (int i=0 ; i<tab.length ; i++) {
            buf.append(tab[i]).append("\t");
        }
        buf.append(0);
        _out.println(buf);
        _nb++;
    }
    
    public void put(Clause [] clauses) {
        StringBuilder buf = new StringBuilder();
        for (int i=0 ; i<clauses.length ; i++) {
            buf.append(clauses[i]).append("\n");
        }
        _out.print(buf);
    }
    
    public void put(int[][] clauses) {
        StringBuilder buf = new StringBuilder();
        for (int j=0 ; j<clauses.length ; j++) {
            int[] tab = clauses[j];
            for (int i=0 ; i<tab.length ; i++) {
                buf.append(tab[i]).append("\t");
            }
            buf.append("0\n");
        }
        _out.print(buf);
    }
    
    /**
     * Gives the number of clauses printed by this stream.
     *
     * @return the number of calls to {@link #put(Clause)} on this stream.
     */
    public int getNumber()
    {
        return this._nb;
    }
    
    /**
     * Closes the stream.  This method should be invoked when no more clause 
     * will be added to the stream.  This method is actually not required for 
     * this implementation.
     */
    public void close()
    {}

}