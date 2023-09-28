package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class DiagnosisManager 
{
	private final Shell _shell;
	
	public DiagnosisManager(Shell shell, Menu diagMenu) 
	{
		_shell = shell;
		init(diagMenu);
	}
	
	private void init(Menu diagMenu) 
	{
    	final MenuItem menu = new MenuItem(diagMenu, SWT.PUSH);
    	menu.setText("Diagnose");
		menu.setAccelerator(SWT.CTRL + 'D');
    	menu.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				diagnose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

    	/**
    	 * Leave this out until the diagnosis will produce a scenario.
    	 * Meanwhile, there is a Filtering menu item under Scenarios.
    	 */
    	/*
		final MenuItem filteringItem = new MenuItem(diagMenu, SWT.PUSH);
		filteringItem.setText("&Filtering");
		filteringItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// will need a scenario object provided by the diagnosis process
				scenarioFiltering(null);
			}
		});
		filteringItem.setEnabled(false);
    	*/
	}
	
	public void diagnose() 
	{
		DiagnosisWindow dw = new DiagnosisWindow();
		dw.display();

	}
}
