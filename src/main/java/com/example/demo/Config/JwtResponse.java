package com.example.demo.Config;

public class JwtResponse {

	private String token;
	private String username;
	private String roles;
	public JwtResponse(String token, String username, String roles) {
		super();
		this.token = token;
		this.username = username;
		this.roles = roles;
	}
	public String getToken() {
		return token;
	}
	public String getUsername() {
		return username;
	}
	public String getRoles() {
		return roles;
	}
	
}
