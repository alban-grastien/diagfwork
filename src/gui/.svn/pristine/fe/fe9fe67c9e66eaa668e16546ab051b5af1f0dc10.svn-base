package gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import lang.State;
import lang.TimedState;
import lang.YAMLDEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.Scenario;
import util.TimedScenario;

import alarmprocessor.AlarmClusteringAbstract;
import alarmprocessor.AlarmClusteringYAMLD;
import alarmprocessor.AlarmProcessingManager;
import alarmprocessor.AlarmProcessingSnapshot;
import alarmprocessor.AlarmProcessor;
import alarmprocessor.ExtractCatastrophicEventsYAMLD;
import alarmprocessor.ExtractIncidentIndependentAlarms;
import alarmprocessor.ExtractTransientAlarms;

/**
 * @author Adi Botea
 * Implements a window to be used to invoke scenario filtering techniques.
 */
public class ScenarioFilteringWindow implements StateListener {
	
	private Shell shell;
	private Button runButton, closeButton;
	private Text rawAlarmText, clusterText, faultIndepText,
		faultText, transientText;
	private AlarmProcessingManager apm;
	
	public ScenarioFilteringWindow(Scenario sce) {
	    //MainWindow.getSingletonObject().addStateListener(this);
		shell = null;
		apm = new AlarmProcessingManager();
		init();
	}
	
	private void init() {
		shell = new Shell(SWT.RESIZE | SWT.BORDER | SWT.TITLE);
		shell.setLayout(new GridLayout(4, true));
		shell.setText("Alarm processing");
		RowLayout vlayout = new RowLayout();
		vlayout.type = SWT.VERTICAL;
		GridData groupGD1 = new GridData();
        groupGD1.widthHint = 430;
        groupGD1.heightHint = 330;
        RowData textRD1 = new RowData();
        textRD1.width = 400;
        textRD1.height = 300;
        RowData textRD2 = new RowData();
        textRD2.width = 400;
        textRD2.height = 120;

        initRawAlarmGroup(groupGD1, textRD1, vlayout);
        initClusterGroup(groupGD1, textRD1, vlayout);
        initFaultIndepGroup(groupGD1, textRD1, vlayout);
        initFaultAndTransientGroups(textRD2, vlayout);
        //this.initRunCloseButtons(shell);

		shell.pack();
	}
	
	public void display() {
		if (shell.isDisposed())
			init();
		shell.open();
	}
	
	private void initRawAlarmGroup(GridData gd, RowData rd, RowLayout layout) {
		Group rawAlarmGroup = new Group(shell, SWT.NONE);
		rawAlarmGroup.setText("Raw alarms");
		rawAlarmGroup.setLayout(layout);
        rawAlarmGroup.setLayoutData(gd);
		rawAlarmText = new Text(rawAlarmGroup,				
				SWT.TITLE | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		rawAlarmText.setText("raw alarms");
        rawAlarmText.setLayoutData(rd);
	}

	private void initClusterGroup(GridData gd, RowData rd, RowLayout layout) {
		Group clusterGroup = new Group(shell, SWT.NONE);
		clusterGroup.setText("Clustered alarms");
		clusterGroup.setLayout(layout);
        clusterGroup.setLayoutData(gd);
		clusterText = new Text(clusterGroup,				
				SWT.TITLE | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		clusterText.setText("clustered alarms");
        clusterText.setLayoutData(rd);
	}

	private void initFaultIndepGroup(GridData gd, RowData rd, RowLayout layout) {
		Group faultIndepGroup = new Group(shell, SWT.NONE);
		faultIndepGroup.setText("Incident-independent alarms");
		faultIndepGroup.setLayout(layout);
        faultIndepGroup.setLayoutData(gd);
		faultIndepText = new Text(faultIndepGroup,				
				SWT.TITLE | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		faultIndepText.setText("fault indep group");
        faultIndepText.setLayoutData(rd);
	}

	private void initFaultAndTransientGroups(RowData rd, RowLayout layout) {
		RowData rd1 = new RowData();
		rd1.height = 155;
		rd1.width = 430;
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayout(layout);
		// add faults group
		Group faultGroup = new Group(comp, SWT.NONE);
		faultGroup.setText("Fault events");
		faultGroup.setLayout(layout);
        faultGroup.setLayoutData(rd1);
		faultText = new Text(faultGroup,				
				SWT.TITLE | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		faultText.setText("faults");
        faultText.setLayoutData(rd);
        // now transient alarm group
		Group transientGroup = new Group(comp, SWT.NONE);
		transientGroup.setText("Transient alarms");
		transientGroup.setLayout(layout);
        transientGroup.setLayoutData(rd1);
		transientText = new Text(transientGroup,				
				SWT.TITLE | SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL);
		transientText.setText("transient alarms");
        transientText.setLayoutData(rd);
	}

	/*
	private void initRunCloseButtons(Composite comp) {
		RowLayout hlayout = new RowLayout();
		hlayout.type = SWT.HORIZONTAL;
		Composite buttonsComp = new Composite(comp, SWT.NONE);
		buttonsComp.setLayout(hlayout);
		runButton = new Button(buttonsComp, SWT.BORDER);
		runButton.setText("Do filtering");
		runButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				performFiltering();
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		closeButton = new Button(buttonsComp, SWT.BORDER);
		closeButton.setText("Close");
		closeButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});		
	}
	*/

	/*
	private void performFiltering() {
		AlarmProcessor ap = new AlarmClusteringYAMLD();
		AlarmClusteringAbstract a = new AlarmClusteringYAMLD();
		ap = new ExtractCatastrophicEventsYAMLD();
		ap = new ExtractIncidentIndependentAlarms(a);
		ap = new ExtractTransientAlarms(a);			
//		ap.process(scenario, extra_info);
		// for now use dummy faults object, until 
		// we move it to the Diagnosis menu, where faults will be available
		Collection<YAMLDEvent> faults = new Vector<YAMLDEvent>();
		Map<String, Object> extra_info = new HashMap<String, Object>();
		extra_info.put("faults", faults);
		ap.process(scenario, extra_info);
		String summary = ap.getProcessingResults().toString();
		rawAlarmText.setText(summary);
	}
	*/

	@Override
	public void newStateHandler(State s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newStateHandler(TimedState s) {
        // TODO: Removed
        /*
		ScenarioManager sm = MainWindow.getSingletonObject().getScenarioManager();
		TimedScenario sce = ((ScenarioManager) sm).getCurrentScenario();
		//sce.alarmLog(obs);
		// TODO Run the following in a new threshold if it turns out to be too slow
		AlarmProcessingSnapshot snapshot = apm.getSnapshot(sce);
		this.rawAlarmText.setText(snapshot.getRawAlarms());
		this.clusterText.setText(snapshot.getAlarmClusters());
		this.faultIndepText.setText(snapshot.getIncidentIndepAlarms());
		this.faultText.setText(snapshot.getFaultEvents());
		this.transientText.setText(snapshot.getTransientAlarms());
		// TIPS
		//  Use scenario.alarmLog for a list of raw alarms.*/
	}
	
}
