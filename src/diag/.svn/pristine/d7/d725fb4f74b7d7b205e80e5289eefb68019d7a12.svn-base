package diag.reiter2.log;

import diag.reiter2.Hypothesis;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import java.util.Collection;
import java.util.List;

/**
 * A <code>CandidateList</code>, i.e., a candidate list, 
 * is a log that states that the current list of candidates 
 * is as specified.  
 */
public class CandidateList implements Log {

    /**
     * The list of candidates in the list.  
     */
    private final UnmodifiableList<? extends Hypothesis> _candidates;
    
    /**
     * Builds a log that states that the current list of candidates 
     * is the specified unmodifiable list.  
     * 
     * @param list the list of candidates.  
     */
    public CandidateList(UnmodifiableList<? extends Hypothesis> list) {
        _candidates = list;
    }
    
    /**
     * Builds a log that states that the current list of candidates 
     * is the specified collection of hypotheses.  
     * 
     * @param list the list of candidates.  
     */
    public CandidateList(Collection<? extends Hypothesis> coll) {
        final UnmodifiableListConstructor<Hypothesis> con = 
                new UnmodifiableListConstructor<Hypothesis>();
        for (final Hypothesis h: coll) {
            con.addElement(h);
        }
        _candidates = con.getList();
    }
    
    @Override
    public String stringLog() {
        final StringBuilder bui = new StringBuilder();
        boolean first = true;
        for (final Hypothesis h: _candidates) {
            if (!first) {
                bui.append("\n");
            }
            bui.append("::: ");
            bui.append(h);
            first = false;
        }
        return bui.toString();
    }
    
}
