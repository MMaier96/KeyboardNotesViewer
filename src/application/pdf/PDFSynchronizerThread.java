package application.pdf;

import application.Application;

public class PDFSynchronizerThread implements Runnable {

	private Application application;

	public PDFSynchronizerThread(Application application) {
		this.application = application;
	}

	@Override
	public void run() {
		PDFFilesSynchronizer syncher = new PDFFilesSynchronizer(application);
		syncher.startSynchronising();
	}
}
