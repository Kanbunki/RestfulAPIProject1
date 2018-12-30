package com.restfulapi.myapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	@JsonProperty("documentation_url")
	private String documentationUrl;
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * @return the documentationUrl
	 */
	public String getDocumentationUrl() {
		return documentationUrl;
	}

	
	/**
	 * @param documentationUrl the documentationUrl to set
	 */
	public void setDocumentationUrl(String documentationUrl) {
		this.documentationUrl = documentationUrl;
	}
	
	private static class Detail implements Serializable {
		private static final long serialVersionUID = 1L;
		private final String target;
		private final String msg;
		private Detail(String target, String msg) {
			this.target = target;
			this.msg = msg;
		}
		@SuppressWarnings("unused")
		public String getTarget() { return target; }
		@SuppressWarnings("unused")
		public String getMsg() { return msg; }
	}
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<Detail> details = new ArrayList<>();
	
	public void addDetail(String target, String message) {
		details.add(new Detail(target, message));
	}
	
	public List<Detail> getDetails() { return details; }
}
