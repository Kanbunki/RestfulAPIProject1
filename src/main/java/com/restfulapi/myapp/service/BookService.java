package com.restfulapi.myapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.restfulapi.myapp.repository.BookRepository;
import com.restfulapi.myapp.exceptions.BookNotFoundException;
import com.restfulapi.myapp.model.*;

@Service
public class BookService {

	@Autowired
	BookRepository repository;
	
	public Book findBookById(Integer bookId) throws BookNotFoundException {
		return repository.findById(bookId);
	}
	
	public List<Book> findAllByCriteria(BookCriteria criteria) {
		return repository.getAllBooks().stream()
				.filter(book -> 
						(criteria.getTitle() == null || book.getTitle().contains(criteria.getTitle()))
						&& (criteria.getAuthor() == null || book.getAuthor().contains(criteria.getAuthor()))
						&& (criteria.getPublishedDate() == null || book.getPublishedDate().equals(criteria.getPublishedDate())))
				.sorted((o1, o2) -> o1.getPublishedDate().compareTo(o2.getPublishedDate()))
				.collect(Collectors.toList());
	}
	
	public Book registerBook(Book book) throws DataAccessException {
		return repository.register(book);
	}
	
	public int updateBook(Book book) {
		return repository.update(book);
	}
	
	public int deleteBook(Integer bookId) {
		return repository.delete(bookId);
	}
}
