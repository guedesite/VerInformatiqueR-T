package fr.guedesite.vinfo.cmd;

public class timer extends CMDRunnable{

	public static boolean isEnable = false;
	private static long time = 0L;
	private static boolean stat = false;
	
	@Override
	public void execute(String[] arg) {
		if(arg.length == 0) {
			showHelp();
		}else if(arg[0].equals("on")) {
			isEnable = true;
			log("Le timer est activé");
		}else if(arg[0].equals("off")) {
			isEnable = false;
			log("Le timer est désactivé");
		}else if(arg[0].equals("status")) {
			if(isEnable) {
				log("Le timer est activé");
			} else {
				log("Le timer est désactivé");
			}
		} else {
			showHelp();
		}
		
	}
	
	public static void call() {
		if(!isEnable) {
			return;
		}
		if(!stat) {
			time = System.currentTimeMillis();
			stat = true;
		} else {
			System.out.println("timer > "+(System.currentTimeMillis() - time)+"ms");
			stat = false;
		}
		
	}

	@Override
	public void showHelp() {
		System.out.println("timer on -> active le timer sur les commandes");
		System.out.println("timer off -> désactive le timer sur les commandes");
		System.out.println("timer status -> affiche l'état du timer");
	}

	@Override
	public String getDescription() {
		return "affiche le temps d'éxécution des commandes";
	}

}
