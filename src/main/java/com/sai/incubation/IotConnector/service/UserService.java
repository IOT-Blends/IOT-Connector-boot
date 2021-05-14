package com.sai.incubation.IotConnector.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.exception.UserExistsException;
import com.sai.incubation.IotConnector.exception.UserNotFoundException;

public interface UserService {

	public Optional<User> addUser(User user) throws UserExistsException;
	
	public Optional<User> updateUser(User user) throws UserNotFoundException, IOException;

	public Optional<List<User>> getAllUser() throws Exception;

	public Optional<User> getUserByEmail(String email) throws UserNotFoundException;

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
	
	public String deleteUser(User user) throws UserNotFoundException;
	
	public void resetPassword(String email) throws UserNotFoundException;
	
	public Optional<User> updateProfileImage(User user) throws UserNotFoundException;

	public Optional<User> updateProfileImage(String email, MultipartFile profileImage);
}
