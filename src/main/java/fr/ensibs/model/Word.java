package fr.ensibs.model;

import net.jini.core.entry.Entry;

/**
 * A Word object to save a user's word.
 * A Word object implement an Entry.
 * @author Nadjim ABASY, Bastien LUCK and Maxence Foucher
 *
 */
public class Word implements Entry {
	/**
	 * represent a word
	 */
	public String value;
	
	/**
	 * A word constructor without parameters. That initialize the variable at null.
	 */
	public Word() {
		this.value = null;
	}

	/**
	 * A word constructor with parameters. That initialize the variable for value.
	 * @param value represent a word
	 */
	public Word(String value) {
		this.value = value;
	}
}
