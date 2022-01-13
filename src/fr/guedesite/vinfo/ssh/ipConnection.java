package fr.guedesite.vinfo.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import fr.guedesite.vinfo.main;

public class ipConnection {

	private JSch jsch;
	private Session connection;
	public Exception lastError;
	
	public String ip;
	public int id;
	
	public ipConnection(int id, String ip) {
		this.ip = ip;
		this.id=id;
		jsch = new JSch();
	}
	
	public boolean isConnected() {
		return this.connection != null && this.connection.isConnected();
	}
	
	public void close() {
		if(this.isConnected()) {
			this.connection.disconnect();
		}
	}
	
	public Session getConnection() {
		return this.connection;
	}

	public boolean connect(String user, String mdp) {
		try {
			connection = jsch.getSession(user, this.ip, 22);
			connection.setPassword(mdp);
			connection.setTimeout(10000);
			connection.setConfig("StrictHostKeyChecking", "no");
			connection.connect();
			return true;
		}catch(Exception e) {
			lastError = e;
			return false;
		}
	}
	
	public static ipConnection findId(int id) {
		for(ipConnection ip:main.IndexedIp) {
			if(ip.id == id) {
				return ip;
			}
		}
		return null;
	}

}
