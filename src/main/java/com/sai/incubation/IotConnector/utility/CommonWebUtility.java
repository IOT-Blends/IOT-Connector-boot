package com.sai.incubation.IotConnector.utility;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sai.incubation.IotConnector.domain.Common.HttpResponse;

public class CommonWebUtility {

	public static ResponseEntity<HttpResponse> createResponseEntiry(HttpStatus httpStatus, HttpHeaders headers,
			String errorMessage, Object data) {

		HttpHeaders httpHeaders = headers != null ? headers : new HttpHeaders();
		HttpResponse response = new HttpResponse(httpStatus.value(), httpStatus, errorMessage, data);
		return new ResponseEntity<HttpResponse>(response, httpHeaders, httpStatus);

	}

	public static ResponseEntity<HttpResponse> incorrectRequestData(HttpStatus httpStatus, String error) {

		return CommonWebUtility.createResponseEntiry(httpStatus, null, error, null);
	}
}
