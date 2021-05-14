package com.sai.incubation.IotConnector.constants;

import static com.sai.incubation.IotConnector.constants.Authority.*;

public enum Role {

	ROLE_USER(USER_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES),
	ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

	private String[] authorities;
	
	Role(String... authorities) {
		// TODO Auto-generated constructor stub
		this.authorities = authorities;
	}
	
	public String[] getAuthorities() {
		return authorities;
	}
}
