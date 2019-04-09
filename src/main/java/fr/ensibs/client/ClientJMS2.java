package fr.ensibs.client;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.ensibs.joram.JoramAdmin;
import fr.ensibs.model.MessageObject;
import fr.ensibs.model.User;

/**
* Messenger used to send/receive messages for the photo sharing application
*/
public class ClientJMS2
{

  /**
  * the name of the topic used to exchange photos
  */
  private static final String TOPIC_NAME = "PICTIONARY";

  /**
  * the JMS connection
  */
  private Connection connection;

  /**
  * the JMS session, used to create and send messages
  */
  private Session senderSession;

  /**
  * the topic used to exchange photos
  */
  private Destination destination;

  private String topicName;
  
  /**
  * the message producer used to send photos to the shared topic
  */
  private MessageProducer sender;

  /**
  * a message receiver initialized when the {@link #subscribe} method is called
  */
  private MessageConsumer receiver;

  /**
  * the listener invoked when a message is received
  */
  private MessageListener listener;

  /**
  * the current user
  */
  private User user;


  /**
  * a queue for the current user used to receive requests and files contents
  */
  private Destination topic;

  /**
  * the JNDI context
  */
  private Context context;

  /**
  * the administration tool
  */
  private JoramAdmin admin;

  /**
  * Constructor: initialize the JMS connection, session and message producer, and
  * start the connection
  *
  * @param host the JMS server host name
  * @param port the JMS server port number
  */
  public ClientJMS2(String host, int port) throws Exception
  {
    // initialize the JMS context properties
    System.setProperty("java.naming.factory.initial", "fr.dyade.aaa.jndi2.client.NamingContextFactory");
    System.setProperty("java.naming.factory.host", host);
    System.setProperty("java.naming.factory.port", Integer.toString(port));
    this.context = new InitialContext();

    // create the JMS connection and session
    ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
    this.connection = factory.createConnection();
    this.senderSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    // create a message producer
    this.destination = (Destination) context.lookup(TOPIC_NAME);
    this.sender = this.senderSession.createProducer(destination);

    // start the JMS connection
    this.connection.start();

    // create a queue to receive requests and files contents
    //this.makeQueue(host, port);
    this.makeTopic(host, port);
  }

  public void send(String username, String message) throws JMSException, NamingException {
	  /*******************************/
	  /*****JMS Message Producer******/
	  /*******************************/
		try {
			TextMessage txt_msg = this.senderSession.createTextMessage(message);
			txt_msg.setStringProperty("username", username);
			txt_msg.setJMSType("word");
			txt_msg.setJMSReplyTo(topic);
			this.sender.send(txt_msg);
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
			String message=x+" "+y;//enoie d'une coordonées en x et y
			TextMessage txt_msg = this.senderSession.createTextMessage(message);
			txt_msg.setStringProperty("username", username);
			txt_msg.setJMSType("word");
			txt_msg.setJMSReplyTo(topic);
			this.sender.send(txt_msg);
		}catch(Exception e) {
	        System.err.println("Error setting destination: " + e.toString());
	        System.exit(1);
		}
	}
  /**
  * Create a receiver that subscribes to the shared topic to receive messages
  * having the given tags as properties
  *
  * @param tags the tags that interest the receiver
  */
  public void subscribe(Properties tags) throws Exception
  {
    if (this.receiver != null) {
      this.receiver.close();
    }
    Session consumerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    //String selector = makeSelector(tags);
    //if (selector != null) {
      //this.receiver = consumerSession.createConsumer(destination, selector);
    //} else {
      this.receiver = consumerSession.createConsumer(destination);
    //}
    this.receiver.setMessageListener(this.listener);
  }

  /*
  * Close the current JMS connection
  */
  public void close()
  {
    try {
      this.admin.deleteDestination(this.topicName);
      this.admin.close();
      this.connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
  * Create a queue to receive requests and files contents
  *
  * @param host the JMS server host name
  * @param port the JMS server port number
  */
  private void makeQueue(String host, int port) throws Exception
  {
    this.topicName = makeQueueName();
    this.admin = new JoramAdmin(host, port);
    this.admin.createQueue(topicName);
    this.topic = (Destination) context.lookup(topicName);
    if (this.topic != null) {
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageConsumer receiver = session.createConsumer(this.topic);
      receiver.setMessageListener(this.listener);
    }
  }

  /**
  * Method invoked when a message containing a photo description has been
  * received through JMS
  *
  * @param message the JMS received message
  */
  public void onMessageReceived(TextMessage message)
  {
    try {
      Destination destination = message.getJMSReplyTo();
      String filename = message.getText();
      switch (message.getJMSType()) {
        case "request":
        File file = new File(this.user.getDirectory(), filename);
        if (file.isFile()) {
          this.send(file, destination);
        }
        break;
        case "word":
        this.sendRequest(filename, destination);
        break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void sendRequest(String filename, Destination destination) throws Exception
  {
    MessageProducer sender = null;
    try {
      System.out.println("5 THREAD " + Thread.currentThread());
      sender = this.session.createProducer(destination);
      TextMessage message = this.session.createTextMessage(filename);
      message.setJMSType("word");
      message.setJMSReplyTo(topic);
      sender.send(message);
    } finally {
      if (sender != null) {
        sender.close();
      }
    }
  }
  /**
  * Send a file content to the given JMS destination
  *
  * @param file the file to be sent
  * @param destination a JMS destination
  */
  private void send(File file, Destination destination) throws Exception
  {
    MessageProducer sender = null;
    try {
      System.out.println("6 THREAD " + Thread.currentThread());
      sender = this.session.createProducer(destination);
      BytesMessage message = this.session.createBytesMessage();
      try (FileInputStream in = new FileInputStream(file)) {
        byte[] data = new byte[in.available()];
        in.read(data);
        message.writeBytes(data);
      }
      message.setStringProperty("owner", this.user.getName());
      message.setStringProperty("filename", file.getName());
      sender.send(message);
    } finally {
      if (sender != null) {
        sender.close();
      }
    }
  }

  /**
  * Create a random queue name
  */
  private String makeQueueName()
  {
    Random random = new Random(System.currentTimeMillis());
    String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String name = "";
    for (int i=0; i<10; i++) {
      int idx = random.nextInt(alpha.length());
      name += alpha.charAt(idx);
    }
    return name;
  }

  /**
  * Create a selector string from the given tags
  *
  * @param tags properties used to compose the selector
  */
  private String makeSelector(Properties tags)
  {
    Iterator<String> it = tags.stringPropertyNames().iterator();
    if (it.hasNext()) {
      String key = it.next();
      String selector = key + " = '" + tags.getProperty(key) + "'";
      while (it.hasNext()) {
        key = it.next();
        selector += " OR " + key + " = '" + tags.getProperty(key) + "'";
      }
      return selector;
    }
    return null;
  }
}
