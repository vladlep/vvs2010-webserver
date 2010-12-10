import java.awt.event.ActionEvent;

import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class BrowseListener extends AbstractAction {
    /** generate serial version UID */
	private static final long serialVersionUID = 8028282704243134323L;
	JFileChooser chooser;
    JFrame frame;

    BrowseListener(JFrame frame, JFileChooser chooser) {
        super("Open");
        this.chooser = chooser;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent evt) {
        // Show dialog; this method does not return until dialog is closed
        int result = chooser.showOpenDialog(frame);

        if(result == JFileChooser.APPROVE_OPTION)
        {// Get the selected file
        File file = chooser.getSelectedFile();
        System.out.println(file.getAbsolutePath());
        }
        else
        	System.out.println("cancel");
    }
}