package com.cinema.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cinema.domain.User;
import com.cinema.exceptions.UserDoesNotExistException;
@Component
public class UserDaoImpl implements UserDao {
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create(User u) {
		String sql = "INSERT INTO user_tbl (username,password,email,role) VALUES (?,?,?,?)";
		this.jdbcTemplate.update(sql, new Object[]{u.getUserName(),u.getPassword(),u.getEmailId(),u.getRole()});
	}

	public User getUser(int userId) {
		String sql ="SELECT * FROM user_tbl WHERE user_id = ?";
		return this.jdbcTemplate.queryForObject(sql,new Object[]{new Integer(userId)}, new UserMapper() );
	}
	
	public User findUserByUsername(String userName) throws UserDoesNotExistException {
		String sql ="SELECT * FROM user_tbl WHERE username = ?";
		try{
			return this.jdbcTemplate.queryForObject(sql,new Object[]{userName}, new UserMapper() );
		}catch(Exception e){
			throw new UserDoesNotExistException(userName);
		}
	}
	public List<User> getAllUsers() {
		String sql ="SELECT * FROM user_tbl";
		return this.jdbcTemplate.query(sql, new UserMapper() );
	}
	
	private static final class UserMapper implements RowMapper<User>{
		public User mapRow(ResultSet rs, int rowCount) throws SQLException {
			User u = new User();
			u.setUserId(rs.getInt("user_id"));
			u.setUserName(rs.getString("username"));
			u.setPassword(rs.getString("password"));
			u.setEmailId(rs.getString("email"));
			u.setRole(rs.getString("role"));
			return u;
		}
	}

	public void updateUser(int userId, User u) {
		String sql ="UPDATE user_tbl SET password = ? WHERE user_id=?";
		this.jdbcTemplate.update(sql, new Object[]{u.getPassword(),new Integer(userId)});
	}



}
