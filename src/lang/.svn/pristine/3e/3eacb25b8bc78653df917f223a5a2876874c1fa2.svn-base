package lang;

import java.util.ArrayList;

@Deprecated
public class YAMLDTrans
{
	private YAMLDEvent _eventTrigger = null;
	private YAMLDFormula _formula = null;
	private ArrayList<YAMLDAssignment> _assments = null;
	private final YAMLDComponent _comp;
	
	public boolean isforced = false;
	
	public double earliest, latest;

	public YAMLDTrans(YAMLDComponent comp, YAMLDEvent ev, YAMLDFormula f)
	{
		_eventTrigger = ev; setFormula(f);
		_comp = comp;
	}

	public YAMLDTrans(YAMLDComponent comp, YAMLDFormula f)
	{
		setFormula(f);
		_comp = comp;
	}
	
	public YAMLDTrans(YAMLDComponent comp, YAMLDEvent e)
	{
		setEventTrigger(e);
		_comp = comp;
	}

	public void addFormula(YAMLDFormula loadFormula) 
	{
		setFormula(loadFormula);
	}

	public void addAssignment(YAMLDAssignment loadAssignment) 
	{
		if (_assments == null)
			_assments = new ArrayList<YAMLDAssignment>();

		_assments.add(loadAssignment);
	}

	public void setEventTrigger(YAMLDEvent _eventTrigger) 
	{
		this._eventTrigger = _eventTrigger;
	}

	public void makeForced(double earliest, double latest) 
	{
		isforced = true;
		this.earliest = earliest;
		this.latest = latest;
	}
	
	public YAMLDEvent eventTrigger() 
	{
		return _eventTrigger;
	}

	public void setFormula(YAMLDFormula _formula) 
	{
		this._formula = _formula;
	}

	public YAMLDFormula formula() 
	{
		return _formula;
	}

	public void setAssignments(ArrayList<YAMLDAssignment> al) 
	{
		if (_assments == null)
			_assments = new ArrayList<YAMLDAssignment>();
		
		_assments.addAll(al);
	}

	public ArrayList<YAMLDAssignment> assignments() 
	{
		return _assments;
	}
	
	public String toFormattedString()
	{
		String str = (isforced? "forced " : "") + "transition ";

		if (_eventTrigger != null && _eventTrigger.name().length() > 0)
			str += _eventTrigger.name() + ": ";
		
		if (isforced) 
			str += "[" + earliest + ".." + latest + "] ";

		if (_formula != null)
			str += _formula.toFormattedString() + " -> ";
		
		if (_assments != null)
			for (YAMLDAssignment ac : _assments) 
				str += ac.toFormattedString() + " ";
		
		return str;
	}
	
	/**
	 * Returns the component for which this transition applies.  
	 * 
	 * @return the component this transition is defined for.  
	 * */
	public YAMLDComponent getComponent() {
		return _comp;
	}

	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 1;
		result = prime * result + ((_eventTrigger == null) ? 0 : _eventTrigger.hashCode());
		result = prime * result + ((_formula == null) ? 0 : _formula.hashCode());
		result = prime * result + ((_assments == null) ? 0 : _assments.hashCode());
		result = prime * result + ((_comp == null) ? 0 : _comp.hashCode());
		return result;
	}
}
