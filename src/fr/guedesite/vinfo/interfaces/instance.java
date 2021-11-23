package fr.guedesite.vinfo.interfaces;

import java.net.ServerSocket;

import fr.guedesite.vinfo.exceptions.RunningIncorrectActionException;

public interface instance {

	public abstract void init(LoggerInstance log);
	
	public abstract void update();
	
	
}
