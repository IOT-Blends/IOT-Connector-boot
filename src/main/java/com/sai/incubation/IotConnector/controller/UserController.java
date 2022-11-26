package com.sai.incubation.IotConnector.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sai.incubation.IotConnector.constants.SecurityConstant;
import com.sai.incubation.IotConnector.constants.UserConstants;
import com.sai.incubation.IotConnector.domain.Common.HttpResponseObj;
import com.sai.incubation.IotConnector.domain.Common.UserPrincipal;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.domain.responseentity.UserResponseEntity;
import com.sai.incubation.IotConnector.exception.ExceptionHandleAdvice;
import com.sai.incubation.IotConnector.exception.UserExistsException;
import com.sai.incubation.IotConnector.exception.UserNotFoundException;
import com.sai.incubation.IotConnector.service.UserService;
import com.sai.incubation.IotConnector.utility.CommonWebUtil;
import com.sai.incubation.IotConnector.utility.JwtUtil;

@RestController
@RequestMapping(path = {"/", "api/user"}) // Included "/" for overriding spring's default white label error
public class UserController extends ExceptionHandleAdvice{

	private Log log = LogFactory.getLog(UserController.class);

	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
	
	@Autowired
	public UserController(UserService userService, JwtUtil jwtUtil) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseEntity> register(@RequestBody User user) throws UserExistsException {
		Optional<User> userOpt = Optional.empty();
		userOpt = userService.addUser(user);

		return userOpt.isPresent() ? CommonWebUtil.createUserHttpResponseEntity(HttpStatus.OK, null, null, userOpt.get())
				: CommonWebUtil.createUserHttpResponseEntity(HttpStatus.BAD_REQUEST, null, "User cannot be created, please check with admin.", null);
	}
	
	@PostMapping("/login")
	public ResponseEntity<HttpResponseObj> login(@RequestBody User user) throws UserNotFoundException {
		
		Optional<User> userOpt = Optional.empty();
		UserPrincipal userPrincipal;
		HttpHeaders jwtHeader = null;
		String jwtToken = null;

		try {
			authenticate(user.getEmail(), user.getPassword());
		} catch (Exception e) {
			log.error("Authentication failure exception");
			// TODO: Create Authentication Failure Exception class
		}
		
		userOpt = userService.getUserByEmail(user.getEmail());
		if(null != userOpt) {
			userPrincipal = new UserPrincipal(userOpt.get());
			jwtHeader = getJwtHeader(userPrincipal);
			jwtToken = jwtUtil.generateJwtToken(userPrincipal);
		}
		return userOpt.isPresent() ? CommonWebUtil.createResponseEntity(HttpStatus.OK, jwtHeader, "User authentication success", jwtToken)
				: CommonWebUtil.createResponseEntity(HttpStatus.BAD_REQUEST, null, "User cannot be verified, invalid username or password.", null);
	}

	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(SecurityConstant.JWT_TOKEN_HEADER, jwtUtil.generateJwtToken(userPrincipal));
		return httpHeaders;
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (Exception e) {
			log.error("Authentication failure exception");
			// TODO: Create Authentication Failure Exception class
		}
	}

	@PutMapping("/update")
	public ResponseEntity<UserResponseEntity> updateUser(@RequestBody User user) throws UserNotFoundException, IOException {
		Optional<User> updatedUser = Optional.empty();
		updatedUser = userService.updateUser(user);
		return updatedUser.isPresent() ? CommonWebUtil.createUserHttpResponseEntity(HttpStatus.OK, null, "User data has been updated successfully", updatedUser.get())
				: CommonWebUtil.createUserHttpResponseEntity(HttpStatus.NOT_FOUND, null, "User Not Found", null);
	}

	@GetMapping("/get/all")
	public ResponseEntity<UserResponseEntity> getAllUsers() {
		Optional<List<User>> userListOpt = Optional.empty();

		try {
			userListOpt = userService.getAllUser();
		} catch (Exception e) {
			log.error("Error while retrieving all Users");
		}

		return userListOpt.isPresent() ? CommonWebUtil.createUserListHttpResponseEntity(HttpStatus.OK, null, null, userListOpt.get())
				: CommonWebUtil.createUserListHttpResponseEntity(HttpStatus.NOT_FOUND, null, "Users Not Found", null);
	}
	
	@PostMapping("/reset/password")
	public ResponseEntity<HttpResponseObj> resetPassword(@RequestBody User user) throws UserNotFoundException {
		try {
			if(!StringUtils.isEmpty(user.getEmail()))
				userService.resetPassword(user.getEmail());
		} catch (Exception e) {
			log.error("Error occurred while password reset");
			// TODO: Throw custom password reset error 
		}
		
		return CommonWebUtil.createResponseEntity(HttpStatus.OK, null, UserConstants.EMAIL_SENT, user);
	}

	@GetMapping("/get/{email}")
	public ResponseEntity<UserResponseEntity> getUserById(@PathVariable String email) {
		Optional<User> userOpt = Optional.empty();

		try {
			userOpt = userService.getUserByEmail(email);
		} catch (Exception e) {
			log.error("Error while retrieving user");
		}

		return userOpt.isPresent() ? CommonWebUtil.createUserHttpResponseEntity(HttpStatus.OK, null, "User exsits", userOpt.get())
				: CommonWebUtil.createUserHttpResponseEntity(HttpStatus.NOT_FOUND, null, "User Not Found", null);
	}

	/*@DeleteMapping("/delete/user")
	@PreAuthorize("hasAnyAuthority('user:delete')")
	public ResponseEntity<HttpResponse> deleteUser(@RequestBody User user) throws UserNotFoundException {
		String delResponse = userService.deleteUser(user);

		return !StringUtils.isEmpty(delResponse) ? CommonWebUtility.createResponseEntity(HttpStatus.NO_CONTENT, null, null, delResponse)
				: CommonWebUtility.createResponseEntity(HttpStatus.NOT_FOUND, null, "User Not Found", null);
	}*/
	
	/*@PutMapping("/update/profile/image") old comment do not delete
	public ResponseEntity<HttpResponse> updateProfileImage(@RequestBody User user) throws UserNotFoundException, IOException {
		Optional<User> updatedUser = Optional.empty();
		updatedUser = userService.updateProfileImage(user);
		return updatedUser.isPresent() ? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, updatedUser.get())
				: CommonWebUtility.createResponseEntity(HttpStatus.NOT_FOUND, null, "User Not Found", null);
	}*/
	
	/*@PostMapping(path="/update/profile/image")
	public ResponseEntity<HttpResponse> updateProfileImage(@RequestParam(name="email") String email, @RequestParam(name="profileImage") MultipartFile profileImage) throws UserNotFoundException, IOException {
		Optional<User> updatedUser = Optional.empty();
		updatedUser = userService.updateProfileImage(email, profileImage);
		return updatedUser.isPresent() ? CommonWebUtility.createResponseEntity(HttpStatus.OK, null, null, updatedUser.get())
				: CommonWebUtility.createResponseEntity(HttpStatus.NOT_FOUND, null, "User Not Found", null);
	}*/
	
	/*@GetMapping(path="/image/{username}/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
		return Files.readAllBytes(Paths.get(FileConstant.USER_FOLDER + username + FileConstant.FORWARD_SLASH + fileName));
		
	}*/
	
	/*@GetMapping(path="/image/profile/{username}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getDynamicProfileImage(@PathVariable("username") String username) throws IOException {
		URL url = new URL(FileConstant.TEMP_PROFILE_IMAGE_BASE_URL + username);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try(InputStream inputStream = url.openStream()) {
			int bytesRead;
			byte[] chunk = new byte[1024]; // To declare how many bytes of data chunk to be read at a time
			while((bytesRead = inputStream.read(chunk)) > 0) {
				byteArrayOutputStream.write(chunk, 0, bytesRead);
			}
		}
		return byteArrayOutputStream.toByteArray();
		
	} */

}
