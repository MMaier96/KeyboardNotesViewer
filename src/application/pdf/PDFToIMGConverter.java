package application.pdf;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import application.config.AppConfig;
import application.gui.windows.LoaderWindow;

public class PDFToIMGConverter {

	private LoaderWindow loaderWindow;
	private List<Image> images;
	private File inputFile;
	private boolean instanceSuccess = true;
	private String name;
	private File outputDirectory;
	private PDDocument pdfDocument;
	private PDFRenderer renderer;

	public PDFToIMGConverter(String path, LoaderWindow loaderWindow) {
		inputFile = new File(path);
		images = new ArrayList<Image>();
		this.loaderWindow = loaderWindow;
		if (checkFileExists())
			return;
		outputDirectory = new File(AppConfig.OUTPUT_DIRECTORY + "/" + name + "/");
		pdfDocument = new PDDocument();
	}

	private boolean checkFileExists() {
		if (inputFile.getName().contains(".")) {
			name = inputFile.getName().split("\\.")[0];
		} else {
			printToConsole("the file [" + inputFile.getName() + "] has no or a wrong file ending! ONLY PDF SUPPORTED!");
			instanceSuccess = false;
			return true;
		}
		return false;
	}

	private boolean loadPDFFile() {
		try {
			pdfDocument = PDDocument.load(inputFile);
		} catch (IOException e) {
			printToConsole(e.getMessage());
			return false;
		}
		return true;
	}

	private void printToConsole(String msg) {
				loaderWindow.logConsole("\t" + msg);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean renderPDFFile() {
		renderer = new PDFRenderer(pdfDocument);
		for (int i = 0; i < pdfDocument.getNumberOfPages(); i++) {
			try {
				images.add(renderer.renderImage(i));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		try {
			pdfDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean saveRenderedImages() {
		int prefixCounter = 0;
		File outputFile;
		for (Image image : images) {
			try {
				outputFile = new File(AppConfig.OUTPUT_DIRECTORY + "/" + name + "/" + (prefixCounter++) + ".png");
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
			printToConsole("because of wrong file ending of [" + inputFile.getName() + "] the convertion was skipped!");
			return;
		}

		printToConsole("checking weather output directory already exists ...");
		if (!outputDirectory.mkdirs()) {
			printToConsole(
					"the output directory [" + outputDirectory + "] already exist. Skipped converting to images");
			return;
		}

		printToConsole("loading pdf file [" + inputFile.getName() + "] ...");
		if (!loadPDFFile()) {
			printToConsole("error occurred on loading the pdf file [" + inputFile.getName() + "] ...");
			return;
		}

		printToConsole("start rendering pdf file [" + inputFile.getName() + "] ...");
		if (!renderPDFFile()) {
			printToConsole("error occurred on rendering pdf file [" + inputFile.getName() + "] ...");
			return;
		}

		printToConsole(
				"start saving images of pdf file [" + inputFile.getName() + "] to [" + outputDirectory + "] ...");
		if (!saveRenderedImages()) {
			printToConsole("error occurred on saving images of pdf file [" + inputFile.getName() + "] to ["
					+ outputDirectory + "] ...");
			return;
		}
		
		
		printToConsole("finished saving images of pdf file [" + inputFile.getName() + "] to [" + outputDirectory + "] ...");
		
	}
}