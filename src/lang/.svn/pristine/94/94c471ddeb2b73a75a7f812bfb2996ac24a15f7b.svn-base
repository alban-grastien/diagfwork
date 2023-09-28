package lang;


/**
 * This class stores the entire YAMLD model as loaded from disk.
 */

import java.util.Hashtable;

public class YAMLDModel 
{
	private Hashtable<String,YAMLDComponent> comps = null;

	private YAMLDModel() 
	{
		comps = new Hashtable<String,YAMLDComponent>();
	}

	private static class SingletonHolder 
	{ 
		private static final YAMLDModel INSTANCE = new YAMLDModel();
	}
	 
	public static YAMLDModel getInstance() 
	{
		return SingletonHolder.INSTANCE;
	}

	public void addComponent(YAMLDComponent newComp)
	{
		comps.put(newComp.name(), newComp);
	}
}
