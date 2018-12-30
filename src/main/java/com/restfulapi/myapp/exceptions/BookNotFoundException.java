package com.restfulapi.myapp.exceptions;

import org.springframework.dao.DataAccessException;

public class BookNotFoundException extends DataAccessException {

	private static final long serialVersionUID = 1L;

	public static Integer bookId;
	
	public BookNotFoundException(String msg) {
		super(msg);
	}
}
