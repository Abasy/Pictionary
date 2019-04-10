package fr.ensibs.view;

import java.io.IOException;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.ensibs.client.ClientJMS;
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
		if (args.length < 2 || args[0].equals("-h"))
		{
			usage();
		}
		else if(args.length == 2)
		{
			String host = args[0];
			String port = args[1];
			
			try
			{
				System.out.println("Searching for a JavaSpace...");
				RiverLookup river = new RiverLookup();
				JavaSpace  space = (JavaSpace) river.lookup(host, Integer.parseInt(port), JavaSpace.class);
				
				ClientRiver clientRiver = new ClientRiver(space);
				//instance.test();
				ClientJMS clientJMS = new ClientJMS(host, Integer.parseInt(port));
				if(space != null)
				{
					FrameAffichage frame = new FrameAffichage( host , port, clientRiver, clientJMS) ;
					frame.setVisible( true ) ;
					//frame.play();
					//frame.dispose();
				}
				else
				{
					System.out.println("No service found. Please launch the river server.");
					System.exit(-1);
				}
			}catch(Exception e) {e.printStackTrace();}
			
			
		}
	}

}
