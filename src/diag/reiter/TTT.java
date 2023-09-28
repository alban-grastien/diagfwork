package diag.reiter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import diag.reiter.HypothesisSubspace.HypothesisSubspaceType;
import diag.reiter.HypothesisSubspace.SearchType;
import diag.reiter.hypos.SequentialConflict;
import diag.reiter.hypos.SequentialHypothesis;
import diag.reiter.hypos.SequentialHypothesisSpace;

import lang.MMLDlightParser;
import lang.Network;
import lang.YAMLDEvent;
import util.AlarmLog;
import edu.supercom.util.Options;
import edu.supercom.util.sat.VariableAssigner;
import edu.supercom.util.sat.VariableLoader;

public class TTT {

	public static void main(String[] args) {
		final Options opt = new Options(args);

		final Network net = Test.readNetwork(opt);
		final Set<YAMLDEvent> faults = Test.readFaults(net, opt);
		final AlarmLog log = Test.readAlarms(net, opt);

		final List<HypothesisSubspace<SequentialHypothesis>> subspaces = 
			new ArrayList<HypothesisSubspace<SequentialHypothesis>>();
		
		final int nbTrans = Test.getNbTrans(opt, log);
		for (int i=0;; i++) {
			final String o = opt.getOption("h"+i);
			if (o == null) {
				break;
			}
			final SequentialHypothesis hypo = Test.readHypothesis(net, "@h"+i, opt, faults, nbTrans);
			final HypothesisSubspace<SequentialHypothesis> hss;
			if (o.equals("in")) {
				hss = new HypothesisSubspace<SequentialHypothesis>(hypo, 
						HypothesisSubspaceType.SUB, SearchType.INCLUDE);
			} else {
				hss = new HypothesisSubspace<SequentialHypothesis>(hypo, 
						HypothesisSubspaceType.SUB, SearchType.EXCLUDE);
			}
			subspaces.add(hss);
		}
		final SequentialHypothesisSpace space = 
			new SequentialHypothesisSpace(faults, nbTrans);
		

		final Diag<SequentialHypothesis,SequentialConflict> diag = 
			new Diag<SequentialHypothesis,SequentialConflict>(
					net, MMLDlightParser.st, space, faults, log);
		final SequentialHypothesis h = 
			diag.newSearchHypothesis(new VariableLoader(), 
					new HashMap<HypothesisSubspace<SequentialHypothesis>, VariableAssigner>(),
					opt, subspaces).getHypothesis();
		System.out.println(h);
		
	}

}
