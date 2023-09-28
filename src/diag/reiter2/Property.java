package diag.reiter2;

import java.io.IOException;
import java.io.Reader;

/**
 * A <code>Property</code>, i.e., a hypothesis property, 
 * is an implicit representation of a set of hypotheses 
 * defined with respect to a specified hypothesis <code>h</code>.  
 * A property is either a descent property 
 * which means that it represents the sets of hypotheses 
 * that are descendant of a specified hypothesis, 
 * or a non descent property 
 * which means that it represents the set of hypotheses 
 * that are not descendant of a specified hypothesis.  
 * 
 * @see Hypothesis
 */
public class Property<H extends Hypothesis> {
    
    /**
     * The hypothesis with respect to which the property is defined.  
     */
    private final H _h;
    
    /**
     * True if the property is a descent property.  
     */
    private final boolean _descent;
    
    private Property(H h, boolean descent) {
        _h = h;
        _descent = descent;
    }
    
    /**
     * Returns the property associated with this property.  
     * 
     * @return the property associated with this property.  
     */
    public H getHypothesis() {
        return _h;
    }
    
    /**
     * Indicates whether this property is a descent property.  
     * If this is the case, then the property 
     * represents all the hypotheses 
     * that are descendant of {@link #getHypothesis() }.  
     * 
     * @return <code>true</code> if this property is descent, 
     * <code>false</code> otherwise.  
     */
    public boolean isDescent() {
        return _descent;
    }
    
    /**
     * Indicates whether this property is a non descent property.  
     * If this is the case, then the property 
     * represents all the hypotheses 
     * that are <b>not</b> descendant of {@link #getHypothesis() }.  
     * 
     * @return <code>true</code> if this property is non descent, 
     * <code>false</code> otherwise.  
     */
    public boolean isNonDescent() {
        return !isDescent();
    }
    
    @Override
    public int hashCode() {
        return _h.hashCode() * (_descent?1:-1);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Property) {
            final Property other = (Property<?>)o;
            
            if (_descent != other._descent) {
                return false;
            }
            
            {
                final Hypothesis h1 = getHypothesis();
                final Hypothesis h2 = other.getHypothesis();
                if (!h1.equals(h2)) {
                    return false;
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        if (isDescent()) {
            return "Descendant of " + getHypothesis().toString();
        } else {
            return "Not Descendant of " + getHypothesis().toString();
        }
    }
    
    /**
     * Builds the property that the hypothesis 
     * is a descendant of the specified hypothesis.  
     * 
     * @param h the hypothesis that is the ancestor 
     * of all hypotheses of the property.  
     * @return the property that the hypothesis 
     * is a descendant of <code>h</code>.  
     */
    public static <H extends Hypothesis> Property<H> descendant(H h) {
        return new Property<H>(h, true);
    }
    
    /**
     * Builds the property that the hypothesis 
     * is not a descendant of the specified hypothesis.  
     * 
     * @param h the hypothesis that is the ancestor 
     * of no hypotheses of the property.  
     * @return the property that the hypothesis 
     * is not a descendant of <code>h</code>.  
     */
    public static <H extends Hypothesis> Property<H> notDescendant(H h) {
        return new Property<H>(h, false);
    }
    
    /**
     * Builds the property that the hypothesis 
     * is, or is not, a descendant of the specified hypothesis.  
     * 
     * @param isDescend whether the property is descent.  
     * @param h the hypothesis that is, or is not, the ancestor 
     * of the hypotheses of the property.  
     * @return the property that the hypothesis 
     * are, or are not, a descendant of <code>h</code>.  
     */
    public static <H extends Hypothesis> Property<H> propertyDescendant(boolean isDescend, H h) {
        return new Property<H>(h, isDescend);
    }
    
    /**
     * Parses a property defined on the specified hypothesis space 
     * in the specified reader.  
     * The reader should contain the string "&gt;=" (if the property is descend) 
     * or "!&gt;=" (if the property is not descend)
     * followed by the description of the base hypothesis.  
     * 
     * @param reader the reader that contains a description of the property.  
     * @param space  the hypothesis space the hypotheses are defined on.  
     * @param <H> the type of hypothesis. 
     * @param <HS> the type of hypothesis space.  
     * @return the property read if any, <code>null</code> otherwise.  
     */
    public static <H extends Hypothesis, HS extends HypothesisSpace<H>> Property<H> 
            parseProperty(Reader reader, HS space) throws DiagnosisIOException {
        try {
            final Boolean descent = parseDescent(reader);
            if (descent == null) {
                return null;
            }
            final H h = space.parse(reader);
            return Property.propertyDescendant(descent, h);
        } catch (IOException e) {
            throw new DiagnosisIOException(e.getMessage());
        }
    }
    
    // Returns null if the reader is finished
    private static Boolean parseDescent(Reader reader) throws IOException, DiagnosisIOException {
        final char[] c = new char[1];
        for(;;) {
            if (reader.read(c) == -1) {
                return null;
            }
            if (Character.isWhitespace(c[0])) {
                continue;
            }
            if (c[0] == '!' || c[0] == '>') {
                final boolean result = (c[0] == '>');
                if (c[0] == '!') {
                    if (reader.read(c) == -1) {
                        throw new DiagnosisIOException("End of reader");
                    }
                }
                if (c[0] != '>') {
                    throw new DiagnosisIOException("Expected '>'");
                }
                if (reader.read(c) == -1) {
                    throw new DiagnosisIOException("End of reader");
                }
                if (c[0] != '=') {
                    throw new DiagnosisIOException("Expected '='");
                }
                return result;
            }
            
            throw new DiagnosisIOException("Expected \">=\" or \"!>=\"");
        }
    }
}
