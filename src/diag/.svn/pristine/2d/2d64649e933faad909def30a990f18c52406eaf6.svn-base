package diag.reiter2.seq;

import diag.reiter2.Conflict;
import diag.reiter2.Property;
import diag.reiter2.SATTranslator;
import edu.supercom.util.Pair;
import edu.supercom.util.UnmodifiableList;
import edu.supercom.util.UnmodifiableListConstructor;
import edu.supercom.util.sat.ClauseStream;
import edu.supercom.util.sat.TimedSemanticFactory;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;
import edu.supercom.util.sat.VariableSemantics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A <code>SequentialTranslator</code>, satVar.e., a sequential translator, 
 * is a SAT translator for the sequential hypothesis space.  
 */
public class SequentialTranslator<X> implements SATTranslator<X,SequentialHypothesis<X>, SequentialHypothesisSpace<X>> {

    private final SequentialTranslatorData<X> _data;

    private final Implemented<X> _implemented;
    
    /**
     * Creates a new sequential translator.  
     */
    public SequentialTranslator() {
        _data = new SequentialTranslatorData<X>();
        _implemented = new Implemented<X>();
    }
    
    @Override
    public int getSATVariable(VariableLoader loader, 
      int firstTimeStep, int lastTimeStep, 
      Property<SequentialHypothesis<X>> p, SequentialHypothesisSpace<X> s) {
        _data.checkTrueSet(loader);

        final boolean isDescent = p.isDescent();
        final SequentialHypothesis<X> h = p.getHypothesis();
        
        
        if (h.equals(s.getMinimalHypothesis())) {
            return _data.truthVariable(isDescent);
        }
        
        for (final SequentialHypothesis<X> prefix: prefixes(h)) {
            final boolean isMinimalHypothesis = prefix.equals(s.getMinimalHypothesis());
            for (int t=firstTimeStep-1 ; t<lastTimeStep ; t++) {
                final SequentialVariable<X> var = new SequentialVariable<X>(isDescent, prefix, firstTimeStep, t);
                if (_data.containsVariable(var)) {
                    continue;
                }
                final int satVar;
                if (isMinimalHypothesis) {
                    // everything is descent of the minimal hypothesis
                    // nothing is not descent of the minimal hypothesis
                    satVar = _data.truthVariable(isDescent); 
                } else if (firstTimeStep-1 == t) {
                    // empty trajectory is descent of nothing (but the minimial hypothesis)
                    // empty trajectory is not descent of everything (but the minimal hypothesis)
                    satVar = _data.truthVariable(!isDescent);
                } else {
                    satVar = loader.allocate(1);
                }
                _data.setVariable(var, satVar);
            }
        }
        
        return _data.getVariable(new SequentialVariable<X>(isDescent, h, firstTimeStep, lastTimeStep-1));
    }
    
    private UnmodifiableList<SequentialHypothesis<X>> prefixes(SequentialHypothesis<X> h) {
        final UnmodifiableListConstructor<SequentialHypothesis<X>> con = 
                new UnmodifiableListConstructor<SequentialHypothesis<X>>();
        final List<X> list = new ArrayList<X>();
        con.addElement(new SequentialHypothesis<X>(list));
        
        for (final X x: h.elements()) {
            list.add(x);
            con.addElement(new SequentialHypothesis<X>(list));
        }
        
        return con.getList();
    }
    
    @Override
    public void createSATClauses(ClauseStream out, VariableAssigner varass, TimedSemanticFactory<X> fact, 
      int firstTimeStep, int lastTimeStep, 
      Property<SequentialHypothesis<X>> p) {
        _data.checkTruthValueEnforced(out);
        
        
        if (p.isDescent()) {
            createAlphaSATClauses(out, varass, fact, firstTimeStep, lastTimeStep, p.getHypothesis(), _implemented);
        } else {
            createOmegaSATClauses(out, varass, fact, firstTimeStep, lastTimeStep, p.getHypothesis(), _implemented);
        }
    }

    private void createAlphaSATClauses(ClauseStream out, VariableAssigner varass, TimedSemanticFactory<X> fact, 
      int firstTimeStep, int lastTimeStep, 
      SequentialHypothesis<X> h, Implemented<X> implemented) {
        final List<X> list = h.elements();
        final List<SequentialHypothesis<X>> prefixes = prefixes(h);
        for (int i=0 ; i<prefixes.size() -1 ; i++) {
            // Will define h2 wrt h1 and h[satVar]
            final SequentialHypothesis<X> h1 = prefixes.get(i);
            final SequentialHypothesis<X> h2 = prefixes.get(i+1);
            final X x = list.get(i);

            for (int t=firstTimeStep ; t<lastTimeStep ; t++) { 
                // h2[begin,t] <-> h2[begin,t-1] \/ (h1[begin,t-1] /\ x[t])
                // a -> b \/ (c /\ d)
                // (notice that b -> c)
                // translated in (the last two are not essential): 
                // 1. a -> b \/ d
                // 2. a -> c
                //////// 3. b -> a
                //////// 4. (c /\ d) -> a
                final int a, b, c, d;
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(true, h2, firstTimeStep, t);
                    if (implemented.implementedVariable(sem)) {
                        continue;
                    }
                    implemented.setImplementedVariable(sem);
                    if (implemented != _implemented) {
                        _implemented.setImplementedVariable(sem);
                    }
                }
                
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(true, h2, firstTimeStep, t);
                    a = _data.getVariable(sem);
                }
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(true, h2, firstTimeStep, t-1);
                    b = _data.getVariable(sem);
                }
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(true, h1, firstTimeStep, t-1);
                    c = _data.getVariable(sem);
                }
                {
                    d = varass.getVariable(fact.buildSemantics(x, t));
                }
                
                out.put(-a, b, d);
                out.put(-a, c);
//                out.put(-b, a);
//                out.put(-c, -d, a);
            }
        }
    }
    
    private void createOmegaSATClauses(ClauseStream out, VariableAssigner varass, TimedSemanticFactory<X> fact, 
      int firstTimeStep, int lastTimeStep, 
      SequentialHypothesis<X> h, Implemented<X> implemented) {
        final List<X> list = h.elements();
        final List<SequentialHypothesis<X>> prefixes = prefixes(h);
        for (int i=0 ; i<prefixes.size() -1 ; i++) {
            // Will define h2 wrt h1 and h[satVar]
            final SequentialHypothesis<X> h1 = prefixes.get(i);
            final SequentialHypothesis<X> h2 = prefixes.get(i+1);
            final X x = list.get(i);
            
//            {
//                final SequentialVariable<X> sem = new SequentialVariable<X>(true, h2, firstTimeStep, firstTimeStep-1);
//                if (!_data.implementedVariable(sem)) {
//                    final int var = _data.getVariable(sem);
//                    out.put(-var);
//                    _data.setImplementedVariable(sem);
//                }
//            }

            for (int t=firstTimeStep ; t<lastTimeStep ; t++) { 
                // not !h2[begin,t] <- not !h2[begin,t-1] \/ (not !h1[begin,t-1] \/ x[t])
                // not a <- not b \/ (not c /\ d)
                // (notice that c -> b)
                // translated in (the first two are not essential): 
                //////// 1. not a -> not b \/ d
                //////// 2. not a -> not c
                // 3. not b -> not a
                // 4. not c /\ d -> not a
                final int a, b, c, d;
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(false, h2, firstTimeStep, t);
                    if (implemented.implementedVariable(sem)) {
                        continue;
                    }
                    implemented.setImplementedVariable(sem);
                    if (implemented != _implemented) {
                        _implemented.setImplementedVariable(sem);
                    }
                }
                
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(false, h2, firstTimeStep, t);
                    a = _data.getVariable(sem);
                }
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(false, h2, firstTimeStep, t-1);
                    b = _data.getVariable(sem);
                }
                {
                    final SequentialVariable<X> sem = new SequentialVariable<X>(false, h1, firstTimeStep, t-1);
                    c = _data.getVariable(sem);
                }
                {
                    d = varass.getVariable(fact.buildSemantics(x, t));
                }
                
                out.put(b, -a);
                out.put(c, -d, -a);
            }
        }
    }
    
    @Override
    public Conflict<SequentialHypothesis<X>> SATConflictToDiagnosisConflict(int[] conflict) {
        final Set<Property<SequentialHypothesis<X>>> props = 
                new HashSet<Property<SequentialHypothesis<X>>>();
        
        for (final int satVar: conflict) {
            final SequentialVariable<X> var = _data.semanticsOfVariable(-satVar);
            final SequentialHypothesis<X> h = var.hypothesis();
            final boolean b = var.isDescent();
            final Property<SequentialHypothesis<X>> p = Property.propertyDescendant(b, h);
            props.add(p);
        }
        
        return new Conflict<SequentialHypothesis<X>>(props);
    }

    @Override
    public void createAllSATClauses(ClauseStream out, VariableAssigner varass, 
    TimedSemanticFactory<X> fact, 
    int firstTimeStep, int lastTimeStep, Collection<Property<SequentialHypothesis<X>>> ps) {
        _data.checkTruthValueEnforced(out);
        
        final Implemented<X> implemented = new Implemented<X>();
        
        for (final Property<SequentialHypothesis<X>> p: ps) {
            if (p.isDescent()) {
                createAlphaSATClauses(out, varass, fact, firstTimeStep, lastTimeStep, p.getHypothesis(), implemented);
            } else {
                createOmegaSATClauses(out, varass, fact, firstTimeStep, lastTimeStep, p.getHypothesis(), implemented);
            }
        }
    }
}
class TrueVariable {
    
    private int _var = 0;
    private boolean _varDefined = false;
    private boolean _truthValueEnforced = false;
    
    public TrueVariable() {
    }
    
    public void checkTrueSet(VariableLoader loader) {
        if (!_varDefined) {
            _var = loader.allocate(1);
            _varDefined = true;
        }
    }
    
    public void checkValueEnforced(ClauseStream out) {
        if (!_truthValueEnforced) {
            out.put(_var);
            _truthValueEnforced = true;
        }
    }
    
    public int trueVariable() {
        if (!_varDefined) {
            throw new IllegalStateException();
        }
        return _var;
    }
    
    public int falseVariable() {
        if (!_varDefined) {
            throw new IllegalStateException();
        }
        return -_var;
    }
    
    public int variable(boolean value) {
        if (value) {
            return trueVariable();
        } else {
            return falseVariable();
        }
    }
}

class SequentialVariable<X> implements VariableSemantics {
    /**
     * Is it a descent?
     */
    private final boolean _isDescent;
    
    /**
     * What sequence?
     */
    private final SequentialHypothesis<X> _h;
    
    /**
     * The min time step.  
     */
    private final int _first;
    
    /**
     * The max time step.  
     */
    private final int _last;
    
    public SequentialVariable(boolean isDescent, SequentialHypothesis<X> h, int first, int last) {
        _isDescent = isDescent;
        _h = h;
        _first = first;
        _last = last;
    }
    
    public boolean isDescent() {
        return _isDescent;
    }
    
    public SequentialHypothesis<X> hypothesis() {
        return _h;
    }
    
    public int first() {
        return _first;
    }
    
    public int last() {
        return _last;
    }
    
    @Override
    public int hashCode() {
        final int d = (_isDescent)? 1 : 0;
        final int h = _h.hashCode();
        final int f = _first;
        final int l = _last;
        
        return Pair.hashCode(Pair.hashCode(d, h), Pair.hashCode(f, l));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        
        if (!(obj instanceof SequentialVariable<?>)) {
            return false;
        }
        
        final SequentialVariable<X> other = (SequentialVariable<X>)obj;
        
        if (_first != other._first) {
            return false;
        }
        if (_last != other._last) {
            return false;
        }
        if (_isDescent != other._isDescent) {
            return false;
        }
        if (!_h.equals(other._h)) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return (_isDescent?"Descents ":"Does not descent ") + _h.toString() 
                + " during [" + _first + ", " + _last + "]";
    }
}

// An encapsulation of the data
class SequentialTranslatorData<X> {
    /**
     * The true variable.  Always true.  
     */
    private final TrueVariable _trueVariable;
    
    private final Map<SequentialVariable<X>, Integer> _varToInt;
    
    private final Map<Integer, SequentialVariable<X>> _intToVar;
    
    public SequentialTranslatorData() {
        _trueVariable = new TrueVariable();
        _varToInt = new HashMap<SequentialVariable<X>, Integer>();
        _intToVar = new HashMap<Integer, SequentialVariable<X>>();
    }

    public void checkTrueSet(VariableLoader loader) {
        _trueVariable.checkTrueSet(loader);

    }
    
    public int truthVariable(boolean var) {
        return _trueVariable.variable(var);
    }
    
    public void checkTruthValueEnforced(ClauseStream out) {
        _trueVariable.checkValueEnforced(out);
    }

    public int getVariable(SequentialVariable<X> sem) {
        final Integer result = _varToInt.get(sem);
        if (result == null) {
            return 0;
        }
        return result;
    }
    
    public boolean containsVariable(SequentialVariable<X> sem) {
        final int var = getVariable(sem);
        return (var != 0);
    }
    
    public void setVariable(SequentialVariable<X> sem, int var) {
 //       System.out.println(sem + " --> " + var);
        _varToInt.put(sem, var);
        _intToVar.put(var, sem);
    }
    
    public void debug() {
        for (final SequentialVariable<X> seq: _varToInt.keySet()) {
            System.out.println(seq + " --> " + _varToInt.get(seq));
        }
    }
    
    public SequentialVariable<X> semanticsOfVariable(int var) {
        return _intToVar.get(var);
    }
}

class Implemented<X> {
    
    private final Set<SequentialVariable<X>> _implemented;
    
    public Implemented() {
        _implemented = new HashSet<SequentialVariable<X>>();
    }
    
    public boolean implementedVariable(SequentialVariable<X> sem) {
        return _implemented.contains(sem);
    }
    
    public void setImplementedVariable(SequentialVariable<X> sem) {
        _implemented.add(sem);
    }
    
}