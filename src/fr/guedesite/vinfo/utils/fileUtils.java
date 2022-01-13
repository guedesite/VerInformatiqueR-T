package fr.guedesite.vinfo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import fr.guedesite.vinfo.main;

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
	
	public static void copyLocalToRemote(Session session, String fromFile, String toFile) throws Exception {
        boolean ptimestamp = true;

        // exec 'scp -t rfile' remotely
        String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + toFile;
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        // get I/O streams for remote scp
        OutputStream out = channel.getOutputStream();
        InputStream in = channel.getInputStream();

        channel.connect();

        if (checkAck(in) != 0) {
            System.exit(0);
        }

        File _lfile = new File(fromFile);

        if (ptimestamp) {
            command = "T" + (_lfile.lastModified() / 1000) + " 0";
            // The access time should be sent here,
            // but it is not accessible with JavaAPI ;-<
            command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }
        }

        // send "C0644 filesize filename", where filename should not include '/'
        long filesize = _lfile.length();

        command = "C0644 " + filesize + " ";
        if (fromFile.lastIndexOf('/') > 0) {
            command += fromFile.substring(fromFile.lastIndexOf('/') + 1);
        } else {
            command += fromFile;
        }

        command += "\n";
        out.write(command.getBytes());
        out.flush();

        if (checkAck(in) != 0) {
            throw new Exception("Mauvais ack");
        }

        // send a content of lfile
        FileInputStream fis = new FileInputStream(fromFile);
        byte[] buf = new byte[1024];
        while (true) {
            int len = fis.read(buf, 0, buf.length);
            if (len <= 0) { break; }
            out.write(buf, 0, len); //out.flush();
        }

        // send '\0'
        buf[0] = 0;
        out.write(buf, 0, 1);
        out.flush();

        if (checkAck(in) != 0) {
            System.exit(0);
        }
        out.close();

        try {
            if (fis != null) fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        channel.disconnect();
    }
	
	public static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //         -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }
	
	public static String getPathCurrentJar() {
		String path = main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if(System.getProperty("os.name").toLowerCase().contains("win") && path.startsWith("/")) {
			return path.substring(1);
		}
		return path;
	}
	
}
