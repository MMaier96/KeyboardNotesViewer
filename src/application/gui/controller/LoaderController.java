package application.gui.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class LoaderController {

	// @formatter:off
	@FXML TextArea console;
	@FXML ProgressBar progressBar;
	@FXML Label progressText;
	// @formatter:on

	@FXML
	public void initialize() {
	}

	public void printConsole(String message) {
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		console.appendText("[" + timeStamp + "]   " + message + "\n");
	}

	public void setProgess(double d) {
		progressBar.setProgress(d);
		progressText.setText(Math.round(d*100) + " %");
	}

	public void showFinishDialog(Application application) {
		application.getPrimeryStage().setAlwaysOnTop(false);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Completed!");
		alert.setContentText("Finished converting all pdf files!\n\nClick 'OK' to start the Application.");
		alert.showAndWait();
		
		application.loadMainWindow();
	}
	
	@FXML
	public void closeLoader() {
		System.exit(0);
	}
}
