package com.sai.incubation.IotConnector.convertor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.sai.incubation.IotConnector.constants.Role;
import com.sai.incubation.IotConnector.domain.EntityDocument.User;

@Component
public class UserConverter implements Converter<User, User> {

	@Override
	public User convert(User user) {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User convert(User existingUser, User currentUser) {
		
		if(!StringUtils.isEmpty(currentUser.getFirstName()))
			existingUser.setFirstName(currentUser.getFirstName());
		if(!StringUtils.isEmpty(currentUser.getLastName()))
			existingUser.setFirstName(currentUser.getLastName());
		if(!StringUtils.isEmpty(currentUser.getPhoneNumber()))
			existingUser.setPhoneNumber(currentUser.getPhoneNumber());
		if(!StringUtils.isEmpty(currentUser.getDob()))
			existingUser.setDob(currentUser.getDob());
		if(!StringUtils.isEmpty(currentUser.getGender()))
			existingUser.setGender(currentUser.getGender());
		if(!StringUtils.isEmpty(currentUser.getAddressLine1()))
			existingUser.setAddressLine1(currentUser.getAddressLine1());
		if(!StringUtils.isEmpty(currentUser.getAddressLine2()))
			existingUser.setAddressLine2(currentUser.getAddressLine2());
		if(!StringUtils.isEmpty(currentUser.getCity()))
			existingUser.setCity(currentUser.getCity());
		if(!StringUtils.isEmpty(currentUser.getState()))
			existingUser.setState(currentUser.getState());
		if(!StringUtils.isEmpty(currentUser.getZip()))
			existingUser.setZip(currentUser.getZip());
		if(!StringUtils.isEmpty(currentUser.getCountry()))
			existingUser.setCountry(currentUser.getCountry());
		if(null != currentUser.getActive())
			existingUser.setActive(currentUser.getActive());
		if(null != currentUser.getNotLocked())
			existingUser.setNotLocked(currentUser.getNotLocked());
		if(!(StringUtils.isEmpty(currentUser.getRole()))) {
			existingUser.setRole(currentUser.getRole());
			existingUser.setAuthority(Role.ROLE_SUPER_ADMIN.getAuthorities());
		}
		
		return existingUser;
	}


}
