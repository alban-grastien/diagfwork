package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.supercom.util.Options;
import gui.MainWindow;

public class YAMLDSim 
{
	public static Options OPTIONS;
	private static Shell shell;

	public static void main(String[] args)  
    {
		final Display displ = new Display();

		OPTIONS = new Options(args);
		
		shell = new Shell(displ, SWT.RESIZE);
		shell.setLayout(new FillLayout());

		int shell_width = displ.getClientArea().width - 50;
		int shell_height = displ.getClientArea().height - 50;

		final MainWindow mw = new MainWindow();
		mw.setSize(shell_width, shell_height);
		mw.setText("AI for the Smart Grid Simulator");
		mw.display();
			
		while (!shell.isDisposed())
			if (!displ.readAndDispatch())
				displ.sleep();
		displ.dispose();
    }
	
	public static Shell shell()
	{
		return shell;
	}
}
