package com.att.developer;

//TODO: Build this out to handle other error cases in various methods
public class AttException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8412497032329552220L; //Recommended by eclipse

	public AttException(String message) {
        super(message);
    }
	
	public AttException(Exception error) {
		super(error);
	}
}
