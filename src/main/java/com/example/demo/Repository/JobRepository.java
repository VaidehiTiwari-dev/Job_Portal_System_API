package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Job;

@Repository  
public interface JobRepository extends JpaRepository<Job,Integer>,JpaSpecificationExecutor<Job> {

	Page<Job> findAll(Pageable pageable);
	

}
