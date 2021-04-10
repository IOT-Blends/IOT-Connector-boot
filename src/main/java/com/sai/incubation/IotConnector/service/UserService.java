package com.sai.incubation.IotConnector.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.sai.incubation.IotConnector.domain.Common.HttpResponse;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;

public interface UserService {

	public Optional<User> addUser(User user) throws Exception;

	public ResponseEntity<HttpResponse> updateUser(User user) throws Exception;

	public Optional<List<User>> getAllUser() throws Exception;

	public Optional<User> getUserByEmail(String email) throws Exception;

	public Optional<String> deleteUserByEmail(String email) throws Exception;
}
