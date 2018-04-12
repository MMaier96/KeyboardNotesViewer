package application.pdf;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

import application.Application;
import application.config.ApplicationConfig;
import application.gui.controller.LoaderController;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class PDFToIMGConverter{

	
	private LoaderController controller;
	private List<Image> images;
	private File inputFile;
	private boolean instanceSuccess = true;
	private String name;
	private File outputDirectory;
	private PDFDocument pdfDocument;
	private SimpleRenderer renderer;

	public PDFToIMGConverter(String path, Application application) {
		inputFile = new File(path);
		controller = application.getLoaderController();
		if(checkFileExists()) return;
		outputDirectory = new File(ApplicationConfig.OUTPUT_FOLDER + "/" + name + "/");
		renderer = new SimpleRenderer();
		pdfDocument = new PDFDocument();
		renderer.setResolution(ApplicationConfig.PDF_RESOLUTION);
	}

	private boolean checkFileExists() {
		if (inputFile.getName().contains(".")) {
			name = inputFile.getName().split("\\.")[0];
		}else {
			printToConsole("the file [" + inputFile.getName() + "] has no or a wrong file ending! ONLY PDF SUPPORTED!" );
			instanceSuccess = false;
			return true;
		}
		return false;
	}

	private boolean loadPDFFile() {
		try {
			pdfDocument.load(inputFile);
		} catch (IOException e) {
			printToConsole(e.getMessage());
			return false;
		}
		return true;
	}

	private void printToConsole(String msg) {
		Platform.runLater(new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				controller.printConsole("\t" + msg);
				return null;
			}
			
		});
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean renderPDFFile() {
		try {
			images = renderer.render(pdfDocument);
		} catch (IOException | RendererException | DocumentException e) {
			printToConsole(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean saveRenderedImages() {
		int prefixCounter = 0;
		File outputFile;
		for (Image image : images) {
			try {
				outputFile = new File(ApplicationConfig.OUTPUT_FOLDER + "/"+ name + "/" + (prefixCounter++) + ".png");
				ImageIO.write((RenderedImage) image, "png", outputFile);
			} catch (IOException e) {
				printToConsole(e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	public void startConvertion() {	
		if (!instanceSuccess) {
			printToConsole("because of wrong file ending of [" + inputFile.getName() + "] the convertion was skipped!" );
			return;
		}

		printToConsole("checking weather output directory already exists ...");
		if (!outputDirectory.mkdirs()) {
			printToConsole("the output directory [" + outputDirectory +"] already exist. Skipped converting to images");
			return;
		}

		printToConsole("loading pdf file ["+ inputFile.getName() +"] ...");
		if (!loadPDFFile()) {
			printToConsole("error occurred on loading the pdf file ["+ inputFile.getName() +"] ...");
			return;
		}
		
		printToConsole("start rendering pdf file ["+ inputFile.getName() +"] ...");
		if (!renderPDFFile()) {
			printToConsole("error occurred on rendering pdf file ["+ inputFile.getName() +"] ...");
			return;
		}
		
		printToConsole("start saving images of pdf file ["+ inputFile.getName() +"] to [" + outputDirectory + "] ...");
		if (!saveRenderedImages()) {
			printToConsole("error occurred on saving images of pdf file ["+ inputFile.getName() +"] to [" + outputDirectory + "] ...");
			return;
		}
		printToConsole("finished saving images of pdf file ["+ inputFile.getName() +"] to [" + outputDirectory + "] ...");
	}
}
