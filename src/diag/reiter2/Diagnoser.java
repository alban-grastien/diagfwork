package diag.reiter2;

import diag.reiter2.log.CandidateFinding;
import diag.reiter2.log.CandidateList;
import diag.reiter2.log.ConflictFinding;
import diag.reiter2.log.HypothesisConsideration;
import diag.reiter2.log.HypothesisNotEssentiality;
import diag.reiter2.log.HypothesisSubsumption;
import diag.reiter2.log.ScenarioFinding;
import diag.reiter2.log.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.Scenario;

/**
 * A <code>Diagnoser</code>, i.e., a diagnoser, 
 * is the algorithm responsible to computing the diagnosis.  
 */
public class Diagnoser<H extends Hypothesis> {
    
    final HypothesisSpace<H> _space;
    
    final Tester<H> _tester;
    
    public Diagnoser(HypothesisSpace<H> space, Tester<H> tester) {
        _space = space;
        _tester = tester;
    }
    
    public Collection<H> diagnose(TightPropertyComputer<? super H> tpc) throws DiagnosisIOException {
        
        final List<H> hypotheses = new ArrayList<H>();
        final Set<H> candidates = new HashSet<H>();
        hypotheses.add(_space.getMinimalHypothesis());
        
        final List<Conflict<H>> conflicts = new ArrayList<Conflict<H>>();
        
        while (!hypotheses.isEmpty()) {
            Diag.getLogger().log(new CandidateList(candidates));
            
            final H currentHypothesis = hypotheses.remove(0);
            Diag.getLogger().log(new HypothesisConsideration(currentHypothesis));
            
            { // Is a super hypothesis of a candidate / another hypothesis
                boolean remove = false;
                for (final H candidate: candidates) {
                    if (_space.isDescendant(currentHypothesis, candidate)) {
                        Diag.getLogger().log(new HypothesisSubsumption(currentHypothesis, candidate));
                        remove = true;
                        break;
                    }
                }
                if (remove) {
                    continue;
                }
            }
//            { // Is a super hypothesis of a candidate / another hypothesis
//                boolean remove = false;
//                
//                for (final H otherHypothesis: hypotheses) {
//                    if (_space.isDescendant(currentHypothesis, otherHypothesis)) {
//                        System.out.println("  Will be tested later.");
//                        remove = true;
//                        break;
//                    }
//                }
//                if (remove) {
//                    continue;
//                }
//            }
            
            Conflict<H> con = null;
            
            {
                final List<Property<H>> essentialProperties = new ArrayList<Property<H>>();
                essentialProperties.add(Property.descendant(currentHypothesis));
                for (final H candidate: candidates) {
                    essentialProperties.add(Property.notDescendant(candidate));
                }
                for (final H otherHypotheses: hypotheses) {
                    essentialProperties.add(Property.notDescendant(otherHypotheses));
                }
                final TestResult<H> essentialityResult = _tester.test(essentialProperties);
                if (!essentialityResult.isSuccessful()) {
                    con = essentialityResult.getConflict();
                    Diag.getLogger().log(new HypothesisNotEssentiality(currentHypothesis, con));
                    continue; // not essential, ignore
                }
            }
            
            if (con == null) {
                for (final Conflict<H> previousConflict: conflicts) {
                    if (Util.hits(_space, currentHypothesis, previousConflict)) {
                        continue;
                    }
                    con = previousConflict;
                    Diag.getLogger().log(new ConflictFinding(con));
                    break;
                }
            }
            
            if (con == null) {
                final Collection<Property<H>> tightProps = tpc.tightProperties(_space, currentHypothesis);
                final TestResult<H> testResult = _tester.test(tightProps);
                if (testResult.isSuccessful()) {
                    Diag.getLogger().log(new CandidateFinding(currentHypothesis));
                    final Scenario sce = testResult.getScenario();
                    Diag.getLogger().log(new ScenarioFinding(sce));
                } else {
                    con = testResult.getConflict();
                    Diag.getLogger().log(new ConflictFinding(con));
                    conflicts.add(con);
                }
            }
            
            if (con == null) {
                candidates.add(currentHypothesis);
            } else {
                final Collection<H> newHypotheses = Util.successors(_space, currentHypothesis, con);
                for (final H newHypothesis: newHypotheses) {
                    boolean ignore = false;
                    for (final H candidate: candidates) {
                        if (_space.isDescendant(newHypothesis, candidate)) {
                            ignore = true;
                            break;
                        }
                    }
                    if (!ignore) {
                        for (final H existingHypothesis: hypotheses) {
                            if (_space.isDescendant(newHypothesis, existingHypothesis)) {
                                ignore = true;
                                break;
                            }
                        }
                    }
                    if (!ignore) {
                        hypotheses.add(newHypothesis);
                    }
                }
            }
        }
        
        Diag.getLogger().log(new State("Computation finished."));
        Diag.getLogger().log(new CandidateList(candidates));
        
        return candidates;
    }
}