package com.restfulapi.myapp.repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.dao.DataAccessException;

import com.restfulapi.myapp.model.*;
import com.restfulapi.myapp.exceptions.BookNotFoundException;

public class BookDao {
	
	private JdbcTemplate jdbcTemp;
	private NamedParameterJdbcTemplate namedParamJdbcTemp;
	private DateTimeFormatter dtFormatter;

	public BookDao(DataSource dataSource) {
		this.jdbcTemp = new JdbcTemplate(dataSource);
		this.namedParamJdbcTemp = new NamedParameterJdbcTemplate(dataSource);
		this.dtFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	}
	
	// IDで書籍検索
	public Book find(Integer bookId) throws BookNotFoundException {
		String sql = "SELECT * FROM book "
				   + "WHERE book_id = ?";
		Book foundBook;
		try {
			foundBook = jdbcTemp.queryForObject(sql, (rs, rowNum) -> {
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
			}, bookId);
		} catch(DataAccessException ex) {
			BookNotFoundException bnfEx = new BookNotFoundException(ex.getMessage());
			BookNotFoundException.bookId = bookId;
			throw bnfEx;
		}
		
		return foundBook;
	}
	
	// 書籍全件検索
	public List<Book> getAllBooks() {
		String sql = "SELECT * FROM book";
		BookRowMapper rowMapper = new BookRowMapper();
		List<Book> bookList = jdbcTemp.query(sql, rowMapper);
		for (Book book : bookList) {
			book.setChapters(getChaptersById(book.getBookId()));
		}
		return bookList;
	}
	
	// 書籍IDでチャプター検索
	private List<Chapter> getChaptersById(Integer bookId) {
		String sql = "SELECT * FROM chapter "
				   + "WHERE book_id = ? "
				   + "ORDER BY chapter_seq";
		List<Chapter> chapterList = jdbcTemp.query(sql, (rs, rowNum) -> {
			Chapter chapter = new Chapter();
			chapter.setSeq(rs.getInt("chapter_seq"));
			chapter.setTitle(rs.getString("chapter_title"));
			chapter.setPageNum(rs.getInt("page_num"));
			return chapter;
		}, bookId);
		if (chapterList.isEmpty()) return null;
		return chapterList;
	}
	
	// パラメータマップを作成
	private Map<String, Object> createParamMap(Book book) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bookId", book.getBookId());
		paramMap.put("title", book.getTitle());
		paramMap.put("author", book.getAuthor());
		paramMap.put("publishedDate", book.getPublishedDate().format(dtFormatter));
		return paramMap;
	}
	
	// 書籍情報から書籍IDを取得
	private Integer getBookId(Book book) {
		String sql = "SELECT book_id FROM book "
				   + "WHERE book_title = :title "
				   + "AND book_author = :author "
				   + "AND published_date = :publishedDate";
		return namedParamJdbcTemp.queryForObject(sql, createParamMap(book), Integer.class);
	}
	
	// 書籍登録
	public Book register(Book book) throws DataAccessException {
		String sql = "INSERT INTO book (book_title, book_author, published_date) "
				   + "VALUES (:title, :author, :publishedDate)";
		namedParamJdbcTemp.update(sql, createParamMap(book));
		return this.find(getBookId(book));
	}
	
	// 書籍更新
	public int update(Book book) {
		String sql = "UPDATE book "
				   + "SET book_title = :title, "
				   + "    book_author = :author, "
				   + "    published_date = :publishedDate "
				   + "WHERE book_id = :bookId";
		return namedParamJdbcTemp.update(sql, createParamMap(book));
	}
	
	// 書籍削除
	public int delete(Integer bookId) {
		String sql = "DELETE FROM book "
				   + "WHERE book_id = ?";
		return jdbcTemp.update(sql, bookId);
	}
}
