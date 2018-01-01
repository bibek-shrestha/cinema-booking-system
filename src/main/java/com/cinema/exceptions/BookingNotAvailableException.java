package com.cinema.exceptions;

public class BookingNotAvailableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE_FORMAT = "Booking for this show is not available. Please try again later";

	public BookingNotAvailableException() {
		super(MESSAGE_FORMAT);
	}

}
