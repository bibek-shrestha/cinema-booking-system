package com.cinema.domain;

import org.springframework.hateoas.Identifiable;

public class Booking implements Identifiable<Integer>{
	private Integer id;
	private int userId;
	private int showId;
	private int seats;
	private String status;
	
	public void setId(Integer id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getShowId() {
		return showId;
	}
	public void setShowId(int showId) {
		this.showId = showId;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getId() {
		return this.id;
	}

}
