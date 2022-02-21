package edu.itk.project.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import edu.itk.project.security.dto.ErrorDTO;

import java.util.HashSet;
import java.util.LinkedHashMap;
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
	private static final String POJECTS_MANAGEMENT_MODULE_HOST = "http://10.5.0.7:8090";

	private static final String GET_PROJECT_BY_ID = POJECTS_MANAGEMENT_MODULE_HOST + "/projects/{projecteId}";


	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/projects/{projectId}")
	public Object getAllProjects(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("projectId") Integer projectId) {
		return this.webClient
				.get()
				.uri(GET_PROJECT_BY_ID, projectId)
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
