package application.algorithm;

import application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class Algorithm implements Runnable{

	
	private Application application;

	public Algorithm(Application application) {
		this.application = application;
	}
	
	@Override
	public void run() {
		startAlgorithm();
	}

	private void startAlgorithm() {

		// Algorithm without updating gui

		for (int i = 0; i < 500000; i++) {
			System.out.println("algorithm running!");
		}
		
		
		Platform.runLater(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				//define here what should happen in GUI
//				application.getController().setTestButtonText("called from algorithm");;
				return null;
			}
			
		});
		
		// Algorithm without updating gui continues
		
	}

}
