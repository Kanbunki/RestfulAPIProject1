package com.restfulapi.myapp.model;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
	private static final long serialVersionUID = 3896903272599657995L;

	private Integer bookId;
	private String title;
	private String author;
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
