// project created on 11.11.2006 at 00:52
using System;
using Gtk;

namespace Overload
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			Application.Init ();
			MainWindow win = new MainWindow ();
			win.Show ();
			Application.Run ();
		}
	}
}