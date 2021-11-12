package fr.guedesite.vinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.guedesite.vinfo.interfaces.instance;

public class Boot {

	private List launchArg;
	
	private boolean export;
	private instance versInstance;
	
	public static void main(String[] args) {
		new Boot(args);
	}

	protected Boot(String[] args) {
		launchArg = new ArrayList<String>(Arrays.asList(args));
		if(launchArg.contains("server")) {
			this.versInstance = new server();
		} else {
			this.versInstance = new client();
		}
	}

}
