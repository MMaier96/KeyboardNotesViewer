package application;

import org.apache.log4j.BasicConfigurator;

public class Main {

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		Application app = new Application();
		app.setArguments(args);
		app.startApplication();
	}

}
