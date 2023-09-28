package lang;

/**
 * A partial state is a state that is only partially defined.
 * */
public interface PartialState {

	/**
	 * Returns the value associated with the specified variable if possible.  
	 * 
	 * @param var the variable whose value is requested.  
	 * @return the value of <code>var</code> if it can be retrieved, 
	 * <code>null</code> otherwise.  
	 * */
	public YAMLDValue getPartialValue(YAMLDGenericVar var);
	
	/**
	 * Returns the network on which this state is defined.  
	 * 
	 * @return the network this state is defined on.  
	 * */
	public Network getNetwork();
	
}
