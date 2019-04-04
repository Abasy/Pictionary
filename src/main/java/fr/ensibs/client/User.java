package fr.ensibs.client;

import java.awt.Color;

import net.jini.core.entry.Entry;

public class User implements Entry {

	private String name;
	private Color colorUser;
	private Integer score;
	
	public User() {
		this.name = null;
		this.colorUser = null;
	}

}
