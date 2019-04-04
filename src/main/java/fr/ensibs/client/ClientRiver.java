package fr.ensibs.client;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;

import fr.ensibs.model.User;
import fr.ensibs.model.Word;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

public class ClientRiver {
	
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

	public void addUser(String name, Color color) throws RemoteException, TransactionException {
		User user = new User();
		user.name = name;
		user.colorUser = color;
		user.score = 0;
		user.isDrawer = false;
		user.isReady = false;
		this.space.write(user, null, 60*60*1000);
		System.out.println("Add Word : ["+user.name+":"+user.colorUser+":"+user.score+":"+user.isDrawer+":"+user.isReady+"]");
	}
	
	public void addWord(String value) throws RemoteException, TransactionException {
		Word word = new Word();
		word.value = value;
		this.space.write(word, null, 60*60*1000);
		System.out.println("Add Word : [Word: "+word.value+"]");
	}
	
	public User readUser(String name) throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		User templateUser = new User();
		templateUser.name = name;
		User userResult = null;
		
		if(this.space.readIfExists(templateUser, null, Long.MAX_VALUE) != null) {
			userResult = (User) this.space.read(templateUser, null, Long.MAX_VALUE);
		}
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
	
	public Word takeWord() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		Word templateWord = new Word();
		Word wordResult = null;
		
		if((wordResult = (Word) this.space.takeIfExists(templateWord, null, Long.MAX_VALUE)) != null) {}
		
		System.out.println("Take Word : ["+wordResult.value+"]");
		return wordResult;
	}
	
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
