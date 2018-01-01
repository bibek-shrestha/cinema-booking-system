package com.cinema.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.domain.Booking;
import com.cinema.domain.BookingResourceAssembler;
import com.cinema.domain.Show;
import com.cinema.domain.ShowResourceAssembler;
import com.cinema.exceptions.AlreadyBookedException;
import com.cinema.exceptions.BookingDoesNotExistException;
import com.cinema.exceptions.BookingLargerThanAvailabilityException;
import com.cinema.exceptions.BookingNotAvailableException;
import com.cinema.exceptions.ShowDoesNotExistException;
import com.cinema.repository.BookingDao;
import com.cinema.repository.ShowDao;
import com.cinema.resource.BookingResource;
import com.cinema.resource.ShowResource;
import com.cinema.security.CustomUserDetails;

@RestController
@RequestMapping(value = "/")
public class ShowController {
	private final ShowResourceAssembler showResourceAssembler = new ShowResourceAssembler();
	private final BookingResourceAssembler bookingResourceAssembler = new BookingResourceAssembler();

	@Autowired
	private ShowDao showDao;
	@Autowired
	private BookingDao bookingDao;

	@RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Link> onLoad() {
		Link retUrl;
		retUrl = linkTo(ShowController.class).slash("shows").withRel("shows");
		return new ResponseEntity<Link>(retUrl, HttpStatus.OK);
	}

	/* Show creation */
	@RequestMapping(method = RequestMethod.POST, value = "/shows", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> createShow(@RequestBody Show show) {
		showDao.createShow(show);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/* Get list of all the shows */
	@RequestMapping(method = RequestMethod.GET, value = "/shows", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<ShowResource>> allShows() {
		List<Show> allShows = showDao.getAllShows();
		List<ShowResource> all = new ArrayList<ShowResource>(allShows.size());
		for (Show show : allShows) {
			ShowResource resource = this.showResourceAssembler.toResource(show);
			all.add(resource);
		}
		return new ResponseEntity<List<ShowResource>>(all, HttpStatus.OK);
	}

	/* Get list of all the shows */
	@RequestMapping(method = RequestMethod.GET, value = "/shows", params = "bookingStatus", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<ShowResource>> allShows(@RequestParam(required = false, value = "bookingStatus") String bookingStatus) {
		List<Show> allShows = showDao.getShows(bookingStatus);
		List<ShowResource> all = new ArrayList<ShowResource>(allShows.size());
		for (Show show : allShows) {
			ShowResource resource = this.showResourceAssembler.toResource(show);
			all.add(resource);
		}
		return new ResponseEntity<List<ShowResource>>(all, HttpStatus.OK);
	}

	/* Get the show details of specific show */
	@RequestMapping(method = RequestMethod.GET, value = "/shows/{showId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ShowResource> getShow(@PathVariable int showId) throws ShowDoesNotExistException {
		Show showDetails = showDao.getShow(showId);
		ShowResource resource = this.showResourceAssembler.toResource(showDetails);
		return new ResponseEntity<ShowResource>(resource, HttpStatus.OK);
	}

	/* Enable or Disable Booking */

	@RequestMapping(method = RequestMethod.PUT, value = "/shows/{showId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> changeBookingStatus(@PathVariable int showId, @RequestBody Show show) throws ShowDoesNotExistException {
		showDao.changeBookingStatus(showId, show);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/* Delete a show */
	@RequestMapping(method = RequestMethod.DELETE, value = "/shows/{showId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> deleteShow(@PathVariable int showId) throws ShowDoesNotExistException {
		showDao.deleteShow(showId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	/* Book a show */
	@RequestMapping(method = RequestMethod.POST, value = "/shows/{showId}/bookings", consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> bookShow(@PathVariable int showId, @RequestBody int seats) throws ShowDoesNotExistException, AlreadyBookedException, BookingLargerThanAvailabilityException, BookingNotAvailableException {
		String status = showDao.getBookingStatus(showId);
		if (status.equalsIgnoreCase("OPEN")) {
			CustomUserDetails activeUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int userId = activeUser.getUserId();
			if ((showDao.totalSeats(showId) - showDao.bookedSeats(showId)) >= seats) {
				bookingDao.bookShow(userId, showId, seats);
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				throw new BookingLargerThanAvailabilityException();
			}
		} else {
			throw new BookingNotAvailableException();
		}
	}

	/* Get all the bookings for a particular show by a user */
	@RequestMapping(value = "/shows/{showId}/bookings", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<BookingResource>> getBookings(@PathVariable int showId) throws BookingDoesNotExistException {
		CustomUserDetails activeUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = activeUser.getUserId();
		List<Booking> bookingList = bookingDao.getBookings(userId, showId);
		List<BookingResource> allBookings = new ArrayList<BookingResource>(bookingList.size());
		for (Booking booking : bookingList) {
			BookingResource resource = this.bookingResourceAssembler.toResource(booking);
			allBookings.add(resource);
		}

		return new ResponseEntity<List<BookingResource>>(allBookings, HttpStatus.OK);
	}

	/* Get details about particular booking */
	@RequestMapping(value = "/shows/{showId}/bookings/{bookingId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BookingResource> getBookings(@PathVariable int showId, @PathVariable int bookingId) throws BookingDoesNotExistException {
		CustomUserDetails activeUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = activeUser.getUserId();
		Booking booking = bookingDao.getBookings(userId, showId, bookingId);
		BookingResource resource = this.bookingResourceAssembler.toResource(booking);
		return new ResponseEntity<BookingResource>(resource, HttpStatus.OK);
	}

	/* Cancel specific booking for a show */
	@RequestMapping(method = RequestMethod.DELETE, value = "/shows/{showId}/bookings/{bookingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> cancelBooking(@PathVariable int showId, @PathVariable int bookingId) throws ShowDoesNotExistException, BookingDoesNotExistException {
		CustomUserDetails activeUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = activeUser.getUserId();
		bookingDao.cancelBooking(userId, showId, bookingId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	@ExceptionHandler({ShowDoesNotExistException.class, BookingDoesNotExistException.class})
	ResponseEntity<String> handleNotFound(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({AlreadyBookedException.class})
	ResponseEntity<String> handleConflict(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler({BookingLargerThanAvailabilityException.class})
	ResponseEntity<String> badRequest(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler({BookingNotAvailableException.class})
	ResponseEntity<String> serviceUnavailable(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}
}
