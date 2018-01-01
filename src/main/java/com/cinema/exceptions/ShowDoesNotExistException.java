package com.cinema.exceptions;

public class ShowDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT = "Show %d doesnot exist";
	
	public ShowDoesNotExistException(int showId){
		super(String.format(MESSAGE_FORMAT, showId));
	}

}
