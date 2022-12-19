package com.sai.incubation.IotConnector.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sai.incubation.IotConnector.domain.Common.CommonResponseEntity;
import com.sai.incubation.IotConnector.domain.EntityDocument.Product;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.domain.responseentity.ProductResponseEntity;
import com.sai.incubation.IotConnector.domain.responseentity.UserResponseEntity;

public class CommonWebUtil {

	public static ResponseEntity<CommonResponseEntity> createResponseEntity(HttpStatus httpStatus, HttpHeaders headers,
			String message, Object data) {
		HttpHeaders httpHeaders = headers != null ? headers : new HttpHeaders();
		CommonResponseEntity response = new CommonResponseEntity(httpStatus.value(), httpStatus, message, data);
		return new ResponseEntity<CommonResponseEntity>(response, httpHeaders, httpStatus);

	}

	/*public static ResponseEntity<HttpResponse> incorrectRequestData(HttpStatus httpStatus, String error) {

		return CommonWebUtility.createResponseEntity(httpStatus, null, error, null);
	}*/
	
	public static ResponseEntity<UserResponseEntity> createUserHttpResponseEntity(HttpStatus httpStatus, HttpHeaders headers,
			String message, Object data) {
		HttpHeaders httpHeaders = headers != null ? headers : new HttpHeaders();
		List<User> users = new ArrayList<User>();
		users.add((User)data);
		UserResponseEntity responseEntity = new UserResponseEntity(users);
		responseEntity.setHttpStatusCode(httpStatus.value());
		responseEntity.setHttpStatus(httpStatus);
		responseEntity.setMessage(message);
		return new ResponseEntity<UserResponseEntity>(responseEntity, httpHeaders, httpStatus);
	}
	
	@SuppressWarnings("unchecked")
	public static ResponseEntity<UserResponseEntity> createUserListHttpResponseEntity(HttpStatus httpStatus, HttpHeaders headers,
			String message, Object data) {
		HttpHeaders httpHeaders = headers != null ? headers : new HttpHeaders();
		List<User> users = new ArrayList<User>();
		users.addAll((List<User>)data);
		UserResponseEntity responseEntity = new UserResponseEntity(users);
		responseEntity.setHttpStatusCode(httpStatus.value());
		responseEntity.setHttpStatus(httpStatus);
		responseEntity.setMessage(message);
		return new ResponseEntity<UserResponseEntity>(responseEntity, httpHeaders, httpStatus);
	}
	
	public static ResponseEntity<ProductResponseEntity> createProductHttpResponseEntity(HttpStatus httpStatus, HttpHeaders headers,
			String message, Object data) {
		HttpHeaders httpHeaders = headers != null ? headers : new HttpHeaders();
		ProductResponseEntity responseEntity = new ProductResponseEntity((Product)data);
		responseEntity.setHttpStatusCode(httpStatus.value());
		responseEntity.setHttpStatus(httpStatus);
		responseEntity.setMessage(message);
		return new ResponseEntity<ProductResponseEntity>(responseEntity, httpHeaders, httpStatus);
	}
}
