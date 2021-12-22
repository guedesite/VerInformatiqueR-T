package fr.guedesite.vinfo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class scanNetwork{

	private int count = 0;
	private List<String> allIp = new ArrayList<String>();
	
	private scanNetwork() {
		final byte[] ip;
		String templocal = null;
		try {
			templocal = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	    try {
	        ip = InetAddress.getLocalHost().getAddress();
	    } catch (Exception e) {
	        return;     // exit method, otherwise "ip might not have been initialized"
	    }
	    final String local = templocal;
	    for(int i=1;i<=254;i++) {
	        final int j = i;  // i as non-final variable cannot be referenced from inner class
	        new Thread(new Runnable() {   // new thread for parallel execution
	            public void run() {
	                try {
	                    ip[3] = (byte)j;
	                    InetAddress address = InetAddress.getByAddress(ip);
	                    String output = address.toString().substring(1);
	                    if (!output.equals(local) && address.isReachable(1000)) {
	                       addAdress(output);
	                    } 
	                    addCount();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();     // dont forget to start the thread
	    }
	}
	
	public static scanNetwork start() {
		scanNetwork scan = new scanNetwork();
		while(scan.count <= 253) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return scan;
	}
	
	private synchronized void addCount() {
		this.count++;
	}
	
	private synchronized void addAdress(String ip) {
		allIp.add(ip);
	}

	public List<String> getAllIp() {
		return this.allIp;
	}
	
	

}
