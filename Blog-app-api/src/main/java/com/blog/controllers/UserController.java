package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.payloads.UserResponse;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// Post create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserdto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserdto, HttpStatus.CREATED);
	}

	// Put update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updatUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}

	// delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUSer(userId);
		return new ResponseEntity(new ApiResponse("User Deleted Successfully.", true), HttpStatus.OK);
	}
	// Get user get

	@GetMapping("")
	public ResponseEntity<UserResponse> getAllUsers(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.USER_SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir) {
		return ResponseEntity.ok(this.userService.getAllUsers(pageNum, pageSize,sortBy,sortDir));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUSer(@PathVariable Integer userId) {

		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}
