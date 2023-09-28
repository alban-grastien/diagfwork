package edu.supercom.util.sat;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Transforms the clauses received by this stream 
 * and generates the specified number of clauses 
 * corresponding to the shifting of the variables 
 * by the specified number of units.
 * 
 * @since 2.0
 * */
public class ShiftClauseStream extends AbstractClauseStream 
  implements ClauseStream {
	
	/**
	 * The list of shifts.  
	 * */
	private final Collection<LiteralShifter> _shifts;
	
	/**
	 * The clause stream where the generated clauses should be transmitted.  
	 * */
	private final ClauseStream _out;
	
	public ShiftClauseStream(ClauseStream out, Collection<LiteralShifter> shifts) {
		_shifts = new ArrayList<LiteralShifter>(shifts);
		_out = out;
	}

	@Override
	public void close() {
		_out.close();
	}
	
	@Override
	public void put(Clause clause) {
		final int size = clause.size();
		final int[] newClause = new int[size];
		for (final LiteralShifter shifter: _shifts) {
			for (int i=0 ; i<size ; i++) {
				final int lit = clause.literal(i);
				if (lit == 0) {
					newClause[i] = 0;
					continue;
				}
				final int kur = shifter.renameLiteral(lit);
				newClause[i] = kur;
			}
			_out.put(newClause);
		}
	}
	
	@Override
	public void put(int lit) {
		for (final LiteralShifter shifter: _shifts) {
			final int kur = shifter.renameLiteral(lit);
			_out.put(kur);
		}
	}

	@Override
	public void put(int lit1, int lit2) {
		for (final LiteralShifter shifter: _shifts) {
			final int kur1 = shifter.renameLiteral(lit1);
			final int kur2 = shifter.renameLiteral(lit2);
			_out.put(kur1,kur2);
		}
	}

	@Override
	public void put(int lit1, int lit2, int lit3) {
		for (final LiteralShifter shifter: _shifts) {
			final int kur1 = shifter.renameLiteral(lit1);
			final int kur2 = shifter.renameLiteral(lit2);
			final int kur3 = shifter.renameLiteral(lit3);
			_out.put(kur1,kur2,kur3);
		}
	}

	@Override
	public void put(int[] tab) {
		final int size = tab.length;
		final int[] newClause = new int[size];
		for (final LiteralShifter shifter: _shifts) {
			for (int i=0 ; i<size ; i++) {
				final int lit = tab[i];
				if (lit == 0) {
					newClause[i] = 0;
					continue;
				}
				final int kur = shifter.renameLiteral(lit);
				newClause[i] = kur;
			}
			_out.put(newClause);
		}
	}

	@Override
	public void put(Collection<Integer> tab) {
		put(new Clause(tab));
	}	
}
