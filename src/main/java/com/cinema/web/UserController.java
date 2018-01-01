package com.cinema.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.domain.User;
import com.cinema.repository.UserDao;

@RestController
@RequestMapping(value = "")
public class UserController {
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<User> getUser(@PathVariable int userId) {
		User u = userDao.getUser(userId);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<User>> getUser() {
		List<User> allUsers = userDao.getAllUsers();
		return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/users", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> createUser(@RequestBody User u) {
		userDao.create(u);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
