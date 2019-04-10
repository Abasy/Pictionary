package fr.ensibs.client;

import fr.ensibs.joram.JoramAdmin;
import fr.ensibs.model.MessageObject;

import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.objectweb.joram.client.jms.admin.AdminException;

public class ClientJMS {
	Context context = null;
	ConnectionFactory factory = null;
	//Destination destination = null;
	
	String topicname = "PICTIONARY-TOPIC";
	
	public ClientJMS(String host, int port) throws NamingException, ConnectException, UnknownHostException, AdminException {
		/********************************/
		/** Connecting to JORAM server:**/
		/********************************/
		// Initialize the JNDI context
		System.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
		System.setProperty("java.naming.factory.host", host);
		System.setProperty("java.naming.factory.port", Integer.toString(port));
		this.context = new InitialContext();
		this.factory = (ConnectionFactory) this.context.lookup("ConnectionFactory");
		//this.destination = (Destination) this.context.lookup(topicname);
        System.out.println("A joram server has been discovered");
	}
	 /**
	  * Print a usage message and exit. Used when the standalone
	  * application is started with wrong command line arguments
	  */
	private static void usage() {
		System.out.println("Usage: java -jar target/pictionary-1.0.jar <server_host> <server_port>");
	    System.exit(-1);
    }
	
	/**
	  * Print a usage message and exit. Used when the standalone
	  * application is started with wrong command line arguments
	  */
	public static void main(String[] args) throws Exception, NamingException, JMSException {
		/*Arguments*/
		String server_host = args[0];
		String server_port = args[1];
		
		ClientJMS jmsclient = new ClientJMS(server_host, Integer.parseInt(server_port));
		jmsclient.context.lookup("ConnectionFactory");
		jmsclient.create(server_host, Integer.parseInt(server_port));
		
		if(args[2].equals("send")) {
			//jmsclient.send("Nadjim","colibri");
			jmsclient.send("Nadjim", 12, 13);
		}else if(args[2].equals("recv")){
			jmsclient.recv();
		}else {
			usage();
		}
	}
	
	public void create(String host, int port) {
		try {
			/*************************************/
			/******Create a topic******/
			/*************************************/
			JoramAdmin admin = new JoramAdmin(host, port);
	        admin.createTopic(this.topicname);
	        admin.close();
		}catch(Exception e) {
	        System.err.println("Error setting destination: " + e.toString());
	        System.exit(1);
		}
	}
	
	public void send(String username, String message) throws JMSException, NamingException {
		/*******************************/
		/*****JMS Message Producer******/
		/*******************************/
		try {
			System.out.println("Mon topic name :"+this.topicname);
			ConnectionFactory factory = (ConnectionFactory) this.context.lookup("ConnectionFactory");
			JMSContext jmscontext = factory.createContext();
			Destination destination = (Destination) this.context.lookup(this.topicname);
			   
			String word = message;
			MessageObject messageObject = null;
			messageObject = new MessageObject(word);
			
			JMSProducer producer = jmscontext.createProducer();
			ObjectMessage jms_om= jmscontext.createObjectMessage(messageObject);
			jms_om.setObject(messageObject);
			jms_om.setStringProperty("username", username);
			
			producer.send(destination, jms_om);
		}catch(Exception e) {
	        System.err.println("Error setting destination: " + e.toString());
	        System.exit(1);
		}
	}
	
	public void send(String username, int x, int y) throws NamingException, JMSException {
		/*******************************/
		/*****JMS Message Producer******/
		/*******************************/
		try {
			System.out.println("Mon topic name :"+this.topicname);
			ConnectionFactory factory = (ConnectionFactory) this.context.lookup("ConnectionFactory");
			JMSContext jmscontext = factory.createContext();
			Destination destination = (Destination) this.context.lookup(this.topicname);
			
			String msg_text=x+" "+y;//enoie d'une coordonées en x et y
			MessageObject messageObject = null;
			messageObject = new MessageObject(msg_text);
			
			System.out.println("Mon message à envoyer : "+messageObject.getMessage());
			
			JMSProducer producer = jmscontext.createProducer();
			ObjectMessage jms_om= jmscontext.createObjectMessage(messageObject);
			jms_om.setObject(messageObject);
			jms_om.setStringProperty("username", username);
			
			System.out.println("Message envoyer : "+jms_om.getBody(MessageObject.class).getMessage());
			
			producer.send(destination, jms_om);
		}catch(Exception e) {
	        System.err.println("Error setting destination: " + e.toString());
	        System.exit(1);
		}
	}
	
	public void recv() throws JMSException, NamingException {
		/*******************************/
		/*****JMS Message Consummer******/
		/*******************************/
		try {
			System.out.println("Mon topic name :"+this.topicname);
			ConnectionFactory factory = (ConnectionFactory) this.context.lookup("ConnectionFactory");
			JMSContext jmscontext = factory.createContext();
			Destination destination = (Destination) this.context.lookup(this.topicname);
			
			JMSConsumer jmsConsumer = jmscontext.createConsumer(destination);
			
			ObjectMessage m = (ObjectMessage) jmsConsumer.receive();
			m.acknowledge();
			
			//System.out.println("Mon message reçu : "+m.getBody(MessageObject.class).getMessage());
			
			MessageObject messageObject = m.getBody(MessageObject.class);
			String user = m.getStringProperty("username");
			
			System.out.println(messageObject.getMessage()+ " : "+ user);
			//System.out.println(messageObject.getMessage());*/
		}catch(Exception e) {
	        System.err.println("Error setting destination: " + e.toString());
	        System.exit(1);
		}
	}
}
