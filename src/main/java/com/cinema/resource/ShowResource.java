package com.cinema.resource;

import org.springframework.hateoas.ResourceSupport;

public class ShowResource extends ResourceSupport{
	public String showName;
	public String showDate;
	public String showTime;
	public int totalSeats;
	public int bookedSeats;
	public String bookingStatus;
}
