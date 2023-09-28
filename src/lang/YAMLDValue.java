package lang;

import java.util.HashMap;
import java.util.Map;

/**
 * Possible value associated with a variable.  
 * 
 * @author Alban Grastien
 * @version 1.0
 * */
public abstract class YAMLDValue implements YAMLDExpr {

	/**
	 * Mapping that keeps the values already created.  
	 * */
	private static final Map<String, YAMLDValue> _values = new HashMap<String, YAMLDValue>();
	
	/**
	 * The constructor is package protected.  The methods {@link #getValue(int)} 
	 * and {@link #getValue(String)} should be used to access values.  
	 * */
	YAMLDValue() {
	}
	
	/**
	 * Returns the {@link YAMLDValue} corresponding to the specified value.  
	 * In case this value is unknown until now, a new object is created.  
	 * Note that for efficiency, a string should be transformed to a value as 
	 * soon as possible.
	 * 
	 * @param s the string that represents the value.  If the value is an integer, 
	 * it has to be an integer in a string format such that 
	 * {@link Integer#parseInt(String)} does not fail (a possibility is to use 
	 * {@link Integer#toString(int)}.  Another possibility is to use 
	 * {@link #getValue(int)}.  
	 * @return the value corresponding to the specified string.  
	 * */
	public static YAMLDValue getValue(String s) {
		{
			final YAMLDValue result = _values.get(s);
			if (result != null) {
				return result;
			}
		}
		
		try {
			final int i = Integer.parseInt(s);
			final YAMLDIntValue result = new YAMLDIntValue(i);
			_values.put(s, result);
			return result;
		} catch (NumberFormatException e) {
		}
		
		final YAMLDValue result = new YAMLDStringValue(s);
		_values.put(s, result);
		return result;
	}

	/**
	 * Returns the {@link YAMLDValue} corresponding to the specified value.  
	 * In case this value is unknown until now, a new object is created.  
	 * Note that for efficiency, a string should be transformed to a value as 
	 * soon as possible.
	 * 
	 * @param i the integer value.
	 * @return the value corresponding to <code>i</code>.    
	 * */
	public static YAMLDValue getValue(int i) {
		final String s = Integer.toString(i);
		
		{
			final YAMLDValue result = _values.get(s);
			if (result != null) {
				return result;
			}
		}
		
		final YAMLDValue result = new YAMLDIntValue(i);
		_values.put(s, result);
		return result;
	}
	
	/**
	 * Adds the specified values.  This assumes that the specified values represent integer.  
	 * The behaviour in case this assumption is violated is unspecified (but you will 
	 * probably get an exception).  
	 * 
	 * @param v1 the first value to be added.  
	 * @param v2 the second value to be added.  
	 * @return the result of the addition of the two values.  
	 * */
	public static YAMLDValue add(YAMLDValue v1, YAMLDValue v2) {
		if (v1 instanceof YAMLDIntValue && v2 instanceof YAMLDIntValue) {
			return ((YAMLDIntValue)v1).add((YAMLDIntValue)v2);
		}
		
		throw new IllegalArgumentException("Cannot add '" + 
				v1 + "' with '" + v2 + "': not integers.");
	}
	
	/**
	 * Indicates whether a value exists for the specified string.  
	 * 
	 * @param n the name of the value.  
	 * @return <code>true</code> if there exists a value with specified name.    
	 * */
	public static boolean existsValue(String n) {
		return _values.containsKey(n);
	}
	
	/**
	 * Subtracts the specified values.  This assumes that the specified values represent integer.  
	 * The behaviour in case this assumption is violated is unspecified (but you will 
	 * probably get an exception).  
	 * 
	 * @param v1 the first value to be added.  
	 * @param v2 the second value to be added.  
	 * @return the result of the addition of the two values.  
	 * */
	public static YAMLDValue sub(YAMLDValue v1, YAMLDValue v2) {
		if (v1 instanceof YAMLDIntValue && v2 instanceof YAMLDIntValue) {
			return ((YAMLDIntValue)v1).sub((YAMLDIntValue)v2);
		}
		
		throw new IllegalArgumentException("Cannot substract '" + 
				v1 + "' from '" + v2 + "': not integers.");
	}

	@Override
        public YAMLDValue value(State state, YAMLDComponent con) {
		return this;
	}

	@Override
	public YAMLDExpr simplify(Network net) {
		return this;
	}

	@Override
	public YAMLDValue getTrivialValue(Network net, YAMLDComponent con) {
		return this;
	}

	@Override
	public YAMLDValue partialValue(PartialState state, YAMLDComponent con) {
		return this;
	}
	
    @Override
    public void test(YAMLDComponent c, Network net) {
    }
}

class YAMLDIntValue extends YAMLDValue {
	private final int _i;
	
	public YAMLDIntValue(int i) {
		_i = i;
	}
	
	@Override
	public String toString () {
		return Integer.toString(_i);
	}
	
	public YAMLDValue add(YAMLDIntValue v) {
		return YAMLDValue.getValue(this._i + v._i);
	}
	
	public YAMLDValue sub(YAMLDIntValue v) {
		return YAMLDValue.getValue(this._i - v._i);
	}

	@Override
	public String toFormattedString() {
		return toString();
	}
}

class YAMLDStringValue extends YAMLDValue {
	private final String _s;
	
	public YAMLDStringValue(String s) {
		_s = s;
	}
	
	@Override
	public String toString () {
		return _s;
	}

	@Override
	public String toFormattedString() {
		return toString();
	}
}
