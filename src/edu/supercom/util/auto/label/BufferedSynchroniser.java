/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.supercom.util.auto.label;

import edu.supercom.util.Pair;
import java.util.HashMap;
import java.util.Map;

/**
 * A buffered synchroniser is a synchroniser that remembers the result of some
 * synchroniser operations.
 *
 * @author Alban Grastien
 * @version 2.0
 * @since 2.0
 */
public class BufferedSynchroniser<L1,L2,L,LP1,LP2>
        extends AbstractSynchroniser<L1,L2,L,LP1,LP2>
        implements Synchroniser<L1,L2,L,LP1,LP2> {

    /**
     * Creates a buffered synchroniser that bufferises the specified operation.
     *
     * @param s the synchroniser.  
     * @param sl a Boolean that indicates whether the operation {@link
     * #synchroniseLabel(java.lang.Object, java.lang.Object)} should be buffered.
     * @param p1 a Boolean that indicates whether the operation {@link
     * #projection1(java.lang.Object) } should be buffered.
     * @param p2 a Boolean that indicates whether the operation {@link
     * #projection2(java.lang.Object) } should be buffered.
     * @param s1 a Boolean that indicates whether the operation {@link
     * #synchroniseProjectedLabel1(java.lang.Object, java.lang.Object)} should
     * be buffered.
     * @param s2 a Boolean that indicates whether the operation {@link
     * #synchroniseProjectedLabel2(java.lang.Object, java.lang.Object) } should
     * be buffered.
     * @param sp a Boolean that indicates whether the operation {@link
     * #synchroniseProjectedLabels(java.lang.Object, java.lang.Object) } should
     * be buffered.
     */
    public BufferedSynchroniser(Synchroniser<L1,L2,L,LP1,LP2> s,
            boolean sl, boolean p1, boolean p2, boolean s1, boolean s2, boolean sp) {
        _synch = s;
        if (sl) {
            _synchMap = new HashMap<Pair<L1, L2>, L>();
        }
        if (p1) {
            _proj1 = new HashMap<L1, LP1>();
        }
        if (p2) {
            _proj2 = new HashMap<L2, LP2>();
        }
        if (s1) {
            _synchProj1 = new HashMap<Pair<LP1, L2>, Boolean>();
        }
        if (s2) {
            _synchProj2 = new HashMap<Pair<L1, LP2>, Boolean>();
        }
        if (sp) {
            _synchProj = new HashMap<Pair<LP1, LP2>, Boolean>();
        }
    }

    /**
     * Creates a synchroniser that bufferises all the operations.
     *
     * @param s the synchroniser that is buffered.  
     */
    public BufferedSynchroniser(Synchroniser<L1,L2,L,LP1,LP2> s) {
        this(s, true, true, true, true, true, true);
    }

    private final Object NULL = new Object();

    private Map<Pair<L1,L2>,L> _synchMap;

    private Map<L1,LP1> _proj1;

    private Map<L2,LP2> _proj2;

    private Map<Pair<LP1,L2>,Boolean> _synchProj1;

    private Map<Pair<L1,LP2>,Boolean> _synchProj2;

    private Map<Pair<LP1,LP2>,Boolean> _synchProj;

    private Synchroniser<L1,L2,L,LP1,LP2> _synch;

    @SuppressWarnings("unchecked")
    @Override
    public L synchroniseLabel(L1 l1, L2 l2) {
        if (_synchMap != null) {
            Pair<L1,L2> pair = new Pair<L1, L2>(l1, l2);
            L result = _synchMap.get(pair);
            if (result == NULL) {
                return null;
            }
            if (result == null) {
                result = _synch.synchroniseLabel(l1, l2);
                L save = (result == null) ? (L)NULL: result;
                _synchMap.put(pair, save);
            }
            return result;
        }

        return _synch.synchroniseLabel(l1, l2);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LP1 projection1(L1 tl) {
        if (_proj1 != null) {
            LP1 result = _proj1.get(tl);
            if (result == NULL) {
                return null;
            }
            if (result == null) {
                result = _synch.projection1(tl);
                LP1 save = (result == null) ? (LP1)NULL: result;
                _proj1.put(tl, save);
            }
        }

        return _synch.projection1(tl);
    }

    @SuppressWarnings("unchecked")
    @Override
    public LP2 projection2(L2 tl) {
        if (_proj2 != null) {
            LP2 result = _proj2.get(tl);
            if (result == NULL) {
                return null;
            }
            if (result == null) {
                result = _synch.projection2(tl);
                LP2 save = (result == null) ? (LP2)NULL: result;
                _proj2.put(tl, save);
            }
        }

        return _synch.projection2(tl);
    }

    @Override
    public boolean synchroniseProjectedLabel1(LP1 proj, L2 tl2) {
        if (_synchProj1 != null) {
            Pair<LP1,L2> pair = new Pair<LP1,L2>(proj,tl2);
            Boolean result = _synchProj1.get(pair);
            if (result == null) {
                result = _synch.synchroniseProjectedLabel1(proj, tl2);
                _synchProj1.put(pair, result);
            }
            return result;
        }

        return _synch.synchroniseProjectedLabel1(proj, tl2);
    }

    @Override
    public boolean synchroniseProjectedLabel2(L1 tl1, LP2 proj) {
        if (_synchProj2 != null) {
            Pair<L1,LP2> pair = new Pair<L1,LP2>(tl1,proj);
            Boolean result = _synchProj2.get(pair);
            if (result == null) {
                result = _synch.synchroniseProjectedLabel2(tl1, proj);
                _synchProj2.put(pair, result);
            }
            return result;
        }

        return _synch.synchroniseProjectedLabel2(tl1, proj);
    }

    @Override
    public boolean synchroniseProjectedLabels(LP1 proj1, LP2 proj2) {
        if (_synchProj2 != null) {
            Pair<LP1,LP2> pair = new Pair<LP1, LP2>(proj1, proj2);
            Boolean result = _synchProj.get(pair);
            if (result == null) {
                result = _synch.synchroniseProjectedLabels(proj1, proj2);
                _synchProj.put(pair, result);
            }
            return result;
        }

        return _synch.synchroniseProjectedLabels(proj1, proj2);
    }

    /**
     * Indicates whether the {@link
     * #synchroniseLabel(java.lang.Object, java.lang.Object)}
     * must be buffered.  If this method is called twice, first with
     * <b>false</b> and second with <b>true</b>, the buffer is reset.
     *
     * @param b <code>true</code> if the method must be buffered.
     */
    public void setSynchroniseLabelStatus(boolean b) {
        if (!b) {
            _synchMap = null;
            return;
        }
        if (_synchMap == null) {
            _synchMap = new HashMap<Pair<L1, L2>, L>();
        }
    }

    /**
     * Indicates whether the {@link #projection1(java.lang.Object) }
     * must be buffered.  If this method is called twice, first with
     * <b>false</b> and second with <b>true</b>, the buffer is reset.
     *
     * @param b <code>true</code> if the method must be buffered.
     */
    public void setProjection1Status(boolean b) {
        if (!b) {
            _proj1 = null;
            return;
        }
        if (_proj1 == null) {
            _proj1 = new HashMap<L1, LP1>();
        }
    }

    /**
     * Indicates whether the {@link #projection2(java.lang.Object) }
     * must be buffered.  If this method is called twice, first with
     * <b>false</b> and second with <b>true</b>, the buffer is reset.
     *
     * @param b <code>true</code> if the method must be buffered.
     */
    public void setProjection2Status(boolean b) {
        if (!b) {
            _proj2 = null;
            return;
        }
        if (_proj2 == null) {
            _proj2 = new HashMap<L2, LP2>();
        }
    }

    /**
     * Indicates whether the {@link
     * #synchroniseProjectedLabel1(java.lang.Object, java.lang.Object) }
     * must be buffered.  If this method is called twice, first with
     * <b>false</b> and second with <b>true</b>, the buffer is reset.
     *
     * @param b <code>true</code> if the method must be buffered.
     */
    public void setSynchroniseProjectedLabel1Status(boolean b) {
        if (!b) {
            _synchProj1 = null;
            return;
        }
        if (_synchProj1 == null) {
            _synchProj1 = new HashMap<Pair<LP1, L2>, Boolean>();
        }
    }

    /**
     * Indicates whether the {@link
     * #synchroniseProjectedLabel2(java.lang.Object, java.lang.Object) }
     * must be buffered.  If this method is called twice, first with
     * <b>false</b> and second with <b>true</b>, the buffer is reset.
     *
     * @param b <code>true</code> if the method must be buffered.
     */
    public void setSynchroniseProjectedLabel2Status(boolean b) {
        if (!b) {
            _synchProj2 = null;
            return;
        }
        if (_synchProj2 == null) {
            _synchProj2 = new HashMap<Pair<L1, LP2>, Boolean>();
        }
    }

    /**
     * Indicates whether the {@link
     * #synchroniseProjectedLabels(java.lang.Object, java.lang.Object) }
     * must be buffered.  If this method is called twice, first with
     * <b>false</b> and second with <b>true</b>, the buffer is reset.
     *
     * @param b <code>true</code> if the method must be buffered.
     */
    public void setSynchroniseProjectedLabelsStatus(boolean b) {
        if (!b) {
            _synchProj = null;
            return;
        }
        if (_synchProj == null) {
            _synchProj = new HashMap<Pair<LP1, LP2>, Boolean>();
        }
    }
}


