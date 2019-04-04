package fr.ensibs.model;

import net.jini.core.entry.Entry;

public class Word implements Entry {
	public String value;
	
	public Word() {
		this.value = null;
	}

	public Word(String value) {
		this.value = value;
	}
}
