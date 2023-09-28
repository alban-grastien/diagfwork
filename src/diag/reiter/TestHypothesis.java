package diag.reiter;

import java.util.Set;

import diag.DiagnosisReport;
import diag.reiter.hypos.SequentialConflict;
import diag.reiter.hypos.SequentialHypothesis;
import diag.reiter.hypos.SequentialHypothesisSpace;

import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDEvent;
import util.AlarmLog;
import edu.supercom.util.Options;
import edu.supercom.util.sat.VariableLoader;

public class TestHypothesis {

	public static void main(String[] args) {
		final Options opt = new Options(args);

		final Network net = Test.readNetwork(opt);
		final Set<YAMLDEvent> faults = Test.readFaults(net, opt);
		final AlarmLog log = Test.readAlarms(net, opt);
		final int nbTrans = Test.getNbTrans(opt, log);
		final SequentialHypothesis hypo = Test.readHypothesis(net, "@h", opt, faults, nbTrans);
		final SequentialHypothesisSpace space = 
			new SequentialHypothesisSpace(faults, nbTrans);

		System.out.println("Testing hypothesis " + hypo);
		
		final Diag<SequentialHypothesis,SequentialConflict> diag = 
			new Diag<SequentialHypothesis,SequentialConflict>(
				net, MMLDlightParser.st, space, faults, log);
		final ExtendedDiagnosisReport<SequentialConflict> xrep = 
			diag.testHypothesis(new VariableLoader(), hypo, opt);
		final DiagnosisReport rep = xrep.getReport();
		if (rep == null) {
			System.out.println("NO SOLUTION");
		} else {
			System.out.println("SATISFIABLE");
//			System.out.println(rep.getScenario().toFormattedString());
		}
		
//		final ExtendedDiagnosisReport xrep = diag(net, log, faults, hypo, MMLDlightParser.st, opt);
//		final DiagnosisReport rep = xrep.getReport();
//		if (rep == null) {
//			System.err.println(xrep.getConflict());
//		} else {
//			Util.printSolution(rep, net);
//		}
		
		//final Scenario sce = rep.getScenario();
		//System.out.println(sce.toFormattedString());
	}
}
