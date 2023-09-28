package gui;

import edu.supercom.util.Options;
//import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lang.MMLDRule;
import lang.MMLDTransition;
import lang.Network;
import lang.Period;
import lang.State;
import lang.PeriodInterval;
import lang.TimedState;
import lang.YAMLDComponent;
import lang.YAMLDFormula;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import util.Time;

/**
 * The component manager is the menu responsible for showing the list of components.  
 * */
public class ComponentManager implements StateListener, NetworkListener { 

    private final Options _opt;
	private final Display _d;
	private final Shell _shell;
	private final Menu _menu;

	private Time _currentTime;
    private final InteractiveInterface _ii;
	
	/**
	 * The list of component menus.
	 * */
	private final List<ComponentSubMenu> _comps;
	/**
	 * A map that indicates which submenu corresponds to a specified component.  
	 * */
	private final Map<YAMLDComponent,ComponentSubMenu> _compToSubmenu;
	/**
	 * The final state.
	 * */
	private TimedState _finalState = null;

	private Time _maxNextTime;
	private Time _nextScheduledTime; 
	private MMLDTransition _nextScheduledTransition; 
	private YAMLDFormula _nextScheduledFormula;
	
	private final Image _forced;
	private final Image _nextImage;
    private final String _menuImgDir;

	public ComponentManager(Display d, Shell shell, Menu cptMenu, Options opt, InteractiveInterface ii) 
	{
        _opt = opt;
        _ii = ii;
		_d = d;
		_menuImgDir = GuiOptions.MENUIMGLIB_DIR.getOption(
                _opt, null, false, MainWindow.DEFAULT_MENU_IMG_DIR);
        _forced = new Image(_d, _menuImgDir + "/redandwhitedot.png");
		_nextImage = new Image(_d, _menuImgDir + "/reddot.png");
		_shell = shell;
		_menu = cptMenu;
		
		_comps = new ArrayList<ComponentSubMenu>();
		_compToSubmenu = new HashMap<YAMLDComponent, ComponentSubMenu>();
		ii.addStateListener(this);
		ii.addNetworkListener(this);
	}
	
	@Override
    public void newNetwork(Network net) 
	{
		final Collection<YAMLDComponent> comps = net.getComponents();
		createMenu(comps, _menu,0);
	}
	
	// The names of the components in comps 
	// starts with the same ${depth} letters
	private void createMenu(Collection<YAMLDComponent> comps, Menu menu, int depth) {
		if (comps.size() < 20) {
			for (final YAMLDComponent comp: comps) {
				createMenu(comp,menu);
			}
			return;
		}

		// There are too many components and we class them by name
		int newDepth = depth +1;
		for(;;) {
			final Map<Character,Collection<YAMLDComponent>> map = new HashMap<Character, Collection<YAMLDComponent>>();
			YAMLDComponent shorty = null;
			for (final YAMLDComponent comp: comps) {
				final String name = comp.name();
				if (name.length() < newDepth) {
					shorty = comp;
					continue;
				}
				final char c = name.charAt(newDepth-1);
				Collection<YAMLDComponent> coll = map.get(c);
				if (coll == null) {
					coll = new ArrayList<YAMLDComponent>();
					map.put(c, coll);
				}
				coll.add(comp);
			}
			
			if (shorty == null && map.size() == 1) {
				newDepth++;
				continue;
			}
			
			if (shorty != null) {
				createMenu(shorty, menu);
			}
			
			final List<Map.Entry<Character,Collection<YAMLDComponent>>> collections = 
				new ArrayList<Map.Entry<Character,Collection<YAMLDComponent>>>(map.entrySet());
			// Sort by alphabetical order
			Collections.sort(collections, 
					new Comparator<Map.Entry<Character,Collection<YAMLDComponent>>>() {
						@Override
						public int compare(
								Entry<Character, Collection<YAMLDComponent>> o1,
								Entry<Character, Collection<YAMLDComponent>> o2) {
							return (o1.getKey() - o2.getKey());
						}
				}
			);
			
			for (final Map.Entry<Character,Collection<YAMLDComponent>> entry: collections) {
				final Collection<YAMLDComponent> coll = entry.getValue();
				final YAMLDComponent firstComp = coll.iterator().next();
				if (coll.size() == 1) {
					createMenu(firstComp, menu);
					continue;
				}
				
				String prefix = firstComp.name();
				for (final YAMLDComponent comp: coll) {
					final String otherName = comp.name();
					int i=0;
					while (i < otherName.length() && i < prefix.length()) {
						if (otherName.charAt(i) != prefix.charAt(i)) {
							break;
						}
						i++;
					}
					prefix = prefix.substring(0, i);
				}
				while(prefix.charAt(prefix.length()-1)=='_') {
					if (prefix.length() == newDepth) {
						break;
					}
					prefix = prefix.substring(0,prefix.length()-1);
				}
				
				//final String prefix = comp.name().substring(0,newDepth);
			    
				final MenuItem compMenuHeader = new MenuItem(menu, SWT.CASCADE);
				compMenuHeader.setText(prefix);

			    final Menu collMenu = new Menu(_shell, SWT.DROP_DOWN);
			    compMenuHeader.setMenu(collMenu);
			    
			    createMenu(coll, collMenu, newDepth);
			}
			return;
		}
	}
	
	private void createMenu(final YAMLDComponent comp, Menu menu) {
		final ComponentSubMenu csm = new ComponentSubMenu(comp, menu);
		_comps.add(csm);
		_compToSubmenu.put(comp, csm);

	}


	@Override
	public void newStateHandler(State s) {
	}

	@Override
	public void newStateHandler(TimedState s) {
        final TimedState tstate = _ii.getFinalState();
		if (tstate == _finalState) {
			return;
		}
		_finalState = tstate;
		_currentTime = _ii.getCurrentTime();

		update();
	}
	
	public void update() {
		// Resets the images.  
		for (final ComponentSubMenu csm: _comps) {
			csm.resetImage();
		}
		
		_nextScheduledTransition = null;
		_nextScheduledFormula = null;
		_nextScheduledTime = Time.MAX_TIME;
		_maxNextTime = Time.MAX_TIME;
		
		// Updates every one.
		for (final ComponentSubMenu csm: _comps) {
			csm.update(_finalState);
		}
		
		// Shows the precondition that can be forced 
		for (final ComponentSubMenu csm: _comps) {
			csm.updateEnabled();
		}
		
		if (_nextScheduledFormula != null) {
			final ComponentSubMenu csm = _compToSubmenu.get(_nextScheduledTransition.getComponent());
			final ForcedTransitionSubMenu ftsm = csm.getForcedTransitionSubMenu(_nextScheduledTransition);
			final PreconditionSubMenu psm = ftsm.getPreconditionSubMenu(_nextScheduledFormula);
			psm.setAsNext();
		}
	}
	
	private void setImageToMenuAndParents(Image img, MenuItem it) {
		do {
			it.setImage(img);
			it = it.getParent().getParentItem();
		} while (it != null);
	}
    
	
	private void fail(Shell shell, String mes) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR
            | SWT.CANCEL);
		messageBox.setText(mes);
		messageBox.setMessage(mes);
		messageBox.open();
	}
	
	private PreconditionSubMenu getPreconditionSubMenu(MMLDTransition trans, YAMLDFormula f) {
		return _compToSubmenu.get(trans.getComponent())
			.getForcedTransitionSubMenu(trans).getPreconditionSubMenu(f);
	}
	
    @Deprecated
    private boolean setFirst(MMLDTransition trans, YAMLDFormula f) {
        return false;
        /*
		final PreconditionSubMenu main = getPreconditionSubMenu(trans, f);
		final double nextTime;
		{
			final double max = _maxNextTime;
			final double min = main._minForcedTime;
			if (min > max) {
				return false;
			}
			nextTime = min + ((max - min) * _man.getRandom().rand(1000) / 1000);
		}
		
		for (final ComponentSubMenu csm: _comps) {
			for (final ForcedTransitionSubMenu ftsm: csm._forced) {
				for (final PreconditionSubMenu psm: ftsm._prec) {
					if (!psm._scheduled) {
						continue;
					}
					final double currentTime = psm._scheduledTime;
					if (currentTime > nextTime) {
						continue;
					}
					final double max = psm._maxForcedTime;
					final double min = nextTime;
					final double newTime = min + ((max - min) * 
							(1+_man.getRandom().rand(1000)) / 1000);
					_man.setSchedule(psm._trans, psm._f, newTime);
				}
			}
		}
		
		_man.setSchedule(trans, f, nextTime);
		update();
		
		return true;
         * 
         */
	}
    
	// The class that takes care of a component 
	class ComponentSubMenu {
		public final YAMLDComponent _comp;
		public final List<SpontaneousTransitionSubMenu> _spons;
		public final List<ForcedTransitionSubMenu> _forced;
		public final Map<MMLDTransition,ForcedTransitionSubMenu> _forcedToSubmenu;
		
		public ComponentSubMenu(YAMLDComponent comp, Menu upperMenu) {
			_comp = comp;
			_spons = new ArrayList<SpontaneousTransitionSubMenu>();
			_forced = new ArrayList<ForcedTransitionSubMenu>();
			_forcedToSubmenu = new HashMap<MMLDTransition, ForcedTransitionSubMenu>();
			
			final Menu compMenu;
			final MenuItem compMenuItem = new MenuItem(upperMenu, SWT.CASCADE);
			compMenuItem.setText(comp.name());
			
			compMenu = new Menu(_shell,SWT.DROP_DOWN);
			compMenuItem.setMenu(compMenu);
			
			{
				final MenuItem item = new MenuItem(compMenu, SWT.CASCADE);
				item.setText("Select");
				
				item.addSelectionListener(new SelectionAdapter() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						_ii.chooseComponent(_comp);
					}
				});
			}
			
		    boolean hasSpontaneous = false;
			// The transitions that can take place spontaneously 
		    for (final MMLDTransition tr: comp.transitions()) {
		    	if (!tr.isSpontaneous()) {
		    		continue;
		    	}
		    	if (!hasSpontaneous) {
		    		new MenuItem(compMenu, SWT.SEPARATOR);
			    	hasSpontaneous = true;
		    	}
		    	_spons.add(new SpontaneousTransitionSubMenu(tr,compMenu));
		    }
		    
		    boolean hasForced = false;
		    // The forced transitions
		    for (final MMLDTransition tr: comp.transitions()) {
		    	if (tr.getPreconditions().isEmpty()) {
		    		continue;
		    	}
		    	if (!hasForced) {
		    		new MenuItem(compMenu, SWT.SEPARATOR);
			    	hasForced = true;
		    	}
		    	final ForcedTransitionSubMenu ftsm = new ForcedTransitionSubMenu(tr,compMenu);
		    	_forced.add(ftsm);
		    	_forcedToSubmenu.put(tr,ftsm);
		    }
		    
		    if (!hasForced && !hasSpontaneous) {
		    	// Simpler version.  
		    	compMenu.dispose();
		    	compMenuItem.dispose();
		    	
				
				final MenuItem item = new MenuItem(upperMenu, SWT.CASCADE);
				item.setText(comp.name());
					
				item.addSelectionListener(new SelectionAdapter() {
						
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						_ii.chooseComponent(_comp);
					}
				});
		    }
		}
	
		public void resetImage() {
			for (final ForcedTransitionSubMenu ftsm: _forced) {
				ftsm.resetImage();
			}
		}
		
		public void update(TimedState ts) {
			for (final SpontaneousTransitionSubMenu stsm: _spons) {
				stsm.update(ts);
			}
			
			for (final ForcedTransitionSubMenu ftsm: _forced) {
				ftsm.update(ts);
			}
		}
		
		public void updateEnabled() {
			for (final ForcedTransitionSubMenu ftsm: _forced) {
				ftsm.updateEnabled();
			}
		}
		
		public ForcedTransitionSubMenu getForcedTransitionSubMenu(MMLDTransition trans) {
			return _forcedToSubmenu.get(trans);
		}
	}
	
	// The class that takes care of a spontaneous transition 
	class SpontaneousTransitionSubMenu {
		public final MMLDTransition _trans;
		private final MenuItem _item;
		
		public SpontaneousTransitionSubMenu(MMLDTransition trans, Menu compMenu) {
			_trans = trans;

			_item = new MenuItem(compMenu, SWT.PUSH);
			_item.setText(_trans.getName());
			_item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					_ii.trigger(_trans);
				}
			});
		}
		
		public void update(TimedState ts) {
			final State state = ts.getState();
			// Obviously the default rule can always be triggered (hence so can the transition) 
			// but the default rule has no effect
			// Therefore we consider the transition is enabled 
			// only if some rule precondition is true
			boolean enabled = false;  
			for (final MMLDRule r: _trans.getRules()) {
				if (r.getCondition().satisfied(state, _trans.getComponent())) {
					enabled = true;
				}
			}
			_item.setEnabled(enabled);
		}
	}
	
	// The class that takes care of a forced transition 
	class ForcedTransitionSubMenu {
		public final MMLDTransition _trans;
		public final List<PreconditionSubMenu> _prec;
		public final Map<YAMLDFormula,PreconditionSubMenu> _formToPrec;
		public final MenuItem _item;
		public final MenuItem _transStatusItem;
		public final MenuItem _triggerNowItem;
		public final MenuItem _triggerNextItem;
		public boolean _enabled;
		public boolean _canBeTriggeredNow;
		public Time _transitionScheduledTime;
		
		public ForcedTransitionSubMenu(MMLDTransition trans, Menu compMenu) {
			_trans = trans;
			_prec = new ArrayList<PreconditionSubMenu>();
			_formToPrec = new HashMap<YAMLDFormula, PreconditionSubMenu>();
		
			_item = new MenuItem(compMenu, SWT.CASCADE);
			_item.setText(_trans.getName());
			
			final Menu transMenu = new Menu(_shell,SWT.DROP_DOWN);
			_item.setMenu(transMenu);
			
			{ // The status of the transition (Disabled, [tmin,tmax])
				_transStatusItem = new MenuItem(transMenu, SWT.CASCADE);
				_transStatusItem.setText("Disabled");
				_transStatusItem.setEnabled(false);
			}
			
			{ // Triggers now
				_triggerNowItem = new MenuItem(transMenu, SWT.CASCADE);
				_triggerNowItem.setText("Trigger now");
				_triggerNowItem.setEnabled(false);
				_triggerNowItem.addSelectionListener(new SelectionAdapter() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						_ii.nextEvent();
					}
				});
			}
				
			{ // Triggers next
				_triggerNextItem = new MenuItem(transMenu, SWT.CASCADE);
				_triggerNextItem.setText("Trigger next");
				_triggerNextItem.setEnabled(false);
				_triggerNextItem.addSelectionListener(new SelectionAdapter() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						_ii.trigger(_trans);
					}
				});
			}
			
			new MenuItem(transMenu, SWT.SEPARATOR);
			
			int i=1; // counts the number of preconditions.
			for (final YAMLDFormula f: _trans.getPreconditions()){
				final PreconditionSubMenu psm = new PreconditionSubMenu(f, transMenu, this, trans, i);
				_prec.add(psm);
				_formToPrec.put(f, psm);
				i++;
			}
		}
		
		public void resetImage() {
			//setImageToMenuAndParents(null, _item);
			for (final PreconditionSubMenu psm: _prec) {
				psm.resetImage();
			}
		}

		public void update(TimedState ts) {
			final MMLDTransition tr = _trans;
			
			_transitionScheduledTime = Time.MAX_TIME;
			_canBeTriggeredNow = false;
			_enabled = false;
			for (final PreconditionSubMenu psm: _prec) {
				psm.update(ts);
			}
			
			_transStatusItem.setText((_enabled)?"Enabled":"Disabled");
			_triggerNowItem.setEnabled(_canBeTriggeredNow);
			_triggerNextItem.setEnabled(false); // Will be set to true outside
			
			// Changing the text to show when it will occur.  
			if (_transitionScheduledTime == Time.MAX_TIME) {
				_item.setText(tr.getName());
			} else {
				_item.setText(tr.getName() + " (" + 
						_transitionScheduledTime + ")");
//						new DecimalFormat("0.####").format(_transitionScheduledTime) + ")");
			}
		}
		
		public void updateEnabled() {
			for (final PreconditionSubMenu psm: _prec) {
				psm.updateEnabled();
			}
		}
		
		public PreconditionSubMenu getPreconditionSubMenu(YAMLDFormula f) {
			return _formToPrec.get(f);
		}
		
		public void setAsNextTransition() {
			_triggerNextItem.setEnabled(true);
		}
	}
	
	// The class that takes care of a transition precondition 
	class PreconditionSubMenu {
		public final YAMLDFormula _f;
		public final YAMLDComponent _comp;
		public final MMLDTransition _trans;
		public final ForcedTransitionSubMenu _ftsm;
		public final MenuItem _statusItem;
		public final MenuItem _scheduledItem;
		public final MenuItem _changeScheduleItem;
		public final MenuItem _setFirstItem;
		public Time _scheduledTime; // When it is scheduled to trigger
		public Time _minForcedTime; // The earliest time it may trigger
		public Time _maxForcedTime; // The latest time it may trigger
		public boolean _scheduled;
		
		public PreconditionSubMenu(YAMLDFormula f, Menu transMenu, ForcedTransitionSubMenu ftsm, MMLDTransition trans, int pos) {
			_f = f;
			_comp = trans.getComponent();
			_trans = trans;
			_ftsm = ftsm;
			
			final MenuItem formMenuItem = new MenuItem(transMenu, SWT.CASCADE);
			formMenuItem.setText("Formula " + pos);
			
			final Menu formMenu = new Menu(_shell,SWT.DROP_DOWN);
			formMenuItem.setMenu(formMenu);
			
			{ // The formula
				final MenuItem item = new MenuItem(formMenu, SWT.CASCADE);
				item.setText(f.toFormattedString());
				item.setEnabled(false);
			}
			
			{ // The time interval
				final MenuItem item = new MenuItem(formMenu, SWT.CASCADE);
				final PeriodInterval ti = trans.getConditionTime(f);
				item.setText("Triggers after " + ti.toString());
				item.setEnabled(false);
			}
			
			{ // The status (Disabled, [tmin,tmax])
				_statusItem = new MenuItem(formMenu, SWT.CASCADE);
				_statusItem.setText("Disabled");
				_statusItem.setEnabled(false);
			}
			
			{ // Scheduled
				_scheduledItem = new MenuItem(formMenu, SWT.CASCADE);
				_scheduledItem.setText("Scheduled for /NaN/");
				_scheduledItem.setEnabled(false);
			}
			
			{ // Manually change schedules
				_changeScheduleItem = new MenuItem(formMenu, SWT.CASCADE);
				_changeScheduleItem.setText("Change scheduled time");
				_changeScheduleItem.setEnabled(false);
				_changeScheduleItem.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						selectNewSchedule(PreconditionSubMenu.this);
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			}
			
			{ // Automatically change schedules to put this formula first
				_setFirstItem = new MenuItem(formMenu, SWT.CASCADE);
				_setFirstItem.setText("Set first");
				_setFirstItem.setEnabled(false);
				_setFirstItem.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
                        _ii.setFirst(_trans, _f);
                        ComponentManager.this.update();
//						if (!setFirst(_trans, _f)) {
//							fail(_shell, "Could not make this precondition first");
//						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			}
		}
		
		public void resetImage() {
			setImageToMenuAndParents(null, _scheduledItem);
		}
		
		public void update(TimedState ts) {
			final State state = ts.getState();
			_scheduled = _f.satisfied(state, _comp);
			if (!_scheduled) {
				_statusItem.setText("Disabled");
				_scheduledItem.setText("Scheduled for /NaN/");
				_changeScheduleItem.setEnabled(false);
				_minForcedTime = Time.MAX_TIME;
				_maxForcedTime = Time.MAX_TIME;
				_scheduledTime = Time.MAX_TIME;
				return;
			}

			_changeScheduleItem.setEnabled(true);
			_ftsm._enabled = true;
			final Time scheduledFor = _ii.willTrigger(_f, _trans);
			_scheduledItem.setText("Scheduled for " + scheduledFor);
//					new DecimalFormat("0.####").format(scheduledFor));
			
			final PeriodInterval ti = _trans.getConditionTime(_f);
			final Period satisfiedFor = ts.satisfiedFor(_trans.getComponent(), _f);
            _minForcedTime = Time.max(
                    _currentTime, 
                    new Time(_currentTime, ti.getBeginning()).removePeriod(satisfiedFor));
            //_minForcedTime = Math.max(_currentTime, _currentTime - satisfiedFor + ti.getBeginning());
            _maxForcedTime = new Time(_currentTime, ti.getBeginning()).removePeriod(satisfiedFor);
			//_maxForcedTime = _currentTime - satisfiedFor + ti.getEnd();
			_maxNextTime = Time.min(_maxNextTime, _maxForcedTime);
			_statusItem.setText("Should trigger: [" + _minForcedTime + "," + _maxForcedTime + "]");
			if (ti.contains(satisfiedFor)) {
				_ftsm._canBeTriggeredNow = true;
			}
			if (_f.satisfied(ts.getState(), _comp)) {
				_scheduledTime = Time.min(_ftsm._transitionScheduledTime, scheduledFor);
				if (_scheduledTime.isBefore(_nextScheduledTime)) {
					_nextScheduledTime = _scheduledTime;
					_nextScheduledTransition = _trans;
					_nextScheduledFormula = _f;
				}
				_ftsm._transitionScheduledTime = _scheduledTime;
				setImageToMenuAndParents(_forced, _scheduledItem);
			}
		}
		
		public void updateEnabled() {
			_setFirstItem.setEnabled(_scheduled && (_minForcedTime.isBefore(_maxNextTime)));
		}
		
		public void setAsNext() {
			setImageToMenuAndParents(_nextImage, _scheduledItem);
			_ftsm.setAsNextTransition();
			_setFirstItem.setEnabled(false);
		}
		
		private void selectNewSchedule(final PreconditionSubMenu psm) {
		    final Shell shell = new Shell(_d);
		    final Layout l = new GridLayout(2, false);
		    shell.setLayout(l);
		    shell.setText("New schedule for precondition");
		    shell.setSize(300, 200);
		    shell.open();
		    
		    new Label(shell, SWT.PUSH).setText("Bounding values:");
		    new Label(shell, SWT.PUSH).setText(
		    		"[" + psm._minForcedTime + "-" 
		    		+ psm._maxForcedTime + "]");
//		    		"[" + new DecimalFormat("0.####").format(psm._minForcedTime) + "-" 
//		    		+ new DecimalFormat("0.####").format(psm._maxForcedTime) + "]");
		    new Label(shell, SWT.PUSH).setText("New schedule:");
		    
		    final Text text = new Text(shell, SWT.BORDER | SWT.PUSH);
		    text.setText("" + psm._scheduledTime);
		    //text.setText(new DecimalFormat("0.####").format(psm._scheduledTime));
		    text.setEditable(true);
		    text.setFocus();
		    
		    Button btnOk = new Button(shell, SWT.PUSH);
		    btnOk.setText("OK");
		    btnOk.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					try {
						final Time newValue = new Time(Double.parseDouble(text.getText()));
						if (
                                (!newValue.isBefore(psm._minForcedTime)) && 
                                (newValue.isBefore(psm._maxForcedTime))) {
							_ii.setSchedule(_trans, _f, newValue);
							shell.close();
							shell.dispose();
							ComponentManager.this.update();
						} else {
							fail(shell, "Value out of bounds.");
						}
					} catch (NumberFormatException e) {
						fail(shell, "Double expected.");
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
		    
		    Button btnCc = new Button(shell, SWT.PUSH);
		    btnCc.setText("Cancel");
		    btnCc.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					shell.close();
					shell.dispose();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
		    
		    shell.pack();
		}
	}
}
