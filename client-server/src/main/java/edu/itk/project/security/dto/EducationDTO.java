package edu.itk.project.security.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EducationDTO {

	private long id;

	private String degree;

	private String type;

	private Date fromDate;
	
	private Date untilDate;
}
