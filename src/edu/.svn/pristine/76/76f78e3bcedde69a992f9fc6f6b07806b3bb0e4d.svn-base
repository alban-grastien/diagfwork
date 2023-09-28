/*
 * TransLabelIntersecter.java
 *
 * Created on 16 February 2007, 16:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.supercom.util.auto.label;

import edu.supercom.util.UnmodifiableSet;
import edu.supercom.util.UnmodifiableSetConstructor;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements a synchroniser.  The trans label intersecter
 * synchronises <code>Set</code> objects.  Given a set of <i>synchronisation
 * objects</i>, two labels can be synchronised if they share the same
 * synchronisation objects.  The synchronisation is then the label with the
 * union of objects in the two set labels.
 *
 * @author Alban Grastien
 * @author Carole Aujames
 * @version 2.0
 * @since 0.5
 */
public class IntersecterSynchroniser<E>
        extends AbstractSynchroniser<Set<E>, Set<E>, Set<E>, Set<E>, Set<E>>
        implements Synchroniser<Set<E>, Set<E>, Set<E>, Set<E>, Set<E>> {

    /**
     * The set of synchronisation objects.
     */
    private final Set<E> _synchr;

    /**
     * Creates a TransLabelIntersecter.
     *
     * @param synchr the set of synchronisation objects.
     */
    public IntersecterSynchroniser(Set<? extends E> synchr) {
        this._synchr = new HashSet<E>(synchr);
    }

    @Override
    public Set<E> synchroniseLabel(Set<E> s1, Set<E> s2) {
        {
            final Set<E> proj1 = projection(s1);
            final Set<E> proj2 = projection(s2);

            if (!proj1.equals(proj2)) {
                return null;
            }
        }

        final Set<E> result = new HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }

    public Set<E> projection(Set<E> set) {
        final Set<E> s = new HashSet<E>(set);
        s.retainAll(_synchr);
        return s;
    }

    @Override
    public Set<E> projection1(Set<E> tl) {
        return projection(tl);
    }

    @Override
    public Set<E> projection2(Set<E> tl) {
        return projection(tl);
    }

    @Override
    public String toString() {
        return "[" + _synchr + "]";
    }

    @Override
    public boolean synchroniseProjectedLabel1(Set<E> proj, Set<E> tb2) {
        final Set<E> proj2 = projection2(tb2);
        return proj.equals(proj2);
    }

    @Override
    public boolean synchroniseProjectedLabel2(Set<E> tl1, Set<E> proj) {
        final Set<E> proj1 = projection1(tl1);
        return proj.equals(proj1);
    }

    @Override
    public boolean synchroniseProjectedLabels(Set<E> proj1, Set<E> proj2) {
        return proj1.equals(proj2);
    }

    public static <E> Synchroniser<UnmodifiableSet<E>, UnmodifiableSet<E>, UnmodifiableSet<E>, Set<E>, Set<E>>
    unmodifiableIntersecter(final Set<E> inter) {
        return 
        new Synchroniser<UnmodifiableSet<E>, UnmodifiableSet<E>, UnmodifiableSet<E>, Set<E>, Set<E>>() {

                final Synchroniser<Set<E>, Set<E>, Set<E>, Set<E>, Set<E>> _internal =
                        new IntersecterSynchroniser<E>(inter);

                @Override
                public UnmodifiableSet<E> synchroniseLabel(UnmodifiableSet<E> l1, UnmodifiableSet<E> l2) {
                    final Set<E> result = _internal.synchroniseLabel(l1, l2);
                    if (result == null) {
                        return null;
                    }
                    return new UnmodifiableSetConstructor<E>(result).getSet();
                }

                @Override
                public Set<E> projection1(UnmodifiableSet<E> l) {
                    return _internal.projection1(l);
                }

                @Override
                public Set<E> projection2(UnmodifiableSet<E> l) {
                    return _internal.projection2(l);
                }

                @Override
                public boolean synchroniseProjectedLabel1(Set<E> l1, UnmodifiableSet<E> l2) {
                    return _internal.synchroniseProjectedLabel1(l1, l2);
                }

                @Override
                public boolean synchroniseProjectedLabel2(UnmodifiableSet<E> l1, Set<E> l2) {
                    return _internal.synchroniseProjectedLabel2(l1, l2);
                }

                @Override
                public boolean synchroniseProjectedLabels(Set<E> l1, Set<E> l2) {
                    return _internal.synchroniseProjectedLabels(l1, l2);
                }

                @Override
                public Projector<UnmodifiableSet<E>, Set<E>> projector1() {
                    return new Projector<UnmodifiableSet<E>, Set<E>>() {

                        @Override
                        public Set<E> project(UnmodifiableSet<E> l) {
                            return _internal.projector1().project(l);
                        }
                    };
                }

                @Override
                public Projector<UnmodifiableSet<E>, Set<E>> projector2() {
                    return new Projector<UnmodifiableSet<E>, Set<E>>() {

                        @Override
                        public Set<E> project(UnmodifiableSet<E> l) {
                            return _internal.projector2().project(l);
                        }
                    };
                }

                @Override
                public boolean synchronise(UnmodifiableSet<E> l1, UnmodifiableSet<E> l2) {
                    return _internal.synchronise(l1, l2);
                }
            };
    }
}
