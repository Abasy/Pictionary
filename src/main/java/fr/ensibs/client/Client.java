package fr.ensibs.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.objectweb.joram.client.jms.Destination;
import org.objectweb.joram.client.jms.admin.AdminException;
import org.objectweb.joram.client.jms.admin.AdminModule;

import fr.ensibs.view.FrameAffichage;

/**
* The entry point for the user photo sharing application that allows to enter
* descriptions and tags of photos to be shared and filter and choose photos
* from their tags
*/
public class Client implements Closeable
{
	
	/**
	* the JNDI context
	*/
	public Context context ;
	//private Context context2 ;
	
	private String host ;
	private int port ;
	private JMSContext jms_context ;
	//private JMSContext jms_context2 ;
	private JMSConsumer consumer ;
	//private JMSConsumer consumer2 ;
	public String topicname ;
	public TopicConnection topic_connection ;
	public Topic topic ;
	public TopicConnection topic_connection2 ;
	public Topic topic2 ;
	public TopicConnection topic_connection3 ;
	public Topic topic3 ;
	public TopicConnection topic_connection4 ;
	public Topic topic4 ;
	public TopicConnection topic_connection5 ;
	public Topic topic5 ;
	public TopicConnection topic_connection6 ;
	public Topic topic6 ;
	public TopicConnection topic_connection7 ;
	public Topic topic7 ;
	public TopicConnection topic_connection8 ;
	public Topic topic8 ;
	public TopicConnection topic_connection9 ;
	public Topic topic9 ;
	public String username ;
	
	/**
	* Print a usage message and exit
	*/
	private static void usage()
	{
		System.out.println( "Usage: java PhotoSharingApp <server_host_IP> <server_port> <user_name> <directory>" ) ;
		System.out.println( "Launch the user photo sharing application" ) ;
		System.out.println( "with :" ) ;
		System.out.println( "<server_host_IP>  the IP of the server" ) ;
		System.out.println( "<server_port>  the port number of the port" ) ;
		System.out.println( "<user_name>  the user name in the community" ) ;
		System.out.println( "<directory>  the local directory where photos are stored" ) ;
		System.exit( 0 ) ;
	}
	
	/**
	* Application entry point
	*
	* @param args see usage
	*/
	/*public static void main( String[] args )
	{
		if ( args.length != 3 )
		{
			usage() ;
		}
		String host = args[0] ;
		int port = Integer.parseInt( args[1] ) ;
		String userName = args[2] ;
		Client client = new Client( host , port , userName ) ;
		try
		{
			client.context = new InitialContext() ;
			client.topic = (Topic) client.context.lookup( client.topicname ) ;
			client.topic2 = (Topic) client.context.lookup( client.topicname ) ;
			client.topic3 = (Topic) client.context.lookup( client.topicname ) ;
			
			TopicConnectionFactory topic_connection_factory = (TopicConnectionFactory) client.context.lookup( "ConnectionFactory" ) ;
			client.topic_connection = topic_connection_factory.createTopicConnection() ;
			client.topic_connection2 = topic_connection_factory.createTopicConnection() ;
			client.topic_connection3 = topic_connection_factory.createTopicConnection() ;
			
			String[] tmp = { "a=a" } ;
			client.filter( client.parseTags( tmp , 0 ) ) ;
			Properties tags = client.parseTags( tmp , 0 ) ;
			client.share( "hello" , tags ) ;
			
			String[] tmp1 = { "b=b" } ;
			client.filter2( client.parseTags( tmp1 , 0 ) ) ;
			Properties tags2 = client.parseTags( tmp1 , 0 ) ;
			client.share2( "hello2" , tags2 ) ;
			client.share( "hello" , tags ) ;
		} catch (NamingException | JMSException e1) {e1.printStackTrace();}
		try
		{
			client.topic_connection.close() ;
			client.context.close() ;
			client.close() ;
		} catch (IOException | NamingException | JMSException e) {e.printStackTrace();}
	}
	*/
	/**
	* Constructor
	*
	* @param userName the user name in the community
	* @param directory the local directory where photos are stored
	*/
	public Client( String host , int port /*, String username*/ )
	{
		this.host = host ;
		this.port = port ;
		this.topicname = "topic_dessin_pictionary" ;
		//this.username = username ;
		
		// Connecting to JORAM server:
		try
		{
			System.out.println("Coucou");
			AdminModule.connect( this.host , this.port + 1 , "root" , "root" ) ;
		} catch (ConnectException | UnknownHostException | AdminException e) {e.printStackTrace();}
		// Initialize the JNDI context
		System.setProperty( "java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory" ) ;
		System.setProperty( "java.naming.factory.host" , this.host ) ;
		System.setProperty( "java.naming.factory.port" , Integer.toString( this.port ) ) ;
	}
	
	/**
	* Share a new photo
	*
	* @param file the photo file in the local directory
	* @param tags a list of tags that describe the photo
	*/
	public void share( String nom_joueur , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic ) ;
			TextMessage message = publish_session.createTextMessage( nom_joueur ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( nom_joueur + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share2( String nom_nouveau_joueur , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection2.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic2 ) ;
			TextMessage message = publish_session.createTextMessage( nom_nouveau_joueur ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection2.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( nom_nouveau_joueur + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share3( String coordonee , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection3.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic3 ) ;
			TextMessage message = publish_session.createTextMessage( coordonee ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection3.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( coordonee + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share4( String who_ready , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection4.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic4 ) ;
			TextMessage message = publish_session.createTextMessage( who_ready ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection4.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( who_ready + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share5( String message_a_envoyer , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection5.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic5 ) ;
			TextMessage message = publish_session.createTextMessage( message_a_envoyer ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection5.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( message_a_envoyer + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share6( String couleur_pinceau , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection6.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic6 ) ;
			TextMessage message = publish_session.createTextMessage( couleur_pinceau ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection6.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( couleur_pinceau + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share7( String couleur_reset , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection7.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic7 ) ;
			TextMessage message = publish_session.createTextMessage( couleur_reset ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection7.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( couleur_reset + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share8( String who_draw , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection8.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic8 ) ;
			TextMessage message = publish_session.createTextMessage( who_draw ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection8.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( who_draw + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	public void share9( String taille_pinceau , Properties tags )
	{
		try
		{
			TopicSession publish_session = this.topic_connection9.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicPublisher topic_publisher = publish_session.createPublisher( this.topic9 ) ;
			TextMessage message = publish_session.createTextMessage( taille_pinceau ) ;
			String key = "" ;
			String value = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				key = (String) entry.getKey() ;
				value = (String) entry.getValue() ;
				message.setStringProperty( key , value ) ;
			}
			this.topic_connection9.start() ;
			topic_publisher.publish( message ) ;
			System.out.println( taille_pinceau + " has been shared" ) ;
		} catch ( JMSException e) {e.printStackTrace();}
	}
	
	/**
	* Specify the photos the user is interested in by setting new tags
	*
	* @param tags the new user tags
	*/
	public void filter( Properties tags , FrameAffichage jeu )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener( jeu ) ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	/**
	* Specify the photos the user is interested in by setting new tags
	*
	* @param tags the new user tags
	*/
	public void filter2( Properties tags , FrameAffichage jeu )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection2.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic2 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener2( jeu ) ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public void filter3( Properties tags )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection3.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic3 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener3() ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public void filter4( Properties tags )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection4.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic4 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener4() ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public void filter5( Properties tags , FrameAffichage jeu )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection5.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic5 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener5( jeu ) ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public void filter6( Properties tags )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection6.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic6 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener6() ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public void filter7( Properties tags )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection7.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic7 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener7() ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public void filter8( Properties tags )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection8.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic8 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener8() ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public void filter9( Properties tags )
	{
		try
		{
			String tags_remis_en_string = "" ;
			for (Map.Entry<Object, Object> entry : tags.entrySet())
			{
				if ( tags_remis_en_string.equals( "" ) == false )
				{
					tags_remis_en_string += " OR " ;
				}
				tags_remis_en_string += entry.getKey() + "='" + entry.getValue() + "'" ;
			}
			TopicSession subscribe_session = this.topic_connection9.createTopicSession( false , Session.AUTO_ACKNOWLEDGE ) ;
			TopicSubscriber topic_subscriber = subscribe_session.createSubscriber( this.topic9 , tags_remis_en_string , false ) ;
			topic_subscriber.setMessageListener( new MyListener9() ) ;
		} catch ( JMSException e) {e.printStackTrace();}
		System.out.println( "Filter " + tags + " has been set" ) ;
	}
	
	public List<String> getPropertyList(Properties properties, String name) 
	{
	    List<String> result = new ArrayList<String>();
	    for (Map.Entry<Object, Object> entry : properties.entrySet())
	    {
	        if (/*((String)entry.getKey()).matches("^" + Pattern.quote(name) + "\\.\\d+$")*/true)
	        {
	            result.add((String) entry.getValue());
	        }
	    }
	    return result;
	}
	
	/**
	* Stop the application
	*/
	public void quit()
	{
		
	}
	
	/**
	* Transform a list of words in the form key=value to tags
	*
	* @param tokens a list of strings
	* @param startIdx the index of the first token in the given list that represent
	* a tag
	* @return the tags
	*/
	public Properties parseTags( String[] tokens , int startIdx )
	{
		Properties tags = new Properties() ;
		for ( int i = startIdx ; i < tokens.length ; i++ )
		{
			String[] token = tokens[i].split( "=" ) ;
			if ( token.length == 2 )
			{
				tags.put( token[0] , token[1] ) ;
			}
			else
			{
				System.err.println( "Cannot parse tag: \"" + tokens[i] + "\"" ) ;
			}
		}
		return tags ;
	}

	@Override
	public void close() throws IOException
	{
		try
		{
			this.context.close() ;
		} catch (Exception e) {}
		AdminModule.disconnect() ;
	}
}
