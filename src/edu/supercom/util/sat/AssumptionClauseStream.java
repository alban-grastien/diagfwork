/*
 * AssumptionClauseStream.java
 *
 * Created on 6 May 2008, 17:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.supercom.util.sat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This implementation of clause stream passes the clauses it receives to a
 * <code>clauseStream</code> under a specific assumption.  It is then possible
 * to dismiss this assumption.  To create an assumption, just encapsulate the
 * <code>clauseStream</code> in an <code>assumptionClauseStream</code>:
 * <blockquote>
 *   AssumptionClauseStream acs = new AssumptionClauseStream(cs,loader);
 * </blockquote>
 * where <code>loader</code> is a {@link VariableLoader}.  Next, any clause
 * sent to <code>acs</code> will be sent to the <code>cs</code> with an
 * additional literal <code>-v</code> where <code>v</code> is a particular
 * variable loaded by this <code>acs</code>.  However, when a clause is unit,
 * its literal is simply added to the list of assumptions.  Whenever the {@link
 * #endAssumption()} method is called, the assumption <code>v</code> is
 * discarded.  Closing this stream will close <code>mcs</code> as well.
 * <p />
 * <b>Important</b>: Because of the optimization with the unit clauses, an
 * <code>assumptionClauseStream</code> should <i>not</i> encapsulate a
 * <code>clauseStream</code> that modifies the unit clauses (such as an {@link
 * ImplyStream} object).
 *
 * @author Alban Grastien
 * @version 1.0.10
 * @since 1.0.10
 */
public class AssumptionClauseStream extends AbstractClauseStream implements ClauseStream {
    
    /**
     * The clause stream where the clauses must be put.
     */
    private final ClauseStream _mcs;
    
    /**
     * The variable that is used as an assumption.
     */
    private final int _var;
    
    /**
     * The list of assumptions associated with this assumption thanks to unit
     * clauses.
     */
    private final List<Integer> _ass;
    
    /**
     * Creates a new instance of AssumptionClauseStream.
     *
     * @param mcs the minisat clause stream where the clauses must be put.
     * @param loader a variable loader that enables to create a new variable.
     */
    public AssumptionClauseStream(ClauseStream mcs, VariableLoader loader) {
        _var = loader.allocate(1);
        _mcs = mcs;
        _ass = new ArrayList<Integer>();
        _mcs.put(_var,-_var); // make sure that it knows about this variable.
    }
    
    @Override
    public void put(Clause clause) {
        if (clause.size() == 1) {
            _ass.add(new Integer(clause.literal(0)));
            return;
        }
        final Clause extended = new Clause(clause,-_var);
        _mcs.put(extended);
    }
    
    @Override
    public void put(int lit) {
        _ass.add(new Integer(lit));
    }
    
    @Override
    public void put(int lit1, int lit2) {
        _mcs.put(lit1,lit2,-_var);
    }
    
    @Override
    public void put(Clause [] clauses) {
//        List<Clause> extendeds = new ArrayList<Clause>();
//        for (int i=0 ; i<clauses.length ; i++) {
//            final Clause clause = clauses[i];
//            if (clause.size() == 1) {
//                _ass.add(new Integer(clause.literal(0)));
//                continue;
//            }
//            final Clause extended = new Clause(clause,-_var);
//            extendeds.add(extended);
//        }
//        final Clause[] newarray = new Clause[extendeds.size()];
//        extendeds.toArray(newarray);
//        _mcs.put(newarray);
        for (final Clause clause: clauses) {
            put(clause);
        }
    }
    
    @Override
    public void close() {
        _mcs.close();
    }
    
    /**
     * Ends the assumption.  This indicates the output clause stream that this assumption is no longer considered.
     */
    public void endAssumption() {
        _mcs.put(-_var);
    }
    
    @Override
    public boolean solve() {
        return solve(new int[0]);
    }
    
    /**
     * Runs the solver of the first clause stream with the specified list of assumptions.
     *
     * @param asses the list of assumptions.
     * @return true if the problem can be solved with the specified list of assumptions.
     */
    public static boolean solve(AssumptionClauseStream [] asses) {
        final List<Integer> lits = new ArrayList<Integer>();
        
        for (int i=1 ; i<asses.length ; i++) {
            final AssumptionClauseStream ass = asses[i];
            lits.add(new Integer(ass._var));
            lits.addAll(ass._ass);
        }
        
        int[] arrayLits = new int[lits.size()];
        {
            int i=0;
            Iterator<Integer> it = lits.iterator();
            while (it.hasNext()) {
                Integer integer = it.next();
                arrayLits[i] = integer.intValue();
                i++;
            }
        }
        return asses[0].solve(arrayLits); 
        // Apply it on asses[0] because arrayLits was computed starting i=1
    }
    
    @Override
    public boolean solve(int [] ass) {
        final int[] real = new int[_ass.size() + ass.length + 1];
        
        int i = 0;
        {
            final Iterator<Integer> it = _ass.iterator();
            while (it.hasNext()) {
                Integer integer = it.next();
                real[i] = integer.intValue();
                i++;
            }
        }
        for (int j=0 ; j<ass.length ; j++) {
            real[i] = ass[j];
            i++;
        }
        real[i] = _var;
        return _mcs.solve(real);
    }
    
    @Override
    public boolean model(int var) {
        return _mcs.model(var);
    }
    
    public static void main(String [] args) {
        MinisatClauseStream minisat = new MinisatClauseStream();
        VariableLoader loader = new VariableLoader();
        TotalizerEncoding encoding = new BailleuxEncoding();
        Totalizer tot,tot2;
        {
            Totalizer a1 = encoding.encode(loader.allocate(1));
            Totalizer a2 = encoding.encode(loader.allocate(1));
            Totalizer a3 = encoding.encode(loader.allocate(1));
            Totalizer a4 = encoding.encode(loader.allocate(1));
            Totalizer a5 = encoding.encode(loader.allocate(1));
            
            Totalizer t1 = encoding.encode(a1,a2);
            Totalizer t2 = encoding.encode(a3,a4);
            tot = encoding.encode(t1,t2);
            
            Totalizer v1 = encoding.encode(a3,a5);
            Totalizer v2 = encoding.encode(a2,a4);
            tot2 = encoding.encode(v1,v2);
        }
        tot.upgrade(loader,minisat);
        tot.upgrade(loader,minisat);
        tot.upgrade(loader,minisat);
        tot2.upgrade(loader,minisat);
        tot2.upgrade(loader,minisat);
        tot2.upgrade(loader,minisat);
        AssumptionClauseStream ass1 = new AssumptionClauseStream(minisat,loader);
        {
            tot.ge(2,ass1);
        }
        AssumptionClauseStream ass2 = new AssumptionClauseStream(minisat,loader);
        {
            tot2.less(2,ass2);
        }
        AssumptionClauseStream[] asses = {ass1,ass2};
        System.out.println(AssumptionClauseStream.solve(asses));
        for (int i=0 ; i<5 ; i++) {
            System.out.println((ass1.model(i+1)?1:-1) * (i+1));
        }
    }
}
