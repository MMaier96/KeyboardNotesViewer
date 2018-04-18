package application.pdf;

import application.gui.windows.LoaderWindow;

public class PDFSynchronizerThread implements Runnable {

	private LoaderWindow loaderWindow;

	public PDFSynchronizerThread(LoaderWindow loaderWindow) {
		this.loaderWindow = loaderWindow;
	}

	@Override
	public void run() {
		PDFFilesSynchronizer syncher = new PDFFilesSynchronizer(loaderWindow);
		syncher.startSynchronising();
		loaderWindow.finish();
	}
}