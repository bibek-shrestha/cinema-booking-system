package com.cinema.repository;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.cinema.domain.User;
import com.cinema.exceptions.UserDoesNotExistException;

public interface UserDao {
	@Secured({"ROLE_ADMIN"})
	public void create(User u);
	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers();
	@Secured("ROLE_ADMIN")
	public User getUser(int userId);
	public User findUserByUsername(String userName) throws UserDoesNotExistException;
	@Secured("ROLE_ADMIN")
	public void updateUser(int userId, User u);
}
