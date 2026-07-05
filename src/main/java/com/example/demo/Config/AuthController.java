package com.example.demo.Config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
     @PostMapping("/login")
     public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request)
     {
    	 UsernamePasswordAuthenticationToken token = new 
    			 UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
    	 
    	 authManager.authenticate(token);
    	 
    	User user = userRepo.findByUsername(request.getUsername())
    			 .orElseThrow(()-> new ResourceNotFoundException("user not found"));
    	
    	 String jwt =  jwtUtil.generateToken(user.getUsername()) ;  
    	 
    	 JwtResponse response = new JwtResponse(jwt,user.getUsername(),user.getRoles());
    	
    	 return ResponseEntity.ok(response);
    	 
     }
}
