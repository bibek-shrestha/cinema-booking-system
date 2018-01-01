package com.cinema.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.cinema.domain.User;
import com.cinema.exceptions.UserDoesNotExistException;
import com.cinema.repository.UserDao;

public class CustomUserDetailsService implements UserDetailsService {
	@Autowired UserDao userDao;
	public UserDetails loadUserByUsername(String username) {
		CustomUserDetails passUser = new CustomUserDetails();
		User user = null;
		try {
			user = userDao.findUserByUsername(username);
		} catch (UserDoesNotExistException e) {
			e.getMessage();
		}
		if (user!=null) {
			passUser.setAuthentication(true);
			passUser.setUserId(user.getUserId());
			passUser.setPassword(user.getPassword());
			Collection<CustomRole> roles = new ArrayList<CustomRole>();
			CustomRole customRole = new CustomRole();
			customRole.setAuthority(user.getRole());
			roles.add(customRole);
			passUser.setAuthorities(roles);
		}
		return passUser;
	}
	private class CustomRole implements GrantedAuthority {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String role = null;

		public String getAuthority() {
			return role;
		}

		public void setAuthority(String roleName) {
			this.role = roleName;
		}

	}

}
