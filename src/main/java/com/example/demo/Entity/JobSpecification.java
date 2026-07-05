package com.example.demo.Entity;

import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

	public static Specification<Job> titleContaning(String title)
	{
		return (root,query,cb)-> title==null?null:
			  cb.like(cb.lower(root.get("title")), "%"+title.toLowerCase()+"%");
	}
	
	public static Specification<Job> locationContaining(String location)
	{
		return (root,query,cb)->location==null?null:
			cb.like(cb.lower(root.get("location")),"%"+location.toLowerCase()+"%");
	}
	public static Specification<Job> salaryGreaterThanEqual(Integer minSalary)
	{
		return (root,query,cb)->minSalary==null?null:
			cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
	}
	public static Specification<Job> salaryLessThanEqual(Integer maxSalary)
	{
		return (root,query,cb)->maxSalary==null?null:
			cb.lessThanOrEqualTo(root.get("salary"), maxSalary); 
	}
	
}
