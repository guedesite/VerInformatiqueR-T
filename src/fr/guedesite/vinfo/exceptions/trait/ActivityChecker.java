package fr.guedesite.vinfo.exceptions.trait;

import java.util.ArrayList;
import java.util.List;

import fr.guedesite.vinfo.interfaces.LoggerInstance;
import fr.guedesite.vinfo.interfaces.TraitementInstance;

public class ActivityChecker implements TraitementInstance{

	private LoggerInstance logger;
	private List<Long> TimerCount;
	
	@Override
	public void init(LoggerInstance log) {
		this.logger = log;
		this.TimerCount = new ArrayList<Long>();
	}

	@Override
	public void loop() {
		List<Long> rm = new ArrayList<Long>();
		this.TimerCount.stream().forEach(x -> { if(x < System.currentTimeMillis() - 30000) { rm.add(x); } });
		this.TimerCount.removeAll(rm);
	}

	@Override
	public String getName() {
		return "ActivityChecker";
	}

	@Override
	public boolean hasToLoop() {
		return true;
	}

	@Override
	public boolean hasInfoToPrint() {
		return false;
	}

	@Override
	public String getInfoToPrint() {
		return null;
	}
	
	public void update() {
		this.TimerCount.add(System.currentTimeMillis());
	}
	
	public long getActivity() {
		return this.TimerCount.size();
	}

}
