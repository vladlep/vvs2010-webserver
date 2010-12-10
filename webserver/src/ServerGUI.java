import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.ParseException;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class ServerGUI {
	public static boolean RIGHT_TO_LEFT = false;

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

		JButton actionButton = new JButton("Start Server");

		contentPane.add(actionButton, BorderLayout.PAGE_START);

		actionButton = new JButton("Button 2 (CENTER)");
//		actionButton.setPreferredSize(new Dimension(200, 100));
		contentPane.add(actionButton, BorderLayout.CENTER);
		
		actionButton = new JButton("Button 3 (LINE_START)");
		contentPane.add(actionButton, BorderLayout.LINE_START);

		actionButton = new JButton("Long-Named Button 4 (PAGE_END)");
		contentPane.add(actionButton, BorderLayout.PAGE_END);

		actionButton = new JButton("5 (LINE_END)");
		contentPane.add(actionButton, BorderLayout.LINE_END);
		
		JComponent labelsPanel = new  JPanel();
		labelsPanel.setLayout(new GridLayout(3,2));
		
		labelsPanel.add(new JLabel("Server status :"));
		JLabel statusServerLable = new JLabel("Stopped");
		labelsPanel.add(statusServerLable);
		contentPane.add(labelsPanel);
		
		
		labelsPanel.add(new JLabel("Server address :"));
		JFormattedTextField serverAddress = new JFormattedTextField(createFormatter("###.###.###.###"));
		labelsPanel.add(serverAddress);
		
		labelsPanel.add(new JLabel("Server port :"));
		JFormattedTextField portServer = new JFormattedTextField(createFormatter("#####"));
		labelsPanel.add(portServer);
//		portServer.setText("11");
//		portServer.setEditable(false);
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

		JFrame frame = new JFrame("Server GUI");
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
