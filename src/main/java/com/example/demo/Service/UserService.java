package com.example.demo.Service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Job;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserDTO;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;

@Service  
public class UserService {

	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired 
	private JobRepository jobRepo;  
	
	public User saveUser(UserDTO userdto)
	{
		User user = new User();
		user.setUsername(userdto.getUsername());
		user.setPassword(encoder.encode(userdto.getPassword()));
		user.setEmail(userdto.getEmail());
		user.setRoles(userdto.getRoles());
		
		return repo.save(user);
	}

	public Optional<User> findUser(int id)
	{
		Optional<User> user = repo.findById(id);
		return user;   
	}
	
	public String deleteUser(int id)
	{
		repo.deleteById(id);
		return id+" deleted";   
	}
	
	public String  uploadResume(int userId,MultipartFile file) throws IOException, TikaException
	{
		User user = repo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user not found "));
		
		String uploadDir = "uploads/";
		Path path = Paths.get(uploadDir);
		if(!Files.exists(path))
		{
		   Files.createDirectories(path);	
		}
		String fileName = file.getOriginalFilename();
		Path filePath = path.resolve(fileName);
		Files.copy(file.getInputStream(),filePath,StandardCopyOption.REPLACE_EXISTING);
		user.setResumePath(fileName);
        repo.save(user);		
        
        Tika tika = new Tika();
        File resumeFile = filePath.toFile();
        String resumeText = tika.parseToString(resumeFile).toLowerCase();
        
        List<Job> jobs = jobRepo.findAll();
        Job bestJob = null;
        int bestScore = 0;
        for(Job job : jobs)
        {
        	int score = 0;
        	String jobText = (job.getTitle()+" "+job.getDescription()).toLowerCase();
        	if(resumeText.contains("java")&&jobText.contains("java"))
        	{
        		score+=25;
        	}
        	if(resumeText.contains("spring")&&jobText.contains("spring"))
        	{
        		score+=25;
        	}
        	if(resumeText.contains("mysql")&&jobText.contains("mysql"))
        	{
        		score+=25;
        	}
        	if(resumeText.contains("rest")&&jobText.contains("rset"))
        	{
        		score+=25;
        	}
        	if(score > bestScore)
        	{
        		bestScore = score;
        		bestJob = job;
        	}
        }
        if(bestJob != null)
        {
        	return "Resume uploaded successfully . Best Match"
        			+bestJob.getTitle()
        			+"Match Score : "
        			+bestScore+"%";
        }
        return "Resume uploaded successfully"; 
        
	}
}
