package fr.ensibs.client;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;

import fr.ensibs.model.User;
import fr.ensibs.model.Word;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

/**
 * This client application allow to create a javaspace and manage
 * User and Word Objects.
 * @author Nadjim ABASY, Bastien LUCK and Maxence Foucher
 *
 */
public class ClientRiver {
	
	/**
	 * A javaSpace that contain all Objets User and Word
	 */
	private JavaSpace space;
	
	/**
	 * The ClientRiver constructor allow to be connected to the river server
	 * @param space the javaspace that contain all objects User and Word
	 */
	public ClientRiver(JavaSpace space) {
		if(space != null) {
			System.out.println("A JavaSpace has been discovered.");
			System.out.println("Add users and words into the space...");
			this.space = space;
		}else {
			System.out.println("No service found");
			System.exit(-1);
		}
	}
	
	/**
	 * Initialize the javaspace by removing all objects in it.
	 * @throws RemoteException
	 * @throws UnusableEntryException
	 * @throws TransactionException
	 * @throws InterruptedException
	 */
	public void initSpace() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		User templateUser = new User();
		Word templateWord = new Word();
		
		while(this.space.readIfExists(templateUser, null, Long.MAX_VALUE) != null) {
			this.space.take(templateUser, null, Long.MAX_VALUE);
		}
		
		while(this.space.readIfExists(templateWord, null, Long.MAX_VALUE) != null) {
			this.space.take(templateWord, null, Long.MAX_VALUE);
		}
		
		System.out.println("\n javaspace empty.");
	}

	/**
	 * Create a user object and add it into the javaspace
	 * @param name the name of the user
	 * @param color the color that represent a user
	 * @throws RemoteException
	 * @throws TransactionException
	 */
	public void addUser(String name, Color color) throws RemoteException, TransactionException {
		/*****************************/
		/** Create a new user object**/
		/*****************************/
		User user = new User();
		user.name = name;
		user.colorUser = color;
		user.score = 0;
		user.isDrawer = false;
		user.isReady = false;
		
		/************************************/
		/** add the user into the javaspace**/
		/************************************/
		this.space.write(user, null, 60*60*1000);//The object exist during one hour into the javaspace. It is destroyed after that.
		System.out.println("Add Word : ["+user.name+":"+user.colorUser+":"+user.score+":"+user.isDrawer+":"+user.isReady+"]");
	}
	
	/**
	 * Create a word object and add it into the javaspace
	 * @param value the value of the word
	 * @throws RemoteException
	 * @throws TransactionException
	 */
	public void addWord(String value) throws RemoteException, TransactionException {
		/*****************************/
		/** Create a new word object**/
		/*****************************/
		Word word = new Word();
		word.value = value;
		
		/************************************/
		/** add the word into the javaspace**/
		/************************************/
		this.space.write(word, null, 60*60*1000);//The object exist during one hour into the javaspace. It is destroyed after that.
		System.out.println("Add Word : [Word: "+word.value+"]");
	}
	
	/**
	 * Reads a user from javaspace using a user's name
	 * @param name the name of the user
	 * @return a user object else null
	 * @throws RemoteException
	 * @throws UnusableEntryException
	 * @throws TransactionException
	 * @throws InterruptedException
	 */
	public User readUser(String name) throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		User templateUser = new User();
		templateUser.name = name;
		User userResult = null;
		
		if((userResult = (User) this.space.readIfExists(templateUser, null, Long.MAX_VALUE)) != null) {}
		System.out.println("Read User : ["+userResult.name+":"+userResult.colorUser+":"+userResult.score+":"+userResult.isDrawer+":"+userResult.isReady+"]");
		return userResult;
	}
	
	/*public User takeUser() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		User templateUser = new User();
		User userResult = null;
		
		if(this.space.readIfExists(templateUser, null, Long.MAX_VALUE) != null) {
			userResult = (User) this.space.take(templateUser, null, Long.MAX_VALUE);
		}
		
		return userResult;
	}*/
	
	/**
	 * Take a random word into the javaspace
	 * @return a word object else null
	 * @throws RemoteException
	 * @throws UnusableEntryException
	 * @throws TransactionException
	 * @throws InterruptedException
	 */
	public Word takeWord() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		Word templateWord = new Word();
		Word wordResult = null;
		
		if((wordResult = (Word) this.space.takeIfExists(templateWord, null, Long.MAX_VALUE)) != null) {}
		
		System.out.println("Take Word : ["+wordResult.value+"]");
		return wordResult;
	}
	
	/**
	 * Take all the users from the javaspace 
	 * @return a list of all users contained from the javaspace else null
	 * @throws RemoteException
	 * @throws UnusableEntryException
	 * @throws TransactionException
	 * @throws InterruptedException
	 */
	public ArrayList<User> takeAllUser() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException{
		ArrayList<User> users = new ArrayList<User>();
		User templateUser = new User();
		
		User myUser = null;
		
		while((myUser = (User) this.space.takeIfExists(templateUser, null, Long.MAX_VALUE)) != null) {
			users.add(myUser);
		}
		System.out.println("Take All User : [number of user: "+users.size()+"]");
		return users;
	}
	
	/*public void test() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		initSpace();
		
		addUser("Ollen", Color.BLACK);
		addUser("Bastien", Color.BLUE);
		addUser("Maxence", Color.CYAN);
		
		addWord("Cheval");
		addWord("Voiture");
		addWord("Piéton");
		
		readUser("Ollen");
		readUser("Bastien");
		readUser("Maxence");
		
		takeWord();
		
		takeAllUser();
		
		initSpace();
	}*/
}
