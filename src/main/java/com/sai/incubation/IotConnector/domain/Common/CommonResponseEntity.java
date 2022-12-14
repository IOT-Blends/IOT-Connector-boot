package com.sai.incubation.IotConnector.domain.Common;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class CommonResponseEntity {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyy hh:mm:ss", timezone = ""
			+ "America/New_York")
	@Setter(AccessLevel.NONE)
	private Date timeStamp;
	private int httpStatusCode;
	private HttpStatus httpStatus;
	private String message;
	private Object data;

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = new Date();
	}
	
	public CommonResponseEntity(int httpStatusCode, HttpStatus httpStatus, String message, Object data) {
		this.timeStamp = new Date();
		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.message = message;
		this.data = data;
	}
}
