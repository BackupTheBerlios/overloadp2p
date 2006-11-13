using System;
using Gtk;

public class MainWindow: Gtk.Window
{
	protected Gtk.TextView txtLog;
	
	public MainWindow (): base ("")
	{
		Stetic.Gui.Build (this, typeof(MainWindow));
	}
	
	protected void OnDeleteEvent (object sender, DeleteEventArgs a)
	{
		Application.Quit ();
		a.RetVal = true;
	}

	protected virtual void OnBtnLogActivated(object sender, System.EventArgs e)
	{
		
	}

	protected virtual void OnBtnNetworkActivated(object sender, System.EventArgs e)
	{
	}
}