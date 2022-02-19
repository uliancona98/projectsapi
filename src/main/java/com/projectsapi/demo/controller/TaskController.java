package com.projectsapi.demo.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.projectsapi.demo.dto.ErrorDTO;
import com.projectsapi.demo.dto.TaskDTO;
import com.projectsapi.demo.dto.request.TaskRequest;
import com.projectsapi.demo.dto.request.TaskUpdateRequest;
import com.projectsapi.demo.exception.NotFoundException;
import com.projectsapi.demo.exception.UnprocessableEntityException;
import com.projectsapi.demo.service.TaskService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/projects")
@Tag(name = "tasks", description = "The Projects Tasks API")
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	//admin, project owner of the project, developer of the project
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_OWNER', 'ROLE_USER')")
	@GetMapping("/{idProject}/tasks")
    @Operation(summary = "Find all project's tasks", description = "Endpoint that retieves all project's tasks", tags = { "tasks" }) 
	@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", 
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskDTO.class)))) })
	public ResponseEntity<Object> getTasksFromProject(@Parameter(description="Id of the project to be obtained. Cannot be empty.", required=true) @PathVariable("idProject") Integer idProject){
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = null;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			}
			boolean hasAdminRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
			.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
			return new ResponseEntity<>(taskService.findAll(idProject, username, hasAdminRole), HttpStatus.OK);
		}
		catch(NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}catch(UnsupportedOperationException ex){
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	//admin, project owner of the project, DEVELOPER
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_OWNER', 'ROLE_USER')")
	@GetMapping("/{idProject}/tasks/{idTask}")
	public ResponseEntity<Object> getTaskByIdFromProject( @Parameter(description="Id of the project to be obtained. Cannot be empty.", required=true) @PathVariable("idProject") Integer idProject, @Parameter(description="Id of the task to be obtained. Cannot be empty.", required=true) @PathVariable("idTask") Integer idTask){
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = null;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			}
			boolean hasAdminRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
			.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

			return new ResponseEntity<>(taskService.findTaskById(idProject, idTask, username, hasAdminRole), HttpStatus.OK);
		}
		catch(NotFoundException ex) {
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}catch(UnsupportedOperationException ex){
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	//admin, project owner of the project
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_OWNER')")
	@PostMapping("/{idProject}/tasks")
	public ResponseEntity<Object> addTaskToProject(@Parameter(description="Id of the project to be obtained. Cannot be empty.", required=true) @PathVariable("idProject") Integer idProject, @RequestBody @Valid TaskRequest taskRequest, BindingResult result){
		try{
			if(result.hasErrors()) {
			 	String errorsString = "";
				List<FieldError> errors = result.getFieldErrors();
				for(int i=0;i<errors.size();i++){
					if(i!=errors.size()-1){
						errorsString += errors.get(i).getField() + ": " + errors.get(i).getDefaultMessage()+ ", ";
					}else{
						errorsString += errors.get(i).getField() + ": " + errors.get(i).getDefaultMessage();
					}
				}
				return new ResponseEntity<>(new ErrorDTO(errorsString), HttpStatus.BAD_REQUEST);
			}
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = null;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			}
			boolean hasAdminRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
			.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

			return new ResponseEntity<>(taskService.saveTask(idProject, taskRequest, username, hasAdminRole), HttpStatus.CREATED);
		}catch(UnprocessableEntityException e){
			return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.CONFLICT);
		}catch(NotFoundException ex){
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
		}catch(UnsupportedOperationException ex){
			return new ResponseEntity<Object>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	//admin, project owner of the project
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_OWNER')")
	@PutMapping("/{idProject}/tasks/{idTask}")
	public ResponseEntity<Object> editProjectTask(@RequestBody @Valid  TaskUpdateRequest taskRequest,  BindingResult result, @Parameter(description="Id of the project to be obtained. Cannot be empty.", required=true) @PathVariable("idProject") Integer idProject, @Parameter(description="Id of the task to be obtained. Cannot be empty.", required=true) @PathVariable("idTask") Integer idTask)throws URISyntaxException{
		try{
			if(result.hasErrors()) {
			 	String errorsString = "";
				List<FieldError> errors = result.getFieldErrors();
				for(int i=0;i<errors.size();i++){
					if(i!=errors.size()-1){
						errorsString += errors.get(i).getField() + ": " + errors.get(i).getDefaultMessage()+ ", ";
					}else{
						errorsString += errors.get(i).getField() + ": " + errors.get(i).getDefaultMessage();
					}
				}
				return new ResponseEntity<>(new ErrorDTO(errorsString), HttpStatus.BAD_REQUEST);
			}
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = null;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			}
			boolean hasAdminRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
			.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

			return new ResponseEntity<>(taskService.editTask(idTask, idProject, taskRequest, username, hasAdminRole), HttpStatus.OK);
		}catch(NotFoundException ex){
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
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
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

