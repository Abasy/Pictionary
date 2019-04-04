package fr.ensibs.model;

import java.io.Serializable;

public class MessageObj implements Serializable {
	private String message;
	
	public MessageObj(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
