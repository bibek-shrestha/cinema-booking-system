package com.cinema.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	Collection<? extends GrantedAuthority> list = null;
	String userName = null;
	int userId;
	String password = null;
	boolean status = false;

	public CustomUserDetails() {
		list = new ArrayList<GrantedAuthority>();
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.list;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
		this.list = roles;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setAuthentication(boolean status) {
		this.status = status;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.userName;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

}
