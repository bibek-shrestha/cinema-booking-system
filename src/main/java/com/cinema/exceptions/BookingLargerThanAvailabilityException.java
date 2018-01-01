package com.cinema.exceptions;

public class BookingLargerThanAvailabilityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT = "You are trying to book seats more than the available seats";

	public BookingLargerThanAvailabilityException() {
		super(MESSAGE_FORMAT);
	}
}
