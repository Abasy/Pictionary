package fr.ensibs.model;

import java.io.Serializable;

public class MessageObject implements Serializable {
	private String message;
	
	public MessageObject(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
