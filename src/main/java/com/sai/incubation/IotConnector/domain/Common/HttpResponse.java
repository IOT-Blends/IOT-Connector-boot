package com.sai.incubation.IotConnector.domain.Common;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HttpResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyy hh:mm:ss", timezone = ""
			+ "America/New_York")
	private Date timeStamp;
	private int httpStatusCode;
	private HttpStatus httpStatus;
	private String message;
	private Object data;

	public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String message, Object data) {
		super();
		this.timeStamp = new Date();
		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.message = message;
		this.data = data;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
