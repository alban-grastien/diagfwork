package diag.reiter;

import java.util.Set;

import diag.reiter.hypos.SequentialConflict;
import diag.reiter.hypos.SequentialHypothesis;
import diag.reiter.hypos.SequentialHypothesisSpace;

import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDEvent;
import util.AlarmLog;
import edu.supercom.util.Options;
import edu.supercom.util.sat.VariableLoader;

public class SearchConflict {

	/**
	 * Tests the specified hypothesis at name <b>h</b> 
	 * and prints the conflict if a conflict is found 
	 * or the trajectory if a trajectory is found.  
	 * 
	 * @see Test#readHypothesis(Network, String, Options).
	 * */
	public static void main(String[] args) {
		final Options opt = new Options(args);

		final Network net = Test.readNetwork(opt);
		final Set<YAMLDEvent> faults = Test.readFaults(net, opt);
		final AlarmLog log = Test.readAlarms(net, opt);

		final int nbTrans = Test.getNbTrans(opt, log);
		final SequentialHypothesis hypo = Test.readHypothesis(net, "@h", opt, faults, nbTrans);
		final SequentialHypothesisSpace space = 
			new SequentialHypothesisSpace(faults, nbTrans);
		

		final Diag<SequentialHypothesis,SequentialConflict> diag = 
			new Diag<SequentialHypothesis,SequentialConflict>(
				net, MMLDlightParser.st, space, faults, log);
		final ExtendedDiagnosisReport<SequentialConflict> xrep = 
			diag.testHypothesis(new VariableLoader(), hypo, opt);
		
		if (xrep.getReport() != null) {
			System.out.println(xrep.getReport().getScenario().toFormattedString());
		} else {
			final Conflict con = xrep.getConflict();
			System.out.println(con);
		}
	}
}
