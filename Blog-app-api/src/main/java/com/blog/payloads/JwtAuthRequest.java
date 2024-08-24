package com.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username; //email as username
	
	private String password;
}
