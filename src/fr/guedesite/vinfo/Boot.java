package fr.guedesite.vinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.guedesite.vinfo.interfaces.LoggerInstance;
import fr.guedesite.vinfo.interfaces.instance;
import fr.guedesite.vinfo.logger.ClientLogger;
import fr.guedesite.vinfo.logger.ServerLogger;

public class Boot {

	public static Boot instance;
	
	private List launchArg;
	
	private boolean isServerSide;
	
	private boolean export;
	private instance versInstance;
	
	private LoggerInstance logger;
	
	private boolean closeRequest = false;
	
	public static void main(String[] args) {
		new Boot(args);
	}

	private Boot(String[] args) {
		instance = this;
		launchArg = new ArrayList<String>(Arrays.asList(args));
		if(launchArg.contains("server")) {
			this.versInstance = new server();
			this.isServerSide = true;
			this.logger = new ServerLogger();
		} else {
			this.versInstance = new client();
			this.isServerSide = true;
			this.logger = new ClientLogger();
		}
		
		this.versInstance.init(logger);
		long lastFrame = System.currentTimeMillis();
		while(!closeRequest) {
			this.getVersInstance().update();
			while(lastFrame > System.currentTimeMillis() - 1000) {
				try {Thread.sleep(100); } catch (InterruptedException e) {}
			}
			lastFrame = System.currentTimeMillis();
		}
		
	}
	
	public List getLaunchArg() {
		return this.launchArg;
	}

	public LoggerInstance getLogger() {
		return logger;
	}
	
	public instance getVersInstance() {
		return this.versInstance;
	}
	
	
}
