package application.gui;

import application.gui.windows.LoaderWindow;
import application.gui.windows.MainWindow;

public class WindowManager {

	public static void showMainWindow() {
		new MainWindow();	
	}

	public static void showLoader() {
		new LoaderWindow();	
	}
}
