package fr.guedesite.vinfo.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

import fr.guedesite.vinfo.main;
import fr.guedesite.vinfo.ssh.ipConnection;
import fr.guedesite.vinfo.utils.fileUtils;

public class deploy extends CMDRunnable {

	@Override
	public void execute(String[] arg) {
		System.out.println(" ");
		String cur = fileUtils.getPathCurrentJar();
		for(ipConnection ip: main.IndexedIp) {
			if(ip != null && !ip.ip.startsWith("127")) {
				if(!ip.isConnected()) {
					if(ip.connect("stud", "toto")) {
						try {
							fileUtils.copyLocalToRemote(ip.getConnection(), cur, cur);
							System.out.print(".");
						} catch (Exception e) {
							System.out.print(":");
						}
					}
				}
			}
		}
		System.out.print(";");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println(".");
		for(ipConnection ip: main.IndexedIp) {
			if(ip != null && !ip.ip.startsWith("127")) {
				if(ip.isConnected()) {
					ChannelExec channel = null;
					try {
						channel = (ChannelExec) ip.getConnection().openChannel("exec");
					} catch (JSchException e2) {
						// TODO Auto-generated catch block
						
						e2.printStackTrace();
						continue;
					}
					channel.setInputStream(null);
				    channel.setErrStream(System.err);
				    channel.setCommand("java -jar "+cur+" deploy");
				    InputStream inputStream;
					try {
						inputStream = channel.getInputStream();
					} catch (IOException e1) {
						log("Une erreur fatal est survenue");
						log(e1.getMessage());
						sc.close();
						return;
					}
					try {
						channel.connect();
					} catch (JSchException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						continue;
					}
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
			}
		}
		
	}

	@Override
	public void showHelp() {
		
	}

	@Override
	public String getDescription() {
		return null;
	}

}
