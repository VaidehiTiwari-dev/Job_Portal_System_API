package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  
@EnableWebSecurity  
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	{
		http
		    .csrf(csrf->csrf.disable())
		    .authorizeHttpRequests(auth->auth
		    		.requestMatchers("/users/register").permitAll()
		    		.requestMatchers("/auth/login").permitAll()
		    		.requestMatchers("/jobs").hasRole("RECRUITER")
		    		.requestMatchers("/jobs/**").hasRole("USER")
		    		.requestMatchers("/application/**").hasRole("USER")
		    		.anyRequest().authenticated())
		    .formLogin(form->form.disable());
		
		http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();   
	}

	@Bean
	public PasswordEncoder passwordEncoder()  
	{
		return new BCryptPasswordEncoder();
	}
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
	{
		return config.getAuthenticationManager();
	}
	@Bean
	public JwtFilter jwtFilter()
	{
		return new JwtFilter();
	}
	
}
