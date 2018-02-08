package application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

	private String[] arguments;
	private Stage primaryStage;
	private AppConfig appConfig;

	@Override
	public void start(Stage primaryStage) {
		loadConfiguration();
		setPrimaryStage(primaryStage);
		loadMainWindow();
	}

	private void loadConfiguration() {
		appConfig = AppConfig.instance;
	}

	private void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setArguments(String... arguments) {
		this.arguments = arguments;
	}

	private void loadMainWindow() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, appConfig.windowWidth, appConfig.windowWidth);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(appConfig.startAsFullScreen);
		primaryStage.show();
	}

	public void startApplication() {
		launch(arguments);
	}

}
