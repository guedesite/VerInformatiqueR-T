package fr.guedesite.vinfo.utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class fileUtils {

	public static File openFileChooser() {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		JPanel j = new JPanel();
		frame.add(j);
		File f = null;
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(j);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            System.out.println(f);
        } 
        frame.setVisible(false);
        frame.dispose();
        return f;
	}
	
}
