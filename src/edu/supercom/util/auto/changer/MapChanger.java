package edu.supercom.util.auto.changer;

import java.util.Map;

/**
 * A <b>label changer</b> defined by a map.
 *
 * @author Alban Grastien
 */
public class MapChanger<L1,L2> implements LabelChanger<L1,L2> {

    private Map<? super L1,? extends L2> _map;

    /**
     * Creates a changer from the specified map.
     *
     * @param map the map used by the changer.  
     */
    public MapChanger(Map<? super L1, ? extends L2> map) {
        _map = map;
    }

    @Override
    public L2 getLabel(L1 old) {
        return _map.get(old);
    }
}
