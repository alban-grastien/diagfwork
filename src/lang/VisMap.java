package lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.supercom.util.Pair;

public class VisMap
{
	private static VisMap singletonObject;

	/**
	 * Maps a component to a shape.
	 * 
	 * @author baueran
	 */
	
	public class CompShapeMapping
	{
		private YAMLDComponent _comp  = null;
		private VisMapShapeDef _shape = null;

		public CompShapeMapping()
		{
		}

		public CompShapeMapping(VisMapShapeDef sd, YAMLDComponent c)
		{
			_shape = sd; _comp = c;
		}
		
		public void set_comp(YAMLDComponent _comp) 
		{
			this._comp = _comp;
		}
		
		public YAMLDComponent comp() 
		{
			return _comp;
		}
		
		public void set_shape(VisMapShapeDef _shape) 
		{
			this._shape = _shape;
		}
		
		public VisMapShapeDef shape() 
		{
			return _shape;
		}
	}
	
	/* Stores the formula and mapping as defined in the vismap file.  */
	private final List<Pair<YAMLDFormula, CompShapeMapping>> _figs = new ArrayList<Pair<YAMLDFormula,CompShapeMapping>>();

    /* Stores the mapping between components and shapes, but not all of them
     * but only those which are assigned due to the evaluation of states.
     * This means that there may be rules in _completeMap, which have not
     * (yet) appeared in _activeMap.  The users of this class in all likelihood
     * will only want to query this map, as it is no point drawing something
     * whose rule has not yet been triggered. */
	protected HashMap<YAMLDComponent, VisMapShapeDef> _activeMap =
		new HashMap<YAMLDComponent, VisMapShapeDef>();

	public static synchronized VisMap getSingletonObject() 
	{
		if (singletonObject == null)
			singletonObject = new VisMap();
		return singletonObject;
	}

	public Object clone() throws CloneNotSupportedException 
	{
		throw new CloneNotSupportedException();
	}

	/**
	 * This function merges two shapes, if on is set to true.  Then, it 
	 * basically overwrites the values of existingDef with the values of 
	 * updateDef, and returns the merged shape.
	 * 
	 * If on is false, then updateDef is returned.  (This flag essentially
	 * enables or disables the functionality of this function.)
	 * 
	 * If anything goes wrong, null is returned.
	 * 
	 * @param existingDef
	 * @param updateDef
	 * @return Note that merge always returns a fresh copy of existingDef,
	 * not just an updated object!
	 */
	
	private VisMapShapeDef merge(VisMapShapeDef existingDef, 
								 VisMapShapeDef updateDef,
								 boolean on)
	{
		if (existingDef == null || (updateDef == null && !on)) 
			return null;
		
		if (!on) 
			return updateDef;
		
		// We always have to return a new VisMap after merging or
		// otherwise all other shapes on the screen, which also use this
		// map will also contain these changes.
		VisMapShapeDef existingDefClone = existingDef.clone();
		
		if (updateDef.imgPath() != null)
			existingDefClone.setImgPath(updateDef.imgPath());

		if (updateDef.id() != null)
			existingDefClone.setId(updateDef.id());
		
		if (updateDef.foregroundColor() != null)
			existingDefClone.setForegroundColor(updateDef.foregroundColor());

		if (updateDef.backgroundColor() != null)
			existingDefClone.setBackgroundColor(updateDef.backgroundColor());

		// The default size is 24.  Only change size if anything different
		// is set by the user's provided shape.
		// TODO: This default size somewhat sucks!
		if (updateDef.size().x != 24 && updateDef.size().y != 24)
			existingDefClone.setSize(updateDef.size());

		// TODO: The fact that this is commented out means that once you
		// determine the shape of a line, you cannot later change it.
		// This is probably ok as I cannot think of many example where you'd
		// like to do this, but it's worth pointing out!
		//
		//		if (updateDef.line() != null) {
		//			Polyline pl = updateDef.line();
		//			
		//			// pl.getPoints().translate(offset.x, offset.y);
		//			existingDefClone.setLine(pl);
		//		}
		
		if (updateDef.lineWidth() != 0)
			existingDefClone.setLineWidth(updateDef.lineWidth());
		
		return existingDefClone;
	}
	
	public Map<YAMLDComponent, VisMapShapeDef> determineChangedShapes(State state)
	{
		for (final Pair<YAMLDFormula,CompShapeMapping> pair: _figs) 
		{
			final YAMLDFormula f = pair.first();
			boolean f_satisfied = f.satisfied(state, null);
			
			CompShapeMapping csm = pair.second();
								
			if (f_satisfied) {
				// Get current shape for component, if it exists
				VisMapShapeDef oldShape = _activeMap.get(csm.comp());
				
				// Get new shape due to state change
				VisMapShapeDef newShape = csm.shape();
				
				// Merge current shape with new shape and store the
				// *new VisMapShapDef object* in activeMap
				VisMapShapeDef mergedShape = (oldShape == null)? newShape : 
					merge(oldShape, newShape, true); 
				_activeMap.put(csm.comp(), mergedShape);
			}				
		}		
				
		return _activeMap;
	}	

	/**
	 * Add a mapping from a logical formula to a VisMapShapeDef
	 * to the list of mappings.
	 * @param f is the logical formula that determines when sd should be
	 * displayed.
	 * @param sd is the shape to be displayed.
	 */
	
	public void addMapping(YAMLDFormula f, VisMapShapeDef sd, YAMLDComponent c)
	{
		_figs.add(new Pair<YAMLDFormula, CompShapeMapping>(f, 
				new CompShapeMapping(sd,c)));
	}
	
	/**
	 * Returns the shape for a component, if it was assigned via
	 * a rule in the visualisation mapping, and triggered by the simulator
	 * having changed into a state that actually does trigger.
	 * 
	 * Default return value, so to speak, is null.
	 * 
	 * @param comp Component we wish to get a shape for.
	 * @return VisMapShapeDef object if any of the rules in the mapping
	 * applies to component, comp, null otherwise.
	 */
	
	public VisMapShapeDef getChangedShape(YAMLDComponent comp) 
	{
		if (_activeMap != null)
			return _activeMap.get(comp);
		return null;
	}
	
	public void delVisMappings()
	{
		if (_figs != null)
			_figs.clear();

		if (_activeMap != null)
			_activeMap.clear();
	}
}
