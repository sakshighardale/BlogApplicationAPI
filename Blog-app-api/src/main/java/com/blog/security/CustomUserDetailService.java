package com.blog.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method
		// loading user from database by username i.e. email
		User user=this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email :" + username, 0));
		return user;
	}

}
