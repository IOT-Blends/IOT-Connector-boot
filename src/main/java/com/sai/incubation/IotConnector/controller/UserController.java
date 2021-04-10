package com.sai.incubation.IotConnector.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.incubation.IotConnector.domain.Common.HttpResponse;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.service.UserService;
import com.sai.incubation.IotConnector.utility.CommonWebUtility;

@RestController
@RequestMapping("api/user")
public class UserController {

	private Log log = LogFactory.getLog(UserController.class);

	@Autowired
	UserService userService;

	@PostMapping("/create")
	public ResponseEntity<HttpResponse> createProduct(@RequestBody User user) {
		Optional<User> userOpt = Optional.empty();

		try {
			userOpt = userService.addUser(user);
		} catch (Exception e) {
			log.error("Error while Creating a Product");
		}

		return userOpt.isPresent() ? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, userOpt.get())
				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

	@PutMapping("update")
	public ResponseEntity<HttpResponse> updateUser(@RequestBody User user) throws Exception {
		return userService.updateUser(user);
	}

	@GetMapping("/all")
	public ResponseEntity<HttpResponse> getAlUsers() {
		Optional<List<User>> userOpt = Optional.empty();

		try {
			userOpt = userService.getAllUser();
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return userOpt.isPresent() ? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, userOpt.get())
				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

	@GetMapping("/getByEmail/{id}")
	public ResponseEntity<HttpResponse> getProductById(@PathVariable String email) {
		Optional<User> userOpt = Optional.empty();

		try {
			userOpt = userService.getUserByEmail(email);
		} catch (Exception e) {
			log.error("Error while retrieving all Product");
		}

		return userOpt.isPresent() ? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, userOpt.get())
				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
	}

//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<HttpResponse> deleteProduct(@PathVariable String id) {
//
//		Optional<String> response = Optional.empty();
//		try {
//			response = productService.deleteProduct(id);
//		} catch (Exception e) {
//			log.error("Error while retrieving all Product");
//		}
//
//		return response.isPresent() ? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, response.get())
//				: CommonWebUtility.createResponseEntiry(HttpStatus.NOT_FOUND, null, "Product Not Found", null);
//	}

}
