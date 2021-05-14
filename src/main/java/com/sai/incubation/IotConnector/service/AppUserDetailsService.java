package com.sai.incubation.IotConnector.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sai.incubation.IotConnector.domain.Common.UserPrincipal;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;
import com.sai.incubation.IotConnector.repository.UserRepository;

@Service
@Qualifier("AppUserDetailsService") // Specific name used by bean when created
public class AppUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserRepository userRepo;
	
	/*
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException { // TODO Auto-generated method stub // Implement
	 * user details with data base here return new User("test", "test", new
	 * ArrayList<>()); }
	 */
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User userObj = userRepo.findByEmail(email);
		if(null == userObj) {
			logger.error("User not found by user id: "+email);
			throw new UsernameNotFoundException("User not found by user id: "+email);
		} else {
			userObj.setLastLoginDateDisplay(userObj.getLastLoginDate());
			userObj.setLastLoginDate(new Date());
			userRepo.save(userObj);
			UserPrincipal userPrincipal = new UserPrincipal(userObj);
			logger.info("Returning found user bu user id: "+ email);
		}
		return null;
	}

}
