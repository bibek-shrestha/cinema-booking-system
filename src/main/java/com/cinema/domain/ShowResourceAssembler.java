package com.cinema.domain;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import com.cinema.resource.ShowResource;
import com.cinema.web.ShowController;

public class ShowResourceAssembler extends ResourceAssemblerSupport<Show, ShowResource> {

	public ShowResourceAssembler() {
		super(ShowController.class, ShowResource.class);
	}
	public ShowResource toResource(Show show) {
		ShowResource resource = instantiateResource(show);
		resource.showName = show.getShowName();
		resource.showDate = show.getShowDate();
		resource.showTime = show.getShowTime();
		resource.totalSeats = show.getTotalSeats();
		resource.bookedSeats = show.getBookedSeats();
		resource.bookingStatus = show.getBookingStatus();
		resource.add(linkTo(ShowController.class).slash("shows").slash(show).withSelfRel());
		resource.add(linkTo(ShowController.class).slash("shows").slash(show).slash("bookings").withRel("bookings"));
		return resource;
		
	}
}
