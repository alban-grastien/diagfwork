package gui;

import lang.Network;
import lang.Period;
import lang.State;
import lang.TimedState;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * The component in charge of showing which events can take place.  
 * */
public class EventManager implements StateListener, NetworkListener 
{
	private final Menu _menu;
	private MenuItem _elapseEvent, _nextEvent, _boundedNextEvent;
    private final InteractiveInterface _ii;
	
	private EventManager(Menu eventMenu, InteractiveInterface ii) 
	{
		_menu = eventMenu;
        _ii = ii;
		_ii.addStateListener(this);
		_ii.addNetworkListener(this);
	}
    
    /**
     * Creates an event manager in the specified menu 
     * that reacts according to the specified interactive interface.  
     */
    public static void createEventManager(Menu eventMenu, InteractiveInterface ii) {
        final EventManager em = new EventManager(eventMenu, ii);
    }
	
    @Override
	public void newNetwork(Network net) 
	{
        for (final MenuItem it: _menu.getItems()) {
            it.dispose();
        }
        
		{
	    	_elapseEvent = new MenuItem(_menu, SWT.PUSH);
	    	_elapseEvent.setText("Elapse 1 seconde");
            
            _elapseEvent.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) 
				{
					_ii.elapseTime(new Period(1));
				}
			});
		}
		
		{ 
	    	_nextEvent = new MenuItem(_menu, SWT.PUSH);
	    	_nextEvent.setText("Next event");
	    	_nextEvent.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) 
				{
					_ii.nextEvent();
				}
			});
		}
		
		{ 
	    	_boundedNextEvent = new MenuItem(_menu, SWT.PUSH);
	    	_boundedNextEvent.setText("Bounded next event");
	    	_boundedNextEvent.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					_ii.elapseOrOneForcedTransition(new Period(1));
				}
			});
		}
	}

	@Override
	@Deprecated
	public void newStateHandler(State state) {
        updateEnabled();
    }
	
	@Override
	public void newStateHandler(TimedState state) {
        updateEnabled();
	}
    
    private void updateEnabled() {
        final boolean finalState = _ii.isInFinalState();
        _elapseEvent.setEnabled(finalState);
        _nextEvent.setEnabled(true); // TODO: should be hasForced
        _boundedNextEvent.setEnabled(finalState);
    }
}
