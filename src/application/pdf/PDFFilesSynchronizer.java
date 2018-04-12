package application.pdf;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import application.Application;
import application.config.ApplicationConfig;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class PDFFilesSynchronizer {

	private Application application;
	private List<File> files;
	private File inputDirectory;
	private boolean instanceSuccess = true;
	private File outputDirectory;

	public PDFFilesSynchronizer(Application application) {
		this.application = application;
		loadDirectoriesFromSettings();
		if (checkDirectories())
			return;
		loadFilesOfDirectories();
	}

	private boolean checkDirectories() {
		printToGuiConsole("Checking if input/output directories exists ...");
		if (!inputDirectory.exists()) {
			instanceSuccess = false;
			printToGuiConsole("the input directory '" + ApplicationConfig.INPUT_FOLDER + "' does not exist!");
			return true;
		} else {
			printToGuiConsole("the input directory '" + ApplicationConfig.INPUT_FOLDER + "' was found!");
		}

		if (!outputDirectory.exists()) {
			instanceSuccess = false;
			printToGuiConsole("the output directory '" + ApplicationConfig.OUTPUT_FOLDER + "' does not exist!");
			return true;
		} else {
			printToGuiConsole("the output directory '" + ApplicationConfig.OUTPUT_FOLDER + "' was found!");
		}
		return false;
	}

	private void loadDirectoriesFromSettings() {
		inputDirectory = new File(ApplicationConfig.INPUT_FOLDER);
		outputDirectory = new File(ApplicationConfig.OUTPUT_FOLDER);
	}

	private void loadFilesOfDirectories() {
		printToGuiConsole("start reading all files ...");
		files = Arrays.asList(inputDirectory.listFiles());
		printToGuiConsole("finished reading all files!");
	}

	private void printToGuiConsole(String msg) {
		Platform.runLater(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				application.getLoaderController().printConsole(msg);
				return null;
			}

		});
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void startSynchronising() {
		if (!instanceSuccess) {
			printToGuiConsole("Skipping convertions because of wrong input folder");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}

		int finishedCounter = 0;
		for (int i = 0; i < files.size(); i++) {
			PDFToIMGConverter converter = new PDFToIMGConverter(files.get(i).getPath(), application);
			printToGuiConsole("start converting of " + files.get(i).getName() + " ...");
			converter.startConvertion();
			application.getLoaderController().printConsole("finished convertion of " + files.get(i).getName() + "!");
			finishedCounter++;
			updateProgressOfGui(finishedCounter, files.size());
		}

		Platform.runLater(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				application.getLoaderController().showFinishDialog(application);
				return null;
			}
		});
	}

	private void updateProgressOfGui(int finishedCounter, int size) {
		Platform.runLater(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				application.getLoaderController().setProgess((double) finishedCounter / size);
				return null;
			}

		});

	}
}
