package com.cinema.exceptions;

public class UserDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT = "Username :%s: doesnot exist";
	
	public UserDoesNotExistException(String userName){
		super(String.format(MESSAGE_FORMAT,userName));
	}
}
