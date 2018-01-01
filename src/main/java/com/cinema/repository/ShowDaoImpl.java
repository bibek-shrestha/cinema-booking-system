package com.cinema.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cinema.domain.Show;
import com.cinema.exceptions.ShowDoesNotExistException;

public class ShowDaoImpl implements ShowDao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void createShow(Show show) {
		show.setBookedSeats(0);
		if (show.getBookingStatus() == null) {
			show.setBookingStatus("CLOSED");
		}
		String sql = "INSERT INTO show_tbl (showname,showtime,showdate,totalseats,bookedseats,bookingstatus) VALUES(?,?,?,?,?,?)";
		this.jdbcTemplate.update(sql, new Object[]{show.getShowName(), show.getShowTime(), show.getShowDate(), show.getTotalSeats(), show.getBookedSeats(), show.getBookingStatus()});
	}

	public void deleteShow(int showId) throws ShowDoesNotExistException {
		String sql = "DELETE FROM show_tbl WHERE show_id=?";
		this.jdbcTemplate.update(sql, new Object[]{new Integer(showId)});
		try {
		} catch (Exception e) {
			throw new ShowDoesNotExistException(showId);
		}
	}

	public List<Show> getAllShows() {
		String sql = "SELECT * FROM show_tbl";
		return this.jdbcTemplate.query(sql, new ShowMapper());
	}

	public Show getShow(int showId) throws ShowDoesNotExistException {
		String sql = "SELECT * FROM show_tbl WHERE show_id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new Object[]{new Integer(showId)}, new ShowMapper());
		} catch (Exception e) {
			throw new ShowDoesNotExistException(showId);
		}
	}

	public List<Show> getShows(String bookingStatus) {
		String sql = "SELECT * FROM show_tbl WHERE bookingstatus = ?";
		return this.jdbcTemplate.query(sql, new Object[]{bookingStatus}, new ShowMapper());
	}

	public void changeBookingStatus(int showId, Show show) throws ShowDoesNotExistException {
		String sql = "UPDATE show_tbl SET bookingstatus=? WHERE show_id=?";
		this.jdbcTemplate.update(sql, new Object[]{show.getBookingStatus(), new Integer(showId)});
	}

	public void changeBookingStatus(int showId, String status) {
		String sql = "UPDATE show_tbl SET bookingstatus=? WHERE show_id=?";
		this.jdbcTemplate.update(sql, new Object[]{status, new Integer(showId)});
	}

	public String getBookingStatus(int showId) throws ShowDoesNotExistException {
		String sql = "SELECT bookingstatus FROM show_tbl WHERE show_id=?";
		try {
			return this.jdbcTemplate.queryForObject(sql, new Object[]{new Integer(showId)}, String.class);
		} catch (Exception e) {
			throw new ShowDoesNotExistException(showId);
		}
	}

	public void updateBookedSeats(int showId, int seats, boolean increase) {
		String sql;
		if (increase) {
			sql = "UPDATE show_tbl SET bookedseats=bookedseats+? WHERE show_id=?";
		} else {
			sql = "UPDATE show_tbl SET bookedseats=bookedseats-? WHERE show_id=?";
		}
		this.jdbcTemplate.update(sql, new Object[]{new Integer(seats), new Integer(showId)});
		if (bookedSeats(showId) >= totalSeats(showId)) {
			changeBookingStatus(showId, "CLOSED");
		} else {
			changeBookingStatus(showId, "OPEN");
		}
	}

	public int bookedSeats(int showId) {
		String sql = "SELECT bookedseats FROM show_tbl WHERE show_id=?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{new Integer(showId)}, Integer.class);
	}

	public int totalSeats(int showId) {
		String sql = "SELECT totalseats FROM show_tbl WHERE show_id=?";
		return this.jdbcTemplate.queryForObject(sql, new Object[]{new Integer(showId)}, Integer.class);
	}
	private static final class ShowMapper implements RowMapper<Show> {

		public Show mapRow(ResultSet rs, int rowCount) throws SQLException {
			Show show = new Show();
			show.setId(rs.getInt("show_id"));
			show.setShowName(rs.getString("showname"));
			show.setShowTime(rs.getString("showtime"));
			show.setShowDate(rs.getString("showdate"));
			show.setTotalSeats(rs.getInt("totalseats"));
			show.setBookedSeats(rs.getInt("bookedseats"));
			show.setBookingStatus(rs.getString("bookingstatus"));
			return show;
		}

	}
}
