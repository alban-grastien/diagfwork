package diag.auto;

import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDComponent;
import util.AlarmLog;
import util.ImmutableAlarmLog;

/**
 * Test of class Language.  
 */
public class _Language {
    
    public static void testToDot() throws Exception {
        final Language diagLanguage1;
        final Language diagLanguage2;
        
        final String networkAddress = "YAMLDSim/data/dx07/dx07.mmld";
        final Network net = Network.readNetwork(networkAddress);
        
        final String logAddress = "YAMLDSim/data/dx07/obs1";
        final AlarmLog log = ImmutableAlarmLog.readLogFile(net, logAddress);
        
        {
            final YAMLDComponent comp = net.getComponent("c00");
            final Language modLanguage = new Language(net, comp, MMLDlightParser.st);
            //System.out.println(modLanguage.toString("Model"));
            
            final Language obsLanguage = new Language(net, comp, log);
            //System.out.println(obsLanguage.toString("Observations"));
        
            diagLanguage1 = Language.intersect(modLanguage, obsLanguage);
            System.out.println(diagLanguage1.toString("Diagnosis"));
        }
        
        {
            final YAMLDComponent comp = net.getComponent("c10");
            final Language modLanguage = new Language(net, comp, MMLDlightParser.st);
            //System.out.println(modLanguage.toString("Model"));
        
            final Language obsLanguage = new Language(net, comp, log);
            //System.out.println(obsLanguage.toString("Observations"));
        
            diagLanguage2 = Language.intersect(modLanguage, obsLanguage);
            System.out.println(diagLanguage2.toString("Diagnosis"));
        }
        
        final Language proj = Language.intersect(diagLanguage1, diagLanguage2).project(diagLanguage1.getAlphabet());
        System.out.println(proj);
        
//        final Language diagLanguage = Language.intersect(diagLanguage1, diagLanguage2);
    }
    
    public static void main(String[] args) throws Exception {
        testToDot();
    }
    
}
