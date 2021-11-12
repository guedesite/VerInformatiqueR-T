package fr.guedesite.vinfo.interfaces;

import java.net.ServerSocket;

import fr.guedesite.vinfo.exceptions.RunningIncorrectActionException;

public abstract class instance {

	protected boolean mute = false;
	protected boolean hasStart = false;
	
	public void setMute(boolean value) throws RunningIncorrectActionException {
		if(hasStart) {
			throw new RunningIncorrectActionException("Cannot change Mute Value while is running");
		}
		mute = value;
	}

	
	public abstract void init(LoggerInstance log);
	
	
}
