package fr.guedesite.vinfo.cmd;

import java.io.IOException;

public class clear extends CMDRunnable {

	@Override
	public void execute(String[] arg) {
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				new ProcessBuilder("cmd", "/c", "clear").inheritIO().start().waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void showHelp() {
		System.out.println("clear -> vide la console");
		
	}

	@Override
	public String getDescription() {
		return "Vide la console";
	}

}
