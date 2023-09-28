package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

public class ChildShell 
{
	Shell child = null;

	public ChildShell(Shell parent) 
	{
		child = new Shell(parent, SWT.RESIZE);
		child.setLayout(new FillLayout());

//	    child.setSize(200, 200);
	 }
	
	public void open()
	{
		child.open();
	}
	
	public Shell shell()
	{
		return child;
	}
}	
