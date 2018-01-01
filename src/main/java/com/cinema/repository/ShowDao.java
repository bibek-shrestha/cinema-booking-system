package com.cinema.repository;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.cinema.domain.Show;
import com.cinema.exceptions.ShowDoesNotExistException;

public interface ShowDao {
	public String getBookingStatus(int showId) throws ShowDoesNotExistException;

	@Secured({"ROLE_ADMIN"})
	public void createShow(Show show);

	public List<Show> getAllShows();
	public Show getShow(int showId) throws ShowDoesNotExistException;
	public List<Show> getShows(String bookingStatus);

	@Secured({"ROLE_ADMIN"})
	public void deleteShow(int showId) throws ShowDoesNotExistException;
	@Secured({"ROLE_ADMIN"})
	public void changeBookingStatus(int showId,Show show) throws ShowDoesNotExistException;
	public void changeBookingStatus(int showId, String status);
	public int bookedSeats(int showId);
	public int totalSeats(int showId);
	public void updateBookedSeats(int showId, int seats, boolean increase);
}
