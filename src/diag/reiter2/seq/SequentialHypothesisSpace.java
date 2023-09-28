package diag.reiter2.seq;

import diag.reiter2.HypothesisSpace;
import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A <code>SequentialHypothesisSpace</code>, i.e., a sequential hypothesis space, 
 * is the hypothesis space defined over the sequences of some domain.  
 */
public class SequentialHypothesisSpace<X> implements HypothesisSpace<SequentialHypothesis<X>> {

    /**
     * The domain of this sequential hypothesis space.  
     */
    public final UnmodifiableSet<X> _domain;
    
    public SequentialHypothesisSpace(Set<X> domain) {
        final UnmodifiableSetConstructor<X> con = new UnmodifiableSetConstructor<X>();
        for (final X x: domain) {
            con.add(x);
        }
        _domain = con.getSet();
    }
    
    @Override
    public Set<SequentialHypothesis<X>> getChildren(SequentialHypothesis<X> h) {
        return h.children(_domain);
    }

    @Override
    public boolean isDescendant(SequentialHypothesis<X> suspectedDescendant, SequentialHypothesis<X> suspectedAncestor) {
        return SequentialHypothesis.isDescendant(suspectedDescendant, suspectedAncestor);
    }

    @Override
    public Set<SequentialHypothesis<X>> minimalCommonDescendants(SequentialHypothesis<X> h1, SequentialHypothesis<X> h2) {
        return SequentialHypothesis.minimalCommonDescendants(h1, h2);
    }

    @Override
    public SequentialHypothesis<X> getMinimalHypothesis() {
        return new SequentialHypothesis<X>();
    }

    @Override
    public Set<SequentialHypothesis<X>> getParents(SequentialHypothesis<X> h) {
        return h.parents();
    }

    /**
     * Parses the specified reader to extract a sequential hypothesis.  
     * The format of the data should be the following: 
     * S -> "[" list "]"
     * list -> epsilon | ( x xlist )
     * xlist -> epsilon | "," x xlist
     * where "x" is the result of applying {@link Object#toString()} to an element of the domain, 
     * enclosed with an apostrophe 
     * (any apostrophe or backslash of the result of {@link Object#toString()} 
     * must also be preceeded by a backslash).  
     * For instance, "[ '1', '0'     ,'5','5']"
     * 
     * @param reader the reader that is read to generate the hypothesis.  
     * @return the sequential hypothesis.  
     */
    @Override
    public SequentialHypothesis<X> parse(Reader reader) throws IOException {
        final Map<String,X> stringToX = new HashMap<String, X>();
        for (final X x: _domain) {
            stringToX.put(x.toString(), x);
        }
        
        {
            final char firstChar = readAndIgnoreWhite(reader);
            if (firstChar != '[') {
                throw new IOException("Expected '[', read " + firstChar);
            }
        }
        
        final List<X> xs = new ArrayList<X>();
        
        for(;;) {
            char c = readAndIgnoreWhite(reader);
            if (c == ']') {
                break;
            }
            
            if (!xs.isEmpty()) {
                if (c != ',') {
                    throw new IOException("Expected ']' or ',', read " + c);
                }
                c = readAndIgnoreWhite(reader);
            }
            
            if (c != '\'') {
                if (xs.isEmpty()) {
                    throw new IOException("Expected ']' or '\'', read " + c);
                } else {
                    throw new IOException("Expected '\'', read " + c);
                }
            }
            
            final String xString = readXString(reader);
            final X x = stringToX.get(xString);
            if (x == null) {
                throw new IOException("Unknown object '" + xString + "'");
            }
            
            xs.add(x);
        }
        
        return new SequentialHypothesis<X>(xs);
    }
    
    private static char readAndIgnoreWhite(Reader reader) throws IOException {
        char[] result = new char[1];
        
        do {
            if (reader.read(result) == -1) {
                throw new IOException("End of reader");
            }
        } while (Character.isWhitespace(result[0]));
        
        return result[0];
    }
    
    private static String readXString(Reader reader) throws IOException {
        final StringBuilder builder = new StringBuilder();
        
        boolean justReadABackslash = false;
        final char[] c = new char[1];
        for(;;) {
            if (reader.read(c) == -1) {
                throw new IOException("End of reader");
            }
            
            if (justReadABackslash) {
                builder.append(c[0]);
                justReadABackslash = false;
                continue;
            }
            
            if (c[0] == '\'') {
                break;
            }
            
            if (c[0] == '\\') {
                justReadABackslash = true;
                continue;
            }
            
            builder.append(c[0]);
        }
        
        return builder.toString();
    }
}