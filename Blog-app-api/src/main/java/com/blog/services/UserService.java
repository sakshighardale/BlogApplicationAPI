package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;
import com.blog.payloads.UserResponse;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	UserResponse getAllUsers(Integer pageNum, Integer pageSize,String sortBy,String sortDir);
	void deleteUSer(Integer userID);
}
