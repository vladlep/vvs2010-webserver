import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

public class ServerGUI {
	public static boolean RIGHT_TO_LEFT = false;
	private JFrame frame;
	private static JTextField rootDir;
	private static JTextField mentenanceDir;
	
	public void addComponentsToPane(Container contentPane) {
		// Use BorderLayout. Default empty constructor with no horizontal and
		// vertical
		// gaps
		contentPane.setLayout(new BorderLayout(5, 5));
		if (!(contentPane.getLayout() instanceof BorderLayout)) {
			contentPane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		if (RIGHT_TO_LEFT) {

			contentPane
					.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}

		// contentPane.add(actionButton, BorderLayout.PAGE_START);
		//
		// actionButton = new JButton("Button 2 (CENTER)");
		// // actionButton.setPreferredSize(new Dimension(200, 100));
		// contentPane.add(actionButton, BorderLayout.CENTER);
		//		
		// actionButton = new JButton("Button 3 (LINE_START)");
		// contentPane.add(actionButton, BorderLayout.LINE_START);
		//
		// actionButton = new JButton("Long-Named Button 4 (PAGE_END)");
		// contentPane.add(actionButton, BorderLayout.PAGE_END);
		//

		JComponent labelsPanel = new JPanel();
		GridLayout labelsLayout = new GridLayout(3, 2);
		labelsLayout.setHgap(2);
		labelsLayout.setVgap(2);
		labelsPanel.setLayout(labelsLayout);

		contentPane.add(labelsPanel, BorderLayout.LINE_START);

		labelsPanel.add(new JLabel("Server status :"));
		JLabel statusServerLable = new JLabel("Stopped");
		labelsPanel.add(statusServerLable);

		labelsPanel.add(new JLabel("Server address (*):"));
		final JFormattedTextField serverAddress = new JFormattedTextField(
				createFormatter("###.###.###.###"));
		labelsPanel.add(serverAddress);

		labelsPanel.add(new JLabel("Server port (*):"));
		final JFormattedTextField portServer = new JFormattedTextField(
				createFormatter("#####"));
		labelsPanel.add(portServer);
		// portServer.setText("11");
		// portServer.setEditable(false);

		final JButton actionButton = new JButton("Start Server");
		contentPane.add(actionButton, BorderLayout.CENTER);

		final JButton mentenaceButton = new JButton("Start Mentenance");
		contentPane.add(mentenaceButton, BorderLayout.LINE_END);

		JPanel pathPanel = new JPanel();

		contentPane.add(pathPanel, BorderLayout.PAGE_END);
		LayoutManager pathPanelLayout = new GridLayout(2, 3);
		pathPanel.setLayout(pathPanelLayout);

		pathPanel.add(new JLabel("Root dir (*) : "));
		rootDir = new JTextField("Insert root dir");
		
		
		rootDir.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("typed");
			}

			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("released");

			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("presed");

			}
		});
		
		pathPanel.add(rootDir);

		final JButton browseButton = new JButton("Browse");
		
		pathPanel.add(browseButton);

		final JFileChooser browseRoot = new JFileChooser();
		
		browseRoot.changeToParentDirectory();
		browseRoot.setMultiSelectionEnabled(false);
		browseRoot.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		browseButton.addActionListener(new BrowseListener(frame, browseRoot,"Root"));

		pathPanel.add(new JLabel("Mentenance dir : "));
		mentenanceDir = new JTextField("Insert mentenance dir");
		
		pathPanel.add(mentenanceDir);

		final JButton browseMentButton = new JButton("Browse");
		pathPanel.add(browseMentButton);
		browseMentButton
				.addActionListener(new BrowseListener(frame, browseRoot,"Mentenace"));

		actionButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (actionButton.getText().equals("Start Server")) {
					System.out.println(portServer.getText().trim()+"\"") ;
					if(serverAddress.getText().equals("   .   .   .   ") || portServer.getText().trim()=="" )
					{
						JOptionPane.showMessageDialog(frame,"Elements marked as (*) can not be empty");
						return;
					}
					serverAddress.setEditable(false);
					portServer.setEditable(false);
					rootDir.setEditable(false);
					browseButton.setEnabled(false);
					actionButton.setText("Stop Server");
				} else {
					//stop server action
					serverAddress.setEditable(true);
					portServer.setEditable(true);
					rootDir.setEditable(true);
					browseButton.setEnabled(true);
					actionButton.setText("Start Server");
					browseMentButton.setEnabled(true);
					mentenaceButton.setText("Start Mentenance");
					mentenanceDir.setEditable(true);
				}
			}
		});

		mentenaceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (mentenaceButton.getText().equals("Start Mentenance")) {
					if(actionButton.getText().equals("Start Server"))
					{
						JOptionPane.showMessageDialog(frame,"Start server first");
						return;
					}
					rootDir.setEditable(true);
					browseButton.setEnabled(true);
					browseMentButton.setEnabled(false);
					mentenaceButton.setText("Stop Mentenace");
					mentenanceDir.setEditable(false);
				}
				else
				{
					browseMentButton.setEnabled(true);
					mentenaceButton.setText("Start Mentenance");
					mentenanceDir.setEditable(true);
					if(actionButton.getText().equals("Stop Server"))
					{
						rootDir.setEditable(false);
						browseButton.setEnabled(false);
					}
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
	public static void setRootDir(String newRoot)
	{
		rootDir.setText(newRoot);
	}
	
	public static void setMentDir(String newDir)
	{
		mentenanceDir.setText(newDir);
	}
	
}
