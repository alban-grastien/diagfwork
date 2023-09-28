package lang;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A formula that is satisfied if there exists a path between two specified components, 
 * possibly through another specified component, such that all components 
 * (except the first and last components) on the path satisfy a specified formula.  
 * 
 * @author Alban Grastien 
 * @version 1.0
 * */
public class YAMLDExistsPath implements YAMLDFormula {
	/**
	 * The network on which the path existence is tested.  
	 * */
	private final Network _net;
	
	/**
	 * First component.  
	 * */
	final YAMLDComponent _first;
	
	/**
	 * Last component.  
	 * */
	final YAMLDComponent _last;
	
	/**
	 * Middle component (<code>null</code> if there is none).  
	 * */
	final YAMLDComponent _mid;
	
	/**
	 * The type of connections in the path.  
	 * */
	final YAMLDConnType _connt;
	
	/**
	 * The formula that must be satisfied by the components of the path.  
	 * */
	final YAMLDFormula _f;
	
	/**
	 * A flag that indicates whether the list of paths should be kept.  
	 * */
	private boolean _keepPaths = true;
	
	/**
	 * The list of paths (equals <code>null</code> if not computed or not kept).
	 * */
	private Collection<Path> _pathes;
	
	public YAMLDExistsPath(Network net, YAMLDComponent first, YAMLDComponent last, 
			YAMLDComponent mid, 
			YAMLDConnType connt, YAMLDFormula f) {
		_net = net;
		_first = first;
		_last = last;
		_mid = mid;
		_connt = connt;
		_f = f;
	}
	
	public YAMLDExistsPath(Network net, YAMLDComponent first, YAMLDComponent last, 
			YAMLDConnType connt, YAMLDFormula f) {
		_net = net;
		_first = first;
		_last = last;
		_mid = null;
		_connt = connt;
		_f = f;
	}

	public boolean satisfiedToo(State state, YAMLDComponent con) {
		//System.out.println("+++++++++++++++++++++++++++++");
		//System.out.println(this.toFormattedString());
		
		if (_mid != null) {
			if (!_f.satisfied(state, _mid)) {
				return false;
			}
		}

		final Set<YAMLDComponent> forbidden = new HashSet<YAMLDComponent>();
		final Set<YAMLDComponent> sat = new HashSet<YAMLDComponent>();
		sat.add(_first);
		sat.add(_last); // because these two don't have to satisfy the formula
		final Set<YAMLDComponent> unsat = new HashSet<YAMLDComponent>();
		
		if (_mid != null) {
			sat.add(_mid);
		}

		final boolean result;
		if (_mid == null) {
//			System.out.println("BEGIN SIMPLE");
			result = findSimpleSatisfyingPath(state, _last, _first, forbidden, sat, unsat);
//			System.out.println("END SIMPLE");
		} else {
			if (!findSimpleSatisfyingPath(state, _mid, _first, forbidden, sat, unsat)) {
				result = false;
			} else {
				result = findComplexSatisfyingPath(state, _last, _mid, _first, forbidden, sat, unsat);
			}
		} 
		return result;
	}
	
	/**
	 * Tests whether the property associated with the path 
	 * is satisfied for the specified component 
	 * in the specified state.  
	 * For efficiency, the results are stored in two specified sets: 
	 * <ul>
	 * <li><code>sat</code> contains the components 
	 * for which the property was proved, and</li>
	 * <li><code>unsat</code> contains the components 
	 * for which the property was disproved.</li>
	 * </ul>
	 * */
	private boolean testComponent(State st, YAMLDComponent comp, 
			Set<YAMLDComponent> sat, Set<YAMLDComponent> unsat) {
		if (sat.contains(comp)) {
			return true;
		} else if (unsat.contains(comp)) {
			return false;
		} else {
			boolean result = _f.satisfied(st, comp);
			if (result) {
				sat.add(comp);
			} else {
				unsat.add(comp);
			}
			return result;
		}
	}
	
	public boolean findComplexSatisfyingPath(State st, 
			YAMLDComponent from, 
			YAMLDComponent through, 
			YAMLDComponent to, 
			Set<YAMLDComponent> forbidden, 
			Set<YAMLDComponent> sat, 
			Set<YAMLDComponent> unsat)  {
		
		if (from == through) {
			return findSimpleSatisfyingPath(st, from, to, forbidden, sat, unsat);
		}
		
		if (!testComponent(st, from, sat, unsat)) {
			return false;
		}
		
		forbidden.add(from);
		
		for (final YAMLDConnection conn: from.conns()) {
			if (!conn.type().equals(_connt)) {
				continue;
			}
			
			final YAMLDComponent next = conn.target();
			if (forbidden.contains(next)) {
				continue;
			}
			if (findComplexSatisfyingPath(st, next, through, to, forbidden, sat, unsat)) {
				forbidden.remove(from);
				return true;
			}
		}
		
		forbidden.remove(from);
		
		return false;
	}
	
	public boolean findSimpleSatisfyingPath(State st, 
			YAMLDComponent from,  
			YAMLDComponent to, 
			Set<YAMLDComponent> forbidden, 
			Set<YAMLDComponent> sat, 
			Set<YAMLDComponent> unsat)  {
		return findSimpleSatisfyingPathToo(st, from, to, forbidden, sat, unsat);
	}
	
	public boolean findSimpleSatisfyingPathToo(State st, 
			YAMLDComponent from,  
			YAMLDComponent to, 
			Set<YAMLDComponent> forbidden, 
			Set<YAMLDComponent> sat, 
			Set<YAMLDComponent> unsat) {
		final Deque<YAMLDComponent> open = new ArrayDeque<YAMLDComponent>();
		open.add(from);
		final Set<YAMLDComponent> closed = new HashSet<YAMLDComponent>();
		closed.add(from);
		
		while (!open.isEmpty()) {
			final YAMLDComponent comp = open.pop();
			
			for (final YAMLDConnection conn: comp.conns()) {
				if (!conn.type().equals(_connt)) {
					//System.out.println("Incorrect connection type");
					continue;
				}
				
				final YAMLDComponent next = conn.target();
				if (next == to) {
					return true;
				}
				
				if (!testComponent(st, next, sat, unsat)) {
					continue;
				}
				
				if (forbidden.contains(next)) {
					//System.out.println("Loop");
					continue;
				}
				
				if (closed.contains(next)) {
					continue;
				}
				
				open.push(next);
				closed.add(next);
			}
		}
		
		return false;
	}
		
	public boolean findSimpleSatisfyingPathOne(State st, 
				YAMLDComponent from,  
				YAMLDComponent to, 
				Set<YAMLDComponent> forbidden, 
				Set<YAMLDComponent> sat, 
				Set<YAMLDComponent> unsat)  {
		// TODO: Implement with an open list.  
		
		//System.out.println("findSatisfyingPath >> ");
		//System.out.println("\tfrom >> " + from.name());
		//System.out.println("\tto   >> " + to.name());
		{
			final boolean fromSatisfies;
			if (sat.contains(from)) {
				fromSatisfies = true;
			} else if (unsat.contains(from)) {
				fromSatisfies = false;
			} else {
				fromSatisfies = _f.satisfied(st, from);
				if (fromSatisfies) {
					sat.add(from);
				} else {
					unsat.add(from);
				}
			}
			
			if (!fromSatisfies) {
				//System.out.println("Not satisfied in from");
				return false;
			}
		}
		
		if (from == to) {
			return true;
		}
		
		forbidden.add(from);
		
		//System.out.println("Looking for connections");
		for (final YAMLDConnection conn: from.conns()) {
			//System.out.println("Looking for connection " + conn + " to " + conn.target().name());
			if (!conn.type().equals(_connt)) {
				//System.out.println("Incorrect connection type");
				continue;
			}
			
			final YAMLDComponent next = conn.target();
			if (forbidden.contains(next)) {
				//System.out.println("Loop");
				continue;
			}
			
			if(findSimpleSatisfyingPathOne(st, next, to, forbidden, sat, unsat)) {
				forbidden.remove(from);
				return true;
			}
		}
		//System.out.println("End looking for connections");
		
		
		forbidden.remove(from);
		
		return false;
	}
	
	@Override
	public boolean satisfied(State state, YAMLDComponent con) {
		return satisfiedToo(state, con);
	}
	
	public boolean satisfiedOne(State state, YAMLDComponent con) {
		// TODO Quite inefficient right now: we don't want to recompute 
		//  all the path at each iteration!
		final Collection<Path> paths = Util.getPaths(_net.getComponents(), 
				_first, _last, _connt);
//		if (_pathes == null) {
//			_pathes = Util.getPaths(_net.getComponents(), 
//					_first, _last, _connt);
//		}
		
		for (final Path p: paths) {
			if (_mid != null) {
				if (!p.contains(_mid)) {
					continue;
				}
			}
			final YAMLDComponent comp = p.satisfied(state, _f); 
			if (comp == null) {
				// All comps of p satisfy f.
				return true;
			}
		}

		return false;
	}
	
	public boolean smartSatisfied(State state, YAMLDComponent con) {
		final Set<YAMLDComponent> satisfyingComponents = new HashSet<YAMLDComponent>();
		
		// The list of components on which a path is searched for
		final Set<YAMLDComponent> comps = new HashSet<YAMLDComponent>(_net.getComponents());
		for (;;) {
			final Path path = findPath(_first, _last, _mid, _connt);
			if (path == null) {
				break;
			}
			
			boolean satisfyingPath = true;
			for (final YAMLDComponent comp: path) {
				if (satisfyingComponents.contains(comp)) {
					continue;
				}
				final boolean compResult = _f.satisfied(state, comp);
				if (compResult == false) {
					comps.remove(comp); // We remove it to avoid accepting this component again.
					satisfyingPath = false;
					break;
				}
				satisfyingComponents.add(comp);
			}
			
			if (satisfyingPath) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Path findPath( 
			YAMLDComponent first, YAMLDComponent last, YAMLDComponent mid, 
			YAMLDConnType ct) {
		final Set<YAMLDComponent> pathComps = new HashSet<YAMLDComponent>();
		pathComps.add(last);
		List<YAMLDComponent> path = reversedRecGetPaths(last, pathComps, first, mid, ct);
		return new ArrayPath(path);
	}
	
	private static List<YAMLDComponent> reversedRecGetPaths(
			YAMLDComponent currentComp, Set<YAMLDComponent> pathComps,
			YAMLDComponent orig, YAMLDComponent mid, YAMLDConnType connt) {
		if (currentComp == orig) {
			if (mid != null && !pathComps.contains(mid)) {
				return null;
			}
			
			final List<YAMLDComponent> result = new LinkedList<YAMLDComponent>();
			result.add(currentComp);
			return result;
		}
		
		for (final YAMLDConnection con: currentComp.conns()) {
			if (!con.type().equals(connt)) {
				continue;
			}
			final YAMLDComponent comp = con.target();
			if (pathComps.contains(comp)) {
				continue;
			}
			
			pathComps.add(comp);
			
			final List<YAMLDComponent> result = 
				reversedRecGetPaths(comp, pathComps, orig, mid, connt);
			if (result != null) {
				result.add(0, currentComp);
				return result;
			}
			
			pathComps.remove(comp);
		}
		
		return null;
	}
	
	private Collection<Path> getModifiablePathes() {
		if (_pathes != null) {
			return _pathes;
		}
		
		Collection<Path> pathes = Util.getPaths(_net.getComponents(), 
				_first, _last, _connt);
		final Collection<Path> result;
		
		if (_mid == null) {
			result = pathes;
		} else {		
			result = new ArrayList<Path>();
			for (final Path p: pathes) {
				if (p.contains(_mid)) {
					result.add(p);
				}
			}
		}
		if (_keepPaths) {
			_pathes = result;
		}
		
		return result;
	}
	
	/**
	 * Returns a list of paths relevant for this formula.  
	 *  
	 * */
	public Collection<Path> getPathes() {
		return Collections.unmodifiableCollection(getModifiablePathes());
	}

	@Override
	public YAMLDFormula simplify(Network net) {
		return this;
	}

	@Override
	public String toFormattedString() {
		return "exists " + _connt + "(" + _first.name() + ".." + 
			((_mid != null)? _mid.name() + ".." : "") 
			+ _last.name() + ") (" + _f.toFormattedString() + ")";
	}
	
	/**
	 * Returns the condition that must be satisfied by the components on the path 
	 * (except the first and last components).  
	 * */
	public YAMLDFormula getCondition() {
		return _f;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_connt == null) ? 0 : _connt.hashCode());
		result = prime * result + ((_f == null) ? 0 : _f.hashCode());
		result = prime * result + ((_first == null) ? 0 : _first.hashCode());
		result = prime * result + ((_last == null) ? 0 : _last.hashCode());
		result = prime * result + ((_mid == null) ? 0 : _mid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof YAMLDExistsPath)) {
			return false;
		}
		YAMLDExistsPath other = (YAMLDExistsPath) obj;
		if (_connt == null) {
			if (other._connt != null) {
				return false;
			}
		} else if (!_connt.equals(other._connt)) {
			return false;
		}
		if (_f == null) {
			if (other._f != null) {
				return false;
			}
		} else if (!_f.equals(other._f)) {
			return false;
		}
		if (_first == null) {
			if (other._first != null) {
				return false;
			}
		} else if (!_first.equals(other._first)) {
			return false;
		}
		if (_last == null) {
			if (other._last != null) {
				return false;
			}
		} else if (!_last.equals(other._last)) {
			return false;
		}
		if (_mid == null) {
			if (other._mid != null) {
				return false;
			}
		} else if (!_mid.equals(other._mid)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isTriviallyFalse(Network net, YAMLDComponent c) {
		// Try to find a path that is not trivially false
		for (final Path p: getPathes()) {
			boolean isTriviallyFalseForP = false;
			final YAMLDComponent first = p.first();
			final YAMLDComponent last = p.last();
			for (final YAMLDComponent comp: p) {
				if (p == first || p == last) {
					continue;
				}
				if (_f.isTriviallyFalse(net, comp)) {
					isTriviallyFalseForP = true;
					break;
				}
			}
			if (!isTriviallyFalseForP) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isTriviallyTrue(Network net, YAMLDComponent c) {
		// Try to find a path that is trivially true
		for (final Path p: getPathes()) {
			boolean isTriviallyTrueForP = true;
			final YAMLDComponent first = p.first();
			final YAMLDComponent last = p.last();
			for (final YAMLDComponent comp: p) {
				if (p == first || p == last) {
					continue;
				}
				if (!_f.isTriviallyTrue(net, comp)) {
					isTriviallyTrueForP = false;
					break;
				}
			}
			if (isTriviallyTrueForP) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public THREE_VALUED_BOOL partiallySatisfied(PartialState state,
			YAMLDComponent comp) {
		// TODO Quite inefficient right now: we don't want to recompute 
		//  all the path at each iteration!
		final Collection<Path> paths = Util.getPaths(_net.getComponents(), 
				_first, _last, _connt);

		boolean undef = false;
		for (final Path p: paths) {
			if (_mid != null) {
				if (!p.contains(_mid)) {
					continue;
				}
			}
			
			boolean allYes = true;
			boolean no = false;
			for (final YAMLDComponent c: p) {
				THREE_VALUED_BOOL b = _f.partiallySatisfied(state, c);
				if (b == THREE_VALUED_BOOL.FALSE) {
					no = true;
					break;
				}
				if (b == THREE_VALUED_BOOL.UNDEFINED) {
					allYes = false;
				}
			}
			
			if (no) {
				continue;
			}
			if (allYes) {
				return THREE_VALUED_BOOL.TRUE;
			}
			undef = true;
		}

		if (undef) {
			return THREE_VALUED_BOOL.UNDEFINED;
		}
		return THREE_VALUED_BOOL.FALSE;
	}

    @Override
    public void test(YAMLDComponent c, Network net) {
        final Set<YAMLDComponent> open = new HashSet<YAMLDComponent>();
        final Set<YAMLDComponent> closed = new HashSet<YAMLDComponent>();
        
        open.add(_first);
        open.add(_last);
        
        while (!open.isEmpty()) {
            final YAMLDComponent cc = open.iterator().next();
            open.remove(cc);
            closed.add(cc);
            
            _f.test(cc,net);
			
			for (final YAMLDConnection conn: cc.conns()) {
				if (!conn.type().equals(_connt)) {
					//System.out.println("Incorrect connection type");
					continue;
				}
				
				final YAMLDComponent next = conn.target();
                if (closed.contains(next)) {
                    continue;
                }
                open.add(next);
            }
        }
    }
}
