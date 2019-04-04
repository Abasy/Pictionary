package fr.ensibs.model;

import fr.ensibs.joram.JoramAdmin;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClientJMS {	
	 /**
	  * Print a usage message and exit. Used when the standalone
	  * application is started with wrong command line arguments
	  */
	private static void usage() {
		System.out.println("Usage: java -jar target/jms-client-1.0.jar <server_host> <server_port> <queue_name> <options>");
	    System.out.println("with options:");
	    System.out.println("create 				[no output]");
	    System.out.println("send <text_message> [no output]");
	    System.out.println("recv 				[output: the text of the first message in the mailbox]");
	    System.exit(-1);
    }
	
	/**
	  * Print a usage message and exit. Used when the standalone
	  * application is started with wrong command line arguments
	  */
	/*private static void usageDestination(String destName) {
		System.out.println("Destination type is " + destName);
		System.err.println("\nArgument must be \"queue\" or " + "\"topic\"");
	    System.exit(-1);
	}*/
	
	public static void main(String[] args) throws NamingException, JMSException {
		Context context = null;
		ConnectionFactory factory = null;
		Destination destination = null;
		
		JMSContext jmsContext = null;
		JMSConsumer jmsConsumer = null;
		
		JoramAdmin admin = null;
		
		/*Arguments*/
		String server_host = args[0];
		String server_port = args[1];
		String destName = args[2]; //Queue or Topic
		String option = args[3];//create, send<messages> or recv
		
		/*************************************************/
		/** Retrieves and verifies command-line arguments**/
		/** that specify the destination type			**/
		/** and the number of arguments					**/
		/*************************************************/
		if (args.length < 4 || args[0].equals("-h")) {
			usage();
		}else if(args.length >= 4) {
			System.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
			System.setProperty("java.naming.factory.host", server_host);
			System.setProperty("java.naming.factory.port", server_port);
			
			context = new InitialContext();
			factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			jmsContext = factory.createContext();
			/***********************************/
			/**Create a JMS Administred object**/
			/***********************************/
			if(option.contains("create")) {
				try {
					/*************************************/
					/******Create a Queue or a topic******/
					/*************************************/
					admin = new JoramAdmin(server_host, Integer.parseInt(server_port));
			        admin.createQueue(destName);
			        
				}catch(Exception e) {
			        System.err.println("Error setting destination: " + e.toString());
			        System.exit(1);
				}finally {
					jmsContext.close();
					admin.close();
				}
			}else if(option.contains("send")) {
				/*******************************/
				/*****JMS Message Producer******/
				/*******************************/
				try {
					/*Create a context for the connection and session*/
					//jmsContext = factory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
					
					destination = (Destination) context.lookup(destName);
					jmsContext.start();
					
					String msg_text="";
					MessageObj myMessage = null;
					for(int i=4;i<args.length;i++) {
						msg_text+=args[i];
					}
					myMessage = new MessageObj(msg_text);
					JMSProducer producer = jmsContext.createProducer();
					producer.send(destination, jmsContext.createObjectMessage(myMessage));
				}finally {
					jmsContext.close();
				}
			}else if(option.contains("recv")){
				/*******************************/
				/*****JMS Message Consummer******/
				/*******************************/
				try {
					destination = (Destination) context.lookup(destName);
					jmsContext.start();
					jmsConsumer = jmsContext.createConsumer(destination);
					Message m = jmsConsumer.receive();
					m.acknowledge();
					MessageObj message = m.getBody(MessageObj.class);
					//System.out.println(message.getMessage());
				}finally {
					jmsConsumer.close();
					jmsContext.close();
				}
			}
		}
	}

}
