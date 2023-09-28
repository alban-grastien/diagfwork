package diag.symb.javabdd;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BuDDyFactory;
import net.sf.javabdd.CUDDFactory;
import net.sf.javabdd.JFactory;

/**
 * A <code>BDDTest</code>, i.e., 
 */
public class BDDTest {
    
    public static void main(String [] args) {
        System.out.println("Zongo");
        final BDDFactory fact = CUDDFactory.init(1, 1);
        //final BDDFactory fact = BuDDyFactory.init(100, 100);
        //final BDDFactory fact = JFactory.init(100, 100);
//        System.out.println("Zongo");
//        
//        final int var1 = fact.extVarNum(1);
//        final int var2 = fact.extVarNum(1);
//        
//        System.out.println("Zongo");
//        
//        fact.extVarNum(1024);
//        
//        
//        final BDD bdd1 = fact.ithVar(var1);
//        final BDD bdd2 = fact.ithVar(var2);
//        
//        final BDD and = bdd1.and(bdd2);
//        final BDD not = and.not();
//        final BDD zero = and.or(not);
//        if (zero.isZero()) {
//            System.out.println("Zero");
//        } else {
//            System.out.println("Pas zero");
//        }
//        for (int i=0 ; i<2000 ; i++) {
//            System.out.println(fact.extVarNum(1));
//            System.out.println(fact.varNum());
//        }
    }
    
}
