package diag.reiter2;

import java.util.Collection;

/**
 * A <code>Tester</code>, i.e., a tester, 
 * is an object that can test some diagnosis hypotheses.  
 */
public interface Tester<H extends Hypothesis> {
    
    public TestResult<H> test(Collection<Property<H>> props) throws DiagnosisIOException;
}
