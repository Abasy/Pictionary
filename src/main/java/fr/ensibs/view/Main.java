package fr.ensibs.view;

import java.io.IOException;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.ensibs.client.Client;
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
				
				if(space != null)
				{
					FrameAffichage frame = new FrameAffichage() ;
					frame.setVisible( true ) ;
					//frame.play();
					//frame.dispose();
					Client client = new Client( host , Integer.parseInt(port) , frame.moi.name ) ;
					try
					{
						client.context = new InitialContext() ;
						client.topic = (Topic) client.context.lookup( client.topicname ) ;
						TopicConnectionFactory topic_connection_factory = (TopicConnectionFactory) client.context.lookup( "ConnectionFactory" ) ;
						client.topic_connection = topic_connection_factory.createTopicConnection() ;
						String[] tmp = { "playing=true" } ;
						client.filter( client.parseTags( tmp , 0 ) ) ;
						Properties tags = client.parseTags( tmp , 0 ) ;
						client.share( "hello" , tags ) ;
					} catch (NamingException | JMSException e1) {e1.printStackTrace();}
					try
					{
						client.context.close() ;
						client.close() ;
					} catch (IOException | NamingException e) {e.printStackTrace();}
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
