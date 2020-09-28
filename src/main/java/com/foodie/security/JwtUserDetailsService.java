package com.foodie.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Value("${jwt.user1}")
	private String user1;

	@Value("${jwt.user1pass}")
	private String user1Pass;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (user1.equals(username)) {
			return new User(user1, user1Pass, new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}