import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FileChooserUI;
import javax.swing.text.MaskFormatter;

public class ServerGUI {
	public static boolean RIGHT_TO_LEFT = false;
	private JFrame frame;
	
	public  void addComponentsToPane(Container contentPane) {
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

		
//		contentPane.add(actionButton, BorderLayout.PAGE_START);
//
//		actionButton = new JButton("Button 2 (CENTER)");
////		actionButton.setPreferredSize(new Dimension(200, 100));
//		contentPane.add(actionButton, BorderLayout.CENTER);
//		
//		actionButton = new JButton("Button 3 (LINE_START)");
//		contentPane.add(actionButton, BorderLayout.LINE_START);
//
//		actionButton = new JButton("Long-Named Button 4 (PAGE_END)");
//		contentPane.add(actionButton, BorderLayout.PAGE_END);
//
		
		
		JComponent labelsPanel = new  JPanel();
		GridLayout labelsLayout =new GridLayout(3,2);
		labelsLayout.setHgap(2);
		labelsLayout.setVgap(2);
		labelsPanel.setLayout(labelsLayout);
		
		contentPane.add(labelsPanel, BorderLayout.LINE_START);
		
		labelsPanel.add(new JLabel("Server status :"));
		JLabel statusServerLable = new JLabel("Stopped");
		labelsPanel.add(statusServerLable);
				
		labelsPanel.add(new JLabel("Server address :"));
		JFormattedTextField serverAddress = new JFormattedTextField(createFormatter("###.###.###.###"));
		labelsPanel.add(serverAddress);
		
		labelsPanel.add(new JLabel("Server port :"));
		JFormattedTextField portServer = new JFormattedTextField(createFormatter("#####"));
		labelsPanel.add(portServer);
//		portServer.setText("11");
//		portServer.setEditable(false);
		
		JButton actionButton = new JButton("Start Server");
		contentPane.add(actionButton, BorderLayout.LINE_END);
		
		JPanel pathPanel = new JPanel();
		
		contentPane.add(pathPanel, BorderLayout.PAGE_END);
		LayoutManager pathPanelLayout = new GridLayout(2,3);
		pathPanel.setLayout(pathPanelLayout);
		
		pathPanel.add(new JLabel("Root dir : "));
		JTextField rootDir = new JTextField("Insert root dir");
		pathPanel.add(rootDir);
		
		JButton browseButton = new JButton("Browse");
		pathPanel.add(browseButton);
		
		final  JFileChooser browseRoot = new JFileChooser();
		browseRoot.changeToParentDirectory();
		browseRoot.setMultiSelectionEnabled(false);
		browseRoot.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		browseButton.addActionListener( new BrowseListener(frame,browseRoot));
		
		pathPanel.add(new JLabel("Mentenance dir : "));
		JTextField mentenanceDir = new JTextField("Insert mentenance dir");
		pathPanel.add(mentenanceDir);
		
		JButton browseMentButton = new JButton("Browse");
		pathPanel.add(browseMentButton);
		browseMentButton.addActionListener( new BrowseListener(frame,browseRoot));
		
		
		
	
		
		//pathPanel.add(browseRoot);
		
		
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
}
