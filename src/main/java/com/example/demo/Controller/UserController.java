package com.example.demo.Controller;

import java.io.IOException;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.User;
import com.example.demo.Entity.UserDTO;
import com.example.demo.Service.UserService;

import jakarta.validation.Valid;

@RestController 
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/users/register") 
	public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO dto)  
	{
		  User saveduser = userService.saveUser(dto);
		  return ResponseEntity.status(HttpStatus.CREATED).body(saveduser);   
	}
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> findUser(@PathVariable int id)
	{
		Optional<User> user = userService.findUser(id);
		if(user.isPresent())
		{
		   return ResponseEntity.status(HttpStatus.OK).body(user);
		}else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
		}
	}
	@DeleteMapping("/users/{id}") 
	public ResponseEntity<String> deleteUser(@PathVariable int id)
	{
		String info = userService.deleteUser(id);
		if(info!=null)
		{
		return ResponseEntity.status(HttpStatus.OK).body(info);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id not found");
		}
    }
	@PostMapping("/users/{userId}/resume")
	public ResponseEntity<String> uploadResume(@PathVariable int userId ,
			    @RequestParam("File") MultipartFile file) throws IOException, TikaException 
	{
		String response = userService.uploadResume(userId, file);
		return ResponseEntity.ok(response);  
	}
	
	
}
