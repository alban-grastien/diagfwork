package gui;

import edu.supercom.util.Options;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import lang.State;
import lang.TimedState;
import lang.VisMap;
import lang.YAMLDComponent;
import util.TimedScenario;

public class VisWindow extends SimpleWindow
//implements //StateChanger, 
//NetworkChanger
{
	//protected State _state = null;
	//protected TimedState _tstate = null;
	//protected Collection<StateListener> _stateListeners;
	protected Collection<ComponentListener> _componentListeners;
//    protected final Collection<NetworkListener> _networkListeners;
	protected NetworkDisplayManager ndm = null;
	protected LightweightSystem _lws = null;

	protected Canvas canvas;
	protected Composite composite;
    

    protected final ScenarioSelecter _ss;

	// The figure to be displayed in the VisWindow
	protected Figure figure;

	public VisWindow(Options opt) 
	{
//		_stateListeners = new HashSet<StateListener>();
		_componentListeners = new HashSet<ComponentListener>();
//        _networkListeners = new HashSet<NetworkListener>();
        _ss = new ScenarioSelecter(opt);
	}
	
	public void setupVisArea() {
		figure = new Figure();	
		figure.setLayoutManager(new XYLayout());

		_lws = new LightweightSystem(canvas);
		_lws.setContents(figure);
	}

//    @Override
//	public void addStateListener(StateListener sl) {
//		_stateListeners.add(sl);
//	}
//	
//    @Override
//	public void removeStateListener(StateListener sl) {
//		_stateListeners.remove(sl);
//	}
//    
	/**
	 * Invoke the StateListener objects when timed state has changed.
	 * 
	 * Also updates the visualisation mapping's internal state.
	 * 
	 * @param state
	 */
//	public void setState(TimedState state) {
//        _tstate = state;
//		_state = state.getState();
//		notifyStateHandlers();
//	}
	
//	protected void notifyStateHandlers() {
//        final TimedState tstate = _ss.getCurrentState();
//        final State state = tstate.getState();
//        
//		VisMap.getSingletonObject().determineChangedShapes(state);
//		
//		for (StateListener sl: _stateListeners) {
//			sl.newStateHandler(tstate);
//		}
//	}
	
    //////////////////////////

//    @Override
//    public void addNetworkListener(NetworkListener nl) {
//        _networkListeners.add(nl);
//    }
//    
//    @Override
//    public void removeNetworkListener(NetworkListener nl) {
//        _networkListeners.remove(nl);
//    }

    //////////////////////////
//    
//	/**
//	 * Adds the specified component listener to the list of objects 
//	 * monitoring the component currently selected.  
//	 * 
//	 * @param cl the component listener that wants to know 
//	 * when the selected component of the network is modified.  
//	 */
//	public void addComponentListener(ComponentListener cl) {
//		_componentListeners.add(cl);
//	}
//	
//	/**
//	 * Removes the component listener from the list of objects 
//	 * monitoring the component currently selected.  
//	 * 
//	 * @param cl the component listener that no longer wants to know 
//	 * when the selected component of the network is modified.  
//	 */	
//	public void removeComponentListener(ComponentListener cl) {
//		_componentListeners.remove(cl);
//	}
//	
//    public void chooseComponent(YAMLDComponent comp) {
//		for (final ComponentListener cl: _componentListeners)
//			cl.newComponent(comp);
//        
//        System.out.println(_componentListeners);
//	}
}
