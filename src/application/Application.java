package application;

import static application.config.ApplicationConfig.APP_HEIGHT;
import static application.config.ApplicationConfig.APP_TITLE;
import static application.config.ApplicationConfig.APP_WIDTH;

import java.io.IOException;

import application.algorithm.Algorithm;
import application.config.ApplicationConfig;
import application.gui.controller.GuiController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Application extends javafx.application.Application {

	private String[] arguments;
	private Algorithm algorithm;
	private Thread algorithmThread;
	private FXMLLoader loader;
	private Stage primaryStage;
	private HBox root;

	public GuiController getController() {
		return loader.getController();
	}

	private void loadMainWindow() {
		primaryStage.setTitle(APP_TITLE);

		loader = new FXMLLoader();
		try {
			root = loader.load(getClass().getResource("gui/models/ApplicationModel.fxml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(ApplicationConfig.APP_FULLSCREEN);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		primaryStage.show();
//		startGeneticAlgorithm();
	}

	public void setArguments(String... arguments) {
		this.arguments = arguments;
	}

	private void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {
		setPrimaryStage(primaryStage);
		loadMainWindow();
	}

	public void startApplication() {
		launch(arguments);

	}

	public void startGeneticAlgorithm() {
		algorithm = new Algorithm(this);
		algorithmThread = new Thread(algorithm);
		algorithmThread.setDaemon(true);
		algorithmThread.start();
	}
}
