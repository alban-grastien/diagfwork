package edu.supercom.util.auto.changer;

import java.util.HashMap;
import java.util.Map;

/**
 * A <code>CopyAvoider</code>, i.e., a copy avoider,
 * is a label changer that makes sure two equal labels 
 * point to the same object.  
 * This avoids multiple copies of the same object.  
 */
public class CopyAvoider<X> implements LabelChanger<X, X> {
    
    /**
     * The maps that indicates the 'normal' object, 
     * i.e., the representative of the class of equivalent objects.  
     */
    public Map<X,X> _normalisationMap;

    public CopyAvoider() {
        _normalisationMap = new HashMap<X, X>();
    }

    @Override
    public X getLabel(X old) {
        {
            final X result = _normalisationMap.get(old);
            if (result != null) {
                return result;
            }
        }
        
        _normalisationMap.put(old, old);
        return old;
    }
}
