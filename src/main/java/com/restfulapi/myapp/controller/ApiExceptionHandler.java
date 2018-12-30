package com.restfulapi.myapp.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;

import com.restfulapi.myapp.exceptions.BookNotFoundException;
import com.restfulapi.myapp.model.ApiError;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
																HttpStatus status, WebRequest request) {
		ApiError apiError = createApiError(ex, "System.error is occurred");
		return super.handleExceptionInternal(ex, apiError, headers, status, request);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final Map<Class<? extends Exception>, String> messageMappings =
			Collections.unmodifiableMap(new LinkedHashMap() {
				private static final long serialVersionUID = 1L;
			{
				put(HttpRequestMethodNotSupportedException.class, 
						"Only request method 'GET' and 'POST' are supported.");
				put(BookNotFoundException.class,
						"The book with id ? is not available.");
				put(MethodArgumentNotValidException.class,
						"Request value is invalid");
			}});
	
	private String resolveMessage(Exception ex, String defaultMessage) {
		String msg = messageMappings.entrySet().stream()
				.filter(entry -> entry.getKey().isAssignableFrom(ex.getClass())).findFirst()
				.map(Map.Entry::getValue).orElse(defaultMessage);
		if (ex.getClass().equals(BookNotFoundException.class)) {
			msg = msg.replace("?", BookNotFoundException.bookId.toString());
		}
		return msg;
	}
	
	private ApiError createApiError(Exception ex, String defaultMessage) {
		ApiError apiError = new ApiError();
		apiError.setMessage(resolveMessage(ex, defaultMessage));
		apiError.setDocumentationUrl("http://restfulapi.com/myapp/errors");
		return apiError;
	}
	
	// BookNotFoundExceptionHandler
	@ExceptionHandler
	public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND, request);
	}
	
	// DataIntegrityViolationException
	@ExceptionHandler
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND, request);
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = createApiError(ex, ex.getMessage());
//		ex.getBindingResult().getGlobalErrors().stream()
//			.forEach(e -> apiError.addDetail(e.getObjectName(), getMessage(e, request)));
		ex.getBindingResult().getFieldErrors().stream()
			.forEach(e -> apiError.addDetail(e.getField(), getMessage(e, request)));
		return super.handleExceptionInternal(ex, apiError, headers, status, request);
	}
	
	private String getMessage(MessageSourceResolvable resolvable, WebRequest request) {
		return messageSource.getMessage(resolvable, request.getLocale());
	}
}
