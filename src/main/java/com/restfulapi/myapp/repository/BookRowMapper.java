package com.restfulapi.myapp.repository;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.restfulapi.myapp.model.Book;

public class BookRowMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setBookId(rs.getInt("book_id"));
		book.setTitle(rs.getString("book_title"));
		book.setAuthor(rs.getString("book_author"));
		String publishedDate = rs.getString("published_date");
		int year = Integer.parseInt(publishedDate.substring(0, 4));
		int month = Integer.parseInt(publishedDate.substring(4, 6));
		int day = Integer.parseInt(publishedDate.substring(6, 8));
		book.setPublishedDate(LocalDate.of(year, month, day));
		return book;
	}
}
