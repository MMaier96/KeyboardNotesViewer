package application.pdf;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import application.config.ApplicationConfig;

public class PDFFilesSynchronizer {

	private static Logger logger = Logger.getLogger(PDFFilesSynchronizer.class);

	
	private List<File> files;
	private boolean instanceSuccess = true;
	
	public PDFFilesSynchronizer() {
		File directory = new File(ApplicationConfig.INPUT_FOLDER);

		if (!directory.exists()) {
			instanceSuccess = false;
			logger.error("the input folder [" + ApplicationConfig.INPUT_FOLDER + "] does not exist!");
			logger.error("the input folder [" + ApplicationConfig.INPUT_FOLDER + "] does not exist!");
			return;
		}
		
		files = Arrays.asList(directory.listFiles());		
	}
	
	public void startSynchronising() {
		if (!instanceSuccess) {
			logger.error("the input folder [" + ApplicationConfig.INPUT_FOLDER + "] does not exist! Skipping convertion");
			return;
		}
		for (File file : files) {
			PDFToIMGConverter converter = new PDFToIMGConverter(file.getPath());
			new Thread(converter).run();
		}
	}
}
