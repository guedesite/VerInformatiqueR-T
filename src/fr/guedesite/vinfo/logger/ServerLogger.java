package fr.guedesite.vinfo.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.guedesite.vinfo.Boot;
import fr.guedesite.vinfo.interfaces.LoggerInstance;

public class ServerLogger implements LoggerInstance{

	private final Logger logger = LoggerFactory.getLogger(Boot.class);

	@Override
	public void tryPrint(String source, String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void err(Exception e) {
		e.printStackTrace();
		
	}


}
