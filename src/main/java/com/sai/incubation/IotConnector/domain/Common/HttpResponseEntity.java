package com.sai.incubation.IotConnector.domain.Common;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public abstract class HttpResponseEntity {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyy hh:mm:ss", timezone = ""
			+ "America/New_York")
	@Setter(AccessLevel.NONE)
	private Date timeStamp;
	private int httpStatusCode;
	private HttpStatus httpStatus;
	private String message;
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = new Date();
	}
}
