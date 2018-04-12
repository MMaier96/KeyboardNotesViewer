package application.gui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import application.config.ApplicationConfig;
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

public class MainWindowController {

	private ListView<String> list;
	private int pageNumber = -1;

	// @formatter:off
	@FXML HBox imageBox;
	@FXML ImageView imageView;
	@FXML HBox main;
	@FXML HBox menu;
	@FXML HBox pdfListBox;
	@FXML HBox toggle;
	// @formatter:on

	private void initGuiListeners() {
		pedalsListener();
	}

	@FXML
	public void initialize() {
		initImageView();
		list = initListView();
		list.setItems(loadDirectories());
		pdfListBox.getChildren().add(list);
		initGuiListeners();
	}

	private void initImageView() {
		imageView.fitWidthProperty().bind(imageBox.widthProperty());
		imageView.fitHeightProperty().bind(imageBox.heightProperty());
		imageView.setPreserveRatio(true);
	}

	private ListView<String> initListView() {
		return new ListView<String>() {{
				setPrefWidth(300);
				setOnMouseClicked(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						Image image = null;
						String selectedItem = getSelectionModel().getSelectedItem();
						if (selectedItem == null) {
							return;
						}
						try {
							image = new Image(new FileInputStream("output/" + selectedItem + "/0.png"));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						imageView.setImage(image);
						pageNumber = 0;
					}
				});
			}};
	}

	private ObservableList<String> loadDirectories() {
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
		return items;
	}

	private void pedalsListener() {
		main.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (pageNumber == -1) {
					return;
				}
				int pages = new File("output/" + list.getSelectionModel().getSelectedItem()).listFiles().length;
				if (event.getCode().equals(KeyCode.LEFT)) {
					if (pageNumber == 0) {
						return;
					}
					try {
						imageView.setImage(new Image(new FileInputStream(
								"output/" + list.getSelectionModel().getSelectedItem() + "/" + --pageNumber + ".png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else if (event.getCode().equals(KeyCode.RIGHT)) {
					if (pageNumber == pages - 1) {
						return;
					}
					try {
						imageView.setImage(new Image(new FileInputStream(
								"output/" + list.getSelectionModel().getSelectedItem() + "/" + ++pageNumber + ".png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@FXML
	public void toggleMenu() {
		System.out.println("toggled: " + pdfListBox.getPrefWidth());
		if (pdfListBox.getPrefWidth() == 0) {
			pdfListBox.setMinWidth(285);
			pdfListBox.setPrefWidth(285);
			pdfListBox.setMaxWidth(285);
			menu.setMinWidth(300);
			menu.setPrefWidth(300);
			menu.setMaxWidth(300);
		} else {
			pdfListBox.setMinWidth(0);
			pdfListBox.setPrefWidth(0);
			pdfListBox.setMaxWidth(0);
			menu.setMinWidth(15);
			menu.setPrefWidth(15);
			menu.setMaxWidth(15);
		}
	}
}
