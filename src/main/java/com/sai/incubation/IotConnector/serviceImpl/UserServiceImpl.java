package com.sai.incubation.IotConnector.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sai.incubation.IotConnector.constants.ExceptionConstants;
import com.sai.incubation.IotConnector.constants.FileConstant;
import com.sai.incubation.IotConnector.constants.Role;
import com.sai.incubation.IotConnector.constants.UserConstants;

import com.sai.incubation.IotConnector.convertor.UserConverter;
import com.sai.incubation.IotConnector.domain.Common.UserPrincipal;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.exception.UserExistsException;
import com.sai.incubation.IotConnector.exception.UserNotFoundException;
import com.sai.incubation.IotConnector.repository.UserRepository;
import com.sai.incubation.IotConnector.service.EmailService;
import com.sai.incubation.IotConnector.service.LoginAttemptService;
import com.sai.incubation.IotConnector.service.UserService;

@Service // To denote its a actual service class
@Transactional // Manage propagation when dealing with transactions
@Qualifier("userDetailsService") // Specific name used by bean when created
public class UserServiceImpl implements UserService, UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserConverter userConverter;

	@Override
	public Optional<User> addUser(User user) throws UserExistsException {

		User userObj;
		try {
			//String password = generatePassword();
			user.setPassword(encodePassword(user.getPassword()));
			user.setRole(Role.ROLE_USER.name());
			// user.setRole(getRoleEnumName("user").name());
			user.setAuthority(Role.ROLE_USER.getAuthorities());
			//user.setAuthority(getRoleEnumName("user").getAuthorities());
			user.setActive(Boolean.TRUE);
			user.setNotLocked(Boolean.FALSE);
			user.setProfileImageUrl(getTemporaryProfImgUrl(user.getFirstName()));
			userObj = userRepo.save(user);
			//logger.info("Registered new user password: "+ password);
			//emailService.sendNewPasswordEmail(user.getFirstName(), user.getPassword(), user.getEmail());
		} catch (DuplicateKeyException e) {
			throw new UserExistsException("Username: "+ user.getEmail() +", already exists");
		}
		catch (Exception e) {
			throw new UserExistsException(e.getMessage());
		}
		
		return userObj != null ? Optional.of(userObj) : Optional.empty();
	}

	@Override
	public Optional<User> updateUser(User currentUser) throws UserNotFoundException, IOException {
		Optional<User> existingUser = Optional.empty();
		User updatedUser = null;
			if (StringUtils.isEmpty(currentUser.getEmail())) {
				/*return CommonWebUtility.incorrectRequestData(HttpStatus.BAD_REQUEST,
						ExceptionConstants.USERID_NOTFOUND);*/
				throw new UserNotFoundException(ExceptionConstants.USERID_NOTFOUND + "Empty id");
			} else {
				existingUser = this.getUserByEmail(currentUser.getEmail());
			}

			if (!existingUser.isPresent()) {
				/*return CommonWebUtility.incorrectRequestData(HttpStatus.NOT_FOUND, ExceptionConstants.USER_NOTFOUND);*/
				throw new UserNotFoundException(ExceptionConstants.USERID_NOTFOUND + " : " + currentUser.getEmail());
			}
			if(null != currentUser) {
				userConverter.convert(existingUser.get(), currentUser);
			}
			updatedUser = userRepo.save(existingUser.get());

		return updatedUser != null ? Optional.of(updatedUser) : Optional.empty();
		/*return updatedUser != null ? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, updatedUser)
				: CommonWebUtility.createResponseEntity(HttpStatus.EXPECTATION_FAILED, null,
						"Error while updating User details", null);*/
	}

	@Override
	public Optional<List<User>> getAllUser() throws Exception {
		List<User> userList = userRepo.findAll();
		return userList.isEmpty() ? Optional.empty() : Optional.of(userList);
	}

	@Override
	public Optional<User> getUserByEmail(String email) throws UserNotFoundException {
		User userObj = userRepo.findByEmail(email);
		if(null == userObj)
			throw new UserNotFoundException(ExceptionConstants.USERID_NOTFOUND + email);
		return userObj != null ? Optional.of(userObj) : Optional.empty();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User userObj = userRepo.findByEmail(email);
		if(null == userObj) {
			logger.error("User not found by user id: "+email);
			throw new UsernameNotFoundException("User not found by user id: "+email);
		} else {
			validateLoginAttempt(userObj);
			userObj.setLastLoginDateDisplay(userObj.getLastLoginDate());
			userObj.setLastLoginDate(new Date());
			userRepo.save(userObj);
			UserPrincipal userPrincipal = new UserPrincipal(userObj);
			logger.info("Returning found user bu user id: "+ email);
			return userPrincipal;
		}
	}

	@Override
	public String deleteUser(User user) throws UserNotFoundException {
		// TODO Auto-generated method stub
		try {
			if(null != user && !StringUtils.isEmpty(user.getEmail()))
				userRepo.deleteByEmail(user);
			else
				throw new Exception(UserConstants.CANNOT_DELETE_USER);
		} catch(Exception ex) {
			throw new UserNotFoundException(ExceptionConstants.USER_NOTFOUND);
		}
		return UserConstants.USER_DELETED_SUCCESSFULLY;
	}

	@Override
	public void resetPassword(String email) throws UsernameNotFoundException{
		// TODO Auto-generated method stub
		try {
			Optional<User> existingUser = this.getUserByEmail(email);
			if(!existingUser.isPresent()) {
				throw new UsernameNotFoundException(UserConstants.NO_USER_FOUND_BY_EMAIL + email);
			}
			String password = generatePassword();
			existingUser.get().setPassword(encodePassword(password));
			userRepo.save(existingUser.get());
			logger.info("Registered new user password: "+ password);
			emailService.sendNewPasswordEmail(existingUser.get().getFirstName(), password, existingUser.get().getEmail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	@Override
	public Optional<User> updateProfileImage(User user) throws UserNotFoundException{
		// TODO Auto-generated method stub
		Optional<User> existingUser = Optional.empty();
		try {
			existingUser = this.getUserByEmail(user.getEmail());
			if(!existingUser.isPresent()) {
				throw new UserNotFoundException(UserConstants.NO_USER_FOUND_BY_EMAIL + user.getEmail());
			}
			saveProfileImage(existingUser.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existingUser;
	}
	
	private void saveProfileImage(User user) throws IOException {
		// TODO Auto-generated method stub
		if(null != user.getProfileImage()) {
			Path userFolder = Paths.get(FileConstant.USER_FOLDER + user.getFirstName()).toAbsolutePath().normalize();
			if(!Files.exists(userFolder)) {
				Files.createDirectories(userFolder);
				logger.info(FileConstant.DIRECTORY_CREATED);
			}
			Files.deleteIfExists(Paths.get(userFolder + user.getFirstName() + FileConstant.DOT + FileConstant.JPG_EXTENSION));
			Files.copy(user.getProfileImage().getInputStream(), userFolder.resolve(user.getFirstName() + FileConstant.DOT + FileConstant.JPG_EXTENSION), StandardCopyOption.REPLACE_EXISTING);
			user.setProfileImageUrl(setProfileImageUrl(user.getFirstName()));
			userRepo.save(user);
			logger.info(FileConstant.FILE_SAVED_IN_SYSTEM + user.getProfileImage().getOriginalFilename());
		}
	}
	
	private String setProfileImageUrl(String firstName) {
		// TODO Auto-generated method stub
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.USER_IMAGE_PATH 
				+ firstName + FileConstant.FORWARD_SLASH + firstName + FileConstant.DOT + FileConstant.JPG_EXTENSION).toUriString();
	}

	private String getTemporaryProfImgUrl(String firstname) {
		// TODO Auto-generated method stub
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.DEFAULT_USER_IMAGE_PATH + firstname).toUriString();
	}

	private String encodePassword(String generatePassword) {
		// TODO Auto-generated method stub
		return passwordEncoder.encode(generatePassword);
	}

	private String generatePassword() {
		// TODO Auto-generated method stub
		SecureRandom SECURE_RANDOM = new SecureRandom();
		return new BigInteger(8, SECURE_RANDOM).toString(8);
	}
	
	private void validateLoginAttempt(User userObj) {
		// TODO Auto-generated method stub
		if(userObj.getNotLocked()) {
			if(loginAttemptService.hasExceededMaxAttempts(userObj.getEmail())) {
				userObj.setNotLocked(false);
			} else {
				userObj.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(userObj.getEmail());
		}
	}
	
	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	@Override
	public Optional<User> updateProfileImage(String email, MultipartFile profileImage) {
		// TODO Auto-generated method stub
		Optional<User> existingUser = Optional.empty();
		try {
			existingUser = this.getUserByEmail(email);
			if(!existingUser.isPresent()) {
				throw new UserNotFoundException(UserConstants.NO_USER_FOUND_BY_EMAIL + email);
			}
			saveProfileImage(existingUser.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existingUser;
	}

}
