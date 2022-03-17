<<<<<<< HEAD
/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.exception.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ibm.test.aws.sqs.sqsprocessor.custom.exception.ApiError;
import com.ibm.test.aws.sqs.sqsprocessor.custom.exception.EntityNotFoundException;

/**
 * @author 02029H744
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "", "");
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	  @ExceptionHandler(Exception.class)
	  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
	    List<String> details = new ArrayList<>();
	    details.add(ex.getLocalizedMessage());
	    ApiError apiError = new ApiError(HttpStatus.PROCESSING, "", details);
	    return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	 
	  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
=======
/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.exception.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ibm.test.aws.sqs.sqsprocessor.custom.exception.ApiError;
import com.ibm.test.aws.sqs.sqsprocessor.custom.exception.EntityNotFoundException;

/**
 * @author 02029H744
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "", "");
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	  @ExceptionHandler(Exception.class)
	  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
	    List<String> details = new ArrayList<>();
	    details.add(ex.getLocalizedMessage());
	    ApiError apiError = new ApiError(HttpStatus.PROCESSING, "", details);
	    return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	 
	  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
>>>>>>> branch 'master' of https://github.com/gapal74/springexperiments.git
