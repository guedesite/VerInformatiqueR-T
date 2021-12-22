package fr.guedesite.vinfo.cmd;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import fr.guedesite.vinfo.main;
import fr.guedesite.vinfo.ssh.ipConnection;
import fr.guedesite.vinfo.utils.fileUtils;

public class sendFile extends CMDRunnable{

	@Override
	public void execute(String[] arg) {
		if(arg.length > 2) {
			
			int id = 0;
			try {
				id = Integer.parseInt(arg[0]);
			} catch(NumberFormatException e) {
				log("Nombre invalide");
				showHelp();
			}
			if(main.IndexedIp == null) {
				log("Aucune IP indexé");
				log("essayez \"scan\"");
				return;
			}
			if(main.IndexedIp.containsKey(id)) {
				ipConnection con = main.IndexedIp.get(id);
				if(con.isConnected()) {
					Session session = con.getConnection();
					File source;
					if(arg[1].equals("filechooser")) {
						source = fileUtils.openFileChooser();
					}else {
						source = new File(arg[1]);
					}
					if(source.exists()) {
						String dest = arg[2] != null ? arg[2] : "/";
						try {
							log("Envoie du fichier "+source.getName()+" en cours ...");
							ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
							sftpChannel.connect();
							sftpChannel.put(source.getAbsolutePath(), dest);
							sftpChannel.disconnect();
							log("Le fichier a été envoyé avec succès");
						} catch (Exception e) {
							log("Une erreur fatal est survenue");
							log(e.getMessage());
						}
					} else {
						log("Le fichier "+source.getName()+" n'éxiste pas");
						log("Vous pouvez utiliser l'argument \"filechooser\" pour facilité la sélection du fichier");
					}
				} else {
					log("La connexion n'est pas établie pour cette id");
					log("essayez \"ssh ?\"");
				}
			} else {
				log("Aucune IP avec cette ID trouvé");
			}
		} else {
			showHelp();
		}
		
	}

	@Override
	public void showHelp() {
		System.out.println("sendFile (id) (fichier source | \"filechooser\" pour sélèctionner le fichier) (optionnel| fichier destination) -> envois un fichier à une connexion SSH");
		
	}

	@Override
	public String getDescription() {
		return "envois un fichier à une connexion SSH";
	}

}
