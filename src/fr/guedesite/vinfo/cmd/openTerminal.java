package fr.guedesite.vinfo.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import fr.guedesite.vinfo.main;
import fr.guedesite.vinfo.ssh.ipConnection;

public class openTerminal extends CMDRunnable {

	@Override
	public void execute(String[] arg) {
		if(arg.length == 1) {
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
			if(ipConnection.findId(id) != null) {
				ipConnection con = ipConnection.findId(id);
				if(con.isConnected()) {
					Session session = con.getConnection();
					try {
						log("Vous êtes maintenant un terminal distant");
						log("Pour sortir de se mode tapez \"openTerminal exit\"");
					    Scanner sc = new Scanner(System.in);
					    boolean exit = false;
						while(!exit) {
							String cmd = sc.nextLine();
							if(cmd.equals("openTerminal exit")) {
								exit = true;
								continue;
							}
							ChannelExec channel = (ChannelExec) session.openChannel("exec");
							channel.setInputStream(null);
						    channel.setErrStream(System.err);
						    channel.setCommand(cmd);
						    InputStream inputStream;
							try {
								inputStream = channel.getInputStream();
							} catch (IOException e1) {
								log("Une erreur fatal est survenue");
								log(e1.getMessage());
								sc.close();
								return;
							}
							channel.connect();
							byte[] byteObject = new byte[10240];
							StringBuilder bld = new StringBuilder();
				            while (true) {
				            	try {
					                while (inputStream.available() > 0) {
					                    int readByte = inputStream.read(byteObject, 0, 1024);
					                    if (readByte < 0)
					                        break;
					                    bld.append(new String(byteObject, 0, readByte));
					                }
					                if (channel.isClosed()) {
					                	break;
					                }
				            	}catch(Exception e) {
				            		log("Une erreur est survenue");
									log(e.getMessage());
				            		break;
				            	}
				            }
				            System.out.println(bld.toString());
							channel.disconnect();
						}
						sc.close();
					} catch (JSchException e) {
						log("Une erreur fatal est survenue");
						log(e.getMessage());
					}
				} else {
					log("La connection n'est pas établie pour cette id");
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
		System.out.println("openTerminal (id) > ouvre le terminal d'une connection");
		
	}

	@Override
	public String getDescription() {
		return "ouvre le terminal d'une connection";
	}

}
