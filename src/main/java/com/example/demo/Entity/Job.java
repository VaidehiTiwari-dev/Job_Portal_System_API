package com.example.demo.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="job")
public class Job {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int jobId;
	private String title;
	private String description;
	private String location;
	private int salary;
	@ManyToOne
	@JoinColumn(name="recruiter_id" )
	private User recruiter;
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public User getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(User recruiter) {
		this.recruiter = recruiter;
	}
	
	
}
