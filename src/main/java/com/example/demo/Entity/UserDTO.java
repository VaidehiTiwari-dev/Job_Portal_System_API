package com.example.demo.Entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

	@NotBlank(message="Username can not be empty")
	private String username; 
	@Size(min=4,message="Password should have minimum 4 digit")
	private String password;
	@Email(message="Invalid email format")
	@NotBlank(message="Email can not be Blank")
	private String email;
	@NotBlank(message="Roles can not bre Blank")
	private String roles;
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public String getRoles() {
		return roles;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
