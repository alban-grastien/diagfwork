package gui;

public class SimpleWindow 
{
	protected ChildShell child = null;

	public SimpleWindow()
	{
		child = new ChildShell(YAMLDSim.shell());
	}
	
	public void display()
	{
		child.open();
	}
	
	public void setSize(int w, int h)
	{
		child.shell().setSize(w, h);
	}
	
	public void setText(String t)
	{
		child.shell().setText(t);
	}
}
