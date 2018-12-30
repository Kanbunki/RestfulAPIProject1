package com.restfulapi.myapp.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restfulapi.myapp.model.Chapter;

public class BookResource implements Serializable {

	private static final long serialVersionUID = -3636963960149122628L;
	
	private Integer bookId;
	@NotEmpty
	private String title;
	@NotEmpty
	private String author;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate publishedDate;
	private List<Chapter> chapters;
	
	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}
	
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the publishedDate
	 */
	public LocalDate getPublishedDate() {
		return publishedDate;
	}
	
	/**
	 * @param publishedDate the publishedDate to set
	 */
	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	
	/**
	 * @return the chapters
	 */
	public List<Chapter> getChapters() {
		return chapters;
	}

	
	/**
	 * @param chapters the chapters to set
	 */
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
}
