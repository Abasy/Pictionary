package fr.ensibs.client;

import fr.ensibs.river.RiverLookup;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

public class ClientRiver {
	/**
	 * true if the application has been closed
	 */
	private boolean closed;
	
	/**
	 * A javaSpace that contain all Objets airports, flights and seats
	 */
	private JavaSpace space;
	
	public ClientRiver(JavaSpace space) {
		if(space != null) {
			System.out.println("A JavaSpace has been discovered.");
			System.out.println("Writing a message into the space...");
			this.space = space;
		}else {
			System.out.println("No service found");
			System.exit(-1);
		}
	}

	
	/**
	 * Print a usage message and exit
	 */
	 private static void usage() {
		 System.out.println("Usage: java -jar target/flight-reservation-1.0.jar <server_host> <server_port>");
		 System.exit(-1);
	 }
	 
	public static void main(String[] args) {
		if (args.length < 2 || args[0].equals("-h")) {
			usage();
		}else if(args.length == 2) {
			String host = args[0];
			String port = args[1];
			
			try {
				System.out.println("Searching for a JavaSpace...");
				RiverLookup river = new RiverLookup();
				JavaSpace  space = (JavaSpace) river.lookup(host, Integer.parseInt(port), JavaSpace.class);
				
				ClientRiver instance = new ClientRiver(space);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addUser() {
		
	}
	
	public void addWord() {
		
	}
}
