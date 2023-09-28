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

public class BottomUp {

	public static void main(String[] args) {
		final Options opt = new Options(args);

		final Network net = Test.readNetwork(opt);
		final Set<YAMLDEvent> faults = Test.readFaults(net, opt);
		final AlarmLog log = Test.readAlarms(net, opt);
		final SequentialHypothesisSpace space = 
			new SequentialHypothesisSpace(faults, Test.getNbTrans(opt, log));

		final Diag<SequentialHypothesis,SequentialConflict> diag = 
			new Diag<SequentialHypothesis,SequentialConflict>(
				net, MMLDlightParser.st, space, faults, log);
		diag.bottomUp(opt);

		System.out.println();
		System.out.println("FINISHED");
		for (final Candidate<SequentialHypothesis> h: diag.diagnosisCandidates()) {
			System.out.println("CANDIDATE >> " + h.getHypothesis());
		}
	}
}
