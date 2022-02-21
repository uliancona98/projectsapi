package edu.itk.project.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import edu.itk.project.security.dto.ErrorDTO;
import edu.itk.project.security.dto.ProjectRequest;
import edu.itk.project.security.dto.TaskRequest;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	public Map<String, String> setSecurityContextHolder(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {		
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
	//@PreAuthorize("hasRole('ROLE_ADMIN') && hasRole('ROLE_MANAGER')")
	private static final String PROJECTS_MANAGEMENT_MODULE_HOST = "http://10.5.0.7:8090";

	private static final String PROJECT_ENDPOINT_BASE_URL = PROJECTS_MANAGEMENT_MODULE_HOST + "/projects/{idProject}";
	private static final String PROJECTS_ENDPOINT_BASE_URL = PROJECTS_MANAGEMENT_MODULE_HOST + "/projects";
	private static final String GET_SELF_PROJECTS_URL = PROJECTS_MANAGEMENT_MODULE_HOST + "/projects/self";
	private static final String PROJECTS_DEVELOPERS_URL = PROJECTS_MANAGEMENT_MODULE_HOST + "/projects/{idProject}/developers";

	private static final String PROJECTS_DISABLE_URL = PROJECTS_MANAGEMENT_MODULE_HOST + "/projects/{idProject}/disable";


	private static final String PROJECT_TASKS_ENDPOINT_BASE_URL = PROJECTS_MANAGEMENT_MODULE_HOST + "/projects/{idProject}/tasks";
	private static final String PROJECT_TASK_ENDPOINT_BASE_URL = PROJECTS_MANAGEMENT_MODULE_HOST + "/projects/{idProject}/tasks/{idTask}";

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects")
	public Object getAllProjects(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri(PROJECTS_ENDPOINT_BASE_URL)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				})
				.block();
	}


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects/self")
	public Object getMyProjects(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri(GET_SELF_PROJECTS_URL)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				})
				.block();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects/{idProject}/disable")
	public Object disableProject(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("idProject") Integer idProject) {
		return this.webClient
				.get()
				.uri(PROJECTS_DISABLE_URL, idProject)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				})
				.block();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects/{idProject}")
	public Object getAllProjects(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("idProject") Integer idProject) {
		return this.webClient
				.get()
				.uri(PROJECT_ENDPOINT_BASE_URL, idProject)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				})
				.block();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects/{idProject}/developers")
	public Object getProjectDevelopers(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("idProject") Integer idProject) {
		return this.webClient
				.get()
				.uri(PROJECTS_DEVELOPERS_URL, idProject)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				})
				.block();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects/{idProject}/tasks")
	public Object getAllProjectTasks(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("idProject") Integer idProject) {
		return this.webClient
				.get()
				.uri(PROJECT_TASKS_ENDPOINT_BASE_URL, idProject)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				})
				.block();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects/{idProject}/tasks/{idTask}")
	public Object getProjectTask(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("idProject") Integer idProject, @PathVariable("idTask") Integer idTask) {
		return this.webClient
				.get()
				.uri(PROJECT_TASK_ENDPOINT_BASE_URL, idProject, idTask)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.exchangeToMono(response -> {
					if (response.statusCode().isError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				})
				.block();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/projects")
	public Object addProject(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @RequestBody ProjectRequest request) {
		return this.webClient.post().uri(PROJECTS_ENDPOINT_BASE_URL).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), ProjectRequest.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/projects/{idProject}/tasks")
	public Object addProjectTask(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("idProject") long idProject, @RequestBody ProjectRequest request) {
		return this.webClient.post().uri(PROJECT_TASKS_ENDPOINT_BASE_URL, idProject).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), ProjectRequest.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/projects/{idProject}")
	public Object editProject(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("idProject") Integer idProject, @RequestBody ProjectRequest request) {
		return this.webClient.put().uri(PROJECT_ENDPOINT_BASE_URL, idProject).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), ProjectRequest.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/projects/{idProject}/tasks/{idTask}")
	public Object editProjectTask(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("idProject") Integer idProject, @PathVariable("idTask") Integer idTask, @RequestBody TaskRequest request) {
		return this.webClient.put().uri(PROJECT_TASK_ENDPOINT_BASE_URL, idProject, idTask).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), TaskRequest.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PatchMapping("/projects/{idProject}/developers")
	public Object editPatchProjectDevelopers(
			@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient,
			@PathVariable("idProject") Integer idProject, @RequestBody List<Integer> request) {
		return this.webClient.patch().uri(PROJECT_ENDPOINT_BASE_URL, idProject).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).body(Mono.just(request), List.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient)).exchangeToMono(response -> {
					if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
						return response.bodyToMono(ErrorDTO.class);
					} else {
						return response.bodyToMono(Object.class);
					}
				}).block();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	public String admin(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}

	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@GetMapping("/manager")
	public String manager(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user")
	public String user(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
	
}