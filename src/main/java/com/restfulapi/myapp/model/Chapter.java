package com.restfulapi.myapp.model;

import java.io.Serializable;

public class Chapter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer seq;
	private String title;
	private Integer pageNum;
	
	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}
	
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
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
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}
	
	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
