package fr.guedesite.vinfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import fr.guedesite.vinfo.cmd.*;
import fr.guedesite.vinfo.ssh.ipConnection;
import fr.guedesite.vinfo.utils.scanNetwork;

public class main {
	
	public static Map<Integer, ipConnection> IndexedIp;
	private static Map<String, CMDRunnable> cmd;
	

	public static void main(String[] args) {
		
		
		
		System.out.println("Démarrage du verre informatique");
		System.out.println("Enregistrement des commandes ...");
		registerCmd();
		System.out.println("");
		System.out.println("Doc: https://github.com/guedesite/VersInformatiqueR-T");
		System.out.println("=> Tapez help pour voir la liste des commandes");
		System.out.println("");
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			String[] line = sc.nextLine().split(" ");
			if(line[0] == "") {
				continue;
			}
			if(cmd.containsKey(line[0])) {
				CMDRunnable run = cmd.get(line[0]);
				String[] arguments = Arrays.copyOfRange(line, 1, line.length);
				if(arguments.length > 0 && arguments[0].equals("?")) {
					run.showHelp();
					continue;
				}
				if(!line[0].equals("timer")) { timer.call(); }
				run.execute(arguments);
				if(!line[0].equals("timer")) { timer.call(); }
				System.out.println("");
			} else {
				if(line[0].equals("help")) {
					System.out.println("Liste de toutes les commandes disponible:");
					System.out.println("");
					Iterator<String> it = cmd.keySet().iterator();
					while(it.hasNext()) {
						String key = it.next();
						System.out.println(key +" -> "+cmd.get(key).getDescription());
					}
					System.out.println("");
					System.out.println("Tapez \"(la commande) ?\"  pour plus d'information");
					System.out.println("");
				} else {
					System.out.println("Commande introuvable");
				}
			}
			
		}
	}
	
	
	
	private static void registerCmd() {
		cmd = new HashMap<String, CMDRunnable>();
		cmd.put("timer", new timer());
		cmd.put("clear", new clear());
		cmd.put("scan", new scan());
		cmd.put("ssh", new ssh());
		cmd.put("brute", new brute());
		cmd.put("openTerminal", new openTerminal());
		cmd.put("sendFile", new sendFile());
		cmd.put("logger", new logger());
		
	}

}
