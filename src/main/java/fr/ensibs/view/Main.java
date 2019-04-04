package fr.ensibs.view;

import fr.ensibs.client.ClientRiver;
import fr.ensibs.river.RiverLookup;
import net.jini.space.JavaSpace;

public class Main
{

	/**
	 * Print a usage message and exit
	 */
	 private static void usage() {
		 System.out.println("Usage: java -jar target/pictionary-1.0.jar <server_host> <server_port>");
		 System.exit(-1);
	 }
	 
	public static void main( String[] args )
	{
		/*FrameAffichage frame = new FrameAffichage() ;
		frame.setVisible( true ) ;
		//frame.play() ;
		//frame.dispose() ;
		*/
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
				//instance.test();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
