package gui;

import edu.supercom.util.Option;
import edu.supercom.util.Options;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import lang.Network;
import lang.State;
import lang.TimedState;
import lang.YAMLDEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import sim.MMLDSim;
import sim.Simulator;
import util.TimedScenarii;
import util.TimedScenario;

/**
 * The <code>ScenarioMenu</code>, i.e., the scenario menu, 
 * is the component responsible for dealing with the menu options in the GUI.  
 */
public class ScenarioMenu implements ScenarioSelecterListener, StateListener {
    /**
     * The scenario manager.
     */
    private final ScenarioSelecter _ss;
    private final Options _opt;

	private final Menu _menu;
	private MenuItem //_load, 
		_open, _close, _copy, _rename, _save, _showAlarms, _cut, 
			_saveAlarms, _filtering, 
            _first, _previous, _next, _last, _selectedItem;
    private final List<MenuItem> _scenarioOperationMenuItems;
	private final Shell _shell;
    
    // The menu items that display the names of the scenarios.
    private final Map<String,MenuItem> _scenarioNameItems;

    // The directory last used for the specified type of request (save the alarms log, etc.)
    private final Map<String, String> _directoryOfType;
    
    /**
     * The list of menus.  
     */
    //private final Map<String,Simulator> _sims;
    
    private String _currentKey;
    
    /** 
     * Creates the scenario menu.  
     * 
     * @param sh the shell this scenario menu is defined on.  
     * @param ss the scenario selecter.  
     * @param menu the menu where the menu item are to be created.  
     */
    public ScenarioMenu(Shell sh, ScenarioSelecter ss, Menu menu, Options opt) {
        _shell = sh;
        _opt = opt;
        _ss = ss;
        _ss.addScenarioSelecterListener(this);
        _ss.addStateListener(this);
        _menu = menu;
//        _sims = new HashMap<String, Simulator>();
        _scenarioNameItems = new HashMap<String, MenuItem>();
        _scenarioOperationMenuItems = new ArrayList<MenuItem>();
        _directoryOfType = new HashMap<String, String>();
        init();
    }
    
    private void init() {
		{
			_open = new MenuItem(_menu, SWT.PUSH);
			_open.setText("Open Scenario");
			_open.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    readScenario();
				}
			});
			_open.setEnabled(false);
            _scenarioOperationMenuItems.add(_open);
		}

		{
			_save = new MenuItem(_menu, SWT.PUSH);
			_save.setText("&Save Scenario");
			_save.setAccelerator(SWT.CTRL + 'S');
			_save.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    saveScenario(getCurrentScenario());
				}
			});
			_save.setEnabled(false);
            _scenarioOperationMenuItems.add(_save);
		}

		{
			_close = new MenuItem(_menu, SWT.PUSH);
			_close.setText("&Close Scenario");
			_close.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    _ss.removeSimulator(_ss.getCurrentKey());
				}
			});
			_close.setEnabled(false); // Originally only one scenario.
            _scenarioOperationMenuItems.add(_close);
		}

		{
			_copy = new MenuItem(_menu, SWT.PUSH);
			_copy.setText("Co&py");
			_copy.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    _ss.copyCurrent();
				}
			});
			_copy.setEnabled(false);
            _scenarioOperationMenuItems.add(_copy);
		}

		{
			_rename = new MenuItem(_menu, SWT.PUSH);
			_rename.setText("&Rename");
			_rename.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    final String oldName = _ss.getCurrentKey();
					final InputDialog id = new InputDialog(_shell);
					id.setMessage("Enter scenario name");
                    id.setInput(oldName);
					id.open();
                    final String newName = id.getInput();
					_ss.changeSimulatorName(oldName, newName);
				}
			});
			_rename.setEnabled(false);
            _scenarioOperationMenuItems.add(_rename);
		}

		new MenuItem(_menu, SWT.SEPARATOR);
		
		{
			_cut = new MenuItem(_menu, SWT.PUSH);
			_cut.setText("Cut scenario");
			//_cut.setAccelerator(SWT.CTRL + 'C');
			_cut.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					_ss.cut();
				}
			});
			_cut.setEnabled(false);
            _scenarioOperationMenuItems.add(_cut);
		}
		
		new MenuItem(_menu, SWT.SEPARATOR);

		{
			_showAlarms = new MenuItem(_menu, SWT.PUSH);
			_showAlarms.setText("Sho&w alarms");
			_showAlarms.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
                    
					final Network net = _ss.getNetwork();
					final Collection<YAMLDEvent> obs = net.observableEvents();
					new AlarmWindow(_ss.getCurrentKey(),
							getCurrentScenario().alarmLog(obs));
				}
			});
			_showAlarms.setEnabled(false);
            _scenarioOperationMenuItems.add(_showAlarms);
		}

		{
			_saveAlarms = new MenuItem(_menu, SWT.PUSH);
			_saveAlarms.setText("Sa&ve alarms");
			_saveAlarms.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					saveAlarms(getCurrentScenario());
				}
			});
			_saveAlarms.setEnabled(false);
            _scenarioOperationMenuItems.add(_saveAlarms);
		}

		{
			_filtering = new MenuItem(_menu, SWT.PUSH);
			_filtering.setText("&Filtering");
			_filtering.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					scenarioFiltering(getCurrentScenario());
				}
			});
			_filtering.setEnabled(false);
            _scenarioOperationMenuItems.add(_filtering);
		}
    }
    
    private TimedScenario getCurrentScenario() {
        return _ss.getCurrentSimulator().getScenario();
    }

    // windowMessage: the message that appears on the window
    // extension: the extension file
    // stringToSave: a callable that can generate the string that should be printed.
    // directoryOptions: a list of options to decide the default directory 
    private void saveSomething(String windowMessage, 
            String extension, 
            Callable<String> stringToSave, 
            Option... directoryOptions) {
		FileDialog fd = new FileDialog(_shell, SWT.SAVE);

		fd.setText(windowMessage);
		
        final String defaultDirectory;
        {
            String tmpDirectory = _directoryOfType.get(windowMessage);
            for (final Option o: directoryOptions) {
                if (tmpDirectory != null) {
                    break;
                }
                o.getOption(_opt, null, false, null);
            }
            if (tmpDirectory == null) {
                defaultDirectory = new File(".").getAbsolutePath();
            } else {
                defaultDirectory = tmpDirectory;
            }
        }
        
        fd.setFilterPath(defaultDirectory);
		String[] filterExt = {extension, "*.*" };
		fd.setFilterExtensions(filterExt);

		String selected = fd.open();
		try {
			if (selected == null) {
				return;
			}

            final File selectedFile = new File(selected);
            
            final File selectedDir = selectedFile.getParentFile();
            _directoryOfType.put(windowMessage, selectedDir.getAbsolutePath());
            
			final PrintStream out = new PrintStream(new FileOutputStream(selectedFile));
			final Network net = _ss.getNetwork();
			final Collection<YAMLDEvent> obs = net.observableEvents();
			out.println(stringToSave.call());
			out.close();
		} catch (IOException e) {
			MessageBox messageBox = new MessageBox(_shell, SWT.ICON_ERROR
					| SWT.CANCEL);
			messageBox.setText("IOException thrown");
			messageBox.setMessage(e.toString());
			messageBox.open();
		} catch (Exception e) {
            error("Internal error.");
        }
    }
    
	private void saveScenario(final TimedScenario sce) {
        saveSomething("Save the scenario", "*.sce", new Callable<String>() {

            @Override
            public String call() throws Exception {
                return sce.toFormattedString();
            }
        });
	}
    
    private void readScenario() {
        final Network net = _ss.getNetwork();
        
		final FileDialog fd = new FileDialog(_shell, SWT.OPEN);
		final String[] filterExt = {"*.sce", "*.*" };
        fd.setFilterExtensions(filterExt);
		fd.setText("Open");
		final String selected = fd.open();
        
        if (selected != null) {
            try {
                final TimedScenario ts = TimedScenarii.readScenario(net, selected);
                if (ts != null) {
                    if (_ss.addSimulator(ts, selected)) {
                        _ss.selectSimulator(selected);
                    }
                }
            } catch(Exception e) {
    			MessageBox messageBox = new MessageBox(_shell, SWT.ICON_ERROR
					| SWT.CANCEL);
                messageBox.setText("Exception thrown");
                messageBox.setMessage(e.toString());
                messageBox.open();
                
                e.printStackTrace();
            }
        }
    }
	
	public void saveAlarms(final TimedScenario sce) {
        saveSomething("Save alarms", "*.al", new Callable<String>() {

            @Override
            public String call() throws Exception {
                final Network net = _ss.getNetwork();
                final Collection<YAMLDEvent> obs = net.observableEvents();
                return sce.alarmLog(obs).toFormattedString();
            }
        });
	}

	/**
	 * Method invoked when clicking on Filtering menu item. Creates and displays
	 * a {@link ScenarioFilteringWindow} object.
	 * 
	 * @param scenario
	 */
	protected void scenarioFiltering(TimedScenario scenario) {
		error("\"Scenario Filtering\" functionality not supported yet.");
	}

    @Override
    public void simulatorSelected(String selectedKey, Simulator selectedSim) {
        if (_currentKey != null) {
            final MenuItem item = _scenarioNameItems.get(_currentKey);
            if (item != null) {
                item.setSelection(false);
            }
        }
        
        _currentKey = selectedKey;
        final MenuItem item = _scenarioNameItems.get(selectedKey);
        if (_selectedItem != null) {
            _selectedItem.setSelection(false);
        }
        _selectedItem = item;
        item.setSelection(true);
        
        setEnabled();
    }

    @Override
    public void simulatorAdded(String addedKey, Simulator addedSim) {
        
		if (_scenarioNameItems.isEmpty()) { // TODO: replace with a Boolean flag.
			new MenuItem(_menu, SWT.SEPARATOR);

			{
				_first = new MenuItem(_menu, SWT.PUSH);
				_first.setText("First state");
				_first.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
                        first();
					}
				});
			}

			{
				_previous = new MenuItem(_menu, SWT.PUSH);
				_previous.setText("Previous state");
				_previous.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
                        previous();
					}
				});
			}

			{
				_next = new MenuItem(_menu, SWT.PUSH);
				_next.setText("Next state");
				_next.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
                        next();
					}
				});
			}

			{
				_last = new MenuItem(_menu, SWT.PUSH);
				_last.setText("Last state");
				_last.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
                        last();
					}
				});
			}

			new MenuItem(_menu, SWT.SEPARATOR);
		}
        
        {
            final MenuItem item  = new MenuItem(_menu, SWT.RADIO);
            item.setText(addedKey);
            _scenarioNameItems.put(addedKey, item);
            item.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
                        _ss.selectSimulator(item.getText());
					}
            });
        }
        
        setEnabled();
    }
    
    private void setEnabled() {
        final boolean enabled = (_ss.getCurrentKey() != null);
        for (final MenuItem item: _scenarioOperationMenuItems) {
            item.setEnabled(enabled);
        }
    }

    @Override
    public void simulatorRemoved(String removedKey, Simulator removedSim) {
        final MenuItem item = _scenarioNameItems.get(removedKey);
        item.dispose();
        if (_selectedItem == item) {
            _selectedItem = null;
        }
        setEnabled();
    }

    @Override
    public void simulatorKeyChanged(String oldKey, String newKey, Simulator sim) {
        final MenuItem item = _scenarioNameItems.get(oldKey);
        item.setText(newKey);
        _scenarioNameItems.put(newKey, item);
        _scenarioNameItems.remove(oldKey);
        _scenarioNameItems.put(newKey, item);
    }
    
    // Makes sure that the menus that should be enabled are enabled.
    private void checkMenusEnabled() {
        _last.setEnabled(!_ss.isInFinalState());
        _next.setEnabled(!_ss.isInFinalState());
        _first.setEnabled(_ss.getCurrentTransitionPosition() != 0);
        _previous.setEnabled(_ss.getCurrentTransitionPosition() != 0);
    }
    
    private void next() {
        _ss.nextEvent();
    }
    
    private void previous() {
        _ss.previous();
    }
    
    private void first() {
        _ss.first();
    }
    
    private void last() {
        _ss.last();
    }

    @Override
    public void newStateHandler(State s) {
        checkMenusEnabled();
    }

    @Override
    public void newStateHandler(TimedState s) {
        checkMenusEnabled();
    }
    
    private void error(String str) {
        final MessageBox mb = new MessageBox(_shell, SWT.ICON_ERROR);
        mb.setMessage(str);
        mb.open();
    }
}
