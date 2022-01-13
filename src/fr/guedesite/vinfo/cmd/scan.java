package fr.guedesite.vinfo.cmd;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import fr.guedesite.vinfo.main;
import fr.guedesite.vinfo.ssh.ipConnection;

public class scan extends CMDRunnable{

	@Override
	public void execute(String[] arg) {
		if(arg.length == 0) {
			Enumeration<NetworkInterface> n = null;
			try {
				n = NetworkInterface.getNetworkInterfaces();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			main.IndexedIp = new ArrayList<ipConnection>();
			int ind = 0;
		    for (; n.hasMoreElements();)
		    {
		        NetworkInterface e = n.nextElement();

		        Enumeration<InetAddress> a = e.getInetAddresses();
		        for (; a.hasMoreElements();)
		        {
		            InetAddress addr = a.nextElement();
		           if(addr.toString().lastIndexOf(".") == -1) {
		        	   continue;
		           }
		            String res = addr.toString().substring(0,  addr.toString().toString().lastIndexOf(".")).substring(1);
		            log(res+".* ...");
		            ConcurrentSkipListSet networkIps = scan(res, 254);
					if(networkIps.size() == 0) {
						continue;
					} else 
					{
						Iterator it = networkIps.iterator();
						while(it.hasNext()) {
							String ip = it.next().toString();
							main.IndexedIp.add(new ipConnection(ind, ip));
							log("id "+ind+": "+ip);
							ind++;
						}
					}
		        }
		    }
		    if(main.IndexedIp.isEmpty()) {
				log("Aucun hote trouvé");
				return;
			}else {
				log(main.IndexedIp.size() +" hote"+(main.IndexedIp.size() > 1 ? "s" : "")+" trouvé");
			}
		} else if(arg[0].equals("show")) {
			if(main.IndexedIp == null || main.IndexedIp.isEmpty()) {
				log("aucune ip indexé");
				return;
			}
			for(ipConnection ip : main.IndexedIp) {
				log("id "+ip.id+": "+ip.ip);
			}
		}
	}

	@Override
	public void showHelp() {
		System.out.println("scan > scan et index les ips");
		System.out.println("scan show > affiche les ips indexé");
	}

	@Override
	public String getDescription() {
		return "Scan le réseau et index les hôtes disponible";
	}

	public static ConcurrentSkipListSet scan(String firstIpInTheNetwork, int numOfIps) {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        final String networkId = firstIpInTheNetwork.substring(0, firstIpInTheNetwork.length() - 1);
        ConcurrentSkipListSet ipsSet = new ConcurrentSkipListSet();

        AtomicInteger ips = new AtomicInteger(0);
        while (ips.get() <= numOfIps) {
            String ip = networkId + ips.getAndIncrement();
            executorService.submit(() -> {
            	
            	 try {
         		    Session connection = new JSch().getSession("xyz", ip, 22);
         			connection.setPassword("xyz");
         			connection.setTimeout(2000);
         			connection.setConfig("StrictHostKeyChecking", "no");
         			connection.connect();
         	    } catch (JSchException e) {
         			if(e.getMessage().equals("Auth fail")) {
         				ipsSet.add(ip);
         			}
         		}
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return ipsSet;
    }
	
}
