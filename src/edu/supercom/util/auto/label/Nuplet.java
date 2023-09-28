package edu.supercom.util.auto.label;

import java.util.Collection;
import java.util.Iterator;

/**
 * A nuplet state label is a nuplet of state labels.
 *
 * @author Carole Aujames
 * @version 1.0
 * @since 1.0
 */
public class Nuplet<SL> {

    /**
     *  The array of state labels.
     */
    private SL[] _ssl;

    /**
     * Creates a new nuplet state label.
     *
     * @param ssl the array of state labels.
     */
    public Nuplet(SL[] ssl) {
        _ssl = ssl;
    }

    /**
     * Creates a new nuplet state label.
     *
     * @param ssl the array of state labels.
     */
    @SuppressWarnings("unchecked")
    public Nuplet(Collection<SL> ssl) {
        _ssl = (SL[])new Object[ssl.size()];
        int i=0;
        Iterator<SL> it = ssl.iterator();
        while (it.hasNext()) {
            _ssl[i] = it.next();
            i++;
        }
    }

    /**
     * Returns a hashcode value for this nuplet.  The hashcode value is simply 
     * the addition of the hashcode value of each state label.
     *
     * @return a hashcode value for this nuplet.
     */
    @Override
    public int hashCode() {
        int res = 0;
        for (int i = 0; i < _ssl.length; i++) {
            res = res + _ssl[i].hashCode();
        }
        return res;
    }

    /**
     * Indicates whether this nuplet of state labels equals the specified object.
     * Two nuplets are equals if they contain the same state labels.
     *
     * @param o the object to compare to this.
     * @return <code>true</code> if <code>o</code> equals <code>this</code>.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Nuplet) {
            Nuplet nsl = (Nuplet) o;
            for (int i = 0; i < _ssl.length; i++) {
                if (!this.get(i).equals(nsl.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the state label.
     *
     * @return the state label.
     */
    public SL get(int i) {
        return (SL)_ssl[i];
    }
}
