package application.config;

public enum ApplicationConfig {
	instance;

	public static final boolean APP_FULLSCREEN = true;
	public static final int APP_HEIGHT = 260;
	public static final String APP_TITLE = "Keyboard Notes Viewer";
	public static final int APP_WIDTH = 600;
//	public static final String INPUT_FOLDER = "D:\\input";
	//public static final String OUTPUT_FOLDER = "D:\\output";
	public static final String INPUT_FOLDER = "/home/pi/input";
	public static final String OUTPUT_FOLDER = "/home/pi/output";
	public static final int PDF_RESOLUTION = 80;
}
