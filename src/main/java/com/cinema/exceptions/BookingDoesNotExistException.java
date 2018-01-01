package com.cinema.exceptions;

public class BookingDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7265469104687455391L;
	private static final String MESSAGE_FORMAT = "Booking %d for show %d does not exist.";
	private static final String MSG_FORMAT = "No bookings are available for show %d";
	
	public BookingDoesNotExistException(int showId, int bookingId){
		super(String.format(MESSAGE_FORMAT, bookingId, showId));
	}
	public BookingDoesNotExistException(int showId){
		super(String.format(MSG_FORMAT, showId));
	}
}
