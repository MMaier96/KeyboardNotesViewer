package application.gui.windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import application.config.AppConfig;
import application.gui.WindowManager;
import application.pdf.PDFSynchronizerThread;


public class LoaderWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel continueButton;
	private JTextArea console;
	private JScrollPane consoleScrollPane;
	private JLabel header;
	private JLabel lblProcess;
	private JLabel processStatus;
	
	public LoaderWindow() {
		getContentPane().setBackground(new Color(24,24,24));
		setTitle(AppConfig.APP_NAME);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setSize(800, 450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		loadComponents();
		setVisible(true);

		startSynchronizing();
		
	}

	private void startSynchronizing() {

		PDFSynchronizerThread syncher = new PDFSynchronizerThread(this);
		syncher.run();
	}

	private void loadComponents() {
		header = new JLabel("");
		header.setBounds(192, 11, 384, 51);
		header.setIcon(new ImageIcon(getClass().getResource("images/title.png")));
		getContentPane().add(header);
		
		consoleScrollPane = new JScrollPane();
		consoleScrollPane.setAutoscrolls(true);
		consoleScrollPane.setBounds(50, 80, 700, 230);
		getContentPane().add(consoleScrollPane);
		
		console = new JTextArea();
		consoleScrollPane.setViewportView(console);
		console.setLineWrap(true);
		console.setWrapStyleWord(true);
		console.setEditable(false);
		
		continueButton = new JLabel("");
		continueButton.setBounds(286, 340, 200, 43);
		continueButton.setIcon(new ImageIcon(getClass().getResource("images/continue_inactive.png")));
		getContentPane().add(continueButton);
		
		lblProcess = new JLabel("");
		lblProcess.setIcon(new ImageIcon(getClass().getResource("images/process.png")));
		lblProcess.setBounds(60, 313, 60, 20);
		getContentPane().add(lblProcess);
		
		processStatus = new JLabel("");
		processStatus.setIcon(new ImageIcon(getClass().getResource("images/running.png")));
		processStatus.setBounds(124, 313, 60, 20);
		getContentPane().add(processStatus);
	}
	
	public void logConsole(String msg) {
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		console.append("   " + timeStamp + ":   " + msg + "\n");
		console.setCaretPosition(console.getDocument().getLength());
	}

	/*
	 * this method gets called when the loader has finished!
	 */
	public void finish() {
		processStatus.setIcon(new ImageIcon(getClass().getResource("images/finished.png")));
		continueButton.setIcon(new ImageIcon(getClass().getResource("images/continue_active.png")));
		continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		continueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				WindowManager.showMainWindow();
				dispose();
			}
		});
	}
}

