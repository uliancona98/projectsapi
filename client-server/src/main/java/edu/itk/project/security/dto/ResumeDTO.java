package edu.itk.project.security.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/* Define the data access object: field that we want to show in the JSON request */
@Data
@RequiredArgsConstructor
public class ResumeDTO {

	private long id;

	private String firstName;

	private String lastName;

	private String email;

	private String phoneNumber;

	private AddressDTO address;

	private Set<TechnologyDTO> technologies = new HashSet<>();

	private Set<SkillDTO> skills = new HashSet<>();

	private Set<EducationDTO> education = new HashSet<>();

	private Set<WorkExperienceDTO> workExperience = new HashSet<>();
}
