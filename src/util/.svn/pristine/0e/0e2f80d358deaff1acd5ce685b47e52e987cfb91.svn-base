
package util;

import java.util.Collection;

import util.*;
import lang.*;

/**
 * Basic implementation of a simple (non-disjunctive) temporal constraint
 * network.
 **/

public class TCN
{
  private final int size;
  private final double[][] matrix;

  public TCN(int s)
  {
    assert(s > 0);
    size = s;
    matrix = new double[size][size];
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
	matrix[i][j] = Double.POSITIVE_INFINITY;
  }

  /**
   * Set max distance from i to j, i.e., t[j] - t[i] <= d.
   */
  public void set_max(int i, int j, double d)
  {
    assert(i < size);
    assert(j < size);
    if (d < matrix[i][j])
      matrix[i][j] = d;
  }

  /**
   * Set min distance from i to j, i.e., t[j] - t[i] >= d.
   */
  public void set_min(int i, int j, double d)
  {
    assert(i < size);
    assert(j < size);
    if (-d < matrix[j][i])
      matrix[j][i] = -d;
  }

  /**
   * Set the distance from i to j to exactly d, i.e., t[j] - t[i] = d.
   */
  public void set_exact(int i, int j, double d)
  {
    set_min(i, j, d);
    set_max(i, j, d);
  }

  /**
   * Compute the minimal distance matrix. This needs to be done after
   * all constraints have been set (using methods above) and before
   * querying the constraint network (with consistent, etc).
   */
  public void compute_minimal()
  {
    for (int k = 0; k < size; k++)
      for (int i = 0; i < size; i++)
	for (int j = 0; j < size; j++) {
	  double d = matrix[i][k] + matrix[k][j];
	  if (d < matrix[i][j])
	    matrix[i][j] = d;
	}
  }

  /**
   * Querying constraint network consistency. This only works if the
   * minimal network has been computed (which is not done automatically).
   */
  public boolean consistent()
  {
    for (int i = 0; i < size; i++)
      if (matrix[i][i] < 0)
	return false;
    return true;
  }

  /**
   * Get maximum distance from i to j, i.e., ub on tj - ti.
   */
  public double max_distance(int i, int j)
  {
    return matrix[i][j];
  }

  /**
   * Get minimum distance from i to j, i.e., lb on tj - ti.
   */
  public double min_distance(int i, int j)
  {
    return -matrix[j][i];
  }

  /**
   * Construct a TCN representing a scenario. The TCN will have one
   * time point variable for each global transition (step) in the
   * scenario, and will have constraints that force the spacing of
   * these to respect observation times (taken from the alarm log).
   */
  static public TCN makeScenarioTCN(Scenario sce, AlarmLog log)
  {
    // The constraint network that we build will have one variable
    // (time point) for each (global) transition in the scenario.
    TCN c = new TCN(sce.nbTrans());

    // step 1: Construct a mapping from the transitions of the scenario
    // to the alarm log. The code below is probably not correct (or at
    // least not complete) in cases where the scenario/log contain
    // multiple occurrences of the same observation and there is some
    // ambiguity about how to match them.
    int[] obs_map = new int[sce.nbTrans()];
    final Collection<YAMLDEvent> obs_events =
      sce.getState(0).getNetwork().observableEvents();
    int next_entry = 0;
    for (int i = 0; i < sce.nbTrans(); i++) {
      obs_map[i] = -1;
      MMLDGlobalTransition trans = sce.getMMLDTrans(i);
      for (YAMLDComponent comp : trans.affectedComponents()) {
	MMLDRule rule = trans.getRule(comp);
	assert(rule != null);
	for (YAMLDEvent ev : rule.getGeneratedEvents()) {
	  if (obs_events.contains(ev)) {
	    boolean found = false;
	    while (!found && (next_entry < log.nbEntries())) {
	      if (log.get(next_entry)._events.contains(ev)) {
		obs_map[i] = next_entry;
		found = true;
	      }
	      else if (obs_map[i] == -1) {
		next_entry += 1;
	      }
	      else {
		System.out.println("error: transition " + trans
				   + " cannot be matched to "
				   + log.get(next_entry));
		return null;
	      }
	    }
	    if (!found) {
	      System.out.println("error: observation " + ev
				 + " of transition " + trans
				 + " not found in log!");
	      return null;
	    }
	  }
	}
      }
    }

    // step 2: Based on the obs map, set time constraints between
    // the transitions that generate log events.
    for (int i = 0; i < sce.nbTrans(); i++)
      for (int j = i + 1; j < sce.nbTrans(); j++)
	if ((obs_map[i] != -1) && (obs_map[j] != -1)) {
      throw new UnsupportedOperationException();
//	  double d = log.get(obs_map[j])._time - log.get(obs_map[i])._time;
//	  c.set_exact(i, j, d);
	}

    return c;
  }

  /**
   * Check if a scenario + time constraints violates some max time
   * rule triggering condition.
   */
  static public boolean checkScenarioTriggers(Scenario sce, TCN c)
  {
    boolean ok = true;
    State s = sce.getState(0);
    final Network net = s.getNetwork();
    for (YAMLDComponent comp : net.getComponents()) {
      for (MMLDTransition trans : comp.transitions()) {
	for (YAMLDFormula prec : trans.getPreconditions()) {
	  //System.out.println("checking " + prec + " of " + trans
	  //		     + " of " + comp + "...");
      throw new UnsupportedOperationException();
//	  TimeInterval tint = trans.getConditionTime(prec);
//	  int i = 0;
//	  while (i < sce.nbTrans()) {
//	    while ((i < sce.nbTrans()) &&
//		   !prec.satisfied(sce.getState(i), comp))
//	      i += 1;
//	    if (i < sce.nbTrans()) {
//	      int ti = (i == 0 ? 0 : i - 1);
//	      int j = i;
//	      while ((j < sce.nbTrans()) &&
//		     prec.satisfied(sce.getState(j), comp))
//		j += 1;
//	      int tj = j - 1;
//	      if (c.min_distance(ti, tj) > tint.getEnd()) {
//		System.out.println("invalid: trigger " + prec
//				   + " holds from state " + i
//				   + " (before transition " + ti
//				   + ") to state " + j
//				   + " (before transition " + tj
//				   + ") which is a min of "
//				   + c.min_distance(ti, tj)
//				   + " > " + tint.getEnd());
//		ok = false;
//	      }
//	      i = j + 1;
//	    }
//	  }
	}
      }
    }
    return ok;
  }

}
