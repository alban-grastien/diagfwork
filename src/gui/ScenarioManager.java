package gui;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lang.MMLDTransition;
import lang.Network;
import lang.TimedState;
import lang.YAMLDEvent;
import lang.YAMLDFormula;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import edu.supercom.util.PseudoRandom;
import edu.supercom.util.SeededPseudoRandom;

import lang.Period;
import lang.YAMLDComponent;
import org.eclipse.swt.events.SelectionAdapter;
import sim.MMLDSim;
import sim.Simulator;
import sim.Simulators;
import util.MMLDGlobalTransition;
import util.Scenario;
import util.Time;
import util.TimedScenario;

/**
 * The scenario manager is the component in charge of displaying a scenario and
 * adding/removing/switching scenarios.
 * */
@Deprecated
public class ScenarioManager {

	private final Shell _shell;

	private final Menu _menu;

//	private static int _nbSce = 0;

	private ScenarioMenu _currentScenario;

	private List<ScenarioMenu> _scenarios = new ArrayList<ScenarioMenu>();

	private int _pos = 0;

	private MenuItem //_load, 
		_close, _copy, _rename, _save, _showAlarms, _cut, 
			_saveAlarms, _first, _last, _next, _previous, _filtering;

	private Scale _scale; // The scale that shows how far in the scenario we are
    
    private final PseudoRandom _rand;
    
    private final InteractiveInterface _ii;

	// (There should be a list of state listeners or something here)

	public ScenarioManager(Shell shell, Menu scenarioMenu, 
            PseudoRandom rand, InteractiveInterface ii) {
        _ii = ii;
        _rand = rand;
		_shell = shell;
		_menu = scenarioMenu;
		init();
	}

	private void init() {
//		{
//			_load = new MenuItem(_menu, SWT.PUSH);
//			_load.setText("&Load Scenario...\tCTRL+L");
//			_load.setAccelerator(SWT.CTRL + 'L');
//			_load.addSelectionListener(new SelectionListener() {
//				@Override
//				public void widgetSelected(SelectionEvent arg0) {
//					final Scenario sce = loadScenario();
//					if (sce != null) {
//						addScenario(sce, "Scenario " + _nbSce++);
//					}
//				}
//
//				@Override
//				public void widgetDefaultSelected(SelectionEvent arg0) {
//				}
//			});
//			_load.setEnabled(false);
//		}

		{
			_save = new MenuItem(_menu, SWT.PUSH);
			_save.setText("&Save Scenario");
			_save.setAccelerator(SWT.CTRL + 'S');
			_save.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					saveScenario(_currentScenario._msim.getScenario());
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			_save.setEnabled(false);
		}

		{
			_close = new MenuItem(_menu, SWT.PUSH);
			_close.setText("&Close Scenario");
			_close.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					removeCurrentScenario();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			_close.setEnabled(false); // Originally only one scenario.
		}

		{
			_copy = new MenuItem(_menu, SWT.PUSH);
			_copy.setText("Co&py (disabled for the moment)");
			_copy.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    // TODO: Disabled at the moment.
					//copy(_currentScenario);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			_copy.setEnabled(false);
		}

		{
			_rename = new MenuItem(_menu, SWT.PUSH);
			_rename.setText("&Rename");
			_rename.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					final InputDialog id = new InputDialog(_shell);
					id.setMessage("Enter scenario name");
					id.setInput(_currentScenario.getName());
					id.open();
					_currentScenario.rename(id.getInput());
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			_rename.setEnabled(false);
		}

		new MenuItem(_menu, SWT.SEPARATOR);
		
		{
			_cut = new MenuItem(_menu, SWT.PUSH);
			_cut.setText("Cut scenario");
			//_cut.setAccelerator(SWT.CTRL + 'C');
			_cut.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					cutScenario();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			_cut.setEnabled(false);
		}
		
		new MenuItem(_menu, SWT.SEPARATOR);

		{
			_showAlarms = new MenuItem(_menu, SWT.PUSH);
			_showAlarms.setText("Sho&w alarms");
			_showAlarms.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    final Network net = _ii.getNetwork();
					final Collection<YAMLDEvent> obs = net.observableEvents();
					new AlarmWindow(_currentScenario._name,
							_currentScenario._msim.getScenario().alarmLog(obs));
				}
			});
			_showAlarms.setEnabled(false);
		}

		{
			_saveAlarms = new MenuItem(_menu, SWT.PUSH);
			_saveAlarms.setText("Sa&ve alarms");
			_saveAlarms.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					saveAlarms(_currentScenario._msim.getScenario());
				}
			});
			_saveAlarms.setEnabled(false);
		}

		{
			_filtering = new MenuItem(_menu, SWT.PUSH);
			_filtering.setText("&Filtering");
			_filtering.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					scenarioFiltering(_currentScenario._msim.getScenario());
				}
			});
			_filtering.setEnabled(false);
		}
	}

	/**
	 * Method invoked when clicking on Filtering menu item. Creates and displays
	 * a {@link ScenarioFilteringWindow} object.
	 * 
	 * @param scenario
	 */
	protected void scenarioFiltering(Scenario scenario) {
		ScenarioFilteringWindow sfw = new ScenarioFilteringWindow(scenario);
		sfw.display();
	}

	/**
	 * Method invoked when clicking on Filtering menu item. Creates and displays
	 * a {@link ScenarioFilteringWindow} object.
	 * 
	 * @param scenario
	 */
	protected void scenarioFiltering(TimedScenario scenario) {
		System.err
				.println("ScenarioManager >> scenarioFiltering: Not implemented yet.");
	}

//	private void copy(ScenarioMenu menu) {
//
//		final ScenarioMenu sm = new ScenarioMenu(menu);
//		sm.setAsCurrentScenario();
//		_scenarios.add(sm);
//		_close.setEnabled(true);
//		
////		_load.setEnabled(true);
////		_copy.setEnabled(true);
//		_rename.setEnabled(true);
//		_save.setEnabled(true);
//		_saveAlarms.setEnabled(true);
//		_showAlarms.setEnabled(true);
//		_filtering.setEnabled(true);
//		_cut.setEnabled(false);
//	}

	public void createTimedScenario(TimedState state, String name) {
		if (_scenarios.isEmpty()) {
			new MenuItem(_menu, SWT.SEPARATOR);

			{
				_first = new MenuItem(_menu, SWT.PUSH);
				_first.setText("First state");
				_first.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						goToFirstState();
					}
				});
			}

			{
				_previous = new MenuItem(_menu, SWT.PUSH);
				_previous.setText("Previous state");
				_previous.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						goToPreviousState();
					}
				});
			}

			{
				_next = new MenuItem(_menu, SWT.PUSH);
				_next.setText("Next state");
				_next.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						goToNextState();
					}
				});
			}

			{
				_last = new MenuItem(_menu, SWT.PUSH);
				_last.setText("Last state");
				_last.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						goToLastState();
					}
				});
			}

			new MenuItem(_menu, SWT.SEPARATOR);
		}

		final ScenarioMenu sm = new ScenarioMenu(state, name);
		sm.setAsCurrentScenario();
		_scenarios.add(sm);
		if (_scenarios.size() > 1) {
			_close.setEnabled(true);
		}

//		_load.setEnabled(true);
		_copy.setEnabled(true);
		_rename.setEnabled(true);
		_save.setEnabled(true);
		_saveAlarms.setEnabled(true);
		_showAlarms.setEnabled(true);
		_filtering.setEnabled(true);
	}

	public void removeCurrentScenario() {
		if (_scenarios.size() == 1) {
			return;
		}

		_currentScenario.delete();
		if (_scenarios.remove(_currentScenario)) {
			_scenarios.get(_scenarios.size() - 1).setAsCurrentScenario();
			if (_scenarios.size() <= 1) {
				_close.setEnabled(false);
			}

		}
	}

    /**
     * Computes the maximum time when the next forced transition can take place.  
     * 
     * @return the maximum time that one can wait to 
     * before a forced transition have to trigger.  
     */
    public Time maxTimeNextForcedTransition() {
        return _currentScenario.maxTimeNextForcedTransition();
    }

    /**
     * Computes the minimum time when the next forced transition can take place.  
     * 
     * @return the minimum time that one can wait to 
     * before a forced transition may trigger.  
     */
    public Time minTimeNextForcedTransition() {
        return _currentScenario.minTimeNextForcedTransition();
    }
    
    public boolean setFirst(MMLDTransition tr, YAMLDFormula f) {
        return Simulators.setFirst(_currentScenario._msim, _rand, tr, f);
//        final Network net = _ii.getNetwork();
//                    
//		final double nextTime;
//		{
//            final double min = minTimeNextForcedTransition();
//            final double max = maxTimeNextForcedTransition();
//            
//			if (min > max) {
//				return false;
//			}
//			nextTime = min + ((max - min) * _rand.rand(1000) / 1000);
//		}
//        
//        final TimedState s = getFinalState();
//        for (final YAMLDComponent c2: net.getComponents()) {
//            for (final MMLDTransition tr2: c2.transitions()) {
//                for (final YAMLDFormula f2: tr2.getPreconditions()) {
////                    final double scheduled = _currentScenario._msim.willTrigger(f2, tr2);
////                    if (scheduled > nextTime) {
////                        continue;
////                    }
//                    
//                    Simulators.pushTriggeringTime(_currentScenario._msim, tr2, f2, nextTime);
//                }
//            }
//        }
//        
//        _currentScenario._msim.setTriggeringTime(tr, f, nextTime);
//        return true;
    }

	class ScenarioMenu {
		private final MenuItem _sceItem;
		// private YAMLDSimulator _sim;
		private String _name;
		private Simulator _msim;

		public ScenarioMenu(TimedState state, String name) {
			_msim = new MMLDSim(state, Time.ZERO_TIME, new SeededPseudoRandom(13));
			_sceItem = new MenuItem(_menu, SWT.CHECK);
			rename(name);
			_sceItem.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					setAsCurrentScenario();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
		}

//		/**
//		 * Creates a new scenario menu that is a copy of the specified menu.  
//		 * 
//		 * @param menu the scenario menu that is copied.
//		 * */
//		public ScenarioMenu(ScenarioMenu menu) {
//			_msim = new MMLDSim(menu._msim);
//			_sceItem = new MenuItem(_menu, SWT.CHECK);
//			rename(menu._name + " (copy)");
//			_sceItem.addSelectionListener(new SelectionListener() {
//				@Override
//				public void widgetSelected(SelectionEvent arg0) {
//					setAsCurrentScenario();
//				}
//
//				@Override
//				public void widgetDefaultSelected(SelectionEvent arg0) {
//				}
//			});
//		}

		public void setAsCurrentScenario() {
			if (_currentScenario != null) {
				if (!_currentScenario._sceItem.isDisposed()) {
					_currentScenario._sceItem.setSelection(false);
				}
			}
			_currentScenario = ScenarioMenu.this;
			_sceItem.setSelection(true);
			goToLastState();
		}

		public final void rename(String name) {
			_name = name;
			_sceItem.setText(_name);
		}

		public String getName() {
			return _name;
		}

		public void delete() {
			_sceItem.dispose();
		}

		// Starting at state 0
		private void goToState(int pos) {
			_pos = pos;
			final TimedState ts = _msim.getScenario().getStateBeforeTransition(
					pos);
			//MainWindow.getSingletonObject().setState(ts);
			testNextPrevious();
		}

		public void goToFirstState() {
			goToState(0);
		}
		
		public boolean isAtLastState() {
			return (_pos >= _msim.getScenario().nbTrans());
		}

		/**
		 * Goes to the next state in this scenario. Does nothing if there is no
		 * next state.
		 * 
		 * @return <code>true</code> if there was a next state,
		 *         <code>false</code> otherwise.
		 * */
		public boolean goToNextState() {
			if (isAtLastState()) {
				return false;
			}

			goToState(_pos + 1);
			return true;
		}

		public boolean goToPreviousState() {
			if (_pos <= 0) {
				return false;
			}

			goToState(_pos - 1);
			return true;
		}

		public void goToLastState() {
			goToState(_msim.getScenario().nbTrans());
		}

		private void testNextPrevious() {
			final int max = _msim.getScenario().nbTrans();
			_previous.setEnabled(_pos != 0);
			_first.setEnabled(_pos != 0);
			_next.setEnabled(_pos != max);
			_last.setEnabled(_pos != max);
			_cut.setEnabled(_pos != max);

			if (_scale != null) {
				_scale.setEnabled(false);
				_scale.setMaximum(max);
				_scale.setMinimum(0);
				_scale.setSelection(_pos);
			}
		}
        
        public Time maxTimeNextForcedTransition() {
            return Simulators.maxTimeNextForcedTransition(_msim);
        }
        public Time minTimeNextForcedTransition() {
            return Simulators.minTimeNextForcedTransition(_msim);
        }
	}

	public void saveScenario(TimedScenario sce) {
		System.err.println("Not implemented yet.");
	}

	public void saveAlarms(TimedScenario sce) {
		// TODO:
		// That piece of code outputs the following error in my terminal:
		// (SWT:18147): Gtk-CRITICAL **: gtk_file_chooser_set_current_folder:
		// assertion `filename != NULL' failed
		// I don't know why.
		// @author Alban
		FileDialog fd = new FileDialog(_shell, SWT.SAVE);

		fd.setText("Save alarms");
		fd.setFilterPath(new java.io.File(".").getAbsolutePath());
		String[] filterExt = { "*.al", "*.*" };
		fd.setFilterExtensions(filterExt);

		String selected = fd.open();
		try {
			if (selected == null) {
				return;
			}

			final PrintStream out = new PrintStream(new FileOutputStream(
					selected.toString()));
			final Network net = _ii.getNetwork();
			final Collection<YAMLDEvent> obs = net.observableEvents();
			out.println(sce.alarmLog(obs).toFormattedString());
			// for (final YAMLDEvent e: sce.observations(obs)) {
			// out.println(e.getComponent().name() + "." + e.name());
			// }
			out.close();
		} catch (IOException e) {
//			e.printStackTrace();
			MessageBox messageBox = new MessageBox(_shell, SWT.ICON_ERROR
					| SWT.CANCEL);
			messageBox.setText("IOException thrown");
			messageBox.setMessage(e.toString());
			messageBox.open();
		}
	}

	public void elapseTime(Period d) {
		Period time = Period.ZERO_PERIOD;
		do {
			final Period elapsing = _currentScenario._msim.elapseTime(d);
//			System.out.println("ScenarioManager >> Elapsing " + elapsing);
			time = Period.add(time,elapsing);
		} while (d.isLonger(time));
		goToLastState();
	}

	/**
	 * Returns <code>true</code> if the time elapsed and no forced transition
	 * was taken.
	 * */
	public boolean elapseOrOneForcedTransition(Period d) {
		boolean result = (_currentScenario._msim.elapseTime(d) == d);
		// final double time;
		// final boolean result;
		// if (d < _currentScenario._sim.queryMaxProgress()) {
		// time = d;
		// result = true;
		// } else {
		// time = _currentScenario._sim.queryMaxProgress();
		// result = false;
		// }
		// elapseTime(time);
		goToLastState();
		return result;
	}

	public void nextEvent() {
		//_currentScenario._msim.triggerNext();
		final MMLDTransition tr = 
                _currentScenario._msim.nextForcedTransition();
        if (tr == null) {
            return;
        }
        _ii.trigger(tr);
		goToNextState();
	}

	public void triggerGlobalTransition(MMLDGlobalTransition tr) {
		for (; !elapseOrOneForcedTransition(new Period(0.1));) {
		}
		_currentScenario._msim.trigger(tr);
		goToLastState();
	}

	// private void sendState() {
	// final TimedScenario sce = _currentScenario._msim.getScenario();
	// MainWindow.getSingletonObject().setState(sce.getLastState());
	// }

	public void goToFirstState() {
		_currentScenario.goToFirstState();
	}

	public boolean goToNextState() {
		return _currentScenario.goToNextState();
	}

	public void goToPreviousState() {
		_currentScenario.goToPreviousState();
	}

	public void goToLastState() {
		_currentScenario.goToLastState();
	}

	public void setScale(Scale scale) {
		_scale = scale;
	}
	
	/**
	 * Cuts the scenario at the current displayed position.    
	 * */
	public void cutScenario() {
		_currentScenario._msim.cut(_pos);
	}
	
	/**
	 * Returns the final time of the current scenario.  
	 * 
	 * @return the time at the end of the current scenario.  
	 * */
	public Time getFinalTime() {
		return _currentScenario._msim.getScenario().getFinalTime();
	}
	
	/**
	 * Returns the time of the current state of the scenario.  
	 * 
	 * @return the time in the state the scenario is currently showing.  
	 * */
	public Time getCurrentTime() {
		return _currentScenario._msim.getScenario().getTime(_pos);
	}
	
	/**
	 * Returns the final state of the current scenario.
	 *   
	 * @return the state at the end of the current scenario.
	 * */
	public TimedState getFinalState() {
		return _currentScenario._msim.getScenario().getLastState();
	}
	
	/**
	 * Indicates when the specified precondition is expected to trigger the specified transition.  
	 * 
	 * @param p a precondition of the transition.  
	 * @param t the transition.  
	 * @return the time when <code>p</code> is expected to force the triggering of <code>t</code>.  
	 * */
	public Time willTrigger(YAMLDFormula p, MMLDTransition t) {
		return _currentScenario._msim.willTrigger(p, t);
	}
	
	/**
	 * Sets the schedule time of the specified precondition 
	 * for the specified transition to the specified value.  
	 * */
	public void setSchedule(MMLDTransition trans, YAMLDFormula f, Time d) {
		_currentScenario._msim.setTriggeringTime(trans, f, d);
	}
	
	/**
	 * Returns the current scenario, i.e., the scenario shown on the window 
	 * from the initial state to the state currently shown.  
	 * */
	public TimedScenario getCurrentScenario() {
		return _currentScenario._msim.get(_pos);
	}
    
    /**
     * Returns the name of the current scenario.  
     * 
     * @return the name of the current scenario.  
     * @todo: Should be dealt with by ScenarioMenu
     */
    public String currentScenarioName() {
        return _currentScenario.getName();
    }

    /**
     * Returns the name of the current scenario.  
     * 
     * @param name the new name.  
     * @return the name of the current scenario.  
     * @todo: Should be dealt with by ScenarioMenu
     */
    public String renameScenario(String name) {
        return _currentScenario._name = name;
    }
}
