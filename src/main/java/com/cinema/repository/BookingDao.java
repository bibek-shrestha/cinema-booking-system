package com.cinema.repository;

import java.util.List;

import com.cinema.domain.Booking;
import com.cinema.exceptions.AlreadyBookedException;
import com.cinema.exceptions.BookingDoesNotExistException;
import com.cinema.exceptions.ShowDoesNotExistException;

public interface BookingDao {
	public List<Booking> getBookings(int userId, int showId);
	public Booking getBookings(int userId, int showId,int bookingId) throws BookingDoesNotExistException;
	public void bookShow(int userId, int showId, int seats) throws ShowDoesNotExistException, AlreadyBookedException;
	public void cancelBooking(int userId, int showId, int bookingId) throws BookingDoesNotExistException;
	public boolean checkIfBooked(int userId, int showId);
}
