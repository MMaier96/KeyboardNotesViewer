package application.gui.windows;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;

import application.config.AppConfig;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JLabel image;
	private JPanel imagePanel;
	private JLabel lblClickToHideshow;
	protected JPanel listPanel;
	private JLayeredPane layeredPane;
	private JLabel closeButton;
	int pageIndex = 0;
	private JList<String> list;

	public MainWindow() {
		
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setSize(1920, 1080);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		layeredPane = new JLayeredPane();
		layeredPane.setIgnoreRepaint(true);
		layeredPane.setBounds(0, 0, 1920, 1080);
		getContentPane().add(layeredPane);

		listPanel = new JPanel();
		listPanel.setBounds(0, 0, 390, 1080);
		listPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				toggleListPanel();
			}
		});
		layeredPane.setLayout(null);

		closeButton = new JLabel();
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
		});
		closeButton.setBounds(1894, 0, 26, 26);
		closeButton.setIcon(new ImageIcon(getClass().getResource("images/close.png")));
		closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		repaint();
		layeredPane.add(closeButton, 1);
		listPanel.setBackground(new Color(24, 24, 24));
		layeredPane.add(listPanel, 2);
		listPanel.setLayout(null);

		list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String selectedFileName = list.getModel().getElementAt(list.getSelectedIndex());
				System.out.println(
						AppConfig.OUTPUT_DIRECTORY.replace("//", "\\\\") + "\\" + selectedFileName + "\\0.png");
				image.setIcon(new ImageIcon(
						AppConfig.OUTPUT_DIRECTORY.replace("//", "\\\\") + "\\" + selectedFileName + "\\0.png"));

				pageIndex = 0;
				getListPaneToFront();

			}
		});
		File outputFolder = new File(AppConfig.OUTPUT_DIRECTORY);
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;

			String[] values = outputFolder.list(new FilenameFilter() {
				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			});

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(0, 0, 365, 1080);
		listPanel.add(list);

		lblClickToHideshow = new JLabel();
		lblClickToHideshow.setBounds(366, 506, 22, 203);
		lblClickToHideshow.setIcon(new ImageIcon(getClass().getResource("images/toggle.png")));
		lblClickToHideshow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				toggleListPanel();
			}
		});
		listPanel.add(lblClickToHideshow);

		imagePanel = new JPanel();
		imagePanel.setBounds(30, 0, 1890, 1080);
		layeredPane.add(imagePanel, 3);
		imagePanel.setLayout(null);

		image = new JLabel("");
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setIcon(new ImageIcon("C:\\output\\Incident31581 - Kopie (3)\\0.png"));
		image.setBounds(30, 0, 1890, 1080);
		imagePanel.add(image);
		setVisible(true);
		addKeyListener(this);
		listPanel.addKeyListener(this);
		image.addKeyListener(this);
		layeredPane.addKeyListener(this);
		list.addKeyListener(this);
	}

	protected void toggleListPanel() {
		if (listPanel.getLocation().getX() < 0) {
			listPanel.setLocation(0, 0);
		} else {
			listPanel.setLocation(-365, 0);
		}
	}

	private void getListPaneToFront() {
		toggleListPanel();
		toggleListPanel();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String selectedFileName = list.getModel().getElementAt(list.getSelectedIndex());
		int maxPages = new File(AppConfig.OUTPUT_DIRECTORY.replace("//", "\\\\") + "\\" + selectedFileName).listFiles().length-1;
		if (e.getKeyCode() == 37 && pageIndex > 0) { // left pedal
			image.setIcon(new ImageIcon(AppConfig.OUTPUT_DIRECTORY.replace("//", "\\\\") + "\\" + selectedFileName + "\\" + --pageIndex +".png"));
		}else if(e.getKeyCode() == 39 && pageIndex < maxPages) { //right pedal
			image.setIcon(new ImageIcon(AppConfig.OUTPUT_DIRECTORY.replace("//", "\\\\") + "\\" + selectedFileName + "\\" + ++pageIndex + ".png"));
		}
		getListPaneToFront();
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
