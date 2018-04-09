package application.gui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import application.config.ApplicationConfig;
import application.pdf.PDFFilesSynchronizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class GuiController {

	private int pageNumber = -1;

	// @FXML Components here
	@FXML
	HBox main;
	@FXML
	HBox pdfListBox;
	@FXML
	HBox imageBox;
	@FXML
	ImageView imageView;

	@FXML
	public void initialize() {


		PDFFilesSynchronizer syncher = new PDFFilesSynchronizer();
		syncher.startSynchronising();

		imageView.fitWidthProperty().bind(imageBox.widthProperty());
		imageView.fitHeightProperty().bind(imageBox.heightProperty());
		imageView.setPreserveRatio(true);

		ListView<String> list = new ListView<String>();
		list.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Image image = null;
				try {
					image = new Image(new FileInputStream("output/" + list.getSelectionModel().getSelectedItem() + "/0.png"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				imageView.setImage(image);
				pageNumber=0;
			}
		});
		ObservableList<String> items = FXCollections.observableArrayList();
		File outputFolder = new File(ApplicationConfig.OUTPUT_FOLDER);
		String[] directories = outputFolder.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		for (String directory : directories) {
			items.add(directory);
		}

		list.setItems(items);
		pdfListBox.getChildren().add(list);
		
		main.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (pageNumber == -1) {
					return;
				}
				int pages = new File("output/" + list.getSelectionModel().getSelectedItem()).listFiles().length;
				if(event.getCode().equals(KeyCode.LEFT)){
					if (pageNumber == 0) {
						return;
					}
					try {
						imageView.setImage(new Image(new FileInputStream("output/" + list.getSelectionModel().getSelectedItem() + "/" + --pageNumber + ".png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}else if(event.getCode().equals(KeyCode.RIGHT)) {
					if (pageNumber == pages-1) {
						return;
					}
					try {
						imageView.setImage(new Image(new FileInputStream("output/" + list.getSelectionModel().getSelectedItem() + "/" + ++pageNumber + ".png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
