package com.cinema.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.cinema.exceptions.BookingDoesNotExistException;
import com.cinema.resource.BookingResource;
import com.cinema.web.ShowController;

public class BookingResourceAssembler{
	
	public BookingResource toResource(Booking booking) throws BookingDoesNotExistException {
		BookingResource resource = new BookingResource();
		resource.showId = booking.getShowId();
		resource.seats = booking.getSeats();
		resource.status = booking.getStatus();
		resource.add(linkTo(methodOn(ShowController.class).getBookings(booking.getShowId(),booking.getId())).withSelfRel());
		return resource;
	}

}
