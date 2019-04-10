package fr.ensibs.model;

import java.awt.Color;

import net.jini.core.entry.Entry;

/**
 * A user object with a name, a color, a score,
 * a boolean that tell if the user is the drawer
 * and a boolean that tell if the user who draw is ready to draw.
 * A user implement an Entry object.
 * @author Nadjim ABASY, Bastien LUCK and Maxence Foucher
 *
 */
public class User implements Entry {
	/**
	 * the name of the user
	 */
	public String name;
	
	/**
	 * the color that represent a user, the same color is used for the draw
	 */
	public Color colorUser;
	
	/**
	 * the score of the current user
	 */
	public Integer score;
	
	/**
	 * a boolean that tell if the user is the drawer
	 */
	public Boolean isDrawer;
	
	/**
	 * a boolean that tell if the user who draw is ready to draw
	 */
	public Boolean isReady;
	
	public String salon;
	
	/**
	 * A user constructor without parameters. That initialize all variables at null.
	 */
	public User() {
		this.name = null;
		this.colorUser = null;
		this.score = null;
		this.isDrawer = null;
		this.isReady = null;
		this.salon = null;
	}
	
	public User( String name )
	{
		this.name = name;
		this.colorUser = Color.BLACK;
		this.score = 0;
		this.isDrawer = false;
		this.isReady = false;
	}

	/**
	 * A user constructor with parameters. That initialize all variables for name, colorUser, score, isDrawer and isReady.
	 * @param name the name of the user
	 * @param colorUser the color that represent a user, the same color is used for the draw
	 * @param score the score of the current user
	 * @param isDrawer a boolean that tell if the user is the drawer
	 * @param isReady a boolean that tell if the user who draw is ready to draw
	 */
	public User(String name, Color colorUser, Integer score, Boolean isDrawer, Boolean isReady, String salon) {
		this.name = name;
		this.colorUser = colorUser;
		this.score = score;
		this.isDrawer = isDrawer;
		this.isReady = isReady;
		this.salon = salon;
		
	}
	
	/**
	 * return the name of the user and his score
	 */
	public String toString() {
		return this.name+" : "+ this.score;
	}
}
