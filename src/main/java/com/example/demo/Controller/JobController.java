package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Job;
import com.example.demo.Entity.JobDTO;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Service.JobService;

@RestController 

public class JobController {

	@Autowired
	private JobRepository jobRepo;
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/jobs")        
	public ResponseEntity<Job> saveJob(@RequestBody Job job)
	{
	      Job savedJob = jobRepo.save(job);
	      return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
	}   
	
	@GetMapping("/jobs")
	public Page<JobDTO> getJobs(@RequestParam int page,@RequestParam int size)
	{
		return jobService.getAllJobs(page,size);
	}
	
	@GetMapping("/jobs/search")
	public ResponseEntity<List<JobDTO>> search(@RequestParam(required=false) String title,
			@RequestParam(required=false) String location ,
			@RequestParam(required=false) Integer minsalary ,
			@RequestParam(required=false) Integer maxsalary,
			@RequestParam(defaultValue="0") int page ,
			@RequestParam(defaultValue="5") int size)
	{
		List<JobDTO> savedJob = jobService.search(title, location, minsalary, maxsalary, page, size);
		return ResponseEntity.status(HttpStatus.OK).body(savedJob);
	}
	
}
