package application;

import static application.config.ApplicationConfig.APP_FULLSCREEN;
import static application.config.ApplicationConfig.APP_HEIGHT;
import static application.config.ApplicationConfig.APP_WIDTH;

import java.io.IOException;

import application.gui.controller.LoaderController;
import application.pdf.PDFSynchronizerThread;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Application extends javafx.application.Application {

	private String[] arguments;
	private FXMLLoader loader;
	private VBox loaderRoot;
	private HBox mainRoot;
	private PDFSynchronizerThread modification;
	private Thread modificationThread;
	private Stage primaryStage;

	public LoaderController getLoaderController() {
		Object controller = loader.getController();
		if (controller instanceof LoaderController) {
			return loader.getController();
		}
		return null;
	}

	public Stage getPrimeryStage() {
		return primaryStage;
	}

	private void loadLoaderWindow() {
		try {
			loader = new FXMLLoader();
			loaderRoot = loader.load(getClass().getResource("gui/models/LoaderWindow.fxml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(loaderRoot, APP_WIDTH, APP_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
		
		startGuiModificationThread();
	}


	public void loadMainWindow() {
		try {
			loader = new FXMLLoader();
			mainRoot = loader.load(getClass().getResource("gui/models/MainWindow.fxml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(mainRoot, APP_WIDTH, APP_HEIGHT);
		primaryStage.close();
		primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreen(APP_FULLSCREEN);
		primaryStage.maximizedProperty().addListener(new ChangeListener<Boolean>() {

		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
		    	primaryStage.setFullScreen(t1);
		    }
		});
		primaryStage.show();
	}

	public void setArguments(String... arguments) {
		this.arguments = arguments;
	}
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		loadLoaderWindow();
	}

	public void startApplication() {
		launch(arguments);
	}

	public void startGuiModificationThread() {
		modification = new PDFSynchronizerThread(this);
		modificationThread = new Thread(modification);
		modificationThread.setDaemon(true);
		modificationThread.start();
	}
}
