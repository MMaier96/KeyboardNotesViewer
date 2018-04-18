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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel image;
	private JPanel imagePanel;
	private JLabel lblClickToHideshow;
	protected JPanel listPanel;
	private JLayeredPane layeredPane;
	private JLabel closeButton;

	public MainWindow() {
		setSize(1920, 1080);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		layeredPane = new JLayeredPane();
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
		
		layeredPane.add(closeButton);
		listPanel.setBackground(new Color(24, 24, 24));
		layeredPane.add(listPanel);
		listPanel.setLayout(null);

		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "fsdf", "fsd", "fsd", "fsd", "f", "sdf", "sd", "f", "sd", "fs" };

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
		layeredPane.add(imagePanel);
		imagePanel.setLayout(null);

		image = new JLabel("");
		image.setHorizontalAlignment(SwingConstants.CENTER);
		image.setIcon(new ImageIcon("C:\\output\\10548168\\0.png"));
		image.setBounds(30, 0, 1890, 1080);
		imagePanel.add(image);
		setVisible(true);
	}

	protected void toggleListPanel() {
		if (listPanel.getLocation().getX()<0) {
			listPanel.setLocation(0, 0);
		}else {
			listPanel.setLocation(-365, 0);
		}
	}
}
