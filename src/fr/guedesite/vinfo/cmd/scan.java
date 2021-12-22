package fr.guedesite.vinfo.cmd;

import java.util.HashMap;
import java.util.Iterator;

import fr.guedesite.vinfo.main;
import fr.guedesite.vinfo.ssh.ipConnection;
import fr.guedesite.vinfo.utils.scanNetwork;

public class scan extends CMDRunnable{

	@Override
	public void execute(String[] arg) {
		if(arg.length == 0) {
			scanNetwork scan = scanNetwork.start();
			if(scan.getAllIp().size() == 0) {
				log("Aucun hote trouvé");
				return;
			} else 
			{
				main.IndexedIp = new HashMap<Integer, ipConnection>();
				int ind = 0;
				for(String s: scan.getAllIp()) {
					main.IndexedIp.put(ind, new ipConnection(s));
					log("id "+ind+": "+s);
					ind++;
				}
			}
		} else if(arg[0].equals("show")) {
			if(main.IndexedIp == null || main.IndexedIp.isEmpty()) {
				log("aucune ip indexé");
				return;
			}
			Iterator<Integer> it = main.IndexedIp.keySet().iterator();
			while(it.hasNext()) {
				int key = it.next();
				log("id "+key+": "+main.IndexedIp.get(key).ip);
			}
		}
	}

	@Override
	public void showHelp() {
		System.out.println("scan > scan et index les ips");
		System.out.println("scan show > affiche les ips indexé");
	}

	@Override
	public String getDescription() {
		return "Scan le réseau et index les hôtes disponible";
	}

}
