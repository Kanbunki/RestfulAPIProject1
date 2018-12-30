package com.restfulapi.myapp.repository;

import java.util.List;

import javax.sql.DataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.restfulapi.myapp.model.*;
import com.restfulapi.myapp.exceptions.BookNotFoundException;

@Component
@Transactional
public class BookRepository {
	
	private BookDao bookDao;
	
	@Autowired
	public BookRepository(DataSource dataSource)	{
		this.bookDao = new BookDao(dataSource);
	}

	public Book findById(Integer bookId) throws BookNotFoundException {
		return bookDao.find(bookId);
	}
	
	public List<Book> getAllBooks() {
		return bookDao.getAllBooks();
	}
	
	public Book register(Book book) throws DataAccessException {
		return bookDao.register(book);
	}
	
	public int update(Book book) {
		return bookDao.update(book);
	}
	
	public int delete(Integer bookId) {
		return bookDao.delete(bookId);
	}
}
