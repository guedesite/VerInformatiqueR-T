package fr.guedesite.vinfo.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.guedesite.vinfo.Boot;
import fr.guedesite.vinfo.interfaces.LoggerInstance;

public class ClientLogger implements LoggerInstance {

	private final Logger logger = LoggerFactory.getLogger(Boot.class);
	
	private boolean isMute = true;
	
	@Override
	public void tryPrint(String source, String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		this.isMute = !Boot.instance.getLaunchArg().contains("noMute");
		
	}

	@Override
	public void err(Exception e) {
		if(!this.isMute) {
			e.printStackTrace();
		}
		
	}

}
