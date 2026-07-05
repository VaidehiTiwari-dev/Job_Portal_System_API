package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Application;
import com.example.demo.Entity.ApplicationDTO;
import com.example.demo.Entity.Job;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ApplicationRepository;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;

@Service 
public class ApplicationService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private JobRepository jobRepo;
	@Autowired
	private ApplicationRepository applicationRepo;
	
	@Autowired
	private EmailService emailService;
	
	public ApplicationDTO convert(Application app)
	{
		ApplicationDTO dto = new ApplicationDTO();
		dto.setApplicationId(app.getId());
		dto.setJobTitle(app.getJob().getTitle());
		dto.setUsername(app.getUser().getUsername());
		dto.setStatus(app.getStatus());
		
		return dto;
	}
	
	public Application apply(int userId,int jobId)  
	{
		User user = userRepo.findById(userId)
				         .orElseThrow(()-> new ResourceNotFoundException("user not found"));
		Job job = jobRepo.findById(jobId)
				         .orElseThrow(()-> new RuntimeException("job not found"));
		
		boolean alreadyApplied = applicationRepo.existsByUser_IdAndJob_JobId(userId, jobId);
		
		if(alreadyApplied)
		{
			throw new RuntimeException("User Already Applied");
		}
		Application application = new Application();
		application.setJob(job);
		application.setUser(user);  
		application.setStatus("APPLIED");
		try
		{
		emailService.sendEmail(user.getEmail(), 
				"Job Application Submitted Succefully",
				"You Applied to "+job.getTitle());
		}catch(Exception e)
		{
			System.out.println("Email not worjking now ");
		}
		return applicationRepo.save(application);
		   
	}
	
	public List<ApplicationDTO> getApplicationByJob(int jobId)
	{
	     Job job = jobRepo.findById(jobId)
	    		     .orElseThrow(()-> new RuntimeException("job not found"));
	     
		 List<ApplicationDTO> application = 
				 applicationRepo.findByJob(job)
				 .stream().map(this::convert).toList();
		 return application;
	}
	
	public List<ApplicationDTO> getApplicationByUser(int userId)
	{
		User user = userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user not found"));
		List<ApplicationDTO> application = applicationRepo.findByUser(user)
				.stream().map(this::convert).toList();
		return application;
	}
	
	public Application updateStatus(int applicationId,String newStatus)
	{
	   Application application = applicationRepo.findById(applicationId)
			   .orElseThrow(()->new ResourceNotFoundException("application not found"));
	   
	   application.setStatus(newStatus);
	   Application updatedApplication = applicationRepo.save(application);
	   User user= application.getUser();
	   String user_email = user.getEmail();
	   String jobTitle = application.getJob().getTitle();
	   emailService.sendEmail(user_email,
			   "Job Application Status Updated",
			   "Your Application for " +jobTitle+ " has been updated to "+newStatus);
	   return updatedApplication;    
	}
	
}
