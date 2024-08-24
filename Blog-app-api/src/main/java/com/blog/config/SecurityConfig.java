package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailService;
import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig {

	public static final String[] PUBLIC_URLS= {
			"/v3/api-docs/**","/swagger-resources/**",
			"/swagger-ui/**","/webjars/**","/v2/api-docs"
	};
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().requestMatchers(PUBLIC_URLS).permitAll().requestMatchers("/api/auth/**").permitAll().requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	}
}
