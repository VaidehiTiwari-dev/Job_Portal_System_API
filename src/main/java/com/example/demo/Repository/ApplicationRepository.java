package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Application;
import com.example.demo.Entity.Job;
import com.example.demo.Entity.User;
@Repository  
public interface ApplicationRepository extends JpaRepository<Application,Integer>
{
   public List<Application> findByJob(Job job);	
   public List<Application> findByUser(User user);
   public boolean existsByUser_IdAndJob_JobId(int userId,int jobId);
}
