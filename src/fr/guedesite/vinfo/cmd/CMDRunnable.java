package fr.guedesite.vinfo.cmd;

import java.util.StringJoiner;

public abstract class CMDRunnable{
	public abstract void execute(String[] arg);
	public abstract void showHelp();
	public abstract String getDescription();
	
	public void log(String... value) {
		StringJoiner strj = new StringJoiner(" ");
		strj.add(this.getClass().getSimpleName());
		strj.add(">");
		for(String s:value) {
			strj.add(s);
		}
		System.out.println(strj.toString());
	}
	
}