package com.cinema.exceptions;

public class AlreadyBookedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT="You have already booked seats for this show. To make a new booking cancel previous booking";
	
	public AlreadyBookedException(){
		super(MESSAGE_FORMAT);
	}
}
