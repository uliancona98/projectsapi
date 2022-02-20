package edu.itk.project.security.dto;

import java.util.Date;

import lombok.Data;

@Data
public class WorkExperienceDTO {
	
	private long id;
	
	private String position;
	
	private String company;

	private String description;
	
	private Date fromDate;
	
	private Date untilDate;
}
