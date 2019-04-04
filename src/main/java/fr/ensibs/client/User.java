package fr.ensibs.client;

import java.awt.Color;

import net.jini.core.entry.Entry;

public class User implements Entry {
	public String name;
	public Color colorUser;
	public Integer score;
	public Boolean isDrawer;
	public Boolean isReady;
	
	public User() {
		this.name = null;
		this.colorUser = null;
		this.score = null;
		this.isDrawer = null;
		this.isReady = null;
	}

	public User(String name, Color colorUser, Integer score, Boolean isDrawer, Boolean isReady) {
		this.name = name;
		this.colorUser = colorUser;
		this.score = score;
		this.isDrawer = isDrawer;
		this.isReady = isReady;
		
	}
	
	public String toString() {
		return this.name+" : "+ this.score;
	}
}
