package com.example.demo.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import com.example.demo.Entity.Job;
import com.example.demo.Entity.JobDTO;
import com.example.demo.Entity.JobSpecification;
import com.example.demo.Entity.User;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;

@Service
public class JobService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private JobRepository repo;
	
	public JobDTO convertToDTO(Job job)
	{
		JobDTO jobdto = new JobDTO();
		jobdto.setJobId(job.getJobId());
		jobdto.setTitle(job.getTitle());
		jobdto.setDescription(job.getDescription());
		jobdto.setLocation(job.getLocation());
		jobdto.setSalary(job.getSalary());
		
		if(job.getRecruiter()!=null)
		{
			jobdto.setRecruiterName(job.getRecruiter().getUsername());
		}
		
		return jobdto;
	}
	
	public Job saveJob(Job job)
	{

		 if(job.getRecruiter()==null||job.getRecruiter().getId()==0)
		 {
			 throw new RuntimeException("Recruiter id is needed");
		 }
		 User existingUser = userRepo.findById(job.getRecruiter().getId())
				    .orElseThrow(()-> new ResourceNotFoundException("user not Found"));
		
		job.setRecruiter(existingUser);
		Job savedJob = repo.save(job);
		return repo.findById(savedJob.getJobId()).get();  
		 
	}
    
	public Page<JobDTO> getAllJobs(int page,int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return  repo.findAll(pageable).map(this::convertToDTO);  
				      
	}
	
	
	
    
	public List<JobDTO> search(String title,String location,Integer minsalary 
			,Integer maxsalary,int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		Specification<Job> spec = Specification
				                       .where(JobSpecification.titleContaning(title))
				                       .and(JobSpecification.locationContaining(location))
				                       .and(JobSpecification.salaryGreaterThanEqual(minsalary))
				                       .and(JobSpecification.salaryLessThanEqual(maxsalary));
	
	  Page<Job> pagejob = repo.findAll(spec,pageable);
	  
	  return pagejob.getContent()
			  .stream()
			  .map(this::convertToDTO)
			  .toList();
	}
}
