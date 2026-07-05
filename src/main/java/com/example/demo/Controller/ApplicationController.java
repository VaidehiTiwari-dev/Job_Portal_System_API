package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Application;
import com.example.demo.Entity.ApplicationDTO;
import com.example.demo.Service.ApplicationService;
import com.example.demo.Service.EmailService;

@RestController 
public class ApplicationController {

	@Autowired 
	private ApplicationService applicationService;
	
	@PostMapping("/application/{userId}/{jobId}")
	public ResponseEntity<Application> apply(@PathVariable int userId,@PathVariable int jobId)
	{
		Application application = applicationService.apply(userId, jobId);
		return ResponseEntity.status(HttpStatus.CREATED).body(application);
	}  
	@GetMapping("/application/job/{jobId}")
	public ResponseEntity<List<ApplicationDTO>> getApplicationByJob(@PathVariable int jobId)
	{  
		List<ApplicationDTO> application = applicationService.getApplicationByJob(jobId);
		return ResponseEntity.ok(application);  
	}
	@GetMapping("/application/user/{userId}")
	public ResponseEntity<List<ApplicationDTO>> getApplicationByUser(@PathVariable int userId)
	{
		List<ApplicationDTO> application = applicationService.getApplicationByUser(userId);
		return ResponseEntity.ok(application);
	}
	@PatchMapping("/application/status")
	public ResponseEntity<Application> updateStatus(@RequestParam int applicationId , @RequestParam String newStatus)
	{
		Application application = applicationService.updateStatus(applicationId, newStatus);
		return ResponseEntity.status(HttpStatus.OK).body(application);
	}
	
}


