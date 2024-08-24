package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.exceptions.*;
import com.blog.config.AppConstants;
import com.blog.entities.Post;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.payloads.UserResponse;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		return this.userToUserDto(savedUser);
	}
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser=this.userRepo.save(user);
		UserDto userDto1=this.userToUserDto(updatedUser);
		return userDto1;
		
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		return this.userToUserDto(user);
	}

	@Override
	public UserResponse getAllUsers(Integer pageNum,Integer pageSize,String sortBy,String sortDir) {
		Sort sortingDir=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		UserResponse userResp=new UserResponse();
		Pageable pageable=PageRequest.of(pageNum, pageSize,sortingDir);
		Page<User>pageUser=this.userRepo.findAll(pageable);
		List<User>users=pageUser.getContent();
		List<UserDto>userDtos=users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		userResp.setContent(userDtos);
		userResp.setLastPage(pageUser.isLast());
		userResp.setPageNum(pageUser.getNumber());
		userResp.setPageSize(pageUser.getSize());
		userResp.setTotalElements(pageUser.getTotalElements());
		userResp.setTotalPages(pageUser.getTotalPages());
		return userResp;
	}

	@Override
	public void deleteUSer(Integer userID) {
		// TODO Auto-generated method stub
		User user=this.userRepo.findById(userID).orElseThrow(()->new ResourceNotFoundException("User","Id",userID));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userdto)
	{
//		User user=new User();
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
//		return user;
		User user=this.modelMapper.map(userdto, User.class);
		return user;
	}
	
	private UserDto userToUserDto(User user)
	{
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		return userDto;
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		//below we have encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//managing roles
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User userNew=this.userRepo.save(user);
		return this.modelMapper.map(userNew, UserDto.class);
	}
	
}
