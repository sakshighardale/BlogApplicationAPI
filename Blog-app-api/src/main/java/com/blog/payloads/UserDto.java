package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min=4,message="Username must be minimum of 4 characters.")
	private String name;
	
	@Email(message="Email address is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10,message="Passwords must be min of 3 characters and max of 10 characters.")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();

}
