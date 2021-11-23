package fr.guedesite.vinfo.interfaces;

public interface LoggerInstance {

	public void init();
	
	public void tryPrint(String source, String key, String value);
	
	public void err(Exception e);

}
