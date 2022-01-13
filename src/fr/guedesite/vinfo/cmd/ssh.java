package fr.guedesite.vinfo.cmd;

import java.util.Iterator;

import fr.guedesite.vinfo.main;
import fr.guedesite.vinfo.ssh.ipConnection;

public class ssh extends CMDRunnable {

	@Override
	public void execute(String[] arg) {
		if(arg.length < 2) {
			showHelp();
			return;
		} else {
			int id = 0;
			try {
				id = Integer.parseInt(arg[0]);
			} catch(NumberFormatException e) {
				log("Nombre invalide");
				showHelp();
				return;
			}
			if(main.IndexedIp == null) {
				log("Aucune IP indexé");
				log("essayez \"scan\"");
				return;
			}
			if(ipConnection.findId(id) != null) {
				
				ipConnection con = ipConnection.findId(id);
				if(arg[1].equals("open")) {
					if(arg.length == 4) {
						log("Connexion: "+arg[2]+"@"+con.ip+" pass:"+arg[3]+" en cours ...");
						if(con.connect(arg[2], arg[3])) {
							log("Connexion établie !");
						} else {
							log("Erreur lors de la tentative de connexion:");
							log(con.lastError.getMessage());
						}
					} else {
						log("Argument manquant");
					}
				} else if(arg[1].equals("close")) {
					if(con.isConnected()) {
						con.close();
					} 
					log("Connexion "+id+" fermé");
				} else {
					showHelp();
					return;
				}
			} else {
				log("Aucune IP avec cette ID trouvé");
			}
		}
		
	}

	@Override
	public void showHelp() {
		System.out.println("ssh (id) open (user) (password) -> initialiser la connexion d'un hôte");
		System.out.println("ssh (id) close -> fermer la connexion d'un hôte");
	}

	@Override
	public String getDescription() {
		return "initialiser la connexion d'un hôte";
	}

}
