package application.pdf;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import application.config.AppConfig;
import application.gui.windows.LoaderWindow;

public class PDFFilesSynchronizer {

	private LoaderWindow loaderWindow;
	private List<File> files;
	private File inputDirectory;
	private boolean instanceSuccess = true;
	private File outputDirectory;

	public PDFFilesSynchronizer(LoaderWindow loaderWindow) {
		this.loaderWindow = loaderWindow;
		loadDirectoriesFromSettings();
		if (checkDirectories())
			return;
		loadFilesOfDirectories();
	}

	private boolean checkDirectories() {
		printToGuiConsole("Checking if input/output directories exists ...");
		if (!inputDirectory.exists()) {
			instanceSuccess = false;
			printToGuiConsole("the input directory '" + AppConfig.INPUT_DIRECTORY + "' does not exist!");
			return true;
		} else {
			printToGuiConsole("the input directory '" + AppConfig.INPUT_DIRECTORY + "' was found!");
		}

		if (!outputDirectory.exists()) {
			instanceSuccess = false;
			printToGuiConsole("the output directory '" + AppConfig.OUTPUT_DIRECTORY + "' does not exist!");
			return true;
		} else {
			printToGuiConsole("the output directory '" + AppConfig.OUTPUT_DIRECTORY + "' was found!");
		}
		return false;
	}

	private void loadDirectoriesFromSettings() {
		inputDirectory = new File(AppConfig.INPUT_DIRECTORY);
		outputDirectory = new File(AppConfig.OUTPUT_DIRECTORY);
	}

	private void loadFilesOfDirectories() {
		printToGuiConsole("start reading all files ...");
		files = Arrays.asList(inputDirectory.listFiles());
		printToGuiConsole("finished reading all files!");
	}

	private void printToGuiConsole(String msg) {
				loaderWindow.logConsole(msg);
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

		for (int i = 0; i < files.size(); i++) {
			PDFToIMGConverter converter = new PDFToIMGConverter(files.get(i).getPath(), loaderWindow);
			printToGuiConsole("start converting of " + files.get(i).getName() + " ...");
			converter.startConvertion();
			loaderWindow.logConsole("finished convertion of " + files.get(i).getName() + "!");
		}
	}

}