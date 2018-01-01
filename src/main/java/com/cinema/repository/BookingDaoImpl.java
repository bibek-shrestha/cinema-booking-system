package com.cinema.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cinema.domain.Booking;
import com.cinema.exceptions.AlreadyBookedException;
import com.cinema.exceptions.BookingDoesNotExistException;
import com.cinema.exceptions.ShowDoesNotExistException;

public class BookingDaoImpl implements BookingDao {
	@Autowired
	ShowDao showDao;

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Booking> getBookings(int userId, int showId) {
		String sql = "SELECT * FROM booking_tbl WHERE user_id= ? AND show_id=?";
		return this.jdbcTemplate.query(sql, new Object[]{new Integer(userId), new Integer(showId)}, new BookingMapper());
	}

	public Booking getBookings(int userId, int showId, int bookingId) throws BookingDoesNotExistException {
		String sql = "SELECT * FROM booking_tbl WHERE user_id= ? AND show_id=? AND booking_id=? AND status ='BOOKED'";
		try {
			return this.jdbcTemplate.queryForObject(sql, new Object[]{new Integer(userId), new Integer(showId), new Integer(bookingId)}, new BookingMapper());
		} catch (Exception e) {
			throw new BookingDoesNotExistException(showId, bookingId);
		}
	}

	public void bookShow(int userId, int showId, int seats) throws ShowDoesNotExistException, AlreadyBookedException {
		if (checkIfBooked(userId, showId)) {
			throw new AlreadyBookedException();
		} else {
			String sql = "INSERT INTO booking_tbl (user_id, show_id, seats) VALUES (?,?,?)";
			try {
				this.jdbcTemplate.update(sql, new Object[]{new Integer(userId), new Integer(showId), new Integer(seats)});
				showDao.updateBookedSeats(showId, seats, true);
			} catch (Exception e) {
				throw new ShowDoesNotExistException(showId);
			}
		}
	}

	public void cancelBooking(int userId, int showId, int bookingId) throws BookingDoesNotExistException {
		int seats = getCancelledSeats(userId, showId, bookingId);
		String sql = "UPDATE booking_tbl SET status='CANCELED' WHERE user_id=? AND show_id=? AND booking_id=?";
		this.jdbcTemplate.update(sql, new Object[]{new Integer(userId), new Integer(showId), new Integer(bookingId)});
		showDao.updateBookedSeats(showId, seats, false);
	}

	public int getCancelledSeats(int userId, int showId, int bookingId) throws BookingDoesNotExistException {
		String sql = "SELECT seats FROM booking_tbl WHERE user_id=? AND show_id=? AND booking_id=? AND status='BOOKED'";
		try {
			return this.jdbcTemplate.queryForObject(sql, new Object[]{new Integer(userId), new Integer(showId), new Integer(bookingId)}, Integer.class);
		} catch (Exception e) {
			throw new BookingDoesNotExistException(showId, bookingId);
		}
	}

	public boolean checkIfBooked(int userId, int showId) {
		String sql = "SELECT * FROM booking_tbl WHERE user_id=? AND show_id=? AND status ='BOOKED'";
		List<Booking> booking = this.jdbcTemplate.query(sql, new Object[]{new Integer(userId), new Integer(showId)}, new BookingMapper());
		if (booking.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	private static final class BookingMapper implements RowMapper<Booking> {

		public Booking mapRow(ResultSet rs, int rowCount) throws SQLException {
			Booking booking = new Booking();
			booking.setId(rs.getInt("booking_id"));
			booking.setUserId(rs.getInt("user_id"));
			booking.setShowId(rs.getInt("show_id"));
			booking.setSeats(rs.getInt("seats"));
			booking.setStatus(rs.getString("status"));
			return booking;
		}

	}

}
