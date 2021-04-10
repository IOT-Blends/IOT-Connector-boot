package com.sai.incubation.IotConnector.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.sai.incubation.IotConnector.constants.ExceptionConstants;
import com.sai.incubation.IotConnector.domain.Common.HttpResponse;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.repository.UserRepository;
import com.sai.incubation.IotConnector.service.UserService;
import com.sai.incubation.IotConnector.utility.CommonWebUtility;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public Optional<User> addUser(User user) throws Exception {

		User userObj = userRepo.save(user);
		return userObj != null ? Optional.of(userObj) : Optional.empty();
	}

	@Override
	public ResponseEntity<HttpResponse> updateUser(User newUser) throws Exception {
		Optional<User> existingUser = Optional.empty();
		User updatedUser = null;
		try {
			if (StringUtils.isEmpty(newUser.getId())) {
				return CommonWebUtility.incorrectRequestData(HttpStatus.BAD_REQUEST,
						ExceptionConstants.PRODUCTID_NOTFOUND);
			} else {
				existingUser = this.getUserByEmail(newUser.getEmail());
			}

			if (!existingUser.isPresent()) {
				return CommonWebUtility.incorrectRequestData(HttpStatus.NOT_FOUND, ExceptionConstants.PRODUCT_NOTFOUND);
			}
			newUser.setId(existingUser.get().getId());
			updatedUser = userRepo.save(newUser);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return updatedUser != null ? CommonWebUtility.createResponseEntiry(HttpStatus.OK, null, null, updatedUser)
				: CommonWebUtility.createResponseEntiry(HttpStatus.EXPECTATION_FAILED, null,
						"Error while updating the Product", null);
	}

	@Override
	public Optional<List<User>> getAllUser() throws Exception {
		List<User> userList = userRepo.findAll();
		return userList.isEmpty() ? Optional.empty() : Optional.of(userList);
	}

	@Override
	public Optional<User> getUserByEmail(String email) throws Exception {
		User userObj = userRepo.findByEmail(email);
		return userObj != null ? Optional.of(userObj) : Optional.empty();
	}

	@Override
	public Optional<String> deleteUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
