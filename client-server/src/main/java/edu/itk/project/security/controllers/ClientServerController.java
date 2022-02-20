package edu.itk.project.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import edu.itk.project.security.dto.*;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class ClientServerController {

	@Autowired
	private WebClient webClient;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	@RequestMapping("/")
	public Map<String, String> setSecurityContextHolder(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		String user = oauth2AuthorizedClient.getPrincipalName();

		String query = "SELECT authorities.authority FROM users, authorities WHERE users.username = authorities.username AND users.username = ?;";
		String role = (String) jdbcTemplate.queryForObject(query, new Object[] { user }, String.class);

		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(role));

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Map<String, String> json = new LinkedHashMap<>();
		json.put("user", user);
		json.put("role", role);

		return json;
	}

	/* --------------- RESUME MODULE ENDPOINTS DEFINITION --------------- */

	private static final String RESUME_MODULE_HOST = "http://10.5.0.7:8090";

	private static final String GET_ALL_RESUMES = RESUME_MODULE_HOST + "/employees/resumes";

	private static final String GET_RESUME_BY_ID = RESUME_MODULE_HOST + "/employees/{employeeId}/resume";

	private static final String GET_TECHNOLOGIES = RESUME_MODULE_HOST + "/employees/{employeeId}/resume/technologies";

	private static final String GET_TECHNOLOGY_BY_ID = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/technologies/{technologyId}";

	private static final String GET_SKILLS = RESUME_MODULE_HOST + "/employees/{employeeId}/resume/skills";

	private static final String GET_SKILL_BY_ID = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/skills/{skillId}";

	private static final String GET_EDUCATIONAL_BACKGROUND = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/education";

	private static final String GET_EDUCATIONAL_DEGREE_BY_ID = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/education/{educationId}";

	private static final String GET_WORK_EXPERIENCE = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/work-experience";

	private static final String GET_WORK_EXPERIENCE_BY_ID = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/work-experience/{workId}";

	private static final String POST_RESUME = RESUME_MODULE_HOST + "/employees/{employeeId}/resume";

	private static final String POST_TECHNOLOGY = RESUME_MODULE_HOST + "/employees/{employeeId}/resume/technologies";

	private static final String POST_SKILL = RESUME_MODULE_HOST + "/employees/{employeeId}/resume/skills";

	private static final String POST_EDUCATION = RESUME_MODULE_HOST + "/employees/{employeeId}/resume/education";

	private static final String POST_WORK_EXPERIENCE = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/work-experience";

	private static final String PUT_RESUME = RESUME_MODULE_HOST + "/employees/{employeeId}/resume";

	private static final String PUT_TECHNOLOGY = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/technologies/{technologyId}";

	private static final String PUT_SKILL = RESUME_MODULE_HOST + "/employees/{employeeId}/resume/skills/{skillId}";

	private static final String PUT_EDUCATION = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/education/{educationId}";

	private static final String PUT_WORK_EXPERIENCE = RESUME_MODULE_HOST
			+ "/employees/{employeeId}/resume/work-experience/{educationId}";

	private static final String PATCH_RESUME = RESUME_MODULE_HOST + "/employees/{employeeId}/resume";
	
	// GET all resumes
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/employees/resumes")
	public Object getAllResumes(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@RequestParam(name = "search", required = false) Optional<String> search,
			@RequestParam(name = "searchBy", required = false) Optional<String> searchBy,
			@PageableDefault(sort = { "id" }) Pageable pageable) {
		return this.webClient.get().uri(GET_ALL_RESUMES,
				uri -> uri.queryParamIfPresent("search", search).queryParamIfPresent("searchBy", searchBy).build())
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Resume by ID
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume")
	public Object findResumeByEmployeeId(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId) {
		return this.webClient.get().uri(GET_RESUME_BY_ID, employeeId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Technologies
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/technologies")
	public Object findTechnologies(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId) {
		return this.webClient.get().uri(GET_TECHNOLOGIES, employeeId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Technology by ID
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/technologies/{technologyId}")
	public Object findTechnology(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("technologyId") long technologyId) {
		return this.webClient.get().uri(GET_TECHNOLOGY_BY_ID, employeeId, technologyId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Skills
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/skills")
	public Object findSkills(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId) {
		return this.webClient.get().uri(GET_SKILLS, employeeId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Skill by ID
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/skills/{skillId}")
	public Object findSkill(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("skillId") long skillId) {
		return this.webClient.get().uri(GET_SKILL_BY_ID, employeeId, skillId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Educational background
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/education")
	public Object findEducation(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId) {
		return this.webClient.get().uri(GET_EDUCATIONAL_BACKGROUND, employeeId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Education background by ID
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/education/{educationId}")
	public Object findEducationById(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("educationId") long educationId) {
		return this.webClient.get().uri(GET_EDUCATIONAL_DEGREE_BY_ID, employeeId, educationId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Work experience
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/work-experience")
	public Object findWorkExperience(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId) {
		return this.webClient.get().uri(GET_WORK_EXPERIENCE, employeeId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// GET Work experience by ID
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	@GetMapping("/employees/{employeeId}/resume/work-experience/{workId}")
	public Object findWorkExperienceById(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("workId") long workId) {
		return this.webClient.get().uri(GET_WORK_EXPERIENCE_BY_ID, employeeId, workId)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// POST a Resume
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/employees/{employeeId}/resume")
	public Object addResume(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @RequestBody ResumeDTO request) {
		return this.webClient.post().uri(POST_RESUME, employeeId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), ResumeDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// POST a Technology
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/employees/{employeeId}/resume/technologies")
	public Object addTechnology(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @RequestBody TechnologyDTO request) {
		return this.webClient.post().uri(POST_TECHNOLOGY, employeeId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), TechnologyDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// POST a Skill
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/employees/{employeeId}/resume/skills")
	public Object addSkill(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @RequestBody SkillDTO request) {
		return this.webClient.post().uri(POST_SKILL, employeeId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), SkillDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// POST Education
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/employees/{employeeId}/resume/education")
	public Object addEducation(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @RequestBody EducationDTO request) {
		return this.webClient.post().uri(POST_EDUCATION, employeeId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), EducationDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// POST Work Experience
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("/employees/{employeeId}/resume/work-experience")
	public Object addWorkExperience(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @RequestBody WorkExperienceDTO request) {
		return this.webClient.post().uri(POST_WORK_EXPERIENCE, employeeId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), WorkExperienceDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// PUT Resume
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PutMapping("/employees/{employeeId}/resume")
	public Object editResume(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @RequestBody ResumeDTO request) {
		return this.webClient.put().uri(PUT_RESUME, employeeId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), ResumeDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// PUT Technology
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PutMapping("/employees/{employeeId}/resume/technologies/{technologyId}")
	public Object editTechnology(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("technologyId") long technologyId,
			@RequestBody TechnologyDTO request) {
		return this.webClient.put().uri(PUT_TECHNOLOGY, employeeId, technologyId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(request), TechnologyDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// PUT Skill
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PutMapping("/employees/{employeeId}/resume/skills/{skillId}")
	public Object editSkill(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("skillId") long skillId,
			@RequestBody SkillDTO request) {
		return this.webClient.put().uri(PUT_SKILL, employeeId, skillId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), SkillDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// PUT Education
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PutMapping("/employees/{employeeId}/resume/education/{educationId}")
	public Object editEducation(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("educationId") long educationId,
			@RequestBody EducationDTO request) {
		return this.webClient.put().uri(PUT_EDUCATION, employeeId, educationId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), EducationDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	// PUT Work Experience
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PutMapping("/employees/{employeeId}/resume/work-experience/{workId}")
	public Object editWorkExperience(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @PathVariable("workId") long workId,
			@RequestBody WorkExperienceDTO request) {
		return this.webClient.put().uri(PUT_WORK_EXPERIENCE, employeeId, workId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), WorkExperienceDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PatchMapping("/employees/{employeeId}/resume")
	public Object editPartialResume(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("employeeId") long employeeId, @RequestBody ResumeDTO request) {
		return this.webClient.patch().uri(PATCH_RESUME, employeeId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), ResumeDTO.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	/* --------------- END OF RESUME MODULE ENDPOINTS DEFINITION --------------- */

}
