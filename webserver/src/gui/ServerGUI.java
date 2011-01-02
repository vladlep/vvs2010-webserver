package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import server.WebServer;

public class ServerGUI {
	private static boolean RIGHT_TO_LEFT = false;
	private static JFrame frame;
	private JTextField rootDir;
	private JTextField mentenanceDir;
	private JLabel validMentDir;
	private JLabel validRootDir;
	private WebServer server = null;

	public void addComponentsToPane(Container contentPane) {

		contentPane.setLayout(new BorderLayout(5, 5));
		if (!(contentPane.getLayout() instanceof BorderLayout)) {
			contentPane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		if (RIGHT_TO_LEFT) {

			contentPane
					.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}

		JComponent labelsPanel = new JPanel();
		GridLayout labelsLayout = new GridLayout(3, 2);
		labelsLayout.setHgap(2);
		labelsLayout.setVgap(2);
		labelsPanel.setLayout(labelsLayout);

		contentPane.add(labelsPanel, BorderLayout.LINE_START);

		labelsPanel.add(new JLabel("Server status :"));
		final JLabel statusServerLable = new JLabel("Stopped");
		labelsPanel.add(statusServerLable);

		labelsPanel.add(new JLabel("Server address (*):"));
		final JFormattedTextField serverAddress = new JFormattedTextField(
				createFormatter("###.###.###.###"));
		labelsPanel.add(serverAddress);

		labelsPanel.add(new JLabel("Server port (*):"));
		final JFormattedTextField portServer = new JFormattedTextField(
				createFormatter("#####"));
		labelsPanel.add(portServer);

		final JButton actionButton = new JButton("Start Server");
		contentPane.add(actionButton, BorderLayout.CENTER);

		final JButton mentenaceButton = new JButton("Start Mentenance");
		contentPane.add(mentenaceButton, BorderLayout.LINE_END);

		JPanel pathPanel = new JPanel();

		contentPane.add(pathPanel, BorderLayout.PAGE_END);
		LayoutManager pathPanelLayout = new GridLayout(2, 4);
		pathPanel.setLayout(pathPanelLayout);

		pathPanel.add(new JLabel("Root dir (*) : "));
		rootDir = new JTextField("./server");

		rootDir.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				validateRoot();
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		pathPanel.add(rootDir);

		final JButton browseButton = new JButton("Browse");

		pathPanel.add(browseButton);

		validRootDir = new JLabel("Not set");
		pathPanel.add(validRootDir);

		final JFileChooser browseRoot = new JFileChooser();

		browseRoot.changeToParentDirectory();
		browseRoot.setMultiSelectionEnabled(false);
		browseRoot.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		browseButton.addActionListener(new BrowseListener(frame, browseRoot,
				"Root",this));

		pathPanel.add(new JLabel("Mentenance dir : "));
		mentenanceDir = new JTextField("./server");

		pathPanel.add(mentenanceDir);

		mentenanceDir.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				validateMent();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final JButton browseMentButton = new JButton("Browse");
		pathPanel.add(browseMentButton);

		validMentDir = new JLabel("Not set");
		pathPanel.add(validMentDir);

		browseMentButton.addActionListener(new BrowseListener(frame,
				browseRoot, "Mentenace",this));

		actionButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (actionButton.getText().equals("Start Server")) {

					if (serverAddress.getText().equals("   .   .   .   ")
							|| portServer.getText().trim() == "") {
						JOptionPane.showMessageDialog(frame,
								"Elements marked as (*) can not be empty");
						return;
					}
					if (!validRootDir.getText().equals("OK")) {
						JOptionPane.showMessageDialog(frame,
								"Not a valid path for the root directory!");
						return;
					}

					int port = Integer.valueOf(portServer.getText());

					server = new WebServer(serverAddress.getText() , port, rootDir.getText(),
							mentenanceDir.getText());
					try{
					server.start();
					 } catch (Exception e1) {
					 JOptionPane.showMessageDialog(frame,
					 "Can't start server. "+e1.getMessage());
					 return;
					 }

					serverAddress.setEnabled(false);
					portServer.setEnabled(false);
					rootDir.setEnabled(false);
					browseButton.setEnabled(false);
					actionButton.setText("Stop Server");
					statusServerLable.setText("Started");
				} else {
					// stop server action
					try{
					server.stopServer();
					}catch(Exception except)
					{
						JOptionPane.showMessageDialog(frame,
								 "Can't stop server. "+except.getMessage());	
						return;
					}
					serverAddress.setEnabled(true);
					portServer.setEnabled(true);
					rootDir.setEnabled(true);
					browseButton.setEnabled(true);
					actionButton.setText("Start Server");
					browseMentButton.setEnabled(true);
					mentenaceButton.setText("Start Mentenance");
					mentenanceDir.setEnabled(true);
					statusServerLable.setText("Stopped");
				}
			}
		});

		mentenaceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (mentenaceButton.getText().equals("Start Mentenance")) {
					if (actionButton.getText().equals("Start Server")) {
						JOptionPane.showMessageDialog(frame,
								"Start server first");
						return;
					}
					if (!validMentDir.getText().equals("OK")) {
						JOptionPane.showMessageDialog(frame,
								"Not a valid path for the mentenace!");
						return;
					}
					server.setPathMent(validMentDir.getText());
					server.setMentenance(true);

					rootDir.setEnabled(true);
					browseButton.setEnabled(true);
					browseMentButton.setEnabled(false);
					mentenaceButton.setText("Stop Mentenace");
					statusServerLable.setText("Mentenace");
					mentenanceDir.setEnabled(false);
					actionButton.setEnabled(false);
				} else {
					// actiunea de stop mentenace
					if (!validRootDir.getText().equals("OK")) {
						JOptionPane.showMessageDialog(frame,
								"Not a valid path for the root directory!");
						return;
					}
					server.setPathRoot(rootDir.getText());
					server.setMentenance(false);
					
					browseMentButton.setEnabled(true);
					mentenaceButton.setText("Start Mentenance");
					mentenanceDir.setEnabled(true);
					rootDir.setEnabled(false);
					browseButton.setEnabled(false);
					statusServerLable.setText("Started");
					actionButton.setEnabled(true);

				}
			}
		});

	}

	protected static MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	private void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);

		frame = new JFrame("Server GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane and add swing components to it
		addComponentsToPane(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ServerGUI().createAndShowGUI();
			}
		});
	}
	


	public void setRootDir(String newRoot) {
		rootDir.setText(newRoot);
	}

	public void setMentDir(String newDir) {
		mentenanceDir.setText(newDir);
	}

	public void validateRoot() {
		String path = rootDir.getText();

		File file = new File(path);

		if (!file.isDirectory()) {
			validRootDir.setText("Not a directory");
			return;
		}
		file = new File(path + "/index.html");
		if (!file.exists()) {
			validRootDir.setText("No index.html");
			return;
		}

		validRootDir.setText("OK");
	}

	public void validateMent() {
		String path = mentenanceDir.getText();

		File file = new File(path);

		if (!file.isDirectory()) {
			validMentDir.setText("Not a directory");
			return;
		}
		file = new File(path + "/mentenance.html");
		if (!file.exists()) {
			validMentDir.setText("No mentenance.html");
			return;
		}

		validMentDir.setText("OK");
	}
	
	public static void showMessage(String msg)
	{
		JOptionPane.showMessageDialog(frame,
				msg);

	}

}
