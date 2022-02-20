package com.projectsapi.demo.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorUtils {
	
	public static String getErrorMessage(BindingResult result) {
		StringBuilder sbErrors = new StringBuilder();
	    for ( FieldError error : result.getFieldErrors() ) {
	        sbErrors.append(error.getDefaultMessage());
	        sbErrors.append(" - ");
	    }
	    sbErrors.delete(sbErrors.length() - 3, sbErrors.length() - 1);
	    return sbErrors.toString().trim();
	}
}
