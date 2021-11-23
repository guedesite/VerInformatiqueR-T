package fr.guedesite.vinfo.interfaces;

public interface TraitementInstance {

	public void init(LoggerInstance log);
	public void loop();
	
	public String getName();
	public boolean hasToLoop();
	
	public boolean hasInfoToPrint();
	public String getInfoToPrint();
}
