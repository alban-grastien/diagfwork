package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lang.MMLDModelLoader;
import lang.MMLDlightParser;
import lang.Network;
import lang.State;
import lang.YAMLDEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.AlarmLog;
import util.ImmutableAlarmLog;
import diag.Diag;
import diag.Util;
import edu.supercom.util.Options;

public class DiagnosisWindow {
	private final Shell _shell;
	private String _netAd, _obsAd, _faultAd;
	private Text _netText, _alarmText, _faultText;
	private Button _runButt;
	private Label _diagStatus;
	private Group _diagGroup;
    
    public DiagnosisWindow() {
		_shell = new Shell(SWT.RESIZE | SWT.BORDER);
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		_shell.setLayout(layout);
    }

	public DiagnosisWindow(String netAd, String obsAd, String faultAd) {
		this();
        _netAd = netAd;
        _obsAd = obsAd;
        _faultAd = faultAd;
	}
	public void display() {
		_shell.setText("Model-Based Diagnoser");
		
		{ // The parameters
			Composite comp = new Composite(_shell, 0);
			comp.setLayout(new GridLayout(3, true));

			{
				Label lbl = new Label(comp, 0);
				lbl.setText("Model: ");
				_netText = new Text(comp, SWT.SINGLE | SWT.BORDER);
				_netText.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						modif();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
				Button butt = new Button(comp, SWT.BORDER);
				butt.setText("Choose file");
				butt.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						chooseNetwork();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			}

			{
				Label lbl = new Label(comp, 0);
				lbl.setText("Alarms: ");
				_alarmText = new Text(comp, SWT.SINGLE | SWT.BORDER);
				_alarmText.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						modif();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
				Button butt = new Button(comp, SWT.BORDER);
				butt.setText("Choose file");
				butt.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						chooseAlarm();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			}

			{
				Label lbl = new Label(comp, 0);
				lbl.setText("Faults: ");
				_faultText = new Text(comp, SWT.SINGLE | SWT.BORDER);
				_faultText.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						modif();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
				Button butt = new Button(comp, SWT.BORDER);
				butt.setText("Choose file");
				butt.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						chooseFault();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			}

		}
		
		{
			Composite comp = new Composite(_shell, SWT.BORDER);
			RowLayout layout = new RowLayout();
			layout.type = SWT.HORIZONTAL;
			comp.setLayout(layout);
			_runButt = new Button(comp, SWT.BORDER);
			_runButt.setText("Run the diagnoser");
			_runButt.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					run();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			_diagStatus = new Label(comp,SWT.NONE);
			_diagStatus.setText("Select alarms and faults");
		}

		{
			_diagGroup = new Group(_shell, SWT.NONE);
			_diagGroup.setText("Diagnosis");
			RowLayout layout = new RowLayout();
			layout.type = SWT.VERTICAL;
			_diagGroup.setLayout(layout);

			new Label(_diagGroup, SWT.NONE).setText("No diagnosis");
			new Label(_diagGroup, SWT.NONE).setText("");
			new Label(_diagGroup, SWT.NONE).setText("");
			new Label(_diagGroup, SWT.NONE).setText("");
			new Label(_diagGroup, SWT.NONE).setText("");
			new Label(_diagGroup, SWT.NONE).setText("");
		}
		
		modif();
		
		_shell.pack();
		_shell.open();
		
        if (_netAd != null)  {
            setNetwork(_netAd);
        }
        if (_obsAd != null) {
            setAlarm(_obsAd);
        }
        if (_faultAd != null) {
            setFault(_faultAd);
        }
	}

	public void chooseNetwork() {
		FileDialog fd = new FileDialog(_shell, SWT.OPEN);

		fd.setText("Read network model");
		// TODO: What is a sensible default here?
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.mmld", "*.*" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		setNetwork(selected);
	}

	public void chooseAlarm() {
		FileDialog fd = new FileDialog(_shell, SWT.OPEN);

		fd.setText("Read alarm log");
		// TODO: What is a sensible default here?
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.al", "*.*" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		setAlarm(selected);
	}

	public void chooseFault() {
		FileDialog fd = new FileDialog(_shell, SWT.OPEN);

		fd.setText("Read fault file");
		// TODO: What is a sensible default here?
		fd.setFilterPath("C:/");
		String[] filterExt = { "*.flt", "*.*" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		setFault(selected);
	}

	public void setNetwork(String network) {
		if (network == null) {
			return;
		}
		_netText.setText(network);
		_diagStatus.setText("");
		modif();
	}

	public void setAlarm(String alarm) {
		if (alarm == null) {
			return;
		}
		_alarmText.setText(alarm);
		_diagStatus.setText("");
		modif();
	}

	public void setFault(String fault) {
		if (fault == null) {
			return;
		}
		_faultText.setText(fault);
		_diagStatus.setText("");
		modif();
	}
	
	public void modif() {
		final List<String> missing = new ArrayList<String>();
		
		if (_netText.getText().equals("")) {
			missing.add("model");
		}
		
		if (_alarmText.getText().equals("")) {
			missing.add("alarms");
		}
		
		if (_faultText.getText().equals("")) {
			missing.add("faults");
		}
		
		if (missing.isEmpty()) {
			_diagStatus.setText("");
			_runButt.setEnabled(true);
			return;
		}
		
		StringBuilder mess = new StringBuilder();
		mess.append("Select ");
		mess.append(missing.get(0));
		if (missing.size() > 1) {
			if (missing.size() == 2) {
				mess.append(" and ").append(missing.get(1));
			} else {
				for (int i=1 ; i<missing.size()-1 ; i++) {
					mess.append(", ").append(missing.get(i));
				}
				mess.append(", and ").append(missing.get(missing.size()-1));
			}
		}
		mess.append(".");
		
		_diagStatus.setText(mess.toString());
		_runButt.setEnabled(false);
	}
	
	public void run() {
		
		// TODO: Does not appear, why?
		_diagStatus.setText("Computing the diagnosis");
		_diagStatus.redraw();
		_shell.redraw();
		
		final AlarmLog log;
		final Collection<YAMLDEvent> faults;

		// Reads the logs
		// try

		Network net;
		State s;
		{
			try {
				final MMLDModelLoader loader = new MMLDModelLoader(_netText.getText());
				if (loader.load()) {
					net = MMLDlightParser.net;
					s = MMLDlightParser.st;
				} else {
					net = null;
					s = null;
				}
			} catch (Exception e) {
				net = null;
				s = null;
			}
		}
		if (net == null) {
			_diagStatus.setText("Incorrect model file.");
			Collection<YAMLDEvent> diagnosis = Collections.emptyList();
			setDiagnosis(diagnosis);
			return;
		}
		
		log = ImmutableAlarmLog.readLogFile(net, _alarmText.getText());
		if (log == null) {
			_diagStatus.setText("Incorrect log file.");
			Collection<YAMLDEvent> diagnosis = Collections.emptyList();
			setDiagnosis(diagnosis);
			return;
		}
		faults = Util.parseEvents(net, _faultText.getText());
		if (faults == null) {
			_diagStatus.setText("Incorrect fault file.");
			Collection<YAMLDEvent> diagnosis = Collections.emptyList();
			setDiagnosis(diagnosis);
			return;
		}

		final Options opt = YAMLDSim.OPTIONS;
		final Collection<YAMLDEvent> diagnosis = Diag.diagnose(net, log, faults, s, opt);

		setDiagnosis(diagnosis);
		
		
		_runButt.setEnabled(false);
		_diagStatus.setText("Diagnosis computed");
	}
	
	private void setDiagnosis(Collection<YAMLDEvent> diagnosis) {
		for (final Control ctl: _diagGroup.getChildren()) {
			ctl.dispose();
		}
		
		if (diagnosis.isEmpty()) {
			Label lbl = new Label(_diagGroup, SWT.NONE);
			lbl.setText("No faulty event");
		}
		for (final YAMLDEvent event : diagnosis) {
			Label lbl = new Label(_diagGroup, SWT.NONE);
			lbl.setText("In component " + event.getComponent().name() + ", event " + event.name());
		}
		_diagGroup.pack();
	}
}
