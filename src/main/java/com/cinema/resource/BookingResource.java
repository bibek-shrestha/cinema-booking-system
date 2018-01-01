package com.cinema.resource;

import org.springframework.hateoas.ResourceSupport;

public class BookingResource extends ResourceSupport {
	public int showId;
	public int seats;
	public String status;
}
