package edu.supercom.util.auto.changer;
 
import java.util.HashMap;
import java.util.Map;

/**
 * This class is an implementation of LabelChanger. The labels are replaced
 * by a simple label.
 *
 * @author Carole Aujames
 * @version 1.0
 * @since 1.0
 */
public class Abstraction<L> implements LabelChanger<L,Integer> {

	/**
	 * A map that maps a label with a simple label created automaticaly.
	 */
	private Map<L,Integer> _buffer = new HashMap<L, Integer>();
	
	/**
	 * The number on the label.
	 */
	private int _counter = 0;

    @Override
    public Integer getLabel(L key) {
        Integer result = _buffer.get(key);
        if (result != null) {
            return result;
        }
        result = new Integer(_counter++);
        _buffer.put(key, result);
        return result;
    }
}
