package com.restfulapi.myapp.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restfulapi.myapp.service.*;
import com.restfulapi.myapp.exceptions.BookNotFoundException;
import com.restfulapi.myapp.model.*;

@RestController
@RequestMapping("/books")
public class BookRestController {

	@Autowired
	BookService bookService;
	
	@RequestMapping(method=RequestMethod.GET, path="{bookId}")
	public BookResource findBook(@PathVariable Integer bookId) throws BookNotFoundException {
		
		Book book = bookService.findBookById(bookId);
		
		BookResource resource = new BookResource();
		resource.setBookId(book.getBookId());
		resource.setTitle(book.getTitle());
		resource.setAuthor(book.getAuthor());
		resource.setPublishedDate(book.getPublishedDate());
		
		return resource;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<BookResource> searchBooks(BookResourceQuery query) {
		BookCriteria criteria = new BookCriteria();
		criteria.setTitle(query.getTitle());
		criteria.setAuthor(query.getAuthor());
		criteria.setPublishedDate(query.getPublishedDate());
		
		List<Book> bookList = bookService.findAllByCriteria(criteria);
		
		return bookList.stream().map(book -> {
			BookResource resource = new BookResource();
			resource.setBookId(book.getBookId());
			resource.setTitle(book.getTitle());
			resource.setAuthor(book.getAuthor());
			resource.setPublishedDate(book.getPublishedDate());
			resource.setChapters(book.getChapters());
			return resource;
		}).collect(Collectors.toList());
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> registerBook(@RequestBody @Valid BookResource bookResource,
													UriComponentsBuilder uriBuilder) throws DataAccessException{
		Book book = new Book();
		book.setTitle(bookResource.getTitle());
		book.setAuthor(bookResource.getAuthor());
		book.setPublishedDate(bookResource.getPublishedDate());
		
		Book newBook = bookService.registerBook(book);
		
		//String resourceUri = "http://win10host.local:8080/restfulapi/myapp/books/" + newBook.getBookId();
		URI resourceUri = uriBuilder.path("/books/{bookId}")
								    .buildAndExpand(newBook.getBookId())
								    .encode()
								    .toUri();
		
		return ResponseEntity.created(resourceUri).build();
	}
	
	@RequestMapping(path="{bookId}", method=RequestMethod.PUT)
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> updateBook(@PathVariable Integer bookId, 
												@RequestBody BookResource bookResource, 
												UriComponentsBuilder uriBuilder) {
		Book book = new Book();
		book.setBookId(bookId);
		book.setTitle(bookResource.getTitle());
		book.setAuthor(bookResource.getAuthor());
		book.setPublishedDate(bookResource.getPublishedDate());
		
		bookService.updateBook(book);
		
		URI resourceUri = MvcUriComponentsBuilder.relativeTo(uriBuilder)
												 .withMethodCall(
														 MvcUriComponentsBuilder.on(BookRestController.class)
														.findBook(book.getBookId()))
												 .build().encode().toUri();
		
		return ResponseEntity.created(resourceUri).build();
	}
	
	@RequestMapping(path="{bookId}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable Integer bookId) {
		bookService.deleteBook(bookId);
	}
}
