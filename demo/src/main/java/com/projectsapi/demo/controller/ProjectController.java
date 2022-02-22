package com.projectsapi.demo.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  com.projectsapi.demo.dto.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.projectsapi.demo.dto.ErrorDTO;
import com.projectsapi.demo.dto.request.ProjectRequest;
import com.projectsapi.demo.exception.NotFoundException;
import com.projectsapi.demo.service.ProjectService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@OpenAPIDefinition(
        info = @Info(title = "PROJECTS MANAGAMENET API", version = "v1")
)

@RestController
@RequestMapping("/projects")
@Tag(name = "projects", description = "The Projects API")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

    @Operation(summary = "Find all projects", description = "Endpoint that retieves all projects", tags = { "projects" }) 
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjectDTO.class)))) })
	@GetMapping
	public ResponseEntity<Object> findAll(
		@Parameter(description="Value to search") @RequestParam(name="search", required = false) String search,
		@Parameter(description="Pageable object with parameters as: page, size, sort") Pageable pageable
	){
		return new ResponseEntity<>(projectService.findAll(search,pageable), HttpStatus.OK);
	}

	//admin and if the project owner belongs
	@GetMapping("/{projectId}")
	@Operation(summary = "Find specific project", description = "Endpoint that retieves an specific project", tags = { "projects" }) 
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation",
                content = @Content(schema = @Schema(implementation = ProjectDTO.class))),
		@ApiResponse(responseCode = "404", description = "Project not found") ,
		@ApiResponse(responseCode = "400", description = "Bad request")
	})
	public ResponseEntity<Object> getProjectById(@Parameter(description="Id of the project to be obtained. Cannot be empty.", required=true) @PathVariable("projectId") Integer projectId){
		try {
			/*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = null;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			}
			boolean hasAdminRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
			  .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
			System.out.println(username+"HAS"+hasAdminRole);

			return new ResponseEntity<>(projectService.findProjectById(projectId, username, hasAdminRole), HttpStatus.OK);*/
			System.out.println("HIHIHIHIHIHI*********ÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑÑ"+projectId);
			return new ResponseEntity<>(projectService.findProjectById(projectId), HttpStatus.OK);

		}
		catch(NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}catch(UnsupportedOperationException ex){
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
	    String name = ex.getName();
	    String type = ex.getRequiredType().getSimpleName();
	    Object value = ex.getValue();
	    String message = String.format("'%s' should be a valid '%s' and '%s' isn't", 
	                                   name, type, value);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<?> handleTypeMismatch(InvalidFormatException ex) {
		String name = ex.getValue().toString();
		String type = ex.getTargetType().getSimpleName();
		Object value = ex.getValue();
		String message = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

}

