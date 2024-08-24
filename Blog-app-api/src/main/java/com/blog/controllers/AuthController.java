package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiException;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());

		System.out.println("Username from request "+request.getUsername()+ " Pass "+request.getPassword());
		UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetail);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			System.out.println("Username :"+username);
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details!!");
			throw new ApiException("Invalid Username or Password");
		}
	

	}
	//registering new user
		@PostMapping("/register")
		public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
		{
			UserDto registerdUser=this.userService.registerNewUser(userDto);
			return new ResponseEntity<UserDto>(registerdUser,HttpStatus.CREATED);
		}
}
