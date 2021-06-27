package com.sai.incubation.IotConnector.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.sai.incubation.IotConnector.domain.Common.HttpResponse;

@RestControllerAdvice
public class ExceptionHandleAdvice implements ErrorController{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String INTERNAL_SERVER_ERROR = "An error occurred while processing the request";
	private static final String INCORRECT_CREDENTIALS = "Username / password is incorrect. Please try again";
	private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
	private static final String PERMISSION_DENIED = "You don't have enough permission";
	private static final String METHOD_NOT_ALLOWED = "This request method is not allowed on this endpoint, please send a '%s' request";
	public static final String ERROR_PATH = "/error";
	private static final String RESOURCE_NOT_FOUND = "Requested resource not found";
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<HttpResponse> badCredentialsException(){
		return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<HttpResponse> accessDeniedException(){
		return createHttpResponse(HttpStatus.FORBIDDEN, PERMISSION_DENIED);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException ex){
		return createHttpResponse(HttpStatus.UNAUTHORIZED, ex.getMessage().toUpperCase());
	}
	
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<HttpResponse> userExistsException(UserExistsException ex){
		return createHttpResponse(HttpStatus.FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<HttpResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
		HttpMethod supportedMethod = Objects.requireNonNull(ex.getSupportedHttpMethods()).iterator().next();
		return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_NOT_ALLOWED, supportedMethod));
	}
	
	/*@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<HttpResponse> internalServerError(NoHandlerFoundException ex){
		logger.error(ex.getMessage());
		return createHttpResponse(HttpStatus.BAD_REQUEST, "Requested page is not found - 404");
	}*/
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse> internalServerError(Exception ex){
		logger.error(ex.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<HttpResponse> ioException(IOException ex){
		logger.error(ex.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
	}

	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
	}
	
	@RequestMapping(ERROR_PATH)
	public ResponseEntity<HttpResponse> resourceNotFound() {
		return createHttpResponse(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERROR_PATH;
	}

}
