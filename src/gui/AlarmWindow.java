package gui;

import lang.YAMLDEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import util.AlarmLog;
import util.AlarmLog.AlarmEntry;
import util.Time;

/**
 * The window that shows a bunch of alarms.  
 * */
public class AlarmWindow {

	public AlarmWindow(String name, AlarmLog log) {
		
		display(name, log);
	}
	
	private void display(String name, AlarmLog log) {
		Shell shell = new Shell();
		
		shell.setText("Alarms for scenario '" + name + "'");
		
		final GridLayout gl = new GridLayout(2, true);
		shell.setLayout(gl);

		final GridData data = new GridData(GridData.FILL_BOTH);
		
		{
			Label lbl = new Label(shell, SWT.BORDER);
			lbl.setText("Time");
			lbl.setLayoutData(data);
		}
		{
			Label lbl = new Label(shell, SWT.BORDER);
			lbl.setText("Alarm");
			lbl.setLayoutData(data);
		}
		
		
		for (final AlarmEntry entry: log) {
			final Time time = entry._time;
			for (final YAMLDEvent event: entry._events) {
				{
					Label lbl = new Label(shell, SWT.BORDER);
					lbl.setText("" + time);
					lbl.setLayoutData(data);
				}
				{
					Label lbl = new Label(shell, SWT.BORDER);
					lbl.setText(event.toFormattedString());
					lbl.setLayoutData(data);
				}
			}
		}
		
		shell.pack();
		shell.open();
	}
	
}
