package com.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//1. get token with tokenHelper
		
		String requestToken=request.getHeader("Authorization");
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			token=requestToken.substring(7); //token without Bearer
			try {
				username=jwtTokenHelper.getUsernameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("Unable to get JWT");
			}
			catch(ExpiredJwtException e)
			{
				System.out.println("JWT has expired");
			}
			catch(MalformedJwtException e)
			{
				System.out.println("Inalid jwt");
			}
		}
//		else{
//			System.out.println("Jwt token does not begins with Bearer");
//		}
		
		
		
		
		//2. after getting token, now validate it
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			if(jwtTokenHelper.validateToken(token, userDetails));
			{
				UsernamePasswordAuthenticationToken usernameAuthPasstoken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				usernameAuthPasstoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernameAuthPasstoken);
			}
//			else
//			{
//				System.out.println("Invalid JWT token");
//			}
		}
		else {
			System.out.println("Username is null or context is not null");
		}
		filterChain.doFilter(request, response);
		
		
		//3. 
		
		
	}
	
	
	
	

}
